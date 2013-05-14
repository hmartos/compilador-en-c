package analizadorSemantico;

import acciones.*;
import accionesEspecificas.AccionR15_1;
import accionesEspecificas.AccionR3_2;

public class AccionesError {
	
	

	static Accion[][][] acciones={
		
/* - - Definicion de Programa - -*/

 								
/*1. PROGRAMA  ->*/{					
	/*1.1.  L_DEFINICIONES EOF */			{}, 	
	/*1.2. EOF*/			{}				
 								},
/*2. L_DEFINICIONES ->  */{			
	/*2.1. DEFINICION_GLOBAL  L_DEFINICIONES*/			{},
	/*2.2 ? */									{}
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
	/*4.2. iden RDEFINICION2*/			{}
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
	/*15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR*/			{}
 								},
/*16. RIDENTIFICADOR ->*/{
	/*16.1.  CORCHETES */			{},
	/*16.2.  (L_PARAMS_LLAMADA) */			{}
 								},
/*17. CORCHETES ->*/{
	/*17.1.  λ */			{},
	/*17.2.  [CONTENIDO] CORCHETES*/			{}
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

 							},
/* - - Definicion de Sentencias - -*/

 								
/*38. L_SENTENCIAS -> */{
	/*38.1. {BLOQUE_SENTENCIAS} */			{},
	/*38.2.  SENTENCIA*/			{}
 								},
/*39. BLOQUE_SENTENCIAS  ->*/{
	/*39.1.  SENTENCIA BLOQUE_SENTENCIAS */			{},
	/*39.2. {BLOQUE_SENTENCIAS}*/					{},
	/*39.3  λ*/									{}
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
	/*41.3.  printf(EXP) */			{},
	/*41.4.  scanf(iden) */			{},
	/*41.5.  return EXP*/			{}

 								},
/*42. ENTRECOMILLADO -> */{
	/*42.1. “(caracter)*” */			{}
 								},
/*44. REFERENCIA -> */{
	/*44.1. & */			{},
	/*44.2.   λ*/			{}
 								},
/*48. SENTENCIA_IF -> */{
	/*48.1. if (EXP) RSENTENCIA_IF*/			{}
 								},
/*49. RSENTENCIA_IF -> */{
	/*49.1. L_SENTENCIAS SENTENCIA_ELSE*/			{}
 								},
/*50. SENTENCIA_ELSE -> */{
	/*50.1. else L_SENTENCIAS */			{},
	/*50.2.  λ*/			{}
 								},
/*51. SENTENCIA_BUCLE -> */{
	/*51.1. do L_SENTENCIAS while(EXP) */			{},
	/*51.2.  while(EXP) L_SENTENCIAS */			{},
	/*51.3.  for(CAMPO;CAMPO;CAMPO) L_SENTENCIAS*/			{}
 								},
/*52. CAMPO  -> */{
	/*52.1. λ */			{},
	/*52.2.   EXP*/			{}
 								},
/*53. SENTENCIA_CASE -> */{
	/*53.1. switch (EXP) L_CASES*/			{}
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
	/*57.2.  Λ*/			{},
	/*57.3. */			{}
 								},
/*58. CASE ->  */{
	/*58.1. case EXP_COND: BLOQUE_SENTENCIAS */			{},
	/*58.2.  default: BLOQUE_SENTENCIAS*/			{}


/* - - Definicion de Expresiones - -*/

 								},
/*59. EXP -> */{
	/*59.1. EXP_COND REXP*/			{}
 								},
/*60. REXP -> */{
	/*60.1. OP_ASIG EXP */			{},
	/*60.2.  λ*/			{}
 								},
/*61. OP_ASIG  -> */{
	/*61.1. =  */			{},
	/*61.2.  += */			{},
	/*61.3.  -= */			{},
	/*61.4.  *= */			{},
	/*61.5.  /= */			{},
	/*61.6.  <<= */			{},
	/*61.7.  >>= */			{},
	/*61.8.  &= */			{},
	/*61.9.  |= */			{},
	/*61.10.  ^= */			{},
	/*61.11.  %=*/			{}
 								},
/*62. EXP_COND  -> */{
	/*62.1. EXP_ORL REXP_COND*/			{}
 								},
/*63. REXP_COND ->*/{
	/*63.1.  ? EXP : EXP REXP_COND*/			{},
	/*63.2.  λ */			{}
 								},
/*64. EXP_ORL  ->  */{
	/*64.1. EXP_ANDL REXP_ORL*/			{}
 								},
/*65. REXP_ORL -> */{
	/*65.1. |EXP_ORL */			{},
	/*65.2.  λ*/			{}
 								},
/*66. EXP_ANDL -> */{
	/*66.1. EXP_ORB REXP_ANDL*/			{}
 								},
/*67. REXP_ANDL -> */{
	/*67.1. && EXP_ANDL */			{},
	/*67.2.  λ*/			{}
 								},
/*68. EXP_ORB  -> */{
	/*68.1. EXP_XORB REXP_ORB*/			{}
 								},
/*69. REXP_ORB ->  */{
	/*69.1. | EXP_ORB */			{},
	/*69.2.  λ*/			{}
 								},
/*70. EXP_XORB  ->  */{
	/*70.1. EXP_ANDB REXP_XORB*/			{}
 								},
/*71. REXP_XORB -> */{
	/*71.1. ^ EXP_XORB */			{},
	/*71.2.  λ*/			{}
 								},
/*72. EXP_ANDB  ->  */{
	/*72.1. EXP_REL REXP_ANDB */			{}
 								},
/*73. REXP_ANDB -> */{
	/*73.1. & EXP_ANDB */			{},
	/*73.2.  λ*/			{}
 								},
/*74. EXP_REL -> */{
	/*74.1. EXP_COMP REXP_REL*/			{}
 								},
/*75. REXP_REL -> */{
	/*75.1. OP_REL EXP_REL */			{},
	/*75.2.  λ*/			{},
 								},
/*76. OP_REL -> */{
	/*76.1. != */			{},
	/*76.2.  == */			{}
 								},
/*77. EXP_COMP -> */{
	/*77.1. EXP_DESPL  REXP_COMP */			{},
 								},
/*78. REXP_COMP -> */{
	/*78.1. OP_COMP   EXP_COMP  */			{},
	/*78.2.   λ*/			{}
 								},
/*79. OP_COMP ->   */{
	/*79.1. < */			{},
	/*79.2. <= */			{},
	/*79.3.  > */			{},
	/*79.4.  >= */			{}
 								},
/*80. EXP_DESPL -> */{
	/*80.1. EXP_AD REXP_DESPL*/			{}
 								},
/*81. REXP_DESPL -> */{
	/*81.1. OP_DESPL  EXP_DESPL */			{},
	/*81.2.  λ*/			{}
 								},
/*82. OP_DESPL ->  */{
	/*82.1.  >> */			{},
	/*82.2.  << */			{}
 								},
/*83. EXP_AD  ->  */{
	/*83.1. EXP_MULT REXP_AD*/			{}
 								},
/*84. REXP_AD  ->  */{
	/*84.1. OP_AD EXP_AD */			{},
	/*84.2.  λ*/			{}
 								},
/*85. OP_AD  ->   */{
	/*85.1. + */			{},
	/*85.2.  -*/			{}
 								},
/*86. EXP_MULT -> */{
	/*86.1. EXP1 REXP_MULT*/			{},
 								},
/*87. REXP_MULT -> */{
	/*87.1. OP_MULT EXP_MULT */			{},
	/*87.2.  λ*/			{}
 								},
/*88. OP_MULT ->  */{
	/*88.1. * */			{},
	/*88.2.  / */			{},
	/*88.3.  %*/			{}
 								},
/*89. EXP1 ->   */{
	/*89.1. EXP2 */			{},
	/*89.2.  OP_UNARIOS EXP1 */			{},
	/*89.3.  sizeof(EXP1)*/			{}
 								},
/*90. OP_UNARIOS  ->  */{
	/*90.1. ! */			{},
	/*90.2.  ~ */			{},
	/*90.3.  * */			{},
	/*90.4.  & */			{},
	/*90.5. OP_AD*/			{},
	/*90.6.  OP_INC*/			{}
 								},
/*91. OP_INC ->   */{
	/*91.1. ++ */			{},
	/*91.2.  --*/			{}
 								},
/*92. EXP2 ->  */{
	/*92.1. EXP3  REXP2*/			{},
 								},
/*93. REXP2  -> */{
	/*93.1. OP_INC */			{},
	/*93.2.  OP_SELECCION EXP3 */			{},
	/*93.3.  λ*/			{}
 								},
/*94. OP_SELECCION  ->    */{
	/*94.1. . */			{},
	/*94.2.  ->*/			{}
 								},
/*95. EXP3 -> */{
	/*95.1. IDENTIFICADOR */			{},
	/*95.2.  entero */			{},
	/*95.3. real */			{},
	/*95.4.  ( REXP3 */			{},
	/*95.5.  NULL */			{},
	/*95.6.  { LISTA_EXP} */			{},
	/*95.7.  comillasChar */			{},
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
	/*98.3.  entero */			{},
	/*98.4. real */			{},
	/*98.5.  ( REXP3 */			{},
	/*98.6.  NULL */			{},
	/*98.7.  { LISTA_EXP} */			{},
	/*98.8.  comillasChar */			{},
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
	/*105.4.  iden REXP3_2 RDEFINICION  // Definir variable con tipo definido por el usuario*/			{},
	/*105.5.  OP_INC IDENTIFICADOR*/			{}

 								},
/*106. M_AMBITO->*/{
	/*106.1. (lambda)*/			{}},
/*107. M_AMBITO_FUNCION->*/{
	/*107.1. (lambda)*/			{}}

	
	};
}

