package token;

import token.TokenLlaves.TipoTokenLlaves;

public class TokenOpTernario extends Token{
	
	TipoTokenOpTernario tipoAtb;
	
	
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
	
}
