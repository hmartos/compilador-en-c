package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class AccionAbrirAmbito extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		ts.nuevoAmbito();
		return new ArrayList<ErrorCompilador>();
	}

}
