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
	
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> l= new ArrayList<ErrorCompilador>( );
		l.add(new ErrorSemantico(op.getValor(listaAtrib, atribActual, ts).toString()));
		return l;
		
	}

}
