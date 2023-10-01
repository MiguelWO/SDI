/*
 *  TestServer.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Usage of example message format.
 *  Rev: 2017
 */
package src02.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import src02.message.Message;
import src02.util.Bin;
import src02.util.Net;


public class TestServer {

	public static void main(String[] args) {
		//
		System.out.println("Float processing server.");
		//
		int port = 8000;
		//
		ServerSocket servSock = null;
		Socket s = null;
		//
		try {
			servSock = new ServerSocket(port);
			s = servSock.accept();
			System.out.println("Connection established.");	
		} catch (IOException e) {
			System.err.println("Connection error.");
			return;
		}
		//
		for (;;) {
			try {
				
				Message mIn = new Message();
				mIn.readFromInputStream(s.getInputStream());
				float f = Bin.dWordToFloat(mIn.payload);
				//
				System.out.println("Server processing float number: " + f);
				f = f * 2;
				//
				byte[] buffer = Bin.floatToDWord(f);
				Message mOut = new Message(buffer);
				mOut.writeToOutputStream(s.getOutputStream());
			} catch (IOException e) {
				System.err.println("Communication error.");
				break;
			}
		}
		try {
			servSock.close();
		} catch (IOException e) {
			System.err.println("Clossing socket error.");
		}
	}
}
