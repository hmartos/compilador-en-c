package token;

public class TokenLambda extends Token {
	
	public TokenLambda(){
		super.tipo=Token.TipoToken.LAMBDA;
	}

	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}


	@Override
	public Object getAtributo() {
		return null;
	}
}