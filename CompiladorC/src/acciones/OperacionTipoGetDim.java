package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperacionTipoGetDim extends ElemUnario implements Operacion{

	
	

	public OperacionTipoGetDim(Operacion op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		Object op1=oper.getValor(listaAtrib, atribActual, ts);
		if (op1 instanceof Tipo) return ((Tipo)op1).getDim();
		return null;
	}

}
