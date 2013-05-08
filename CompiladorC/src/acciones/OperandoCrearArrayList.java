package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class OperandoCrearArrayList implements Operando{

	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		
		return new ArrayList<Object>();
	}

}
