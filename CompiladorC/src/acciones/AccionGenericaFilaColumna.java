package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import tablaSimbolos.TablaSimbolos;


public class AccionGenericaFilaColumna extends Accion{

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) 
	{
		boolean busquedaIzqDer = true;
		boolean recorridoDerIzq= false;
		
		Iterator<Object> itAtr=listaAtrib.iterator();
		Object atr=itAtr.next();
		Object filaInicio = ((HashMap<String, Object>)atr).get("filaInicio");
		Object filaFin    = ((HashMap<String, Object>)atr).get("filaFin");
		
		while(itAtr.hasNext() && busquedaIzqDer)
		{
			atr=itAtr;
			// No Terminal
			if (atr instanceof HashMap)
			{
				if (filaInicio!=null)
				{	
					// Me kedo con ellas se la paso al padre
					busquedaIzqDer = false;
					atribActual.put("filaInicio", atr);
					recorridoDerIzq = true;
				}
			}
			// Terminal
			else
			{
				atr=itAtr;
				busquedaIzqDer = false;
				// no tengo que hacer recorrido derecha izquierda, asigno directamente.
				atribActual.put("filaInicio", atr);
				//atribActual.put("filaFin", hay);
				recorridoDerIzq = true;
			}
		}
		
		// Recorrido de derecha a izquierda
		ListIterator<Object> itDerIzq = listaAtrib.listIterator(listaAtrib.size());
		
		while(recorridoDerIzq && itDerIzq.hasPrevious() )
		{
			if (atr instanceof HashMap)
			{
				if (filaFin!=null)
				{	
					atr=itDerIzq;
					// Me kedo con ellas se la paso al padre
					atribActual.put("filaFin", atr);
					recorridoDerIzq = false;
				}
			}
			// Terminal
			else
			{	
				atr=itDerIzq;
				atribActual.put("filaFin", atr);
			}
		}
		return new ArrayList<ErrorCompilador>();
	}

}
