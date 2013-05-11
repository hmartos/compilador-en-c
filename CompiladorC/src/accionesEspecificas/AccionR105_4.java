package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaTypeDef;
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
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();
		
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		int colErr = (Integer)(atribActual.get("colInicio"));
		
		
		
		String lex = (String)((Token)listaAtrib.get(0)).getAtributo();
		
		Boolean esOperacion=false;
		Boolean esOperacionB=(Boolean)((HashMap)listaAtrib.get(1)).get("esOperacion");
		if (esOperacionB!=null)esOperacion=esOperacionB;
		
		Boolean esDeclaracion=false;
		Boolean esDeclaracionB=(Boolean)((HashMap)listaAtrib.get(2)).get("esDeclaracion");
		if (esDeclaracionB!=null)esDeclaracion=esDeclaracionB;
		
		
		if (esOperacion && esDeclaracion){
			//Error, no puede declararse y operarse a la vez.
			atribActual.put("error", true);
	
			
			int rowErrDeclaracion = (Integer)((HashMap)listaAtrib.get(2)).get("filaInicio");
			int colErrDeclaracion = (Integer)((HashMap)listaAtrib.get(2)).get("colInicio");
			listErr.add(new ErrorSemantico(rowErrDeclaracion,colErrDeclaracion,"No se esperaba una declaración."));
		}
		else if (esOperacion){ //tiene que hacer lo mismo que en la 15.1
			
			
			Boolean esFun=false;
			Boolean esFunB=(Boolean)((HashMap)listaAtrib.get(1)).get("esFuncion");
			if (esFunB!=null)esFun=esFunB;
			
			
			
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
						if (new CondicionEsCompatible(new OperandoDirecto(new Tipo(atv.getTipo(),atv.getDim()-(nCor))),new OperandoGramatica(1,"tipo")).getValor(listaAtrib, atribActual, ts, ci)){
							atribActual.put("tipo", new OperacionCompatibilizarTipos(new OperandoDirecto(new Tipo(atv.getTipo(),atv.getDim()-(nCor))),new OperandoGramatica(1,"tipo")).getValor(listaAtrib, atribActual, ts, ci));
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
			
			
			
			int nAst=0;
			Object nAstB=((HashMap)listaAtrib.get(1)).get("num");
			if (nAstB!=null)nAst=(Integer)nAstB;
			
			
			
			Boolean esFun = (Boolean)((HashMap)listaAtrib.get(2)).get("esFuncion");
			
			EntradaTabla et=ts.busquedaCompleta(lex);
			Atributo at=null;
			if (et!=null){
				at=et.getAtt();
			}
			
			if (at instanceof AtributosTablaTypeDef){
			Tipo tipo0 = new Tipo(lex,nAst);
				
				
				if (!esFun){	// Es una declaracion de variable.
					
					int numCorchetes = (Integer)((HashMap)listaAtrib.get(2)).get("num");
					
					
					tipo0.setDim(tipo0.getDim()+numCorchetes);
					//Aqui habría un error en la gramatica por el que solo se puede poner corchetes en la primera variable.
					
					ArrayList<Object> listaVar = (ArrayList<Object>)((HashMap)listaAtrib.get(2)).get("listaVar");
					ArrayList<Object> listaValor = (ArrayList<Object>)((HashMap)listaAtrib.get(2)).get("listaValor");
					boolean valido =true;
					for (Iterator<Object> itVal=listaValor.iterator();itVal.hasNext();){
						Object o= itVal.next();
						if (o!=null && !tipo0.equals(o)){
							valido=false;
							
	
							listErr.add(new ErrorSemantico(rowErr,colErr,"No coinciden los tipos: "+tipo0.toString()+" y "+ o.toString()));
							atribActual.put("error", true);
						}
					}
					if (valido){
						for (Iterator<Object> itVar=listaVar.iterator();itVar.hasNext();){
							Object lex2= itVar.next();
							if(ts.busquedaAmbito((String)lex2)==null){
								ts.insertar((String)lex2);
								ts.añadirAtributos((String)lex2, new AtributosTablaVariable(tipo0.getTipo(),tipo0.getDim(),null));
							}else {
								atribActual.put("error", true);
								listErr.add(new ErrorSemantico(rowErr,colErr,"Ya existe la variable: "+lex2.toString()+" en este contexto."));
							}
	
						}
					
					}
					
					
					
				}else{ // Es una declaracion de funcion.
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"No se puede definir una función en este contexto."));
				}
			}else{ // No es un typedef.
				atribActual.put("error", true);
				listErr.add(new ErrorSemantico(rowErr,colErr,"No está definido el tipo "+lex));
			}
			
			
			
			
			
			
			
		}
		
		
		return listErr;
	}
		
		
	

}
