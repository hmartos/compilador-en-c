package token;

import token.Token.TipoToken;

public class TokenAsterisco extends Token {
	
	public TokenAsterisco(){
		super.tipo=Token.TipoToken.ASTERISCO;
	}
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	@Override
	public Object getAtributo() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	public boolean equals(TipoToken t){
		if(t==TipoToken.ASTERISCO) return true;
		return false;
	}*/
}
