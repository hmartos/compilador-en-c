package acciones;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class AccionGenError extends Accion {

	Operacion op;
	
	public AccionGenError (Operacion op){
		this.op=op;
	}
	
	public AccionGenError (String s){
		this.op= new OperandoDirecto(s);
	}
	
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> l= new ArrayList<ErrorCompilador>( );
		l.add(new ErrorSemantico(op.getValor(listaAtrib, atribActual, ts).toString()));
		
		
		atribActual.put("error", true);
		return l;
		
	}

}
