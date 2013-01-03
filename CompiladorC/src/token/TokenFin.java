package token;

public class TokenFin extends Token {

	
	
	public TokenFin(){
		super.tipo=Token.TipoToken.FIN;
	}
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	@Override
	public Object getAtributo() {
		return null;
	}
}
