/**
 * CifradorClient.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package client;

import sdi.cifradorIce.CifradorPrx;


class CifradorClient {
	
	
	public CifradorClient(String[] args) {
		
	}
	
    public static void main(String[] args)
    {
    	// Get the initialized property set.
    	com.zeroc.Ice.Properties props = com.zeroc.Ice.Util.createProperties(args);
    	// Set props
//    	PROXY INDIRECTO
    	props.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 12000");
    
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
//        	!!!!!!! PROXY DIRECTO
//            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("Cifrador:default -h localhost -p 10000");
//        	PROXY INDIRECTO
            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("Cifrador@CifradorAdapter");
            //
            // Show proxy as string
            String ior = ic.proxyToString(base);
            System.out.println("iceStrIOR (en cliente): " + ior);
            //
            // Narrow proxy.
            CifradorPrx cif = CifradorPrx.checkedCast(base);
            if(cif == null)
            {
                throw new Error("Invalid proxy");
            }
            //Use the remote object.
			String msgCifrado = cif.cifra("ABCDEFGHIJK", "1234");
			String msgOrigina = cif.descifra(msgCifrado, "1234");
			//
			System.out.println("Mensaje cifrado: " + msgCifrado + "\nMensaje descrifrado: " + msgOrigina);
        } 
    }
}
