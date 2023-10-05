/*
 *  Cifrante.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */

package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.CifradorInt;


public class Cifrante {

	
	public static void main(String[] args) {
		
		if (args.length<1){
			System.out.println("Uso echo <host>");System.exit(1);
		}
		
		String host = args[0];
        
        
        CifradorInt cif = null;
		try {
			System.out.println("Locating registry at "+host+ " port 1099");
			Registry reg = LocateRegistry.getRegistry(host,1099);
			System.out.println("Lookingup micifradorXOR ");
			cif = (CifradorInt) reg.lookup("micifradorXOR");
			//cif = (CifradorInt) Naming.lookup("micifradorXOR");
			String men = "ABCDEFGHI";
			String menCifrado = "";
			System.out.println("Calling remote cipher ");
			menCifrado = cif.cifra(men, "pepone");

			System.out.println("Mensaje cifrado: "+ menCifrado);
			System.out.println("Mensaje descifrado: "+ cif.descifra(menCifrado, "pepone"));
			
		} catch (RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        

	}
	
}
