package estructuras;

public class Lista {
	private NodoList cabecera;
	private int longitud;

	public Lista() {
		this.cabecera = null;
		this.longitud = 0;
	}

	public boolean insertar(Object nuevoElem, int pos) {
		boolean exito;
		if (pos > 0 || pos <= longitud + 1) {

			if (pos == 1) {
				this.cabecera = new NodoList(nuevoElem, this.cabecera);
			} else {
				int i = 1;
				NodoList aux = this.cabecera;
				while (i < pos - 1) {
					aux = aux.getEnlace();
					i++;
				}
				NodoList nuevoNodo = new NodoList(nuevoElem);
				nuevoNodo.setEnlace(aux.getEnlace());
				aux.setEnlace(nuevoNodo);
			}
			this.longitud += 1;
			exito = true;
		} else {
			exito = false;
		}
		return exito;
	}

	public boolean eliminar(int pos) {
		boolean exito;
		if (pos < 1 || pos > this.longitud + 1 || this.esVacia()) {
			exito = false;
		} else {
			if (pos == 1) {
				this.cabecera = this.cabecera.getEnlace();
			} else {
				NodoList aux = this.cabecera;
				int i = 1;
				while (i < pos - 1) {
					aux = aux.getEnlace();
					i++;
				}
				aux.setEnlace(aux.getEnlace().getEnlace());
			}
			this.longitud -= 1;
			exito = true;
		}
		return exito;
	}

	public Object recuperar(int pos) {
		Object elemento;
		if (esVacia() || pos < 1 || pos > longitud()) {
			elemento = null;
		} else {
			if (pos == 1) {
				elemento = this.cabecera.getElemento();
			} else {
				int i = 0;
				NodoList aux = this.cabecera;
				while (i < pos - 1) {
					aux = aux.getEnlace();
					i++;
				}
				elemento = aux.getElemento();
			}
		}

		return elemento;
	}

	public int localizar(Object elem) {
		int pos;
		if (esVacia()) {
			pos = -1;
		} else {
			pos = 1;
			NodoList aux = this.cabecera;
			boolean encontrado = false;
			while (!encontrado && pos <= this.longitud) {
				if (aux.getElemento().equals(elem)) {
					encontrado = true;
				} else {
					aux = aux.getEnlace();
					pos++;
				}
			}
			if (!encontrado) {
				pos = -1;
			}
		}
		return pos;
	}

	public int longitud() {
		return this.longitud;
	}

	public boolean esVacia() {
		boolean vacia = false;
		if (this.cabecera == null) {
			vacia = true;
		}
		return vacia;
	}

	public void vaciar() {
		this.cabecera = null;
	}

	public String toString() {
		String cadena = "";
		if (this.cabecera == null) {
			cadena = "Lista vacia";
		} else {
			cadena = "[";
			NodoList aux = this.cabecera;
			while (aux != null) {
				cadena += aux.getElemento();
				aux = aux.getEnlace();
				if (aux != null) {
					cadena += ",";
				}
			}
			cadena += "]";
		}
		return cadena;
	}

	public NodoList cloneNodos(NodoList nodo) {
		NodoList nuevoNodo;
		if (nodo.getEnlace() == null) {
			nuevoNodo = new NodoList(nodo.getElemento());
		} else {
			nuevoNodo = new NodoList(nodo.getElemento(), cloneNodos(nodo.getEnlace()));
		}
		return nuevoNodo;
	}

	public Lista clone() {
		Lista listaClon = new Lista();
		listaClon.cabecera = cloneNodos(this.cabecera);
		listaClon.longitud = this.longitud;
		return listaClon;
	}
}