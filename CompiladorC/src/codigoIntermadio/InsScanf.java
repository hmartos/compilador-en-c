package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsScanf extends InstruccionIntermedio{

	EntradaTabla IDEN;
	
	public InsScanf() {
		super();
	}
	
	public void setEXPlugar(EntradaTabla IDEN) {
		this.IDEN = IDEN;
	}
	
	public EntradaTabla getEXPlugar() {
		return this.IDEN;
	}
	
	public InstruccionIntermedio nuevo(){
		return new InsScanf();
	}
	
	public String toString(){
		String sVlugar="";
		if (this.IDEN!=null)sVlugar=this.IDEN.toString();
		return super.toString()+"scanf "+sVlugar;
	}

}
