package token;

public class TokenOpComillas_Simples extends Token {
	public TokenOpComillas_Simples(){
		super.tipo=Token.TipoToken.COMILLA_SIMPLE;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	
}