import threading
import concurrent.futures

def mine_block(block, start_nonce, end_nonce, stop_event, comm, logger, num_threads=2):
    """Mine a block within the given nonce range, listening for a stop signal."""
    
    def listen_for_stop():
        """Listen for the stop signal and set the event."""
        signal = comm.bcast(None, root=0)  # Receive broadcast
        if signal == "stop":
            logger.info("Mining stopped")
            stop_event.set()

    # Create a thread to listen for the stop signal
    listener_thread = threading.Thread(target=listen_for_stop, daemon=True)
    listener_thread.start()
    
    def mine_range(start, end):
        mine_range_block= block.copy()
        logger.info("block copyid in thread")
        logger.info(vars(mine_range_block))
        """Mine a block within the given nonce range."""
        for nonce in range(start, end):
            if stop_event.is_set():
                return None
            
            mine_range_block.nonce = nonce
            mine_range_block.hash = mine_range_block.calculate_hash()
            if mine_range_block.is_valid():
                stop_event.set()  # Notify others that mining is done
                logger.info("Block mined")
                logger.info(vars(mine_range_block)) 
                return mine_range_block
        return None

    # Divide the nonce range among the threads
    nonce_range = end_nonce - start_nonce
    range_per_thread = nonce_range // num_threads

    with concurrent.futures.ThreadPoolExecutor(max_workers=num_threads) as executor:
        futures = []
        for i in range(num_threads):
            thread_start_nonce = start_nonce + i * range_per_thread
            thread_end_nonce = start_nonce + (i + 1) * range_per_thread if i != num_threads - 1 else end_nonce
            futures.append(executor.submit(mine_range, thread_start_nonce, thread_end_nonce))

        for future in concurrent.futures.as_completed(futures):
            result = future.result()
            if result is not None and result.is_valid():
                logger.info("Block mined:")
                logger.info(vars(result))
                return result
            if result is not None:
                logger.info("Block not valid:")
                logger.info(vars(result))
            
    logger.info("Exiting mining")
    return None