package token;


public class TokenCorchetes extends Token{
	TipoTokenCorchetes tipoAtb;
	
	
	public TokenCorchetes(TipoTokenCorchetes t){
		super.tipo=Token.TipoToken.CORCHETE;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenCorchetes {
		ABIERTO,CERRADO
	}
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}
}