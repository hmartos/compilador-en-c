package analizadorSemantico;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import token.Token;

public class CondicionEsCompatible {

	String atrEmisor1;
	String atrEmisor2;
	
	int emisor1;
	int emisor2;
	
	public CondicionEsCompatible( int emisor1,String atrEmisor1,
			int emisor2,String atrEmisor2 ) {
		
		this.atrEmisor1 = atrEmisor1;
		this.atrEmisor2 = atrEmisor2;
		
		this.emisor1 = emisor1;
		this.emisor2 = emisor2;

	}
	
	public ArrayList<ErrorCompilador> evaluar(ArrayList<Object> listaAtrib,HashMap<String, Object> atribActual,TablaSimbolos ts) {
		//Object oper1 = valor1==null ? (listaAtrib.get(emisor1).
		Object oper1=null;
		Object oper2=null;
		if (emisor1<-1){ // es un valor directo, deberia estar asignado valor, si no oper1=null
			
		}else{ //Es un valor de la listaAtrib o del propio atribActual.
			Object emi;
			if (emisor1==-1) emi=atribActual; //es atribActual
			else emi=listaAtrib.get(emisor1);// es de listaAtrib
			
			if (emi instanceof HashMap){// es una lista de atributos de un NT
				oper1=((HashMap<String, Object>) emi).get(atrEmisor1);
			}else if (emi instanceof Token){// es un token de un T
				oper1=((Token)emi).getAtributo(); //le asignamos su atributo. 
				//Esto solo debe de ser usado para valores de los atributos como enteros, cadenas, etc
			
			} //si no es ninguno de los dos hay algun error en la constuccion.
			
		}
		
			//mismo codigo que para oper1
			if (emisor2<-1){ 
				
			}else{ //Es un valor de la listaAtrib.
				Object emi;
				if (emisor2==-1) emi=atribActual; //es atribActual
				else emi=listaAtrib.get(emisor2);// es de listaAtrib
				
				if (emi instanceof HashMap){// es una lista de atributos de un NT
					oper2=((HashMap<String, Object>) emi).get(atrEmisor2);
				}else if (emi instanceof Token){// es un token de un T
					oper2=((Token)emi).getAtributo();  
				
				} 
				
			}
		}
		
		boolean hacerTrue;
	
	
}
