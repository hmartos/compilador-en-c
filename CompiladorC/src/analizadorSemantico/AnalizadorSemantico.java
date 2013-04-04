package analizadorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import analizadorSintactico.Gramatica;
import analizadorSintactico.NT;

public class AnalizadorSemantico {

	
	public void ejecutar(NT nT,int nRegla,ArrayList<Object> listaAtrib,HashMap<String,Object>atribActual){
		Accion[] listAcciones=AccionesGramatica.acciones[nT.ordinal()][nRegla];
		for (int i=0;i<listAcciones.length;i++){
			 listAcciones[i].ejecutar(listaAtrib,atribActual);
		}
	}
	
}
