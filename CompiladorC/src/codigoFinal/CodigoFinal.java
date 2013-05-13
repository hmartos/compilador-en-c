package codigoFinal;

import java.util.ArrayList;
import java.util.Iterator;

import javax.print.attribute.Size2DSyntax;

import codigoIntermadio.InsAsigFun;
import codigoIntermadio.InsAsigValor;
import codigoIntermadio.InsCall;
import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InsGoto;
import codigoIntermadio.InsIfGoto;
import codigoIntermadio.InsIni;
import codigoIntermadio.InsParam;
import codigoIntermadio.InsReturn;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class CodigoFinal {

	ArrayList<InstruccionIntermedio> entrada;
	ArrayList<String> salida;

	TablaSimbolos ts;
	
	int numIni=0;
	int numParam=0;
	ArrayList<EntradaTabla> parametros;

	ArrayList<ArrayList<EntradaTabla>> descriptReg; 
	//el primer array se indexa por n�mero de registro. 
	//El segundo contiene los nombres que referencian a este registro. 
	
	
	
	/* LLAMADA PILA:
	 * -parametros (params) - 
	 * -retorno    (call) 
	 * -registros  (call)
	 * -PC			(call)
	 * -variables locales. (Ini)-(return)
	 */
	
	
	
	
	public CodigoFinal(TablaSimbolos ts,	ArrayList<InstruccionIntermedio> entrada){
		this.ts=ts;
		this.entrada=entrada;
		salida=new ArrayList<String>();
		descriptReg=new ArrayList<ArrayList<EntradaTabla>>();
		parametros=new ArrayList<EntradaTabla>();	
		for (int i=0; i<10;i++){
			descriptReg.add(new ArrayList<EntradaTabla>());
		}
		
	}
	
	
	
	public ArrayList<InstruccionIntermedio> getEntrada() {
		return entrada;
	}



	public void setEntrada(ArrayList<InstruccionIntermedio> entrada) {
		this.entrada = entrada;
	}



	public ArrayList<String> getSalida() {
		return salida;
	}



	public void setSalida(ArrayList<String> salida) {
		this.salida = salida;
	}



	private boolean seUsa(EntradaTabla nombre,int numInst,int profundidad){
		boolean seUsa=false;
		for (int i =0;i<profundidad;i++){
			InstruccionIntermedio insSig=entrada.get(numInst+i);

			if (insSig instanceof InsCuarteto){
				InsCuarteto insCSig=(InsCuarteto) insSig;
				seUsa|=nombre.equals(insCSig.getOp1());
				seUsa|=nombre.equals(insCSig.getOp2());
			}
		}
		return seUsa;
	}
	
	
	
	public LugarRM obtenLugar(int numInst){
		InstruccionIntermedio inst=entrada.get(numInst);
		
		//Instrucciones insCuarteto, tipo : x=y,  x=y op,  x=y op z;
		if (inst instanceof InsCuarteto){
			int registroLibre= -1;
			
			Iterator<ArrayList<EntradaTabla>> itReg = descriptReg.iterator();
			int i=0;
			while (registroLibre==-1 && itReg.hasNext()){
				ArrayList<EntradaTabla> regAct= itReg.next();
				if (regAct.size()==0)registroLibre=i;
				i++;
			}
		
		
		
			
			InsCuarteto instC=(InsCuarteto) inst;
			EntradaTabla nombreY=instC.getOp1(); //Nombre y (referencia a TS)
			LugarRM lugarY=nombreY.getDescriptDir(); // L -> lugar de Y
			
			if (lugarY.estaEnRegistro()){//El valor de y esta en un registro
				
				if (descriptReg.get(lugarY.getDescriptDirReg()).size()==1){ //el registro solo est� referenciado por un nombre(el del Y).
					if (!seUsa(nombreY,numInst+1,1)){ //El nombre y no se usa en la(s) siguiente(s) instruccion(es).
						if (lugarY.isActualizadoReg()){ //Esta actualizado, no hay que hacer store.
							
							descriptReg.get(lugarY.getDescriptDirReg()).remove(nombreY);
							
							descriptReg.get(lugarY.getDescriptDirReg()).add(instC.getRes());
							instC.getRes().getDescriptDir().setDescriptDirReg(lugarY.getDescriptDirReg());
							
							nombreY.getDescriptDir().setDescriptDirReg(-1);
							nombreY.getDescriptDir().setActualizadoReg(true);

							instC.getRes().getDescriptDir().setActualizadoReg(true);
							
							return lugarY;
						}else if (registroLibre!=-1){ //Hay registros libres
							//Mover y al registro nuevo.
							salida.add("MOVE "+getOperando(lugarY)+",.r"+registroLibre+"; traemos y al nuevo registro (obtenLugar)");
							
							descriptReg.get(registroLibre).add(instC.getRes());
							instC.getRes().getDescriptDir().setDescriptDirReg(registroLibre);
							instC.getRes().getDescriptDir().setActualizadoReg(true);

							return new LugarRM(registroLibre);
						} else {					// No hay registros libres.
							//store de y y quitar del descriptor.
							salida.add("MOVE .sp,.ix");
							salida.add("MOVE .r"+lugarY.getDescriptDirReg()+",#-"+lugarY.getDescriptDirMem()+"[.ix]"+"; store de y (obtenLugar)");
							descriptReg.get(lugarY.getDescriptDirReg()).remove(nombreY);
							
							descriptReg.get(lugarY.getDescriptDirReg()).add(instC.getRes());
							instC.getRes().getDescriptDir().setDescriptDirReg(lugarY.getDescriptDirReg());
							
							nombreY.getDescriptDir().setDescriptDirReg(-1);
							nombreY.getDescriptDir().setActualizadoReg(true);

							instC.getRes().getDescriptDir().setActualizadoReg(true);
							
							return lugarY;
						}
					}
				}	
					
					
			}
			if (registroLibre!=-1){ //El valor de y no esta en un registro, o ese registro est� referenciado, o se usar� proximamente.
				//Hay registros libres
				//Mover y al registro nuevo.
				salida.add("MOVE "+getOperando(lugarY)+",.r"+registroLibre+"; traemos y al nuevo registro (obtenLugar)");
				descriptReg.get(registroLibre).add(instC.getRes());
				instC.getRes().getDescriptDir().setDescriptDirReg(registroLibre);
				instC.getRes().getDescriptDir().setActualizadoReg(true);

				return new LugarRM(registroLibre);
			}else if (!seUsa(nombreY,numInst+1,1)&&operaMemoria(numInst)){//Operaremos desde memoria
				//if x en registro, store X.
				salida.add("MOVE .sp,.ix");
				salida.add("MOVE .r"+instC.getRes().getDescriptDir().getDescriptDirReg()+",#-"+instC.getRes().getDescriptDir().getDescriptDirMem()+"[.ix]"+"; store de x (obtenLugar)");
				return instC.getRes().getDescriptDir();
				
			}else{ //Necesitamos un registro para operar, obtenemos uno adecuado.
				int registro=liberarRegOcupado(numInst);
				//Mover y al registro nuevo.
				salida.add("MOVE "+getOperando(lugarY)+",.r"+registro+"; traemos y al registro liberado (obtenLugar)");
				
				descriptReg.get(registro).add(instC.getRes());
				instC.getRes().getDescriptDir().setDescriptDirReg(registro);
				instC.getRes().getDescriptDir().setActualizadoReg(true);
				return new LugarRM(registro); 
				
				
			}
			
				
				
		} return null;
	}
	
	
private int liberarRegOcupado(int numInst){
	int registro=-1;
	int i=0;
	while (registro==-1 &&i<descriptReg.size()){
		boolean actualizado=true;
		for (Iterator<EntradaTabla> it=descriptReg.get(i).iterator();it.hasNext();){
			actualizado=actualizado&&it.next().getDescriptDir().isActualizadoReg();
		}
		if (actualizado) registro=i;
	}
	
	if (registro!=-1) return registro;
	else{
		i=0;
		while (registro==-1 &&i<descriptReg.size()){
			boolean seUsa=false;
			for (Iterator<EntradaTabla> it=descriptReg.get(i).iterator();it.hasNext();){
				seUsa=seUsa||seUsa(it.next(), numInst, 5);
			}
			if (!seUsa)registro=i;
		}
		if (registro!=-1) {
			for (Iterator<EntradaTabla> it=descriptReg.get(registro).iterator();it.hasNext();){
				EntradaTabla et=it.next();
				salida.add("MOVE " +".r"+registro+","+et.getDescriptDir().getDescriptDirMem()+"; store para liberar (liberarRegOcupado)");
				et.getDescriptDir().setDescriptDirReg(-1);
				et.getDescriptDir().setActualizadoReg(true);
			}
			return registro;
		}
		else {
			for (Iterator<EntradaTabla> it=descriptReg.get(0).iterator();it.hasNext();){
				EntradaTabla et=it.next();
				salida.add("MOVE " +".r"+0+","+et.getDescriptDir().getDescriptDirMem()+"; store para liberar (liberarRegOcupado)");
				et.getDescriptDir().setDescriptDirReg(-1);
				et.getDescriptDir().setActualizadoReg(true);
			}
			return 0;
		}
	}
	
}
	
private boolean operaMemoria(int numInst){
	
	LugarRM lugarX= obtenLugar(numInst);
	InstruccionIntermedio inst=entrada.get(numInst);
	
	if (inst instanceof InsCuarteto){
		InsCuarteto instC=(InsCuarteto) inst;
		String op=((InsCuarteto) inst).getOpRel();

		if (op==null){//asignacion simple (x=y)
			return true;
		}else{ //Op no es null
		EntradaTabla nombreZ=((InsCuarteto) inst).getOp2();
		
			if (nombreZ==null){
				
				if (op.equals("+")){
					return true;
				}else if (op.equals("-")){
					return true;
				}else if (op.equals("*")){
					return true;
				}else if (op.equals("&")){
					
				}
			}else { //hay operando 2 (Z)
				LugarRM lugarZ= nombreZ.getDescriptDir();
				if (op.equals("+")){
					return true;
				}else if (op.equals("-")){
					return true;
				}else if (op.equals("*")){
					return true;
				}else if (op.equals("&")){
					return true;
				}
			}
			
		}
	}
	return false;
}

	
public void genCodigo(int numInst ){
	
	
	InstruccionIntermedio inst=entrada.get(numInst);
	
	salida.add("; "+inst.toString());
	//Por comodidad ponemos la etiqueta con un nop, se podria a�adir donde correspondiera al inicio de cada instruccion.
	if (inst.getEtiqueta()!=null){
		salida.add(inst.getEtiqueta()+": nop"); 
		if (inst.getEtiqueta().startsWith("finFun")){
			numIni=0;
		}
	}
	
	
	
	
	
	//Instrucciones insCuarteto, tipo : x=y,  x=y op,  x=y op z;
	if (inst instanceof InsCuarteto){
		LugarRM lugarX= obtenLugar(numInst);
		InsCuarteto instC=(InsCuarteto) inst;
		String op=((InsCuarteto) inst).getOpRel();

		if (op==null){//asignacion simple (x=y)
			if (lugarX.estaEnRegistro()){//La operacion se ha resuelto asignando el mismo registro
				descriptReg.get(lugarX.getDescriptDirReg()).add(instC.getRes());
				
			}else{//La operacion se ha hecho directamente en memoria.
				
			}
		}else{ //Op no es null
		EntradaTabla nombreZ=((InsCuarteto) inst).getOp2();
		
			if (nombreZ==null){
				
				if (op.equals("+")){
					
				}else if (op.equals("-")){
					
				}else if (op.equals("*")){
					
				}else if (op.equals("&")){
					
				}
			}else { //hay operando 2 (Z)
				LugarRM lugarZ= nombreZ.getDescriptDir();
				if (op.equals("+")){
					salida.add("ADD "+getOperando(lugarX)+","+getOperando(lugarZ)+"; suma  (genCodigo)");
					salida.add("MOVE .a,"+getOperando(lugarX) );
				}else if (op.equals("-")){
					
				}else if (op.equals("*")){
					
				}else if (op.equals("&")){
					
				}
			}
		}
	}else if (inst instanceof InsParam){
		InsParam instP=(InsParam)inst;
		parametros.add(instP.getParam());
		numParam++;
	
	}else if (inst instanceof InsCall){
		InsCall instF=(InsCall)inst;
		salida.add("MOVE .sp,.ix"); //Metiendo los parametros de llamada
		for (int i=0;i<numParam;i++){
			salida.add("PUSH "+getOperando(parametros.get(i).getDescriptDir())+"; param (genCodigo)");
		}
		parametros= new ArrayList<EntradaTabla>(); //limpiamos params, pues ya los hemos usado.
		
		salida.add("PUSH #0"+"; valor de retorno (genCodigo)"); //valor retorno
		for (int i=0;i<descriptReg.size();i++){ //Salvaguarda registros
			salida.add("PUSH .r"+i+"; salvar registros (genCodigo)");
		}
		salida.add("MOVE .sp,.ix");
		salida.add("CALL /"+instF.getDir());
		
		for (int i=descriptReg.size()-1;i>=0;i--){ //Recuperamos registros
			salida.add("POP .r"+i+"; recuperar registros (genCodigo)");
		}
		
		
		
	}else if (inst instanceof InsAsigValor){
		InsAsigValor instAV=(InsAsigValor)inst;
		salida.add("MOVE #"+instAV.getValor()+","+getOperando(instAV.getRes().getDescriptDir())+"; valor directo (genCodigo)");
		
	}else if (inst instanceof InsReturn){
		InsReturn instR=(InsReturn)inst;
		
		salida.add("MOVE .sp,.ix");
		if (instR.getValorRet()!=null){ //Metemos el valor de retorno en la posicion
			salida.add("MOVE "+getOperando(instR.getValorRet().getDescriptDir())+",#-"+(12+numIni)+"[.ix]"+"; guardamos valor de retorno (genCodigo)");
		}else{
			salida.add("MOVE #0,"+"#-"+(12+numIni)+"[.ix]"+"; valor de retorno 0 (genCodigo)");

		}
		
		for(int i=0; i<numIni;i++){ //sacamos variables locales
			salida.add("DEC .sp"+"; sacar variables locales (genCodigo)");
		}
		
		salida.add("RET ");
		
		
	
	}else if (inst instanceof InsAsigFun){
		InsAsigFun instAF=(InsAsigFun)inst;
		salida.add("POP .a"+"; guardamos retorno en A (genCodigo)");
		
		for(int i=0; i<numParam;i++){ //sacamos parametros de llamada
			salida.add("DEC .sp"+"; sacamos param (genCodigo)");
		}
		
		salida.add("MOVE .a,"+getOperando(instAF.getRes().getDescriptDir())+"; guardamos de A al que recibe return (genCodigo)");

		numParam=0;
		
		
		
	}else if (inst instanceof InsGoto){
		InsGoto instG=(InsGoto)inst;
		salida.add("BR /"+instG.getDir());
		
	}else if (inst instanceof InsIfGoto){
		InsIfGoto instIF=(InsIfGoto)inst;
		
	}else if (inst instanceof InsIni){
		InsIni instIni=(InsIni)inst;
		salida.add("PUSH #"+instIni.getValorIni()+"; variable local (genCodigo)");
		numIni++;
		
	}
	
}

private String getOperando(LugarRM l){
	if (l.estaEnRegistro()){
		return ".r"+l.getDescriptDirReg();
	}else {
		salida.add("MOVE .sp,.ix");
		return "#-"+(l.getDescriptDirMem()+1)+"[.ix]";
	}
}

	
public boolean generarCodigoFinal(){
	

	
	for (int i=0;i<entrada.size();i++){
		genCodigo(i);
	}
	salida.add(0,"org 0");
	salida.add("end");
	return true;
}
	

}
	
	
	
	
	
	

