package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR15_1 extends Accion {

	
	// 15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		Boolean esFun=false;
		Boolean esFunB=(Boolean)((HashMap)listaAtrib.get(3)).get("esFuncion");
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		int colErr = (Integer)(atribActual.get("colInicio"));
		
		if (esFunB!=null)esFun=esFunB;

		
		String lex = (String)((Token)listaAtrib.get(2)).getAtributo();
		
		EntradaTabla entTabla =ts.busquedaCompleta(lex);
		Atributo at;
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();
		if (entTabla!=null)  at= entTabla.getAtt();
		else at=null;
		
		 
		
		
		if (at instanceof AtributosTablaVariable ){ //Es una variable 
			if (!esFun){
				AtributosTablaVariable atv= (AtributosTablaVariable)at;
				Object nCorB=((HashMap)listaAtrib.get(3)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
				int nCor=0;
				if (nCorB!=null)nCor = (Integer)nCorB;
				
				int nAst = (Integer)((HashMap)listaAtrib.get(1)).get("num");
				Boolean tieneAmp =(Boolean)((HashMap)listaAtrib.get(0)).get("tieneAmp");
				int nAmp = tieneAmp ? 1:0;
				atv.getDim();
				if (nCor+nAst-nAmp<=atv.getDim()){
					atribActual.put("tipo", new Tipo(atv.getTipo(),atv.getDim()-(nCor+nAst-nAmp)));
					atribActual.put("esFuncion", false);
				}
				else {
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"El número de indirecciones ("+(atv.getDim()-(nCor+nAst-nAmp))+ ") es incorrecto"));
				}
			}else {
				atribActual.put("error", true);
				listErr.add(new ErrorSemantico(rowErr,colErr,"La función "+lex+" no está definida."));
			}
		} else if (at instanceof AtributosTablaFuncion){// Es una funcion
			if (esFun){	
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
						atribActual.put("esFuncion", true);

					} else {
						atribActual.put("error", true);
						listErr.add(new ErrorSemantico(rowErr,colErr,"El número de indirecciones ("+(atf.getDimRet()-(nAst-nAmp))+ ") es incorrecto"));
					}
	
				} else {
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"Los tipos no concuerdan en los parametros de la funcion "+ lex));
				}
			}else{
				atribActual.put("error", true);
				listErr.add(new ErrorSemantico(rowErr,colErr,"La variable "+lex+" no está definida."));
			}
		} else {
			atribActual.put("error", true);
			listErr.add(new ErrorSemantico(rowErr,colErr,"El identificador "+lex+" no está definido."));
		}
		return listErr;
	}
		
		
		
	

}
