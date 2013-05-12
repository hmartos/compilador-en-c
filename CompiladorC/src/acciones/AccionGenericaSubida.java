package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.TablaSimbolos;

public class AccionGenericaSubida extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		for (Iterator<Object> itAtr=listaAtrib.iterator();itAtr.hasNext();){
			Object atr=itAtr.next();
			if (atr instanceof HashMap){
				HashMap<String,Object >atrH=((HashMap<String, Object>)atr);
				for (Iterator<String> itK=atrH.keySet().iterator();itK.hasNext();){
					String k= itK.next();
					if(k!="filaInicio"&&k!="colInicio"&&k!="filaFin"&&k!="colFin"&&k!="codigo")atribActual.put(k, atrH.get(k)); 
					//El valor antiguo es remplazado, por lo que si hay colisiones, hay que resolverlas manualmente.
					//Este codigo se ejecutara cuando los errores sean false, asique no debe haber problemas al cojer el error del ultimo.(sera false igual)
				}
				
				
				
				
			}
			// No hace falta especificar que hacer si es un token, pues los tokens siempre hay que asignarlos a atributos de forma manual.
		}
		
		return new ArrayList<ErrorCompilador>();
	}

}
