package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;

public class OperandoCrearVarTemp extends ElemUnario implements Operando {

	
	
	
	

	public OperandoCrearVarTemp(Operacion op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {
		// TODO Auto-generated method stub
		return ci.tempNuevo((Tipo)oper.getValor(listaAtrib, atribActual, ts, ci));
	}

}
