package analizadorSemantico;

import gestorErrores.ErrorCompilador;
import gestorErrores.GestorDeErrores;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

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
		/*
		Accion[] listAcciones=AccionesGramatica.acciones[nT.ordinal()][nRegla];
		// Aqui pueden ir acciones predeterminadas para todos.
		
		ArrayList<ErrorCompilador> listaErrores=new ArrayList<ErrorCompilador>();
		for (int i=0;i<listAcciones.length;i++){
			listaErrores.addAll(listAcciones[i].ejecutar(listaAtrib,atribActual,ts));
		}
		ge.addLista(listaErrores);
		// Aqui pueden ir acciones predeterminadas para todos.
		*/
	}
	
}
