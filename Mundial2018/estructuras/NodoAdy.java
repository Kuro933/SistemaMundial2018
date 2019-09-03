package estructuras;

public class NodoAdy {

	private NodoVert vertice;
	private NodoAdy sigAdy;
	private double etiqueta;
	
	public NodoAdy (){
		this.vertice = null;
		this.sigAdy  = null;
		this.etiqueta = 0;
	}
	
	public NodoAdy(NodoVert nodo, double etiq) {
		this.vertice = nodo;
		this.etiqueta = etiq;
	}
	
	public NodoVert getVertice() {
		return this.vertice;
	}
	
	public NodoAdy getSiguienteAdyacente () {
		return this.sigAdy;
	}
	
	public double getEtiqueta() {
		return this.etiqueta;
	}
	
	public void setVertice(NodoVert nodoVert) {
		this.vertice  = nodoVert;
	}
	
	public void setSiguienteAdyacente(NodoAdy nodoAdy) {
		this.sigAdy = nodoAdy;
	}
	
	public void setEtiqueta (double etiq) {
		this.etiqueta  = etiq;
	}
	
	
	
	
}
