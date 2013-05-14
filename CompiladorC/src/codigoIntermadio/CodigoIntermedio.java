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
	int numTempAmbito;
	
	
	public CodigoIntermedio(TablaSimbolos ts){
		numTempVar=0;
		numTempIf=0;
		numTempBucle=0;
		numTempAmbito=0;
		lista= new ArrayList<InstruccionIntermedio>();
		this.ts=ts;
	}
	
	
	public ArrayList<InstruccionIntermedio> getLista() {
		
		// encontrar main
				/*
		ArrayList<InstruccionIntermedio> insDelMain= new ArrayList<InstruccionIntermedio>();
		
		
		boolean mainEncontrado=false;
		int posMain=0;
		while (!mainEncontrado && posMain<lista.size()){
			if ("comienzoFunmain".equals(lista.get(posMain).getEtiqueta())){
				mainEncontrado=true;
			}
			posMain++;
		}
		if (!mainEncontrado) return null;
		
		posMain--;
		boolean mainFinEncontrado=false;
		while (!mainFinEncontrado&& posMain<lista.size()){
			
			if ("finFunmain".equals(lista.get(posMain).getEtiqueta())){
				mainFinEncontrado=true;
			}
			InstruccionIntermedio delMain =lista.get(posMain);
			insDelMain.add(delMain);
			lista.remove(posMain);
			
			
		}
		if (!mainFinEncontrado) return null;
		
		lista.addAll(0,insDelMain);
		*/
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
	public Object ambitoNuevo() {
		String s= String.valueOf(numTempAmbito);
		numTempAmbito++;
		return s;
	}
	
	
	
}
