package gestorErrores;

public class ErrorSemantico extends ErrorCompilador {
	
	public ErrorSemantico(int row,int col,String s){
		super(row,col,s);
		super.tipo=ErrorCompilador.TipoError.SEMANTICO;
	}
}
