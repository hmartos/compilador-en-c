package acciones;

import java.util.ArrayList;
import java.util.HashMap;


import tablaSimbolos.TablaSimbolos;

public class OperacionHeredada extends ElemBinario implements Operacion {

	
	String operacion;

	public OperacionHeredada(Operacion op1, Operacion op2,String operacion) {
		super(op1, op2);
		this.operacion=operacion;
		// TODO Auto-generated constructor stub
	}

	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		
		if ((op1 instanceof Integer)&& (op2 instanceof Integer)){
			Integer opInt1= (Integer)op1;
			Integer opInt2=(Integer)op2;
			if (operacion.equals("suma")){
				return opInt1+opInt2;
			}else if (operacion.equals("resta")){
				return opInt1-opInt2;
			}
		}
		return null;
	}

}
