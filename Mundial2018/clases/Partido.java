package clases;

public class Partido {

	private String equipo1, equipo2, ciudad, fase;
	private int golesEq1, golesEq2;

	public Partido() {

	}

	public Partido(String eq1, String eq2, String ciudad, String fase, int golesEq1, int golesEq2) {
		this.equipo1 = eq1;
		this.equipo2 = eq2;
		this.ciudad = ciudad;
		this.fase = fase;
		this.golesEq1 = golesEq1;
		this.golesEq2 = golesEq2;
	}

	public String getEquipo1() {
		return this.equipo1;
	}

	public String getEquipo2() {
		return this.equipo2;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public String getFase() {
		return this.fase;
	}

	public int getGolesEq1() {
		return this.golesEq1;
	}

	public int getGolesEq2() {
		return this.golesEq2;
	}

	public void setEquipo1(String eq1) {
		this.equipo1 = eq1;
	}

	public void setEquipo2(String eq2) {
		this.equipo2 = eq2;
	}

	public void setCiudad(String city) {
		this.ciudad = city;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public void setGolesE1(int golesE1) {
		this.golesEq1 = golesE1;
	}

	public void setGolesE2(int golesE2) {
		this.golesEq2 = golesE2;
	}

	public String toString() {
		String s = "";
		s += this.equipo1 + ": " + this.golesEq1 + " VS  " + this.equipo2 + " : " + this.golesEq2 + "\nCiudad: "
				+ this.ciudad + "\nFase: " + this.fase;
		return s;
	}
}