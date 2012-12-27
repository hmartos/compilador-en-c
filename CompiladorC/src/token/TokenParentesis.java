package token;


public class TokenParentesis extends Token{
	TipoTokenParentesis tipoAtb;
	
	
	public TokenParentesis(TipoTokenParentesis t){
		super.tipo=Token.TipoToken.PARENTESIS;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenParentesis {
		ABIERTO,CERRADO
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb.toString();
	}
	
}