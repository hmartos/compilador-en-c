package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaEnum;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.AtributosTablaStruct;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class OperacionClaseEntradaTS extends ElemUnario implements Operacion {

	public OperacionClaseEntradaTS(Operacion op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		
		Object op=oper.getValor(listaAtrib, atribActual, ts, ci);
		
		
		
		String lex=null;
		if (op instanceof String ){
			lex=(String)op;
			
		}else if (op instanceof Tipo){
			lex= ((Tipo)op).getTipo();
		}
		
		
		if (lex!=null){
			EntradaTabla entTabla =ts.busquedaCompleta(lex);
			Atributo attTabla;
			if (entTabla!=null)  attTabla= entTabla.getAtt();
			else return null;
			
			
			 if (attTabla instanceof AtributosTablaVariable){
				return "variable";
			}
			
			else if (attTabla instanceof AtributosTablaFuncion){
				return "funcion";	
			}
			else if (attTabla instanceof AtributosTablaPalRes){
				return "palres";	
			}
			else if (attTabla instanceof AtributosTablaEnum){
				return "enum";	
			}
			else if (attTabla instanceof AtributosTablaStruct){
				return "struct";	
			}
			else if (attTabla instanceof AtributosTablaTypeDef){
				return "typedef";	
			}
			return null;
		}
		return null;
	}
	
	
	
	
		
	
	
	
	
	
}
