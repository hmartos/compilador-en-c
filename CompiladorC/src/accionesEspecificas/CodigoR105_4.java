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
import acciones.AccionGenCodigo;
import acciones.CondicionEsCompatible;
import acciones.OperandoCrearArrayList;
import acciones.OperandoDirecto;
import acciones.OperandoGramatica;
import acciones.Tipo;

public class CodigoR105_4 extends Accion {

	
	/*105. REXP4 -> */
	//105.4.  iden REXP3_2 RDEFINICION
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		Boolean esOper=false;
		Boolean esOperB=(Boolean)((HashMap)listaAtrib.get(1)).get("esOperacion");
		if (esOperB!=null)esOper=esOperB;
		
		
		
		ArrayList<InstruccionIntermedio> listaCodOriginal=(ArrayList<InstruccionIntermedio>) atribActual.get("codigo");
		
		ArrayList<InstruccionIntermedio> listaCod= new ArrayList<InstruccionIntermedio>();

		
		String lex = (String)((Token)listaAtrib.get(0)).getAtributo();
				
		
		String varAct=lex;
		
			if (esOper){//Es una operacion
				
				Object nCorB=((HashMap)listaAtrib.get(1)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
				int nCor=0;
				if (nCorB!=null)nCor = (Integer)nCorB;
				ArrayList<String> listaCor=null;
				if (nCor>0){
					listaCor = (ArrayList<String>)((HashMap)listaAtrib.get(1)).get("listaCorchete");
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
				
				
				//Ahora miramos que tipo de operacion es
				String operacion=(String)((HashMap)listaAtrib.get(1)).get("operacion");
				String oper1=(String)((HashMap)listaAtrib.get(1)).get("lugar");
				
				if (operacion.endsWith("=")){//Es una asignacion.
					if (operacion.equals("=")){
						 nuevaIns= new InsCuarteto();
						 listaCod.add(nuevaIns);
						 nuevaIns.setRes(varAct);
						 nuevaIns.setOp1(oper1);
						 
						
					}else{
						String operacion2=(String)((HashMap)listaAtrib.get(1)).get("operacion2");
						nuevaIns= new InsCuarteto();
						listaCod.add(nuevaIns);
						nuevaIns.setRes(varAct);
						 nuevaIns.setOp1(varAct);
						 nuevaIns.setOpRel(operacion2);
						 nuevaIns.setOp2(oper1);
					}
				}else {
					//Aqui irian operaciones que no fueran asignacion....
				}
		
			}
			
				listaCodOriginal.addAll(listaCod);
				atribActual.put("codigo", listaCodOriginal);
				atribActual.put("lugar", varAct);
		return null;
	}
		
		
		
	

}
