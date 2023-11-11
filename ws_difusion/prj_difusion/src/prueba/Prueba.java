/*
 *  @author Jose Simo. 
 *  (c) ai2-UPV Creative Commons.
 *  Rev: 2022
 */
package prueba;

import java.util.LinkedList;
import comm.*;
import agencia.datos.IPYPort;
import agencia.datos.Instantanea;
import agencia.datos.EstadoRobot;

//------------------------------------------------------------------------------
// La clase Prueba
//------------------------------------------------------------------------------
public class Prueba {
  IPYPort ipyport;

//------------------------------------------------------------------------------
  public Prueba(){
    ipyport = new IPYPort("228.1.1.1",1110);
    new CamaraDifusion();
    new RobotDifusion();
  }

//------------------------------------------------------------------------------
  public static void main(String args[]) {
	  //Crea el objeto para arrancar la aplicacion
	  new Prueba();
  }


//------------------------------------------------------------------------------
// La clase anidada CamaraDifusion (el servidor)
//------------------------------------------------------------------------------
  class CamaraDifusion extends Thread{
    Difusion difusion;
    Instantanea instantanea;
    LinkedList<EstadoRobot> listaEstados = new LinkedList<EstadoRobot>();
    EstadoRobot st1 = new EstadoRobot();
    EstadoRobot st2 = new EstadoRobot();

//------------------------------------------------------------------------------
    public CamaraDifusion(){
      difusion = new DifusionMulticast(ipyport.ip, ipyport.port);
      st1.nombre = "Hola 1!"; 
      st2.nombre = "Hola 2!";
      listaEstados.add(st1); 
      listaEstados.add(st2);
      this.start();
    }

//------------------------------------------------------------------------------
    public void run(){

      for(int i=1; i<6; i++) {
        instantanea = new Instantanea((EstadoRobot[]) listaEstados.toArray(new EstadoRobot[0]));
        difusion.sendObject(instantanea);
        try{
          Thread.sleep(400);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }


//------------------------------------------------------------------------------
// La clase anidada RobotDifusion (el cliente)
//------------------------------------------------------------------------------
  class RobotDifusion extends Thread{
    Difusion difusion;
    Instantanea instantanea;
    EstadoRobot st = new EstadoRobot();

//------------------------------------------------------------------------------
    public RobotDifusion(){
      difusion =  new DifusionMulticast(ipyport.ip, ipyport.port);
      this.start();
    }

//------------------------------------------------------------------------------
    public void run(){

      while(true){
        instantanea = (Instantanea) difusion.receiveObject();
        System.out.println("-Escuchada difusion-");
        for(int i=0; i<instantanea.estadorobs.length; i++){
            st = instantanea.estadorobs[i];
            System.out.println("Contenido " + i + ": " + st.nombre);
        }
        System.out.println("Finalizando");
        try{
          Thread.sleep(400);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }
}