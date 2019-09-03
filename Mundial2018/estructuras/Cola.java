package estructuras;

public class Cola {
	private static final int TAM = 50;
	private int frente;
	private int end;
	private Object[] arrCola;

	public Cola() {
		this.frente = 0;
		this.end = 0;
		this.arrCola = new Object[TAM];
	}

	/* Metodos */
	public boolean poner(Object nuevoElem) {
		boolean exito = false;
		if (!((this.end + 1) == this.frente)) {
			this.arrCola[end] = nuevoElem;
			end = (end + 1) % TAM;
			exito = true;
		}
		return exito;
	}

	public boolean sacar() {
		boolean exito = false;
		if (!(this.esVacia())) {
			this.frente = (this.frente + 1) % TAM;
			exito = true;
		}
		return exito;
	}

	public boolean esVacia() {
		boolean vacia = false;
		if (this.end == this.frente) {
			vacia = true;
		}
		return vacia;
	}

	public Object obtenerFrente() {
		Object elemFrente = null;
		if (!esVacia()) {
			elemFrente = arrCola[frente];
		}
		return elemFrente;
	}

	public void vaciar() {
		while (!esVacia()) {
			this.sacar();
		}
	}

	public Cola clonar() {
		Cola colaClon = new Cola();
		colaClon.frente = this.frente;
		colaClon.end = this.end;
		if (!esVacia()) {
			int i = this.frente;
			while (this.arrCola[i] != null) {
				colaClon.arrCola[i] = this.arrCola[i];
				i = (i + 1) % TAM;
			}
		}
		return colaClon;
	}

	public String toString() {
		String cad = "[ ";
		int i = this.frente;
		while (this.arrCola[i] != null) {
			cad += ", " + arrCola[i];
			i = (i + 1) % TAM;
		}
		cad += " ]";
		return cad;
	}
}