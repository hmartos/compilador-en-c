package analizadorSintactico;

import java.util.ArrayList;

import tablaSimbolos.PalRes;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import token.TokenLambda;
import gestorErrores.GestorDeErrores;
import analizadorLexico.AnalizadorLexico;

public class AnalizadorSintactico {
	
	AnalizadorLexico lexico;
	GestorDeErrores errores;
	TablaSimbolos simbolos;
	Token tokenActual;
	Token tokenLambda;
	Object[][] aa= {{"","asda"}};
	int col;
	int row;
	
	public AnalizadorSintactico(AnalizadorLexico al,GestorDeErrores ge,TablaSimbolos ts){
		lexico=al;
		errores=ge;
		simbolos=ts;
		tokenLambda=new TokenLambda();
	}
	
	
	public boolean analizar(){
				
		
		return true;
	}
	
	
	
	public boolean analizarRec(NT nT,int nRegla){
		
		Object[] regla=gramatica[nT][nRegla];
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
		while (nTerm<regla.length){
			Object termAct=regla[nTerm];
			if (termAct instanceof NT){
				int nReglaNT=selectorRegla((NT) termAct);
				if(nReglaNT!=-1){
					valido=valido&&analizarRec((NT) termAct,nReglaNT);
					nTerm++;
				
				}else{//No se puede aplicar ninguna regla
					if(Primeros.main((NT) termAct, tokenLambda)){
						nTerm++;
					}
					else{
						//Lanzar error.
						//No se ha podido aplicar ninguna regla.
						
						valido=false;
					}
				}
			}else{  //Es un terminal
				if (termAct instanceof PalRes){//Es un terminal palabra reservada
					if(tokenActual.getAtributo().equals(termAct)){
						nTerm++;
						tokenActual=lexico.Scan();
					}
					else{
						//Lanzar error.
						//Se esperaba palabra clave...
						//LE podemos pasar termAct. 
						valido=false;
					}
					
				}else{ //Es cualquier otro terminal (en forma de token)
					
					Token termTokenAct=(Token) termAct;
					if(tokenActual.getTipo().equals( termTokenAct.getTipo()) &&
							tokenActual.getAtributo().equals(termTokenAct.getAtributo())){ 
						//El token actual coincide con el terminal de la regla.
						nTerm++;
						tokenActual=lexico.Scan();
						
					}else{
						//Lanzar error.
						//Se esperaba token tal...
						valido=false;
					}
				}
				
			}
		}
		
		
		return valido;
	}
	
	
	
	
	
	
	private int selectorRegla(NT nT){
		
		Object[][] gram = gramatica[nT];
		
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
			while (nTerm<gram.length){
				Object termAct=gram[nRegla][nTerm];
				if (termAct instanceof NT){ // El elemento de la regla es un NoTerminal
					if (Primeros.main((NT) termAct, tokenActual)){
					//El token actual coincide con el Primero del NoTerminal
						return nRegla; //La regla que aplicaremos será esta.
						
					}else if (Primeros.main((NT) termAct, tokenLambda)){
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
							if(tokenActual.getAtributo().equals(termAct)){
								return nRegla;;
							}
							else{
								nRegla++;
								nTerm=0;
							}
							
						}else{ //Es cualquier otro terminal (en forma de token)
							
							Token termTokenAct=(Token) termAct;
							if(tokenActual.getTipo().equals( termTokenAct.getTipo()) &&
									tokenActual.getAtributo().equals(termTokenAct.getAtributo())){ 
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
	
	
	
	
	
	
	
	
	/*
	private boolean analisis_PROGRAMA(){
		if (Primeros.main(noTerminales.L_DEFINICIONES, tActual)) return analisis_L_DEFINICIONES();
		else if (Primeros.main(noTerminales.L_DEFINICIONES, tokenLambda)){
			return tActual.getTipo().equals(Token.TipoToken.FIN);
		}
		return false;
	}
	
	private boolean analisis_L_DEFINICIONES() {
		
		if (Primeros.main(noTerminales.DEFINICION_GLOBAL, tActual))return analisis_DEFINICION_GLOBAL();
		else if (Primeros.main(noTerminales.DEFINICION_GLOBAL, tokenLambda)){
			if (Primeros.main(noTerminales.L_DEFINICIONES, tActual))return analisis_L_DEFINICIONES();
			
		}
		return false;
	}
	MACROS T | A| B
	private boolean analisis_DEFINICION_GLOBAL() {
		if (Primeros.main(noTerminales.MACROS, tActual)) return analisis_MACROS();
		else if macros is landa {
			if primeros de T return T
		}
		
		return false;
	}
*/

	
	
	
	
	
}
