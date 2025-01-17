import hashlib
import time
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