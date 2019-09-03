package estructuras;

import estructuras.NodoDicc;

public class Diccionario {

	private NodoDicc raiz;

	public Diccionario() {
		raiz = null;
	}

	public boolean insertar(Comparable clave, Object dato) {
		// System.out.println(" inserta " + clave);
		boolean exito = true;
		if (this.raiz == null) {
			this.raiz = new NodoDicc(clave, dato);
		} else {
			exito = insertarAux(this.raiz, clave, dato, this.raiz);
		}
		this.raiz.recalcularAltura();
		// System.out.println(toString());
		// System.out.println(" =============================");
		return exito;
	}

	private boolean insertarAux(NodoDicc nodo, Comparable clave, Object dato, NodoDicc padre) {

		boolean exito = true;
		if (clave.compareTo(nodo.getClave()) == 0) {
			exito = false;
		} else {

			if (clave.compareTo(nodo.getClave()) < 0) {
				if (nodo.getIzq() != null) {
					exito = insertarAux(nodo.getIzq(), clave, dato, nodo);
				} else {
					nodo.setIzq(new NodoDicc(clave, dato));
				}
			} else {
				if (nodo.getDer() != null) {
					exito = insertarAux(nodo.getDer(), clave, dato, nodo);
				} else {
					nodo.setDer(new NodoDicc(clave, dato));
				}
			}

			nodo.recalcularAltura();
			this.balancearArbol(nodo, padre);
		}
		return exito;
	}

	public boolean eliminar(Comparable clave) {
		boolean exito = false;
		// elimina el elemento que se indica
		if (!this.esVacio()) {
			if (this.raiz.getClave().compareTo(clave) == 0) {
				if (this.raiz.getDer() == null && this.raiz.getIzq() == null) {
					this.raiz = null;
				} else if (this.raiz.getDer() != null && this.raiz.getIzq() == null) {
					this.raiz = this.raiz.getDer();
				} else if (this.raiz.getIzq() != null && this.raiz.getDer() == null) {
					this.raiz = this.raiz.getIzq();
				}
			} else if (this.raiz.getClave().compareTo(clave) > 0) {
				exito = eliminarAux(this.raiz.getIzq(), this.raiz, clave);
			} else {
				exito = eliminarAux(this.raiz.getDer(), this.raiz, clave);
			}
		}
		return exito;
	}

	private boolean eliminarAux(NodoDicc nodo, NodoDicc padre, Comparable clave) {
		// revisa los casos que sea una hoja, que tenga 1 solo hijo o que tenga
		// 2 hijos
		boolean exito = false;
		if (nodo != null) {
			if (nodo.getClave().compareTo(clave) == 0) {

				if (esCaso3(nodo)) {
					ejecutarCaso3(nodo);
				} else if (ejecutarCaso2(nodo, padre)) {
					exito = true;
				} else if (ejecutarCaso1(nodo, padre)) {
					exito = true;
				}
			} else {
				if (nodo.getClave().compareTo(clave) > 0) {
					exito = eliminarAux(nodo.getIzq(), nodo, clave);
				} else {
					exito = eliminarAux(nodo.getDer(), nodo, clave);
				}
			}
		}

		this.balancearArbol(nodo, padre);
		return exito;
	}

	private void balancearArbol(NodoDicc nodo, NodoDicc padre) {

		if (this.balance(nodo) == 2) {
			if (this.balance(nodo.getIzq()) < 0) {
				rotacionDobleIzqDer(nodo, padre);
			} else if (nodo == this.raiz) {
				this.raiz = rotacionSimpleDer(nodo);
				this.raiz.recalcularAltura();
			} else {
				NodoDicc nuevo = rotacionSimpleDer(nodo);
				if (padre.getIzq() == nodo) {
					padre.setIzq(nuevo);
				} else {
					padre.setDer(nuevo);
				}
			}

		} else if (this.balance(nodo) == -2) {
			if (this.balance(nodo.getDer()) > 0) {
				rotacionDobleDerIzq(nodo, padre);
			} else if (nodo == this.raiz) {
				this.raiz = rotacionSimpleIzq(nodo);
				this.raiz.recalcularAltura();
			} else {
				NodoDicc nuevo = rotacionSimpleIzq(nodo);
				if (padre.getIzq() == nodo) {
					padre.setIzq(nuevo);
				} else {
					padre.setDer(nuevo);
				}
			}
		}

	}

	private int balance(NodoDicc nodo) {
		int balance = 0, izq = 0, der = 0;

		if (nodo.getIzq() != null) {
			izq = nodo.getIzq().getAltura();
		} else {
			izq = -1;
		}
		if (nodo.getDer() != null) {
			der = nodo.getDer().getAltura();
		} else {
			der = -1;
		}

		balance = (izq - der);

		return balance;
	}

	private NodoDicc rotacionSimpleDer(NodoDicc nodo) {
		NodoDicc hijo = nodo.getIzq();
		NodoDicc temp = hijo.getDer();
		hijo.setDer(nodo);
		nodo.setIzq(temp);

		return hijo;
	}

	private NodoDicc rotacionSimpleIzq(NodoDicc nodo) {
		NodoDicc hijo = nodo.getDer();
		NodoDicc temp = hijo.getIzq();
		hijo.setIzq(nodo);
		nodo.setDer(temp);

		return hijo;
	}

	private void rotacionDobleDerIzq(NodoDicc r, NodoDicc padre) {
		r.setDer(rotacionSimpleDer(r.getDer()));
		if (r == this.raiz) {
			this.raiz = rotacionSimpleIzq(r);
		} else {
			NodoDicc nuevaRaiz = rotacionSimpleIzq(r);
			if (padre.getIzq() == r) {
				padre.setIzq(nuevaRaiz);
			} else {
				padre.setDer(nuevaRaiz);
			}
		}
	}

	private void rotacionDobleIzqDer(NodoDicc r, NodoDicc padre) {
		r.setIzq(rotacionSimpleIzq(r.getIzq()));
		if (r == this.raiz) {
			this.raiz = rotacionSimpleDer(r);
		} else {
			NodoDicc nuevaRaiz = rotacionSimpleDer(r);
			if (padre.getIzq() == r) {
				padre.setIzq(nuevaRaiz);
			} else {
				padre.setDer(nuevaRaiz);
			}
		}
	}

	public boolean esVacio() {
		return this.raiz == null;
	}

	private boolean ejecutarCaso1(NodoDicc nodo, NodoDicc padre) {
		boolean exito = false;
		if (nodo.getDer() == null && nodo.getIzq() == null) {
			if (padre.getIzq() == nodo) {
				padre.setIzq(null);
			} else {
				padre.setDer(null);
			}
			exito = true;
		}
		return exito;
	}

	private boolean ejecutarCaso2(NodoDicc nodo, NodoDicc padre) {
		boolean exito = false;
		if (nodo.getClave().compareTo(padre.getClave()) < 0) {
			if (esCaso2Izq(nodo)) {
				padre.setIzq(nodo.getIzq());
				exito = true;
			} else if (esCaso2Der(nodo)) {
				padre.setIzq(nodo.getDer());
				exito = true;
			}
		} else {
			if (esCaso2Izq(nodo)) {
				padre.setDer(nodo.getIzq());
				exito = true;
			} else if (esCaso2Der(nodo)) {
				padre.setDer(nodo.getDer());
				exito = true;
			}
		}
		return exito;
	}

	private void ejecutarCaso3(NodoDicc nodo) {
		NodoDicc siguiente = nodo.getIzq();
		NodoDicc padre = nodo;
		// mayor de menores

		if (siguiente.getDer() != null) {
			while (siguiente.getDer() != null) {

				padre = siguiente;
				siguiente = siguiente.getDer();
			}
		}
		nodo.setClave(siguiente.getClave());
		nodo.setDato(siguiente.getDato());
		if (esHoja(siguiente)) {
			ejecutarCaso1(siguiente, padre);
		} else {
			ejecutarCaso2(siguiente, padre);
		}

	}

	private boolean esCaso3(NodoDicc nodo) {
		return (nodo.getIzq() != null && nodo.getDer() != null);
	}

	private boolean esCaso2Izq(NodoDicc nodo) {
		return (nodo.getDer() == null && nodo.getIzq() != null);
	}

	private boolean esHoja(NodoDicc nodo) {
		return (nodo.getIzq() == null && nodo.getDer() == null);
	}

	private boolean esCaso2Der(NodoDicc nodo) {
		return (nodo.getDer() != null && nodo.getIzq() == null);
	}

	public boolean existeClave(Comparable clave) {
		boolean exito = false;
		NodoDicc nodo = this.raiz;

		if (nodo != null) {
			if (nodo.getClave().compareTo(clave) == 0) {
				exito = true;
			} else if (nodo.getClave().compareTo(clave) > 0) {
				exito = existeClaveAux(clave, nodo.getIzq());
			} else {
				exito = existeClaveAux(clave, nodo.getDer());
			}
		}

		return exito;
	}

	private boolean existeClaveAux(Comparable clave, NodoDicc nodo) {
		boolean exito = false;

		if (nodo != null) {
			if (nodo.getClave().compareTo(clave) == 0) {
				exito = true;
			} else if (nodo.getClave().compareTo(clave) > 0) {
				exito = existeClaveAux(clave, nodo.getIzq());
			} else {
				exito = existeClaveAux(clave, nodo.getDer());
			}
		}

		return exito;
	}

	public boolean modificarClave(Comparable claveAnt, Comparable claveNueva) {
		boolean exito = false;
		NodoDicc nodo = this.raiz;
		if (!existeClave(claveNueva)) {
			if (nodo.getClave().compareTo(claveNueva) == 0) {
				nodo.setClave(claveNueva);
				exito = true;
			} else if (nodo.getClave().compareTo(claveNueva) > 0) {
				exito = modificarClaveAux(claveAnt, claveNueva, nodo.getIzq());
			} else {
				exito = modificarClaveAux(claveAnt, claveNueva, nodo.getDer());
			}
		}
		return exito;
	}

	private boolean modificarClaveAux(Comparable anterior, Comparable nueva, NodoDicc nodo) {
		boolean exito = false;

		if (nodo != null) {
			if (nodo.getClave().compareTo(anterior) == 0) {
				nodo.setClave(nueva);
				exito = true;
			} else if (nodo.getClave().compareTo(nueva) > 0) {
				exito = modificarClaveAux(anterior, nueva, nodo.getIzq());
			} else {
				exito = modificarClaveAux(anterior, nueva, nodo.getDer());
			}
		}
		return exito;
	}

	public Object obtenerInformacion(Comparable clave) {
		Object dato = null;
		if (existeClave(clave)) {
			if (clave.compareTo(this.raiz.getClave()) == 0) {
				dato = this.raiz.getDato();
			} else if (clave.compareTo(this.raiz.getClave()) < 0) {
				dato = obtenerInfAux(this.raiz.getIzq(), clave);
			} else {
				dato = obtenerInfAux(this.raiz.getDer(), clave);
			}
		}

		return dato;

	}

	private Object obtenerInfAux(NodoDicc n, Comparable clave) {
		Object dato = null;
		if (n != null && dato == null) {
			if (clave.compareTo(n.getClave()) == 0) {
				dato = n.getDato();
			} else if (clave.compareTo((n.getClave())) < 0) {
				dato = obtenerInfAux(n.getIzq(), clave);
			} else {
				dato = obtenerInfAux(n.getDer(), clave);
			}
		}
		return dato;
	}

	public Lista listarClaves() {
		Lista listaClaves = new Lista();
		if (!this.esVacio()) {
			listarClavesAux(this.raiz, listaClaves);
		}
		return listaClaves;
	}

	private void listarClavesAux(NodoDicc n, Lista listado) {

		if (n != null) {
			if ((n.getIzq() == null) || (n.getIzq() == null && n.getDer() == null)) {
				listado.insertar(n.getClave(), listado.longitud() + 1);
			} else {
				listarClavesAux(n.getIzq(), listado);
				listado.insertar(n.getClave(), listado.longitud() + 1);
			}
			listarClavesAux(n.getDer(), listado);
		}
	}

	public Lista listarRango(Comparable min, Comparable max) {
		Lista lista = new Lista();
		System.out.println("en listarRango");
		listarRangoAux(lista, this.raiz, min, max);
		return lista;
	}

	private void listarRangoAux(Lista lista, NodoDicc n, Comparable min, Comparable max) {
		// listar rango de mayor a menor
		if (n != null) {

			if (min.compareTo(n.getClave()) < 0)
				listarRangoAux(lista, n.getIzq(), min, max);

			if (max.compareTo(n.getClave()) >= 0 && min.compareTo(n.getClave()) <= 0)
				lista.insertar(n.getClave(), lista.longitud() + 1);
			if (max.compareTo(n.getClave()) > 0)
				listarRangoAux(lista, n.getDer(), min, max);
		}

	}

	public String toString() {
		String arbol = "";

		if (this.raiz != null) {
			arbol += toStringAux(this.raiz);
		} else {
			arbol = "Arbol vacio";
		}

		return arbol;
	}

	private String toStringAux(NodoDicc nodo) {
		String listado = "";

		if (nodo != null) {
			listado += "Padre: " + nodo.getClave() + " dato asociado " + nodo.getDato() + " ALTURA " + nodo.getAltura();
			listado += "\n";
			if (nodo.getIzq() != null) {
				listado += "Hijo izquierdo: " + nodo.getIzq().getClave() + " ";
			} else {
				listado += "Hijo izquierdo: No tiene ";
			}
			if (nodo.getDer() != null) {
				listado += "Hijo derecho: " + nodo.getDer().getClave() + " \n";
			} else {
				listado += " Hijo derecho: No tiene \n";
			}

			listado += "\n";
			listado += toStringAux(nodo.getIzq());
			listado += toStringAux(nodo.getDer());
		}
		return listado;
	}

}