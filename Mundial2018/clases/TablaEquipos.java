package clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import estructuras.ArbolHeap;
import estructuras.Diccionario;
import estructuras.Lista;

public class TablaEquipos {
	private Diccionario dicc;
	private ArbolHeap arbH;
	private PartidoHashMap partidos;

	public TablaEquipos(Diccionario dicc, PartidoHashMap partidos) {
		this.dicc = dicc;
		this.partidos = partidos;
	}

	public boolean insertarEquipo(String nombrePais, String nombreDT, char grupo) {
		Equipo equipoNuevo = new Equipo(nombrePais, nombreDT, grupo);
		boolean exito = dicc.insertar(nombrePais, equipoNuevo);
		if (exito) {
			addToLog("Equipo agregado : " + equipoNuevo.toString() +  "\n");
		}
		return exito;

	}

	public void listarInformacionEquipo(String nombrePais) {
		Equipo eqAux = (Equipo) dicc.obtenerInformacion(nombrePais);
		System.out.println(eqAux.toString());
		boolean listaVacia = (eqAux.getEquiposEnfrentados()).esVacia();
		if (!listaVacia) {
			int i = 1;
			Lista lista = eqAux.getEquiposEnfrentados();
			while (i <= lista.longitud()) {
				Equipo aux = (Equipo) lista.recuperar(i);
				System.out.println("partido Nº: " + i +"\n" + partidos.obtenerPartido(nombrePais, aux.getSeleccion()).toString() + "\n");
				i++;
			}
		}

	}

	public void listarRango(String min, String max) {
		System.out
				.println("Rango : [ " + min + " , " + max + " ] \nListado de equipos: " + dicc.listarRango(min, max));
	}

	public void mostrarTablaEquipos() {
		System.out.println(dicc.toString());
	}

	public void eliminarEquipo(String nombrePais) {
		dicc.eliminar(nombrePais);
		addToLog("Se ha eliminado el equipo : "+nombrePais + "\n");
	}

	
	public String mostrarTablaPosiciones() {
		arbH = new ArbolHeap();
		Equipo eqAux;
		String posiciones = "";
		int puesto = 1;
		Lista listadoEquipos = listarEquipos();
		while (!listadoEquipos.esVacia()) {
			eqAux = (Equipo) listadoEquipos.recuperar(1);
			arbH.insertar(eqAux);
			listadoEquipos.eliminar(1);
		}
		while (!arbH.esVacio()) {
			eqAux = (Equipo) arbH.recuperarCima();
			arbH.eliminarCima();
			posiciones += puesto + " ) " + eqAux.getSeleccion() + " Puntos: " + eqAux.getPuntos() + " DifGol: "
					+ eqAux.getDiferenciaGoles() + "\n";
			puesto++;
		}
		return posiciones;
	}

	public boolean existeEquipo(String nombreEquipo) {
		return dicc.existeClave(nombreEquipo);
	}

	private Lista listarEquipos() {
		Lista equipos = new Lista();
		Lista listadoClaves = dicc.listarClaves();
		while (!listadoClaves.esVacia()) {
			Equipo eq = (Equipo) dicc.obtenerInformacion((String) listadoClaves.recuperar(1));
			equipos.insertar(eq, equipos.longitud() + 1);
			listadoClaves.eliminar(1);
		}
		return equipos;
	}

	public void agregarNuevoPartido(String equipo1, String equipo2, int golesEq1, int golesEq2) {
		Equipo eq1 = (Equipo) dicc.obtenerInformacion(equipo1);
		Equipo eq2 = (Equipo) dicc.obtenerInformacion(equipo2);
		int ganador=3,perdedor=0,empate=1;
		eq1.agregarEnfrentamiento(eq2);
		eq2.agregarEnfrentamiento(eq1);
		if (golesEq1 > golesEq2) {
			eq1.agregarResultado(golesEq1, golesEq2,ganador);
			eq2.agregarResultado(golesEq2, golesEq1,perdedor);
		} else {
			if (golesEq1 < golesEq2) {
				eq1.agregarResultado(golesEq1, golesEq2,perdedor);
				eq2.agregarResultado(golesEq2, golesEq1,ganador);
			} else {
				eq1.agregarResultado(golesEq1, golesEq2,empate);
				eq2.agregarResultado(golesEq2, golesEq1,empate);
			}
		}
	}

	public void listarEquiposConDifGolNeg() {
		Lista equipos = listarEquipos();
		Lista listado = new Lista();
		while (!equipos.esVacia()) {
			Equipo eqAux = (Equipo) equipos.recuperar(1);
			if (eqAux.getDiferenciaGoles() < 0) {
				listado.insertar(eqAux.getSeleccion(), 1);
			}
			equipos.eliminar(1);
		}
		System.out.println("Listado de equipos con dif gol negativa : " + listado.toString());

	}

	public void modificarNombreEq(String nombreEquipo, String nuevoNombre) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		dicc.modificarClave(nombreEquipo, nuevoNombre);
		eq.setSeleccion(nuevoNombre);
		addToLog("Modificacion de nombre de : "+nombreEquipo+" nuevo Nombre: "+nuevoNombre  +"\n");

	}

	public void modificarDt(String nombreEquipo, String nuevoDt) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		String dtAnterior = eq.getDirector();
		eq.setDirector(nuevoDt);
		addToLog("Modificacion de  DT de: " + nombreEquipo + ", DT anterior: " + dtAnterior + ", nuevo DT : "
				+ nuevoDt + "\n");
	}

	public void modificarGrupo(String nombreEquipo, char nuevoGrupo) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		char grupoAnterior = eq.getGrupo();
		eq.setGrupo(nuevoGrupo);
		addToLog("Modificacion de grupo de: " + nombreEquipo + ", grupo anterior: " + grupoAnterior
				+ ", nuevo grupo: " + nuevoGrupo + "\n");
	}

	public void modificarPuntos(String nombreEquipo, int puntos) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int puntosAnterior = eq.getPuntos();
		eq.setPuntos(puntos);
		addToLog("Modificacion de puntos de: " + nombreEquipo + ", puntos anteriorres: " + puntosAnterior
				+ ", nuevos puntos : " + puntos  + "\n");
	}

	public void modificarGolesFavor(String nombreEquipo, int golesFavor) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int golesAnterior = eq.getGolesAFavor();
		eq.setGolesAFavor(golesFavor);
		addToLog("Modificacion de goles favor de: " + nombreEquipo + ", goles favor anteriorres: " + golesAnterior
				+ ", nuevos golesFavor : " + golesFavor +  "\n");
	}

	public void modificarGolesContra(String nombreEquipo, int golesContra) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int golesAnterior = eq.getGolesAFavor();
		eq.setGolesContra(golesContra);
		addToLog("Modificacion de goles contra de: " + nombreEquipo + ", goles contra anteriorres: " + golesAnterior
				+ ", nuevos golesContra : " + golesContra + "\n");
	}

	public String debug() {
		return dicc.toString();
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