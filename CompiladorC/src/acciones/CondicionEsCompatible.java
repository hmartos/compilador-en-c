package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.AtributosTablaStruct;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.TablaSimbolos;
import token.Token;

public class CondicionEsCompatible extends ElemBinario implements Condicion {

	
	
	
	
	public CondicionEsCompatible(Operando op1, Operando op2) {
		super();
		oper1=new OperacionTipoTS(op1);
		oper2=new OperacionTipoTS(op2);
		
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		
		if (op1==null || op2==null) return false;
		Tipo t1, t2;
		
		t1=(Tipo) op1;
		t2=(Tipo) op2;
		if (t1.equals(t2)){
			return true;
		}else if (t1.getDim()==t2.getDim()){
			if (t1.getTipo().equals("char")&&t2.getTipo().equals("int")) return true;
			if (t1.getTipo().equals("int")&&t2.getTipo().equals("char")) return true;
			if (t1.getTipo().equals("float")&&t2.getTipo().equals("int")) return true;
			if (t1.getTipo().equals("int")&&t2.getTipo().equals("float")) return true;
			if (t1.getTipo().equals("double")&&t2.getTipo().equals("int")) return true;
			if (t1.getTipo().equals("int")&&t2.getTipo().equals("double")) return true;
			if (t1.getTipo().equals("double")&&t2.getTipo().equals("float")) return true;
			if (t1.getTipo().equals("float")&&t2.getTipo().equals("float")) return true;

		}
		return false;
		
	}

	
		
		
	
	
}
