package token;

public class TokenOrL extends Token {
	
	public TokenOrL(int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.OR_L;
	}
	
	public TokenOrL(){
		super.tipo=Token.TipoToken.OR_L;
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
