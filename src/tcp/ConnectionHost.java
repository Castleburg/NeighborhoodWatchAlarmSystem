package tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import main.Controller;

public class ConnectionHost extends Thread {
	String transmitterName;
	int portNumber;
	Thread transmitter;
	String inbound;
	
	Controller controller;
	
	ArrayBlockingQueue<String> inboundQueue = new ArrayBlockingQueue<String>(20);
	ArrayBlockingQueue<String> outbound = new ArrayBlockingQueue<String>(20);
	
	public ConnectionHost(int portNumber)
	{
		this.portNumber = portNumber;
	}
	
	public void run(){
		ServerSocket welcomeSocket;
		Socket sock;
		while(true) {
			try{
				welcomeSocket = new ServerSocket(portNumber);
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress("amazonaws.com", 80));
				System.out.println("Address: "+socket.getLocalAddress()+"/nPort: "+portNumber);
				socket.close();
				sock = welcomeSocket.accept();
				System.out.println("Connection Accepted");
				
				BufferedReader buffRead = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				OutputStream out = sock.getOutputStream();
				OutputStreamWriter outW = new OutputStreamWriter(out);
				BufferedWriter outBW = new BufferedWriter(outW);
				
				//The send/receive loop.
				while(sock.isConnected()) {
					if (!outbound.isEmpty()) {
						try {
							String a = outbound.take();
							outBW.write(a);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						outBW.flush();
					}
					if (buffRead.ready()) {
						try {
							inboundQueue.put(buffRead.readLine());
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				welcomeSocket.close();
			} catch(IOException e){
				System.out.println(e);
			}
		}
	}
	/**
	 * Puts data into queue. Everything in this queue is sent to the connected host.
	 * @param toQueue
	 */
	public void putToQueue(String toQueue)
	{
		try {
			outbound.put(toQueue + "\r\n");
		} catch (InterruptedException e) {
			
		}
	}
	/**
	 * Takes received data from queue.
	 * @return String
	 */
	public String takeFromQueue(){
		String get = "";
		try {
			if (!inboundQueue.isEmpty()) {
				get = inboundQueue.take();
			}
			} catch (InterruptedException e) {
				
				get = "Failed to get data from queue.";
			}
		return get;
	}
}