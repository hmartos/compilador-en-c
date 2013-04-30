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
		boolean hay = false;
		boolean busquedaIzqDer = true;
		boolean recorridoDerIzq= false;
		
		Iterator<Object> itAtr=listaAtrib.iterator();
		Object atr=itAtr.next();
		Object filaInicio = ((HashMap<String, Object>)atr).get("filaInicio");
		Object filaFin    = ((HashMap<String, Object>)atr).get("filaFin");
		
		while(itAtr.hasNext() && busquedaIzqDer)
		{
			
			// No Terminal
			if (atr instanceof HashMap)
			{
				if (filaInicio!=null)
				{	
					// Me kedo con ellas se la paso al padre
					hay = true;
					busquedaIzqDer = false;
					atribActual.put("filaInicio", hay);
					recorridoDerIzq = true;
				}
			}
			// Terminal
			else
			{
				hay = true;
				busquedaIzqDer = false;
				// no tengo que hacer recorrido derecha izquierda, asigno directamente.
				atribActual.put("filaInicio", hay);
				//atribActual.put("filaFin", hay);
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
					// Me kedo con ellas se la paso al padre
					hay = true;
					atribActual.put("filaFin", hay);
					recorridoDerIzq = false;
				}
			}
			// Terminal
			else
			{
				hay = true;
				atribActual.put("filaFin", hay);
			}
		}
		return new ArrayList<ErrorCompilador>();
	}

}
