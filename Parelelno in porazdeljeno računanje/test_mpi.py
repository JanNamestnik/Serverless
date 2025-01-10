from mpi4py import MPI
import hashlib
import time
import os


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


class Blockchain:
    def __init__(self, difficulty):
        self.chain = [self.create_genesis_block(difficulty)]
        self.difficulty = difficulty

    def create_genesis_block(self, difficulty):
        return Block(0, "Genesis Block", "0", difficulty)

    def get_last_block(self):
        return self.chain[-1]

    def add_block(self, block):
        if self.validate_block(block):
            self.chain.append(block)

    def validate_block(self, block):
        last_block = self.get_last_block()
        if block.index != last_block.index + 1:
            print("Invalid block index", block.index, last_block.index)
            print(vars(last_block))
            return False
        if block.prev_hash != last_block.hash:
            print("Previous hash does not match")
            return False
        if not block.is_valid():
            print("Block hash is invalid")
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


def mine_block(block, start_nonce, end_nonce):
    """Mine a block within the given nonce range."""
    for nonce in range(start_nonce, end_nonce):
        block.nonce = nonce
        block.hash = block.calculate_hash()
        if block.is_valid():
            return block
    return None


def main():
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()
    logger = setup
    difficulty = 6
    blockchain = Blockchain(difficulty)
    log_filename = f"process_{rank}.log"

    # Rank 0 is the master
    with open(log_filename, "w") as log_file:
        os.dup2(log_file.fileno(), 1)  # Redirect stdout to the log file
        print(f"Rank {rank} started")
        if rank == 0:
            while True:
                print("aaaaaaaaaaaaaa")
        #         last_block = blockchain.get_last_block()
        #         new_block = Block(
        #             len(blockchain.chain), "New Block Data", last_block.hash, difficulty
        #         )

        #         total_nonce_range = 2**32
        #         range_per_process = total_nonce_range // size

        #         # Distribute work to all processes
        #         for i in range(1, size):
        #             start_nonce = i * range_per_process
        #             end_nonce = (i + 1) * range_per_process
        #             comm.send((new_block, start_nonce, end_nonce), dest=i)

        #         # Master mines its range
        #         start_nonce = 0
        #         end_nonce = range_per_process
        #         mined_block = mine_block(new_block, start_nonce, end_nonce)

        #         # Collect results from workers
        #         for i in range(1, size):
        #             worker_block = comm.recv(source=i)
        #             if worker_block is not None:
        #                 mined_block = worker_block
        #                 break

        #         if mined_block:
        #             print("Mined block found:", vars(mined_block))
        #             blockchain.add_block(mined_block)
        #             if blockchain.validate_chain():
        #                 print("Blockchain valid")
        #             else:
        #                 print("Blockchain invalid. Debugging...")
        #                 for i in range(1, len(blockchain.chain)):
        #                     if not blockchain.validate_block(blockchain.chain[i]):
        #                         print(
        #                             f"Issue with block at index {i}:",
        #                             vars(blockchain.chain[i]),
        #                         )
        #             for block in blockchain.chain:
        #                 print(vars(block))

        #             # Notify workers to start mining the next block
        #             for i in range(1, size):
        #                 comm.send("new_block", dest=i)

        #         else:
        #             print("No valid block found.")
        else:
            while True:
                logger.info("aaaaaaaaaaaaaa")
        #         # Workers receive tasks and mine
        #         message = comm.recv(source=0)
        #         if message == "new_block":
        #             last_block = blockchain.get_last_block()
        #             new_block = Block(
        #                 len(blockchain.chain),
        #                 "New Block Data",
        #                 last_block.hash,
        #                 difficulty,
        #             )

        #             start_nonce = rank * (2**32 // size)
        #             end_nonce = (rank + 1) * (2**32 // size)

        #             mined_block = mine_block(new_block, start_nonce, end_nonce)
        #             comm.send(mined_block, dest=0)
        #             break


if __name__ == "__main__":
    main()
