package codigoIntermadio;

public class InsCuarteto extends InstruccionIntermedio {

	String res;
	String op1;
	String op2;
	String opRel;
	public InsCuarteto() {
		super();
	
		// TODO Auto-generated constructor stub
	}


	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
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
	
	
	
}
