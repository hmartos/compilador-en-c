package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import token.Token;

public class AccionCondicionada extends Accion {

	/*
	 
	 * emisor1,2 es el numero del elemento de la regla del que se van a usar sus atributos.
	 
	 * emisor 1,2 puede ser entre 0 y +... para usar los atributos de los hijos.
	  		-1 hace que se refiera a los propios atributos del nodo actual
	   		inferior a -1 se ignoran los atributos y representa que se usa un valor directo.
	 
	 * atrEmisor1,2 es el nombre del atributo (de los hijos,o el propio nodo) 
	  		que va a dar valor a la asignacion.
	 
	 * valor1,2 son los valores directos que se pueden usar si se especifica con emisores < -1
	
	 * operacion es un string definiendo que operacion se quiere usar. El metodo operar
	  		reconocerá estos strings dependiendo el tipo (Hay que definirlos manualmente)
	 
	 * accionesTrue y False son las acciones que se llevaran a cabo en caso de que la condicion
	 		sea cierta o falsa respectivamente.
	 */
	
	Accion[] accionesTrue;
	Accion[] accionesFalse;
	
	
	Condicion condicion;
	
	
	
	
	
	//Comparaciones entre atributos con listas de acciones
	public AccionCondicionada(Condicion cond,Accion[] accionesTrue,Accion[] accionesFalse){
		this();
		condicion = cond;
		this.accionesTrue=accionesTrue;
		this.accionesFalse=accionesFalse;
	}
		
	//Comparaciones entre atributos con listas de acciones
	public AccionCondicionada( int emisor1,String atrEmisor1,String operacion,
			int emisor2,String atrEmisor2,Accion[] accionesTrue,Accion[] accionesFalse ) {
		this();
		condicion = new CondicionHeredada(
				new OperandoGramatica(emisor1,atrEmisor1),
				new OperandoGramatica (emisor2,atrEmisor2),operacion
				);
	
		this.accionesTrue=accionesTrue;
		this.accionesFalse=accionesFalse;
	}
	//Comparaciones entre atributos con accion unica
	public AccionCondicionada( int emisor1,String atrEmisor1,String operacion,
			int emisor2,String atrEmisor2,Accion accionesTrue,Accion accionesFalse ) {
		this();
		condicion = new CondicionHeredada(
				new OperandoGramatica(emisor1,atrEmisor1),
				new OperandoGramatica (emisor2,atrEmisor2),operacion
				);
		if (accionesTrue!=null){
			this.accionesTrue=new Accion[1];
			this.accionesTrue[0]=accionesTrue;
		}
		if (accionesFalse!=null){
			this.accionesFalse=new Accion[1];
			this.accionesFalse[0]=accionesFalse;
		}
		
		
	}
	//Solo con lista de accionesTrue
	public AccionCondicionada( int emisor1,String atrEmisor1,String operacion,
			int emisor2,String atrEmisor2,Accion[] accionesTrue){
		this(emisor1,atrEmisor1,operacion,emisor2,atrEmisor2,accionesTrue,null);
	}
	//Solo con accion unica True
	public AccionCondicionada( int emisor1,String atrEmisor1,String operacion,
			int emisor2,String atrEmisor2,Accion accionesTrue){
		this(emisor1,atrEmisor1,operacion,emisor2,atrEmisor2,accionesTrue,null);
	}
	//Comparacion entre atributo y valor con listas de acciones
	public AccionCondicionada( int emisor1,String atrEmisor1,
			String operacion,Object valor2,Accion[] accionesTrue,Accion[] accionesFalse ) {
		this();
		condicion = new CondicionHeredada(
				new OperandoGramatica(emisor1,atrEmisor1),
				new OperandoDirecto (valor2),operacion
				);
		this.accionesTrue=accionesTrue;
		this.accionesFalse=accionesFalse;
	}
	//Comparacion entre atributo y valor con acciones unicas
		public AccionCondicionada( int emisor1,String atrEmisor1,
				String operacion,Object valor2,Accion accionesTrue,Accion accionesFalse ) {
			this();
			condicion = new CondicionHeredada(
					new OperandoGramatica(emisor1,atrEmisor1),
					new OperandoDirecto (valor2),operacion
					);
			if (accionesTrue!=null){
				this.accionesTrue=new Accion[1];
				this.accionesTrue[0]=accionesTrue;
			}
			if (accionesFalse!=null){
				this.accionesFalse=new Accion[1];
				this.accionesFalse[0]=accionesFalse;
			}
		}
		
		//Solo con lista de accionesTrue
	public AccionCondicionada( int emisor1,String atrEmisor1,
			String operacion,Object valor2,Accion[] accionesTrue){
		this(emisor1, atrEmisor1, operacion, valor2, accionesTrue, null);
	}
	
	//Solo con accion unica True
	public AccionCondicionada( int emisor1,String atrEmisor1,
			String operacion,Object valor2,Accion accionesTrue){
		this(emisor1, atrEmisor1, operacion, valor2, accionesTrue, null);
	}
	
	public AccionCondicionada() {
		super();
		accionesTrue=null;
		accionesFalse=null;
		
		
	}
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,HashMap<String, Object> atribActual,TablaSimbolos ts) {
		//Object oper1 = valor1==null ? (listaAtrib.get(emisor1).
		Object oper1=null;
		Object oper2=null;
		
		
	
		Accion[] accionesEjecutar;

		if (condicion.getValor(listaAtrib, atribActual, ts)) accionesEjecutar=accionesTrue;
		else accionesEjecutar=accionesFalse;
		
		ArrayList<ErrorCompilador> listaErrores = new ArrayList<ErrorCompilador>(); 
		
		if(accionesEjecutar!=null){
			for(int i=0;i<accionesEjecutar.length;i++){
				listaErrores.addAll(accionesEjecutar[i].ejecutar(listaAtrib, atribActual,ts));
			}
		}
		return listaErrores;

	}
	
	


}
