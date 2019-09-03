package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import estructuras.Cola;
import estructuras.Diccionario;
import estructuras.GrafoEtiq;
import utiles.TecladoIn;

public class Mundial2018 {

	public static void main(String[] args) {
		Diccionario dicc = new Diccionario();
		GrafoEtiq<Ciudad> grafo = new GrafoEtiq();
		MapaCiudades mapa = new MapaCiudades(grafo);
		PartidoHashMap partidos = new PartidoHashMap();
		TablaEquipos tabla = new TablaEquipos(dicc, partidos);
		int opcion = 0;

		cargaInicial(tabla, mapa, partidos);

		while (opcion != -1) {
			menu();
			System.out.print("ingrese la opcion deseada: ");
			opcion = TecladoIn.readInt();
			switch (opcion) {
			case 1:
				ABMCiudades(mapa);
				break;
			case 2:
				ABMEquipos(tabla);
				break;

			case 3:
				altaPartido(tabla, partidos);
				break;

			case 4:
				consultaEquipo(tabla);
				break;

			case 5:
				System.out.print("Ingrese nombre ciudad: ");
				String nombreCiudad = TecladoIn.readWord().toUpperCase();
				if (mapa.existeCiudad(nombreCiudad)) {
					mapa.listarInformacionCiudad(nombreCiudad);
				} else {
					System.out.println("la ciudad ingresada no existe en el sistema\n");
				}

				break;

			case 6:
				consultaViaje(mapa);
				break;
			case 7:
				System.out.println(tabla.mostrarTablaPosiciones());
				break;

			case 8:
				System.out.println("\n Equipos :\n" + tabla.debug());
				System.out.println("\n Ciudades :\n" + mapa.debug());
				System.out.println("\n Partidos :\n" + partidos.debug());
				break;
			}

		}
		
		String cadFinal="Fin Ejecucion Sistema...Estructuras:  \n";
		cadFinal+="Equipos:"+tabla.debug();
		cadFinal+="Ciudades:"+mapa.debug();
		cadFinal+="Partidos:"+partidos.debug();
		cadFinal+="Tabla Posiciones:"+tabla.mostrarTablaPosiciones();
		addToLog(cadFinal);
		
	}

	public static void menu() {
		System.out.println(" --------------------------------------------------------");
		System.out.println("| Este es el menu de opciones del Sistema para el Mundial|");
		System.out.println("| de futbol realizado en Rusia 2018, por favor seleccione|");
		System.out.println("|                la opcion  deseada                      |");
		System.out.println(" --------------------------------------------------------\n");

		System.out.println(" --------------------------------------------------------");
		System.out.println("|                    Opciones                            |");
		System.out.println("|       1_ Alta, baja o modificacion de ciudades         |");
		System.out.println("|       2_ Alta, baja o modificacion de equipos          |");
		System.out.println("|       3_ Alta  de partidos                             |");
		System.out.println("|       4_ Consultar informacion sobre equipos           |");
		System.out.println("|       5_ Consultar informacion acerca de una ciudad    |");
		System.out.println("|       6_ Consultar informacion sobre viajes            |");
		System.out.println("|       7_ Obtener la tabla de posiciones                |");
		System.out.println("|       8_ mostrar Sistema                               |");
		System.out.println("|     para salir del sistema por favor ingrese -1        |");
		System.out.println(" --------------------------------------------------------");

	}

	public static void cargaInicial(TablaEquipos tabla, MapaCiudades mapa, PartidoHashMap partidos) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			br = new BufferedReader(new FileReader("cargaInicial.txt"));
			String cadena = br.readLine();
			Cola q = new Cola();
			while (cadena != null) {
				char tipoCarga = cadena.charAt(0);
				String cad = "";
				int i = 2;
				while (cadena.charAt(i) != '.') {
					char caracterActual = cadena.charAt(i);
					if (caracterActual != ';') {
						cad += caracterActual;
					} else {
						q.poner(cad);
						cad = "";
					}
					i++;
				}

				q.poner(cad);

				switch (tipoCarga) {
				case 'E':
					cargarEquipo(q, tabla);
					break;
				case 'C':
					cargarCiudad(q, mapa);
					break;
				case 'R':
					cargarRuta(q, mapa);
					break;
				case 'P':
					cargarPartido(q, partidos, tabla);
					break;
				}

				cadena = br.readLine();
			}

			if (br != null)
				br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void cargarCiudad(Cola info, MapaCiudades mapa) {
		String nombreCiudad = (String) info.obtenerFrente();
		info.sacar();
		double superficie = Double.parseDouble(info.obtenerFrente().toString());
		info.sacar();
		int cantHabitantes = Integer.parseInt(info.obtenerFrente().toString());
		info.sacar();
		boolean sede = (info.obtenerFrente().toString().equals("TRUE"));
		info.sacar();
		mapa.insertarCiudad(nombreCiudad, superficie, cantHabitantes, sede);

	}

	public static void cargarEquipo(Cola info, TablaEquipos tabla) {
		String nombreEquipo = (String) info.obtenerFrente();
		info.sacar();
		String nombreDt = (String) info.obtenerFrente();
		info.sacar();
		char grupo = info.obtenerFrente().toString().charAt(0);
		info.vaciar();
		tabla.insertarEquipo(nombreEquipo, nombreDt, grupo);
	}

	public static void cargarPartido(Cola info, PartidoHashMap partidos, TablaEquipos tabla) {
		String eq1 = (String) info.obtenerFrente();
		info.sacar();
		String eq2 = (String) info.obtenerFrente();
		info.sacar();
		String ciudad = (String) info.obtenerFrente();
		info.sacar();
		String fase = (String) info.obtenerFrente();
		info.sacar();
		int golesEq1 = Integer.parseInt(info.obtenerFrente().toString());
		info.sacar();
		int golesEq2 = Integer.parseInt(info.obtenerFrente().toString());
		info.sacar();
		partidos.insertarPartido(eq1, eq2, ciudad, fase, golesEq1, golesEq2);
		tabla.agregarNuevoPartido(eq1, eq2, golesEq1, golesEq2);

	}

	public static void cargarRuta(Cola q, MapaCiudades mapa) {
		String ciudadOrigen = ((String) q.obtenerFrente()).toUpperCase();
		q.sacar();
		String ciudadDestino = ((String) q.obtenerFrente()).toUpperCase();
		q.sacar();
		double distancia = Double.parseDouble(q.obtenerFrente().toString());
		q.sacar();
		mapa.insertarRuta(ciudadOrigen, ciudadDestino, distancia);
	}

	public static void ABMCiudades(MapaCiudades mapa) {
		System.out.println("1_Agregar Ciudad");
		System.out.println("2_eliminar ciudad");
		System.out.println("3_ Modificar Ciudad");
		System.out.println("4_ agregar Ruta entre ciudad A y ciudad B");

		int opcion = TecladoIn.readInt();
		String nombreCiudad;
		switch (opcion) {
		case 1:
			System.out.println("Ingrese nombre de ciudad");
			nombreCiudad = TecladoIn.readLine().toUpperCase();
			if (!mapa.existeCiudad(nombreCiudad)) {

				System.out.println("Ingrese superficie aproximada");
				double sup = TecladoIn.readDouble();
				System.out.println("Ingrese cantidad de habitantes");
				int cantHabit = TecladoIn.readInt();
				System.out.println("La ciudad es sede del mundial?(s|n)");
				char respuesta = TecladoIn.readLineNonwhiteChar();
				boolean sede = false;
				if (respuesta == 's') {
					sede = true;
				}
				mapa.insertarCiudad(nombreCiudad, sup, cantHabit, sede);
			} else {
				System.out.println("La ciudad ya existe");
			}
			break;
		case 2:
			System.out.println("ingrese nombre ciudad");
			nombreCiudad = TecladoIn.readLine().toUpperCase();
			if (mapa.existeCiudad(nombreCiudad)) {

				mapa.eliminarCiudad(nombreCiudad);
				System.out.println("Ciudad eliminada");
			}
			break;

		case 3:
			System.out.println("Ingrese la ciudad a modificar");
			String ciudadModif = TecladoIn.readLine().toUpperCase();
			if (mapa.existeCiudad(ciudadModif)) {
				System.out.println("Seleccione el atributo que desea modificar");
				System.out.println("1_ Nombre Ciudad");
				System.out.println("2_ Superficie Aprox");
				System.out.println("3_ Cantidad de Habit");
				System.out.println("4_ Es sede?");
				int opcion2 = TecladoIn.readInt();
				switch (opcion2) {
				case 1:
					System.out.println("Ingrese el nuevo nombre");
					String nuevoNombre = TecladoIn.readLine().toUpperCase();
					mapa.modificarNombre(ciudadModif, nuevoNombre);
					break;
				case 2:
					System.out.println("Ingrese la nueva superficie");
					double sup = TecladoIn.readDouble();
					mapa.modificarSuperficie(ciudadModif, sup);
					break;
				case 3:
					System.out.println("Ingrese la cant habitantes");
					int cantHabit = TecladoIn.readInt();
					mapa.modificarCantHabit(ciudadModif, cantHabit);
					break;
				case 4:
					System.out.println("¿ La ciudad es sede? (s|n)");
					char resp = TecladoIn.readLineNonwhiteChar();
					if (resp == 's') {
						mapa.modificarSede(ciudadModif, true);
					} else {
						mapa.modificarSede(ciudadModif, false);
					}
					break;

				}
			} else {
				System.out.println("No existe la ciudad ingresada");
			}
			break;
		case 4:
			System.out.println("Ingrese el nombre de la ciudad origen");
			String ciudadOrigen = TecladoIn.readLine().toUpperCase();
			System.out.println("Ingrese el nombre de la ciudad destino");
			String ciudadDestino = TecladoIn.readLine().toUpperCase();
			System.out.println("Ingrese la cantidad de km que tendra la ruta deseada");
			double km = TecladoIn.readDouble();
			mapa.insertarRuta(ciudadOrigen, ciudadDestino, km);
			break;
		}
	}

	public static void ABMEquipos(TablaEquipos tabla) {

		System.out.println("1_ Agregar equipo");
		System.out.println("2_ Eliminar equipo");
		System.out.println("3_ Modificar equipo");
		String nombreSeleccion;
		int puntos, golesFavor, golesContra;
		int opcion = TecladoIn.readInt();
		switch (opcion) {
		case 1:

			System.out.println("Ingrese el nombre del equipo ");
			nombreSeleccion = TecladoIn.readWord();
			nombreSeleccion = nombreSeleccion.toUpperCase();
			System.out.println("Ingrese el nombre del DT");
			String nombreDt = TecladoIn.readWord();
			nombreDt = nombreDt.toUpperCase();
			System.out.println("Ingrese el grupo ");
			char grupo = TecladoIn.readChar();
			tabla.insertarEquipo(nombreSeleccion, nombreDt, grupo);
			break;

		case 2:
			System.out.println("Ingrese el nombre de la seleccion que desea eliminar");
			nombreSeleccion = TecladoIn.readLine().toUpperCase();
			if (tabla.existeEquipo(nombreSeleccion)) {
				tabla.eliminarEquipo(nombreSeleccion);
			} else {
				System.out.println("El equipo ingresado no existe en el sistema");
			}
			break;
		case 3:
			System.out.println("Ingrese el equipo a modificar");
			String seleccion = TecladoIn.readLine().toUpperCase();
			if (tabla.existeEquipo(seleccion)) {
				System.out.println("Seleccione el atributo que desea modificar");
				System.out.println("1_ Nombre equipo");
				System.out.println("2_ Nombre DT");
				System.out.println("3_ Grupo");
				System.out.println("4_ Puntos");
				System.out.println("5_ goles Favor");
				System.out.println("6_ Goles Contra");
				int opcion2 = TecladoIn.readInt();
				switch (opcion2) {
				case 1:
					System.out.println("Ingrese el nuevo nombre de equipo");
					String nuevoNombre = TecladoIn.readWord().toUpperCase();
					tabla.modificarNombreEq(seleccion, nuevoNombre);
					break;
				case 2:
					System.out.println("Ingrese el nombre del DT");
					String nuevoDt = TecladoIn.readWord().toUpperCase();
					tabla.modificarDt(seleccion, nuevoDt);
					break;
				case 3:
					System.out.println("Ingrese el grupo");
					char nuevoGrupo = TecladoIn.readChar();
					tabla.modificarGrupo(seleccion, nuevoGrupo);
					break;
				case 4:
					System.out.println("Ingrese los puntos");
					puntos = TecladoIn.readInt();
					tabla.modificarPuntos(seleccion, puntos);
					break;
				case 5:
					System.out.println("Ingrese los goles a favor");
					golesFavor = TecladoIn.readInt();
					tabla.modificarGolesFavor(seleccion, golesFavor);
					break;
				case 6:
					System.out.println("Ingrese los goles en contra");
					golesContra = TecladoIn.readInt();
					tabla.modificarGolesContra(seleccion, golesContra);
					break;
				}
			} else {
				System.out.println("El equipo ingresado no existe en el sistema\n");
			}
			break;

		}
	}

	public static void altaPartido(TablaEquipos tabla, PartidoHashMap partidos) {
		System.out.println("Ingrese nombre equipo 1 ");
		String eq1 = TecladoIn.readWord().toUpperCase();
		if (tabla.existeEquipo(eq1)) {
			System.out.println("Ingrese nombre equipo 2 ");
			String eq2 = TecladoIn.readWord().toUpperCase();
			if (tabla.existeEquipo(eq2)) {

				System.out.println("Ingrese goles hechos por : " + eq1);
				int golesEq1 = TecladoIn.readInt();
				System.out.println("Ingrese goles hechos por : " + eq2);
				int golesEq2 = TecladoIn.readInt();

				System.out.println("Ingrese la ciudad donde se disputo el partido ");
				String ciudad = TecladoIn.readLine();
				System.out.println("Ingrese la fase por la que se jugo (GRUPO/OCTAVOS/CUARTOS/SEMIFINAL/FINAL)");
				String fase = TecladoIn.readWord();
				partidos.insertarPartido(eq1, eq2, ciudad, fase, golesEq1, golesEq2);
				tabla.agregarNuevoPartido(eq1, eq2, golesEq1, golesEq2);
			} else {
				System.out.println("No existe el equipo 2");
			}
		} else {
			System.out.println("No existe el equipo 1");
		}
	}

	public static void consultaEquipo(TablaEquipos tabla) {
		int opcion;

		System.out.println("1_ Ingrese el nombre de la seleccion de la cual desea informacion");
		System.out.println("2_ Ingresar dos selecciones para listar en rango lexicografico los equipos");
		System.out.println("3_ Listar los equipos que posean una diferencia de goles negativas");
		System.out.print("Ingrese la opcion  deseada:  ");
		opcion = TecladoIn.readInt();
		switch (opcion) {
		case 1:
			System.out.print("Nombre de seleccion deseada: ");
			String nombreSel = TecladoIn.readWord().toUpperCase();
			if (tabla.existeEquipo(nombreSel)) {
				tabla.listarInformacionEquipo(nombreSel);
			} else {
				System.out.println("Dicha seleccion no existe en el sistema");
			}

			break;

		case 2:
			System.out.println("Ingrese nombre MIN (nombre menor lexicograficamente)");
			String nombreSel1 = TecladoIn.readWord().toUpperCase();
			System.out.println("Ingrese nombre MAX (nombre mayor lexicograficamente)");
			String nombreSel2 = TecladoIn.readWord().toUpperCase();
			tabla.listarRango(nombreSel1, nombreSel2);
			break;

		case 3:
			tabla.listarEquiposConDifGolNeg();
		}

	}

	public static void consultaViaje(MapaCiudades mapa) {
		int opcion;
		System.out.println("Ingrese la ciudad de partida");
		String ciudadOrigen = TecladoIn.readLine().toUpperCase();
		if(mapa.existeCiudad(ciudadOrigen)) {
			System.out.println("Ingrese la ciudad a la cual desea dirigirse");
			String ciudadDestino = TecladoIn.readLine().toUpperCase();
			if(mapa.existeCiudad(ciudadDestino)) {
				System.out.println("Las siguientes consultas estan a su disposicion");
				System.out.println("1_ Obtener el camino mas corto (en km) hacia donde desea dirigirse");
				System.out.println("2_ Obtener el camino hacia la ciudad de destino que pase por la menor cantidad de ciudades");
				System.out.println("3_ Obtener todas las posibles rutas hacia su destino");
				System.out.println("4_ Obtener el camino mas corto (en km)  hacia su destino pasando por una ciudad de camino");
				System.out.print("Ingrese la opcion deseada: ");
				opcion = TecladoIn.readInt();
				switch(opcion) {
				case 1:
					mapa.caminoMasCorto(ciudadOrigen, ciudadDestino);
					break;
				case 2:
					mapa.caminoMenosCiudades(ciudadOrigen, ciudadDestino);
					break;
				case 3:
					mapa.todosLosCaminos(ciudadOrigen, ciudadDestino);
					break;
				case 4:
					System.out.println("Ingrese la ciudad de escala en el recorrido");
					String ciudadEscala = TecladoIn.readLine().toUpperCase();
					if(mapa.existeCiudad(ciudadEscala)) {
						mapa.caminoMasCortoPorCiudad(ciudadOrigen, ciudadDestino, ciudadEscala);
					}else {
						System.out.println("Dicha ciudad no existe en el sistema");
					}
					break;
				case 5:
					mapa.caminoPesoEntre(ciudadOrigen, ciudadDestino);
					
				}
				
			}else {
				System.out.println("Ciudad de destino no se encuentra en el sistema");
			}
		}else {
			System.out.println("Ciudad de partida no se encuentra en el sistema");			
		}
		
	}

	public static void addToLog(String res) {
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
