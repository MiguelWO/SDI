/*
 *  Cifrador.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package server;

import java.rmi.RemoteException;

import rmi.CifradorInt;

public class Cifrador implements CifradorInt{

	@Override
	public String cifra(String input, String clave) throws RemoteException {
		String respuesta = new String("");
		if (clave.length()==0) return input;
		int j=0;
		for(int i=0;i<input.length();i++) {
			respuesta += (char)((int)input.charAt(i) ^ (int)clave.charAt(j));
			j = (j+1)%clave.length();
		}
		return respuesta;
	}

	@Override
	public String descifra(String input, String clave) throws RemoteException {		
		return cifra(input,clave);
	}
	

}
