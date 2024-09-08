package depositos;

public class Cinta {
	
	private String productoCinta;
	private int permiso;
	private Dproduccion dproduccion;
	private Ddistribucion distribucion;

	
	public Cinta(Dproduccion dproduccion, Ddistribucion distribucion) {
		this.permiso = 1;
		this.dproduccion = dproduccion;
		this.distribucion = distribucion;
	}
	
	public synchronized int getPermiso() {
		return permiso;
	}
	
	public synchronized String getProductoCinta() {
		return productoCinta;
	}
	
	public synchronized void aumentarPermiso() {
		permiso++;
	}
	
	public synchronized void disminuirPermiso() {
		permiso--;
	}
	
	public synchronized void ponerProducto(String producto) {
		productoCinta = producto; 
	}
	
	public synchronized String retirarProducto() {
		String retorno = productoCinta;
		productoCinta = null;
		return retorno;
	}
	
	public synchronized void salirCinta() {
		permiso++;
		notifyAll();
	}
	
	public synchronized void avisarProduccion() {
		synchronized (dproduccion) {  
			dproduccion.notifyAll();  
	    }
	}
	
	public synchronized void avisarDistribuidores() {
		synchronized (distribucion) {  
	        distribucion.notifyAll();  
	    }
	}
	
	public void setProductoCinta(String productoCinta) {
		this.productoCinta = productoCinta;
	}
	
}
