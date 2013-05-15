package codigoIntermadio;

public class InsTextoDirecto extends InstruccionIntermedio {

	String texto;
	public InsTextoDirecto() {
		super();
	
	}

	

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public InstruccionIntermedio nuevo(){
		return new InsTextoDirecto();
	}
	
	public String toString(){
		String sTexto="";
		if (texto!=null)sTexto=texto.toString();
		return super.toString()+sTexto;
	}
	
}
