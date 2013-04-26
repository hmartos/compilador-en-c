package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperacionSumarDimTipo extends ElemBinario implements Operacion {
	
	
	public OperacionSumarDimTipo(Operacion op1, Operacion op2) {
		super(op1, op2);
	
		
	}
	

	@Override
	public Tipo getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		
		if (op1 instanceof Tipo && op2 instanceof Integer){
			Tipo t1=(Tipo)op1;
			return new Tipo(t1.getTipo(),t1.getDim()+(Integer)op2);
			
		}
		return null;
	}

}
