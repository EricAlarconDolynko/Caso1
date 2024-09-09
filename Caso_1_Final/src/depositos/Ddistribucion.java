package depositos;

import java.util.ArrayList;

public class Ddistribucion {
	
	private int capDepDist;
	private ArrayList<String> deposito;
	private int counterInControl;
	private int counterOutControl;
	private int permiso;
	
	
	public Ddistribucion(int capDepDist) {
		this.capDepDist = capDepDist;
		this.deposito = new ArrayList<String>();
		this.counterInControl = 4;
		this.counterOutControl = 4;
		this.permiso = 1;
	}

	public synchronized void entrarDeposito(String producto) {
		while (!present(producto) || permiso == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		permiso--;
	}
	
	public synchronized int getSizeDeposito() {
		return deposito.size();
	}
	
	public synchronized int getCapDepDist() {
		return capDepDist;
	}
	
	public synchronized boolean present(String producto) {
		boolean flag = false;
		for (String products: deposito) {
			if(products.contains(producto)) {
				flag = true;
			}
		}
		return flag;
		
	}
	
	public synchronized void agregarProducto(String producto) {
		deposito.add(producto);
		if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
			counterInControl -= 1;
		}
		if (counterInControl == 0) {
			System.out.println("ENTRARON todos los productos al deposito de distribucion");
		}
	}
	
	public synchronized String retirarProducto(String tipo) {
		String producto =  obtenerProducto(tipo);
		if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
			counterOutControl -= 1;
		}
		if (counterOutControl == 0) {
			System.out.println("SALIERON todos los productos del deposito de distribucion");
		}
		
		return producto;
	}
	
	public synchronized void salirDeposito() {
		permiso++;
		notify();
	}
	
	public synchronized String obtenerProducto(String tipo) {
		
		int i = 0;
		int target = -1;

		while (i < deposito.size()) {
			if (deposito.get(i).contains(tipo)) {
				target = i;
			}
			i++;
		}
		
		if (target == -1) {
			System.out.println("Algo salio mal en distribución");
			return null;
		}
		else {
			String producto = deposito.get(target);
			deposito.remove(target);
			return producto;
		}
	}

}
