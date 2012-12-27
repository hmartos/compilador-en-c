package token;

public class TokenEntrecomillado extends Token {

	
String cadena;
	
	
	public TokenEntrecomillado(String e){
		super.tipo=Token.TipoToken.ENTRECOMILLADO;
		cadena=e;
	}
	public String toString(){
		return super.toString()+" Atributo: \""+cadena+"\"";
	}
}
