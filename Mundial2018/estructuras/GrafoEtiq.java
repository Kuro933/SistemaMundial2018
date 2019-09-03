package estructuras;

public class GrafoEtiq<T> {

	private NodoVert<T> inicio;

	public GrafoEtiq() {
		this.inicio = null;
	}

	public NodoVert<T> ubicarVertice(Object elto) {
		NodoVert<T> aux = this.inicio;

		while (aux != null && !aux.getElemento().equals(elto)) {
			aux = aux.getSigVer();
		}
		return aux;
	}

	public NodoVert[] ubicarVertices(Object primerElto, Object segundoElto) {
		NodoVert[] arr = new NodoVert[2];
		NodoVert<T> aux = this.inicio;

		while (aux != null && (arr[0] == null || arr[1] == null)) {
			if (aux.getElemento().equals(primerElto)) {
				arr[0] = aux;
			}
			if (aux.getElemento().equals(segundoElto)) {
				arr[1] = aux;
			}
			aux = aux.getSigVer();
		}
		return arr;
	}

	public boolean insertarVertice(Object nuevaVert) {
		boolean exito = false;
		NodoVert<T> aux = ubicarVertice(nuevaVert);
		if (aux == null) {
			this.inicio = new NodoVert<T>(nuevaVert, this.inicio);
			exito = true;
		}

		return exito;
	}

	public boolean eliminarVertice(Object vert) {
		boolean exito = false;
		NodoVert<T> aux = this.inicio, temp = null;
		while (aux != null && !exito) {
			if (aux.getElemento().equals(vert) && aux == this.inicio) {
				this.eliminarVerticeAux(aux);
				this.inicio = this.inicio.getSigVer();
				exito = true;
			} else if (aux.getElemento().equals(vert)) {
				this.eliminarVerticeAux(aux);
				temp.setSigVer(aux.getSigVer());
				exito = true;
			} else {
				temp = aux;
				aux = aux.getSigVer();
			}
		}
		return exito;
	}

	private void eliminarVerticeAux(NodoVert<T> vert) {
		NodoAdy arco = vert.getPrimerAdy();
		while (arco != null) {
			NodoVert<T> vertAux = arco.getVertice();
			NodoAdy arcoDest = vertAux.getPrimerAdy();

			if (arcoDest.getVertice().getElemento().equals(vert.getElemento())) {
				vertAux.setPrimerAdy(arcoDest.getSiguienteAdyacente());
			} else {
				NodoAdy arcoAux = arcoDest;
				arcoDest = arcoDest.getSiguienteAdyacente();
				boolean bandera = false;
				while (arcoDest != null && !bandera) {
					if (arcoDest.getVertice().getElemento().equals(vert.getElemento())) {
						arcoAux.setSiguienteAdyacente(arcoDest.getSiguienteAdyacente());
						bandera = true;
					}
					arcoAux = arcoAux.getSiguienteAdyacente();
					arcoDest = arcoDest.getSiguienteAdyacente();
				}
			}
			arco = arco.getSiguienteAdyacente();
		}
	}

	public boolean insertarArco(Object origen, Object destino, double etiqueta) {
		boolean exito = false;
		// verificar que existan los datos origen y destino
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert<T> vertOrigen = nodos[0];
		NodoVert<T> vertDest = nodos[1];
		if (vertOrigen != null && vertDest != null) {
			if (!existeArco(vertOrigen, vertDest)) {
				exito = true;
				insertarArcoAux(vertOrigen, vertDest, etiqueta);
				insertarArcoAux(vertDest, vertOrigen, etiqueta);
			}
		}

		return exito;
	}

	private void insertarArcoAux(NodoVert<T> origen, NodoVert<T> destino, double etiqueta) {
		NodoAdy arco = new NodoAdy();
		arco.setEtiqueta(etiqueta);
		arco.setVertice(destino);
		arco.setSiguienteAdyacente(origen.getPrimerAdy());
		origen.setPrimerAdy(arco);
	}

	public boolean eliminarArco(Object origen, Object destino) {
		boolean exito = false;
		// verificar que existan los datos origen y destino
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert<T> vertOrigen = nodos[0];
		NodoVert<T> vertDest = nodos[1];

		if (vertOrigen != null && vertDest != null && existeArco(vertOrigen, vertDest)) {
			eliminarArcoAux(vertOrigen, vertDest);
			eliminarArcoAux(vertDest, vertOrigen);
			exito = true;
		}

		return exito;
	}

	private void eliminarArcoAux(NodoVert<T> vertOrigen, NodoVert<T> vertDest) {
		NodoAdy adyOrg = vertOrigen.getPrimerAdy();
		if (adyOrg.getVertice().getElemento().equals(vertDest.getElemento())) {
			vertOrigen.setPrimerAdy(vertOrigen.getPrimerAdy().getSiguienteAdyacente());
		} else {
			NodoAdy sigAdyOrg = vertOrigen.getPrimerAdy().getSiguienteAdyacente();
			boolean encontrado = false;
			while (sigAdyOrg != null && !encontrado) {
				if (sigAdyOrg.getVertice().getElemento().equals(vertDest.getElemento())) {
					encontrado = true;
					adyOrg.setSiguienteAdyacente(sigAdyOrg.getSiguienteAdyacente());
				} else {
					sigAdyOrg = sigAdyOrg.getSiguienteAdyacente();
					adyOrg = adyOrg.getSiguienteAdyacente();
				}
			}
		}

	}

	public Object obtenerElemVertice(Object eltoBuscado) {
		return (ubicarVertice(eltoBuscado)).getElemento();
	}

	public boolean existeVertice(Object elto) {
		return (ubicarVertice(elto) != null);
	}

	public boolean existeArco(NodoVert<T> origen, NodoVert<T> destino) {
		boolean exito = false;
		NodoAdy arco = origen.getPrimerAdy();
		while (arco != null && !exito) {
			if (arco.getVertice().getElemento().equals(destino.getElemento())) {
				exito = true;
			}
			arco = arco.getSiguienteAdyacente();
		}
		return exito;
	}

	public Lista listarEnProfundidad() {
		Lista visitados = new Lista();
		NodoVert<T> nodo = this.inicio;
		while (nodo != null) {
			if (visitados.localizar(nodo.getElemento()) < 0) {
				listarEnProfundidadAux(nodo, visitados);
			}
			nodo = nodo.getSigVer();
		}
		return visitados;
	}

	private void listarEnProfundidadAux(NodoVert n, Lista vis) {
		if (n != null) {
			vis.insertar(n.getElemento(), vis.longitud() + 1);
			NodoAdy ady = n.getPrimerAdy();
			while (ady != null) {
				if (vis.localizar(ady.getVertice().getElemento()) < 0) {
					listarEnProfundidadAux(ady.getVertice(), vis);
				}
				ady = ady.getSiguienteAdyacente();
			}
		}
	}

	public Lista listarEnAnchura() {
		Lista visitados = new Lista();
		NodoVert<T> nodo = this.inicio;
		while (nodo != null) {
			if (visitados.localizar(nodo.getElemento()) < 0) {
				anchuraDesde(nodo, visitados);
			}
			nodo = nodo.getSigVer();
		}
		return visitados;
	}

	private Lista anchuraDesde(NodoVert<T> vertInicial, Lista visitados) {
		Lista list = new Lista();
		list.insertar(vertInicial, list.longitud() + 1);
		NodoVert<T> u;
		NodoAdy arco;
		while (list.longitud() != 0) {
			u = (NodoVert) list.recuperar(1);
			list.eliminar(1);
			visitados.insertar(u.getElemento(), visitados.longitud() + 1);
			arco = u.getPrimerAdy();
			while (arco != null) {
				if (list.localizar(arco.getVertice()) < 0 && visitados.localizar(arco.getVertice().getElemento()) < 0) {
					list.insertar(arco.getVertice(), list.longitud() + 1);
				}

				arco = arco.getSiguienteAdyacente();
			}
		}
		return visitados;
	}

	public boolean existeCamino(Object origen, Object destino) {
		boolean exito = false;
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert<T> nodoOrigen = nodos[0];
		NodoVert<T> nodoDest = nodos[1];

		if (nodoOrigen != null && nodoDest != null) {
			Lista visitados = new Lista();
			exito = existeCaminoAux(nodoOrigen, destino, visitados);
		}
		return exito;
	}

	private boolean existeCaminoAux(NodoVert<T> nodo, Object destino, Lista visitados) {
		boolean exito = false;

		if (nodo != null) {
			if (nodo.getElemento().equals(destino)) {
				exito = true;
			} else {
				visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
				NodoAdy arco = nodo.getPrimerAdy();
				while (arco != null && !exito) {
					if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
						exito = existeCaminoAux(arco.getVertice(), destino, visitados);
					}
					arco = arco.getSiguienteAdyacente();
				}
			}

		}
		return exito;
	}

	public Lista caminoMasCorto(Object origen, Object destino) {
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert aux0 = nodos[0];
		NodoVert aux1 = nodos[1];
		Lista visitados = new Lista(), camino = new Lista(), caminoFinal = new Lista();
		double[] arr = new double[1];
		arr[0] = Double.MAX_VALUE;
		if (aux0 != null && aux1 != null) {
			caminoFinal = caminoMasCortoAux(aux0, destino, visitados, 0, camino, arr);
		}
		return caminoFinal;
	}

	private Lista caminoMasCortoAux(NodoVert<T> n, Object destino, Lista visitados, double distActual, Lista camino,
			double[] distMin) {
		if (n != null) {
			visitados.insertar(n.getElemento(), visitados.longitud() + 1);
			if (n.getElemento().equals(destino)) {
				if (distActual < distMin[0]) {
					camino = visitados.clone();
					distMin[0] = distActual;
				}
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					distActual += arco.getEtiqueta();
					if (distActual < distMin[0]) {
						if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
							camino = caminoMasCortoAux(arco.getVertice(), destino, visitados, distActual, camino,
									distMin);
						}
					}
					distActual -= arco.getEtiqueta();
					arco = arco.getSiguienteAdyacente();
				}

			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;
	}

	public Lista caminoMenosNodos(Object origen, Object destino) {
		Lista visitados = new Lista();
		Lista camino = new Lista();
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		if (aux0 != null && aux1 != null) {
			camino = caminoMenosNodosAux(aux0, destino, visitados, camino);
		}
		return camino;
	}

	private Lista caminoMenosNodosAux(NodoVert n, Object destino, Lista visitados, Lista camino) {

		if (n != null) {
			visitados.insertar(n.getElemento(), visitados.longitud() + 1);
			if (!n.getElemento().equals(destino)) {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
						if (camino.longitud() != 0 && visitados.longitud() < camino.longitud()) {
							camino = caminoMenosNodosAux(arco.getVertice(), destino, visitados, camino);
						} else {
							camino = caminoMenosNodosAux(arco.getVertice(), destino, visitados, camino);
						}
					}
					arco = arco.getSiguienteAdyacente();
				}
			} else {
				if (camino.longitud() == 0 || visitados.longitud() < camino.longitud()) {
					camino = visitados.clone();
				}
			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;

	}

//	public Lista entreDist(Object origen, Object destino, int pesoMin, int pesoMax) {
//		Lista caminoFinal = new Lista(), visitados = new Lista(), camino = new Lista();
//		NodoVert aux0 = ubicarVertice(origen);
//		NodoVert aux1 = ubicarVertice(destino);
//		if (aux0 != null && aux1 != null) {
//			caminoFinal = entreDistAux(aux0, destino, pesoMin, pesoMax, visitados, camino, 0);
//		}
//		return caminoFinal;
//	}
//
//	private Lista entreDistAux(NodoVert origen, Object destino, int pesoMin, int pesoMax, Lista visitados, Lista camino,
//			double pesoActual) {
//		if (origen != null) {
//			visitados.insertar(origen.getElemento(), visitados.longitud() + 1);
//			if (origen.getElemento().equals(destino)) {
//				if (pesoActual <= pesoMax && pesoActual >= pesoMin) {
//					camino = visitados.clone();
//				}
//			} else {
//				NodoAdy arco = origen.getPrimerAdy();
//				pesoActual = pesoActual + arco.getEtiqueta();
//				while (arco != null) {
//					if (pesoActual <= pesoMax && pesoActual >= pesoMin) {
//						if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
//							camino = entreDistAux(arco.getVertice(), destino, pesoMin, pesoMax, visitados, camino,
//									pesoActual);
//						}
//					}
//					pesoActual -= arco.getEtiqueta();
//					arco.getSiguienteAdyacente();
//				}
//			}
//			visitados.eliminar(visitados.longitud());
//		}
//		return camino;
	}

	public Cola caminosEntre(Object origen, Object destino) {
		Lista visitados = new Lista();
		Cola q = new Cola();
		Lista camino = new Lista();
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert aux0 = nodos[0];
		NodoVert aux1 = nodos[1];
		if (aux0 != null && aux1 != null) {
			caminosEntreAux(aux0, destino, visitados, q);
		}
		return q;
	}

	private void caminosEntreAux(NodoVert<T> n, Object destino, Lista visitados, Cola q) {

		if (n != null) {
			visitados.insertar(n.getElemento(), visitados.longitud() + 1);
			if (n.getElemento().equals(destino)) {
				q.poner(visitados.clone());
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
						caminosEntreAux(arco.getVertice(), destino, visitados, q);
					}
					arco = arco.getSiguienteAdyacente();
				}

			}
			visitados.eliminar(visitados.longitud());
		}

	}

	public Lista caminoMasCortoPorCiudad(Object origen, Object destino, Object medio) {
		NodoVert[] nodos = ubicarVertices(origen, destino);
		NodoVert aux0 = nodos[0];
		NodoVert aux1 = nodos[1];
		NodoVert aux2 = ubicarVertice(medio);

		Lista visitados = new Lista();
		Lista camino = new Lista();
		boolean[] arrBool = new boolean[1];
		double[] arrDoub = new double[1];
		arrBool[0] = false;
		arrDoub[0] = 9999;
		if (aux0 != null && aux1 != null && aux2 != null) {
			camino = caminoMasCortoPorCiudadAux(aux0, destino, medio, visitados, camino, arrBool, arrDoub, 0);
		}
		return camino;
	}

	private Lista caminoMasCortoPorCiudadAux(NodoVert<T> n, Object destino, Object medio, Lista visitados, Lista camino,
			boolean[] bandera, double[] distMin, double distActual) {

		if (n != null) {
			visitados.insertar(n.getElemento(), visitados.longitud() + 1);
			if (n.getElemento().equals(medio)) {
				bandera[0] = true;
			}
			if (n.getElemento().equals(destino)) {
				if (bandera[0]) {
					if (distActual < distMin[0]) {
						camino = visitados.clone();
						distMin[0] = distActual;
					}
				}
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					distActual += arco.getEtiqueta();
					if (distActual < distMin[0]) {
						if (visitados.localizar(arco.getVertice().getElemento()) < 0) {
							camino = caminoMasCortoPorCiudadAux(arco.getVertice(), destino, medio, visitados, camino,
									bandera, distMin, distActual);
							if (arco.getVertice().getElemento().equals(medio)) {
								bandera[0] = false;
							}
						}
					}
					distActual -= arco.getEtiqueta();
					arco = arco.getSiguienteAdyacente();
				}
			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;

	}

	@Override
	public String toString() {
		String s = "";

		NodoVert<T> nodo = this.inicio;
		while (nodo != null) {
			s += "El Vertice: " + nodo.getElemento() + ".\n";
			NodoAdy arco = nodo.getPrimerAdy();
			s += "Tiene los siguientes arcos: \n";

			while (arco != null) {
				s += "Etiqueta: " + arco.getEtiqueta() + " ------> " + arco.getVertice().getElemento() + "\n";
				arco = arco.getSiguienteAdyacente();
			}
			s += "\n";
			nodo = nodo.getSigVer();
		}
		return s;
	}
}