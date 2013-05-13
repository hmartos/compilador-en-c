package codigoIntermadio;

import tablaSimbolos.EntradaTabla;
public class InsReturn extends InstruccionIntermedio {

	EntradaTabla valorRet;
	public EntradaTabla getValorRet() {
		return valorRet;
	}

	public void setValorRet(EntradaTabla valorRet) {
		this.valorRet = valorRet;
	}

	public InsReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InsReturn(String et) {
		super(et);
		// TODO Auto-generated constructor stub
	}

	
	public InstruccionIntermedio  nuevo(){
		return new InsReturn();
	}
	
	
	
	public String toString(){
		String sVRet="";
		if (valorRet!=null)sVRet=valorRet.toString();
		return super.toString()+"return "+sVRet;
	}
}
