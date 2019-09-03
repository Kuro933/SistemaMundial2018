package clases;

public class PartidoClave {
	private String eq1, eq2;

	public PartidoClave(String e1, String e2) {
		String aux;
		e1 = e1.toUpperCase();
		e2 = e2.toUpperCase();

		if (e1.compareTo(e2) > 0) {
			aux = e2;
			e2 = e1;
			e1 = aux;
		}
		eq1 = e1;
		eq2 = e2;

	}

	public String getEq1() {
		return this.eq1;
	}

	public String getEq2() {
		return this.eq2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eq1 == null) ? 0 : eq1.hashCode());
		result = prime * result + ((eq2 == null) ? 0 : eq2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartidoClave aux = (PartidoClave) obj;
		if (eq1 == null) {
			if (aux.eq1 != null)
				return false;
		} else if (!eq1.equals(aux.eq1))
			return false;
		if (eq2 == null) {
			if (aux.eq2 != null)
				return false;
		} else if (!eq2.equals(aux.eq2))
			return false;
		return true;
	}

	public boolean equals(PartidoClave otroP) {
		boolean res;

		res = (this.eq1.equals(otroP.eq1)) && (this.eq2.equals(otroP.eq2));

		return res;

	}

	public String toString() {
		String cad = "[eq1: " + this.eq1 + ", eq2: " + this.eq2 + "]";
		return cad;
	}
}