package token;


public class TokenParentesis extends Token{
	TipoTokenParentesis tipoAtb;

	public TokenParentesis(TipoTokenParentesis t,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.PARENTESIS;
		tipoAtb=t;
	}
	public TokenParentesis(TipoTokenParentesis t){
		super.tipo=Token.TipoToken.PARENTESIS;
		tipoAtb=t;
	}
	
	public TipoTokenParentesis getAtributo() {
		return tipoAtb;
	}
	
	static public enum TipoTokenParentesis {
		ABIERTO,CERRADO
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb.toString();
	}
	
}
