package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

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
		// TODO Auto-generated method stub
		return true;
	}

	
		
		
	
	
}
