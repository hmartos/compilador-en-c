package token;

import token.Token.TipoToken;


public class TokenCorchetes extends Token{
	TipoTokenCorchetes tipoAtb;
	
	
	public TokenCorchetes(TipoTokenCorchetes t){
		super.tipo=Token.TipoToken.CORCHETE;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenCorchetes {
		ABIERTO,CERRADO
	}
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}
	@Override
	public TipoTokenCorchetes getAtributo() {
		return tipoAtb;
	}
	
	public boolean equals(TipoToken t){
		if(t==TipoToken.CORCHETE) return true;
		return false;
	}
}
