package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public abstract class  Accion {
	public abstract ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,HashMap<String,Object> atribActual,TablaSimbolos ts,CodigoIntermedio ci);
		
}
