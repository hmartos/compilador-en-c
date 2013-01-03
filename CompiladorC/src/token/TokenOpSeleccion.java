package token;

import token.TokenOpDespl.TipoTokenOpDespl;

public class TokenOpSeleccion extends Token {

	TipoTokenOpSeleccion tipoAtb;
	
	
	public TokenOpSeleccion(TipoTokenOpSeleccion t){
		super.tipo=Token.TipoToken.OP_SELECCION;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenOpSeleccion {
		PUNTO,FLECHA
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}
	
	@Override
	public TipoTokenOpSeleccion getAtributo() {

		return tipoAtb;
	}
}
