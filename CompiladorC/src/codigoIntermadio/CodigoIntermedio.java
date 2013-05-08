package codigoIntermadio;

import java.util.ArrayList;

public class CodigoIntermedio {

	
	ArrayList<InstruccionIntermedio> lista;
	int numTempVar;
	
	public CodigoIntermedio(){
		numTempVar=0;
		lista= new ArrayList<InstruccionIntermedio>();
	}
	
	
	public void addInstruccion (InstruccionIntermedio i){
		lista.add(i);
	}
	
	
	public String tempNuevo(){
		String s= "t"+numTempVar;
		numTempVar++;
		return s;
	}
	
	
	
}
