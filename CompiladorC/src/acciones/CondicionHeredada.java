package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class CondicionHeredada extends ElemBinario implements Condicion  {

	String operacion;
	
	public CondicionHeredada(Operacion op1, Operacion op2,String operacion) {
		super(op1, op2);
		// TODO Auto-generated constructor stub
		this.operacion=operacion;
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		//Metodo para añadir operaciones para los distintos objetos
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		
		//Operador generico
		
		if ((op1 instanceof Boolean) && (op2 instanceof Boolean)){
			Boolean opBool1= (Boolean) op1;
			Boolean opBool2= (Boolean) op2;

			if (operacion.equals("and")){
				return opBool1 && opBool2;
			}else if (operacion.equals("or")){
				return opBool1 || opBool2;
			}
		}
		if (op1!=null && op2!=null){
			if (operacion.equals("igual")){
				return op1.equals(op2);
				
			}else if (operacion.equals("distinto")){
				return !op1.equals(op2);
			}
		} else if (op1==null && op2== null) return true;
		return false;
		
		/*
		if (op1 instanceof Integer)&& (op2 instanceof Integer)){
			Integer opInt1= (Integer)op1;
			Integer opInt2=(Integer)op2;
			if (operacion.equals("igual")){
				return opInt1==opInt2;
			}else if (operacion.equals("distinto")){
				return opInt1!=opInt2;
			}
		}
		
		else if ((op1 instanceof String)&& (op2 instanceof String)){
			String opString1= (String)op1;
			String opString2=(String)op2;
			if (operacion.equals("igual")){
				return opString1.equals(op2);
				
			}else if (operacion.equals("distinto")){
				return !op1.equals(op2);
			}
		}*/
		
		
	}

}
