/*
 *  ObserverI.java
 *
 *  @author Jose Simo. 
 *  (c) ai2-DISCA-UPV Creative Commons.
 *  Rev: 2022
 */

package observer;

import com.zeroc.Ice.Current;


public class ObserverI implements sdi.observerIce.Observer {
	/**
	 * Constructor
	 */	
	public ObserverI() {
		//
	}

	@Override
	public String eventCallback(String arg, Current current) {
		// 
		System.out.println("Observer callback receive: " + arg);
		return "";
	}
}
