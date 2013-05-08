package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class AccionCerrarAmbito extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		ts.cerrarAmbito();
		return new ArrayList<ErrorCompilador>();
	}

}
