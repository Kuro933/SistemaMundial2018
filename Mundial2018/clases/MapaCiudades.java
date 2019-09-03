package clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import estructuras.GrafoEtiq;

public class MapaCiudades {

	private GrafoEtiq<Ciudad> grafoEtiq;

	public MapaCiudades(GrafoEtiq<Ciudad> grafoEtiq) {
		this.grafoEtiq = grafoEtiq;
	}

	public boolean insertarCiudad(String nombreCiudad, double sup, int cantHabit, boolean sede) {
		Ciudad city = new Ciudad(nombreCiudad, sup, cantHabit, sede);
		addToLog("Ciudad Agregada " + city.toStringCompleto()+  "\n");
		return (grafoEtiq.insertarVertice(city));
	}

	public boolean existeCiudad(String nombreCiudad) {
		Ciudad ciudadAux = new Ciudad(nombreCiudad);
		return (grafoEtiq.existeVertice(ciudadAux));
	}

	public void listarInformacionCiudad(String nombreCiudad) {
		Ciudad ciudadAux = new Ciudad(nombreCiudad);
		ciudadAux = (Ciudad)grafoEtiq.obtenerElemVertice(ciudadAux);
		System.out.println(ciudadAux.toStringCompleto());
	}

	public void eliminarCiudad(String nombreCiudad) {
		Ciudad ciudadAux = new Ciudad(nombreCiudad);
		grafoEtiq.eliminarVertice(ciudadAux);
		addToLog("\nSe ha eliminado la ciudad : "+nombreCiudad);
	}

	public void insertarRuta(String origen, String destino, double km) {
		Ciudad ciudadOrigen = new Ciudad(origen);
		Ciudad ciudadDestino = new Ciudad(destino);
		grafoEtiq.insertarArco(ciudadOrigen, ciudadDestino, km);
	}

	public void modificarNombre(String nombreCiudad, String nuevoNombre) {
		Ciudad city = new Ciudad(nombreCiudad);
		Ciudad ciudadAux = (Ciudad) grafoEtiq.obtenerElemVertice(city);
		ciudadAux.setNombre(nuevoNombre);
		addToLog("Modificacion de nombre de: "+nombreCiudad+", nuevo nombre: "+nuevoNombre + "\n");
	}

	public void modificarSuperficie(String nombreCiudad, double sup) {
		Ciudad city = new Ciudad(nombreCiudad);
		city = (Ciudad) grafoEtiq.obtenerElemVertice(city);
		double supAnterior=city.getSuperficieAprox();
		city.setSuperficie(sup);
		addToLog("Modificacion de superficie de: "+nombreCiudad+", superficie anterior: "+supAnterior+", nueva superficie: "+sup + "\n");
	}

	public void modificarCantHabit(String nombreCiudad, int cant) {
		Ciudad city = new Ciudad(nombreCiudad);
		city = (Ciudad) grafoEtiq.obtenerElemVertice(city);
		int cantAnterior=city.getCantHabitantes();
		city.setCantHabit(cant);
		addToLog("Modificacion de cantHabit de: "+nombreCiudad+", cantidad anterior: "+cantAnterior+", nueva cantidad: "+cant + "\n");
	}

	public void modificarSede(String nombreCiudad, boolean sede) {
		Ciudad city = new Ciudad(nombreCiudad);
		city = (Ciudad) grafoEtiq.obtenerElemVertice(city);
		boolean sedeAnterior=city.esSede();
		city.setSede(sede);
		addToLog("\nModificacion de sede de: "+nombreCiudad+", valor de sede anterior: "+sedeAnterior+", nuevo valor de sede: "+sede);
	}

	public void caminoMasCorto(String origen, String destino) {
		Ciudad city0 = new Ciudad(origen);
		Ciudad city1 = new Ciudad(destino);
		System.out.println("Camino mas corto entre " + origen + " y " + destino + " es: \n"
				+ grafoEtiq.caminoMasCorto(city0, city1).toString());
	}
	
	public void caminoPesoEntre(String origen, String destino) {
		Ciudad city0 = new Ciudad(origen);
		Ciudad city1 = new Ciudad(destino);
		System.out.println("Camino mas corto entre " + origen + " y " + destino + " es: \n"
				+ grafoEtiq.entreDist(city0, city1, 500, 2000).toString());
	}
	
	

	public void caminoMenosCiudades(String origen, String destino) {
		Ciudad city0 = new Ciudad(origen);
		Ciudad city1 = new Ciudad(destino);
		System.out.println("Camino mas corto y con menos ciudades entre " + origen + " y " + destino + " es: \n"
				+ grafoEtiq.caminoMenosNodos(city0, city1).toString());
	}

	public void todosLosCaminos(String origen, String destino) {
		Ciudad city0 = new Ciudad(origen);
		Ciudad city1 = new Ciudad(destino);
		System.out.println("Caminos entre " + origen + " y " + destino + " es: \n"
				+ grafoEtiq.caminosEntre(city0, city1).toString());
	}

	public void caminoMasCortoPorCiudad(String origen, String destino, String medio) {
		Ciudad city0 = new Ciudad(origen);
		Ciudad city1 = new Ciudad(destino);
		Ciudad city2 = new Ciudad(medio);
		System.out.println("Camino mas corto entre " + origen + " y " + destino + " que pasa por : " + medio
				+ " es : \n" + grafoEtiq.caminoMasCortoPorCiudad(city0, city1, city2).toString());
	}

	public String debug() {
		return grafoEtiq.toString();
	}

	public void addToLog(String res) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true));
			bw.write(res);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}