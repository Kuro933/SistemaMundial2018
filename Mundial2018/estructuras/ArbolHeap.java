package estructuras;

public class ArbolHeap {
	private Comparable[] arr;
	private int ultimo;
	private static final int TAM = 50;

	public ArbolHeap() {
		this.arr = new Comparable[TAM];
		this.ultimo = 1;
	}

	public boolean insertar(Comparable elem) {
		boolean exito = false;
		if (this.ultimo < TAM) {
			arr[ultimo] = elem;
			if (ultimo != 1) {
				hacerSubir(ultimo);
			}
			ultimo++;
			exito = true;
		}
		return exito;
	}
	public boolean eliminarCima() {
		boolean exito;
		if (this.ultimo == 1) {
			exito = false;
		} else {
			this.arr[1] = this.arr[ultimo - 1];
			this.ultimo--;
			hacerBajar(1);
			exito = true;
		}
		return exito;
	}

	public Comparable recuperarCima() {
		return arr[1];
	}

	public void hacerSubir(int pos) {
		boolean salir = false;
		while (!salir) {
			Comparable n = arr[pos];
			Comparable padre = arr[pos / 2];
			if (n.compareTo(padre) > 0) {
				Comparable temp = n;
				arr[pos] = padre;
				arr[pos / 2] = temp;
				pos = pos / 2;
				if (pos == 1) {
					salir = true;
				}
			} else {
				salir = true;
			}
		}
	}



	private void hacerBajar(int pos) {
		int hijoMenor;
		Comparable temp = this.arr[pos];
		boolean salir = false;
		while (!salir) {
			hijoMenor = pos * 2;
			if (hijoMenor <= this.ultimo) {
				// temp tiene hijos(al menos uno)
				if (hijoMenor < this.ultimo) {
					// hijoMenor tiene hermano derecho
					if (this.arr[hijoMenor + 1].compareTo(this.arr[hijoMenor]) > 0) {
						hijoMenor++;

					}
				}
				if ((this.arr[hijoMenor].compareTo(temp)) > 0) {
					this.arr[pos] = this.arr[hijoMenor];
					pos = hijoMenor;
				} else {
					// el padre es menor que sus hijos
					salir = true;
				}
			} else {
				// hijo menor es hoja
				salir = true;
			}
		}
		this.arr[pos] = temp;

	}

	public boolean esVacio() {
		return ultimo == 1;
	}

}