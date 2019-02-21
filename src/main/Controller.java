package main;

import tcp.*;

public class Controller {
	
	private int boardPort;
	
	public Controller(int boardPort){
		this.boardPort = boardPort;
	}
	public void startServer() throws InterruptedException{
		ConnectionHost host = new ConnectionHost(boardPort);
		host.run();
		
	}
}
