package token;

public class TokenAndL extends Token {
	
	public TokenAndL(){
		super.tipo=Token.TipoToken.AND_L;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}

	@Override
	public Object getAtributo() {
		// TODO Auto-generated method stub
		return null;
	}
}
