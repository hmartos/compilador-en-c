package main;

import interfaz.ClasePrincipal;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import analizadorLexico.AnalizadorLexico;

public class Lanzador {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClasePrincipal o = new ClasePrincipal();
		o.setVisible(true);
		o.setEnabled(true);
		o.setSize(1000,600);
		
		
	}
	
	/*
	public static void main(String[] args) {
		AnalizadorLexico al =AnalizadorLexico();
		TablaSimbolos ts=TablaSimbolos();
		
		
		
		
	}
*/	
	

}
