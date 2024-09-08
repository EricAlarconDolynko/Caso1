package depositos;

import java.util.ArrayList;

public class Dproduccion {
	
	private int capDepProd;
	private ArrayList<String> deposito;
	private int counterInControl;
	private int counterOutControl;
	
	public Dproduccion(int capDepProd) {
		this.capDepProd = capDepProd;
		this.deposito = new ArrayList<String>();
		this.counterInControl = 4;
		this.counterOutControl = 4;
	}

	public synchronized void entrarDeposito() {
		while (deposito.size() >= capDepProd) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized int getSizeDeposito() {
		return deposito.size();
	}
	
	public synchronized void agregarProducto(String producto) {
		deposito.add(producto);
		if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
			counterInControl -= 1;
		}
		if (counterInControl == 0) {
			System.out.println("ENTRARON todos los productos al deposito de producción");
		}
	}
	
	public synchronized String retirarProducto() {
		String producto =  deposito.get(0);
		deposito.remove(0);
		
		if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
			counterOutControl -= 1;
		}
		if (counterOutControl == 0) {
			System.out.println("SALIERON todos los productos del deposito de producción");
		}
		return producto;
	}
	
	public synchronized void salirDeposito() {
		notifyAll();
	}
	
}
