package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperandoDirecto implements Operando {
	
	Object oper;
	public OperandoDirecto (Object a){
		oper=a;
	}
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		return oper;
	}

}
