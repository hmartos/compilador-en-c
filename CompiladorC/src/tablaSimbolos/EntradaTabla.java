package tablaSimbolos;

import codigoFinal.LugarRM;

public class EntradaTabla {
	
String lex;

Atributo att;

LugarRM descriptDir;





public LugarRM getDescriptDir() {
	return descriptDir;
}

public void setDescriptDir(LugarRM descriptDir) {
	this.descriptDir = descriptDir;
}

public String getLex() {
	return lex;
}

public void setLex(String lex) {
	this.lex = lex;
}



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
