package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.CondicionEsCompatible;
import acciones.OperandoDirecto;
import acciones.Tipo;

public class CodigoR15_1 extends Accion {

	
	// 15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		Boolean esFun=false;
		Boolean esFunB=(Boolean)((HashMap)listaAtrib.get(3)).get("esFuncion");
		if (esFunB!=null)esFun=esFunB;
		
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		int colErr = (Integer)(atribActual.get("colInicio"));
		
		

		
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

				if (nCor+nAst-nAmp<=atv.getDim()){
					atribActual.put("tipo", new Tipo(atv.getTipo(),atv.getDim()-(nCor+nAst-nAmp)));
					atribActual.put("esFuncion", false);
				}
				else {
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"El número de indirecciones ("+(atv.getDim()-(nCor+nAst-nAmp))+ ") para el identificador "+lex+" es incorrecto"));
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
				if (listParam==null) correcto=false;
				while (correcto && i<Math.max(atf.getnCampos(),listParam.size())){
					Tipo tPasado=null;
					if (i<listParam.size())tPasado= listParam.get(i);
					
					Tipo tReciv=null;
					if (i<atf.getnCampos()){
						tReciv=new Tipo(atf.getListaTipos().get(i),atf.getListaDim().get(i));
						
					}
					correcto= (tPasado!=null)&&(tReciv!=null)&& new CondicionEsCompatible(new OperandoDirecto(tPasado), new OperandoDirecto(tReciv)).getValor(listaAtrib, atribActual, ts, ci);
					i++;
				}
				if (correcto){
					int nAst = (Integer)((HashMap)listaAtrib.get(1)).get("num");
					int nAmp = ((HashMap)listaAtrib.get(0)).get("tieneAmp").equals("true") ? 1:0;
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
