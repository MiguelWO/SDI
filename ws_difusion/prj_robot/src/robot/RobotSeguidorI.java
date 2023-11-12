/*
 *  @author Jose Simo. 
 *  (c) ai2-UPV Creative Commons.
 *  Rev: 2022
 */
package robot;

import com.zeroc.Ice.Current;

import agencia.datos.EstadoRobot;
import agencia.datos.Instantanea;
import agencia.datos.suscripcion;
import agencia.objetos.CamaraPrx;
import comm.Difusion;

import comm.DifusionMulticast;
//
//

public class RobotSeguidorI implements agencia.objetos.RobotSeguidor
{

    CamaraPrx camaraProxy = null;

    private String minombre = "Mazinger";
    public void setNombre(String nom) {minombre = nom;}
    private String miIOR = null;
    public void setRobotIOR(String ior) {miIOR = ior;}    
    private int miid;

    private Instantanea instantanea;


    public RobotSeguidorI(String rName, CamaraPrx camPrx) {
    	
    	minombre = rName;
    	camaraProxy = camPrx;
    }
    //
    
    @Override
    public EstadoRobot ObtenerEstado(Current current) {
    	///////////////////////////////////////////////////////////
    	//EJERCICIO: componer un objeto EstadoRobot y retornarlo
    	///////////////////////////////////////////////////////////

      return null;
    }

	// Arranca la actividad del robot.
    public void start(){
        new RobotDifusion().start();
    }
    

  //------------------------------------------------------------------------------
  // La clase anidada RobotDifusion
  //------------------------------------------------------------------------------
  class RobotDifusion extends Thread {

    private Difusion difusion;
    private EstadoRobot sr;
    private suscripcion sus;

    public void run(){
      //////////////////////////////////////////////////////////////
      //EJERCICIO: suscribir el robot en la camara. 
      //           Almacenar la suscrioción en "sus". 
      //           Almacenar el Id otorgado.
      //EJERCICIO: crear conector de difusion (DifusionMulticast)
      //////////////////////////////////////////////////////////////	
 
      
      //////////////////////////////////////////////////////////////
      //Bucle: recibir, ...
      while(true){
    	  
    	//////////////////////////////////////////////////////////////
      //EJERCICIO: recibir instantanea
      //EJERCICIO: iterar sobre la lista de estados, imprimiendo el nombre de
      //           todos los robots cuyo estado figura en la instantanea.
      //////////////////////////////////////////////////////////////
      }
    } 
  } // de RobotDifusion
  //
} // de RobotSeguidorI
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
