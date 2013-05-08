package codigoIntermadio;

public class InstruccionIntermedio {
	String etiqueta;
	
	public InstruccionIntermedio(String et){
		etiqueta=et;
	}
	
	public InstruccionIntermedio(){
		etiqueta=null;
	}

	
	
	
	
	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	
	public InstruccionIntermedio nuevo(){
		return new InstruccionIntermedio();
	}
	
	
	
	
	
	
	
}
