package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import token.Token;

public class OperandoGramatica implements Operando {
	
	int oper;
	String atrib;
	public OperandoGramatica(int i,String s){
		
	}
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		Object emi;
		if (oper<=-1) emi=atribActual; //es atribActual
		else emi=listaAtrib.get(oper);// es de listaAtrib
		
		if (emi instanceof HashMap){// es una lista de atributos de un NT
			return ((HashMap<String, Object>) emi).get(atrib);
		}else if (emi instanceof Token){// es un token de un T
			return ((Token)emi).getAtributo();  
		
		}
		return null;
	}

}
