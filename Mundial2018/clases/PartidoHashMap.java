package clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import clases.PartidoClave;
import clases.Partido;
import clases.TablaEquipos;

public class PartidoHashMap {
	private HashMap partidos;
	private TablaEquipos tablaEquipos;

	public PartidoHashMap() {
		this.partidos = new HashMap();
	}

	public boolean insertarPartido(String eq1, String eq2, String ciudad, String fase, int golesEq1, int golesEq2) {

		Partido partidoNuevo;
		PartidoClave clave = new PartidoClave(eq1, eq2);
		boolean exito = false;
		boolean existe = partidos.containsKey(clave);
		if (!existe) {
			partidoNuevo = new Partido(eq1, eq2, ciudad, fase, golesEq1, golesEq2);
			partidos.put(clave, partidoNuevo);
			exito = true;
			addToLog("\nPartido agregado: " + partidoNuevo.toString());

		}else {
			System.out.println("\nError! El partido ya existe\n");
		}
		return exito;
	}

	public boolean existePartido(String e1, String e2) {
		PartidoClave clave = new PartidoClave(e1, e2);
		return partidos.containsKey(clave);
	}

	public Partido obtenerPartido(String eq1, String eq2) {
		PartidoClave clave = new PartidoClave(eq1, eq2);
		Partido partidoAux = (Partido) partidos.get(clave);
		return partidoAux;
	}

	public String debug() {
		String cad = "";
		Object[] arr = partidos.values().toArray();
		Object[] keys = partidos.keySet().toArray();
		for (int i = 0; i < arr.length; i++) {
			cad += "\nClave: " + keys[i].toString() + ".\n Partido: " + arr[i].toString() + "\n";
		}
		return cad;
	}

	public void addToLog(String res) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true));
			bw.write(res);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}