/**
 * SensorLocal.java 
 * @author: José Simó 
 * SDI-ETSINF projects.
 * (c) ai2-UPV Creative Commons
 */
package componente;

public class SensorLocal {
	
	private String id;
	private double valor;
	
	private static long periodoDefecto = 100L;
	private long periodo = periodoDefecto;
	HiloMuestreo muestreador = null;
	
	SensorLocal (String identificador) {
		this(identificador,periodoDefecto);
	}
	
	SensorLocal (String identificador, long per) {
		this.id = identificador;
		this.periodo = per;
		muestreador = new HiloMuestreo();
		muestreador.start();
	}
	
	public String getId() {
		return id;
	}

	public synchronized double getValor() {
		return valor;
	}
	public synchronized void setValor(double valor) {
		this.valor = valor;
	}


	private class HiloMuestreo extends Thread {
		@Override
		public void run() {
			for(;;) {
				setValor(Math.random());
				try {
					Thread.sleep(periodo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
