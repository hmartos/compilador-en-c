package token;

public class TokenXorB extends Token {
	
	public TokenXorB(int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.XOR_B;
	}
	
	public TokenXorB(){
		super.tipo=Token.TipoToken.XOR_B;
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
