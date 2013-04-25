package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaEnum;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.AtributosTablaStruct;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.TablaSimbolos;

public class OperacionClaseEntradaTS extends ElemUnario implements Operacion {

	public OperacionClaseEntradaTS(Operando op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tipo getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		Object op=oper.getValor(listaAtrib, atribActual, ts);
		
		
		
		
		if (op instanceof String ){
			return calcularTipo(ts,(String)op);
			
		}
		return null;
	}
	
	
	
	private Tipo calcularTipo( TablaSimbolos ts,String lex){
		
		
		Atributo attTabla =ts.busquedaCompleta(lex).getAtt();
		
		
		 if (attTabla instanceof AtributosTablaVariable){
			return new Tipo (((AtributosTablaVariable)attTabla).getTipo(),((AtributosTablaVariable)attTabla).getDim());
		}
		
		else if (attTabla instanceof AtributosTablaFuncion){
			return new Tipo (((AtributosTablaFuncion)attTabla).getTipoRet(),((AtributosTablaFuncion)attTabla).getDimRet());

		}
		return null;
	}
		
	
	
	
	
	
}
