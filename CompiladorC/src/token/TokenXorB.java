package token;

public class TokenXorB extends Token {
	
	public TokenXorB(){
		super.tipo=Token.TipoToken.XOR_B;
	}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	
}
