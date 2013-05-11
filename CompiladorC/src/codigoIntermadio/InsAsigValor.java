package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsAsigValor extends InstruccionIntermedio {

	
	
	EntradaTabla res;
	String op1;
	


	
	
	public InsAsigValor() {
		super();
		
		
	}
	
	public EntradaTabla getRes() {
		return res;
	}

	public void setRes(EntradaTabla res) {
		this.res = res;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	
	
	public InstruccionIntermedio nuevo(){
		return new InsAsigValor();
	}
	
	
public String toString(){
		
		
		String sOp1="",sRes="";
		
		
		if (op1!=null)sOp1=op1.toString();
		if (res!=null)sRes=res.toString();
		
		return super.toString()+sRes+":="+sOp1;
	}
}
