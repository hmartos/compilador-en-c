package token;



public class TokenNumEntero extends Token {
	int num;
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
	
}
