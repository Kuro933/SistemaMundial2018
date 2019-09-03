package estructuras;

public class NodoList {
	private Object elem;
	private NodoList enlace;

	public NodoList(Object elemento, NodoList enl) {
		this.elem = elemento;
		this.enlace = enl;
	}

	public NodoList(Object elemento) {
		this.elem = elemento;
	}

	public Object getElemento() {
		return this.elem;
	}

	public void setElemento(Object elemento) {
		this.elem = elemento;
	}

	public NodoList getEnlace() {
		return this.enlace;
	}

	public void setEnlace(NodoList enl) {
		this.enlace = enl;
	}
}