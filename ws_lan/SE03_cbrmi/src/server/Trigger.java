/*
 *  Trigger.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import rmi.ObserverInt;
import rmi.TriggerInt;

public class Trigger implements TriggerInt {

	private HashMap<String, ObserverInt> listeners = null;
	private int listenersCount = 0;
	
	public Trigger() {
		listeners = new HashMap<String, ObserverInt>();
		new dummyActivity().start();
	}
	
	@Override
	public String addListener(ObserverInt observer) throws RemoteException {
		String id = "tiggerListener" + listenersCount;
		listenersCount++;
		synchronized(listeners) {
			listeners.put(id, observer);
		}
		return id;
	}

	@Override
	public boolean removeListener(String observerId) throws RemoteException {
		Object obj = null;
		synchronized(listeners) {
			obj = listeners.remove(observerId);
		}
		return (obj != null) ? true : false;
	}
	
	// Working thread
	class dummyActivity extends Thread {
		public void run() {
			System.out.println("dummyActivity: Thread started. ");
			ArrayList<String> idSet = new ArrayList<String>();
			for(int i=0 ;; i++) {
				String dummyParam = "loop index " + i;
				System.out.println("dummyActivity: " + dummyParam);
				// iterate over the registered listeners
				synchronized(listeners) {
					idSet.addAll(listeners.keySet());
				}
				for (String listenerID : idSet) {
					ObserverInt listener = (ObserverInt) listeners.get(listenerID);
					if (listener != null) {
						// Call to the remote listener object callback.
						try {
							long t1 = System.nanoTime();
							listener.eventCallback(dummyParam);
							long diffT = (System.nanoTime() - t1) / 1000;
							System.out.println("Remote call time: " + diffT + " microseconds." );
						} catch (RemoteException e) {
							System.err.println("Remote exception .... removing object (ID:"+listenerID + ") from the listeners list.");
							synchronized(listeners) {
									listeners.remove(listenerID);
							}
						}
					}
				}
				idSet.clear();
				// 
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {;}
			}
		}
	}
}


