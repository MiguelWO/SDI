/*
 *  TriggerServer.java
 *
 *  @author Jose Simo. 
 *  (c) ai2-DISCA-UPV Creative Commons.
 *  Rev: 2022
 */

package trigger;

//
public class TriggerServer {

	public static void main(String[] args) {

		// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	// Set props
    	props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
    	props.setProperty("TriggerAdapter.Endpoints", "tcp");
    	props.setProperty("TriggerAdapter.AdapterId", "TriggerAdapter");
    	//
    	// Initialize a communicator with these properties.
    	com.zeroc.Ice.InitializationData initData = new com.zeroc.Ice.InitializationData();
    	initData.properties = props;
        //
        // Try with resources block.
    	// Communicator "ic" is automatically destroyed
        // at the end of this try blockpwd
    	// try (com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(args)) 
        //
        try (com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(initData)) 
        {
        	//Create adapter.
        	com.zeroc.Ice.ObjectAdapter adapter = ic.createObjectAdapter("TriggerAdapter");
        	//
        	// Create servant and register it into the adapter.
            com.zeroc.Ice.Object iceObject = new TriggerI(ic);
            adapter.add(iceObject, com.zeroc.Ice.Util.stringToIdentity("Trigger"));            
            //
            //Generate a proxy to get its string form.
            com.zeroc.Ice.ObjectPrx proxy = adapter.createDirectProxy(com.zeroc.Ice.Util.stringToIdentity("Trigger"));
            String s1 = ic.proxyToString(proxy);
            System.out.println("IOR del trigger (en servidor): " + s1);
            //
            // Activate adapter
            adapter.activate();
            // Block main thread.
            ic.waitForShutdown();
        }
	}
}
