package token;


public class TokenOpTernario extends Token{
	
	TipoTokenOpTernario tipoAtb;
	
	
	public TokenOpTernario(TipoTokenOpTernario t,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.OP_TERNARIO;
		tipoAtb=t;
	}
	public TokenOpTernario(TipoTokenOpTernario t){
		super.tipo=Token.TipoToken.OP_TERNARIO;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenOpTernario {
		INTERROGACION,DOSPUNTOS
	}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}


	@Override
	public TipoTokenOpTernario getAtributo() {
		return tipoAtb;
	}
	
}
