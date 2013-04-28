package analizadorSemantico;

import gestorErrores.ErrorCompilador;
import gestorErrores.GestorDeErrores;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

import acciones.Accion;
import acciones.AccionGenericaError;
import acciones.AccionGenericaSubida;
import analizadorSintactico.Gramatica;
import analizadorSintactico.NT;

public class AnalizadorSemantico {

	GestorDeErrores ge;
	TablaSimbolos ts;
	
	public AnalizadorSemantico(GestorDeErrores ge,TablaSimbolos ts){
		this.ge=ge;
		this.ts=ts;
	}
	
	public void ejecutar(NT nT,int nRegla,ArrayList<Object> listaAtrib,HashMap<String,Object>atribActual){
		
		System.out.println("Ejecutando acciones de:"+(nT.ordinal()+1)+":"+(nRegla+1));
		//Lista de errores para el gestor.
		ArrayList<ErrorCompilador> listaErrores=new ArrayList<ErrorCompilador>();

		
		
		//Ejecutamos la accion predeterminada para los errores.
		new AccionGenericaError().ejecutar(listaAtrib, atribActual, ts);
		//Ejecutamos las acciones especificas sobre la tabla de errores.
		Accion[] listAcciones=AccionesError.acciones[nT.ordinal()][nRegla];
		for (int i=0;i<listAcciones.length;i++){
			ArrayList<ErrorCompilador> l=listAcciones[i].ejecutar(listaAtrib,atribActual,ts);
			if (l!=null)listaErrores.addAll(l);
		}
		ge.addLista(listaErrores);
		listaErrores=new ArrayList<ErrorCompilador>();
		//Comprobamos que el valor error, no se ha puesto a true, y continuamos ejecutando:
		if (!(Boolean)atribActual.get("error")){
			//Ejecutamos la accion predefinida de subida de atributos.
			new AccionGenericaSubida().ejecutar(listaAtrib, atribActual, ts);
			atribActual.put("error", false); //Si al subir los argumentos se modifica el error, este debe de ser false. (Ya esta comprobado antes)
			//Ejecutamos las acciones del comprobador de tipo (Aqui tambien se pueden generar errores)
			//Los errores aquí generados subiran a sus padres y estos los gestionaran mediante las AccionesError.
			listAcciones=AccionesTipos.acciones[nT.ordinal()][nRegla];
			for (int i=0;i<listAcciones.length;i++){
				ArrayList<ErrorCompilador> l=listAcciones[i].ejecutar(listaAtrib,atribActual,ts);
				if (l!=null)listaErrores.addAll(l);			
				}
			ge.addLista(listaErrores);
			listaErrores=new ArrayList<ErrorCompilador>();
		}
		//Si el comprobador de tipos no ha dado errores ejecutamos el generador de codigo.
		if (!(Boolean)atribActual.get("error")){
			listAcciones=AccionesGenCodigo.acciones[nT.ordinal()][nRegla];
			System.out.println("Generando codigo... de:"+(nT.ordinal()+1)+":"+(nRegla+1));

			for (int i=0;i<listAcciones.length;i++){
				ArrayList<ErrorCompilador> l=listAcciones[i].ejecutar(listaAtrib,atribActual,ts);
				if (l!=null)listaErrores.addAll(l);
			}
			ge.addLista(listaErrores);
			listaErrores=new ArrayList<ErrorCompilador>();
		}
		// Aqui pueden ir acciones predeterminadas para todos.
		
		// Aqui pueden ir acciones predeterminadas para todos.
		
	}
	
}
