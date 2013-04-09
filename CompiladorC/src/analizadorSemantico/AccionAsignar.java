package analizadorSemantico;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import token.Token;

public class AccionAsignar extends Accion {

	/*
	 * atrReceptor es el nombre del atributo (del nodo actual) que va a recibir la asignacion.
	 
	 * emisor1,2 es el numero del elemento de la regla del que se van a usar sus atributos.
	 
	 * emisor 1,2 puede ser entre 0 y +... para usar los atributos de los hijos.
	  		-1 hace que se refiera a los propios atributos del nodo actual
	   		inferior a -1 se ignoran los atributos y representa que se usa un valor directo.
	 
	 * atrEmisor1,2 es el nombre del atributo (de los hijos,o el propio nodo) 
	  		que va a dar valor a la asignacion.
	 
	 * valor1,2 son los valores directos que se pueden usar si se especifica con emisores < -1
	
	 * operacion es un string definiendo que operacion se quiere usar. El metodo operar
	  		reconocerá estos strings dependiendo el tipo (Hay que definirlos manualmente)
	
	 */
	
	String atrReceptor;
	String atrEmisor1;
	String atrEmisor2;
	String operacion;
	int emisor1;
	int emisor2;
	Object valor1;
	Object valor2;
	
	
	//Asignacion simple entre atributos
	/*	
	//Atributos con el mismo nombre
	public AccionAsignar(String atrReceptor, int emisor){
		this();
		this.atrReceptor=atrReceptor;
		this.emisor1=emisor;
		this.atrEmisor1=atrReceptor;
	}*/
		//Atributos con distinto nombre
	public AccionAsignar(String atrReceptor,int emisor,String atrEmisor){
		this();
		this.atrReceptor=atrReceptor;
		this.emisor1=emisor;
		this.atrEmisor1=atrEmisor;
	}
			
	
	//Asignacion directa (con valor)
	public AccionAsignar(String atrReceptor,Object valor1){
		this();
		this.atrReceptor=atrReceptor;
		this.valor1=valor1;
	}
	//Asignaciones con operacion entre atributos
	public AccionAsignar(String atrReceptor, int emisor1,String atrEmisor1,
			String operacion,int emisor2,String atrEmisor2 ) {
		this();
		this.atrReceptor = atrReceptor;
		this.atrEmisor1 = atrEmisor1;
		this.atrEmisor2 = atrEmisor2;
		this.operacion = operacion;
		this.emisor1 = emisor1;
		this.emisor2 = emisor2;
	}
	//Asignaciones con operacion entre atributo y valor
	public AccionAsignar(String atrReceptor, int emisor1,String atrEmisor1,
			String operacion,Object valor2 ) {
		this();
		this.atrReceptor = atrReceptor;
		this.atrEmisor1 = atrEmisor1;

		this.operacion = operacion;
		this.emisor1 = emisor1;
		this.valor2=valor2;
	}
	
	
	public AccionAsignar() {
		super();
		atrReceptor=null;
		atrEmisor1=null;
		atrEmisor2=null;
		operacion=null;
		emisor1=-2;
		emisor2=-2;
		valor1=null;
		valor2=null;
		
	}
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,HashMap<String, Object> atribActual,TablaSimbolos ts) {
		//Object oper1 = valor1==null ? (listaAtrib.get(emisor1).
		Object oper1=null;
		Object oper2=null;
		if (emisor1<-1){ // es un valor directo, deberia estar asignado valor, si no oper1=null
			oper1=valor1;
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
		if (operacion!=null){ //Recogemos el valor del oper2 solo si hay operacion
			
			//mismo codigo que para oper1
			if (emisor2<-1){ //Es un valor directo
				oper1=valor2;
			}else{ //Es un valor de la listaAtrib.
				Object emi;
				if (emisor1==-1) emi=atribActual; //es atribActual
				else emi=listaAtrib.get(emisor1);// es de listaAtrib
				
				if (emi instanceof HashMap){// es una lista de atributos de un NT
					oper2=((HashMap<String, Object>) emi).get(atrEmisor2);
				}else if (emi instanceof Token){// es un token de un T
					oper2=((Token)emi).getAtributo();  
				
				} 
				
			}
		}
		
		Object resultado;
		if (operacion!=null){
			resultado=this.operar(oper1,operacion,oper2);
		}else{
			resultado=oper1;
		}
		atribActual.put(atrReceptor, resultado);
		return new ArrayList<ErrorCompilador>();

	}
	
	private Object operar(Object oper1,String operacion,Object oper2){
		//Metodo para añadir operaciones para los distintos objetos
		if ((oper1 instanceof Integer)&& (oper2 instanceof Integer)){
			Integer op1= (Integer)oper1;
			Integer op2=(Integer)oper2;
			if (operacion.equals("suma")){
				return op1+op2;
			}else if (operacion.equals("resta")){
				return op1-op2;
			}
		}
		return null;
	}


}
