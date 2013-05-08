package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;

public class OperandoCrearVarTemp implements Operando{

	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {
		// TODO Auto-generated method stub
		return ci.tempNuevo();
	}

}
