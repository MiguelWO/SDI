package client;

import java.net.*;
import java.io.*;
import rmi.EchoInt;
import java.util.Timer;
import java.util.TimerTask;

public class EchoObjectStub2 implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  private int port=7;
  private String output = "Error";
  private boolean connected  = false;
  Timeout tout=null;


  public void setHostAndPort(String host, int port) {
    this.host= host; this.port =port;
    tout = new Timeout(10,this);
  }

  public String echo(String input) throws java.rmi.RemoteException {
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
    programDisconnection();
    return output;
  }

  private synchronized void connect() throws java.rmi.RemoteException {
	//EJERCICIO: lo mismo que en EchoObjectStub 
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
	//EJERCICIO: lo mismo que en EchoObjectStub 
	  if(connected) {
		  try {
			  if (os != null) os.close();
//			  System.out.println("Os");
			  if (is != null) is.close();
//			  System.out.println("Is");
			  if (echoSocket != null) echoSocket.close();
//			  System.out.println("Socket");
			  
			  connected = false;
		  } catch (IOException e) {
			  System.err.println("Error al desconectar del servidor: " + e.getMessage());
		  }
	  }
  }

  private synchronized void programDisconnection(){
    tout.start();
  }

  class Timeout {
     Timer timer;
     EchoObjectStub2 stub;
     int seconds;

     public Timeout (int seconds, EchoObjectStub2 stub) {
       this.seconds = seconds;
       this.stub = stub;
     }

     public void start() {
    	//EJERCICIO 
//    	 timer = new Timer();
//    	 timer.schedule(new TimeoutTask(stub), seconds*1000);
    	 
    	 if (timer != null) {
    		 timer.cancel();
    	 }
    	 timer = new Timer();
    	 timer.schedule(new TimeoutTask(), seconds*1000);
     }

     public void cancel() {
    	//EJERCICIO
    	 System.out.println("Canceling Timer");
    	 if (timer != null)
    		 timer.cancel();
     }

     class TimeoutTask extends TimerTask {
//    	 EchoObjectStub2 stub;
//    	 
//    	 public TimeoutTask(EchoObjectStub2 stub) {
//    		 this.stub = stub;
//    	 }

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("Firing timer handler");
			System.out.println(connected);
			stub.disconnect();
		}
    	//EJERCICIO 
     }

   }
}




