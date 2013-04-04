package token;




public class TokenRelIgual extends Token {

	TipoTokenRelIgual tipoAsig;
	
	public TokenRelIgual(TipoTokenRelIgual t,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.REL_IGUAL;
		tipoAsig=t;
	}
	
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
	
	@Override
	public TipoTokenRelIgual getAtributo() {

		return tipoAsig;
	}
	
}
