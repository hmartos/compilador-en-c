package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class AccionCerrarAmbito extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		ts.cerrarAmbito();
		return new ArrayList<ErrorCompilador>();
	}

}
