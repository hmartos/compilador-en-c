package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsParam extends InstruccionIntermedio {

	EntradaTabla param;
	public InsParam() {
		super();
		// TODO Auto-generated constructor stub
	}



	public EntradaTabla getParam() {
		return param;
	}

	public void setParam(EntradaTabla param) {
		this.param = param;
	}

	
	public InstruccionIntermedio nuevo(){
		return new InsParam();
	}
	
	public String toString(){
		return super.toString()+"param "+param.toString();
	}
}
