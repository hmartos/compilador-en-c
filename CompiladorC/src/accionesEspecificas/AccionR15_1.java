package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR15_1 extends Accion {

	
	// 15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		String lex = (String)((Token)listaAtrib.get(2)).getAtributo();
		Atributo at= ts.busquedaCompleta(lex).getAtt();
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		
		if (at instanceof AtributosTablaVariable){
			AtributosTablaVariable atv= (AtributosTablaVariable)at;
			int nCor = (Integer)((HashMap)listaAtrib.get(3)).get("num");
			int nAst = (Integer)((HashMap)listaAtrib.get(1)).get("num");
			int nAmp = ((HashMap)listaAtrib.get(1)).get("tieneAmp").equals("true") ? 1:0;
			atv.getDim();
			if (nCor+nAst-nAmp<=atv.getDim()){
				atribActual.put("tipo", new Tipo(atv.getTipo(),atv.getDim()-(nCor+nAst-nAmp)));
			}
			else listErr.add(new ErrorSemantico("El número de indirecciones ("+(atv.getDim()-(nCor+nAst-nAmp))+ ") es incorrecto"));
		} else if (at instanceof AtributosTablaFuncion){
			AtributosTablaFuncion atf= (AtributosTablaFuncion)at;
			ArrayList<Tipo> listParam= (ArrayList<Tipo>)((HashMap)listaAtrib.get(3)).get("lista");
			
			boolean correcto=true;
			int i=0;
			while (correcto && i<atf.getnCampos()){
				correcto=atf.getListaTipos().get(i).equals(listParam.get(i).getTipo()) 
						&& atf.getListaDim().get(i).equals(listParam.get(i).getDim());
				i++;
			}
			if (correcto){
				int nAst = (Integer)((HashMap)listaAtrib.get(1)).get("num");
				int nAmp = ((HashMap)listaAtrib.get(1)).get("tieneAmp").equals("true") ? 1:0;
				if (nAst-nAmp<=atf.getDimRet()){
					atribActual.put("tipo", new Tipo(atf.getTipoRet(),atf.getDimRet()-(nAst-nAmp)));

				} else listErr.add(new ErrorSemantico("El número de indirecciones ("+(atf.getDimRet()-(nAst-nAmp))+ ") es incorrecto"));

			} else listErr.add(new ErrorSemantico("Los tipos no concuerdan en los parametros de la funcion "+ lex));
			
		} else listErr.add(new ErrorSemantico("Se esperaba una variable o funcion"));
		return listErr;
	}
		
		
		
	

}
