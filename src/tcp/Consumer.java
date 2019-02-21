package tcp;

public class Consumer extends Thread {
	private String inbound;
	private ConnectionHost connection;
	private String name;
	
	/**
	 * Creates the consumer thread. It will consume messages from the queue of its given connection.
	 * @param name
	 * @param connection
	 * @param controller
	 */
	public Consumer(String name, ConnectionHost connection){
		this.connection = connection;
		this.name = name;
	}
	public void run(){
		while(true){
			//To spend less cpu than it needs to.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("A Thread named: " + name + " has died!");
			}
			//Take from queue and check if it contains information
			inbound = connection.takeFromQueue();
			if(inbound != ""){
				connection.putToQueue(inbound);
			}
		}
	}
}
