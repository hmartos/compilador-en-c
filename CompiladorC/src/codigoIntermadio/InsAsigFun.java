package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsAsigFun extends InstruccionIntermedio {

	
	
	EntradaTabla res;
	
	


	
	
	public InsAsigFun() {
		super();
		
		
	}
	
	public EntradaTabla getRes() {
		return res;
	}

	public void setRes(EntradaTabla res) {
		this.res = res;
	}

	

	
	
	public InstruccionIntermedio nuevo(){
		return new InsAsigFun();
	}
	
	
public String toString(){
		
		
		String sRes="";
		
		
		
		if (res!=null)sRes=res.toString();
		
		return super.toString()+sRes+":=return";
	}
}
