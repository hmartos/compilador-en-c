package token;

public class TokenEntrecomillado extends Token {

	
String cadena;
	
	public TokenEntrecomillado(String e,int l, int c){
		super(l,c);
		super.tipo=Token.TipoToken.ENTRECOMILLADO;
		cadena=e;
	}
	public TokenEntrecomillado(String e){
		super.tipo=Token.TipoToken.ENTRECOMILLADO;
		cadena=e;
	}
	public String toString(){
		return super.toString()+" Atributo: \""+cadena+"\"";
	}
	@Override
	public String getAtributo() {
		return cadena;
	}
	
	/*Para la gramatica usamos el equals. No usar el equals para
	comparar las cadenas que contiene el token
	 */
	public boolean equals(Object a){
		return a instanceof TokenEntrecomillado;
	}
	
	public int hashCode(){
		return tipo.ordinal()*1000;
	}
	
}
