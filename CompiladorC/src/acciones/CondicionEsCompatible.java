package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.AtributosTablaStruct;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.TablaSimbolos;
import token.Token;

public class CondicionEsCompatible extends ElemUnario implements Condicion {

	Operacion op1;
	Operacion op2;
	
	
	
	public CondicionEsCompatible(Operacion op1, Operacion op2) {
		super();
		this.op1=op1;
		this.op2=op2;
		oper=new OperacionCompatibilizarTipos(op1,op2);
		
		
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		Object compat=oper.getValor(listaAtrib, atribActual, ts, ci);
		Object t1 =op1.getValor(listaAtrib, atribActual, ts, ci);
		Object t2 =op2.getValor(listaAtrib, atribActual, ts, ci);
		
		if (t1==null && t2==null) return true;
		
		if (compat==null ) return false;
		else return true;
		
		
		
	}

	
		
		
	
	
}
