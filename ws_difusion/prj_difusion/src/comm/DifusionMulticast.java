/*
 *  @author Jose Simo. 
 *  (c) ai2-UPV Creative Commons.
 *  Rev: 2022
 */

package comm;

import java.io.*;
import java.net.*;



public class DifusionMulticast implements Difusion{

  MulticastSocket socket;
  String m_ip;
  int m_port;
  public InetSocketAddress group;
  
  public static final int BUFSIZE = 65535;

//------------------------------------------------------------------------------
  public DifusionMulticast(String ip, int port) {
	  
	m_ip = ip;
	m_port = port;
	///////////////////////////////////////////////////////////
	  //EJERCICIO: 
	  //Crear el socket multicast 
	  //EJERCICIO: 
	  //Obtener la direccion del grupo 
	  //EJERCICIO: 
	  //Unirse al grupo 
	///////////////////////////////////////////////////////////
	
  }

//------------------------------------------------------------------------------
  public Object receiveObject(){

    Object object = null;  
    byte[] buffer = new byte[BUFSIZE];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    ByteArrayInputStream bis = null;
    ObjectInputStream ois = null;
    ///////////////////////////////////////////////////////////
    //EJERCICIO: recibir el paquete y deserializarlo 
    ///////////////////////////////////////////////////////////

    return object;
  }

//------------------------------------------------------------------------------
  public void sendObject(Object object){
    byte[] buffer;
    DatagramPacket packet;
    ByteArrayOutputStream bos = null;
    ObjectOutputStream oos = null;
    ///////////////////////////////////////////////////////////
    //EJERCICIO: serializar el paquete y difundirlo   
    ///////////////////////////////////////////////////////////
    
  }
}