package main;

import main.Controller;

public class Main {
	//Starts the program
	public static void main(String[] args){
		System.out.println("Hello from Main");
		//Create controller and start server hosting
		Controller controller = new Controller(6262);
		try {
			controller.startServer();
		} catch (InterruptedException e) {
			
		}
	}
	
}
