package codigoIntermadio;

import java.util.ArrayList;

import acciones.Tipo;

import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class CodigoIntermedio {

	
	ArrayList<InstruccionIntermedio> lista;
	
	TablaSimbolos ts;
	int numTempVar;
	int numTempIf;
	int numTempBucle;
	
	
	public CodigoIntermedio(TablaSimbolos ts){
		numTempVar=0;
		numTempIf=0;
		numTempBucle=0;
		lista= new ArrayList<InstruccionIntermedio>();
		this.ts=ts;
	}
	
	
	public ArrayList<InstruccionIntermedio> getLista() {
		return lista;
	}


	public void setLista(ArrayList<InstruccionIntermedio> lista) {
		this.lista = lista;
	}


	
	
	
	public void addInstruccion (InstruccionIntermedio i){
		lista.add(i);
	}
	
	
	public EntradaTabla tempNuevo(Tipo t){
		String s= "t"+numTempVar;
		numTempVar++;
		
		EntradaTabla ret=ts.insertar(s);
		ts.añadirAtributos(s, new AtributosTablaVariable(t.getTipo(), t.getDim(),null));
		return ret;
	}
	
	public String ifNuevo(){
		String s= String.valueOf(numTempIf);
		numTempIf++;
		return s;
	}


	public Object bucleNuevo() {
		String s= String.valueOf(numTempBucle);
		numTempBucle++;
		return s;
	}
	
	
	
}
