package tablaSimbolos;


public class AtributosTablaTypeDef extends Atributo {
	String tipoRel;
	int dim;
	
	
	public AtributosTablaTypeDef(String tipoRel, int dim) {
		super();
		this.tipoRel = tipoRel;
		this.dim = dim;
	}
	public String getTipoRel() {
		return tipoRel;
	}
	public void setTipoRel(String tipoRel) {
		this.tipoRel = tipoRel;
	}
	public int getDim() {
		return dim;
	}
	public void setDim(int dim) {
		this.dim = dim;
	}
	
	
	
}
