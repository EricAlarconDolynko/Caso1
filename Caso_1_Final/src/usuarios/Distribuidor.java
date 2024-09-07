package usuarios;

import depositos.Ddistribucion;

public class Distribuidor extends Thread{
	
	private String color;
	private Ddistribucion distribucion;
	private String productoActual;
	
	public Distribuidor(String color, Ddistribucion distribucion) {
		this.color = color;
		this.distribucion = distribucion;
		this.productoActual = "C";
	}
	
	public synchronized void consumir() {
		String productoFinal = productoFinalConsumir();
		String productoAConsumir = productoConsumir();
		while (!productoActual.equals(productoFinal)) {
			distribucion.entrarDeposito(productoAConsumir);
			productoActual = distribucion.retirarProducto(productoAConsumir);
			distribucion.salirDeposito();
		}
		System.out.println(color + " TERMINE DE CONSUMIR");
	}
	
	public String productoConsumir() {
		if (color.equals("azul")) {
			return "A";
		}
		else {
			return "B";
		}
	}
	
	public String productoFinalConsumir() {
		if (color.equals("azul")) {
			return "FIN_A";
		}
		else {
			return "FIN_B";
		}
	}
	
	@Override 
	public void run() {
		consumir();
	}

}
