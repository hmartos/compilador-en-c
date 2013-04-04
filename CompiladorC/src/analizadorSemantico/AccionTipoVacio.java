package analizadorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionTipoVacio extends Accion {

	public void ejecutar(ArrayList<Object> listaAtrib,HashMap<String, Object> atribActual) {
		atribActual.put("tipo", "vacio");

	}

}
