/**
 * ConectorDifusion.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package componente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ConectorDifusion {

	private String direccionMulticast = "228.10.1.1"; // Rango [224.0.0.0..239.255.255.255]. (224.0.0.0 reservada)
	private int puertoMulticast = 7110;
	//
	private long periodoDifusion = 200;
	
	private MulticastSocket socket = null;
	private InetSocketAddress inetAddr = null;
	
	private GrupoSensores grupoSensores = null;
	
	ConectorDifusion(GrupoSensores grupoSens, String direccionIP, int puerto, long periodo) {
		this(grupoSens,direccionIP,puerto);
		periodoDifusion = periodo;
	}
	
	ConectorDifusion(GrupoSensores grupoSens, String direccionIP, int puerto) {
		
		grupoSensores = grupoSens;
		direccionMulticast = direccionIP;
		puertoMulticast = puerto;
		
		inetAddr = new InetSocketAddress(direccionMulticast, puertoMulticast);
		try {
			socket = new MulticastSocket(puertoMulticast);
			socket.joinGroup(inetAddr, null); // null utiliza el interfaz de red por defecto.
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		new HiloLecturaDifusion().start();
		new HiloEscrituraDifusion().start();
	}
	
	private class HiloLecturaDifusion extends Thread {
		@Override
		public void run() {
		
			byte[] buffer = new byte[4096];
		    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		    
			for(;;) {
			    try {
					socket.receive(packet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			    buffer = packet.getData();
			    String mensaje = new String(buffer);
			    String id = mensaje.substring(mensaje.indexOf("{\"")+2, mensaje.indexOf("\":"));
			    String valor = mensaje.substring(mensaje.indexOf("\":\"")+3, mensaje.indexOf("}")-1);
			    grupoSensores.nuevoValorSensor(id,Float.parseFloat(valor));
			    

			}
		}	
	}
	
	
	private class HiloEscrituraDifusion extends Thread {
		@Override
		public void run() {
			
			byte[] buffer = new byte[4096];
		    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		      
			for(;;) {
				String mensaje = "{\""+grupoSensores.getSensorLocal().getId() +
						         "\":\""+grupoSensores.getSensorLocal().getValor()+"\"}";
				buffer = mensaje.getBytes();
				
			    try {
			    	packet = new DatagramPacket(buffer, 
			    								buffer.length, 
			    								InetAddress.getByName(direccionMulticast), 
			    								puertoMulticast);
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			    
				try {
					Thread.sleep(periodoDifusion);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
