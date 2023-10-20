package client;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import echo.EchoInt;

public class EchoRMI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length<1){
			System.out.println("Uso echo <host>");System.exit(1);
		}
		if(System.getSecurityManager()== null) {
			System.setSecurityManager(new SecurityManager());
		}
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter stdOut = new PrintWriter(System.out);

        String input,output;
        try{
        	//EJERCICIO: "lookup" the Echo RMI object
        	String host = args[0];
        	Registry reg = LocateRegistry.getRegistry(host);
        	
        	EchoInt echo = (EchoInt) reg.lookup("echo");
        	
        	

          stdOut.print("> "); stdOut.flush();
          while ( (input = stdIn.readLine())!=null){
        	  
        	  //EJERCICIO: call echo RMI object 
        	  output = echo.echo(input);
        	  
              stdOut.println(output);
              stdOut.print("> "); stdOut.flush();
          }
        }catch(Exception e){
              System.out.println("RMI Echo Client error: " + e.getMessage());
        }
	}

}
