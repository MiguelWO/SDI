package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import sdi.echoIce.EchoPrx;

public class ClientEcho {
	
	public ClientEcho(String[] args) {
			
	}
	
    public static void main(String[] args)
    {
    	boolean isProxyDirect = false;
    	if(args.length > 0 && args[0].equalsIgnoreCase("0")) {
			isProxyDirect = true;
		}
    	// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	
    	if (!isProxyDirect) {
        	// Set props
    		props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
    	}
    	    
    	// Initialize a communicator with these properties.
    	com.zeroc.Ice.InitializationData initData = new com.zeroc.Ice.InitializationData();
    	initData.properties = props;
        //
        // Try with resources block.
    	// Communicator "ic" is automatically destroyed
        // at the end of this try block
    	// try (com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(args)) 
        //
        try (com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(initData)) 
        {
            //
            // Install shutdown hook to (also) destroy communicator during JVM shutdown.
            // This ensures the communicator gets destroyed when the user interrupts the application with Ctrl-C.
            Runtime.getRuntime().addShutdownHook(new Thread(() -> ic.destroy()));
            //
            
            com.zeroc.Ice.ObjectPrx base;
            if(isProxyDirect) {
            	base = ic.stringToProxy("Echo:default -h localhost -p 10000");
            } else {
            	base = ic.stringToProxy("Echo@EchoAdapter");
            }

            // Show proxy as string
            String ior = ic.proxyToString(base);
            String tipo = isProxyDirect ? "Directo" : "Indirecto";
            System.out.println("iceStrIOR (en cliente): "+ tipo + ior);
            //
            // Narrow proxy.
            EchoPrx echo = EchoPrx.checkedCast(base);
            if(echo == null)
            {
                throw new Error("Invalid proxy");
            }
            
            BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
            PrintWriter stdOut = new PrintWriter(System.out);      
            String input,output;
            try {
            	while(true) {
            		System.out.println("Leer el teclado");
            		input = stdIn.readLine();
            		
            		if(input == null || input.equalsIgnoreCase("exit")) break;
            		
            		output = echo.serviceEcho(input);
            		
            		stdOut.println("Server Response:"+ output);
            		stdOut.flush();
            		
//            		System.out.println("Server Response" + output);
//            		System.out.flush();
            	}
            } catch (UnknownHostException e) {
              System.err.println("Don't know about host: "+ args[0]);
            } catch (IOException e) {
              System.err.println("I/O failed for connection to host: "+args[0]);
            }
        } 
    }

}
