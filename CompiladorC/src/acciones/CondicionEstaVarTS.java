package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class CondicionEstaVarTS extends ElemUnario implements Condicion {

	public CondicionEstaVarTS(Operacion op1) {
		super();
		oper=new OperacionVarTS(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		
		Object op1=oper.getValor(listaAtrib, atribActual, ts, ci);
		
		if (op1!=null && op1 instanceof Tipo) return true;
		
		return false;
	}

}
