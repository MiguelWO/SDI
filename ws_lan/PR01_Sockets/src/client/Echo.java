package client;

import java.io.*;
import java.net.*;
import java.io.FileWriter;

public class Echo {
  private static EchoObjectStub ss;

  public static void main(String[] args) {

    if (args.length<2) {
        System.out.println("Usage: Echo <host> <port#>");
        System.exit(1);
    }
    ss = new EchoObjectStub();
    ss.setHostAndPort(args[0],Integer.parseInt(args[1]));
    
//    Socket echoSocket = null;
//    OutputStream os = null;
//    InputStream is = null;

    BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
    
    
    String input,output;
    try {
    	//EJERCICIO: el bucle infinito: 
    	//EJERCICIO: Leer de teclado //EJERCICIO: Invocar el stub 
    	//EJERCICIO: Imprimir por pantalla s
    	
    	while(true) {
    		System.out.println("Leer el teclado");
    		input = stdIn.readLine();
    		
    		if(input == null || input.equalsIgnoreCase("exit")) break;
    		
    		output = ss.echo(input);
    		
//    		stdOut.println("Server Response:"+ output);
//    		stdOut.flush();
    		
    		System.out.println("Server Response" + output);
    		System.out.flush();
    	}
    	
//    	echoSocket = new Socket(args[0], Integer.parseInt(args[1]));
//    	os = echoSocket.getOutputStream();
//    	is = echoSocket.getInputStream();
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: "+ args[0]);
    } catch (IOException e) {
      System.err.println("I/O failed for connection to host: "+args[0]);
    }
    
//    if(echoSocket != null && os != null && is != null) {
//    	try {
//    		byte[] bufferin = new byte[256];
//    		byte[] bufferout = new byte[256];
//    		int count = 0;
//    		String salida;
//    		
//    		while((count == System.in.read(bufferin))) {
//    			os.write(bufferin,0,count);
//    			count = is.read(bufferout);
//    			salida = new String(bufferout,0,count);
//    			System.out.println("echo: " +  salida);
//    			if(salida.equals("Bye"+" \r\n")) break;
//    			
//    			os.close();
//    			is.close();
//    			echoSocket.close();
//    		}
//    	} catch (IOException e) {
//    		System.err.println("I/O failed on connection to " + args[0]);
//    	}
//    }
    
  }
}