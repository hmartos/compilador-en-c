package tablaSimbolos;

import java.util.ArrayList;

public class AtributosTablaVariable extends Atributo {

	String tipo;
	int dim;
	ArrayList<Integer> tama�os;
	
	
	
	public AtributosTablaVariable(String tipo, int dim,
			ArrayList<Integer> tama�os) {
		super();
		this.tipo = tipo;
		this.dim = dim;
		this.tama�os = tama�os;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getDim() {
		return dim;
	}
	public void setDim(int dim) {
		this.dim = dim;
	}
	public ArrayList<Integer> getTama�os() {
		return tama�os;
	}
	public void setTama�os(ArrayList<Integer> tama�os) {
		this.tama�os = tama�os;
	}
	
	
	
}
