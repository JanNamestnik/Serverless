import argparse
from mpi4py import MPI
import queue
import time
import os
import logging
from threading import Event

from block import Block, Blockchain
from mining import parallel_mine_block_threading


def setup_logger(rank):
    log_filename = f"process_{rank}.log"
    with open(log_filename, "w") as log_file:
        log_file.truncate(0)  # Clear the log file
    logging.basicConfig(
        filename=log_filename, level=logging.INFO, format="%(asctime)s - %(message)s"
    )
    return logging.getLogger()
 
difficulty = 5
block_queue = queue.Queue()
block_queue.put(Block(index=0, data="Benchmark Data 0", prev_hash="0", difficulty=5))
block_queue.put(Block(index=1, data="Benchmark Data 1", prev_hash="0", difficulty=5))
block_queue.put(Block(index=2, data="Benchmark Data 2", prev_hash="0", difficulty=5))
block_queue.put(Block(index=3, data="Benchmark Data 3", prev_hash="0", difficulty=5))
block_queue.put(Block(index=4, data="Benchmark Data 4", prev_hash="0", difficulty=5))
block_queue.put(Block(index=5, data="Benchmark Data 5", prev_hash="0", difficulty=5))
block_queue.put(Block(index=6, data="Benchmark Data 6", prev_hash="0", difficulty=5))
block_queue.put(Block(index=7, data="Benchmark Data 7", prev_hash="0", difficulty=5))
block_queue.put(Block(index=8, data="Benchmark Data 8", prev_hash="0", difficulty=5))
block_queue.put(Block(index=9, data="Benchmark Data 9", prev_hash="0", difficulty=5))
block_queue.put(Block(index=10, data="Benchmark Data 10", prev_hash="0", difficulty=5))
block_queue.put(Block(index=11, data="Benchmark Data 11", prev_hash="0", difficulty=5))
block_queue.put(Block(index=12, data="Benchmark Data 12", prev_hash="0", difficulty=5))
block_queue.put(Block(index=13, data="Benchmark Data 13", prev_hash="0", difficulty=5))
block_queue.put(Block(index=14, data="Benchmark Data 14", prev_hash="0", difficulty=5))
block_queue.put(Block(index=15, data="Benchmark Data 15", prev_hash="0", difficulty=5))
block_queue.put(Block(index=16, data="Benchmark Data 16", prev_hash="0", difficulty=5))
block_queue.put(Block(index=17, data="Benchmark Data 17", prev_hash="0", difficulty=5))
block_queue.put(Block(index=18, data="Benchmark Data 18", prev_hash="0", difficulty=5))
block_queue.put(Block(index=19, data="Benchmark Data 19", prev_hash="0", difficulty=5))
block_queue.put(Block(index=20, data="Benchmark Data 20", prev_hash="0", difficulty=5))
block_queue.put(Block(index=21, data="Benchmark Data 21", prev_hash="0", difficulty=5))
block_queue.put(Block(index=22, data="Benchmark Data 22", prev_hash="0", difficulty=5))
block_queue.put(Block(index=23, data="Benchmark Data 23", prev_hash="0", difficulty=5))
block_queue.put(Block(index=24, data="Benchmark Data 24", prev_hash="0", difficulty=5))
block_queue.put(Block(index=25, data="Benchmark Data 25", prev_hash="0", difficulty=5))
block_queue.put(Block(index=26, data="Benchmark Data 26", prev_hash="0", difficulty=5))
block_queue.put(Block(index=27, data="Benchmark Data 27", prev_hash="0", difficulty=5))
block_queue.put(Block(index=28, data="Benchmark Data 28", prev_hash="0", difficulty=5))
block_queue.put(Block(index=29, data="Benchmark Data 29", prev_hash="0", difficulty=5))
block_queue.put(Block(index=30, data="Benchmark Data 30", prev_hash="0", difficulty=5))

blockchain = Blockchain(difficulty)



def main(threads):
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup_logger(rank)
    stop_event = Event()

    ADJUSTMENT_INTERVAL = 5  # Adjust difficulty every 5 blocks
    BLOCK_CREATION_TIME = 10

    log_filename = f"process_{rank}.log"

    with open(log_filename, "w") as log_file:
        os.dup2(log_file.fileno(), 1)  # Redirect stdout to the log file
        logger.info(vars(blockchain.get_last_block()))
        logger.info("thread")
        logger.info(threads)    
        start_time = time.time()

        while True:
            if rank == 0:
                if block_queue.empty():
                    logger.info("Data queue is empty")
                    time.sleep(1)
                    elapsed_time = time.time() - start_time
                    logger.info(f"Time elapsed since start: {elapsed_time:.2f} seconds")
                else:

                    last_block = blockchain.get_last_block()
                    new_block = block_queue.get() 
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

                    # if mined_block:
                    #     blockchain.add_block(block=mined_block, logger=logger)
                    #     logger.info("Block added to blockchain")
                    #     logger.info(len(blockchain.chain))
                    #     if blockchain.validate_chain():
                    #         logger.info("Blockchain valid")
                   
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
