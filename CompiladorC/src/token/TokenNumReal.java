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

	@Override
	public Object getAtributo() {
		// TODO Auto-generated method stub
		return num;
	}
	
	/*Para la gramatica usamos el equals. No usar el equals para
	comparar el numero que contiene el token
	 */
	public boolean equals(Object a){
		return a instanceof TokenNumReal;
	}
	public int hashCode(){
		return tipo.ordinal()*1000;
	}
}
