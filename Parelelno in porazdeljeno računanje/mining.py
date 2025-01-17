import threading


def mine_block(block, start_nonce, end_nonce, stop_event,comm, logger):
    """Mine a block within the given nonce range, listening for a stop signal."""
    
    def listen_for_stop():
        """Listen for the stop signal and set the event."""
        signal = comm.bcast(None, root=0)  # Receive broadcast
        if signal == "stop":
            stop_event.set()

    # Create a thread to listen for the stop signal
    listener_thread = threading.Thread(target=listen_for_stop, daemon=True)
    listener_thread.start()
    
    # Mining loop
    for nonce in range(start_nonce, end_nonce):
        if stop_event.is_set():
            print(f"Mining stopped at nonce {nonce}.")
            break
        
        block.nonce = nonce
        block.hash = block.calculate_hash()
        if block.is_valid():
            stop_event.set()  # Notify others that mining is done
            return block

    return None