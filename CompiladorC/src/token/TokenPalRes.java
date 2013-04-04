package token;

import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.PalRes;

public class TokenPalRes extends Token {

	
	AtributosTablaPalRes entrada;
	
	
	public TokenPalRes(AtributosTablaPalRes e,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.PAL_RES;
		entrada=e;
	}
	
	public TokenPalRes(AtributosTablaPalRes e){
		super.tipo=Token.TipoToken.PAL_RES;
		entrada=e;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+entrada.getPalabra().toString();
	}

	@Override
	public PalRes getAtributo() {
		return entrada.getPalabra();
	}
	
	
}
