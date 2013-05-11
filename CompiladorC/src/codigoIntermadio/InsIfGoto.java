package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsIfGoto extends InstruccionIntermedio {

	String dir;
	EntradaTabla op1;
	EntradaTabla op2;
	String opRel;
	public InsIfGoto() {
		super();
		
		
		// TODO Auto-generated constructor stub
	}



	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public EntradaTabla getOp1() {
		return op1;
	}

	public void setOp1(EntradaTabla op1) {
		this.op1 = op1;
	}

	public EntradaTabla getOp2() {
		return op2;
	}

	public void setOp2(EntradaTabla op2) {
		this.op2 = op2;
	}

	public String getOpRel() {
		return opRel;
	}

	public void setOpRel(String opRel) {
		this.opRel = opRel;
	}

	
	public InstruccionIntermedio nuevo(){
		return new InsIfGoto();
	}
	
	
	public String toString(){
		return super.toString()+"if("+op1.toString()+opRel.toString()+op2.toString()+") goto "+dir.toString();
	}
	
}
