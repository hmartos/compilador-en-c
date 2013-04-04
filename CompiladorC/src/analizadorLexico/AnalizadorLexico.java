package analizadorLexico;

import gestorErrores.ErrorLexico;
import gestorErrores.GestorDeErrores;

import interfaz.ClasePrincipal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaPalRes;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.PalRes;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import token.Token.TipoToken;
import token.TokenAlmohadilla;
import token.TokenAmpersand;
import token.TokenAndL;
import token.TokenAsig;
import token.TokenAsterisco;
import token.TokenComa;
import token.TokenComillasChar;
import token.TokenCorchetes;
import token.TokenEntrecomillado;
import token.TokenFin;
import token.TokenId;
import token.TokenLlaves;
import token.TokenNumEntero;
import token.TokenNumReal;
import token.TokenOpDespl;
import token.TokenOpMult;
import token.TokenOpSeleccion;
import token.TokenOpTernario;
import token.TokenOpUnario;
import token.TokenOrB;
import token.TokenOrL;
import token.TokenPalRes;
import token.TokenParentesis;
import token.TokenPuntoyComa;
import token.TokenRelComp;
import token.TokenRelIgual;
import token.TokenSimboloAdicion;
import token.TokenXorB;

public class AnalizadorLexico {

	/**
	 * Constructor con la ruta al fichero que contiene el código a analizar.
	 * Debe contener además el gestor de errores y la tabla de simbolos para 
	 * que el analizadorLexico pueda usarlas.
	 * @param ruta
	 */
	
	
	//ATRIBUTOS
	
	TablaSimbolos TS;
	GestorDeErrores GE;
	String leido;
	int nLinea;
	int nChar;	
	
	
	//File archivo = new File ("C:\\archivo.txt");
	
	BufferedReader br;
		
	public AnalizadorLexico(boolean modoString, String cadenaEntrada, TablaSimbolos tabla ,  GestorDeErrores errores){
		
		//Guardar en atributos TS y GE.
			TS=tabla;
			GE=errores;
			nLinea=1;
			nChar=0;
			try 
			 {
		         // Apertura del fichero y creacion de BufferedReader para poder 
		         // hacer una lectura comoda (disponer del metodo readLine()).
				File archivo = new File (cadenaEntrada);
				Reader r;
				if (modoString)r = new StringReader(cadenaEntrada);
				else r = new FileReader (archivo);;
		         br = new BufferedReader(r); 
		      }
		      catch(Exception e)
		      {
		         e.printStackTrace();
		      }
		//}

		
	}
	
	public void devolverChar() 
	{
		nChar--;
		try {
			br.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int leerChar()  //Pasamos el int para poder marcar final : -1
	{
		nChar++;
		try {
			br.mark(1);
			return br.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return -1; //Si hay una excepcion devuelve final de fichero.
		
	}
	
	
	/**
	 * Funcion principal que usara el analizador sintáctico para obtener un token nuevo
	 * @return
	 * @throws IOException 
	 */
	public Token Scan() { 
		
		
		
		
		//Inicializacion
		Estado eAct=Estado.e0;
		
		int charAct=leerChar(); /*Pasaremos int en vez de char para poder marcar con -1 el final de fichero.
								Si es necesario haremos casting (char)*/
		
		
		
		String bufferString="";
		Token tokenGen=null;
		
		boolean hasLeidoBarra=false;
		char auxChar ='ç';
		
		//Ante un error las resoluciones pueden ser:
		//tokenGen=Scan();  ==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
		//charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
		
		
		while (tokenGen==null || (tokenGen.getTipo().equals(TipoToken.PAL_RES) && (tokenGen.getAtributo().equals(PalRes.PAL_MAC_pragma) || tokenGen.getAtributo().equals(PalRes.PAL_MAC_error)))){
			//GE.generarError(eAct,charAct,nChar,nLinea);
			switch (eAct){	// miramos en que estado estamos y entraremos en la rama del switch correspondiente
			case e0:{
				switch (charAct){								// Según el caracter leido lo analizamos 
				
					case (' '): {								// Leemos desde el estado 0, el espacio
						charAct=leerChar();						//Accion semantica A
						break;
					}
					
					case ('\t'):{								// Leemos desde el estado 0, TAB
						charAct=leerChar();						//Accion semantica A
						break;
					}
					
					case '\n':{									// Leemos desde el estado 0, EOL
						nChar=0;
						nLinea++;
						charAct=leerChar();						// Accion semantica A
						break;
					}

					case ('\r'):  {								// Leemos desde el estado 0, el retorno de carro
						charAct=leerChar();						//Accion semantica A
						break;
					}

					case ('_'):{								// Leemos desde el estado 0, TAB
						eAct=Estado.e2;
						bufferString+=(char)charAct;			//Accion semantica B
						charAct=leerChar(); //Accion semantica A
						break;
					}
					
					case ('#'):{								// Leemos desde el estado 0, #
						nChar = 0;
						//charAct=leerChar(); 					//Accion semantica A
						tokenGen = new TokenAlmohadilla(nLinea,nChar);
						break;
					}
					
					case '"': {									// Leemos desde el estado 0, "
							eAct=Estado.e10;
							charAct=leerChar();    				//Accion semantica A
							break;
					}
					

					case ('('):	{								// Leemos desde el estado 0, (
						eAct=Estado.e13;
						tokenGen = new TokenParentesis(TokenParentesis.TipoTokenParentesis.ABIERTO,nLinea,nChar);  //Accion Semantica O, GenerarToken (
						break;				
					}
					
					case (')'):									// Leemos desde el estado 0, )
					{
						eAct=Estado.e14;
						tokenGen = new TokenParentesis(TokenParentesis.TipoTokenParentesis.CERRADO,nLinea,nChar);  //Accion Semantica O, GenerarToken )
						break;
					}
					case ('{'):									// Leemos desde el estado 0, {
					{
						eAct=Estado.e15;
						tokenGen = new TokenLlaves(TokenLlaves.TipoTokenLlaves.ABIERTO,nLinea,nChar);  //Accion Semantica S, GenerarToken {
						break;
					}
					case ('}'):									// Leemos desde el estado 0, }
					{
						eAct=Estado.e16;
						tokenGen = new TokenLlaves(TokenLlaves.TipoTokenLlaves.CERRADO,nLinea,nChar);  //Accion Semantica S, GenerarToken }
						break;
					}
					case ('['):									// Leemos desde el estado 0, [
					{
						eAct=Estado.e17;
						tokenGen = new TokenCorchetes(TokenCorchetes.TipoTokenCorchetes.ABIERTO,nLinea,nChar);  //Accion Semantica R, GenerarToken [
						break;
					}
					case (']'):									// Leemos desde el estado 0, ]
					{
						eAct=Estado.e18;
						tokenGen = new TokenCorchetes(TokenCorchetes.TipoTokenCorchetes.CERRADO,nLinea,nChar);  //Accion Semantica R, GenerarToken ]
						break;
					}	
					case (','):									// Leemos desde el estado 0, ,
					{
						eAct=Estado.e19;
						tokenGen = new TokenComa(nLinea,nChar);  			//Accion Semantica Q, GenerarToken ,
						break;
					}
					case (';'):									// Leemos desde el estado 0, ;				
					{
						eAct=Estado.e20;
						tokenGen = new TokenPuntoyComa(nLinea,nChar); 		//Accion Semantica J, GenerarToken ;
						break;
					}
					case (':'):									// Leemos desde el estado 0, :
					{
						eAct=Estado.e21;
						tokenGen = new TokenOpTernario(TokenOpTernario.TipoTokenOpTernario.DOSPUNTOS,nLinea,nChar);  //Accion Semantica V, GenerarToken :
						break;
					}
					case ('?'):									// Leemos desde el estado 0, ?
					{
						eAct=Estado.e22;
						tokenGen = new TokenOpTernario(TokenOpTernario.TipoTokenOpTernario.INTERROGACION,nLinea,nChar);  //Accion Semantica V, GenerarToken ?
						break;
					}
					case ('|'):									// Leemos desde el estado 0, |
					{
						eAct=Estado.e23;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('%'):{								// Leemos desde el estado 0, %
						eAct=Estado.e26;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('/'):{								// Leemos desde el estado 0, /
						eAct=Estado.e27;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('*'):{								// Leemos desde el estado 0, *
						eAct=Estado.e33;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break; 
					}
					case ('&'):{								// Leemos desde el estado 0, &
						eAct=Estado.e35;
						charAct=leerChar();  					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('~'):{								// Leemos desde el estado 0, ~
						eAct=Estado.e39;
						break;
					}
					case ('!'):{								// Leemos desde el estado 0, !
						eAct=Estado.e38;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('='):{								// Leemos desde el estado 0, =
						eAct=Estado.e40;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('+'):{								// Leemos desde el estado 0, +
						eAct=Estado.e42;
						charAct=leerChar();			  			// Accion Semantica A, leemos siguiente caracter 
						break;	
					}
					case ('-'):{								// Leemos desde el estado 0, -
						eAct=Estado.e45;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter  
						break;
					}
					case ('.'):{ 								// Leemos desde el estado 0, .
						tokenGen = new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.PUNTO,nLinea,nChar);  // Acción semántica DZ, Generar Token .
						break;
					}
					case ('^'):{								// Leemos desde el estado 0, ^
						eAct=Estado.e47;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('<'):{								// Leemos desde el estado 0, <
						eAct=Estado.e49;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('>'):{								// Leemos desde el estado 0, >
						eAct=Estado.e51;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter
						break;
					}
					case ('\''):{  								// Leemos desde el estado 0, '  La comilla simple se representa como \' en C
						eAct=Estado.e55;
						charAct=leerChar();   					// Accion Semantica A, leemos siguiente caracter 
						break;
					}
					default:{									// Caso en el que no ha encontrado ningún símbolo reconocido
						if (esLetra(charAct)){					// Leemos desde el estado 0, una letra
							eAct=Estado.e1;
							bufferString+=(char)charAct; 		//Accion semantica B, concatena caracter al final de la cadena que está leyendo
							charAct=leerChar(); 		 		//Accion semantica A, leemos siguiente caracter
						}
						else if (Character.isDigit(charAct)){	// Leemos desde el estado 0, un dígito
							eAct=Estado.e6;
							bufferString+=(char)charAct; 		//Accion semantica B, concatena caracter al final de la cadena que está leyendo
							charAct=leerChar();					//Accion semantica A, leemos siguiente caracter
						}
						else if (charAct==-1){					// Leemos desde el estado 0, EOF
							//estado por asignar.  
							tokenGen=new TokenFin(nLinea,nChar);			// Acción semántica K, hemos terminado de leer  el fichero
						
						}
						else if (charAct=='\\'){					
								String descr="No se esperaba \"\\\".";
								ErrorLexico e = new ErrorLexico(nLinea,nChar,descr,ErrorLexico.TipoErrorLexico.TIPO3,(char) charAct,bufferString); //Creamos un nuevo error de TIPO3
								GE.add(e);                                               //Añadimos a la cola de errores
								
								//Resolución del error:
								charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
								
						}
						else{
							ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
							GE.add(e);                          							//Añadimos a la cola de errores
							//Resolución del error:
							charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
							
						}
						
						break;
					}
				
				
				
				}
			break;
			}
			
			case e1:{							                                       // En el estado 1, ya he leido letra, analizo el siguiente charAct
				if (esLetra(charAct)||Character.isDigit(charAct)||charAct=='_'){
					bufferString+=(char)charAct; 							          // Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 									          // Accion semantica A, leemos siguiente caracter
					                                                                 //Permanece en el estado 1.
				} else if (esCaracterPermitido(charAct) && (charAct!='_')){			// lee un delimitador
					EntradaTabla entrada= TS.busquedaPalabraReservada(bufferString);  			// Busco el ID en la tabla
					if (entrada!=null){												  			// Si no esta creo una entrada nueva
						tokenGen=new TokenPalRes((AtributosTablaPalRes) entrada.getAtt(),nLinea,nChar);
						if(tokenGen.getAtributo().equals(PalRes.PAL_MAC_pragma) || tokenGen.getAtributo().equals(PalRes.PAL_MAC_error)){   //Si leemos pragma o error, ignoramos hasta el salto de linea, vamos al estado 30, como si hubieramos le’do //
							eAct = Estado.e30;
							charAct = leerChar();
							break;
						}
					}else{															  			// si no meto el ID en su atributo
						tokenGen=new TokenId(bufferString,nLinea,nChar);
						TS.insertar(bufferString); 									  			// Lo introducimos en la TS para verlo en la demo.
					}
					devolverChar();											        // Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                          							//Añadimos a la cola de errores
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			
			case e2:{							                             // En el estado 2, ya he leido _ , analizo el siguiente charAct				
				if (esLetra(charAct)||Character.isDigit(charAct)){
					bufferString+=(char)charAct; 							// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 									// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e1;
				}else if (charAct=='_'){							
					bufferString+=(char)charAct; 							// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 									// Accion semantica A, leemos siguiente caracter
					                                                        //Permanece en el estado 2.
				} else if (esCaracterPermitido(charAct)){ 					//Todos los permitidos menos "_", que se especifica arriba.
					String descr="Se esperaba un identificador tras \"_\".";
					ErrorLexico e = new ErrorLexico(nLinea,nChar,descr,ErrorLexico.TipoErrorLexico.TIPO3,(char) charAct,bufferString); //Creamos un nuevo error de TIPO3
					GE.add(e);                                               //Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				} else { //Caracteres no permitidos
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e); //Añadimos a la cola de errores
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			

			case e3:{							                             // En el estado 3, ya he leido # , analizo el siguiente charAct		
				if (esLetra(charAct)){
					bufferString+=(char)charAct; 							// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar();										// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e4;
				} else if (esCaracterPermitido(charAct) || Character.isDigit(charAct) ){ //No es letra pero si permitido
					String descr="Las macros solo pueden contener letras.";
					ErrorLexico e = new ErrorLexico(nLinea,nChar,descr+" (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO3,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                             //Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				} else {                                                  //Caracteres no permitidos
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                           //Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e4:{							                             // En el estado 4, ya he leido #letra , analizo el siguiente charAct	
				if (esLetra(charAct)){
					bufferString+=(char)charAct; 							 // Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 									// Accion semantica A, leemos siguiente caracter
					                                                        //Permanece en el estado e4.
				}  else if (charAct=='_' || Character.isDigit(charAct)){
					String descr="Las macros solo pueden contener letras.";
					ErrorLexico e = new ErrorLexico(nLinea,nChar,descr+" (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO3,(char) charAct,bufferString); //Creamos un nuevo error de TIPO3
					GE.add(e);                                              //Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				} else if (esCaracterPermitido(charAct)){ 					//Cualquier caracter menos "_"
					EntradaTabla entrada= TS.busquedaPalabraReservada(bufferString);  			// Busco el ID en la tabla
					if (entrada!=null){												  			// Si no esta creo una entrada nueva
						tokenGen=new TokenPalRes((AtributosTablaPalRes) entrada.getAtt(),nLinea,nChar);
						devolverChar();
					}else {
						String descr="Macro no definida.";
						ErrorLexico e = new ErrorLexico(nLinea,nChar,descr+" (Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO3,'#',bufferString); //Creamos un nuevo error de TIPO3
						GE.add(e);                                              //Añadimos a la cola de errores
						
						//Resolución del error:
						devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
						tokenGen=Scan();  //==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					}
					 										// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				
				}else {                                                  //Caracteres no permitidos
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                           //Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				
				break;
			}
				
			
			
			case e6:{															// En el estado 6, ya he leido digito, analizo el siguiente charAct			
				if (Character.isDigit(charAct)){
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 										// Accion semantica A, leemos siguiente caracter
					//Permanece en el estado 6.
				} 
				else if (charAct=='.'){
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 										// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e7;
				}
				else if (esCaracterPermitido(charAct) || esLetra(charAct)){  	// Descartado que sea el punto porque hubiera entrado en el caso anterior
					boolean ret;
					ret = analizarCadena(bufferString);
					if(ret){
						tokenGen=new TokenNumEntero(Integer.valueOf(bufferString),nLinea,nChar); // Acción semántica D, convertir la cadena a número y generar el token entero
						devolverChar();// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
						//No ponemos que va al estado 9 porque los estado finales no los implementamos
					}else{
						ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO2,(char) charAct,bufferString); //Creamos un nuevo error de TIPO2
						GE.add(e);                                              	//Añadimos a la cola de errores
						
						//Resolución del error:
						charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
						
					}
				}  else {
					
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				
				break;
			}
		
			case e7:{															// En el estado 7, ya he leido la parte entera del decimal, analizo el siguiente charAct	
				if (Character.isDigit(charAct)){
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 										// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e8;
				} else if(esCaracterPermitido(charAct) || esLetra(charAct)) {
					String desc="Se esperaba parte decimal despues de \".\"";
					ErrorLexico e = new ErrorLexico(nLinea,nChar,desc+" (Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO2,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
										
					//Resolución del error:
					devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
					tokenGen=Scan();  //==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e8:{															// En el estado 8, estoy leyendo la parte decimal del numero, analizo el siguiente charAct	
				if (Character.isDigit(charAct)){
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar(); 										// Accion semantica A, leemos siguiente caracter
				} else if (esCaracterPermitido(charAct)||esLetra(charAct)){
					tokenGen=new TokenNumReal(Float.valueOf(bufferString),nLinea,nChar);		// Acción semántica E, convierte la cadena en numero y general el token numero real
					devolverChar();												// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
						
			case e10:{															// En el estado 10, ya hemos leido ", analizo el siguiente charAct
				if (charAct=='"'){
					tokenGen=new TokenEntrecomillado(bufferString,nLinea,nChar);				// Acción semántica M, leemos el entrecomillado 
				} else if(charAct=='\\'){
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar();											// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e11;	
				} else if(charAct==-1 || charAct=='\n' || charAct=='\r') {
					
					ErrorLexico e = new ErrorLexico(nLinea,nChar,"Se esperaba \" (Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO4,(char) charAct,bufferString); //Creamos un nuevo error de TIPO4
					GE.add(e);                                              	//Añadimos a la cola de errores
					//Resolución del error:
					devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
					tokenGen=Scan();  //==> Desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					
				} else { 														// Resto de caracteres 
					bufferString+=(char)charAct; 								// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar();											// Accion semantica A, leemos siguiente caracter
					eAct=Estado.e10;
				}
				break;	
			}
			
			case e11:{															// En el estado 11, seguimos leyendo comentario
				bufferString+=(char)charAct; 									// Accion semantica B, concatena caracter al final de la cadena que está leyendo
				charAct=leerChar();												// Accion semantica A, leemos siguiente caracter
				
				// ACCIÓN SEMÁNTICA FZ
				
				eAct=Estado.e10;
				break;
			}
			
			case e23:{     														// En el estado 23, llevamos leido |, analizamos charAct
				if(charAct == '=') {
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.ORB_ASIG,nLinea,nChar); // Acción semántica H, generamos token asignación |=
				} else if(charAct == '|') {										
					tokenGen = new TokenOrL(nLinea,nChar);									// Acción semántica W, genera token ||
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					devolverChar(); 											// Accion Semántica EZ(Decr. caracter leido)
					tokenGen = new TokenOrB(nLinea,nChar);		 							// Acción semántica G, genera token ||
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e26:{ 		       													// En el estado 26, llevamos leido %
				if(charAct == '='){				
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.MOD_ASIG,nLinea,nChar);		// Accion Semántica H, genera token %=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {						
					tokenGen = new TokenOpMult(TokenOpMult.TipoTokenOpMult.MOD,nLinea,nChar);	// Accion Semántica BZ, genera token %
					devolverChar(); 												// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e27:{																// En el estado 27, llevamos leido /
				if(charAct == '='){			
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.DIV_ASIG,nLinea,nChar); 	// Accion Semántica H, genera token /=
				} else if(charAct == '/'){											// Llevamos leido //
					eAct=Estado.e30;
					charAct = leerChar();											// Acción semántica A, leemos siguiente caracter
				} else if(charAct == '*'){											// Llevamos leido /*
					eAct=Estado.e29;
					charAct = leerChar();											// Acción semántica A, leemos siguiente caracter
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					tokenGen = new TokenOpMult(TokenOpMult.TipoTokenOpMult.DIV,nLinea,nChar);	// Acción semántica BZ , genera token /
					devolverChar(); 												// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e29:{																// En el estado 29, llevamos leido /* y puede que parte del comentario
				if(charAct == '*'){	
					charAct = leerChar();											// Acción semántica A, leemos siguiente caracter
					eAct=Estado.e31;
				} else if(charAct != -1){
					charAct = leerChar();											// Acción semántica A, leemos siguiente caracter
				}else{
					eAct=Estado.e0;
				}
				break;
			}
			
			case e30:{											// En el estado 30, estamos leyendo el comentario con // o con pragma o error
				if(charAct == '\n' || charAct == -1) { 							
					nChar=0;														// reseteamos los caracteres leidos
					nLinea++; // incrementamos el número de líneas leídos
					if(tokenGen == null){
							eAct = Estado.e0; // Hemos terminado de leer el comentario de // cuando reconocemos EOL p EOF
					}else{
						return tokenGen;
					}
				} else {
					eAct=Estado.e30;
				}
				charAct = leerChar();												// Acción semántica A, leemos siguiente caracter
				break;
			}
			
			case e31:{																// En el estado 31, estamos leyendo el final del comentario (varios *) y /
				if(charAct == '/' || charAct == -1){
					eAct=Estado.e0;													// Hemos terminado de leer el comentario
				} else if(charAct == '*') {
					eAct=Estado.e31;	
				} else {
					eAct=Estado.e29;
				}
				charAct = leerChar();												// Acción semántica A, leemos siguiente caracter
				break;
			}

			case e33:{																// En el estado 33, llevamos leido *
				if(charAct == '='){
					tokenGen=new TokenAsig(TokenAsig.TipoTokenAsig.MULT_ASIG,nLinea,nChar);		// Acción semántica H, generamos token *=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					tokenGen = new TokenAsterisco(nLinea,nChar);								// Acción semántica F, genera token *
					devolverChar(); 												// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e35:{																// En el estado 35, llevamos leido &
				if(charAct == '='){
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.ANDB_ASIG,nLinea,nChar);	// Acción semántica H, generamos token &=
				} else if(charAct == '&') {
					tokenGen = new TokenAndL(nLinea,nChar);										// Acción semántica X, generamos token &&	
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					tokenGen = new TokenAmpersand(nLinea,nChar);								// Acción semántica U, generamos token &
					devolverChar();													// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}

			case e38:{																									// En el estado 38, llevamos leido !
				if(charAct == '='){	
					tokenGen = new TokenRelIgual(TokenRelIgual.TipoTokenRelIgual.DISTINTO,nLinea,nChar);								// Acción semántica Y, generamos token !=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)){				// token !
					tokenGen = new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.NOT_L,nLinea,nChar);								// Acción semántica CZ, generamos token !=
					devolverChar();																						// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e40:{																							// En el estado 40, llevamos leido =
				if(charAct == '='){																				// token ==
					tokenGen = new TokenRelIgual(TokenRelIgual.TipoTokenRelIgual.IGUAL,nLinea,nChar);						// Acción semántica Y, generamos token ==
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {				// token =
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.ASIG,nLinea,nChar);		// Acción semántica H, generamos token =
					devolverChar();												// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e42:{						 	// En el estado 42, llevamos leido +
				if(charAct == '='){	    	 
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.SUMA_ASIG,nLinea,nChar);			             // Acción semántica H, generamos token +=
				} else if(charAct == '+'){	 
					tokenGen = new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.INCREMENTO,nLinea,nChar);			 // Acción semántica CZ, generamos token ++
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)){						 
					tokenGen = new TokenSimboloAdicion(TokenSimboloAdicion.TipoTokenSimboloAdicion.SUMA,nLinea,nChar);// Acción semántica I, generamos token +
					devolverChar(); 											// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e45:{						// En el estado 45, llevamos leido -
				if(charAct == '='){			
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.RESTA_ASIG,nLinea,nChar);						  // Acción semántica H, generamos token -=
				} else if(charAct == '-') {     
					tokenGen = new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.DECREMENTO,nLinea,nChar);			  // Acción semántica CZ, generamos token --
				} else if(charAct == '>') {	
					tokenGen = new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.FLECHA,nLinea,nChar);		  // Acción semántica DZ, generamos token ->
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					tokenGen = new TokenSimboloAdicion(TokenSimboloAdicion.TipoTokenSimboloAdicion.RESTA,nLinea,nChar);// Acción semántica I, generamos token -
					devolverChar();																		  // Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				}  else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e47:{						// En el estado 47, llevamos leido ^
				if(charAct == '='){			
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.XORB_ASIG,nLinea,nChar);	// Acción semántica H, generamos token ^=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {					
					tokenGen = new TokenXorB(nLinea,nChar); 									// Acción semántica T, generamos token ^
					devolverChar();													// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e49:{						// En el estado 49, llevamos leido <
				if(charAct == '=') {		
					tokenGen = new TokenRelComp(TokenRelComp.TipoTokenRelComp.IGUAL_MENOR,nLinea,nChar); // Acción semántica Z, generamos token <=
				} else if(charAct == '<') {
					charAct=leerChar(); 													// Acción Semántica A, leemos otro caracter 
					eAct=Estado.e53;
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					devolverChar(); 														// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
					tokenGen = new TokenRelComp(TokenRelComp.TipoTokenRelComp.MENOR,nLinea,nChar);		// Accion semantica Z, generamos token <
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e51:{						// En el estado 51, llevamos leido >
				if(charAct == '=') {		
					tokenGen = new TokenRelComp(TokenRelComp.TipoTokenRelComp.IGUAL_MAYOR,nLinea,nChar);	// Acción semántica Z, generamos token >=
				} else if(charAct == '>') {
					charAct=leerChar();														// Acción Semántica A, leemos otro caracter
					eAct=Estado.e52;
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {
					devolverChar(); 														// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
					tokenGen = new TokenRelComp(TokenRelComp.TipoTokenRelComp.MAYOR,nLinea,nChar);		// Accion semantica Z, generamos token >
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}

			case e52:{						// En el estado 52, llevamos leido >>
				if(charAct == '=') { 
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.DESP_DER_ASIG,nLinea,nChar);		// Acción semántica H, generamos token >>=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {				
					devolverChar();															// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
					tokenGen = new TokenOpDespl(TokenOpDespl.TipoTokenOpDespl.DESPL_DER,nLinea,nChar);	// Acción semántica AZ, generamos token >>
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e53:{						// En el estado 51, llevamos leido <<
				if(charAct == '=') {	
					tokenGen = new TokenAsig(TokenAsig.TipoTokenAsig.DESP_IZQ_ASIG,nLinea,nChar); 		// Acción semántica H, generamos token <<=
				} else if(esCaracterPermitido(charAct) || esLetra(charAct) || Character.isDigit(charAct)) {				
					devolverChar(); 														// Accion semantica EZ, retrocede una posición el puntero de lectura del fichero fuente
					tokenGen = new TokenOpDespl(TokenOpDespl.TipoTokenOpDespl.DESPL_IZQ,nLinea,nChar);	// Acción semántica AZ, generamos token <<
				} else {
					ErrorLexico e = new ErrorLexico(nLinea,nChar," (Prosigue lectura token)",ErrorLexico.TipoErrorLexico.TIPO1,(char) charAct,bufferString); //Creamos un nuevo error de TIPO1
					GE.add(e);                                              	//Añadimos a la cola de errores
					
					//Resolución del error:
					charAct=leerChar(); //==> Para tratar de seguir leyendo el token, con lo que llevabamos e ignorar el error.
					
				}
				break;
			}
			
			case e55:{						// En el estado 55, llevamos leido '
				hasLeidoBarra = false; 
				if(charAct == '\\') {   	// LLevamos '\  en C la barra la coge con \\
					eAct=Estado.e56;
					bufferString+=(char)charAct; 					// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					charAct=leerChar();								// Acción Semántica A, leemos otro caracter

				} 
				else if(charAct=='\n' || charAct=='\r' || charAct==-1) {
					ErrorLexico e = new ErrorLexico(nLinea,nChar,"Se esperaba \' (Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO5,(char) charAct); //Creamos un nuevo error de TIPO5
					GE.add(e);                                              											//Añadimos a la cola de errores
					//Resolución del error:
					devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
					tokenGen=Scan();  // ==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					
				}
				else{
					eAct=Estado.e58;
				//	charAct=leerChar();								// Acción Semántica A, leemos otro caracter
					bufferString+=(char)charAct; 					// Accion semantica B, concatena caracter al final de la cadena que está leyendo
					auxChar = (char)charAct;
					
				}
				break;
			}
			
			case e56:{														// En el estado 56, llevamos leido '\
				if(charAct=='\n' || charAct=='\r' || charAct==-1) {
					ErrorLexico e = new ErrorLexico(nLinea,nChar,"Se esperaba \' (Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO5,(char) charAct); //Creamos un nuevo error de TIPO5
					GE.add(e);                                              											//Añadimos a la cola de errores
					//Resolución del error:
					devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
					tokenGen=Scan(); // ==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					
				}
				else{
					hasLeidoBarra = true; 
					eAct=Estado.e58;
				}
				
				break;
			}

			case e58:{						// En el estado 58, llevamos leido ' con cualquier otra cosa
				
				if(charAct=='\n' || charAct=='\r' || charAct==-1) {
					ErrorLexico e = new ErrorLexico(nLinea,nChar, "Se esperaba \'(Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO5,(char) charAct); //Creamos un nuevo error de TIPO5
					GE.add(e);
					//Resolución del error:
					devolverChar(); //Devolvemos el char que ha provocado el error porque puede ser parte de un nuevo token.
					tokenGen=Scan(); // ==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
					
				}
				else if(hasLeidoBarra){
					    char c =(char)charAct;	
						charAct=leerChar();							// Acción Semántica A. leemos otro caracter
						if(charAct == '\''){						
							if(c == 'b'){
								tokenGen = new TokenComillasChar('\b',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == 't'){
								tokenGen = new TokenComillasChar('\t',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == 'n'){
								tokenGen = new TokenComillasChar('\n',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == 'f'){
								tokenGen = new TokenComillasChar('\f',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == 'r'){
								tokenGen = new TokenComillasChar('\r',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == '"'){
								tokenGen = new TokenComillasChar('\"',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == '\''){
								tokenGen = new TokenComillasChar('\'',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}else if(c == '\\'){
								tokenGen = new TokenComillasChar('\\',nLinea,nChar);			// Acción semántica HZ, generar token carácter
							}
							else{
								ErrorLexico e = new ErrorLexico(nLinea,nChar, "No se permite este carácter especial(Reset lectura token)",ErrorLexico.TipoErrorLexico.TIPO5,(char) charAct); //Creamos un nuevo error de TIPO5
								GE.add(e);                                              											//Añadimos a la cola de errores
								//Resolución del error:
								//No devolvemos el caracter leido porque es el final del char '.
								tokenGen=Scan();  // ==> Para desechar el token que llevabamos leido y empezar a construir uno nuevo con el siguiente caracter
								
							}
						}
						else{
							bufferString+=(char)charAct; //seria buffer string c
							//charAct=leerChar();									// Acción Semántica A. leemos otro caracter
						}
				}
				else{
					char v = (char) charAct;
					bufferString+=(char)charAct; 
					charAct=leerChar();											// Acción Semántica A. leemos otro caracter
					if(charAct == '\''){						
						tokenGen = new TokenComillasChar(v,nLinea,nChar);					// Acción semántica HZ, generar token carácter
					}
				}

				break;
			}
			
			}
		}
		
		return tokenGen;	
	}

	private boolean analizarCadena(String cadena){
		boolean ret=true;
		int i=0;
		for(i=0;i<cadena.length();i++){
			if(esLetra(cadena.charAt(i))){
				ret= false;
			}
		}
		
		return ret;
	}



	private boolean esCaracterPermitido(int charAct) {  // Antes se llamaba esDelim
		
		return 	charAct=='_'|| charAct=='#' || charAct=='\'' || 
				charAct=='"' || charAct=='(' || charAct==')' || 
				charAct=='{' || charAct=='}' || charAct=='[' || 
				charAct==']' || charAct==',' || charAct==';' || 
				charAct==':'|| charAct=='?' || charAct=='|' || 
				charAct=='%' || charAct=='/' || charAct=='*' || 
				charAct=='&' || charAct=='!' || charAct=='~' || 
				charAct=='+' || charAct=='-' || charAct=='.' || 
				charAct=='^' || charAct=='<' || charAct=='>' || 
				charAct=='=' || charAct=='\\' || charAct=='\n' ||  //\n es igual a EOL
				charAct==' '|| charAct=='\r'|| charAct=='\t' ||
				charAct==-1; //-1 es igual a EOF
	}





	private boolean esLetra(int charAct) { //Letras sin nada (LOWERCASE Y UPERCASE)
		
		return (charAct>=65 && charAct<=90) ||(charAct>=97 && charAct<=122);
	}
	
	
	
}
