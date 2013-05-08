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

public class OperacionVarTS extends ElemUnario implements Operacion {

	public OperacionVarTS(Operacion op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tipo getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		
		Object op=oper.getValor(listaAtrib, atribActual, ts, ci);
		
		
		
		
		if (op instanceof String ){
			return calcularTipo(ts,(String)op);
			
		}
		return null;
	}
	
	
	
	private Tipo calcularTipo( TablaSimbolos ts,String lex){
		
		
		EntradaTabla entTabla =ts.busquedaCompleta(lex);
		Atributo attTabla;
		if (entTabla!=null)  attTabla= entTabla.getAtt();
		else return null;
		
		
		 if (attTabla instanceof AtributosTablaVariable){
			return new Tipo (((AtributosTablaVariable)attTabla).getTipo(),((AtributosTablaVariable)attTabla).getDim());
		}
		
		else if (attTabla instanceof AtributosTablaFuncion){
			return new Tipo (((AtributosTablaFuncion)attTabla).getTipoRet(),((AtributosTablaFuncion)attTabla).getDimRet());

		}
		return null;
	}
		
	
	
	
	
	
}
