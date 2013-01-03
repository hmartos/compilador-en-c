package token;

public class TokenOpUnario extends Token {

	TipoTokenOpUnario tipoAtb;
	
	
	public TokenOpUnario(TipoTokenOpUnario t){
		super.tipo=Token.TipoToken.OP_UNARIO;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenOpUnario {
		INCREMENTO,DECREMENTO,NOT_L,NOT_B
	}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}


	@Override
	public TipoTokenOpUnario getAtributo() {
		return tipoAtb;
	}
	
}
