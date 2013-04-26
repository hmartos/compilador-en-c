package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperacionAgregarALista extends ElemBinario implements Operacion {

	public OperacionAgregarALista(Operacion op1, Operacion op2) {
		super(op1, op2);
	}
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		if (op1 instanceof ArrayList){
			((ArrayList<Object>) op1).add(0, op2);
			return op1;
		}
		return null; //o error?
	}

}
