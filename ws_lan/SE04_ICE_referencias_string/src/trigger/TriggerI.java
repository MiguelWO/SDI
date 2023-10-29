/*
 * TriggerI.java
 *
 *  @author Jose Simo. 
 *  (c) ai2-DISCA-UPV Creative Commons.
 *  Rev: 2022
 */

package trigger;

import com.zeroc.Ice.Current;

import sdi.observerIce.ObserverPrx;

//
public class TriggerI implements sdi.observerIce.Trigger {
	
	private com.zeroc.Ice.Communicator myIc = null;
	private Trigger myTrigger = null;
	
	public TriggerI(com.zeroc.Ice.Communicator ic) {
		myIc = ic;
		myTrigger = new Trigger();
	}
	
	@Override
	public String addListener(String observerIOR, Current current) {
		//
		System.out.println("Recibido IOR: " + observerIOR);
        com.zeroc.Ice.ObjectPrx base = myIc.stringToProxy(observerIOR);
        // Narrow proxy.
        ObserverPrx observerObj = ObserverPrx.checkedCast(base);
        if(observerObj == null) {
            throw new Error("Invalid proxy");
        }
		String respponseID = myTrigger.addListener(observerObj);
		return respponseID;
	}

	@Override
	public boolean removeListener(String observerId, Current current) {
		//
		return  myTrigger.removeListener(observerId);
	}

}
