package token;

public class TokenSimboloAdicion extends Token {

	TipoTokenSimboloAdicion tipoAsig;
	
	
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
	
}
