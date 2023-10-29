/*
 *  Trigger.java
 *
 *  @author Jose Simo. 
 *  (c) ai2-DISCA-UPV Creative Commons.
 *  Rev: 2022
 */
package trigger;

import java.util.ArrayList;
import java.util.HashMap;


public class Trigger  {

	private HashMap<String, sdi.observerIce.ObserverPrx> listeners = null;
	private int listenersCount = 0;
	
	public Trigger() {
		listeners = new HashMap<String, sdi.observerIce.ObserverPrx>();
		new dummyActivity().start();
	}
	

	public String addListener(sdi.observerIce.ObserverPrx observer) {
		String id = "tiggerListener" + listenersCount;
		listenersCount++;
		synchronized(listeners) {
			listeners.put(id, observer);
		}
		return id;
	}

	
	public boolean removeListener(String observerId)  {
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
					sdi.observerIce.ObserverPrx listener = (sdi.observerIce.ObserverPrx) listeners.get(listenerID);
					if (listener != null) {
						// Call to the remote listener object callback.
						try {
							long t1 = System.nanoTime();
							listener.eventCallback(dummyParam);
							long diffT = (System.nanoTime() - t1) / 1000;
							System.out.println("Remote call time: " + diffT + " microseconds." );
						} catch (Exception e) {
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


