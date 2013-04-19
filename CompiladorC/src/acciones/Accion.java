package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public abstract class  Accion {
	public abstract ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,HashMap<String,Object> atribActual,TablaSimbolos ts);
		
}
