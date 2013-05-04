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
import acciones.CondicionEsCompatible;
import acciones.OperacionCompatibilizarTipos;
import acciones.OperandoDirecto;
import acciones.OperandoGramatica;
import acciones.Tipo;

public class AccionR105_4 extends Accion {

/*105. REXP4 -> */
	//105.4.  iden REXP3_2 RDEFINICION
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();
		
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		int colErr = (Integer)(atribActual.get("colInicio"));
		
		int rowErrDeclaracion = (Integer)((HashMap)listaAtrib.get(2)).get("filaInicio");
		int colErrDeclaracion = (Integer)((HashMap)listaAtrib.get(2)).get("colInicio");
		
		int rowErrOperacion = (Integer)((HashMap)listaAtrib.get(1)).get("filaInicio");
		int colErrOperacion = (Integer)((HashMap)listaAtrib.get(1)).get("colInicio");
		
		Boolean esOperacion=false;
		Boolean esOperacionB=(Boolean)((HashMap)listaAtrib.get(1)).get("esOperacion");
		if (esOperacionB!=null)esOperacion=esOperacionB;
		
		Boolean esDeclaracion=false;
		Boolean esDeclaracionB=(Boolean)((HashMap)listaAtrib.get(2)).get("esDeclaracion");
		if (esDeclaracionB!=null)esDeclaracion=esDeclaracionB;
		
		
		if (esOperacion && esDeclaracion){
			//Error, no puede declararse y operarse a la vez.
			atribActual.put("error", true);
	
			listErr.add(new ErrorSemantico(rowErrDeclaracion,colErrDeclaracion,"No se esperaba una declaración."));
		}
		else if (esOperacion){ //tiene que hacer lo mismo que en la 15.1
			
			
			Boolean esFun=false;
			Boolean esFunB=(Boolean)((HashMap)listaAtrib.get(1)).get("esFuncion");
			if (esFunB!=null)esFun=esFunB;
			
			String lex = (String)((Token)listaAtrib.get(0)).getAtributo();
			
			EntradaTabla entTabla =ts.busquedaCompleta(lex);
			Atributo at;
			if (entTabla!=null)  at= entTabla.getAtt();
			else at=null;
			
			if (at instanceof AtributosTablaVariable ){ //Es una variable 
				if (!esFun){
					AtributosTablaVariable atv= (AtributosTablaVariable)at;
					Object nCorB=((HashMap)listaAtrib.get(1)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
					int nCor=0;
					if (nCorB!=null)nCor = (Integer)nCorB;
					
					if (nCor<=atv.getDim()){
						if (new CondicionEsCompatible(new OperandoDirecto(new Tipo(atv.getTipo(),atv.getDim()-(nCor))),new OperandoGramatica(1,"tipo")).getValor(listaAtrib, atribActual, ts)){
							atribActual.put("tipo", new OperacionCompatibilizarTipos(new OperandoDirecto(new Tipo(atv.getTipo(),atv.getDim()-(nCor))),new OperandoGramatica(1,"tipo")).getValor(listaAtrib, atribActual, ts));
							atribActual.put("esFuncion", false);
						}else{
							atribActual.put("error", true);
							listErr.add(new ErrorSemantico(rowErr,colErr,"Los tipos no son compatibles."));
						}
					}else{
						atribActual.put("error", true);
						listErr.add(new ErrorSemantico(rowErr,colErr,"El número de indirecciones ("+(atv.getDim()-(nCor))+ ") es incorrecto"));
					}	
				}else {
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"La función "+lex+" no está definida."));
				}
			
			
			}  else if (at instanceof AtributosTablaFuncion){// Es una funcion
				if (esFun){	
					AtributosTablaFuncion atf= (AtributosTablaFuncion)at;
					ArrayList<Tipo> listParam= (ArrayList<Tipo>)((HashMap)listaAtrib.get(1)).get("lista");
					
					boolean correcto=true;
					int i=0;
					while (correcto && i<atf.getnCampos()){
						correcto=atf.getListaTipos().get(i).equals(listParam.get(i).getTipo()) 
								&& atf.getListaDim().get(i).equals(listParam.get(i).getDim());
						i++;
					}
					if (correcto){
						
						
						atribActual.put("tipo", new Tipo(atf.getTipoRet(),atf.getDimRet()));
						atribActual.put("esFuncion", true);

						
		
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
			
		} else if (esDeclaracion){
			
			
			
			
		}
		
		
		return listErr;
	}
		
		
	

}
