package client;

import java.net.*;
import java.io.*;
import rmi.*;

public class EchoObjectStub implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  private int port=7;
  private String output = "Error";
  private boolean connected  = false;

  public void setHostAndPort(String host, int port) {
    this.host= host; this.port =port;
  }

  public String echo(String input)throws java.rmi.RemoteException {
    connect();
    if (echoSocket != null && os != null && is != null) {
  	try {
             os.println(input);
             os.flush();
             output= is.readLine();
      } catch (IOException e) {
        System.err.println("I/O failed in reading/writing socket");
      }
    }
    disconnect();
    return output;
  }

  private synchronized void connect() throws java.rmi.RemoteException {
	//EJERCICIO: Implemente el m�todo connect 
	  if (!connected) {
		  try {
			  echoSocket = new Socket(host,port);
			  
			  os = new PrintWriter(echoSocket.getOutputStream(), true);
			  is = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			  
			  connected = true;
		  } catch (IOException e) {
			  System.err.println("Error al conectar al servidor: " + e.getMessage());
		  }
	  }
	  
  }

  private synchronized void disconnect(){ 
	//EJERCICIO: Implemente el m�todo disconnect 
	  if(connected) {
		  try {
			  if (os != null) os.close();
			  if (is != null) is.close();
			  if (echoSocket != null) echoSocket.close();
			  
			  connected = false;
		  } catch (IOException e) {
			  System.err.println("Error al desconectar del servidor: " + e.getMessage());
		  }
	  }
  }
}
