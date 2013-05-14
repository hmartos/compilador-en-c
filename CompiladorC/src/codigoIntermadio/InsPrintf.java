package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsPrintf extends InstruccionIntermedio{
	
	EntradaTabla EXPlugar;
	
	public InsPrintf() {
		super();
	}
	
	public void setEXPlugar(EntradaTabla EXPlugar) {
		this.EXPlugar = EXPlugar;
	}
	
	public EntradaTabla getEXPlugar() {
		return this.EXPlugar;
	}
	
	public InstruccionIntermedio nuevo(){
		return new InsPrintf();
	}
	
	public String toString(){
		String sVlugar="";
		if (this.EXPlugar!=null)sVlugar=this.EXPlugar.toString();
		return super.toString()+"printf "+sVlugar;
	}
}
