package token;

public class TokenAlmohadilla extends Token {
	
	public TokenAlmohadilla(int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.ALMOHADILLA;
	}
	
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
	
	/*public boolean equals(TipoToken t){
		if(t==TipoToken.ALMOHADILLA) return true;
		return false;
	}*/
}
