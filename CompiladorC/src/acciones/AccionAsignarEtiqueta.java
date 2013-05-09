package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InstruccionIntermedio;

public class AccionAsignarEtiqueta extends Accion {

	Operacion opListaCod;
	Operacion opEtiqueta;
	int lugar;
	
	
	
	public AccionAsignarEtiqueta(Operacion listaCod, Operacion etiqueta,
			int lugar) {
		super();
		this.opListaCod = listaCod;
		this.opEtiqueta = etiqueta;
		this.lugar = lugar;
	}



	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {

		ArrayList<InstruccionIntermedio> listaCod=(ArrayList<InstruccionIntermedio>)opListaCod.getValor(listaAtrib, atribActual, ts, ci);
		String etiqueta=(String)opEtiqueta.getValor(listaAtrib, atribActual, ts, ci);
		listaCod.get(lugar).setEtiqueta(etiqueta);
		
		return new ArrayList<ErrorCompilador>();
	}

}
