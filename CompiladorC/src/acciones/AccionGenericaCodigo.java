package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.TablaSimbolos;

public class AccionGenericaCodigo extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		 ArrayList<InstruccionIntermedio> listaCod= new ArrayList<InstruccionIntermedio>();
		
		for (Iterator<Object> itAtr=listaAtrib.iterator();itAtr.hasNext();){
			
			Object atr=itAtr.next();
			if (atr instanceof HashMap){
				Object listaCodHijo=((HashMap<String, Object>)atr).get("codigo");
				if (listaCodHijo!=null){
					listaCod.addAll(0,(ArrayList<InstruccionIntermedio>)listaCodHijo);
				}
			}
		}
		
		
		atribActual.put("codigo",listaCod );
		return new ArrayList<ErrorCompilador>();
	}

}
