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
		
		Boolean esFun=false;
		Boolean esFunB=(Boolean)((HashMap)listaAtrib.get(1)).get("esFuncion");
		if (esFunB!=null)esFun=esFunB;
		
		ArrayList<InstruccionIntermedio> listaCodOriginal=(ArrayList<InstruccionIntermedio>) atribActual.get("codigo");
		
		ArrayList<InstruccionIntermedio> listaCod= new ArrayList<InstruccionIntermedio>();

		
		String lex = (String)((Token)listaAtrib.get(0)).getAtributo();
				
		
		EntradaTabla varAct=ts.busquedaCompleta(lex);
		Tipo tIden;
		
			if (esOper){//Es una operacion
				
				if (!esFun){
					Object nCorB=((HashMap)listaAtrib.get(1)).get("num"); // esta accion viene para prevenir el asunto de las lambdas que no llegan.
					int nCor=0;
					if (nCorB!=null)nCor = (Integer)nCorB;
					ArrayList<EntradaTabla> listaCor=null;
					if (nCor>0){
						listaCor = (ArrayList<EntradaTabla>)((HashMap)listaAtrib.get(1)).get("listaCorchete");
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
						varSig=ci.tempNuevo(new Tipo(tIden.getTipo(),tIden.getDim()-i-1)); // el tipo de la variable auxiliar es una indireccion menos por cada vuelta.
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
					EntradaTabla oper1=(EntradaTabla)((HashMap)listaAtrib.get(1)).get("lugar");
					
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
			
					
				
				}else {//es funcion
					AtributosTablaFuncion att= (AtributosTablaFuncion)varAct.getAtt();
					tIden=new Tipo(att.getTipoRet(),att.getDimRet());
					ArrayList<EntradaTabla> listParam= (ArrayList<EntradaTabla>)((HashMap)listaAtrib.get(1)).get("listaLugar");
					if (listParam!=null){
						
						for (Iterator<EntradaTabla> it=listParam.iterator();it.hasNext();){
		
							InsParam insparam=new InsParam();
							insparam.setParam(it.next());
							listaCod.add(insparam);
						}
						InsCall nuevaIns=new InsCall();
						listaCod.add( nuevaIns);
						nuevaIns.setDir("comienzoFun"+lex);
						nuevaIns.setNum(listParam.size());
						//Asignar retorno a variable auxiliar.
						InsAsigFun nuevaIns2=new InsAsigFun();
						listaCod.add( nuevaIns2);
						varAct=ci.tempNuevo(tIden);
						nuevaIns2.setRes(varAct);
						
					}
				}
			}
			
				
				atribActual.put("lugar", varAct);
				listaCodOriginal.addAll(listaCod);
				atribActual.put("codigo", listaCodOriginal);
				
		return null;
	}
		
		
		
	

}
