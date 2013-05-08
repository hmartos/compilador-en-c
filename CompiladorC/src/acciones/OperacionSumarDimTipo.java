package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class OperacionSumarDimTipo extends ElemBinario implements Operacion {
	
	
	public OperacionSumarDimTipo(Operacion op1, Operacion op2) {
		super(op1, op2);
	
		
	}
	

	@Override
	public Tipo getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		// TODO Auto-generated method stub
		
		Object op1=oper1.getValor(listaAtrib, atribActual, ts, ci);
		Object op2=oper2.getValor(listaAtrib, atribActual, ts, ci);
		
		if (op1 instanceof Tipo && op2 instanceof Integer){
			Tipo t1=(Tipo)op1;
			return new Tipo(t1.getTipo(),t1.getDim()+(Integer)op2);
			
		}
		return null;
	}

}
