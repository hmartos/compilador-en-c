package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsIni extends InstruccionIntermedio {

	int  valorIni;
	public InsIni() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getValorIni() {
		return valorIni;
	}

	public void setValorIni(int valorIni) {
		this.valorIni = valorIni;
	}

	
	public InstruccionIntermedio nuevo(){
		return new InsIni();
	}
	
	public String toString(){
		return super.toString()+"ini "+valorIni;
	}
}
