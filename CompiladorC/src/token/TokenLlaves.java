package token;

public class TokenLlaves extends Token{
	TipoTokenLlaves tipoAtb;
	
	public TokenLlaves(TipoTokenLlaves t,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.LLAVE;
		tipoAtb=t;
	}
	
	public TokenLlaves(TipoTokenLlaves t){
		super.tipo=Token.TipoToken.LLAVE;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenLlaves {
		ABIERTO,CERRADO
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}

	@Override
	public TipoTokenLlaves getAtributo() {
		return tipoAtb;
	}
}
