package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InsCuarteto;
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
				
		
		String varAct=lex;
		
			if (!esFun){//Es una variable 
				
				
				
				
				Object nCorB=((HashMap)listaAtrib.get(3)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
				int nCor=0;
				if (nCorB!=null)nCor = (Integer)nCorB;
				ArrayList<String> listaCor=null;
				if (nCor>0){
					listaCor = (ArrayList<String>)((HashMap)listaAtrib.get(3)).get("listaCorchete");
				}
				
				
				String varSig;
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
					 varSig=ci.tempNuevo();
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
			}
			
			
			
			Object nIndB=((HashMap)listaAtrib.get(1)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
			int nInd=0;
			if (nIndB!=null)nInd = (Integer)nIndB;
			
			
			//La varAct vendr� de los corchetes (en caso de variable) o el lugar devuelto por la funcion.
			for (int i=0;i<nInd;i++){
				String varSig=ci.tempNuevo();
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
