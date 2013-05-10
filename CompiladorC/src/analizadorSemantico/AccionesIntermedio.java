package analizadorSemantico;

import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InsGoto;
import codigoIntermadio.InsIfGoto;
import codigoIntermadio.InstruccionIntermedio;
import acciones.*;
import accionesEspecificas.AccionR15_1;
import accionesEspecificas.AccionR3_2;
import accionesEspecificas.CodigoR105_4;
import accionesEspecificas.CodigoR15_1;

public class AccionesIntermedio {
	
	

	static Accion[][][] acciones={
		
/* - - Definicion de Programa - -*/

 								
/*1. PROGRAMA  ->*/{					
	/*1.1.  L_DEFINICIONES EOF */			{new AccionRecogerCodigo()}, 	
	/*1.2. EOF*/			{}				
 								},
/*2. L_DEFINICIONES ->  */{			
	/*2.1. DEFINICION_GLOBAL  L_DEFINICIONES*/			{},
	/*2.2 ? */	{}
 								},
/*3. DEFINICION_GLOBAL -> */{		
	/*3.1. MACROS */			{},				
	/*3.2. TIPO RDEFINICION*/			{},		
	/*3.3.  DEFINICION_STRUCT */			{},	
	/*3.4. DEFINICION_ENUM */			{},		
	/*3.5. DEFINICION_UNION */			{},		
	/*3.6.  DEFINICION_TYPEDEF*/			{}
									
 								},
/*4. RDEFINICION -> */{
	/*4.1. ;  */			{},
	/*4.2. iden RDEFINICION2*/			{new AccionCondicionada(1,"esFuncion","igual",true,new AccionCondicionada(1,"esPrototipo","igual",false, new AccionAsignarEtiqueta(new OperandoGramatica(1,"codigo"),new OperandoGramatica (0,""),0)))}
 								},
/*5. RDEFINICION2 -> */{
	/*5.1. CORCHETES RDEF_VARIABLE */			{},
	/*5.2. (L_PARAMS) RDEF_FUNCION*/			{}
 								},
/*6. RDEF_VARIABLE -> */{
	/*6.1. OP_ASIG EXP RDEF_VARIABLE2 */			{},
	/*6.2. RDEF_VARIABLE2*/			{}
 								},
/*7. RDEF_VARIABLE2 -> */{
	/*7.1. , iden RDEF_VARIABLE */			{},
	/*7.2.  ;*/			{}


/* - - Definicion de Macros - -*/

 								},
/*8. MACROS -> */{
	/*8.1. # RMACRO*/			{}
 								},
/*9. RMACRO  -> */{
	/*9.1. define IDENTIFICADOR EXP */			{},
	/*9.2.  include RINCLUDE */			{},
	/*9.3.  ifdef iden RIFDEF #endif */			{},
	/*9.4.  undef iden */			{},
	/*9.5.   ifndef iden */			{},
	/*9.6.  error (caracter)* EOF */			{},
	/*9.7.  pragma (caracter)* EOL */			{}
 								},
/*10. RINCLUDE -> */{
	/*10.1. ENTRECOMILLADO */			{},
	/*10.2.  <iden.iden>*/			{}
 								},
/*11. RIFDEF ->  */{
	/*11.1. # RIFDEF2 RIFDEF */			{},
	/*11.2.  L_SENTENCIAS */			{},
	/*11.3.  λ */			{}
 								},
/*12. RIFDEF2 -> */{
	/*12.1. elif iden RIFDEF*/			{},
	/*12.2. else L_SENTENCIAS */			{},
	/*12.3.  RMACRO*/			{}


/* - - Definicion de Variables - -*/

 								},
/*13. DEFINICION_UNION -> */{
	/*13.1. union  iden  {L_VARIABLES}  LISTA_IDENS*/			{}
 								},
/*14. LISTA_IDENS -> */{
	/*14.1. iden CORCHETES RDEF_VARIABLE */			{},
	/*14.2.  ;*/			{}
 								},
/*15. IDENTIFICADOR -> */{
	/*15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR*/			{new CodigoR15_1()}
 								},
/*16. RIDENTIFICADOR ->*/{
	/*16.1.  CORCHETES */			{},
	/*16.2.  (L_PARAMS_LLAMADA) */			{}
 								},
/*17. CORCHETES ->*/{
	/*17.1.  λ */			{new AccionAsignar("listaCorchete",new OperandoCrearArrayList())},
	/*17.2.  [CONTENIDO] CORCHETES*/			{new AccionAsignar("listaCorchete",new OperacionAgregarALista(new OperandoGramatica(3,"listaCorchete"), new OperandoGramatica(1,"lugar")))}
 								},
/*18. CONTENIDO -> */{
	/*18.1. λ */			{},
	/*18.2.  EXP*/			{}
 								},
/*19. DEFINICION_ENUM -> */{
	/*19.1. enum iden{ iden RENUM} ;*/			{}
 								},
/*20. RENUM -> */{
	/*20.1. = OP_ASIG EXP RENUM2 */			{},
	/*20.2.  , iden RENUM */			{},
	/*20.3.  λ*/			{}
 								},
/*21. RENUM2-> */{
	/*21.1. ,iden RENUM */			{}, 
	/*21.2. λ */			{}
 								},
/*22. DEFINICION_STRUCT -> */{
	/*22.1. struct  iden  {L_VARIABLES}  LISTA_IDENS*/			{}
 								},
/*23. L_VARIABLES -> */{
	/*23.1. DEFINICION_STRUCT   L_VARIABLES */			{},
	/*23.2.  DEF_VAR L_VARIABLES */			{},
	/*23.3. DEFINICION_ENUM L_VARIABLES */			{},
	/*23.4.  DEFINICION_UNION L_VARIABLES*/			{},
	/*23.5.  λ*/			{}
 								},
/*24. DEF_VAR ->*/{
	/*24.1.  TIPO LISTA_IDENS*/			{}
 								},
/*25. DEFINICION_TYPEDEF -> */{
	/*25.1. typedef RDEF_TYPEDEF*/			{}
 								},
/*26. RDEF_TYPEDEF -> */{
	/*26.1. struct iden INDIRECCION iden; */			{},
	/*26.2.  TIPO iden; */			{},
	/*26.3.  union iden INDIRECCION iden; */			{},
	/*26.4.  enum iden INDIRECCION iden;*/			{}


/* - - Definicion de Funciones - -*/

 								},
/*27. RDEF_FUNCION -> */{
	/*27.1. ; */			{},
	/*27.2.  { BLOQUE_SENTENCIAS }*/			{}
 								},
/*28. L_PARAMS -> */{
	/*28.1. λ */			{},
	/*28.2.  TIPO RL_PARAMS*/			{}
 								},
/*29. RL_PARAMS -> */{
	/*29.1. λ */			{},
	/*29.2.   ,TIPO RL_PARAMS2 */			{},
	/*29.3.  iden RL_PARAMS3*/			{}
 								},
/*30. RL_PARAMS2 -> */{
	/*30.1. λ */			{},
	/*30.2.   ,TIPO RL_PARAMS2*/			{}
 								},
/*31. RL_PARAMS3 -> */{
	/*31.1. λ */			{},
	/*31.2.   ,TIPO iden RL_PARAMS3*/			{}


/* - - Definicion de Tipos - -*/

 								},
/*32. TIPO -> */{
	/*32.1. L_MODIFICADORES RTIPO*/			{},
 								},
/*33. RTIPO -> */{
	/*33.1. TIPO_PRIMITIVO INDIRECCION */			{},
	/*33.2.  iden INDIRECCION*/			{}
 								},
/*34. INDIRECCION -> */{
	/*34.1. * INDIRECCION */			{},
	/*34.2.  λ*/			{}
 								},
/*35. L_MODIFICADORES -> */{
	/*35.1. MODIFICADOR L_MODIFICADORES */			{},
	/*35.2.  λ*/			{},
 								},
/*36. MODIFICADOR ->*/{
	/*36.1.  auto */			{},
	/*36.2.  volatile */			{},
	/*36.3.  register */			{},
	/*36.4.  extern*/			{},
	/*36.5. const */			{},
	/*36.6.  unsigned */			{},
	/*36.7.  signed */			{},
	/*36.8.  static*/			{}
 								},
/*37. TIPO_PRIMITIVO -> */{
	/*37.1. void */			{},
	/*37.2. int */			{},
	/*37.3. char */			{},
	/*37.4.  float */			{},
	/*37.5. double*/			{}


/* - - Definicion de Sentencias - -*/

 								},
/*38. L_SENTENCIAS -> */{
	/*38.1. {BLOQUE_SENTENCIAS} */			{},
	/*38.2.  SENTENCIA*/			{}
 								},
/*39. BLOQUE_SENTENCIAS  ->*/{
	/*39.1.  SENTENCIA BLOQUE_SENTENCIAS */			{},
	/*39.2. {BLOQUE_SENTENCIAS}*/					{},
	/*39.3  λ*/										{}
 								},
/*40. SENTENCIA ->  */{
	/*40.1.  ; */			{},
	/*40.2.  SENTENCIA_IF */			{},
	/*40.3.  SENTENCIA_BUCLE */			{},
	/*40.4.  REXP4 */			{},
	/*40.5.  SENTENCIA_CASE */			{},
	/*40.6.  OTRAS_SENTENCIAS ; */			{},
	/*40.7.  MACROS */			{},
	/*40.8.  DEFINICION_STRUCT */			{},
	/*40.9.  DEFINICION_ENUM */			{},
	/*40.10.  DEFINICION_UNION */			{},
	/*40.11.  DEFINICION_TYPEDEF*/			{}
 								},
/*41. OTRAS_SENTENCIAS  -> */{
	/*41.1. break */			{},
	/*41.2.  continue */			{},
	/*41.3.  printf(ENTRECOMILLADO RPRINTF) */			{},
	/*41.4.  scanf(ENTRECOMILLADO RSCANF) */			{},
	/*41.5.  return EXP*/			{}

 								},
/*42. ENTRECOMILLADO -> */{
	/*42.1. “(caracter)*” */			{}
 								},
/*43. RPRINTF -> */{
	/*43.1. , REFERENCIA   INDIRECCION   RPRINTF2 */			{},
	/*43.2.  λ*/			{}
 								},
/*44. REFERENCIA -> */{
	/*44.1. & */			{},
	/*44.2.   λ*/			{}
 								},
/*45. RPRINTF2 -> */{ 
	/*45.1. EXP RPRINTF*/			{}
 								},
/*46. RSCANF -> */{
	/*46.1. REFERENCIA   INDIRECCION   RSCANF2 */			{},
	/*46.2.  λ*/			{}
 								},
/*47. RSCANF2 -> */{
	/*47.1. IDENTIFICADOR CORCHETES */			{},
	/*47.2.  λ*/			{}
 								},
/*48. SENTENCIA_IF -> */{
	/*48.1. if (EXP) RSENTENCIA_IF*/			{	//Creamos una lista nueva para el codigo 
													new AccionAsignar("codigo",new OperandoCrearArrayList()),
													/*Se introducen como en una pila*/
													//Metemos el codigo de resto de sentecia IF. (los bloques)
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(4,"codigo"))), 
													//Metemos la instruccion if (exp.lugar=0) goto else-if
													new AccionGenCodigo(new InsIfGoto(),null,new OperandoGramatica(2,"lugar"),new OperandoDirecto("="),new OperandoDirecto("0"), new OperacionHeredada(new OperandoDirecto("else-if"),new OperandoGramatica(-1,"numIf"),"suma"),0), 
													//Metemos el codigo de la exp.
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(2,"codigo"))),
													
													/*codigo exp
													 * (exp.lugar=0) goto else-if
													 * bloques...
													 */
													
													
														}
 								},
/*49. RSENTENCIA_IF -> */{									
	/*49.1. L_SENTENCIAS SENTENCIA_ELSE*/			{new AccionAsignar("numIf",new OperandoCrearIfTemp()), //Creamos un nuevo numero para el if. El numIf se propagará hacia arriba.
														
														//Creamos una lista nueva para el codigo
														new AccionAsignar("codigo",new OperandoCrearArrayList()),
														
														//Asignamos la etiqueta else-ifX  al principio del bloque else.
														new AccionAsignarEtiqueta(new OperandoGramatica(1,"codigo"),new OperacionHeredada(new OperandoDirecto("else-if"),new OperandoGramatica(-1,"numIf"),"suma"),0),  
														
														/*Se introducen como en una pila*/
														
														//Metemos la etiqueta fin-ifX al final del codigo
														new AccionGenCodigo(new InstruccionIntermedio(),new OperacionHeredada(new OperandoDirecto("fin-if"),new OperandoGramatica(-1,"numIf"),"suma"),null,null,null,null,0), 
														
														//Metemos el bloque de codigo else.
														new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(1,"codigo"))), 
														
														//Metemos la instruccion goto despues del bloque if (para cuando es true se salte el else)
														new AccionGenCodigo(new InsGoto(),null,new OperacionHeredada(new OperandoDirecto("fin-if"),new OperandoGramatica(-1,"numIf"),"suma"),null,null,null,0), 
									
														//Metemos el bloque de codigo if.
														new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(0,"codigo"))),  

														
														
														
														/*bloque if
														 * goto fin-if
														 * etiqueta else-if: bloque else
														 * etiqueta fin-if
														 */
														
														
														
													}
	
						},
/*50. SENTENCIA_ELSE -> */{
	/*50.1. else L_SENTENCIAS */			{},
	/*50.2.  λ*/			{}
 								},
/*51. SENTENCIA_BUCLE -> */{
	/*51.1. do L_SENTENCIAS while(EXP) */			{//Creamos una lista nueva para el codigo
													new AccionAsignar("codigo",new OperandoCrearArrayList()),
													
													//Creamos un nuevo numero para el bucle
													new AccionAsignar("numBucle",new OperandoCrearBucleTemp()), 
													
													//Asignamos la etiqueta bucle-comienzo al principio del codigo de L_SENTENCIAS
													new AccionAsignarEtiqueta(new OperandoGramatica(1,"codigo"),new OperacionHeredada(new OperandoDirecto("comienzo"),new OperandoGramatica(-1,"numBucle"),"suma"),0),  
													
													
													
													/*Se introducen como en una pila*/
													
													//Metemos la instruccion if (exp.lugar=1) goto comienzo-bucle
													new AccionGenCodigo(new InsIfGoto(),null,new OperandoGramatica(4,"lugar"),new OperandoDirecto("="),new OperandoDirecto("1"), new OperacionHeredada(new OperandoDirecto("comienzo-bucle"),new OperandoGramatica(-1,"numBucle"),"suma"),0),
													
													//Metemos el codigo de la EXP
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(4,"codigo"))),		
															
													//Metemos el codigo de L_SENTENCIAS. 
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(1,"codigo"))), 
													
													/*etiqueta comienzo-bucle: bloque L_SENTENCIAS
													 * codigo EXP
													 * if (exp.lugar=1) goto comienzo-bucle
													 * 
													 */
													
	
	
	},
	/*51.2.  while(EXP) L_SENTENCIAS */			{  /* //Creamos una lista nueva para el codigo
													new AccionAsignar("codigo",new OperandoCrearArrayList()),
													//Asignamos la etiqueta comienzo al principio del bloque while
													new AccionAsignarEtiqueta(new OperandoGramatica(0,"codigo"),new OperacionHeredada(new OperandoDirecto("comienzo"),new OperandoGramatica(-1,"numWhile"),"suma"),0),  
													
													//Metemos el codigo de L_SENTENCIAS. (los bloques)
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(4,"codigo"))), 
													//Metemos la instruccion if (exp.lugar=0) goto siguiente
													new AccionGenCodigo(new InsIfGoto(),null,new OperandoGramatica(2,"lugar"),new OperandoDirecto("="),new OperandoDirecto("0"), new OperacionHeredada(new OperandoDirecto("siguiente"), null, null)),
													//El numWhile se propagará hacia arriba
													new AccionAsignar("numWhile",new OperandoCrearIfTemp()), 
													//Metemos el codigo de la EXP
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(4,"codigo"))),
													//Metemos la instruccion que vuelve al comienzo del while
													new AccionGenCodigo(new InsGoto(),null),
													
													
<<<<<<< .mine
													
													
													//Metemos la etiqueta fin-ifX al final del codigo
													new AccionGenCodigo(new InstruccionIntermedio(),new OperacionHeredada(new OperandoDirecto("fin-if"),new OperandoGramatica(-1,"numIf"),"suma"),null,null,null,null,0), 
													
													//Metemos el bloque de codigo else.
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(1,"codigo"))), 
													
													//Metemos la instruccion goto despues del bloque if (para cuando es true se salte el else)
													new AccionGenCodigo(new InsGoto(),null,new OperacionHeredada(new OperandoDirecto("fin-if"),new OperandoGramatica(-1,"numIf"),"suma"),null,null,null,0), 
								
													//Metemos el bloque de codigo if.
													new AccionAsignar("codigo",new OperacionAgregarALista(new OperandoGramatica(-1,"codigo"),new OperandoGramatica(0,"codigo"))),
													*/ 
		
													/* etiqueta comienzo-bucle: codigo EXP
													 * if (exp.lugar=0) goto fin-bucle
													 * bloque L_SENTENCIAS
													 * goto comienzo-bucle
													 * etiqueta fin-bucle:
													 */
		
		
													

												},
	/*51.3.  for(CAMPO;CAMPO;CAMPO) L_SENTENCIAS*/			{
													
													
													/* codigo CAMPO1
													 * etiqueta comienzo-bucle: codigo CAMPO2
													 * if (CAMPO2.lugar=0) goto fin-bucle
													 * bloque L_SENTENCIAS
													 * codigo CAMPO3
													 * goto comienzo-bucle
													 * etiqueta fin-bucle:
													 */
												}
 								},
/*52. CAMPO  -> */{
	/*52.1. λ */			{},
	/*52.2.   EXP*/			{}
 								},
/*53. SENTENCIA_CASE -> */{
	/*53.1. switch (EXP) L_CASES*/			{
		
		
												
												/* codigo EXP
												 * etiqueta caseX.1: codigo EXP(case1)
												 * if (EXP.lugar=EXP(case1).lugar) goto caseX.2
												 * bloque case1
												 
												 * etiqueta caseX.2: codigo EXP(case2)
												 * if (EXP.lugar=EXP(case2).lugar) goto caseX.3
												 * bloque case2
												 
												 * etiqueta caseX.3: codigo EXP(case3)
												 * if (EXP.lugar=EXP(case3).lugar) goto defaultX
												 * bloque case3
												 
												 * etiqueta defaultX: bloque default
												 * etiqueta fin-caseX:
												 */
		
											//Como en este nivel no tenemos los atributos necesarios(el codigo,y el lugar de las EXP_COND y el codigo de los BLOQUE_SENTENCIAS) los tenemos que subir en listas
											//Como hacer el break??; y las listas de listas?? listas<listas<codigo>>;
		
											}
 								},
/*54. L_CASES  -> */{
	/*54.1. CASE */			{},
	/*54.2.  {CASES}*/			{}
 								},
/*55. CASES  ->  */{
	/*55.1. case EXP_COND: RCASES */			{},
	/*55.2.  default: RCASES2 */			{},
	/*55.3.  λ*/			{}
 								},
/*56. RCASES  ->  */{
	/*56.1. BLOQUE_SENTENCIAS CASES */			{},
	/*56.2.  λ*/			{}
 								},
/*57. RCASES2  -> */{
	/*57.1.  BLOQUE_SENTENCIAS */			{},
	/*57.2.  Λ*/			{}
 								},
/*58. CASE ->  */{
	/*58.1. case EXP_COND: BLOQUE_SENTENCIAS */			{},
	/*58.2.  default: BLOQUE_SENTENCIAS*/			{}


/* - - Definicion de Expresiones - -*/

 								},
/*59. EXP -> */{
	/*59.1. EXP_COND REXP*/			{new AccionGenericaCodigo(true),
										new  AccionCondicionada(1,"lugar","distinto",null,
											new AccionCondicionada(1,"operacion","igual","=",
												new Accion[]{ new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"lugar"))},
												new Accion[]{ new AccionGenCodigo(new InsCuarteto(),null,  new OperandoGramatica(0,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion2"), new OperandoGramatica(1,"lugar"))}
											)
										)
							
									}
											//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
									
									
									},
/*60. REXP -> */{
	/*60.1. OP_ASIG EXP */			{new AccionAsignar("operacion",0,"operacion"),new AccionAsignar("operacion2",0,"operacion2")},
	/*60.2.  λ*/			{}
 								},
/*61. OP_ASIG  -> */{
	/*61.1. =  */			{new AccionAsignar("operacion","=")},
	/*61.2.  += */			{new AccionAsignar("operacion","+="),new AccionAsignar("operacion2","+")},
	/*61.3.  -= */			{new AccionAsignar("operacion","-="),new AccionAsignar("operacion2","-")},
	/*61.4.  *= */			{new AccionAsignar("operacion","*="),new AccionAsignar("operacion2","*")},
	/*61.5.  /= */			{new AccionAsignar("operacion","/="),new AccionAsignar("operacion2","/")},
	/*61.6.  <<= */			{new AccionAsignar("operacion","<<="),new AccionAsignar("operacion2","<<")},
	/*61.7.  >>= */			{new AccionAsignar("operacion",">>="),new AccionAsignar("operacion2",">>")},
	/*61.8.  &= */			{new AccionAsignar("operacion","&="),new AccionAsignar("operacion2","&")},
	/*61.9.  |= */			{new AccionAsignar("operacion","|="),new AccionAsignar("operacion2","|")},
	/*61.10.  ^= */			{new AccionAsignar("operacion","^="),new AccionAsignar("operacion2","^")},
	/*61.11.  %=*/			{new AccionAsignar("operacion","%="),new AccionAsignar("operacion2","%")}
 								},
/*62. EXP_COND  -> */{
	/*62.1. EXP_ORL REXP_COND*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
												new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
											//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*63. REXP_COND ->*/{
	/*63.1.  ? EXP : EXP REXP_COND*/			{},
	/*63.2.  λ */			{}
 								},
/*64. EXP_ORL  ->  */{
	/*64.1. EXP_ANDL REXP_ORL*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
											new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
										//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*65. REXP_ORL -> */{
	/*65.1. |EXP_ORL */			{new AccionAsignar("operacion","||")},
	/*65.2.  λ*/			{}
 								},
/*66. EXP_ANDL -> */{
	/*66.1. EXP_ORB REXP_ANDL*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
											new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
										//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*67. REXP_ANDL -> */{
	/*67.1. && EXP_ANDL */			{new AccionAsignar("operacion","&&")},
	/*67.2.  λ*/			{}
 								},
/*68. EXP_ORB  -> */{
	/*68.1. EXP_XORB REXP_ORB*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
			new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
		//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*69. REXP_ORB ->  */{
	/*69.1. | EXP_ORB */			{new AccionAsignar("operacion","|")},
	/*69.2.  λ*/			{}
 								},
/*70. EXP_XORB  ->  */{
	/*70.1. EXP_ANDB REXP_XORB*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
													new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
												//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
									}
 								},
/*71. REXP_XORB -> */{
	/*71.1. ^ EXP_XORB */			{new AccionAsignar("operacion","^")},
	/*71.2.  λ*/			{}
 								},
/*72. EXP_ANDB  ->  */{
	/*72.1. EXP_REL REXP_ANDB */			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
			new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
		//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*73. REXP_ANDB -> */{
	/*73.1. & EXP_ANDB */			{new AccionAsignar("operacion","&")},
	/*73.2.  λ*/			{}
 								},
/*74. EXP_REL -> */{
	/*74.1. EXP_COMP REXP_REL*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
			new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
		//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*75. REXP_REL -> */{
	/*75.1. OP_REL EXP_REL */			{new AccionAsignar("operacion",0,"operacion")},
	/*75.2.  λ*/			{},
 								},
/*76. OP_REL -> */{
	/*76.1. != */			{new AccionAsignar("operacion","!=")},
	/*76.2.  == */			{new AccionAsignar("operacion","==")}
 								},
/*77. EXP_COMP -> */{
	/*77.1. EXP_DESPL  REXP_COMP */			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
												new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
											//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
											},
 								},
/*78. REXP_COMP -> */{
	/*78.1. OP_COMP   EXP_COMP  */			{new AccionAsignar("operacion",0,"operacion")},
	/*78.2.   λ*/			{}
 								},
/*79. OP_COMP ->   */{
	/*79.1. < */			{new AccionAsignar("operacion","<")},
	/*79.2. <= */			{new AccionAsignar("operacion","<=")},
	/*79.3.  > */			{new AccionAsignar("operacion",">")},
	/*79.4.  >= */			{new AccionAsignar("operacion",">=")}
 								},
/*80. EXP_DESPL -> */{
	/*80.1. EXP_AD REXP_DESPL*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
												new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
											//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
											}
 								},
/*81. REXP_DESPL -> */{
	/*81.1. OP_DESPL  EXP_DESPL */			{new AccionAsignar("operacion",0,"operacion")},
	/*81.2.  λ*/			{}
 								},
/*82. OP_DESPL ->  */{
	/*82.1.  >> */			{new AccionAsignar("operacion",">>")},
	/*82.2.  << */			{new AccionAsignar("operacion","<<")}
 								},
/*83. EXP_AD  ->  */{
	/*83.1. EXP_MULT REXP_AD*/			{new AccionGenericaCodigo(true), new AccionCondicionada(1,"lugar","distinto",null,
											new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
										//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
									}
 								},
/*84. REXP_AD  ->  */{
	/*84.1. OP_AD EXP_AD */			{new AccionAsignar("operacion",0,"operacion")},
	/*84.2.  λ*/			{}
 								},
/*85. OP_AD  ->   */{
	/*85.1. + */			{new AccionAsignar("operacion","+")},
	/*85.2.  -*/			{new AccionAsignar("operacion","-")}
 								},
/*86. EXP_MULT -> */{
	/*86.1. EXP1 REXP_MULT*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
												new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
											//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
										}
 								},
/*87. REXP_MULT -> */{
	/*87.1. OP_MULT EXP_MULT */			{new AccionAsignar("operacion",0,"operacion")},
	/*87.2.  λ*/			{}
 								},
/*88. OP_MULT ->  */{
	/*88.1. * */			{new AccionAsignar("operacion","*")},
	/*88.2.  / */			{new AccionAsignar("operacion","/")},
	/*88.3.  %*/			{new AccionAsignar("operacion","%")}
 								},
/*89. EXP1 ->   */{
	/*89.1. EXP2 */			{},
	/*89.2.  OP_UNARIOS EXP1 */			{/*falta instruccion aqui*/},
	/*89.3.  sizeof(EXP1)*/			{}
 								},
/*90. OP_UNARIOS  ->  */{
	/*90.1. ! */			{new AccionAsignar("operacion","!")},
	/*90.2.  ~ */			{new AccionAsignar("operacion","~")},
	/*90.3.  * */			{new AccionAsignar("operacion","*")},
	/*90.4.  & */			{new AccionAsignar("operacion","&")},
	/*90.5. OP_AD*/			{},
	/*90.6.  OP_INC*/			{}
 								},
/*91. OP_INC ->   */{
	/*91.1. ++ */			{new AccionAsignar("operacion","++")},
	/*91.2.  --*/			{new AccionAsignar("operacion","--")}
 								},
/*92. EXP2 ->  */{
	/*92.1. EXP3  REXP2*/			{new AccionGenericaCodigo(true),new AccionCondicionada(1,"lugar","distinto",null,
											new Accion[]{new AccionAsignar("lugar",new OperandoCrearVarTemp()), new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,"lugar"), new OperandoGramatica(1,"operacion"), new OperandoGramatica(1,"lugar"))})
										//Si no hay lugar, se conserva el de EXP (izquierda) y no hace falta subirlo pues se propaga solo.
									}
 								},
/*93. REXP2  -> */{
	/*93.1. OP_INC */			{},
	/*93.2.  OP_SELECCION EXP3 */			{new AccionAsignar("operacion",0,"operacion")},
	/*93.3.  λ*/			{}
 								},
/*94. OP_SELECCION  ->    */{
	/*94.1. . */			{new AccionAsignar("operacion",".")},
	/*94.2.  ->*/			{new AccionAsignar("operacion","->")}
 								},
/*95. EXP3 -> */{
	/*95.1. IDENTIFICADOR */			{/*Cogemos el lugar del identificador*/},
	/*95.2.  entero */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
									new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*95.3. real */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
										new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*95.4.  ( REXP3 */			{},
	/*95.5.  NULL */			{},
	/*95.6.  { LISTA_EXP} */			{},
	/*95.7.  comillasChar */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
											new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*95.8.  ENTRECOMILLADO*/			{}
 								},
/*96. LISTA_EXP ->  */{
	/*96.1.  λ */			{},
	/*96.2.  EXP , LISTA_EXP*/			{}

 								},
/*97. REXP3 ->  */{
	/*97.1. TIPO_PRIMITIVO INDIRECCION) EXP */			{},
	/*97.2.  EXP_SIN_IDEN REXP) */			{},
	/*97.3.  MODIFICADOR L_MODIFICADORES RTIPO) EXP */			{},
	/*97.4.  iden REXP3_2 ) AUX*/			{}
 								},
/*98. EXP_SIN_IDEN -> */{
	/*98.1. & INDIRECCION iden RIDEN */			{},
	/*98.2.  INDIRECCION2 iden RIDEN */			{},
	/*98.3.  entero */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
									new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*98.4. real */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
										new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*98.5.  ( REXP3 */			{},
	/*98.6.  NULL */			{},
	/*98.7.  { LISTA_EXP} */			{},
	/*98.8.  comillasChar */			{new AccionAsignar("lugar",new OperandoCrearVarTemp()),
											new AccionGenCodigo(new InsCuarteto(),null, new OperandoGramatica(-1,"lugar"), new OperandoGramatica(0,""), null, null)},
	/*98.9.  ENTRECOMILLADO*/			{}
 								},
/*99. REXP3_2 -> */{
	/*99.1. INDIRECCION2 */			{},
	/*99.2.  RIDEN REXPRESIONES*/			{}
 								},
/*100. REXPRESIONES -> */{
	/*100.1. REXP */			{},
	/*100.2.  REXP_COND */			{},
	/*100.3.  REXP_ORL */			{},
	/*100.4.  REXP_ANDL */			{},
	/*100.5.  REXP_ORB */			{},
	/*100.6.  REXP_XORB */			{},
	/*100.7.  REXP_ANDB */			{},
	/*100.8.  REXP_REL */			{},
	/*100.9.  REXP_COMP */			{},
	/*100.10.  REXP_DESPL */			{},
	/*100.11.  REXP_AD */			{},
	/*100.12.  REXP_MULT */			{},
	/*100.13.  OP_SELECCION EXP3 */			{},
	/*100.14.  OP_INC*/			{}
 								},
/*101. INDIRECCION2 -> */{
	/*101.1. * INDIRECCION*/			{}
 								},
/*102. L_PARAMS_LLAMADA -> */{
	/*102.1.  EXP  RES_LISTA_PARAMS_LLAMDA */			{},
	/*102.2.  λ*/			{}
 								},
/*103. RES_LISTA_PARAMS_LLAMADA ->  */{
	/*103.1. ,EXP  RES_LISTA_PARAMS_LLAMADA */			{},
	/*103.2.  λ*/			{}
 								},
/*104. AUX-> */{
	/*104.1. EXP */			{},
	/*104.2.  λ*/			{}
 								},
/*105. REXP4 -> */{
	/*105.1. TIPO_PRIMITIVO INDIRECCION  RDEFINICION */			{},
	/*105.2.  EXP_SIN_IDEN REXP; */			{},
	/*105.3.  MODIFICADOR L_MODIFICADORES RTIPO RDEFINICION */			{},
	/*105.4.  iden REXP3_2 RDEFINICION  */			{new CodigoR105_4()},
		// Definir variable con tipo definido por el usuario
		//Aqui hay que ver que tipo de operacion hace.
																													
	/*105.5.  OP_INC IDENTIFICADOR*/			{}

 								},
/*106. M_AMBITO->*/{
	/*106.1. (lambda)*/			{}},
/*107. M_AMBITO_FUNCION->*/{
	/*107.1. (lambda)*/			{}}
	
	};
}

