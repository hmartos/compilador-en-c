package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import tablaSimbolos.TablaSimbolos;
import token.Token;


public class AccionGenericaFilaColumna extends Accion{

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) 
	{
		boolean busquedaIzqDer = true;
		boolean recorridoDerIzq= false;
		
		Iterator<Object> itAtr=listaAtrib.iterator();
		
		
		
		while(itAtr.hasNext() && busquedaIzqDer)
		{
			Object atr=itAtr.next();
			// No Terminal
			if (atr instanceof HashMap)
			{
				Object filaInicio = ((HashMap<String, Object>)atr).get("filaInicio");
				Object colInicio = ((HashMap<String, Object>)atr).get("colInicio");

				if (filaInicio!=null)
				{	
					// Me kedo con ellas se la paso al padre
					busquedaIzqDer = false;
					atribActual.put("filaInicio", filaInicio);
					atribActual.put("colInicio", colInicio);
					recorridoDerIzq = true;
				}
			}
			// Terminal
			else
			{
				Token tok= (Token) atr;
				
				busquedaIzqDer = false;
				// no tengo que hacer recorrido derecha izquierda, asigno directamente.
				atribActual.put("filaInicio", tok.getLinea());
				atribActual.put("fcolInicio", tok.getCol());
				//atribActual.put("filaFin", hay);
				recorridoDerIzq = true;
			}
		}
		
		// Recorrido de derecha a izquierda
		ListIterator<Object> itDerIzq = listaAtrib.listIterator(listaAtrib.size());
		
		while(recorridoDerIzq && itDerIzq.hasPrevious() )
		{
			Object atr = itDerIzq.hasPrevious();
			if (atr instanceof HashMap)
			{
				Object filaFin = ((HashMap<String, Object>)atr).get("filaFin");
				Object colFin = ((HashMap<String, Object>)atr).get("colFin");
				if (filaFin!=null)
				{	
					// Me kedo con ellas se la paso al padre
					atribActual.put("filaFin", filaFin);
					atribActual.put("colFin", colFin);
					recorridoDerIzq = false;
				}
			}
			// Terminal
			else
			{	
				Token tok= (Token) atr;
				atribActual.put("filaFin", tok.getLinea());
				atribActual.put("fcolFin", tok.getCol());
			}
		}
		return new ArrayList<ErrorCompilador>();
	}

}
