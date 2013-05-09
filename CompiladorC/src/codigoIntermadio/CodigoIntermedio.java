package codigoIntermadio;

import java.util.ArrayList;

public class CodigoIntermedio {

	
	ArrayList<InstruccionIntermedio> lista;
	int numTempVar;
	int numTempIf;
	
	
	public CodigoIntermedio(){
		numTempVar=0;
		numTempIf=0;
		lista= new ArrayList<InstruccionIntermedio>();
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
	
	
	public String tempNuevo(){
		String s= "t"+numTempVar;
		numTempVar++;
		return s;
	}
	
	public String ifNuevo(){
		String s= String.valueOf(numTempIf);
		numTempIf++;
		return s;
	}
	
	
	
}
