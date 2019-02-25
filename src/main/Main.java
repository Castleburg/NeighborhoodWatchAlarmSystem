package main;

import main.Controller;

public class Main {
	//Starts the program
	public static void main(String[] args){
		//Create controller and start server hosting
		Controller controller = new Controller(6060);
		try {
			controller.startServer();
		} catch (InterruptedException e) {
			
		}
	}
	
}
