from block import Block
import threading
import time

def mine_block(block, start_nonce,  stop_event , step = 1, comm = None, logger = None):
    """Mine a block within the given nonce range."""
    mining_block = block.copy()
    nonce = start_nonce
    logger.info(f"Start nonce: {start_nonce}")
    logger.info(f"Step: {step}")
    while(not stop_event.is_set()):
        nonce += step
        mining_block.nonce = nonce
        mining_block.hash = mining_block.calculate_hash()
        if mining_block.is_valid():
            stop_event.set()
            logger.info("Block found")
            logger.info(vars(mining_block))
            return mining_block  
    return None



def parallel_mine_block_threading(block, start_nonce, num_threads , comm, logger, number_of_miners ,stop_event):
    def listen_for_stop():
        """Listen for the stop signal and set the event."""
        signal = comm.bcast(None, root=0)  # Receive broadcast
        if signal == "stop":
            stop_event.set()

    # Create a thread to listen for the stop signal
    listener_thread = threading.Thread(target=listen_for_stop, daemon=True)
    listener_thread.start()

    def mine_in_range(start, stop_event, result):
        mined_block = mine_block(block, start,stop_event,num_threads * number_of_miners , comm, logger)
        if mined_block:
            result.append(mined_block)

    threads = []
    result = []
    for i in range(num_threads):
        thread_start = start_nonce + i
        thread = threading.Thread(target=mine_in_range, args=(thread_start, stop_event,result))
        threads.append(thread)
        thread.start()
    logger.info("All threads started")
    for thread in threads:
        thread.join()
    logger.info("All threads finished")
    return result[0] if result else None

if __name__ == "__main__":
    starttime= time.time()
    block = Block(0, "Genesis Block", "0", 5)
    mined_block = parallel_mine_block_threading(block, 0, 2**32,2 )
    if mined_block:
        print(vars(mined_block))
    else:
        print("Block not found")
    print("Time taken: ", time.time()-starttime)