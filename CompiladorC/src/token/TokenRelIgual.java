package token;



public class TokenRelIgual extends Token {

	TipoTokenRelIgual tipoAsig;
	
	
	public TokenRelIgual(TipoTokenRelIgual t){
		super.tipo=Token.TipoToken.REL_IGUAL;
		tipoAsig=t;
	}
	
	
	
	static public enum TipoTokenRelIgual{
		IGUAL,DISTINTO
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAsig;
	}
	
}
