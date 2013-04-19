package acciones;

import java.util.ArrayList;
import java.util.HashMap;


import tablaSimbolos.TablaSimbolos;

public class OperacionHeredada extends ElemBinario implements Operacion {

	
	public OperacionHeredada(Operando op1, Operando op2) {
		super(op1, op2);
		// TODO Auto-generated constructor stub
	}

	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
