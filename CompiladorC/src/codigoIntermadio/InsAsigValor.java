package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsAsigValor extends InstruccionIntermedio {

	
	
	EntradaTabla res;
	String valor;
	


	
	
	public InsAsigValor() {
		super();
		
		
	}
	
	public EntradaTabla getRes() {
		return res;
	}

	public void setRes(EntradaTabla res) {
		this.res = res;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	
	
	public InstruccionIntermedio nuevo(){
		return new InsAsigValor();
	}
	
	
public String toString(){
		
		
		String sOp1="",sRes="";
		
		
		if (valor!=null)sOp1=valor.toString();
		if (res!=null)sRes=res.toString();
		
		return super.toString()+sRes+":="+sOp1;
	}
}
