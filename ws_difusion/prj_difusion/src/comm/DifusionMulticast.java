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
	try {
		socket = new MulticastSocket(port);
		group = new InetSocketAddress(ip,port);
		socket.joinGroup(group, null);
	} catch (IOException e) {
		e.printStackTrace();
	}
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
    	try {
			socket.receive(packet);
			buffer = packet.getData();
	    	bis = new ByteArrayInputStream(buffer);
	    	ois = new ObjectInputStream(bis);
			object = ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if ( bis!= null) bis.close();
				if (ois != null) ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
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
    
    try {
    	bos = new ByteArrayOutputStream();
    	oos = new ObjectOutputStream(bos);
    	
    	oos.writeObject(object);
    	oos.flush();
    	buffer = bos.toByteArray();
    	
    	packet = new DatagramPacket(buffer, buffer.length, group.getAddress(), m_port);
    	
    	socket.send(packet);	
    } catch (IOException e) {
    	e.printStackTrace();
    } finally {
    	try {
			if (bos!= null) bos.close();
			if (oos != null)oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
  }
}