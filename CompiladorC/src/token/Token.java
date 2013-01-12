package token;

public abstract class Token {

	TipoToken tipo;

	
	
	public abstract Object getAtributo();
	
	public TipoToken getTipo() {
			return tipo;
		}
	
	public String toString(){
		return ("token tipo: "+tipo.toString());
	}
	
	
	
	
	static public enum TipoToken {
		PAL_RES,ID,PARENTESIS,ASIGNACION, OR_L, NUM_ENTERO, ENTRECOMILLADO, FIN, AMPERSAND,
		AND_L, ASTERISCO, COMILLAS_CHAR, OP_DESPL, OP_MULT, OP_SELECCION, OP_UNARIO, REL_COMP,
		REL_IGUAL, SIMBOLO_ADICION, XOR_B, OR_B, LLAVE, CORCHETE, COMA, PUNTOYCOMA, OP_TERNARIO,
		COMILLA_SIMPLE, NUM_REAL,
		
		LAMBDA, ALMOHADILLA, EOL
		}





}
