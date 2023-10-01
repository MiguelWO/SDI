package src01;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class DemoCliente {

	public static void main(String[] args) {

		System.out.println("Cliente de rebotes.");
		
		Socket s;
		String host = "localhost";
		int port = 8000;
		OutputStream os = null;
		InputStream  is;

		try {
			s = new Socket(host, port);
			is = s.getInputStream();
			os = s.getOutputStream();
			System.out.println("Conexion establecida.");
			
		} catch (IOException e) {
			System.err.println("Error de conexion.");
			return;
		}

		BufferedReader bis = new BufferedReader(new InputStreamReader(is));
		PrintWriter bos = new PrintWriter(os);

		String inputLine = "";
		for (int i=0;i<3;i++){
			try {
				bos.println(inputLine+"_C"+i);
				bos.flush();
				inputLine = bis.readLine();
				
			} catch (IOException e) {
				System.err.println("Error de comunicaci�n.");
			}
		}
		
		System.out.println(inputLine);
		try {
			s.close();
		} catch (IOException e) {
			System.err.println("Error de comunicaci�n.");
		}
	}
}
