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

public class CondicionEsCompatible extends ElemUnario implements Condicion {

	
	
	
	
	public CondicionEsCompatible(Operacion op1, Operacion op2) {
		super();
		oper=new OperacionCompatibilizarTipos(op1,op2);
		
		
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper.getValor(listaAtrib, atribActual, ts);
		
		
		if (op1==null ) return false;
		else return true;
		
		
		
	}

	
		
		
	
	
}
