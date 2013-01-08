package gestorErrores;

import gestorErrores.ErrorLexico.TipoErrorLexico;
import tablaSimbolos.PalRes;
import token.Token;

public class ErrorSintactico extends ErrorCompilador {
	
	Token tokenRecibido;
	Object esperado;
	
	public ErrorSintactico(int row,int col,String s,Token tR,Object esp) {
		super(row,col,s);
		super.tipo=ErrorCompilador.TipoError.SINTACTICO;
		tokenRecibido=tR;
		esperado=esp;
	}
	
	public ErrorSintactico(int row,int col,String s,Token tR) {
		super(row,col,s);
		super.tipo=ErrorCompilador.TipoError.SINTACTICO;
		tokenRecibido=tR;
		esperado=null;
	}
	
	public String toString(){
		
		String cadena=super.toString()+": ";
		cadena+="reconocido "+tokenRecibido.toString();
		if (esperado==null){
			cadena+=" no esperado en la sintaxis.";
		}else if (esperado instanceof PalRes){
			cadena+=". Se esperaba la palabra clave "+esperado.toString();
		}else{
			cadena+=". Se esperaba "+esperado.toString();

		}
		
		
		
		return description;
	}
	
}
