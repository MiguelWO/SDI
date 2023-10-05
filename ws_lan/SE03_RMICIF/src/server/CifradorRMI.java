/*
 *  CifradorRMI.java
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


import rmi.CifradorInt;
import util.Net;

public class CifradorRMI implements CifradorInt{

	private Cifrador cif = new Cifrador();
	
	protected CifradorRMI()  {
		super();
	}

	@Override
	public String cifra(String input, String clave) throws RemoteException {
		return cif.cifra(input, clave);
	}

	@Override
	public String descifra(String input, String clave) throws RemoteException {
		return cif.descifra(input, clave);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 //System.setProperty("java.rmi.server.hostname","192.168.1.45");
		 String localIP = Net.chooseOperatingIPAddr();
		 System.out.println("The local IP address is " + localIP);
		 System.setProperty("java.rmi.server.hostname",localIP);
		 
	       try {
	            Registry registry = LocateRegistry.getRegistry();
	            CifradorInt stub = (CifradorInt) UnicastRemoteObject.exportObject(new CifradorRMI(),0);
	            registry.rebind("micifradorXOR", stub);
	        } catch (RemoteException e) {
	            System.err.println("Error en el sistema remoto:" + e.getMessage());
	            System.exit(-1); // can't just return, rmi threads may not exit
	        }
	        System.out.println("Servidor de cifrado preparado");

	}
	
	
	
	
	
	
}
