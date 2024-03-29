package token;

public abstract class Token {

	TipoToken tipo;
	int linea, columna;
	
	
	public int getLinea() {
		return linea;
	}

	public int getCol() {
		return columna;
	}

	public abstract Object getAtributo();
	
	//Manentemos este constructor por compatibilidad con la gramatica, donde no nos interesa linea y columna
	public Token(){
		linea=0;
		columna=0;
	}
	
	public Token(int l, int c){
		linea=l;
		columna=c;
	}
	
	public TipoToken getTipo() {
			return tipo;
		}
	
	public String toString(){
		return ("token tipo: "+tipo.toString());
	}
	
	public boolean equals(Object a){
	if (a instanceof Token){	
		Token otroToken= (Token)a;
	
		if (this.getTipo().equals( otroToken.getTipo())){
			if (this.getAtributo()==null) return otroToken.getAtributo()==null;
			else return (this.getAtributo().equals(otroToken.getAtributo()));
		
		} else return false;
				
		
	}else return false;
	}
	
	public int hashCode(){
		int code=tipo.ordinal()*1000;
		if (getAtributo()!=null)code+=getAtributo().hashCode();
		return code;
		
	}
	
	
	static public enum TipoToken {
		PAL_RES,ID,PARENTESIS,ASIGNACION, OR_L, NUM_ENTERO, ENTRECOMILLADO, FIN, AMPERSAND,
		AND_L, ASTERISCO, COMILLAS_CHAR, OP_DESPL, OP_MULT, OP_SELECCION, OP_UNARIO, REL_COMP,
		REL_IGUAL, SIMBOLO_ADICION, XOR_B, OR_B, LLAVE, CORCHETE, COMA, PUNTOYCOMA, OP_TERNARIO,
		COMILLA_SIMPLE, NUM_REAL,
		
		LAMBDA, ALMOHADILLA, EOL
		}





}
