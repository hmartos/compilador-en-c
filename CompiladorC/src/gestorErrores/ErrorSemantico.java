package gestorErrores;

public class ErrorSemantico extends ErrorCompilador {
	
	public ErrorSemantico(String s){
		super(0,0,s);
		super.tipo=ErrorCompilador.TipoError.SEMANTICO;
	}
}
