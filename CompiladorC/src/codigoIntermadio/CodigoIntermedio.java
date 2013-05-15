package codigoIntermadio;

import java.util.ArrayList;

import acciones.Tipo;

import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class CodigoIntermedio {

	
	ArrayList<InstruccionIntermedio> lista;
	
	TablaSimbolos ts;
	int numTempVar;
	int numTempIf;
	int numTempBucle;
	int numTempAmbito;
	boolean accionesPostHechas=false;
	
	
	public CodigoIntermedio(TablaSimbolos ts){
		numTempVar=0;
		numTempIf=0;
		numTempBucle=0;
		numTempAmbito=0;
		lista= new ArrayList<InstruccionIntermedio>();
		this.ts=ts;
	}
	
	
	public ArrayList<InstruccionIntermedio> getLista() {
		
		// encontrar main
				/*
		ArrayList<InstruccionIntermedio> insDelMain= new ArrayList<InstruccionIntermedio>();
		
		
		boolean mainEncontrado=false;
		int posMain=0;
		while (!mainEncontrado && posMain<lista.size()){
			if ("comienzoFunmain".equals(lista.get(posMain).getEtiqueta())){
				mainEncontrado=true;
			}
			posMain++;
		}
		if (!mainEncontrado) return null;
		
		posMain--;
		boolean mainFinEncontrado=false;
		while (!mainFinEncontrado&& posMain<lista.size()){
			
			if ("finFunmain".equals(lista.get(posMain).getEtiqueta())){
				mainFinEncontrado=true;
			}
			InstruccionIntermedio delMain =lista.get(posMain);
			insDelMain.add(delMain);
			lista.remove(posMain);
			
			
		}
		if (!mainFinEncontrado) return null;
		
		lista.addAll(0,insDelMain);
		*/
		
		
		
		if (!accionesPostHechas){
			//simulamos llamada al main.
			//todas las variables son globales.
			ts.setActual(ts.getGlobal());
			EntradaTabla etMain= ts.busquedaAmbito("main");
			AtributosTablaFuncion attMain = (AtributosTablaFuncion) etMain.getAtt();
			int numParamMain=attMain.getnCampos();
			
			ArrayList<EntradaTabla> paramMain = new ArrayList<EntradaTabla>();
			ArrayList<InstruccionIntermedio> llamadaMain=new ArrayList<InstruccionIntermedio>();

			//inicializacion del retorno
			paramMain.add(ts.insertar("0RetMain"));
			EntradaTabla etRet=ts.añadirAtributos("0RetMain", new AtributosTablaVariable(attMain.getTipoRet(), attMain.getDimRet(), null));
			InsAsigValor iniRet= new InsAsigValor();
			iniRet.setRes(etRet);
			iniRet.setValor("0");
			llamadaMain.add(iniRet);
			
			
			
			//inicializacion de parametros
			for(int i=0;i<numParamMain;i++){
				paramMain.add(ts.insertar("0ParamMain"+i));
				EntradaTabla etPar=ts.añadirAtributos("0ParamMain"+i, new AtributosTablaVariable(attMain.getListaTipos().get(i), attMain.getListaDim().get(i), null));
				InsAsigValor iniPar= new InsAsigValor();
				iniPar.setRes(etPar);
				iniPar.setValor("0");
				llamadaMain.add(iniPar);
			}
			
	
			
			
			//instrucciones param.
			for(int i=0;i<numParamMain;i++){
				InsParam param= new InsParam();
				param.setParam(paramMain.get(i));
				llamadaMain.add(param);
			}
			//cal del main
			InsCall callMain = new InsCall();
			callMain.setDir("comienzoFunmain");
			callMain.setNum(numParamMain);
			llamadaMain.add(callMain);
			
			// retorno del main
			InsAsigFun asigRet= new InsAsigFun();
			asigRet.setRes(etRet);
			llamadaMain.add(asigRet);
			
			
			
			// imprimir cadena final
			String despedida="Fin: ";
			for (int i=0; i<despedida.length();i++){
				
				ts.insertar("0Cadena"+i);
				EntradaTabla etChar=ts.añadirAtributos("0Cadena"+i, new AtributosTablaVariable("char", 0, null));
				InsAsigValor asigChar= new InsAsigValor();
				asigChar.setRes(etChar);
				asigChar.setValor(String.valueOf(despedida.charAt(i)));
				llamadaMain.add(asigChar);
				
				InsPrintf printChar= new InsPrintf();
				printChar.setEXPlugar(etChar);
				llamadaMain.add(printChar);
			}
			
			//imprimir valor de retorno
			InsPrintf printRet= new InsPrintf();
			printRet.setEXPlugar(etRet);
			llamadaMain.add(printRet);
			
			//hacer halt
			InsTextoDirecto halt= new InsTextoDirecto();
			halt.setTexto("halt");
			llamadaMain.add(halt);
			
			lista.addAll(0,llamadaMain);
			
			accionesPostHechas=true;
		}
		
		return lista;
	}


	public void setLista(ArrayList<InstruccionIntermedio> lista) {
		this.lista = lista;
	}


	
	
	
	public void addInstruccion (InstruccionIntermedio i){
		lista.add(i);
	}
	
	
	public EntradaTabla tempNuevo(Tipo t){
		String s= "t"+numTempVar;
		numTempVar++;
		
		EntradaTabla ret=ts.insertar(s);
		ts.añadirAtributos(s, new AtributosTablaVariable(t.getTipo(), t.getDim(),null));
		return ret;
	}
	
	public String ifNuevo(){
		String s= String.valueOf(numTempIf);
		numTempIf++;
		return s;
	}


	public Object bucleNuevo() {
		String s= String.valueOf(numTempBucle);
		numTempBucle++;
		return s;
	}
	public Object ambitoNuevo() {
		String s= String.valueOf(numTempAmbito);
		numTempAmbito++;
		return s;
	}
	
	
	
}
