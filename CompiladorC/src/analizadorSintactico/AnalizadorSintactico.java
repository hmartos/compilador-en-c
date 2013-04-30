package analizadorSintactico;

import java.util.ArrayList;
import java.util.HashMap;

import gestorErrores.ErrorSintactico;
import gestorErrores.GestorDeErrores;
import tablaSimbolos.PalRes;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import token.TokenLambda;
import analizadorLexico.AnalizadorLexico;
import analizadorSemantico.AnalizadorSemantico;

public class AnalizadorSintactico {
	
	AnalizadorLexico lexico;
	AnalizadorSemantico semantico;
	GestorDeErrores errores;
	TablaSimbolos simbolos;
	Token tokenActual;
	Token tokenLambda;
	Primeros primeros;
	
	public AnalizadorSintactico(AnalizadorLexico al,GestorDeErrores ge,TablaSimbolos ts,AnalizadorSemantico s){
		
		tokenLambda=new TokenLambda();
		primeros=new Primeros();
		primeros.calculoPrimeros();
		lexico=al;
		errores=ge;
		simbolos=ts;
		semantico=s;
	}
	
	
	public boolean analizar(){
				
		tokenActual=lexico.Scan();
		return analizarRec(NT.PROGRAMA,0)==null? false:true;
	}
	
	
	//Retorna los atributos de este nodo (NT). null si hay un error.
	HashMap<String,Object> analizarRec(NT nT,int nRegla){
		
		Object[] regla=Gramatica.reglasGramatica[nT.ordinal()][nRegla];
		int nTerm=0;
		boolean valido=true;
		/*El algoritmo es: sobre la regla que sabemos que hay que aplicar, vamos mirando terminos uno a uno:
		 * -Si el termino actual es un T:
		 * 		Si NO concuerda con tActual -> fallo
		 * 		Si concuerda con tActual -> pedimos otro token y cojemos el siguiente termino.
		 * -Si el termino actual es un NT: Determinamos que regla se aplicaría 
		 * 		Si una regla se puede aplicar, -> analizarRec de ese NT y el nRegla
		 * 		Si no se puede aplicar ninguna pero en sus primeros esta lambda, continuamos con el siguiente termino.
		 * 		Si no se puede aplicar ninguna y no esta lambda -> error
		 */
		
		//Parte semantico: atributos de este NT
		HashMap<String,Object> atribActual = new HashMap<String,Object>();
		
		/*Parte semantico: la lista con los atributos que recibe de sus hijos (en caso de ser un NT)
		o el token que coincide con el T (en caso de ser T) 
		Para eso ponemos object.*/
		ArrayList<Object> listaAtrib= new ArrayList<Object>();
		
		
		while (nTerm<regla.length){
			Object termAct=regla[nTerm];
			System.out.println("Estamos en "+nT.toString()+ ". Vamos por " +termAct.toString());
			
			
			
			if (termAct instanceof NT){
				int nReglaNT=selectorRegla((NT) termAct);
				if(nReglaNT!=-1){
					HashMap<String,Object> atribHijo=analizarRec((NT) termAct,nReglaNT);
					if (atribHijo==null) valido=false;
					listaAtrib.add(atribHijo); //Semantico
					nTerm++;
				
				}else{//No se puede aplicar ninguna regla
					if(primeros.estaPrimeros((NT) termAct, tokenLambda)){
						
						//Semantico: para poder ejecutar las acciones de una regla que sea lambda, buscamos su posicion exclusivamente.
						Object[][] reglaConLambda=Gramatica.reglasGramatica[((NT)termAct).ordinal()];
						int b=0;
						boolean encontrada=false;
						while (b<reglaConLambda.length && !encontrada){
							if (reglaConLambda[b][0].equals(tokenLambda)) encontrada=true;
							b++;
						}
						b--;
						
						HashMap<String,Object> atribLambda= new HashMap<String, Object>();
						semantico.ejecutar((NT)termAct, b, new ArrayList<Object>(),atribLambda );
						listaAtrib.add(atribLambda); 
						//Semantico: si es lambda añadimos atributos vacios.
						
						nTerm++;
						System.out.println("Tomamos "+termAct.toString()+" como LAMBDA");

					}
					else{
						//Lanzar error.
						errores.add(new ErrorSintactico(tokenActual.getLinea(), tokenActual.getCol(), "",tokenActual,null,nT));
						System.out.println("=====> ERROR!!: No podemos aplicar ningua regla para "+termAct.toString()+" con el token "+ tokenActual.toString());
						//Para el depurado en cuanto hay un error devolvemos false (Solo probamos programas correctos)
						return null;
						/*valido=false;
						tokenActual=lexico.Scan(); // y seguimos con el siguiente token para ver si coincide en ese contexto.
						if (tokenActual.getTipo().equals(Token.TipoToken.FIN)){
							return valido;
						}*/
					}
				}
			}else{  //Es un terminal
				if (termAct instanceof PalRes){//Es un terminal palabra reservada
					if(tokenActual.getAtributo().equals(termAct)){
						//Coincide con la palabra reservada esperada.
						System.out.println("Coincide "+termAct.toString());
						nTerm++;
						listaAtrib.add(tokenActual); //Semantico
						tokenActual=lexico.Scan();
						System.out.println(".  nuevo token: "+tokenActual.toString());
					}
					else{
						//Lanzar error.
						System.out.println("=====> ERROR!!: No coincide esperado "+termAct.toString()+" con el token "+ tokenActual.toString());
						errores.add(new ErrorSintactico(tokenActual.getLinea(), tokenActual.getCol(), "",tokenActual,termAct,nT)); 
						//Para el depurado en cuanto hay un error devolvemos false (Solo probamos programas correctos)
						return null;
						/*valido=false;
						tokenActual=lexico.Scan(); // y seguimos con el siguiente token para ver si coincide en ese contexto.
						if (tokenActual.getTipo().equals(Token.TipoToken.FIN)){
							return valido;
						}*/
					
					}
					
				}else{ //Es cualquier otro terminal (en forma de token)
					
					Token termTokenAct=(Token) termAct;
					if((tokenActual.getTipo().equals(Token.TipoToken.ID)&&termTokenAct.getTipo().equals(Token.TipoToken.ID))
							//Caso especial para el tipo id pues no importa que tenga en el atributo (equals comprueba el atributo).
							||(tokenActual.equals(termTokenAct))){ 
						//El token actual coincide con el terminal de la regla.
						System.out.println("Coincide "+termAct.toString());
						nTerm++;
						listaAtrib.add(tokenActual); //Semantico
						tokenActual=lexico.Scan();
						System.out.println(".  nuevo token: "+tokenActual.toString());
						
					}else{
						//Lanzar error.
						errores.add(new ErrorSintactico(tokenActual.getLinea(), tokenActual.getCol(), "",tokenActual,termAct,nT));
						System.out.println("=====> ERROR!!: No coincide esperado "+termAct.toString()+" con  "+ tokenActual.toString());
						//Para el depurado en cuanto hay un error devolvemos false (Solo probamos programas correctos)
						return null;
						/*valido=false;
						tokenActual=lexico.Scan(); // y seguimos con el siguiente token para ver si coincide en ese contexto.
						if (tokenActual.getTipo().equals(Token.TipoToken.FIN)){
							return valido;
						}*/
					
					}
				}
				
			}
		}
		if (valido){
			semantico.ejecutar(nT,nRegla,listaAtrib,atribActual);
		//AQUI ha acabado la regla y se deben ejecutar las acciones semanticas.
		}
		return valido ? atribActual : null;
	}
	
	
	
	
	
	
	private int selectorRegla(NT nT){
		
		Object[][] gram = Gramatica.reglasGramatica[nT.ordinal()];
		
		int nRegla=0;
		int nTerm=0;
		/*
		 * El algoritmo es: Tomamos una regla, en principio la nº0 
		 * y miramos el termino por el que vayamos (en principio el primero, el 0) 
		 * si es nT y coincide el token actual con sus Primeros la aplicamos (terminamos).
		 * si es nT y NO coincide el token actual pero sus Primeros incluyen a lambda miramos el proximo termino (seguimos).
		 * si es nT y NO coincide el token actual y NO incluye a lambda desechamos la regla y miramos la siguiente (seguimos).
		 * si es un T y coincide con el token actual elegimos la regla(terminamos).
		 * si es un T y NO coincide con el token actual desechamos la regla (seguimos).
		 * 
		 * Si al ir a mirar el proximo termino, no hay mas en esa regla la desechamos (seguimos)
		 * Si al ir a mirar la proxima regla no existen mas, generaremos error y devolvemos -1.
		 */
		
		while (nRegla<gram.length){
			while (nRegla<gram.length && nTerm<gram[nRegla].length){
				Object termAct=gram[nRegla][nTerm];
				if (termAct instanceof NT){ // El elemento de la regla es un NoTerminal
					if (primeros.estaPrimeros((NT) termAct, tokenActual)){
					//El token actual coincide con el Primero del NoTerminal
						return nRegla; //La regla que aplicaremos será esta.
						
					}else if (primeros.estaPrimeros((NT) termAct, tokenLambda)){
					//No coincide con los Primeros pero estos pueden ser Lambda
						nTerm++; //Seguimos mirando los proximos terminos de esta regla.
					}else{
					//No coincide con los Primeros y ademas no puede ser Lambda
						nRegla++;
						nTerm=0;
						//Pasamos a la siguiente regla posible.
					}	
				}else{ /*El elemento de la regla es un Terminal
						No buscaremos en este metodo mas que un Terminal por regla.
						Si coincide elegimos esa regla automaticamente. Si no la desechamos*/
					
					 //Es un terminal
						if (termAct instanceof PalRes){//Es un terminal palabra reservada
							if(tokenActual.getAtributo()!=null && tokenActual.getAtributo().equals(termAct)){
								return nRegla;
							}
							else{
								nRegla++;
								nTerm=0;
							}
							
						}else{ //Es cualquier otro terminal (en forma de token)
							
							Token termTokenAct=(Token) termAct;
							if((tokenActual.getTipo().equals(Token.TipoToken.ID)&&termTokenAct.getTipo().equals(Token.TipoToken.ID))
									//Caso especial para el tipo id pues no importa que tenga en el atributo (equals comprueba el atributo).		 
									||tokenActual.equals(termTokenAct)){  
								//El token actual coincide con el terminal de la regla.
								return nRegla;
								
							}else{
								nRegla++;
								nTerm=0;;
							}
						}
					
				}
			}
			/*Si llegamos al final de la regla (Esto sera cuando todos tengan de primero lambda)
			  entonces desecharemos la regla.*/
			 
			nRegla++;
			nTerm=0;
		}
		/*Si no podemos aplicar ninguna regla generaremos el error*/
		
		return -1;
	}
	
	
	
	
	
	
	
	


	
	
	
	
	
}
