package tablaSimbolos;

public class EntradaTabla {
	
Atributo att;

public Atributo getAtt() {
	return att;
}

public void setAtt(Atributo att) {
	this.att = att;
}

public String toString(){
	return att.toString();
}

public EntradaTabla(){att=null;}
	

}
