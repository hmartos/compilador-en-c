package token;

public class TokenEOL extends Token {
//CREO QUE ESTE TOKEN NO ES NECESARIO.
	
	
	public TokenEOL(){
		super.tipo=Token.TipoToken.EOL;
	}
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}
	@Override
	public Object getAtributo() {
		return null;
	}
}
