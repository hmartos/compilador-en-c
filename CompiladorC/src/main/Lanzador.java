package main;

import javax.swing.JFrame;

import interfaz.ClasePrincipal;

public class Lanzador {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClasePrincipal o = new ClasePrincipal();
		o.setVisible(true);
		o.setEnabled(true);
		o.setSize(1000,600);
		o.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	/*
	public static void main(String[] args) {
		AnalizadorLexico al =AnalizadorLexico();
		TablaSimbolos ts=TablaSimbolos();
		
		
		
		
	}
*/	
	

}
