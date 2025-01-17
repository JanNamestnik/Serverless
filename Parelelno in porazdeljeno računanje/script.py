from mpi4py import MPI
import hashlib
import time
import os
import logging
import threading
from threading import Event 
from block import Block, Blockchain
from mining import mine_block

def setup_logger(rank):
    log_filename = f"process_{rank}.log"
    logging.basicConfig(
        filename=log_filename, level=logging.INFO, format="%(asctime)s - %(message)s"
    )
    return logging.getLogger()


def main():
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup_logger(rank)
    stop_event = Event()
    logger.info(f"Rank {rank} started")
    difficulty = 6
    blockchain = Blockchain(difficulty, logger)

    log_filename = f"process_{rank}.log"

    # Rank 0 is the master
    with open(log_filename, "w") as log_file:
        os.dup2(log_file.fileno(), 1)  # Redirect stdout to the log file
        logger.info(f"Rank {rank} started")
        logger.info(vars(blockchain.get_last_block()))
        while True:
            if rank == 0:
                last_block = blockchain.get_last_block()
                new_block = Block(
                    len(blockchain.chain),
                    "New Block Data",
                    last_block.hash,
                    difficulty,
                )
                logger.info("Block sent to workers:")
                logger.info(vars(new_block))
                
                # Send new block to workers
                for i in range(1, size):
                    comm.send(new_block, dest=i)

                mined_block = None
                received_workers = set()

                # Listen to all workers simultaneously
                worker_block = comm.recv(source=MPI.ANY_SOURCE)
                received_workers.add(worker_block)
                if worker_block is not None:
                    mined_block = worker_block
                    comm.bcast("stop", root=0)
                    logger.info("Mined block found:", vars(mined_block))
                    break

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

                        # If it's a new block, start mining
                        if isinstance(message, Block):
                            new_block = message
                            logger.info("Received new block:")
                            logger.info(vars(new_block))

                            start_nonce = rank * (2**32 // size)
                            end_nonce = (rank + 1) * (2**32 // size)

                            mined_block = mine_block(new_block, start_nonce, end_nonce,stop_event, comm, logger)
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


if __name__ == "__main__":
    main()
