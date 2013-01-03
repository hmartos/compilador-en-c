package token;

public class TokenAlmohadilla extends Token {
	
	public TokenAlmohadilla(){
		super.tipo=Token.TipoToken.ALMOHADILLA;
	}

	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}


	@Override
	public Object getAtributo() {
		return null;
	}
}
