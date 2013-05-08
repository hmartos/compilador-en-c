package codigoIntermadio;

public class InsParam extends InstruccionIntermedio {

	String param;
	public InsParam() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	
	public InstruccionIntermedio nuevo(){
		return new InsParam();
	}
	
	
}
