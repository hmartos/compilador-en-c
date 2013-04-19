package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public interface  Condicion extends Operacion  {
	public  Boolean getValor (ArrayList<Object> listaAtrib,HashMap<String,Object> atribActual,TablaSimbolos ts);
}
