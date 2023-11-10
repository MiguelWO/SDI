/**
 * SensorGrupo.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package componente;

public class SensorGrupo {

	private String id;
	private double valor;
	private long timestamp;
	
	SensorGrupo(String identificador, double val) {
		this.id = identificador;
		setValor(val);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
		timestamp = System.currentTimeMillis();
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	public long getAntiguedad() {
		return System.currentTimeMillis() - timestamp;
	}

}
