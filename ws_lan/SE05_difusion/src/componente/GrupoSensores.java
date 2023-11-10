/**
 * GrupoSensores.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package componente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrupoSensores {

	private SensorLocal miSensor;
	private ConectorDifusion miDifusion;
	HashMap<String, SensorGrupo> todosSensores = new HashMap<String, SensorGrupo>();
	
	GrupoSensores(String localId, String IP, int puerto) {
		miSensor = new SensorLocal(localId);
		miDifusion = new ConectorDifusion(this,IP,puerto,500);
	}
	
	public SensorLocal getSensorLocal() {
		return miSensor;
	}
	
	public synchronized void nuevoValorSensor(String id, float val) {	
		SensorGrupo sensor = todosSensores.get(id);
		if (sensor != null) {
			sensor.setValor(val);
		} else {
			sensor = new SensorGrupo(id,val);
			todosSensores.put(id, sensor);
		}
	}
	
	public synchronized List<SensorGrupo> getSensores(){
		List<SensorGrupo> listaSensores = new ArrayList<SensorGrupo>();
		listaSensores.addAll(todosSensores.values());
		return listaSensores;
	}
	
	public synchronized void deleteSensor(String id){
		todosSensores.remove(id);
	}
	
	
	public static void main(String[] args) {
		
		if (args.length < 3) {
			System.err.println("Uso: SensorGrupo nombre IPDifusion puerto\n");
			return;
		}
		
		GrupoSensores miGrupo = new GrupoSensores(args[0], args[1], Integer.parseInt(args[2]));
		
		for(;;) {
			List<String> listaBorrados = new ArrayList<String>();
			for (SensorGrupo sensor: miGrupo.getSensores()) {
				System.out.println("Sensor: " + sensor.getId() +" = "+ sensor.getValor() + 
						           " ("+sensor.getTimestamp()+")");
				if (sensor.getAntiguedad() > 2000) {
					listaBorrados.add(sensor.getId());
				}
			}
			for (String id:listaBorrados) {
				miGrupo.deleteSensor(id);
			}
			System.out.println("-------------------");
			try {	
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
