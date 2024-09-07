package consola;

import java.util.Scanner;

import depositos.Cinta;
import depositos.Dproduccion;
import usuarios.Productor;
import depositos.Ddistribucion;
import usuarios.Distribuidor;
import usuarios.Operario;


public class Ejecucion {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("=================================");
		System.out.println("BIENVENIDO AL CASO 1 \n");
		
		System.out.println("Ingrese el valor para numProductos");
		int numProductos = Integer.parseInt(scan.nextLine());
		System.out.println("\n");
		
		System.out.println("Ingrese el valor para capDepProd");
		int capDepProd = Integer.parseInt(scan.nextLine());
		System.out.println("\n");
		
		System.out.println("Ingrese el valor para capDepDist");
		int capDepDist = Integer.parseInt(scan.nextLine());
		System.out.println("\n");
		
		System.out.println("Cargando.... \n");
		
		Dproduccion dproduccion = new Dproduccion(capDepProd); 
		Ddistribucion distribucion = new Ddistribucion(capDepDist);
		Cinta cinta = new Cinta(dproduccion,distribucion);
		
		
		//Creacion Productores
		for(int i = 0; i < 4; i++) {
			if(i%2 == 0) {
				Productor productorA = new Productor("azul",numProductos,dproduccion);
				productorA.start();
			}
			else {
				Productor productorB = new Productor("naranja",numProductos,dproduccion);
				productorB.start();
			}
		}
		
		//Creacion Operario
		Operario operarioIN = new Operario("produccion",dproduccion,distribucion,cinta);
		Operario operarioOUT = new Operario("distribucion",dproduccion,distribucion,cinta);
		operarioIN.start();
		operarioOUT.start();

		//Creacion Distribuidor
		for(int i = 0; i < 4; i++) {
			if(i%2 == 0) {
				Distribuidor distribuidorA = new Distribuidor("azul",distribucion);
				distribuidorA.start();
			}
			else {
				Distribuidor distribuidorB = new Distribuidor("naranja",distribucion);
				distribuidorB.start();
			}
		}
		
		
		
	}

}
