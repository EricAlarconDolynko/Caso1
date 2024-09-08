package usuarios;

import depositos.Cinta;
import depositos.Ddistribucion;
import depositos.Dproduccion;

public class Operario extends Thread{
	
	private String tipo;
	private Dproduccion dproduccion;
	private Ddistribucion distribucion;
	private int counterIN;
	private int counterOUT;
	private Cinta cinta;
	
	public Operario(String tipo, Dproduccion dproduccion, Ddistribucion distribucion, Cinta cinta) {
		this.tipo = tipo;
		this.dproduccion = dproduccion;
		this.distribucion = distribucion;
		this.counterIN = 4;
		this.counterOUT = 4;
		this.cinta = cinta;
		
	}
	
	public synchronized void colocarProducto() {
		while (counterIN > 0) {
			entrarRetirarDeposito();
			String producto = dproduccion.retirarProducto();
			
			if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
				counterIN -= 1;
			}
			
			entrarCinta();
			cinta.ponerProducto(producto);
			
			if (counterIN == 0) {
				System.out.println("OPERADOR terminó de pasar todo a la cinta");	
			}
			cinta.salirCinta();
			cinta.avisarProduccion();
		}

	}
	
	public synchronized void entrarCinta() {
		while (cinta.getPermiso() == 0 || cinta.getProductoCinta()!=null) {
			Thread.yield();
		}
		cinta.disminuirPermiso();
	}
	
	
	public synchronized void entrarRetirarDeposito() {
		while (dproduccion.getSizeDeposito() ==0) {
			Thread.yield();	
		}
	}
	
	public synchronized void depositarProducto() {
		while (counterOUT > 0) {
			entrarCintaRetiro();
			String producto = cinta.retirarProducto();
			cinta.setProductoCinta(null);
			
			if (producto.equals("FIN_A") || producto.equals("FIN_B")) {
				counterOUT -= 1;
			}
			if (counterOUT == 0) {
				System.out.println("OPERADOR terminó de recoger todo de la cinta");		
			}
			
			cinta.salirCinta();
			
			entrarDepositoAgregar();
			distribucion.agregarProducto(producto);		
			
			cinta.avisarDistribuidores();
		}
	}
	
	public synchronized void entrarDepositoAgregar() {
		while (distribucion.getSizeDeposito() >= distribucion.getCapDepDist()) {
			synchronized(distribucion) {
				distribucion.notifyAll();				
			}
			Thread.yield();
		}
	}
	
	public synchronized void entrarCintaRetiro() {
		while (cinta.getPermiso() == 0 || cinta.getProductoCinta() == null) {
			Thread.yield();
		}
		cinta.disminuirPermiso();
	}
	
	@Override
	public void run() {
		if (tipo.equals("produccion")) {
			colocarProducto();
		}
		else {
			depositarProducto();
		}
	}

}
