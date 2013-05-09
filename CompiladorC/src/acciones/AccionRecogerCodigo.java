package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InstruccionIntermedio;

public class AccionRecogerCodigo extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {
		
		ci.setLista((ArrayList<InstruccionIntermedio>)atribActual.get("codigo"));
		
		return new ArrayList<ErrorCompilador>();
	}

}
