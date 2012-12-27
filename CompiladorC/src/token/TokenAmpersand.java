package token;

public class TokenAmpersand extends Token {
	
	public TokenAmpersand(){
		super.tipo=Token.TipoToken.AMPERSAND;
	}

	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
}
