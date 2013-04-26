package analizadorSemantico;

import acciones.*;
import accionesEspecificas.AccionR15_1;

public class AccionesGramatica {
	
	

	static Accion[][][] acciones={
/* - - Definicion de Programa - -*/

/*1. PROGRAMA  ->*/					{
	/*1.1.  L_DEFINICIONES EOF */ 		{new AccionAsignar("tipo",0,"tipo")},
	/*1.2. EOF*/						{new AccionAsignar("tipo","vacio")}
									},

	/*2. L_DEFINICIONES ->  */			{
	/*2.1. DEFINICION_GLOBAL  L_DEFINICIONES*/	{new AccionCondicionada(1,"tipo","igual","vacio",new AccionAsignar("tipo",0,"tipo"),new AccionAsignar("tipo","error"))}
										},

	/*3. DEFINICION_GLOBAL -> */		{
	/*3.1. MACROS */						{new AccionAsignar("tipo",0,"tipo")},
	/*3.2. TIPO RDEFINICION*/				{new AccionCondicionada(1,"tipo","igual","vacio",new AccionAsignar("tipo","vacio"),new AccionCondicionada(0,"tipo","igual",1,"tipo",new AccionAsignar("tipo","vacio"), new AccionAsignar("tipo","error")))},
	/*3.3.  DEFINICION_STRUCT */			{new AccionAsignar("tipo",0,"tipo")},
	/*3.4. DEFINICION_ENUM */				{new AccionAsignar("tipo",0,"tipo")},
	/*3.5. DEFINICION_UNION */				{new AccionAsignar("tipo",0,"tipo")},
	/*3.6.  DEFINICION_TYPEDEF*/			{new AccionAsignar("tipo",0,"tipo")}
										},
	
/*4. RDEFINICION -> */				{
	/*4.1. ;  */                    	{new AccionAsignar("tipo","vacio")},
	/*4.2. iden RDEFINICION2*/      	{new AccionAsignar("tipo",1,"tipo")}
									},
	
/*5. RDEFINICION2 -> */					{
	/*5.1. CORCHETES RDEF_VARIABLE */		{new AccionAsignar("tipo",1,"tipo")},
	/*5.2. (L_PARAMS) RDEF_FUNCION*/		{new AccionCondicionada(1,"tipo","igual","vacio",new AccionAsignar("tipo","vacio"),new AccionCondicionada(1,"tipo","igual",1,"tipo",new AccionAsignar("tipo","vacio"), new AccionAsignar("tipo","error")))},
										},
							
/*6. RDEF_VARIABLE -> */				{ 
	/*6.1. OP_ASIG EXP RDEF_VARIABLE2 */	{new AccionCondicionada(1,"tipo","igual",2,"tipo",new AccionAsignar("tipo",2,"tipo"),new AccionCondicionada(2,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo",1,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 6.1: Los tipos no coinciden."))}))},
	/*6.2. RDEF_VARIABLE2*/					{new AccionAsignar("tipo",0,"tipo")},   
										},  
									
									
											
/*7. RDEF_VARIABLE2 -> */			{
	/*7.1. , iden RDEF_VARIABLE */		{new AccionAsignar("tipo",1,"vacio")},
	/*7.2.  ;*/							{new AccionAsignar("tipo","vacio")},
									},

/* - - Definicion de Macros - -*/

/*8. MACROS -> */					{
	/*8.1. # RMACRO*/					{new AccionAsignar("tipo",1,"tipo")}, 
									},
/*9. RMACRO  -> */						{
	/*9.1. define IDENTIFICADOR EXP */		{new AccionCondicionada(2,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionAsignar("tipo","vacio"))},
	/*9.2.  include RINCLUDE */				{new AccionAsignar("tipo",1,"tipo")},
	/*9.3.  ifdef iden RIFDEF #endif */		{new AccionAsignar("tipo",2,"tipo")},
	/*9.4.  undef iden */					{new AccionAsignar("tipo","vacio")},
	/*9.5.   ifndef iden */					{new AccionAsignar("tipo","vacio")},
	/*9.6.  error (caracter)* EOF */		{new AccionAsignar("tipo","vacio")},
	/*9.7.  pragma (caracter)* EOL */		{new AccionAsignar("tipo","vacio")},
										},
/*10. RINCLUDE -> */			{
	/*10.1. ENTRECOMILLADO */		{new AccionAsignar("tipo","vacio")},
	/*10.2.  <iden.iden>*/			{new AccionAsignar("tipo","vacio")},
								},
/*11. RIFDEF ->  */				{
	/*11.1. # RIFDEF2 RIFDEF */		{new AccionCondicionada(1,"tipo","igual",2,"tipo",new Accion[]{new AccionAsignar("tipo","tipo")},new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 11.1: Los tipos no coinciden."))})},
	/*11.2.  L_SENTENCIAS */		{new AccionAsignar("tipo",0,"tipo")},
	/*11.3.  ? */					{new AccionAsignar("tipo","vacio")},
								},
/*12. RIFDEF2 -> */				{
	/*12.1. elif iden RIFDEF*/		{new AccionAsignar("tipo",2,"tipo")},
	/*12.2. else L_SENTENCIAS */	{new AccionAsignar("tipo",1,"tipo")},
	/*12.3.  RMACRO*/				{new AccionAsignar("tipo",0,"tipo")},
								},

/* - - Definicion de Variables - -*/

/*13. DEFINICION_UNION -> */							{
	/*13.1. union  iden  {L_VARIABLES}  LISTA_IDENS*/		{new AccionCondicionada(5,"tipo","igual","listaUnion", 
			                                                 		new AccionCondicionada(3,"tipo","igual","vacio",
			                                                 				new Accion[]{new AccionAsignar("tipo","vacio")},  
			                                                 			new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 13.1: Las variables declaradas beden de ser de tipo Union."))} ), 
			                                                 		new AccionCondicionada(5,"tipo","igual","vacio", 
			                                                 				new AccionCondicionada(3,"tipo","igual","vacio",
			                                                 						new Accion[]{new AccionAsignar("tipo","vacio")},  
			                                                 						new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 13.1: Error en la definición de la unión."))}))), 
},
														},
/*14. LISTA_IDENS -> */						{
	/*14.1. iden CORCHETES RDEF_VARIABLE */		{new AccionAsignar("tipo",2,"tipo")},
	/*14.2.  ;*/								{new AccionAsignar("tipo","vacio")},
											},
/*15. IDENTIFICADOR -> */								{
	/*15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR*/	{ new AccionR15_1()}
														},
/*16. RIDENTIFICADOR ->*/		{					
	/*16.1.  CORCHETES */			{new AccionAsignar("tipo",0,"tipo"), new AccionAsignar("num",0,"num")},
	/*16.2.  (L_PARAMS_LLAMADA) */	{new AccionAsignar("tipo",1,"tipo"),new AccionAsignar("lista",0,"lista")},
								},
/*17. CORCHETES ->*/ {	
	/*17.1.  ? */    {new AccionAsignar("tipo","vacio"), new AccionAsignar("num","0")},
	/*17.2.  [CONTENIDO] CORCHETES*/ {new AccionAsignar("tipo","vacio"),new AccionAsignar("num",3,"num","suma",1)}},
/*18. CONTENIDO -> */ {
	/*18.1. ? */ {new AccionAsignar("tipo","vacio")},
	/*18.2.  EXP*/ {new AccionAsignar("tipo",0,"tipo")}},
/*19. DEFINICION_ENUM -> */ {
		
	/*19.1. enum iden{iden RENUM} ;*/ {new AccionAsignar("tipo",4,"tipo")}},
/*20. RENUM -> */ {
	/*20.1. = OP_ASIG EXP RENUM2 */ {new AccionCondicionada(3,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",4,"tipo"))},
	/*20.2.  , iden RENUM */ {new AccionAsignar("tipo",3,"tipo")},
	/*20.3.  ?*/ {new AccionAsignar("tipo","vacio")}},
/*21. RENUM2-> */ {
	/*21.1. ,iden RENUM */ {new AccionAsignar("tipo",3,"tipo")},
	/*21.2. ? */ {new AccionAsignar("tipo","vacio")}},
/*22. DEFINICION_STRUCT -> */								{
		/*22.1. struct  iden  {L_VARIABLES}  LISTA_IDENS*/ 		{new AccionCondicionada(5,"tipo","igual","listaStruct", new AccionCondicionada(3,"tipo","igual","vacio", new Accion[]{new AccionAsignar("tipo","vacio")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 22.1: Las variables declaradas beden de ser de tipo Struct."))}),new AccionCondicionada(5,"tipo","igual","vacio", new AccionCondicionada(3,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo","vacio")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla 22.1: Error en la definición del Struct.")) } )))}}, 

/*23. L_VARIABLES -> */ {
	/*23.1. DEFINICION_STRUCT L_VARIABLES */ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.2.  DEF_VAR L_VARIABLES */ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.3. DEFINICION_ENUM L_VARIABLES */ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.4.  DEFINICION_UNION L_VARIABLES*/ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.5.  ?*/ {new AccionAsignar("tipo","vacio")}},
	/*24. DEF_VAR ->*/											{
		/*24.1.  TIPO LISTA_IDENS*/    							{new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada(0,"tipo","igual",1,"tipo",new AccionAsignar("tipo","vacio"),new AccionCondicionada(1,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo","vacio")},new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 24.1: Los tipos no coinciden."))})))}},

/*25. DEFINICION_TYPEDEF -> */ {
	/*25.1. typedef RDEF_TYPEDEF*/ {new AccionAsignar("tipo",1,"tipo")}},
/*26. RDEF_TYPEDEF -> */ {
	/*26.1. struct iden INDIRECCION iden; */ {new AccionAsignar("tipo","vacio")},
	/*26.2.  TIPO iden; */ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo", "vacio"))},
	/*26.3.  union iden INDIRECCION iden; */ {new AccionAsignar("tipo","vacio")},
	/*26.4.  enum iden INDIRECCION iden;*/ {new AccionAsignar("tipo","vacio")}},


/* - - Definicion de Funciones - -*/

/*27. RDEF_FUNCION -> */ {
	/*27.1. ; */ {new AccionAsignar("tipo","vacio")},
	/*27.2.  { BLOQUE_SENTENCIAS }*/ {new AccionAsignar("tipo",1,"tipo")}},
/*28. L_PARAMS -> */ {
	/*28.1. ? */ {new AccionAsignar("tipo","vacio")},
	/*28.2.  TIPO RL_PARAMS*/ {new AccionAsignar("tipo",1,"tipo")}},
/*29. RL_PARAMS -> */ {
	/*29.1. ? */ {new AccionAsignar("tipo","vacio")},
	/*29.2.   ,TIPO RL_PARAMS2 */ {new AccionAsignar("tipo",2,"tipo")},
	/*29.3.  iden RL_PARAMS3*/ {new AccionAsignar("tipo",1,"tipo")}},
/*30. RL_PARAMS2 -> */ {
	/*30.1. ? */ {new AccionAsignar("tipo","vacio")},
	/*30.2.   ,TIPO RL_PARAMS2*/ {new AccionCondicionada(1,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada(2,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo","vacio")))}},
/*31. RL_PARAMS3 -> */ {
	/*31.1. ? */ {new AccionAsignar("tipo","vacio")},
	/*31.2.   ,TIPO iden RL_PARAMS3*/ {new AccionCondicionada(1,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada(3,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo","vacio")))}},


/* - - Definicion de Tipos - -*/

/*32. TIPO -> */ {
	/*32.1. L_MODIFICADORES RTIPO*/ {new AccionCondicionada(0,"tipo","igual","vacio", new AccionAsignar("tipo",1,"tipo"),new AccionAsignar("tipo","error"))}},
/*33. RTIPO -> */ {
	/*33.1. TIPO_PRIMITIVO INDIRECCION */	{new AccionAsignar("tipo",new OperacionSumarDimTipo(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"num")))},
	/*33.2.  iden INDIRECCION*/ 			{new AccionCondicionada
												(new CondicionHeredada(new OperacionClaseEntradaTS(new OperandoGramatica(0,"")),new OperandoDirecto("typedef"),"igual"),
												new Accion[]{new AccionAsignar("tipo",new OperacionSumarDimTipo(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"num")))},
												new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 33.2: El tipo no existe en la TS."))})}
					},
/*34. INDIRECCION -> */ {
	/*34.1. * INDIRECCION */ {new AccionAsignar("tipo","vacio"),new AccionAsignar("num",new OperacionHeredada(new OperandoGramatica(1,"num"),new OperandoDirecto(1),"sumar"))},
	/*34.2.  ?*/ {new AccionAsignar("tipo","vacio"),new AccionAsignar("num",0)}},
/*35. L_MODIFICADORES -> */ {
	/*35.1. MODIFICADOR L_MODIFICADORES */ 
	/*35.2.  ?*/ {new AccionAsignar("tipo","vacio")}},
/*36. MODIFICADOR ->*/ {
	/*36.1.  auto */ {new AccionAsignar("tipo","vacio")},
	/*36.2.  volatile */ {new AccionAsignar("tipo","vacio")},
	/*36.3.  register */ {new AccionAsignar("tipo","vacio")},
	/*36.4.  extern*/ {new AccionAsignar("tipo","vacio")},
	/*36.5. const */ {new AccionAsignar("tipo","vacio")},
	/*36.6.  unsigned */ {new AccionAsignar("tipo","vacio")},
	/*36.7.  signed */ {new AccionAsignar("tipo","vacio")},
	/*36.8.  static*/ {new AccionAsignar("tipo","vacio")}},
/*37. TIPO_PRIMITIVO -> */ {
	/*37.1. void */ {new AccionAsignar("tipo",new OperandoCrearTipo("void",0))},
	/*37.2. int */ {new AccionAsignar("tipo",new OperandoCrearTipo("int",0))},
	/*37.3. char */ {new AccionAsignar("tipo",new OperandoCrearTipo("char",0))},
	/*37.4.  float */ {new AccionAsignar("tipo",new OperandoCrearTipo("float",0))},
	/*37.5. double*/ {new AccionAsignar("tipo",new OperandoCrearTipo("double",0))}},


/* - - Definicion de Sentencias - -*/

/*38. L_SENTENCIAS -> */ {
	/*38.1. {BLOQUE_SENTENCIAS} */ {new AccionAsignar("tipo",1,"tipo")},
	/*38.2.  SENTENCIA*/ {new AccionAsignar("tipo",0,"tipo")}},
/*39. BLOQUE_SENTENCIAS  ->*/ {
	/*39.1.  SENTENCIA BLOQUE_SENTENCIAS */ {new AccionCondicionada(0,"tipo","igual", 1, "tipo",new Accion[]{ new AccionAsignar("tipo",0,"tipo")},new Accion[] {new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla: 39.1: Los tipos no coinciden"))})},
	/*39.2. {BLOQUE_SENTENCIAS} */ {new AccionAsignar("tipo",1,"tipo")},
	/* 39.3. ?*/ {new AccionAsignar("tipo","vacio")}},
/*40. SENTENCIA ->  */ {
	/*40.1.  ; */ {new AccionAsignar("tipo","vacio")},
	/*40.2.  SENTENCIA_IF */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.3.  SENTENCIA_BUCLE */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.4.  REXP4 */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.5.  SENTENCIA_CASE */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.6.  OTRAS_SENTENCIAS ; */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.7.  MACROS */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.8.  DEFINICION_STRUCT */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.9.  DEFINICION_ENUM */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.10.  DEFINICION_UNION */ {new AccionAsignar("tipo",0,"tipo")},
	/*40.11.  DEFINICION_TYPEDEF*/ {new AccionAsignar("tipo",0,"tipo")}},
/*41. OTRAS_SENTENCIAS  -> */ {
	/*41.1. break */ {new AccionAsignar("tipo","vacio")},
	/*41.2.  continue */ {new AccionAsignar("tipo","vacio")},
	/*41.3.  printf(ENTRECOMILLADO RPRINTF) */ {new AccionAsignar("tipo",4,"tipo")},
	/*41.4.  scanf(ENTRECOMILLADO RSCANF) */ {new AccionAsignar("tipo",4,"tipo")},
	/*41.5.  return EXP*/ {new AccionAsignar("tipo",1,"tipo")}},

/*42. ENTRECOMILLADO -> */ {
	/*42.1. “(caracter)*” */ {new AccionAsignar("tipo",new OperandoCrearTipo("char",1))}},
/*43. RPRINTF -> */ {
	/*43.1. , REFERENCIA   INDIRECCION   RPRINTF2 */ 
	/*43.2.  ?*/ {new AccionAsignar("tipo","vacio")}},
/*44. REFERENCIA -> */ {
	/*44.1. & */ {new AccionAsignar("tipo","vacio"), new AccionAsignar("tieneAmp","true")},
	/*44.2.   ?*/ {new AccionAsignar("tipo","vacio"),new AccionAsignar("tieneAmp","false")}},
/*45. RPRINTF2 -> */ {
	/*45.1. EXP RPRINTF*/ {new AccionCondicionada(1,"tipo","igual","error", new AccionAsignar("tipo","error"), new AccionAsignar("tipo",0,"tipo"))}},
/*46. RSCANF -> */ {
	/*46.1. REFERENCIA   INDIRECCION   RSCANF2 */
	/*46.2.  ?*/ {new AccionAsignar("tipo","vacio")}},
/*47. RSCANF2 -> */ {
	/*47.1. IDENTIFICADOR CORCHETES */
	/*47.2.  ?*/ {new AccionAsignar("tipo","vacio")}},
/*48. SENTENCIA_IF -> */ {
	/*48.1. if (EXP) RSENTENCIA_IF*/  },
/*49. RSENTENCIA_IF -> */ {
	/*49.1. L_SENTENCIAS SENTENCIA_ELSE*/ {new AccionCondicionada(0,"tipo","igual","error", new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))}},
/*50. SENTENCIA_ELSE -> */ {
	/*50.1. else L_SENTENCIAS */ {new AccionAsignar("tipo",1,"tipo")},
	/*50.2.  ?*/ {new AccionAsignar("tipo","vacio")}},
	
/*51. SENTENCIA_BUCLE -> */			{
	/*51.1. do L_SENTENCIAS while(EXP) */	{new AccionCondicionada(4,"tipo","distinto",new OperandoCrearTipo("null",0),new Accion[]{new AccionAsignar("tipo",1,"tipo")},new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla 51.1: La condición no puede ser vacía."))})},
	/*51.2.  while(EXP) L_SENTENCIAS */		{new AccionCondicionada(2,"tipo","distinto",new OperandoCrearTipo("null",0),new Accion[]{new AccionAsignar("tipo",4,"tipo")},new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla 51.2: La condición no puede ser vacía."))})},
	/*51.3.  for(CAMPO;CAMPO;CAMPO) L_SENTENCIAS*/	{new AccionCondicionada(2,"tipo","distinto",new OperandoCrearTipo("null",0),new Accion[] {new AccionAsignar("tipo",4,"tipo")},new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla 51.3: Los campos del for no pueden ser vacíos."))})}
	},
/*52. CAMPO  -> */	{
	/*52.1. ? */			{new AccionAsignar("tipo","vacio")},
	/*52.2.   EXP*/		{new AccionAsignar("tipo",0,"tipo")}},
/*53. SENTENCIA_CASE -> */	{
	/*53.1. switch (EXP) L_CASES*/	{new AccionCondicionada(2,"tipo","igual",new OperandoCrearTipo("int",0),new Accion[]{ new AccionAsignar("tipo",4,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoDirecto("Regla 53.1: El tipo del switch debe de ser entero."))})}
							},
/*54. L_CASES  -> */ {
	/*54.1. CASE */	{new AccionAsignar("tipo",0,"tipo")},
	/*54.2.  {CASES}*/	{new AccionAsignar("tipo",1,"tipo")}},
/*55. CASES  ->  */	{
	/*55.1. case EXP_COND: RCASES */	{new AccionCondicionada(1,"tipo","igual",new OperandoCrearTipo("int",0), new Accion[]{new AccionCondicionada(1,"esConstante","igual","true", new Accion[]{new AccionAsignar("tipo",3,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 58.1: Después del case debe venir una constante"))})},new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 58.1: Después del case debe venir una constante"))})},
	/*55.2.  default: RCASES2 */	{new AccionAsignar("tipo",2,"tipo")},
	/*55.3.  ?*/		{new AccionAsignar("tipo","vacio")}},
/*56. RCASES  ->  */{
	/*56.1. BLOQUE_SENTENCIAS CASES */	{new AccionCondicionada(0,"tipo","igual","vacio", new AccionAsignar("tipo",1,"tipo"), new AccionAsignar("tipo","error"))},
	/*56.2.  ?*/	{new AccionAsignar("tipo","vacio")}},
/*57. RCASES2  -> */{
	/*57.1.  BLOQUE_SENTENCIAS */	{new AccionAsignar("tipo",0,"tipo")},
	/*57.2.  ?*/	{new AccionAsignar("tipo","vacio")}},
	/*57.3. */ //esta regla no existe
/*58. CASE ->  */{
	/*58.1. case EXP_COND: BLOQUE_SENTENCIAS */	{new AccionCondicionada(1,"tipo","igual",new OperandoCrearTipo("int",0),new Accion[]{new AccionCondicionada(1,"esConstante","igual","true", new Accion[]{new AccionAsignar("tipo",3,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 58.1: Después del case debe venir una constante"))})},new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 58.1: Después del case debe venir una constante"))})},
	/*58.2.  default: BLOQUE_SENTENCIAS*/	{new AccionAsignar("tipo",2,"tipo")}},


/* - - Definicion de Expresiones - -*/

/*59. EXP -> */{
	/*59.1. EXP_COND REXP*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new AccionAsignar("tipo",0,"tipo"))}},
/*60. REXP -> */{
	/*60.1. OP_ASIG EXP */	{new AccionAsignar("tipo",1,"tipo")},
	/*60.2.  ?*/	{new AccionAsignar("tipo","vacio")}},
/*61. OP_ASIG  -> */{
	/*61.1. =  */	{new AccionAsignar("tipo","vacio")},
	/*61.2.  += */	{new AccionAsignar("tipo","vacio")},
	/*61.3.  -= */	{new AccionAsignar("tipo","vacio")},
	/*61.4.  *= */	{new AccionAsignar("tipo","vacio")},
	/*61.5.  /= */	{new AccionAsignar("tipo","vacio")},
	/*61.6.  <<= */	{new AccionAsignar("tipo","vacio")},
	/*61.7.  >>= */	{new AccionAsignar("tipo","vacio")},
	/*61.8.  &= */	{new AccionAsignar("tipo","vacio")},
	/*61.9.  |= */	{new AccionAsignar("tipo","vacio")},
	/*61.10.  ^= */	{new AccionAsignar("tipo","vacio")},
	/*61.11.  %=*/	{new AccionAsignar("tipo","vacio")}},
/*62. EXP_COND  -> */{
	/*62.1. EXP_ORL REXP_COND*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new AccionAsignar("tipo",0,"tipo"), new AccionAsignar("tipo","error"))}},
/*63. REXP_COND ->*/{
	/*63.1.  ? */	{new AccionAsignar("tipo","vacio")},
	/*63.2.  ? EXP : EXP REXP_COND*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(1,"tipo"),new OperandoGramatica(3,"tipo")), new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(1,"tipo"),new OperandoGramatica(4,"tipo")), new AccionAsignar("tipo",1,"tipo"), new AccionAsignar("tipo","error")), new AccionAsignar("tipo","error"))}},
/*64. EXP_ORL  ->  */{
	/*64.1. EXP_ANDL REXP_ORL*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new AccionAsignar("tipo",0,"tipo"))}},
/*65. REXP_ORL -> */{
	/*65.1. |*/		{new AccionAsignar("tipo",1,"tipo")},
	/*65.2.  EXP_ORL */	//esta sobra, va dentro de la 65.1
	/*65.3.  ?*/	{new AccionAsignar("tipo","vacio")}},	
/*66. EXP_ANDL -> */			{
	/*66.1. EXP_ORB REXP_ANDL*/		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 66.1: Los tipos no son compatibles."))})} 
								},
/*67. REXP_ANDL -> */		{
	/*67.1. && EXP_ANDL */		{new AccionAsignar("tipo", 1, "tipo")},
	/*67.2.  ?*/			    {new AccionAsignar("tipo","vacio")} },
/*68. EXP_ORB  -> */			{
	/*68.1. EXP_XORB REXP_ORB*/		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 68.1: Los tipos no son compatibles."))})}
								},
/*69. REXP_ORB ->  */			{
	/*69.1. | EXP_ORB */ 			{new AccionAsignar("tipo", 1, "tipo")},
	/*69.2.  ?*/					{new AccionAsignar("tipo","vacio")} 
								},
/*70. EXP_XORB  ->  */			{
	/*70.1. EXP_ANDB REXP_XORB*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new AccionAsignar("tipo",0,"tipo"))} 
						},
/*71. REXP_XORB -> */			{
	/*71.1. ^ EXP_XORB */			{new AccionAsignar("tipo", 1, "tipo")},
	/*71.2.  ?*/					{new AccionAsignar("tipo","vacio")} 
								},
/*72. EXP_ANDB  ->  */			{
	/*72.1. EXP_REL REXP_ANDB */	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 72.1: Los tipos no son compatibles."))})}
								},
/*73. REXP_ANDB -> */			{
	/*73.1. & EXP_ANDB */			{new AccionAsignar("tipo", 1, "tipo")},
	/*73.2.  ?*/					{new AccionAsignar("tipo","vacio")} 
								},
/*74. EXP_REL -> */				{
	/*74.1. EXP_COMP REXP_REL*/		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 74.1: Los tipos no son compatibles."))})}
								},
/*75. REXP_REL -> */			{
	/*75.1. OP_REL EXP_REL */		{new AccionAsignar("tipo", 1, "tipo")},
	/*75.2.  ?*/					{new AccionAsignar("tipo","vacio")}
								},
/*76. OP_REL -> */				{
	/*76.1. != */					{new AccionAsignar("tipo","vacio")},
	/*76.2.  == */					{new AccionAsignar("tipo","vacio")}
								},
/*77. EXP_COMP -> */			{
	/*77.1. EXP_DESPL  REXP_COMP */	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 77.1: Los tipos no son compatibles."))})}
								},
/*78. REXP_COMP -> */			{
	/*78.1. OP_COMP   EXP_COMP  */	{new AccionAsignar("tipo", 1, "tipo")},
	/*78.2.   ?*/					{new AccionAsignar("tipo","vacio")}
								},
/*79. OP_COMP ->   */			{
	/*79.1. < */					{new AccionAsignar("tipo","vacio")},
	/*79.2. <= */					{new AccionAsignar("tipo","vacio")},
	/*79.3.  > */					{new AccionAsignar("tipo","vacio")},
	/*79.4.  >= */					{new AccionAsignar("tipo","vacio")}
								},
/*80. EXP_DESPL -> */			{
	/*80.1. EXP_AD REXP_DESPL*/		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 80.1: Los tipos no son compatibles."))})}
								},
/*81. REXP_DESPL -> */			{
	/*81.1. OP_DESPL  EXP_DESPL */	{new AccionAsignar("tipo", 1, "tipo")},
	/*81.2.  ?*/					{new AccionAsignar("tipo","vacio")}
								},
/*82. OP_DESPL ->  */			{
	/*82.1.  >> */					{new AccionAsignar("tipo","vacio")},
	/*82.2.  << */					{new AccionAsignar("tipo","vacio")}
								},
/*83. EXP_AD  ->  */			{
	/*83.1. EXP_MULT REXP_AD*/		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 83.1: Los tipos no son compatibles."))})}
								},
/*84. REXP_AD  ->  */			{
	/*84.1. OP_AD EXP_AD */			{new AccionAsignar("tipo", 1, "tipo")},
	/*84.2.  ?*/					{new AccionAsignar("tipo","vacio")}
								},
								
/*85. OP_AD  ->   */{
	/*85.1. + */						{new AccionAsignar("tipo","vacio")},
	/*85.2.  -*/						{new AccionAsignar("tipo","vacio")}},
/*86. EXP_MULT -> */{
	/*86.1. EXP1 REXP_MULT*/	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoDirecto("Regla 86.1: Los tipos no son copatibles."))})}},
/*87. REXP_MULT -> */{
	/*87.1. OP_MULT EXP_MULT */			{new AccionAsignar("tipo",1,"tipo")},
	/*87.2.  ?*/						{new AccionAsignar("tipo","vacio")}},
/*88. OP_MULT ->  */{
	/*88.1. * */						{new AccionAsignar("tipo","vacio")},
	/*88.2.  / */						{new AccionAsignar("tipo","vacio")},
	/*88.3.  %*/						{new AccionAsignar("tipo","vacio")}},
/*89. EXP1 ->   */{
	/*89.1. EXP2 */						{new AccionAsignar("tipo",0,"tipo")},
	/*89.2.  OP_UNARIOS EXP1 */			{new AccionAsignar("tipo",1,"tipo")},
	/*89.3.  sizeof(EXP1)*/				{new AccionAsignar("tipo",2,"tipo")}},
/*90. OP_UNARIOS  ->  */{
	/*90.1. ! */						{new AccionAsignar("tipo","vacio")},
	/*90.2.  ~ */						{new AccionAsignar("tipo","vacio")},
	/*90.3.  * */						{new AccionAsignar("tipo","vacio")},
	/*90.4.  & */						{new AccionAsignar("tipo","vacio")},
	/*90.5. OP_AD*/						{new AccionAsignar("tipo",0,"tipo")},
	/*90.6.  OP_INC*/					{new AccionAsignar("tipo",0,"tipo")}},
/*91. OP_INC ->   */{
	/*91.1. ++ */						{new AccionAsignar("tipo","vacio")},
	/*91.2.  --*/						{new AccionAsignar("tipo","vacio")}},
/*92. EXP2 ->  */{
	/*92.1. EXP3  REXP2*/				{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 92.1: Los tipos no son compatibles."))})}},
/*93. REXP2  -> */{
	/*93.1. OP_INC */					{new AccionAsignar("tipo",0,"tipo")},
	/*93.2.  OP_SELECCION EXP3 */		{new AccionAsignar("tipo",0,"tipo")},
	/*93.3.  ?*/						{new AccionAsignar("tipo","vacio")}},
/*94. OP_SELECCION  ->    */{
	/*94.1. . */						{new AccionAsignar("tipo","vacio")},
	/*94.2.  ->*/						{new AccionAsignar("tipo","vacio")}},
/*95. EXP3 -> */{
	/*95.1. IDENTIFICADOR */			{new AccionAsignar("tipo",0,"tipo")},
	/*95.2.  entero */					{new AccionAsignar("tipo",new OperandoCrearTipo("int",0))},
	/*95.3. real */						{new AccionAsignar("tipo",new OperandoCrearTipo("float",0))},
	/*95.4.  ( REXP3 */					{new AccionAsignar("tipo",1,"tipo")},
	/*95.5.  NULL */					{new AccionAsignar("tipo","vacio")},
	/*95.6.  { LISTA_EXP} */			{new AccionAsignar("tipo",1,"tipo")},
	/*95.7.  comillasChar */			{new AccionAsignar("tipo",new OperandoCrearTipo("char",0))},
	/*95.8.  ENTRECOMILLADO*/			{new AccionAsignar("tipo",0,"tipo")}},
/*96. LISTA_EXP ->  */{
	/*96.1.  ? */						{new AccionAsignar("tipo","vacio")},
	/*96.2.  EXP , LISTA_EXP*/			{new AccionCondicionada(0,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 96.2: Los tipos no son compatibles."))})}},

/*97. REXP3 ->  */{
	/*97.1. TIPO_PRIMITIVO INDIRECCION) EXP */			{/*sePuedeHacerCasting*/},
	/*97.2.  EXP_SIN_IDEN REXP) */						{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoDirecto("Regla 97.2: Los tipos no son compatibles."))})},
	/*97.3.  MODIFICADOR L_MODIFICADORES RTIPO) EXP */	{/*sePuedeHacerCasting*/},
	/*97.4.  iden REXP3_2 ) AUX*/						{/*esTipoDefinidoPorUsuario, esCompatible, esMayor*/new AccionCondicionada(/*si*/1,"esCasting","igual","true",/*entonces0*/new AccionCondicionada(/*si1*/4,"tipo","igual","vacio",/*entonces1*/new AccionAsignar("tipo",1,"tipo"),/*si no1*/new AccionCondicionada(/*si2*/4,"tipo","igual","error",/*entonces2*/new AccionAsignar("tipo",1,"tipo"),/*si no2*/new AccionAsignar("tipo","error"))),/*si no0*//*esCompatible*/null)}},
/*98. EXP_SIN_IDEN -> */{
	/*98.1. & INDIRECCION iden RIDEN */	{/*puntero-TS*/},
	/*98.2.  INDIRECCION2 iden RIDEN */	{/*puntero-TS*/},
	/*98.3.  entero */					{new AccionAsignar("tipo",new OperandoCrearTipo("int",0))},
	/*98.4. real */						{new AccionAsignar("tipo",new OperandoCrearTipo("float",0))},
	/*98.5.  ( REXP3 */					{new AccionAsignar("tipo",1,"tipo")},
	/*98.6.  NULL */					{new AccionAsignar("tipo","vacio")},
	/*98.7.  { LISTA_EXP} */			{new AccionAsignar("tipo",1,"tipo")},
	/*98.8.  comillasChar */			{new AccionAsignar("tipo",new OperandoCrearTipo("char",0))},
	/*98.9.  ENTRECOMILLADO*/			{new AccionAsignar("tipo",0,"tipo")}},
/*99. REXP3_2 -> */{
	/*99.1. INDIRECCION2 */				{new AccionAsignar("esCasting","true"),new AccionAsignar("tipo","vacio"),new AccionAsignar("num",0,"num")},
	/*99.2.  RIDEN REXPRESIONES*/		{new AccionAsignar("tipo",1,"tipo"),new AccionAsignar("esCasting","false")}},
/*100. REXPRESIONES -> */{
	/*100.1. REXP */					{new AccionAsignar("tipo",0,"tipo")},
	/*100.2.  REXP_COND */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.3.  REXP_ORL */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.4.  REXP_ANDL */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.5.  REXP_ORB */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.6.  REXP_XORB */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.7.  REXP_ANDB */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.8.  REXP_REL */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.9.  REXP_COMP */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.10.  REXP_DESPL */			{new AccionAsignar("tipo",0,"tipo")},
	/*100.11.  REXP_AD */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.12.  REXP_MULT */				{new AccionAsignar("tipo",0,"tipo")},
	/*100.13.  OP_SELECCION EXP3 */		{new AccionAsignar("tipo",1,"tipo")},
	/*100.14.  OP_INC*/					{new AccionAsignar("tipo","vacío"),new AccionAsignar("op",0,"op")}},
/*101. INDIRECCION2 -> */{
	/*101.1. * INDIRECCION*/			{new AccionAsignar("num",0,"num","suma",1)}},
/*102. L_PARAMS_LLAMADA -> */{
	/*102.1.  EXP  RES_LISTA_PARAMS_LLAMDA */	{/*TS*/},
	/*102.2.  ?*/								{new AccionAsignar("tipo","vacío"),new AccionAsignar("posicionArgumento",0)}},
/*103. RES_LISTA_PARAMS_LLAMADA ->  */{
	/*103.1. ,EXP  RES_LISTA_PARAMS_LLAMADA */	{/*TS*/new AccionCondicionada(0,"tipo","igual","vacio",new AccionAsignar("tipo",1,"tipo"),new AccionAsignar("tipo","error"))},
	/*103.2.  ?*/								{new AccionAsignar("tipo","vacío"),new AccionAsignar("posicionArgumento",0)}},
/*104. AUX-> */{
	/*104.1. EXP */								{new AccionAsignar("tipo",0,"tipo")},
	/*104.2.  ?*/								{new AccionAsignar("tipo","vacio")}},
/*105. REXP4 -> */{
	/*105.1. TIPO_PRIMITIVO INDIRECCION  RDEFINICION */											{new AccionCondicionada(0, "estaEnTS",null, 2,"lexema",new AccionAsignar("tipo","error"),new AccionAsignar("tipo",0,"tipo"))},
	/*105.2.  EXP_SIN_IDEN REXP; */																{new AccionCondicionada(0,"esConstante","false",new AccionCondicionada(1,"esAsignacion","true",new AccionAsignar("tipo","error"),new AccionAsignar("tipo","vacio")),new AccionAsignar("tipo","error"))},
	/*105.3.  MODIFICADOR L_MODIFICADORES RTIPO RDEFINICION */									{/*new AccionCondicionada(2,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada("estaEnTS",3,"lexema",new AccionAsignar("tipo","error"), new Action[] {new AccionAsignar("tipo","vacio"),new AccionAsignar("tipo",terminar,sdffdw,"tipo")}))}*/},
	/*105.4.  iden REXP3_2 RDEFINICION  // Definir variable con tipo definido por el usuario*/	{/*TS*/},
	/*105.5.  OP_INC IDENTIFICADOR*/															{}}

	
	};
}

