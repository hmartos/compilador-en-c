package token;

public class TokenSimboloAdicion extends Token {

	TipoTokenSimboloAdicion tipoAsig;
	
	public TokenSimboloAdicion(TipoTokenSimboloAdicion t,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.SIMBOLO_ADICION;
		tipoAsig=t;
	}
	
	public TokenSimboloAdicion(TipoTokenSimboloAdicion t){
		super.tipo=Token.TipoToken.SIMBOLO_ADICION;
		tipoAsig=t;
	}
	
	
	
	static public enum TipoTokenSimboloAdicion {
		SUMA,RESTA
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAsig;
	}


	@Override
	public TipoTokenSimboloAdicion getAtributo() {
		// TODO Auto-generated method stub
		return tipoAsig;
	}
	
}
