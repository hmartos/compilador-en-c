package codigoFinal;

import java.util.ArrayList;
import java.util.Iterator;

import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class CodigoFinal {

	ArrayList<InstruccionIntermedio> entrada;
	ArrayList<String> salida;

	TablaSimbolos ts;

	ArrayList<ArrayList<EntradaTabla>> descriptReg; 
	//el primer array se indexa por número de registro. 
	//El segundo contiene los nombres que referencian a este registro. 
	
	
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
				
				if (descriptReg.get(lugarY.getDescriptDirReg()).size()==1){ //el registro solo está referenciado por un nombre(el del Y).
					if (!seUsa(nombreY,numInst+1,1)){ //El nombre y no se usa en la(s) siguiente(s) instruccion(es).
						if (lugarY.isActualizadoReg()){ //Esta actualizado, no hay que hacer store.
							return lugarY;
						}else if (registroLibre!=-1){ //Hay registros libres
							//Mover y al registro nuevo.
							return new LugarRM(registroLibre);
						} else {					// No hay registros libres.
							//store de y
							return lugarY;
						}
					}
				}	
					
					
			}
			if (registroLibre!=-1){ //El valor de y no esta en un registro, o ese registro está referenciado, o se usará proximamente.
				//Hay registros libres
				//Mover y al registro nuevo.
				return new LugarRM(registroLibre);
			}else if (!seUsa(nombreY,numInst+1,1)&&operaMemoria(instC.getOpRel())){//Operaremos desde memoria
				//if x en registro, store X.
				return instC.getRes().getDescriptDir();
				
			}else{ //Necesitamos un registro para operar, obtenemos uno adecuado.
				liberarRegOcupado();
				//Mover y al registro nuevo.
			}
				
				
		}
	}
		

	
public void genCodigo(int numInst ){
	
	LugarRM lugarX= obtenLugar(numInst);
	InstruccionIntermedio inst=entrada.get(numInst);
	
	//Instrucciones insCuarteto, tipo : x=y,  x=y op,  x=y op z;
	if (inst instanceof InsCuarteto){
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
				if (op.equals("+")){
					if(nombreZ.getDescriptDir().estaEnRegistro()){
						
					}else if(nombreZ.getDescriptDir().estaEnRegistro()){
						
					}
				}else if (op.equals("-")){
					
				}else if (op.equals("*")){
					
				}else if (op.equals("&")){
					
				}
			}
		}
	}
	
}

	
	

}
	
	
	
	
	
	

