package analizadorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public abstract class Condicion {
	public abstract boolean evaluar (ArrayList<Object> listaAtrib,HashMap<String,Object> atribActual,TablaSimbolos ts);
}
