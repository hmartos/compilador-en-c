package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaEnum;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.AtributosTablaStruct;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class OperacionTipoTS extends ElemUnario implements Operacion {

	public OperacionTipoTS(Operacion op1) {
		super(op1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts, CodigoIntermedio ci) {
		
		Object op=oper.getValor(listaAtrib, atribActual, ts, ci);
		
		
		
		
		if (op instanceof String ){
			if (op.equals("vacio")||op.equals("error")) return op;
			return calcularTipo(ts,(String)op);
			
		} else if (op instanceof Tipo){
			Tipo nt= calcularTipo(ts,((Tipo)op).getTipo());
			nt.setDim(nt.getDim()+((Tipo)op).getDim());
			return nt;
		}
		return null;
	}
	
	
	
	private Tipo calcularTipo( TablaSimbolos ts,String lex){
		
		
		EntradaTabla entTabla =ts.busquedaCompleta(lex);
		Atributo attTabla;
		if (entTabla!=null)  attTabla= entTabla.getAtt();
		else return null;
		
		if (attTabla instanceof AtributosTablaTypeDef){
			String lex2= ((AtributosTablaTypeDef)attTabla).getTipoRel();
			Tipo t=calcularTipo(ts,lex2);
			if (t!=null){
				return new Tipo(t.getTipo(),t.getDim()+((AtributosTablaTypeDef)attTabla).getDim());
			}else return null;
		}
		else if (attTabla instanceof AtributosTablaStruct){
			return new Tipo(lex,0);
		}
		else if (attTabla instanceof AtributosTablaEnum){
			
			return new Tipo(lex,0);
		}
		else if (attTabla instanceof AtributosTablaVariable){
			return null;
		}
		
		else if (attTabla instanceof AtributosTablaPalRes){
			String lex2= ((AtributosTablaPalRes)attTabla).getLexema();
			if (lex2.equals("int")||lex2.equals("float")||lex2.equals("char")||lex2.equals("double")){
				return new Tipo(lex2,0);
			}
			else return null;
		}
		return null;
	}
		
	
	
	
	
	
}
