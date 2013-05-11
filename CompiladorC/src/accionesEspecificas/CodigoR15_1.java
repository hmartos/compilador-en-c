package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InsAsigFun;
import codigoIntermadio.InsCall;
import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InsParam;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.AccionAsignar;
import acciones.CondicionEsCompatible;
import acciones.OperandoCrearArrayList;
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
		
	ArrayList<InstruccionIntermedio> listaCodOriginal=(ArrayList<InstruccionIntermedio>) atribActual.get("codigo");
		
		ArrayList<InstruccionIntermedio> listaCod= new ArrayList<InstruccionIntermedio>();
		
		
		
		String lex = (String)((Token)listaAtrib.get(2)).getAtributo();
				
		

				
		
		EntradaTabla varAct=ts.busquedaCompleta(lex);
		Tipo tIden=null;
			if (!esFun){//Es una variable 
				
				
				
				
				Object nCorB=((HashMap)listaAtrib.get(3)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
				int nCor=0;
				if (nCorB!=null)nCor = (Integer)nCorB;
				ArrayList<EntradaTabla> listaCor=null;
				if (nCor>0){
					listaCor = (ArrayList<EntradaTabla>)((HashMap)listaAtrib.get(3)).get("listaCorchete");
				}
				
				
				AtributosTablaVariable att= (AtributosTablaVariable)varAct.getAtt();
				tIden=new Tipo(att.getTipo(),att.getDim());
				EntradaTabla varSig;
				InsCuarteto nuevaIns;
				/*
				varSig=ci.tempNuevo();
				nuevaIns= new InsCuarteto();
				listaCod.add(nuevaIns);
				nuevaIns.setOp1(varAct);
				nuevaIns.setRes(varSig);
				varAct=varSig;
				*/
				//Vamos haciendo una operacion =[] por cada corchete que aparezca.
				for (int i=0;i<nCor;i++){
					tIden.setDim(tIden.getDim()-1);
					varSig=ci.tempNuevo(tIden); // el tipo de la variable auxiliar es una indireccion menos por cada vuelta.

					 nuevaIns= new InsCuarteto();
					listaCod.add(nuevaIns);
					nuevaIns.setOp1(varAct);
					nuevaIns.setOpRel("=[]");
					nuevaIns.setOp2(listaCor.get(i));
					nuevaIns.setRes(varSig);
					
					varAct=varSig; //Avanzamos la variable.
					
				}
				
				
		// Es una funcion
			}else if (esFun){	
			 //De donde devuelve el lugar??.
				
				AtributosTablaFuncion att= (AtributosTablaFuncion)varAct.getAtt();
				tIden=new Tipo(att.getTipoRet(),att.getDimRet());
				ArrayList<EntradaTabla> listParam= (ArrayList<EntradaTabla>)((HashMap)listaAtrib.get(3)).get("listaLugar");
				if (listParam!=null){
					
					for (Iterator<EntradaTabla> it=listParam.iterator();it.hasNext();){
	
						InsParam insparam=new InsParam();
						insparam.setParam(it.next());
						listaCod.add(insparam);
					}
					//el call
					InsCall nuevaIns=new InsCall();
					listaCod.add( nuevaIns);
					nuevaIns.setDir(lex);
					nuevaIns.setNum(listParam.size());
					//Asignar retorno a variable auxiliar.
					InsAsigFun nuevaIns2=new InsAsigFun();
					listaCod.add( nuevaIns2);
					varAct=ci.tempNuevo(tIden);
					nuevaIns2.setRes(varAct);
					
					
				}
				
				
				
				
			}
			
			
			
			Object nIndB=((HashMap)listaAtrib.get(1)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
			int nInd=0;
			if (nIndB!=null)nInd = (Integer)nIndB;
			
			
			//La varAct vendrá de los corchetes (en caso de variable) o el lugar devuelto por la funcion.
			for (int i=0;i<nInd;i++){
				tIden.setDim(tIden.getDim()-1);
				EntradaTabla varSig=ci.tempNuevo(tIden); // el tipo de la variable auxiliar es una indireccion menos por cada vuelta.
				InsCuarteto nuevaIns= new InsCuarteto();
				listaCod.add(nuevaIns);
				nuevaIns.setOp1(varAct);
				nuevaIns.setOpRel("*");
				nuevaIns.setRes(varSig);
				
				varAct=varSig; //Avanzamos la variable.
				
			}	
				
			listaCodOriginal.addAll(listaCod);
			atribActual.put("codigo", listaCodOriginal);
				atribActual.put("lugar", varAct);
		return null;
	}
		
		
		
	

}
