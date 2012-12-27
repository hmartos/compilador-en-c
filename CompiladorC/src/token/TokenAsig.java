package token;

public class TokenAsig extends Token {

	TipoTokenAsig tipoAsig;
	
	
	public TokenAsig(TipoTokenAsig t){
		super.tipo=Token.TipoToken.ASIGNACION;
		tipoAsig=t;
	}
	
	
	
	static public enum TipoTokenAsig {
		ASIG,SUMA_ASIG,RESTA_ASIG,MULT_ASIG,DIV_ASIG,MOD_ASIG,DESP_IZQ_ASIG,DESP_DER_ASIG,ANDB_ASIG,XORB_ASIG,ORB_ASIG
		}
	
	public String toString(){
		return super.toString()+" Atributo: "+tipoAsig.toString();
	}
}