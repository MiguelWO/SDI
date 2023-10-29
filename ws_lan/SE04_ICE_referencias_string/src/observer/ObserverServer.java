/*
 *  ObserverServer.java
 *
 *  @author Jose Simo. 
 *  (c) ai2-DISCA-UPV Creative Commons.
 *  Rev: 2022
 */

package observer;

import sdi.observerIce.TriggerPrx;

//
public class ObserverServer {
	
	public static void main(String[] args) {
    	// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	// Set props
    	props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
    	//
    	// Initialize a communicator with these properties.
    	com.zeroc.Ice.InitializationData initData = new com.zeroc.Ice.InitializationData();
    	initData.properties = props;
        //
        // Try with resources block.
    	// Communicator "ic" is automatically destroyed at the end of this try block
        //
        try (com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(initData)) 
        {
            //
            // Install shutdown hook to (also) destroy communicator during JVM shutdown.
            Runtime.getRuntime().addShutdownHook(new Thread(() -> ic.destroy()));
            //
            ///////////////////////////////////////////////////////////////////////////////
        	// Create Observer ICE object.
        	com.zeroc.Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("ObserverAdapter","tcp");
        	// Create servant and register it into the adapter.
            com.zeroc.Ice.Object iceObject = new ObserverI();
            adapter.add(iceObject, com.zeroc.Ice.Util.stringToIdentity("Observer"));   
            // Activate adapter
            adapter.activate();
            //
            //Generate a proxy to get its string form.
            com.zeroc.Ice.ObjectPrx proxy = adapter.createDirectProxy(com.zeroc.Ice.Util.stringToIdentity("Observer"));
            String observerIOR = ic.proxyToString(proxy);
            System.out.println("IOR (observer): " + observerIOR);
            //
            ///////////////////////////////////////////////////////////////////////////////
            // Get trigger proxy
            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("Trigger@TriggerAdapter");
            // Narrow proxy.
            TriggerPrx triObj = TriggerPrx.checkedCast(base);
            if (triObj == null) {
                throw new Error("Invalid proxy");
            }
            ///////////////////////////////////////////////////////////////////////////////
            // Register observer into the trigger
            //
            System.out.println("Going to register listener. ");
            String res= triObj.addListener(observerIOR);
            System.out.println("Observer registered into the Trigger with ID: " + res);
            //
            // Block main thread.
            ic.waitForShutdown();
        } 
		
	}
}
