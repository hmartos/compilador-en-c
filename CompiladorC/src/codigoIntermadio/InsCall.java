package codigoIntermadio;

public class InsCall extends InstruccionIntermedio {

	String dir;
	int num;
	public InsCall() {
		super();
		
		
	}



	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public InstruccionIntermedio nuevo(){
		return new InsCall();
	}
	
	
	
}
