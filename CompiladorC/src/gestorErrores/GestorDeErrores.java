package gestorErrores;


import java.util.ArrayList;


public class GestorDeErrores {
	
	private ArrayList<ErrorCompilador> cola;
	
	
	public GestorDeErrores(){
		cola=new ArrayList<ErrorCompilador>();
	}
	
	public void add(ErrorLexico e){
		this.cola.add(e);
	}

	public ArrayList<ErrorCompilador> getListaErrores() {
		return cola;
	}
	

}
