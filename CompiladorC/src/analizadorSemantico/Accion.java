package analizadorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class  Accion {
	public abstract void ejecutar(ArrayList<Object> listaAtrib,HashMap<String,Object>atribActual);
		
}
