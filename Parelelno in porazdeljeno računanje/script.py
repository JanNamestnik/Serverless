from mpi4py import MPI
import hashlib
import time
import os
import logging


class Block:
    def __init__(self, index, data, prev_hash, difficulty):
        self.index = index
        self.data = data
        self.timestamp = time.time()
        self.prev_hash = prev_hash
        self.difficulty = difficulty
        self.nonce = 0
        self.hash = self.calculate_hash()

    def calculate_hash(self):
        hash_data = f"{self.index}{self.data}{self.timestamp}{self.prev_hash}{self.difficulty}{self.nonce}"
        return hashlib.sha256(hash_data.encode()).hexdigest()

    def is_valid(self):
        return self.hash.startswith("0" * self.difficulty)


def setup_logger(rank):
    log_filename = f"process_{rank}.log"
    logging.basicConfig(
        filename=log_filename, level=logging.INFO, format="%(asctime)s - %(message)s"
    )
    return logging.getLogger()


class Blockchain:
    def __init__(self, difficulty, logger):
        self.chain = [self.create_genesis_block(difficulty,logger)]
        self.difficulty = difficulty

    def create_genesis_block(self, difficulty,logger):
        block = Block(0, "Genesis Block", "0", difficulty)
        logger.info("Genesis block created")
        logger.info(vars(block))
        return block

    def get_last_block(self):
        return self.chain[-1]

    def add_block(self, block, logger):
        logger.info("Adding block to blockchain")
        logger.info(vars(block))
        logger.info("Validating block")
        logger.info(self.validate_block(block ,logger))
        if self.validate_block(block, logger) :
            self.chain.append(block)

    def validate_block(self, block ,logger):
        last_block = self.get_last_block()
        logger.info("Validating block  to last block in blockchain")
        logger.info(vars(last_block))
        if block.index != last_block.index + 1:
            logger.info("Invalid block index")
            logger.info(block.index) 
            logger.info(last_block.index)
            logger.info(vars(last_block))
            return False
        if block.prev_hash != last_block.hash:
            logger.info("Previous hash does not match")
            logger.info(block.prev_hash)
            logger.info(last_block.hash)
            return False
        if not block.is_valid():
            logger.info("Block hash is invalid")
            return False
        return True

    def validate_chain(self):
        for i in range(1, len(self.chain)):
            current_block = self.chain[i]
            previous_block = self.chain[i - 1]
            if current_block.prev_hash != previous_block.hash:
                print(f"Block {i} has an invalid previous hash")
                return False
            if not current_block.is_valid():
                print(f"Block {i} has an invalid hash")
                return False
            if current_block.index != previous_block.index + 1:
                print(f"Block {i} has an invalid index")
                return False
        return True


def mine_block(block, start_nonce, end_nonce, comm,logger, rank):
    """Mine a block within the given nonce range, checking for a stop signal."""
    for nonce in range(start_nonce, end_nonce):
        # Periodically check for a stop signal
        if comm.Iprobe(source=0):
            message = comm.recv(source=0)
            if message == "stop":
                logger.info(f"{rank} Stop signal received. Stopping mining.")
                return None  # Stop mining and return

        block.nonce = nonce
        block.hash = block.calculate_hash()
        if block.is_valid():
            return block
    return None


def main():
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup_logger(rank)

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
                for i in range(1, size):
                    worker_block = comm.recv(source=i)
                    if worker_block is not None:
                        mined_block = worker_block
                        logger.info("Mined block found:", vars(mined_block))
                        break

                # Broadcast stop signal to all workers
                if(mined_block):
                    comm.bcast("stop", root=0)
                    logger.info("Stop signal sent to workers")
                else:
                    comm.bcast("continue", root=0)
                

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

                            mined_block = mine_block(new_block, start_nonce, end_nonce,comm,logger,rank)
                            comm.send(mined_block, dest=0)

                            if mined_block:
                                logger.info("Mined block found:")
                                logger.info(vars(mined_block))

                        # If a stop signal is received, break and await new instructions
                        elif message == "stop":
                            logger.info("Stop signal received. Waiting for new block.")
                            break

                    # Perform other tasks or idle
                    # Optional: Add a small sleep to reduce CPU usage during idle
                    logger.info("Idle")
                    time.sleep(0.1)




if __name__ == "__main__":
    main()
