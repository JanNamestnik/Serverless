import argparse
from mpi4py import MPI
import queue
import time
import os
import logging
from threading import Event 
from block import Block, Blockchain
from mining import mine_block

def setup_logger(rank):
    log_filename = f"process_{rank}.log"
    logging.basicConfig(
        filename=log_filename, level=logging.INFO, format="%(asctime)s - %(message)s"
    )
    return logging.getLogger()


def main(threads):
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup_logger(rank)
    stop_event = Event()
    logger.info(f"Rank {rank} started")
    difficulty = 5
    ADJUSTMENT_INTERVAL = 5  # Adjust difficulty every 5 blocks
    BLOCK_CREATION_TIME = 10

    blockchain = Blockchain(difficulty, logger)

    log_filename = f"process_{rank}.log"
    # create queue for data, that will be stored in blockchain
    data_queue = queue.Queue()
    for i  in range( 8080, 8180):
        data_queue.put(f"Data {i}")
 

    # Rank 0 is the master
    with open(log_filename, "w") as log_file:
        os.dup2(log_file.fileno(), 1)  # Redirect stdout to the log file
        logger.info(f"Rank {rank} started")
        logger.info(vars(blockchain.get_last_block()))
        while True:
            if rank == 0:
                # Adjust difficulty if needed
                if data_queue.empty():
                    logger.info("Data queue is empty")
                    time.sleep(1)
                else:
                    new_difficulty = blockchain.difficulty
                    if len(blockchain.chain) % ADJUSTMENT_INTERVAL == 0 and len(blockchain.chain) >= ADJUSTMENT_INTERVAL:
                        new_difficulty = blockchain.adjust_difficulty(BLOCK_CREATION_TIME, ADJUSTMENT_INTERVAL)
                    if new_difficulty != blockchain.difficulty:
                        logger.info(f"Difficulty adjusted: {blockchain.difficulty} -> {new_difficulty}")
                    blockchain.difficulty = new_difficulty
                    
                    # Create a block that will be sent to workers
                    last_block = blockchain.get_last_block()
                    new_block = Block(
                        len(blockchain.chain),
                        "New Block Data",
                        last_block.hash,
                        blockchain.difficulty,
                    )
                    if(not data_queue.empty()):
                        new_block.data = data_queue.get()   
                    logger.info("Block sent to workers:")
                    logger.info(vars(new_block))
                    
                    # Send new block to workers
                    for i in range(1, size):
                        comm.send(new_block, dest=i)

                    mined_block = None
                    # Listen to all workers for found blocks
                    worker_block = comm.recv(source=MPI.ANY_SOURCE)
                    if worker_block is not None:
                        mined_block = worker_block
                        comm.bcast("stop", root=0)
                        logger.info("Mined block found:")
                        logger.info(vars(mined_block))
                    else:
                        logger.info(f"Worker block is None {worker_block}")
                        

                    if mined_block:
                        blockchain.add_block(block=mined_block, logger=logger)
                        logger.info("Block added to blockchain")
                        logger.info(len(blockchain.chain))
                        if blockchain.validate_chain():
                            logger.info("Blockchain valid")
                        else:
                            logger.info("Blockchain invalid. Debugging...")
                            for i in range(1, len(blockchain.chain)):
                                if not blockchain.validate_block(blockchain.chain[i]):
                                    logger.info(
                                        f"Issue with block at index {i}:",
                                        vars(blockchain.chain[i]),
                                    )
                        for block in blockchain.chain:
                            logger.info(vars(block))
                            logger.info("====================================")
                    else:
                        logger.info("No valid block found.")
            else:
                while True:
                    # Check if a new message is available from the master
                    if comm.Iprobe(source=0):
                        message = comm.recv(source=0)
                        stop_event = Event()
                        # If it's a new block, start mining
                        if isinstance(message, Block):
                            new_block = message
                            logger.info("Received new block:")
                            logger.info(vars(new_block))

                            start_nonce = rank * (2**32 // size)
                            end_nonce = (rank + 1) * (2**32 // size)

                            mined_block = mine_block(new_block, start_nonce, end_nonce,stop_event, comm, logger ,threads)
                            if(mined_block is not None):
                                logger.info("Mined block send by worker:")
                                logger.info(vars(mined_block))
                                comm.send(mined_block, dest=0)

                            if mined_block:
                                logger.info("Mined block found:")
                                logger.info(vars(mined_block))
                                break

                        # If a stop signal is received, break and await new instructions
                        elif message == "stop":
                            logger.info("Stop signal received. Waiting for new block.")
                            break

                    # Perform other tasks or idle
                    # Optional: Add a small sleep to reduce CPU usage during idle
                    time.sleep(0.1)
                    logger.info("Worker idling")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="MPI Script with Thread Argument")
    parser.add_argument(
        "--threads", type=int, default=1, help="Number of threads per process"
    )
    args = parser.parse_args()
    main(args.threads)
