package token;

public class TokenRelComp extends Token {

	TipoTokenRelComp tipoAtb;
	
	
	public TokenRelComp(TipoTokenRelComp t){
		super.tipo=Token.TipoToken.REL_COMP;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenRelComp {
		MAYOR,MENOR,IGUAL_MAYOR,IGUAL_MENOR
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}
	
}