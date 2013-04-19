package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperacionTipoTS extends ElemUnario implements Operacion {

	public OperacionTipoTS(Operando op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		Atributo ts.busquedaCompleta((String)oper.getValor(listaAtrib, atribActual, ts));
		
	}

}
