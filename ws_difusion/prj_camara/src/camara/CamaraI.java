/*
 *  @author Jose Simo. 
 *  (c) ai2-UPV Creative Commons.
 *  Rev: 2022
 */
package camara;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.zeroc.Ice.Current;


import agencia.datos.EstadoRobot;
import agencia.datos.IPYPort;
import agencia.datos.Instantanea;
import agencia.datos.suscripcion;
import agencia.objetos.RobotSeguidorPrx;
import comm.Difusion;
import comm.DifusionMulticast;

/**
 * 
 */
public class CamaraI implements agencia.objetos.Camara
{
	
   private LinkedList<String> listaRobots = new LinkedList<String>();
   private LinkedList<EstadoRobot> listaEstados = new LinkedList<EstadoRobot>();

   Instantanea instantanea;
   private int nrobots;

   private IPYPort ipyport;
   
   private com.zeroc.Ice.Communicator iceComunicator;

///////////////////////////////////////////////////////////////////////////////////////////////
   
   public CamaraI(com.zeroc.Ice.Communicator ic, IPYPort iport) {
	   
	   iceComunicator = ic;
     ipyport = new IPYPort(iport.ip, iport.port);
     nrobots = 0; 
   }
   
   
   public void start(){

	      new CamaraDifusion(ipyport);
   }
   
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
   
	@Override
	public suscripcion SuscribirRobot(String IORrob, Current current) {
		
		//////////////////////////////////////////////////////
	  //EJERCICIO: Implementar la suscripcion del robot
		//EJERCICIO: Retornar un objeto "suscripcion".
		//////////////////////////////////////////////////////

        return null;
	}



///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

    //------------------------------------------------------------------------------
    // La clase anidada CamaraDifusion
    //------------------------------------------------------------------------------
    class CamaraDifusion {
      private Timer timer;
      private Difusor df;
      private int periodMS = 500;

      public CamaraDifusion(IPYPort iport){
         timer = new Timer();
         df= new Difusor(iport);
         timer.schedule(df,0,periodMS);
      }
    //------------------------------------------------------------------------------
      class Difusor extends TimerTask {
    	  
          private Difusion difusion;
          //
          public Difusor(IPYPort iport){
           //
            try {
				difusion = new DifusionMulticast(iport.ip, ipyport.port);
			} catch (Exception e) {
				e.printStackTrace();
			}
          }
          public void run() {
             //System.out.print("Voy a difundir...");
             EstadoRobot st = new EstadoRobot();
             LinkedList<String> listaFallos = new LinkedList<String>();
              //
             listaEstados.clear();
             listaFallos.clear();
             LinkedList<String> listaRobotsCopia = (LinkedList<String>) listaRobots.clone();
             
             for (Iterator<String> i = listaRobotsCopia.iterator(); i.hasNext(); ) {
               String ior=null;
               try {
                    ior = (String) i.next();
                    ////////////////////////////////////////////////////////////////
                    //EJERCICIO: invocar via ICE el metodo ObtenerEstado y anyadir
                    //el estado del robot correspondiente a la lista de estados          
                    ////////////////////////////////////////////////////////////////
                  

               } catch (Exception e){
                  System.out.println("Detectado fallo x en Robot: " + ior );
                  ////////////////////////////////////////////////////////////////
                  //EJERCICIO: anyadir el robot caido a la lista de fallos 
                  ////////////////////////////////////////////////////////////////

               }
             }
             //
             for (Iterator<String> i = listaFallos.iterator(); i.hasNext(); ) {
            	 listaRobots.remove(i.next());
             }
             ////////////////////////////////////////////////////////////////
             //EJERCICIO: crear una instantanea a partir de la lista de estados de los robots. 
             //instantanea = new Instantanea(/*EJERCICIO*/);
             //EJERCICIO: difundir la instantanea 
             ////////////////////////////////////////////////////////////////////////
             
             //////
        } // de run
      } // de clase difusor
    } // de clase CamaraDifusion
//////////////////////////////////////CamaraDifusion//////////////////////////////////////////////
    
}
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

