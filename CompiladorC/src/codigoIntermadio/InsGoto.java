package codigoIntermadio;

public class InsGoto extends InstruccionIntermedio {

	String dir;
	public InsGoto() {
		super();
	
	}

	

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public InstruccionIntermedio nuevo(){
		return new InsGoto();
	}
	
	public String toString(){
		return super.toString()+"goto "+dir.toString();
	}
	
}
