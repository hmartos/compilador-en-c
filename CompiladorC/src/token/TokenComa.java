package token;

public class TokenComa extends Token{
	public TokenComa(){
		super.tipo=Token.TipoToken.COMA;
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
