package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class OperandoCrearTipo extends ElemBinario implements Operando {

	
	public OperandoCrearTipo(String tipo, int dim){
		oper1=new OperandoDirecto(tipo);
		oper2= new OperandoDirecto (dim);
	}
	
	public OperandoCrearTipo(Operacion op1, Operacion op2){
		oper1=op1;
		oper2=op2;
	}
	
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		Object op1= oper1.getValor(listaAtrib, atribActual, ts, ci);
		Object op2= oper2.getValor(listaAtrib, atribActual, ts, ci);
		if (op1 instanceof String && op2 instanceof Integer){
		
			return new Tipo((String)op1,(Integer)op2);
		}
		return null;
	}

}
