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

	@Override
	public String getAtributo() {
		
		return id;
	}
	/*Para la gramatica usamos el equals. No usar el equals para
	comparar el lexema que contiene el token
	 */
	public boolean equals(Object a){
		return a instanceof TokenId;
	}
	public int hashCode(){
		return tipo.ordinal()*1000;
	}
	
}
