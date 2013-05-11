package codigoIntermadio;

import tablaSimbolos.EntradaTabla;

public class InsCuarteto extends InstruccionIntermedio {

	EntradaTabla res;
	EntradaTabla op1;
	EntradaTabla op2;
	String opRel;
	public InsCuarteto() {
		super();
	
		// TODO Auto-generated constructor stub
	}


	public EntradaTabla getRes() {
		return res;
	}

	public void setRes(EntradaTabla res) {
		this.res = res;
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
		return new InsCuarteto();
	}
	
	public String toString(){
		
		
		String sOp2="",sOpRel="",sOp1="",sRes="";
		
		if (opRel!=null)sOpRel=opRel.toString();
		if (op2!=null)sOp2=op2.toString();
		if (op1!=null)sOp1=op1.toString();
		if (res!=null)sRes=res.toString();
		
		return super.toString()+sRes+":="+sOp1+sOpRel+sOp2;
	}
	
}
