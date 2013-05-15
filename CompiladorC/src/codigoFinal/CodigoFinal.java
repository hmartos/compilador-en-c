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
import codigoIntermadio.InsPrintf;
import codigoIntermadio.InsReturn;
import codigoIntermadio.InsScanf;
import codigoIntermadio.InsTextoDirecto;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaAmbito;
import tablaSimbolos.TablaSimbolos;

public class CodigoFinal {

	ArrayList<InstruccionIntermedio> entrada;
	ArrayList<String> salida;

	TablaSimbolos ts;
	
	ArrayList<Integer> numIni=new ArrayList<Integer>();
	ArrayList<Integer> numAmbitos=new ArrayList<Integer>();

	int numIniIndex=1;
	int numParam=0;
	ArrayList<EntradaTabla> parametros;
	



	ArrayList<ArrayList<EntradaTabla>> descriptReg; 
	//el primer array se indexa por número de registro. 
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
		
		ts.setActual(ts.getGlobal());
		this.entrada=entrada;
		salida=new ArrayList<String>();
		descriptReg=new ArrayList<ArrayList<EntradaTabla>>();
		parametros=new ArrayList<EntradaTabla>();	
		for (int i=0; i<10;i++){
			descriptReg.add(new ArrayList<EntradaTabla>());
		}
		
		numIni.add(0);
		numAmbitos.add(0);
		
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
	
	
	
	private int obtenLugar(EntradaTabla t,int numInst){
		//si ya está en registro devolvemos ese registro directamente
		if (t.getDescriptDir().estaEnRegistro()) return t.getDescriptDir().getDescriptDirReg();
		else {
			
			//buscamos si hay registros libres.
			int registroLibre= -1;
			Iterator<ArrayList<EntradaTabla>> itReg = descriptReg.iterator();
			int i=0;
			while (registroLibre==-1 && itReg.hasNext()){
				ArrayList<EntradaTabla> regAct= itReg.next();
				if (regAct.size()==0)registroLibre=i;
				i++;
			}
			//si hay un registro libre, lo usamos.
			if (registroLibre!=-1){
				salida.add("MOVE "+getOperando(t)+",.r"+registroLibre+"; traemos el operando al nuevo registro (obtenLugar)");
				//actualizacion de los descriptores.
				descriptReg.get(registroLibre).add(t);
				t.getDescriptDir().setDescriptDirReg(registroLibre);
				return registroLibre;
			}
			else{
				int reg= liberarRegOcupado(numInst);
				salida.add("MOVE "+getOperando(t)+",.r"+reg+"; traemos el operando al registro liberado (obtenLugar)");
				//actualizacion de los descriptores.
				descriptReg.get(reg).add(t);
				t.getDescriptDir().setDescriptDirReg(reg);
				return reg;
				
				
			}
		
			
			

		}
	}
	
	
	
	/*
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
				
				if (descriptReg.get(lugarY.getDescriptDirReg()).size()==1){ //el registro solo está referenciado por un nombre(el del Y).
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
							salida.add("MOVE "+getOperando(lugarY,nombreY.getLex())+",.r"+registroLibre+"; traemos y al nuevo registro (obtenLugar)");
							
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
			if (registroLibre!=-1){ //El valor de y no esta en un registro, o ese registro está referenciado, o se usará proximamente.
				//Hay registros libres
				//Mover y al registro nuevo.
				salida.add("MOVE "+getOperando(lugarY,nombreY.getLex())+",.r"+registroLibre+"; traemos y al nuevo registro (obtenLugar)");
				descriptReg.get(registroLibre).add(instC.getRes());
				instC.getRes().getDescriptDir().setDescriptDirReg(registroLibre);
				instC.getRes().getDescriptDir().setActualizadoReg(true);

				return new LugarRM(registroLibre);
			}else if (!seUsa(nombreY,numInst+1,1)&&operaMemoria(numInst)){//Operaremos desde memoria (revisar)
				//if x en registro, store X.
				salida.add("MOVE .sp,.ix");
				salida.add("MOVE .r"+instC.getRes().getDescriptDir().getDescriptDirReg()+",#-"+instC.getRes().getDescriptDir().getDescriptDirMem()+"[.ix]"+"; store de x (obtenLugar)");
				return instC.getRes().getDescriptDir();
				
			}else{ //Necesitamos un registro para operar, obtenemos uno adecuado.
				int registro=liberarRegOcupado(numInst);
				//Mover y al registro nuevo.
				salida.add("MOVE "+getOperando(lugarY,nombreY.getLex())+",.r"+registro+"; traemos y al registro liberado (obtenLugar)");
				
				descriptReg.get(registro).add(instC.getRes());
				instC.getRes().getDescriptDir().setDescriptDirReg(registro);
				instC.getRes().getDescriptDir().setActualizadoReg(true);
				return new LugarRM(registro); 
				
				
			}
			
				
				
		} return null;
	}
	
	*/
	
	private void liberarRegistro(int registro){
		for (Iterator<EntradaTabla> it=descriptReg.get(registro).iterator();it.hasNext();){
			EntradaTabla et=it.next();
			if (!et.getDescriptDir().isActualizadoReg()){
				salida.add("MOVE " +".r"+registro+","+getOperandoMem(et)+"; store para liberar (liberarRegistro)");
			}
			et.getDescriptDir().setDescriptDirReg(-1);
			et.getDescriptDir().setActualizadoReg(true);
		}
		descriptReg.get(registro).clear();
		
		
	}



	private void actualizarMem() {
		for(int registro=0;registro<descriptReg.size();registro++){
			liberarRegistro(registro);
		}
		
	}
	
	
	
	
	
	
	
	
private int liberarRegOcupado(int numInst){
	int registro=-1;
	int i=0;
	
	InstruccionIntermedio inst= entrada.get(numInst);
	
	//si hay alguno actualizado cogemos ese.
	while (registro==-1 &&i<descriptReg.size()){
		boolean actualizado=true;
		for (Iterator<EntradaTabla> it=descriptReg.get(i).iterator();it.hasNext();){
			actualizado=actualizado&&it.next().getDescriptDir().isActualizadoReg();
		}
		if (actualizado){
			if (inst instanceof InsCuarteto){
				InsCuarteto instC= (InsCuarteto) inst;
				if (i!=instC.getRes().getDescriptDir().getDescriptDirReg()
						&& i!=instC.getOp1().getDescriptDir().getDescriptDirReg()
						&&(instC.getOp2()==null || i!=instC.getOp2().getDescriptDir().getDescriptDirReg()))
					registro=i;
			}else registro=i;
		}
		i++;
	}
	
	if (registro!=-1){
		liberarRegistro(registro);
		return registro;
	} // si no miramos el siguiente "que tarde mas en usarse"
	else{
		i=0;
		while (registro==-1 &&i<descriptReg.size()){
			boolean seUsa=false;
			for (Iterator<EntradaTabla> it=descriptReg.get(i).iterator();it.hasNext();){
				seUsa=seUsa||seUsa(it.next(), numInst, 5);
			}
			if (!seUsa){
				if (inst instanceof InsCuarteto){
					InsCuarteto instC= (InsCuarteto) inst;
					if (i!=instC.getRes().getDescriptDir().getDescriptDirReg()
							&& i!=instC.getOp1().getDescriptDir().getDescriptDirReg()
							&&(instC.getOp2()==null || i!=instC.getOp2().getDescriptDir().getDescriptDirReg()))
						registro=i;
				}else registro=i;
			}
			i++;
		}
		if (registro!=-1) {
			liberarRegistro(registro);
			return registro;
		}// si no el registro 0.
		else {
			

			i=0;
			while (registro==-1 &&i<descriptReg.size()){
			
			
				if (inst instanceof InsCuarteto){
					InsCuarteto instC= (InsCuarteto) inst;
					if (i!=instC.getRes().getDescriptDir().getDescriptDirReg()
							&& i!=instC.getOp1().getDescriptDir().getDescriptDirReg()
							&& (instC.getOp2()==null ||i!=instC.getOp2().getDescriptDir().getDescriptDirReg()))
						registro=i;
				}else registro=i;
			i++;
			}
			
			if (registro!=-1){
				liberarRegistro(registro);
				return registro;
			}
		}
	}
	
	liberarRegistro(0);
	return 0;
	
}
	/*
private boolean operaMemoria(int numInst){
	

	
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
				int regZ= obtenLugar(instC.getOp2(),numInst);

				
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
*/
	
public void genCodigo(int numInst )
{
	
	
	InstruccionIntermedio inst=entrada.get(numInst);
	
	if (!inst.getClass().equals(InstruccionIntermedio.class)){
		salida.add("; "+inst.toString());
	//Por comodidad ponemos la etiqueta con un nop, se podria añadir donde correspondiera al inicio de cada instruccion.
	}
	if (inst.getEtiqueta()!=null)
	{
		
		
		if (inst.getEtiqueta().startsWith("openAmbito")){
			//liberamos los registros.
			actualizarMem();
			
			numIni.add(0);
			numIniIndex++;
			ts.setActual(ts.getActual().contenido.get(numAmbitos.get(numAmbitos.size()-1)));
			numAmbitos.set(numAmbitos.size()-1,numAmbitos.get(numAmbitos.size()-1)+1);
			numAmbitos.add(0);
			
		}else if (inst.getEtiqueta().startsWith("closeAmbito")){
			//liberamos los registros.
			actualizarMem();
			
			for(int i=0; i<numIni.get(numIniIndex-1);i++){ //sacamos variables locales
				salida.add("DEC .sp"+"; sacar variables locales (genCodigo)");
			}
			numAmbitos.remove(numAmbitos.size()-1);
			ts.setActual(ts.getActual().contenedor);
			
			
			numIni.remove(numIniIndex-1);
			numIniIndex--;
			
		}else if (inst.getEtiqueta().startsWith("comienzoBucle")){
			actualizarMem();
		
		}else if (inst.getEtiqueta().startsWith("finBucle")){
			actualizarMem();
		}
		salida.add(inst.getEtiqueta()+": nop"); 
		/*
		if (inst.getEtiqueta().startsWith("finFun"))
		{
			numIni.remove(numIniIndex-1);
			numIniIndex--;
			numAmbitos.remove(numAmbitos.size()-1);
			ts.setActual(ts.getActual().contenedor);
		
		}else if(inst.getEtiqueta().startsWith("comienzoFun")){
				numIni.add(0);
				numIniIndex++;
				ts.setActual(ts.getActual().contenido.get(numAmbitos.get(numAmbitos.size()-1)));
				numAmbitos.set(numAmbitos.size()-1,numAmbitos.get(numAmbitos.size()-1)+1);
				
				numAmbitos.add(0);
			
		}else if(inst.getEtiqueta().startsWith("comienzoBucle")){
			numIni.add(0);
			numIniIndex++;
			ts.setActual(ts.getActual().contenido.get(numAmbitos.get(numAmbitos.size()-1)));
			numAmbitos.set(numAmbitos.size()-1,numAmbitos.get(numAmbitos.size()-1)+1);
			numAmbitos.add(0);
			
		}else if(inst.getEtiqueta().startsWith("finBucle")){
			
			
			
		}
	*/
	}
	
	
	
	
	//Instrucciones insCuarteto, tipo : x=y,  x=y op,  x=y op z;
	if (inst instanceof InsCuarteto)
	{
		salida.add("MOVE .sp,.ix");

		InsCuarteto instC=(InsCuarteto) inst;
		int regX= obtenLugar(instC.getRes(),numInst);
		int regY= obtenLugar(instC.getOp1(),numInst);
		
		
		String op=((InsCuarteto) inst).getOpRel();
		
		//en cualquier operacion de desactualiza el registro x
		instC.getRes().getDescriptDir().setActualizadoReg(false);
		
		if (op==null){//asignacion simple (x=y)
			salida.add("MOVE .r"+regY+",.r"+regX +"; asignacion directa");
			
		}
		else
		{ //Op no es null, operadores unarios
		    EntradaTabla nombreZ=((InsCuarteto) inst).getOp2();
		
			if (nombreZ==null)
			{
				
				if (op.equals("+")){
					// ESTA MALS
					//salida.add("INC "+getOperando(lugarX)+","+getOperando(lugarZ)+"; incremento  (genCodigo)");
				}else if (op.equals("-")){
					salida.add("MOVE .r"+regY+",.r"+regX);
					salida.add("NEG .r"+regX+"; cambioSigno  (genCodigo)");
				}else if (op.equals("*")){
					salida.add("MOVE .r"+regY+",.r"+regX); //COMPROBAR
				}else if (op.equals("&")){
					
				}
			}
			else 
			{ //hay operando 2 (Z), operadores binarios
				int regZ = obtenLugar(instC.getOp2(), numInst);
				if (op.equals("+")){
					salida.add("ADD "+".r"+regY+",.r"+regZ+"; suma  (genCodigo)");
					salida.add("MOVE .a,.r"+regX );
				}else if (op.equals("-")){
					salida.add("SUB "+".r"+regY+",.r"+regZ+"; resta (genCodigo)");
					salida.add("MOVE .a,.r"+regX );
				}else if (op.equals("*")){
					salida.add("MUL "+".r"+regY+",.r"+regZ+"; mult (genCodigo)");
					salida.add("MOVE .a,.r"+regX );
				}else if (op.equals("/")){
					salida.add("DIV "+".r"+regY+",.r"+regZ+"; div (genCodigo)");
					salida.add("MOVE .a,.r"+regX );
				}else if (op.equals("%")){
					salida.add("MOD "+".r"+regY+",.r"+regZ+"; mod (genCodigo)");
					salida.add("MOVE .a,.r"+regX );
				}else if (op.equals("&")){
					// COMPROBAR!! TENEMOS DUDAS
					// salida.add("MOVE .a,"+getOperando(lugarX) );  
				}
				else if (op.equals(">")){
					salida.add("CMP "+".r"+regY+",.r"+regZ+"; comparacion mayor  (genCodigo)");
					salida.add("BP $2 ; salta compZero"); 
					salida.add("BR $2 ; salta asig0");
					//compZero
					salida.add("BNZ $5 ; es compZero, salta a asig1" );
					//asig0
					salida.add("MOVE #0,.a ; es asig0");
					salida.add("BR $3 ; salta a fin");
					//asig1
					salida.add("MOVE #1,.a ; es asig1");
					//fin
					salida.add("MOVE .a,.r"+regX +" ; esto es fin");

					
					/* otra alternativa que no funciona porque la division de numeros negativos devuelve siempre 0 ??¿¿¿
					salida.add("MOVE .sr,.a");
					salida.add("NOT .a");
					salida.add("DIV .a,#16");
					salida.add("MOD .a,#2");
					salida.add("MOVE .a,"+getOperando(lugarX,nombreX.getLex())); // no negativo
					
					salida.add("MOVE .sr,.a");
					salida.add("NOT .a");
					salida.add("MOD .a,#2");//nocero
					salida.add("AND .a,"+getOperando(lugarX,nombreX.getLex())); // nocero y no negativo 
					
					salida.add("MOVE .a,"+getOperando(lugarX,nombreX.getLex()));
			*/
				}
				else if (op.equals(">="))
				{
						salida.add("CMP "+".r"+regY+",.r"+regZ+"; comparacion mayor-igual  (genCodigo)");
						salida.add("BP $5 ; salta asig1"); 
						
						//asig0
						salida.add("MOVE #0,.a ; es asig0");
						salida.add("BR $3 ; salta a fin");
						//asig1
						salida.add("MOVE #1,.a ; es asig1");
						//fin
						salida.add("MOVE .a,.r"+regX +" ; esto es fin");
				}

				else if (op.equals("<"))
				{
						salida.add("CMP "+".r"+regY+",.r"+regZ+"; comparacion menor  (genCodigo)");
						salida.add("BN $2 ; salta compZero"); 
						salida.add("BR $2 ; salta asig0");
						//compZero
						salida.add("BNZ $5 ; es compZero, salta a asig1" );
						//asig0
						salida.add("MOVE #0,.a ; es asig0");
						salida.add("BR $3 ; salta a fin");
						//asig1
						salida.add("MOVE #1,.a ; es asig1");
						//fin
						salida.add("MOVE .a,.r"+regX +" ; esto es fin");
				}

				else if (op.equals("<="))
				{
						salida.add("CMP "+".r"+regY+",.r"+regZ+"; menor  (genCodigo)");					
						salida.add("BN $5 ; salta asig1"); 
						
						//asig0
						salida.add("MOVE #0,.a ; es asig0");
						salida.add("BR $3 ; salta a fin");
						//asig1
						salida.add("MOVE #1,.a ; es asig1");
						//fin
						salida.add("MOVE .a,.r"+regX +" ; esto es fin");
				}

				else if (op.equals("=="))
				{
						salida.add("CMP "+".r"+regY+",.r"+regZ+"; menor  (genCodigo)");					
						salida.add("BZ $5 ; salta asig1"); 
						
						//asig0
						salida.add("MOVE #0,.a ; es asig0");
						salida.add("BR $3 ; salta a fin");
						//asig1
						salida.add("MOVE #1,.a ; es asig1");
						//fin
						salida.add("MOVE .a,.r"+regX +" ; esto es fin");
				}
				else if (op.equals("!="))
				{
						//salida.add("ADD "+".r"+regY+",r"+regZ+"; suma  (genCodigo)");
						salida.add("CMP "+".r"+regY+",.r"+regZ+"; menor  (genCodigo)");
						salida.add("BNZ $5 ; salta asig1"); 
						
						//asig0
						salida.add("MOVE #0,.a ; es asig0");
						salida.add("BR $3 ; salta a fin");
						//asig1
						salida.add("MOVE #1,.a ; es asig1");
						//fin
						salida.add("MOVE .a,.r"+regX +" ; esto es fin");
				}
				
			}
			
		    
		}
	}else if (inst instanceof InsParam){
		InsParam instP=(InsParam)inst;
		parametros.add(instP.getParam());
		numParam++;
	
	}else if (inst instanceof InsCall){
		//actualizando la memoria con los registros en uso.
		actualizarMem();
		
		InsCall instF=(InsCall)inst;
		salida.add("MOVE .sp,.ix"); //Metiendo los parametros de llamada
		for (int i=0;i<numParam;i++){
			salida.add("PUSH "+getOperando(parametros.get(i))+"; param (genCodigo)");
		}
		parametros= new ArrayList<EntradaTabla>(); //limpiamos params, pues ya los hemos usado.
		
		salida.add("PUSH #0"+"; valor de retorno (genCodigo)"); //valor retorno
		/*for (int i=0;i<descriptReg.size();i++){ //Salvaguarda registros
			salida.add("PUSH .r"+i+"; salvar registros (genCodigo)");
		}*/
		salida.add("MOVE .sp,.ix");
		salida.add("CALL /"+instF.getDir());
		
		/*for (int i=descriptReg.size()-1;i>=0;i--){ //Recuperamos registros
			salida.add("POP .r"+i+"; recuperar registros (genCodigo)");
		}*/
		
		
		
	}else if (inst instanceof InsAsigValor){
		InsAsigValor instAV=(InsAsigValor)inst;
		instAV.getRes().getDescriptDir().setActualizadoReg(false);
		AtributosTablaVariable att=(AtributosTablaVariable) instAV.getRes().getAtt();
		salida.add("MOVE .sp,.ix");
		
		if (att.getTipo().equals("char")&&att.getDim()==0){	
			//Si es un char
			salida.add("MOVE #"+Integer.valueOf(instAV.getValor().charAt(0))+","+getOperando(instAV.getRes())+"; valor directo (genCodigo)");
		}else if (att.getTipo().equals("int")&&att.getDim()==0){		
			//Si es un entero
			salida.add("MOVE #"+instAV.getValor()+","+getOperando(instAV.getRes())+"; valor directo (genCodigo)");
		//numIni.set(numIniIndex-1,numIni.get(numIniIndex-1)+1);
		}else{
			salida.add("MOVE #"+Integer.valueOf(instAV.getValor().charAt(0))+","+getOperando(instAV.getRes())+"; valor directo (genCodigo)");
		}
		
		
		
	}else if (inst instanceof InsReturn){
		InsReturn instR=(InsReturn)inst;
		
		int busqNumInst=numInst+1;
		InstruccionIntermedio busq=entrada.get(busqNumInst);
		//int numAmbitosCerrar=0;
		while (busq.getEtiqueta()==null||!busq.getEtiqueta().startsWith("finFun")){
			//no es necesario contar los ambitos porque las funciones siempre están en el ambito 0
			//if (busq.getEtiqueta()!=null&&busq.getEtiqueta().startsWith("closeAmbito")) numAmbitosCerrar++;
			//if (busq.getEtiqueta()!=null&&busq.getEtiqueta().startsWith("openAmbito")) numAmbitosCerrar--;
			
			busqNumInst++;
			busq=entrada.get(busqNumInst);
			
		}
		
		int desplHastaSpFun=0;
		for (int i=numIniIndex-1;i>0;i--){ //las funciones siempre están en el ambito 0.
			desplHastaSpFun+=numIni.get(i);
		}
		
		//liberamos los registros.
		actualizarMem();
		
		salida.add("MOVE .sp,.ix");
		if (instR.getValorRet()!=null){ //Metemos el valor de retorno en la posicion
			salida.add("MOVE "+getOperando(instR.getValorRet())+",#-"+(2+desplHastaSpFun)+"[.ix]"+"; guardamos valor de retorno (genCodigo)");
		}else{
			salida.add("MOVE #0,"+"#-"+(2+desplHastaSpFun)+"[.ix]"+"; valor de retorno 0 (genCodigo)");

		}
		
		for(int i=0; i<desplHastaSpFun+numIni.get(0);i++){ //sacamos variables locales de todos los ambitos anidados y tambien de la funcion.
			salida.add("DEC .sp"+"; sacar variables locales (genCodigo)");
		}
		
		salida.add("RET ");
		
		
	
	}else if (inst instanceof InsAsigFun){
		InsAsigFun instAF=(InsAsigFun)inst;
		
		instAF.getRes().getDescriptDir().setActualizadoReg(false);

		salida.add("POP .a"+"; guardamos retorno en A (genCodigo)");
		
		for(int i=0; i<numParam;i++){ //sacamos parametros de llamada
			salida.add("DEC .sp"+"; sacamos param (genCodigo)");
		}
		
		//aqui ya estamos en el sp del llamante.
		salida.add("MOVE .sp,.ix");

		salida.add("MOVE .a,"+getOperando(instAF.getRes())+"; guardamos de A al que recibe return (genCodigo)");

		numParam=0;

		
		
	}else if (inst instanceof InsGoto){
		InsGoto instG=(InsGoto)inst;
		actualizarMem();
		salida.add("BR /"+instG.getDir());
		
	}else if (inst instanceof InsIfGoto){
		InsIfGoto instIF=(InsIfGoto)inst;
		salida.add("MOVE .sp,.ix");
		actualizarMem();
		salida.add("CMP "+getOperando(instIF.getOp1())+","+getOperando(instIF.getOp2())+"; comparacion del salto (genCodigo)");

		if ("=".equals(instIF.getOpRel())) salida.add("BZ /"+instIF.getDir());
		else if ("!=".equals(instIF.getOpRel())) salida.add("BNZ /"+instIF.getDir());

	}else if (inst instanceof InsIni){
		InsIni instIni=(InsIni)inst;
		salida.add("PUSH #"+instIni.getValorIni()+"; variable local (genCodigo)");
		numIni.set(numIniIndex-1,numIni.get(numIniIndex-1)+1);
		
	}else if (inst instanceof InsPrintf){
		InsPrintf instPrintf=(InsPrintf)inst;
		//mirar de la entrada de la tabla el tipo y según el tipo poner la instucción
		AtributosTablaVariable att=(AtributosTablaVariable) instPrintf.getEXPlugar().getAtt();
		salida.add("MOVE .sp,.ix");
		if (att.getTipo().equals("char")&&att.getDim()==0){	
			//Si es un char
			salida.add("WRCHAR "+getOperando(instPrintf.getEXPlugar())+"; imprimiendo char (genCodigo)");
		}else if (att.getTipo().equals("int")&&att.getDim()==0){		
			//Si es un entero
			salida.add("WRINT "+getOperando(instPrintf.getEXPlugar())+"; imprimiendo int (genCodigo)");
		//numIni.set(numIniIndex-1,numIni.get(numIniIndex-1)+1);
		}else{
			salida.add("WRINT "+getOperando(instPrintf.getEXPlugar())+"; imprimiendo valor? (genCodigo)");
		}
	}else if (inst instanceof InsScanf){
		InsScanf instScanf=(InsScanf)inst;
		instScanf.getEXPlugar().getDescriptDir().setActualizadoReg(false);
		//mirar de la entrada de la tabla el tipo y según el tipo poner la instucción
		AtributosTablaVariable att=(AtributosTablaVariable) instScanf.getEXPlugar().getAtt();
		salida.add("MOVE .sp,.ix");
		if (att.getTipo().equals("char")&&att.getDim()==0){	
			//Si es un char
			salida.add("INCHAR "+getOperando(instScanf.getEXPlugar())+"; leyendo char (genCodigo)");
		}else if (att.getTipo().equals("int")&&att.getDim()==0){		
			//Si es un entero
			salida.add("ININT "+getOperando(instScanf.getEXPlugar())+"; leyendo int (genCodigo)");
		//numIni.set(numIniIndex-1,numIni.get(numIniIndex-1)+1);
		}else{
			salida.add("ININT "+getOperando(instScanf.getEXPlugar())+"; leyendo valor? (genCodigo)");
		}
	}else if (inst instanceof InsTextoDirecto){
		salida.add(((InsTextoDirecto)inst).getTexto());
	}
	
}





private String getOperandoMem(EntradaTabla et){
	if (et.getDescriptDir().getDescriptDirMem()!=-1){//esta en memoria, pila
		salida.add("MOVE .sp,.ix");
		int prof=ts.busquedaCompletaProfundidad(et.getLex());
		int despl=0;
		for (int i=numIniIndex-1;i>numIniIndex-1-prof;i--){
			despl+=numIni.get(i);
		}
		despl+=et.getDescriptDir().getDescriptDirMem();
		return "#-"+(despl+1)+"[.ix]"; //?? es +1??
		//return "#-"+(despl)+"[.ix]";
	
	}else{ //es una variable global y tiene su etiqueta.
		return "/"+et.getDescriptDir().getDescriptDirGlobal();
		
	}
}



private String getOperando(EntradaTabla et){
	if (et.getDescriptDir().estaEnRegistro()){ //esta en registro
		return ".r"+et.getDescriptDir().getDescriptDirReg();
		
	}else if (et.getDescriptDir().getDescriptDirMem()!=-1){//esta en memoria, pila
		int prof=ts.busquedaCompletaProfundidad(et.getLex());
		int despl=0;
		for (int i=numIniIndex-1;i>numIniIndex-1-prof;i--){
			despl+=numIni.get(i);
		}
		despl+=et.getDescriptDir().getDescriptDirMem();
		return "#-"+(despl+1)+"[.ix]"; //?? es +1??
		//return "#-"+(despl)+"[.ix]";
	
	}else{ //es una variable global y tiene su etiqueta.
		return "/"+et.getDescriptDir().getDescriptDirGlobal();
		
	}
}

	
public boolean generarCodigoFinal(){
	
	
	
	
	//borrar ambitos vacios del global (por los prototipos).
			for (int i=0;i<ts.getGlobal().contenido.size();){
				TablaAmbito t= ts.getGlobal().contenido.get(i);
				if (t.tabla.size()==0){
					ts.getGlobal().contenido.remove(t);
				}else i++;
			}
	
	
	
	
	
	//lugar estatico para las variables globales.
	
	ArrayList<String> insVarGlobal= new ArrayList<String>();

	
	for (Iterator<EntradaTabla> it =ts.getGlobal().tabla.values().iterator();it.hasNext();){
		EntradaTabla et= it.next();
		Atributo att= et.getAtt();
		if (att instanceof AtributosTablaVariable){
			LugarRM l= new LugarRM(-1);
			l.setDescriptDirGlobal("varGlobal"+et.getLex());
			et.setDescriptDir(l);
			insVarGlobal.add("varGlobal"+et.getLex()+":RES 1");
		}
	}
	
	
	
	
	
	for (int i=0;i<entrada.size();i++){
		genCodigo(i);
	}
	
	ArrayList<String> insDelMain= new ArrayList<String>();
	
	
	/* no hace falta pues ahora hacemos una llamada
	//Poner el main el primero
	boolean mainEncontrado=false;
	int posMain=0;
	while (!mainEncontrado && posMain<salida.size()){
		if (salida.get(posMain).startsWith("comienzoFunmain")){
			mainEncontrado=true;
		}
		posMain++;
	}
	if (!mainEncontrado) return false;
	
	posMain--;
	boolean mainFinEncontrado=false;
	while (!mainFinEncontrado&& posMain<salida.size()){
		
		if (salida.get(posMain).startsWith("finFunmain")){
			mainFinEncontrado=true;
		}
		String delMain =salida.get(posMain);
		insDelMain.add(delMain);
		salida.remove(posMain);
		
		
	}
	if (!mainFinEncontrado) return false;
	
	*/
	
	
	

	
	
	
	
	//salida.addAll(0,insDelMain);
	
	
	salida.addAll(0,insVarGlobal);
	
	salida.add(0,"org 0");
	salida.add("end");
	return true;
}
	

}
	
	
	
	
	
	

