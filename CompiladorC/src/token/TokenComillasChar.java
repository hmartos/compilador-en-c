package token;

import token.Token.TipoToken;

public class TokenComillasChar extends Token {

	
char caracter;
	
	public TokenComillasChar(char e,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.COMILLAS_CHAR;
		caracter=e;
	}
	public TokenComillasChar(char e){
		super.tipo=Token.TipoToken.COMILLAS_CHAR;
		caracter=e;
	}
	public TokenComillasChar(){
		super.tipo=Token.TipoToken.COMILLAS_CHAR;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+caracter;
	}
	@Override
	public Object getAtributo() {
		// TODO Auto-generated method stub
		return caracter;
	}
	
	/*Para la gramatica usamos el equals. No usar el equals para
	comparar las cadenas que contiene el token
	 */
	public boolean equals(Object a){
		return a instanceof TokenComillasChar;
	}
	public int hashCode(){
		return tipo.ordinal()*1000;
	}
}
