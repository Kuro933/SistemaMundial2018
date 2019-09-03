package clases;

import estructuras.Lista;

public class Equipo implements Comparable{

	private String seleccion, director;
	private Lista equiposEnfrentados;
	private int golesAFavor, golesContra, puntos;
	private char grupo;
	
	
	public Equipo(String nombreSelec, String dt, char grupo) {
		this.seleccion = nombreSelec;
		this.director = dt;
		this.equiposEnfrentados = new Lista();
		this.golesAFavor = 0;
		this.golesContra = 0;
		this.puntos = 0;
		this.grupo  =  grupo;
	}

	public void agregarEnfrentamiento(Equipo contrario) {
		this.equiposEnfrentados.insertar(contrario, (equiposEnfrentados.longitud() + 1));
	}

	public void agregarResultado(int golesF, int golesC, int score) {
		this.golesAFavor += golesF;
		this.golesContra += golesC;
		this.puntos += score;
	}

	public int getDiferenciaGoles() {
		return (this.golesAFavor - this.golesContra);
	}

	// comienzo de metodos observacion y modificacion
	public void setSeleccion(String nombSelecc) {
		this.seleccion = nombSelecc;
	}

	public String getSeleccion() {
		return this.seleccion;
	}
	
	public Lista getEquiposEnfrentados() {
		return this.equiposEnfrentados;
	}

	public void setDirector(String dt) {
		this.director = dt;
	}

	public String getDirector() {
		return this.director;
	}

	public void setGolesAFavor(int goles) {
		this.golesAFavor = goles;
	}

	public int getGolesAFavor() {
		return this.golesAFavor;
	}

	public void setGolesContra(int goles) {
		this.golesContra = goles;
	}

	public int getGolesContra() {
		return this.golesContra;
	}

	public void setPuntos(int score) {
		this.puntos = score;
	}

	public int getPuntos() {
		return this.puntos;
	}
	
	public void setGrupo(char grup) {
		this.grupo=grup;
	}
	
	public char getGrupo() {
		return this.grupo;
	}

	// fin de metodos observacion y modificacion

	public int compareTo(Object equipo) {
		int resp;
		Equipo eqComp = (Equipo) equipo;

		if (this.puntos == eqComp.getPuntos()) {
			if (this.getDiferenciaGoles() == eqComp.getDiferenciaGoles()) {
				resp = this.seleccion.compareTo(eqComp.getSeleccion());
			} else if (this.getDiferenciaGoles() > eqComp.getDiferenciaGoles()) {
				resp = 1;
			} else {
				resp = -1;
			}
		} else if (this.puntos > eqComp.getPuntos()) {
			resp = 1;
		} else {
			resp = -1;
		}

		return resp;
	}

	public boolean equals(Equipo equipo) {
		return this.seleccion.equalsIgnoreCase(equipo.getSeleccion());
	}
	
	

	public String toString() {
		String s = "";
		
		s += "Seleccion: " + this.seleccion +  "\nDirector Tecnico: " + this.director + "\nPuntos: " + this.puntos
				+  "\nGoles a Favor: "  + this.golesAFavor +  "\nGoles en contra: " +  this.golesContra;
				
		return s;
	}

}
