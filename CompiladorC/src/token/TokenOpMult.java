package token;

import token.TokenOpDespl.TipoTokenOpDespl;

public class TokenOpMult extends Token {

	TipoTokenOpMult tipoAtb;
	
	
	public TokenOpMult(TipoTokenOpMult t){
		super.tipo=Token.TipoToken.OP_MULT;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenOpMult {
		DIV,MOD
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}
	
	@Override
	public TipoTokenOpMult getAtributo() {

		return tipoAtb;
	}
}
