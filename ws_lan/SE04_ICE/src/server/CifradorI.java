/**
 * CifradorI.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package server;

import com.zeroc.Ice.Current;

import sdi.cifradorIce.Cifrador;

public class CifradorI implements Cifrador{

	//Tunnel
	CifradorObject servObject = new CifradorObject();

	@Override
	public String cifra(String input, String clave, Current current) {		
		return servObject.cifra(input, clave);
	}

	@Override
	public String descifra(String input, String clave, Current current) {
		return servObject.descifra(input, clave);
	}
	
}
