package token;



public class TokenNumReal extends Token {
	float num;
	public TokenNumReal(float e){
		super.tipo=Token.TipoToken.NUM_REAL;
		num=e;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+num;
	}
	
}
