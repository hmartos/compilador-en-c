package acciones;

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
	
	
	
	
	Operacion operacion;
	String atrReceptor;
	
	//Asignacion simple entre atributos
	/*	
	//Atributos con el mismo nombre
	public AccionAsignar(String atrReceptor, int emisor){
		this();
		this.atrReceptor=atrReceptor;
		this.emisor1=emisor;
		this.atrEmisor1=atrReceptor;
	}*/
		
	
	public AccionAsignar(String atrReceptor, Operacion operacion){
		this(atrReceptor);
		this.operacion=operacion;
	}
	
	//Atributos con distinto nombre
	public AccionAsignar(String atrReceptor,int emisor,String atrEmisor){
		this(atrReceptor);
		operacion = new OperandoGramatica(emisor,atrEmisor);
	}
			
	
	//Asignacion directa (con valor)
	public AccionAsignar(String atrReceptor,Object valor1){
		this(atrReceptor);
		operacion= new OperandoDirecto(valor1);
	}
	//Asignaciones con operacion entre atributos
	public AccionAsignar(String atrReceptor, int emisor1,String atrEmisor1,
			String operacion,int emisor2,String atrEmisor2 ) {
		this(atrReceptor);
		this.operacion = new OperacionHeredada (new OperandoGramatica(emisor1,atrEmisor1),new OperandoGramatica (emisor2,atrEmisor2),operacion);
	}
	//Asignaciones con operacion entre atributo y valor
	public AccionAsignar(String atrReceptor, int emisor1,String atrEmisor1,
			String operacion,Object valor2 ) {
		this(atrReceptor);
		this.atrReceptor=atrReceptor;
		this.operacion = new OperacionHeredada (new OperandoGramatica(emisor1,atrEmisor1),new OperandoDirecto(valor2),operacion);

	}
	
	
	public AccionAsignar(String atrReceptor) {
		super();
		this.atrReceptor=atrReceptor;
		
		
		
	}
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,HashMap<String, Object> atribActual,TablaSimbolos ts) {
		//Object oper1 = valor1==null ? (listaAtrib.get(emisor1).
		
		
		
		atribActual.put(atrReceptor, operacion.getValor(listaAtrib, atribActual, ts));
		return new ArrayList<ErrorCompilador>();

	}
	



}
