package hola;

import java.io.*;

public class CopyFile {
	public static void main (String[] args)
		throws java.io.IOException
	{
		int count = 0;
		String filenameorg= null;
		String filenamedst= null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[256];

		if 	(args.length < 2){
			System.out.println("Usage: copy fileorg filedst");
			System.exit(1);
		}

		// EJERCICIO: Cree una instancia de FileInputStream, llamada is, 
		// para leer del fichero que se especifica como args[0]
		filenameorg = args[0];
		is = new FileInputStream(filenameorg);

		// EJERCICIO: Cree una instancia de FileOutputStream, llamada os, 
		// para escribir en el fichero que se especifica como args[1] 
		filenamedst = args[1];
		is = new FileInputStream(filenamedst);

		while ((count = is.read(buffer)) != -1)
			os.write(buffer,0,count);
		
		is.close();
		os.close();
		
		System.out.println("Copied " + filenameorg + " to " + filenamedst);
	}
}