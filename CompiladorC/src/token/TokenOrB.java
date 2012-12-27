package token;

public class TokenOrB extends Token {
	
	public TokenOrB(){
		super.tipo=Token.TipoToken.OR_B;
	}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	
}
