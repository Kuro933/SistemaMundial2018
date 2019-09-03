package clases;

public class Ciudad {
	private String nombreCiudad;
	private double superficieAprox;
	private int cantHabitantes;
	private boolean sede;

	public Ciudad(String nombre) {
		this.nombreCiudad = nombre;
	}

	public Ciudad(String nombre, double superficie, int cantHabit, boolean sede) {
		this.nombreCiudad = nombre;
		this.superficieAprox = superficie;
		this.cantHabitantes = cantHabit;
		this.sede = sede;
	}

	public String getNombreCiudad() {
		return this.nombreCiudad;
	}

	public double getSuperficieAprox() {
		return this.superficieAprox;
	}

	public int getCantHabitantes() {
		return this.cantHabitantes;
	}

	public boolean esSede() {
		return this.sede;
	}

	public void setNombre(String nombre) {
		this.nombreCiudad = nombre;
	}

	public void setSuperficie(double sup) {
		this.superficieAprox = sup;
	}

	public void setCantHabit(int cantH) {
		this.cantHabitantes = cantH;
	}

	public void setSede(boolean sede) {
		this.sede = sede;
	}

	public boolean equals(Object city) {
		return ((this.nombreCiudad.equals(((Ciudad) city).nombreCiudad)));
	}

	public String toStringCompleto() {
		String inf = "";
		inf += "Informacion Ciudad \n Nombre: " + this.nombreCiudad + "\n Superficie: " + this.superficieAprox
				+ "\n Cantidad Habitantes: " + this.cantHabitantes + "\n Sede: " + this.sede;
		return inf;
	}

	public String toString() {
		return this.nombreCiudad;
	}

}