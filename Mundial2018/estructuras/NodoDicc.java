package estructuras;

public class NodoDicc {
	private Comparable clave;
	private Object dato;
	private int altura;
	private NodoDicc izq;
	private NodoDicc der;

	public NodoDicc() {
		this.altura = 0;
		this.izq = null;
		this.der = null;
	}

	public NodoDicc(Comparable clave, Object dato) {
		this.clave = clave;
		this.dato = dato;
	}

	public Object getDato() {
		return dato;
	}

	public void setDato(Object nuevo) {
		dato = nuevo;
	}

	public int getAltura() {
		return altura;
	}

	public void recalcularAltura() {
		int altIzq,altDer;
		
		if(this.izq!=null){
			altIzq = this.izq.getAltura();
		}else{
			altIzq = -1;
		}
		if(this.der!=null){
			altDer = this.der.getAltura();
		}else{
			altDer = -1;
		}
		
		this.altura = (Math.max(altIzq, altDer))+1;
	}


	public NodoDicc getIzq() {
		return this.izq;
	}


	public void setIzq(NodoDicc izquierdo) {
		this.izq = izquierdo;
		if (izquierdo != null) {
			izquierdo.recalcularAltura();
		}
		this.recalcularAltura();
	}

	public NodoDicc getDer() {
		return this.der;
	}

	public void setDer(NodoDicc derecho) {
		der = derecho;
		if (derecho != null) {
			derecho.recalcularAltura();
		}
		this.recalcularAltura();
	}

	public void setClave(Comparable clave) {
		this.clave = clave;
	}

	public Comparable getClave() {
		return this.clave;
	}

}