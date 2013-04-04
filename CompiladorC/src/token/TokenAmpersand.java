package token;

import token.Token.TipoToken;

public class TokenAmpersand extends Token {
	
	public TokenAmpersand(int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.AMPERSAND;
	}
	public TokenAmpersand(){
		super.tipo=Token.TipoToken.AMPERSAND;
	}

	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}


	@Override
	public Object getAtributo() {
		return null;
	}
	
	/*
	public boolean equals(TipoToken t){
		if(t==TipoToken.AMPERSAND) return true;
		return false;
	}*/
}
