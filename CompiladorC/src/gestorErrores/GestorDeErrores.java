package gestorErrores;


import java.util.ArrayList;


public class GestorDeErrores {
	
	private ArrayList<ErrorCompilador> cola;
	
	
	public GestorDeErrores(){
		cola=new ArrayList<ErrorCompilador>();
	}
	
	public void add(ErrorCompilador e){
		this.cola.add(e);
	}
	
	public void addLista(ArrayList<ErrorCompilador> lista){
		cola.addAll(lista);
	}

	public ArrayList<ErrorCompilador> getListaErrores() {
		return cola;
	}
	

}
