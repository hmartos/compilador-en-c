package tablaSimbolos;

import java.util.ArrayList;

public class AtributosTablaVariable extends Atributo {

	String tipo;
	int dim;
	ArrayList<Integer> tamaños;
	
	
	
	public AtributosTablaVariable(String tipo, int dim,
			ArrayList<Integer> tamaños) {
		super();
		this.tipo = tipo;
		this.dim = dim;
		this.tamaños = tamaños;
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
	public ArrayList<Integer> getTamaños() {
		return tamaños;
	}
	public void setTamaños(ArrayList<Integer> tamaños) {
		this.tamaños = tamaños;
	}
	
	
	
}
