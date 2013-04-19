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
		super(op1, op2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper1.getValor(listaAtrib, atribActual, ts);
		if (op1 instanceof String ){
			String opString1=(String) op1;
			String opString2=(String) op2;
			Atributo attTabla =ts.busquedaCompleta(opString1).getAtt();
			if (attTabla instanceof AtributosTablaTypeDef);
			else if (attTabla instanceof AtributosTablaStruct);
			else if (attTabla instanceof AtributosTablaPalRes){
				String lex= ((AtributosTablaPalRes)attTabla).getLexema();
				if (lex.equals("int")||lex.equals("float")||)
			}
		}
		return true;
	}

	
		
		
	
	
}
