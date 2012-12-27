package token;



public class TokenId extends Token {

	
	String id;
	
	
	public TokenId(String e){
		super.tipo=Token.TipoToken.ID;
		id=e;
	}
	
	public String toString(){
		return super.toString()+" Atributo: \""+id+"\"";
	}
	
}
