package token;

public class TokenAsterisco extends Token {
	
	public TokenAsterisco(){
		super.tipo=Token.TipoToken.ASTERISCO;
	}
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
}
