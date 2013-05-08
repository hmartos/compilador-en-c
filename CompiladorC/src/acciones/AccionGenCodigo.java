package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InsCall;
import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InsGoto;
import codigoIntermadio.InsIfGoto;
import codigoIntermadio.InsParam;
import codigoIntermadio.InsReturn;
import codigoIntermadio.InstruccionIntermedio;

public class AccionGenCodigo extends Accion{
	
	InstruccionIntermedio ins;
	Operacion op1=null,op2=null,op3=null,op4=null,op5=null;
	
	public AccionGenCodigo (InstruccionIntermedio i){
		ins=i;
	}
	
	public AccionGenCodigo (InstruccionIntermedio i,Operacion op1){
		ins=i;
		this.op1=op1;
	}
	public AccionGenCodigo (InstruccionIntermedio i,Operacion op1,Operacion op2){
		ins=i;
		this.op1=op1;
		this.op1=op1;
	}
	public AccionGenCodigo (InstruccionIntermedio i,Operacion op1,Operacion op2,Operacion op3){
		ins=i;
		this.op1=op1;
		this.op2=op2;
		this.op3=op3;
	}
	public AccionGenCodigo (InstruccionIntermedio i,Operacion op1,Operacion op2,Operacion op3,Operacion op4){
		ins=i;
		this.op1=op1;
		this.op2=op2;
		this.op3=op3;
		this.op4=op4;
	}
	public AccionGenCodigo (InstruccionIntermedio i,Operacion op1,Operacion op2,Operacion op3,Operacion op4,Operacion op5){
		ins=i;
		this.op1=op1;
		this.op2=op2;
		this.op3=op3;
		this.op4=op4;
		this.op5=op5;
	}
	
	

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {
		
		
		ArrayList<InstruccionIntermedio> listaCod = (ArrayList<InstruccionIntermedio>)(atribActual.get("codigo"));
		
		ins=ins.nuevo();
		Object ob1=null;
		Object ob2=null;
		Object ob3=null;
		Object ob4=null;
		Object ob5=null;
		
		if (op1!=null)ob1=op1.getValor(listaAtrib, atribActual, ts,ci);
		if (op2!=null)ob2=op2.getValor(listaAtrib, atribActual, ts,ci);
		if (op3!=null)ob3=op3.getValor(listaAtrib, atribActual, ts,ci);
		if (op4!=null)ob4=op4.getValor(listaAtrib, atribActual, ts,ci);
		if (op5!=null)ob5=op5.getValor(listaAtrib, atribActual, ts,ci);
		
		
		
		if (ins instanceof InsCall){
			((InsCall)ins).setEtiqueta((String)ob1);
			((InsCall)ins).setDir((String)ob2);
			((InsCall)ins).setNum((Integer)ob3);
			listaCod.add(ins);
			
		}else if (ins instanceof InsCuarteto){
			((InsCuarteto)ins).setEtiqueta((String)ob1);
			((InsCuarteto)ins).setRes((String)ob2);
			((InsCuarteto)ins).setOp1((String)ob3);
			((InsCuarteto)ins).setOpRel((String)ob4);
			((InsCuarteto)ins).setOp2((String)ob5);
			listaCod.add(ins);
			
		}else if (ins instanceof InsGoto){
			((InsGoto)ins).setEtiqueta((String)ob1);
			((InsGoto)ins).setDir((String)ob2);
			listaCod.add(ins);
			
		}else if (ins instanceof InsIfGoto){
			((InsIfGoto)ins).setEtiqueta((String)ob1);
			((InsIfGoto)ins).setOp1((String)ob2);
			((InsIfGoto)ins).setOpRel((String)ob3);
			((InsIfGoto)ins).setOp2((String)ob3);
			((InsIfGoto)ins).setDir((String)ob4);
			listaCod.add(ins);
		
		}else if (ins instanceof InsParam){
			((InsParam)ins).setEtiqueta((String)ob1);
			((InsParam)ins).setParam((String)ob2);
			listaCod.add(ins);
		
		}else if (ins instanceof InsReturn){
			listaCod.add(ins);
		}
		
		
		return new ArrayList<ErrorCompilador>();
	}

}
