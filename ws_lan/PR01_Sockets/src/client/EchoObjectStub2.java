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
  }

  private synchronized void disconnect(){
	//EJERCICIO: lo mismo que en EchoObjectStub 
  }

  private synchronized void programDisconnection(){
    tout.start();
  }

  class Timeout {
     Timer timer;
     EchoObjectStub4 stub;
     int seconds;

     public Timeout (int seconds, EchoObjectStub4 stub) {
       this.seconds = seconds;
       this.stub = stub;
     }

     public void start() {
    	//EJERCICIO 
     }

     public void cancel() {
    	//EJERCICIO 
     }

     class TimeoutTask extends TimerTask {
    	//EJERCICIO 
     }

   }
}




