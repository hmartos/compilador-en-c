package token;

public class TokenOpComillas_Simples extends Token {
	//CREO QUE ESTE TOKEN NO SE USA (ES INNECESARIO)
	public TokenOpComillas_Simples(){
		super.tipo=Token.TipoToken.COMILLA_SIMPLE;
	}
	
	public String toString(){
		return super.toString()+" Atributo: "+"NULL";
	}

	@Override
	public Object getAtributo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
