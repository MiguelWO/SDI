package server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.echo.*;
/**************************+
 * Args parameters 
 * 0 for direct 
 * 1 for indirect
 */
public class ServerEcho {
	/**
	 * 
	 */
	public static void main(String[] args) {
		boolean isProxyDirect = false;
		
		if(args.length > 0 && args[0].equalsIgnoreCase("0")) {
			isProxyDirect = true;
		}
		
		// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	
    	 if (!isProxyDirect) {
    		// Set props
        	props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
        	props.setProperty("EchoAdapter.Endpoints", "tcp");
        	props.setProperty("EchoAdapter.AdapterId", "EchoAdapter");
    	}
    	
    	//
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
        	com.zeroc.Ice.ObjectAdapter adapter;
        	
        	if (isProxyDirect) {
        		//Create adapter.
            	adapter = ic.createObjectAdapterWithEndpoints("EchoAdapter", "default -h localhost -p 10000");
        	} else {
        		//Create adapter.
        		adapter = ic.createObjectAdapter("EchoAdapter");
        	}
        	
        	// Create servant and register it into the adapter.
            com.zeroc.Ice.Object iceObject = new EchoInt();
            adapter.add(iceObject, com.zeroc.Ice.Util.stringToIdentity("Echo"));            
            //
            
            
        	//Generate a proxy to get its string form.
        	com.zeroc.Ice.ObjectPrx proxy = adapter.createDirectProxy(com.zeroc.Ice.Util.stringToIdentity("Echo"));
            String s1 = ic.proxyToString(proxy);
            String tipo = isProxyDirect ? "Directo" : "Indirecto";
            System.out.println("IOR (en servidor): " + tipo + s1);
           
            
            //
            // Activate adapter
            adapter.activate();
            // Block main thread.
            ic.waitForShutdown();
        }
	}

}