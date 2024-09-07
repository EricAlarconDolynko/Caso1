package usuarios;

import depositos.Dproduccion;

public class Productor extends Thread{
	
	private String color;
	private int numProductos;
	private Dproduccion dproduccion;
	
	public Productor(String color, int numProductos, Dproduccion dproduccion){
		this.color = color;
		this.numProductos = numProductos;
		this.dproduccion = dproduccion;
	}
	
	public synchronized void producir() {
		while (numProductos > 0) {
			String producto = crearProducto();
			dproduccion.entrarDeposito();
			dproduccion.agregarProducto(producto);
			dproduccion.salirDeposito();
			numProductos--;
		}
		
		String producto = ultimoProducto();
		dproduccion.entrarDeposito();
		System.out.println(color + "Entregó su último producto");
		dproduccion.agregarProducto(producto);
		dproduccion.salirDeposito();
	}
	
	public String crearProducto() {
		if (color.equals("azul")) {
			return "A";
		}
		else {
			return "B";
		}
	}
	
	public String ultimoProducto() {
		if (color.equals("azul")) {
			return "FIN_A";
		}
		else {
			return "FIN_B";
		}
	}
	
	@Override 
	public void run() {
		producir();
	}
	
}
