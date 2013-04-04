package token;



public class TokenNumEntero extends Token {
	int num;
	
	public TokenNumEntero(int e,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.NUM_ENTERO;
		num=e;
	}
	public TokenNumEntero(int e){
		super.tipo=Token.TipoToken.NUM_ENTERO;
		num=e;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+num;
	}

	@Override
	public Object getAtributo() {
		
		return num;
	}
	
	/*Para la gramatica usamos el equals. No usar el equals para
	comparar los numeros que contiene el token
	 */
	public boolean equals(Object a){
		return a instanceof TokenNumEntero;
	}
	public int hashCode(){
		return tipo.ordinal()*1000;
	}
}
