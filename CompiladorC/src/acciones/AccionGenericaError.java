package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import tablaSimbolos.TablaSimbolos;

public class AccionGenericaError extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		boolean error = false;
		for (Iterator<Object> itAtr=listaAtrib.iterator();itAtr.hasNext();){
			Object atr=itAtr.next();
			if (atr instanceof HashMap){
				Object hayError=((HashMap<String, Object>)atr).get("error");
				if (hayError!=null &&(Boolean)hayError)error=true;
			}
			// No hace falta especificar que hacer si es un token, pues en este caso no hay error.
		}
		atribActual.put("error", error);
		return new ArrayList<ErrorCompilador>();
	}

}
