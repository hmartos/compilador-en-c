package codigoIntermadio;

public class InsIfGoto extends InstruccionIntermedio {

	String dir;
	String op1;
	String op2;
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
		return new InsIfGoto();
	}
	
	
}
