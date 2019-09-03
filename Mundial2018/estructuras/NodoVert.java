package estructuras;

public class NodoVert<T> {
	
	private Object elemento;  
	private NodoVert<T> sigVert;
	private NodoAdy primerAdy;
	
	
	public NodoVert() {
		this.elemento = null;
		this.sigVert = null;
		this.primerAdy = null;
	}
	
	
	public NodoVert(Object elto,NodoVert<T> vert) {
		this.elemento=elto;
		this.sigVert=vert;
	}
	
	
	public Object getElemento() {
		return elemento;
	}
	
	
	public void setElemento(Object elemento) {
		this.elemento = elemento;
	}
	public NodoVert<T> getSigVer() {
		return this.sigVert;
	}
	public void setSigVer(NodoVert<T> ver) {
		this.sigVert = ver;
	}
	public NodoAdy getPrimerAdy() {
		return primerAdy;
	}
	public void setPrimerAdy(NodoAdy primerAdy) {
		this.primerAdy = primerAdy;
	}

	
}
