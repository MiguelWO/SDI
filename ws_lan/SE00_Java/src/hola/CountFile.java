package hola;

import java.io.*;

public class CountFile {
	public static void main (String[] args)
		throws java.io.IOException,
		java.io.FileNotFoundException
	{
		int count = 0;
		InputStream is = null;
		String filename;


		if (args.length >= 1) {
			// EJERCICIO: Cree una instancia de FileInputStream, llamada is, 
			// para leer del fichero que se especifica como args[0] 
			filename = args[0];
			File file = new File(filename);
//			System.out.println(filename);
			is = new FileInputStream(file);

//			filename = args[0];
		} else {
			is = System.in;
			filename = "Input";
		}
		
		int character;

		while (is.read() != -1) 
			//EJERCICIO: utilice un metodo de FileInputStream para leer un caracte
			count++;
//		if (is != System.in)
//			is.close();
		
		System.out.println(filename + " has " + count
							+ " chars.");
		
	}

}