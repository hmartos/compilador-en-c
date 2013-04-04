package token;

public class TokenPuntoyComa extends Token{
	public TokenPuntoyComa(int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.PUNTOYCOMA;
	}
	public TokenPuntoyComa(){
		super.tipo=Token.TipoToken.PUNTOYCOMA;
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
