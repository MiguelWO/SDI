/*
 *  ObserverRMI.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmi.TriggerInt;
import rmi.ObserverInt;

public class ObserverRMI implements ObserverInt {

	@Override
	public String eventCallback(String arg) throws RemoteException {
		System.out.println("ObserverRMI:eventCallback. " + arg);
		return null;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Starting observer object. The registry location can be passed as argument.");
		//
		String host = "localhost";
		if (args.length > 0) host = args[0];
		// Do not forget the set the java.security.policy VM property.
        //
        TriggerInt trig = null;
        String remoteTriggerObjectName = "myTriggerObject";
		try {
			System.out.println("Locating registry at "+ host + " port 1099");
			Registry reg = LocateRegistry.getRegistry(host,1099);
			//
			System.out.println("Lookingup " + remoteTriggerObjectName);
			trig = (TriggerInt) reg.lookup(remoteTriggerObjectName);
			// Create the remote object and the remote reference.
			ObserverRMI observerObject = new ObserverRMI();
			ObserverInt stub = (ObserverInt) UnicastRemoteObject.exportObject((Remote) observerObject,0);
			// Register the observer into the remote trigger object
			long t1 = System.nanoTime();
			String myID = trig.addListener(stub);
			long diffT = (System.nanoTime() - t1) / 1000;
			System.out.println("Remote call time: " + diffT + " microseconds." );
			//
			System.out.println("Observer object registered in " + remoteTriggerObjectName + " with ID: " + myID);
			System.out.println("Receiving callbacks during 20 seconds...");
			try {
				Thread.sleep(20000);
			} catch (Exception e) {}
			//
			// Unregister the observer from the remote trigger object
			trig.removeListener(myID);
			// Exit process
			System.exit(0); // can't just return, rmi threads may not exit
			//
		} catch (RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

