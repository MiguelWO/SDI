/*
 *  TriggerRMI.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmi.ObserverInt;
import rmi.TriggerInt;
import util.Net;

public class TriggerRMI implements TriggerInt{

	private Trigger trig = null;
	
	protected TriggerRMI()  {
		super();
		trig = new Trigger();
	}

	@Override
	public String addListener(ObserverInt observer) throws RemoteException {
		//Tunnel
		return trig.addListener(observer);
	}

	@Override
	public boolean removeListener(String observerId) throws RemoteException {
		//Tunnel
		return trig.removeListener(observerId);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 //
		 // Select the local network to use
		 String localIP = Net.chooseOperatingIPAddr();
		 System.out.println("The local IP address is " + localIP);
		 System.setProperty("java.rmi.server.hostname",localIP);
		 //
	     String remoteTriggerObjectName = "myTriggerObject";
	     //
	     try {
	    	 //Locate the local registry
	    	 Registry registry = LocateRegistry.getRegistry();
	    	 //Create  the Trigger object and register it into the registry
	         TriggerInt stub = (TriggerInt) UnicastRemoteObject.exportObject(new TriggerRMI(),0);
	         registry.rebind(remoteTriggerObjectName, stub);
	         //
	     } catch (RemoteException e) {
	    	 System.err.println("Remote error: " + e.getMessage());
	    	 System.exit(-1); // can't just return, rmi threads may not exit
	     }
	     System.out.println("Trigger-observer server ready!...");
	}

}
