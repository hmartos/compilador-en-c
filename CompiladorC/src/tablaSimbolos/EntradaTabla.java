package tablaSimbolos;

public class EntradaTabla {
	
String lex;

public String getLex() {
	return lex;
}

public void setLex(String lex) {
	this.lex = lex;
}

Atributo att;

public Atributo getAtt() {
	return att;
}

public void setAtt(Atributo att) {
	this.att = att;
}

public String toString(){
	return lex;
}

public EntradaTabla(String lex){
	this.lex=lex;att=null;
}
	

}
