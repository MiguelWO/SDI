//Se trata de escribir en un fichero con formato: 
// 
// 9.99 	12 	Java T-shirt 
// 9.99 	8	Java Mug 
// 15.99 13 	Duke Juggling Dolls 
// 
// y luego leerlo y sacarlo por pantalla 
// NOTA: los datos num�ricos deben escribirse como "n�meros" y 
// no como cadenas de caracteres. 
// NOTA: los Strings deben escribirse como cadenas de bytes 
// no como cadenas de caracteres (1caracter = 2 bytes) 

package hola;
import java.io.*;
import java.text.DecimalFormat;     

public class DataIOTest {

    public static void main(String[] args) throws IOException {

       //EJERCICIO: Instancie un objeto de tipo DataOutputStream para escribir 
       //     	     en el fichero "invoice1.txt" 
    	DataOutputStream out = new DataOutputStream(new FileOutputStream("invoice1.txt"));

        double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
        int[] units = { 12, 8, 13, 29, 50 };
        String[] descs = { "Java T-shirt",
                           "Java Mug",
                           "Duke Juggling Dolls",
                           "Java Pin",
                           "Java Key Chain" };
        
        for (int i = 0; i < prices.length; i ++) {
            out.writeDouble(prices[i]);
            out.writeChar('\t');
            out.writeInt(units[i]);
            out.writeChar('\t');
            out.writeChars(descs[i]);
            out.writeChar('\n');
        }
        out.close();

      //EJERCICIO: Instancie un objeto de tipo DataInputStream para leer 
      //     	   del fichero "invoice1.txt" 
        DataInputStream in = new DataInputStream(new FileInputStream("invoice1.txt"));

        double price;
        int unit;
        String desc;
        double total = 0.0;
        DecimalFormat df = new DecimalFormat("###.##");
        
        System.out.println("You've ordered:\n");
        
        try {
            while (true) {
              //EJERCICIO: leer el primer double del fichero sobre la variable price 
            	price = in.readDouble();
                in.readChar();       // throws out the tab
              //EJERCICIO: leer el int siguiente sobre la variable unit 
                unit = in.readInt();
                in.readChar();       // throws out the tab

                //EJERCICIO: leer la cadena siguiente sobre la variable desc 
                // Nota: no se puede usar
                //     desc = in.readLine(); //is deprecated
                //     Hay que leer caracter a caracter con readChar()
                StringBuilder descBuilder = new StringBuilder();
                char nextChar;
                
                while((nextChar = in.readChar()) != '\n')
                	descBuilder.append(nextChar);
                desc = descBuilder.toString();

                //  display results
                printInColumn(Integer.toString(unit), 10, 1);
                printInColumn(desc, 25, 0);
                printInColumn(Double.toString(price), 10, 2);
                System.out.println();
                                    
                total = total + unit * price;
            }
        } catch (EOFException e) {
        }
        
        System.out.println("\nFor a TOTAL of: $" + df.format(total));
        in.close();
    }    
 
    // code from Java Class Libraries: 2nd edition, p.636   
    // modified to determine if value is int or double
    static void printInColumn(String str, int col, int flag) {
        String s;
        int length;
        DecimalFormat dfRnd = new DecimalFormat("#00.##");    
        DecimalFormat dfInt = new DecimalFormat("00");
               
        switch(flag) {
            case 1:         // argument is an int
                s = "\t" + dfInt.format(Integer.parseInt(str));
                length = s.length();       
                break;
            case 2:         // argument is a double
                s = dfRnd.format(Double.parseDouble(str));
                length = s.length();
                break;
            default:
                s = str;
                length = str.length();
        }
        System.out.print(s);
        
        for(int p = length; p<col; ++p) {
            System.out.print(" ");
        }
    }
}
