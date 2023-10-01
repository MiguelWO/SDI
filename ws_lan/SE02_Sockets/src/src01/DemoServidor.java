package src01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class DemoServidor {

	public static void main(String[] args) {

		System.out.println("Servidor de rebotes.");
		
		ServerSocket servSock = null;
		Socket s = null;
		int port = 8000;
		OutputStream os = null;
		InputStream  is;

		try {
			servSock = new ServerSocket(port);
			s = servSock.accept();
			is = s.getInputStream();
			os = s.getOutputStream();
			System.out.println("Conexion establecida.");

			
		} catch (IOException e) {
			System.err.println("Error de conexion.");
			return;
		}

		BufferedReader bis = new BufferedReader(new InputStreamReader(is));
		PrintWriter bos = new PrintWriter(os);

		int i=0;
		for (;;){
			String inputLine = "";
			try {
				inputLine = bis.readLine();
				if (inputLine == null) return;
				System.out.println("Serv: "+inputLine);
				bos.println(inputLine+"_S"+i);
				bos.flush();
			} catch (IOException e) {
				System.err.println("Error de comunicacion.");
				return;
			}
			i++;
		}
	}
}
