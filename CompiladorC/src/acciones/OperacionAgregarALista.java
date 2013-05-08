package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class OperacionAgregarALista extends ElemBinario implements Operacion {

	public OperacionAgregarALista(Operacion lista, Operacion elemento) {
		super(lista, elemento);
	}
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts, ci);
		Object op2=oper2.getValor(listaAtrib, atribActual, ts, ci);
		if (op1 instanceof ArrayList){
			if (op2 instanceof ArrayList){
				((ArrayList<Object>) op1).addAll(0, (ArrayList<Object>)op2);
			}else {
				((ArrayList<Object>) op1).add(0, op2);
			}
			return op1;
		}
		return null; 
	}

}
