package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import compute.TaskInt;



public class TaskEcho implements TaskInt,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Override
	public Object execute() throws RemoteException {

			//EJERCICIO
		return "Sin Parametros";
	}

	@Override
	public Object execute(Object params) throws RemoteException {
			
			//EJERCICIO
		return "Info: "  + params.toString();
	}		
		
}
	
	

