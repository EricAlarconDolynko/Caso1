package usuarios;

import depositos.Ddistribucion;

public class Distribuidor extends Thread{
	
	private String color;
	private Ddistribucion distribucion;
	private String productoActual;
	private int counterProducto;
	
	public Distribuidor(String color, Ddistribucion distribucion) {
		this.color = color;
		this.distribucion = distribucion;
		this.productoActual = "C";
		this.counterProducto = 0;
	}
	
	public synchronized void consumir() {
		String productoFinal = productoFinalConsumir();
		String productoAConsumir = productoConsumir();
		while (!productoActual.equals(productoFinal)) {
			distribucion.entrarDeposito(productoAConsumir);
			productoActual = distribucion.retirarProducto(productoAConsumir);
			counterProducto += 1;
			distribucion.salirDeposito();
		}
		System.out.println(color + " TERMINE DE CONSUMIR " + (counterProducto-1) + " productos  y 1 de tipo FIN_" + productoConsumir());
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
