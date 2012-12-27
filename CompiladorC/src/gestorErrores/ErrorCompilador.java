package gestorErrores;
public class ErrorCompilador {
	protected int row;
	protected int column;
	protected String description;
	protected TipoError tipo;
	
	
	
	ErrorCompilador(int row,int col,String s){
		description=s;
		this.row=row;
		this.column=col;
	}
	enum TipoError{
		LEXICO,SINTACTICO,SEMANTICO
	};
	
	public String toString(){
		String cadena="Error "+this.tipo;
		return cadena;
	}
}
