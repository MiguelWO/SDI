/**
 * CifradorServer.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package server;


public class CifradorServer {

	public static void main(String[] args) {
    	// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	// Set props
//    	PROXY INDIRECTO
    	props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
    	props.setProperty("CifradorAdapter.Endpoints", "tcp");
    	props.setProperty("CifradorAdapter.AdapterId", "CifradorAdapter");
    	
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
        	//Create adapter.
//        	!!!!!!! PROXY DIRECTO
//        	com.zeroc.Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("CifradorAdapter", "default -h localhost -p 10000");
//        	PROXY INDIRECTO
        	com.zeroc.Ice.ObjectAdapter adapter = ic.createObjectAdapter("CifradorAdapter");
        	//
        	// Create servant and register it into the adapter.
            com.zeroc.Ice.Object iceObject = new CifradorI();
            adapter.add(iceObject, com.zeroc.Ice.Util.stringToIdentity("Cifrador"));            
            //
            //Generate a proxy to get its string form.
            com.zeroc.Ice.ObjectPrx proxy = adapter.createDirectProxy(com.zeroc.Ice.Util.stringToIdentity("Cifrador"));
            String s1 = ic.proxyToString(proxy);
            System.out.println("IOR (en servidor): " + s1);
            //
            // Activate adapter
            adapter.activate();
            // Block main thread.
            ic.waitForShutdown();
        }
		
	}
	
}
