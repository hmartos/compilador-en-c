package token;

public class TokenOpDespl extends Token {

	TipoTokenOpDespl tipoAtb;
	
	
	public TokenOpDespl(TipoTokenOpDespl t){
		super.tipo=Token.TipoToken.OP_DESPL;
		tipoAtb=t;
	}
	
	
	
	static public enum TipoTokenOpDespl {
		DESPL_IZQ,DESPL_DER
		}
	
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAtb;
	}


	@Override
	public TipoTokenOpDespl getAtributo() {

		return tipoAtb;
	}
	
}
