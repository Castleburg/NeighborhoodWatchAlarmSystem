package tcp;

public class Consumer extends Thread {
	private String inbound;
	private ConnectionHost connection;
	
	/**
	 * Creates the consumer thread. It will consume messages from the queue of its given connection.
	 * @param name
	 * @param connection
	 * @param controller
	 */
	public Consumer(ConnectionHost connection){
		this.connection = connection;
	}
	public void run(){
		while(true){
			//To spend less cpu than it needs to.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			//Take from queue and check if it contains information
			inbound = connection.takeFromQueue();
			if(inbound != ""){
				connection.putToQueue(inbound);
			}
		}
	}
}
