package hola;

import java.io.*;

class Punto {
   public int x = 0;
   public int y = 0;

   public Punto(int x, int y) {
      this.x = x;
      this.y = y;
   }
}

class Rectangulo {
  protected Punto origen;
  protected int ancho = 0;
  protected int alto = 0;
  private static String nombreClase ="Rectangulo";

  public Rectangulo(int origenx, int origeny, int ancho, int alto){
    origen=new Punto(origenx, origeny);
    this.ancho=ancho; this.alto=alto;
  }
  public Rectangulo(Punto p, int ancho, int alto){
    origen= p;
    this.ancho=ancho; this.alto=alto;
  }
  public Rectangulo(int ancho, int alto){
    origen= new Punto(0,0);
    this.ancho=ancho; this.alto=alto;
  }
  public Rectangulo(){
    origen= new Punto(0,0);
    this.ancho=0; this.alto=0;
  }
  public int ancho(){
    return ancho;
  }
  public int alto(){
    return alto;
  }
  public int area(){
    return (ancho*alto);
  }
  public void mover(int arriba, int derecha){
    origen.x+=arriba; origen.y+=derecha;
  }
  public String toString() {
    return "(Origen: {" + Integer.toString(origen.x) +
           "," + Integer.toString(origen.y) +
           "}, Final: {" + Integer.toString(origen.x+ancho) +
           "," + Integer.toString(origen.y+alto) + "})";
  }
  public static String nombre(){
      return nombreClase;
  }
  protected void finalize() throws Throwable {
     origen = null;
     super.finalize();
  }
}

//EJERCICIO: Implemente una clase RectanguloColor, bas�ndose en la clase Rectangulo. 
//EJERCICIO: con un nuevo atributo color y sobrecargando el m�todo toString 
class RectanguloColor extends Rectangulo {
	//EJERCICIO: ... 
	private int color;
	
	
    

    public RectanguloColor(Punto p, int lado, int lado2, int color) {
		super(p,lado,lado2);
		this.color = color;
	}

	@Override
	public String toString() {
    //EJERCICIO: Sobrecargue este m�todo para que incluya en el String tambi�n el color 
    	return "RectanguloColor [largo=" + alto() + ", ancho=" + ancho() + ", color=" + color + "]";
    }
}


class CuadradoColor extends RectanguloColor {
    public CuadradoColor(Punto p, int lado, int color){
       super(p,lado,lado,color);
   }
}


public class Objetos{

  static Rectangulo R1 = new Rectangulo(1,1,7,8);
  static Rectangulo R2 = new Rectangulo(new Punto(2,2),7,8);
  static Rectangulo R3 ;
  static RectanguloColor RC;
  static CuadradoColor C;

  public static void main(String args[]) throws IOException{

    if (args.length < 4){
      System.out.println("Uso: Objetos origen-x origen-y ancho alto");
      System.exit(1);
    }

    int[] i = new int[4];
    int j = 0;

    for(j=0; j < i.length; j++)
       i[j] = Integer.parseInt(args[j]);
    R3 = new Rectangulo(i[0],i[1],i[2],i[3]);

    RC= 
    	//EJERCICIO: Cree una instancia de rect�ngulo color RC 
    	//EJERCICIO: que a�ada a R3 el atributo de color. 

    System.out.println("Nombre de la clase: " + Rectangulo.nombre());
    System.out.println("Nombre de la clase de R1: " + R1.getClass().getName());
    System.out.println("Area de R3: " + R3.area());
    System.out.println("R3: " + R3.toString());
    System.out.println("RC: " + RC.toString());

  //EJERCICIO: Invoque el m�todo mover(10,10) sobre R3 
  //EJERCICIO: Invoque el m�todo toString sobre R3 y RC 
  //EJERCICIO: y visualicelos por pantalla el resultado 

  //PREGUNTA: Se ha "movido" R3? y RC? Deber�a haberse "movido" RC? 
  // Explique convenientemente este aspecto. 


  C= //EJERCICIO: Cree un CuadradoColor con origen en 
//   	el punto: (2,2),alto=5,color=25 


    System.out.println("C: " + C.toString());
    System.out.println("Area de C: " + C.area());

   }
}
