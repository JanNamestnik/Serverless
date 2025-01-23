import argparse
from mpi4py import MPI
import queue
import time
import os
import logging
from threading import Event
import threading 
from block import Block, Blockchain
from mining import mine_block
from flask import Flask, jsonify, request
from flask_swagger_ui import get_swaggerui_blueprint
from mining import parallel_mine_block_threading

# Initialize Flask app
app = Flask(__name__)

# Swagger configuration
SWAGGER_URL = '/swagger'
API_URL = '/static/swagger.json'
swaggerui_blueprint = get_swaggerui_blueprint(SWAGGER_URL, API_URL)
app.register_blueprint(swaggerui_blueprint, url_prefix=SWAGGER_URL)

def setup_logger(rank):
    log_filename = f"process_{rank}.log"
    with open(log_filename, "w") as log_file:
        log_file.truncate(0)  # Clear the log file
    logging.basicConfig(
        filename=log_filename, level=logging.INFO, format="%(asctime)s - %(message)s"
    )
    return logging.getLogger()
 
difficulty = 5
data_queue = queue.Queue()
for i in range(8080, 8090):
    data_queue.put(f"Data {i}")

blockchain = Blockchain(difficulty)

@app.route('/add_to_queue', methods=['POST'])
def add_to_queue():
    data = request.json.get('data')
    if data:
        data_queue.put(data)
        return jsonify({"message": "Data added to queue"}), 201
    return jsonify({"error": "No data provided"}), 400

@app.route('/get_blockchain', methods=['GET'])
def get_blockchain():
    chain = [vars(block) for block in blockchain.chain]
    return jsonify({"blockchain": chain}), 200

def run_flask_app():
    app.run(host='0.0.0.0', port=5000, debug=False)


def main(threads):
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup_logger(rank)
    stop_event = Event()

    ADJUSTMENT_INTERVAL = 5  # Adjust difficulty every 5 blocks
    BLOCK_CREATION_TIME = 10

    log_filename = f"process_{rank}.log"
    if rank == 0:
        flask_thread = threading.Thread(target=run_flask_app, daemon=True)
        flask_thread.start()

    with open(log_filename, "w") as log_file:
        os.dup2(log_file.fileno(), 1)  # Redirect stdout to the log file
        logger.info(vars(blockchain.get_last_block()))
        logger.info("thread")
        logger.info(threads)    
        start_time = time.time()

        while True:
            if rank == 0:
                if data_queue.empty():
                    logger.info("Data queue is empty")
                    time.sleep(1)
                    elapsed_time = time.time() - start_time
                    logger.info(f"Time elapsed since start: {elapsed_time:.2f} seconds")
                else:
                    new_difficulty = blockchain.difficulty
                    if len(blockchain.chain) % ADJUSTMENT_INTERVAL == 0 and len(blockchain.chain) >= ADJUSTMENT_INTERVAL:
                        new_difficulty = blockchain.adjust_difficulty(BLOCK_CREATION_TIME, ADJUSTMENT_INTERVAL)
                    if new_difficulty != blockchain.difficulty:
                        logger.info(f"Difficulty adjusted: {blockchain.difficulty} -> {new_difficulty}")
                    blockchain.difficulty = new_difficulty

                    last_block = blockchain.get_last_block()
                    new_block = Block(
                        len(blockchain.chain),
                        "New Block Data",
                        last_block.hash,
                        blockchain.difficulty,
                    )
                    if not data_queue.empty():
                        new_block.data = data_queue.get()   
                    logger.info("Block sent to workers:")
                    logger.info(vars(new_block))

                    for i in range(1, size):
                        comm.send(new_block, dest=i)

                    mined_block = None
                    worker_block = comm.recv(source=MPI.ANY_SOURCE)
                    if worker_block is not None:
                        mined_block = worker_block
                        comm.bcast("stop", root=0)
                        logger.info("Mined block found:")
                        logger.info(vars(mined_block))                    

                    if mined_block:
                        blockchain.add_block(block=mined_block, logger=logger)
                        logger.info("Block added to blockchain")
                        logger.info(len(blockchain.chain))
                        if blockchain.validate_chain():
                            logger.info("Blockchain valid")
                   
            else:
                while True:
                    if comm.Iprobe(source=0):
                        message = comm.recv(source=0)
                        stop_event = Event()
                        if isinstance(message, Block):
                            new_block = message
                            logger.info("Received new block:")
                            logger.info(vars(new_block))

                            start_nonce = (rank - 1) * threads 
                            logger.info(f"Rank : {rank} Start nonce: {start_nonce}") 
                            mined_block = parallel_mine_block_threading(new_block, start_nonce, threads, comm, logger, size, stop_event)  
                            logger.info("block mined")
                            if mined_block is not None:
                                logger.info("Mined block send by worker:")
                                logger.info(vars(mined_block))
                                comm.send(mined_block, dest=0)

                            if mined_block:
                                logger.info("Mined block found:")
                                logger.info(vars(mined_block))
                                break

                        elif message == "stop":
                            logger.info("Stop signal received. Waiting for new block.")
                            break

                    time.sleep(0.1)
                    logger.info("Worker idling")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="MPI Script with Thread Argument")
    parser.add_argument(
        "--threads", type=int, default=1, help="Number of threads per process"
    )
    args = parser.parse_args()
    main(args.threads)
