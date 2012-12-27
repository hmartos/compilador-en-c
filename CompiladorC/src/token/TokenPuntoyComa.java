package token;

public class TokenPuntoyComa extends Token{
	public TokenPuntoyComa(){
		super.tipo=Token.TipoToken.PUNTOYCOMA;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	
}
