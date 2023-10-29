/**
 * CifradorObject.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package server;

public class CifradorObject {

	public String cifra(String input, String clave)  {
		String respuesta = new String("");
		if (clave.length()==0) return input;
		int j=0;
		for(int i=0;i<input.length();i++) {
			respuesta += (char)((int)input.charAt(i) ^ (int)clave.charAt(j));
			j = (j+1)%clave.length();
		}
		return respuesta;
	}

	public String descifra(String input, String clave) {		
		return cifra(input,clave);
	}
	
}
