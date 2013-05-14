package analizadorSemantico;

import acciones.*;
import accionesEspecificas.AccionR105_1;
import accionesEspecificas.AccionR105_4;
import accionesEspecificas.AccionR15_1;
import accionesEspecificas.AccionR25_1;
import accionesEspecificas.AccionR28_2;
import accionesEspecificas.AccionR3_2;

public class AccionesTipos {
	
	

	static Accion[][][] acciones={
/* - - Definicion de Programa - -*/

/*1. PROGRAMA  ->*/					{
	/*1.1.  L_DEFINICIONES EOF */ 		{},
	/*1.2. EOF*/						{}
									},

/*2. L_DEFINICIONES ->  */			{
	/*2.1. DEFINICION_GLOBAL  L_DEFINICIONES*/	{},
	/*2.2 ? */									{}
										},

/*3. DEFINICION_GLOBAL -> */		{
	/*3.1. MACROS */						{},
	/*3.2. TIPO RDEFINICION*/				{new AccionR3_2()},
												
	/*3.3.  DEFINICION_STRUCT */			{},
	/*3.4. DEFINICION_ENUM */				{},
	/*3.5. DEFINICION_UNION */				{},
	/*3.6.  DEFINICION_TYPEDEF*/			{}
										},
	
/*4. RDEFINICION -> */				{
	/*4.1. ;  */                    	{new AccionAsignar("esDeclaracion",false)}, 
	/*4.2. iden RDEFINICION2*/      	{new AccionAsignar("esDeclaracion",true),new AccionCondicionada(1,"esFuncion","igual",true,
											new AccionAsignar ("nombreFun",0,""),
											new AccionAsignar("listaVar",new OperacionAgregarALista(new OperandoGramatica(1,"listaVar"),new OperandoGramatica(0,"")))
										)
										}
									},
	
/*5. RDEFINICION2 -> */					{
	/*5.1. CORCHETES RDEF_VARIABLE */		{new AccionAsignar ("esFuncion",false)},
	/*5.2. M_AMBITO(L_PARAMS) RDEF_FUNCION*/		{new AccionAsignar ("esFuncion",true),
	/*Abre el ambito siempre*/						 new AccionCondicionada(2,"esPrototipo","distinto",4,"esPrototipo",new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 5:2 no coincide, definición de prototipo con los parametros de la funcion.")),
													 new AccionCerrarAmbito()
											}
										},
							
/*6. RDEF_VARIABLE -> */				{ 
	/*6.1. OP_ASIG EXP RDEF_VARIABLE2 */	{new AccionAsignar("listaValor",new OperacionAgregarALista(new OperandoGramatica(2,"listaValor"),new OperandoGramatica(1,"tipo")))},
																						
	/*6.2. RDEF_VARIABLE2*/					{new AccionAsignar("listaValor",new OperacionAgregarALista(new OperandoGramatica(0,"listaValor"),new OperandoDirecto(null)))}},  
									
									
											
/*7. RDEF_VARIABLE2 -> */			{
	/*7.1. , iden RDEF_VARIABLE */		{new AccionAsignar("listaVar",new OperacionAgregarALista(new OperandoGramatica(2,"listaVar"),new OperandoGramatica(1,"")))},
	/*7.2.  ;*/							{new AccionAsignar("listaVar",new OperandoCrearArrayList()),new AccionAsignar("listaValor",new OperandoCrearArrayList())},
									},

/* - - Definicion de Macros - -*/

/*8. MACROS -> */					{
	/*8.1. # RMACRO*/					{}, 
									},
/*9. RMACRO  -> */						{
	/*9.1. define IDENTIFICADOR EXP */		{},
	/*9.2.  include RINCLUDE */				{},
	/*9.3.  ifdef iden RIFDEF #endif */		{},
	/*9.4.  undef iden */					{},
	/*9.5.   ifndef iden */					{},
	/*9.6.  error (caracter)* EOF */		{},
	/*9.7.  pragma (caracter)* EOL */		{},
										},
/*10. RINCLUDE -> */			{
	/*10.1. ENTRECOMILLADO */		{},
	/*10.2.  <iden.iden>*/			{},
								},
/*11. RIFDEF ->  */				{
	/*11.1. # RIFDEF2 RIFDEF */		{},
	/*11.2.  L_SENTENCIAS */		{},
	/*11.3.  ? */					{},
								},
/*12. RIFDEF2 -> */				{
	/*12.1. elif iden RIFDEF*/		{},
	/*12.2. else L_SENTENCIAS */	{},
	/*12.3.  RMACRO*/				{},
								},

/* - - Definicion de Variables - -*/

/*13. DEFINICION_UNION -> */							{
	/*13.1. union  iden  {L_VARIABLES}  LISTA_IDENS*/		{new AccionCondicionada(5,"tipo","igual","listaUnion", 
			                                                 		new AccionCondicionada(3,"tipo","igual","vacio",
			                                                 				new Accion[]{new AccionAsignar("tipo","vacio")},  
			                                                 			new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 13.1: Las variables declaradas beden de ser de tipo Union."))} ), 
			                                                 		new AccionCondicionada(5,"tipo","igual","vacio", 
			                                                 				new AccionCondicionada(3,"tipo","igual","vacio",
			                                                 						new Accion[]{new AccionAsignar("tipo","vacio")},  
			                                                 						new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 13.1: Error en la definición de la unión."))}))), 
},
														},
/*14. LISTA_IDENS -> */						{
	/*14.1. iden CORCHETES RDEF_VARIABLE */		{new AccionAsignar("tipo",2,"tipo")},
	/*14.2.  ;*/								{},
											},
/*15. IDENTIFICADOR -> */								{
	/*15.1. REFERENCIA INDIRECCION iden RIDENTIFICADOR*/	{ new AccionR15_1()}
														},
/*16. RIDENTIFICADOR ->*/		{					
	/*16.1.  CORCHETES */			{new AccionAsignar ("esFuncion",false)},
	/*16.2.  (L_PARAMS_LLAMADA) */	{new AccionAsignar ("esFuncion",true)},
								},
/*17. CORCHETES ->*/ {	
	/*17.1.  ? */    { new AccionAsignar("num",0)},
	/*17.2.  [CONTENIDO] CORCHETES*/ {new AccionAsignar("num",3,"num","suma",1)}},
/*18. CONTENIDO -> */ {
	/*18.1. ? */ {},
	/*18.2.  EXP*/ {new AccionCondicionada (0,"tipo","igual",new Tipo("int",0),new Accion[]{},new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 18:2 La expresion entre [] debe de ser de tipo entero")})}},
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
		/*22.1. struct  iden  {L_VARIABLES}  LISTA_IDENS*/ 		{new AccionCondicionada(5,"tipo","igual","listaStruct", new AccionCondicionada(3,"tipo","igual","vacio", new Accion[]{new AccionAsignar("tipo","vacio")}, new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 22.1: Las variables declaradas beden de ser de tipo Struct."))}),new AccionCondicionada(5,"tipo","igual","vacio", new AccionCondicionada(3,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo","vacio")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 22.1: Error en la definición del Struct.")) } )))}}, 

/*23. L_VARIABLES -> */ {
	/*23.1. DEFINICION_STRUCT L_VARIABLES */ {},
	/*23.2.  DEF_VAR L_VARIABLES */ {},
	/*23.3. DEFINICION_ENUM L_VARIABLES */ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.4.  DEFINICION_UNION L_VARIABLES*/ {new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo",1,"tipo"))},
	/*23.5.  ?*/ {new AccionAsignar("tipo","vacio")}},
	/*24. DEF_VAR ->*/											{
		/*24.1.  TIPO LISTA_IDENS*/    							{new AccionCondicionada(0,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada(0,"tipo","igual",1,"tipo",new AccionAsignar("tipo","vacio"),new AccionCondicionada(1,"tipo","igual","vacio",new Accion[]{new AccionAsignar("tipo","vacio")},new Accion[]{new AccionAsignar("tipo","error"), new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 24.1: Los tipos no coinciden."))})))}},

/*25. DEFINICION_TYPEDEF -> */ {
	/*25.1. typedef RDEF_TYPEDEF*/ {new AccionR25_1()}},
/*26. RDEF_TYPEDEF -> */ {
	/*26.1. struct iden INDIRECCION iden; */ {new AccionCondicionada (new CondicionHeredada(new OperacionClaseEntradaTS(new OperandoGramatica(1,"")),new OperandoDirecto("struct"),"igual"),
												new Accion[]{new AccionAsignar("tipo",new OperandoCrearTipo(new OperandoGramatica(1,""),new OperandoGramatica(2,"num"))), new AccionAsignar("lex",new OperandoGramatica(3,""))},
												new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 26.1: El identificador no está definido o no lo está como struct."))})},
	/*26.2.  TIPO iden; */ {/*tipo subido automaticamente*/new AccionAsignar("lex",new OperandoGramatica(1,""))},
	/*26.3.  union iden INDIRECCION iden; */ {new AccionCondicionada (new CondicionHeredada(new OperacionClaseEntradaTS(new OperandoGramatica(1,"")),new OperandoDirecto("struct"),"igual"),
												new Accion[]{new AccionAsignar("tipo",new OperandoCrearTipo(new OperandoGramatica(1,""),new OperandoGramatica(2,"num"))),new AccionAsignar("lex",new OperandoGramatica(3,""))},
												new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 26.1: El identificador no está definido o no lo está como union."))})},
	/*26.4.  enum iden INDIRECCION iden;*/ {new AccionCondicionada (new CondicionHeredada(new OperacionClaseEntradaTS(new OperandoGramatica(1,"")),new OperandoDirecto("struct"),"igual"),
												new Accion[]{new AccionAsignar("tipo",new OperandoCrearTipo(new OperandoGramatica(1,""),new OperandoGramatica(2,"num"))),new AccionAsignar("lex",new OperandoGramatica(3,""))},
												new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 26.1: El identificador no está definido o no lo está como enum."))})},
							},


/* - - Definicion de Funciones - -*/

/*27. RDEF_FUNCION -> */ {
	/*27.1. ; */ {new AccionAsignar("esPrototipo",true)},
	/*27.2.  {  BLOQUE_SENTENCIAS }*/ {new AccionAsignar("esPrototipo",false)}},
	
/*28. L_PARAMS -> */ {
	/*28.1. ? */ {new AccionAsignar("esPrototipo",false),
					new AccionAsignar("listaTipo",new OperandoCrearArrayList()),
					new AccionAsignar("listaIden",new OperandoCrearArrayList())},
	/*28.2.  TIPO RL_PARAMS*/ {new AccionAsignar("listaTipo",new OperacionAgregarALista(new OperandoGramatica(1,"listaTipo"),new OperandoGramatica(0,"tipo"))),
									new AccionR28_2()}
						},

/*29. RL_PARAMS -> */ {
	/*29.1. ? */ {new AccionAsignar("esPrototipo",true),new AccionAsignar("listaTipo",new OperandoCrearArrayList())},
	/*29.2.   ,TIPO RL_PARAMS2 */ {new AccionAsignar("esPrototipo",true),
								new AccionAsignar("listaTipo",new OperacionAgregarALista(new OperandoGramatica(2,"listaTipo"),new OperandoGramatica(1,"tipo"))),

							},
	/*29.3.  iden RL_PARAMS3*/ {new AccionAsignar("esPrototipo",false),	
							new AccionAsignar("listaIden",new OperacionAgregarALista(new OperandoGramatica(1,"listaIden"),new OperandoGramatica(0,"")))

							}
					},
/*30. RL_PARAMS2 -> */ {
	/*30.1. ? */ {
					new AccionAsignar("listaTipo",new OperandoCrearArrayList()),
				},
	/*30.2.   ,TIPO RL_PARAMS2*/ {
									new AccionAsignar("listaTipo",new OperacionAgregarALista(new OperandoGramatica(2,"listaTipo"),new OperandoGramatica(1,"tipo"))),
									}
						},
					//new AccionCondicionada(1,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada(2,"tipo","igual","error",new AccionAsignar("tipo","error"), new AccionAsignar("tipo","vacio")))}},
/*31. RL_PARAMS3 -> */ {
	/*31.1. ? */ {
				new AccionAsignar("listaTipo",new OperandoCrearArrayList()),
				new AccionAsignar("listaIden",new OperandoCrearArrayList())
				},
	/*31.2.   ,TIPO iden RL_PARAMS3*/ {
										new AccionAsignar("listaTipo",new OperacionAgregarALista(new OperandoGramatica(3,"listaTipo"),new OperandoGramatica(1,"tipo"))),
										new AccionAsignar("listaIden",new OperacionAgregarALista(new OperandoGramatica(3,"listaIden"),new OperandoGramatica(2,"")))

										}
						},

/* - - Definicion de Tipos - -*/

/*32. TIPO -> */ {
	/*32.1. L_MODIFICADORES RTIPO*/ {}},
/*33. RTIPO -> */ {
	/*33.1. TIPO_PRIMITIVO INDIRECCION */	{new AccionAsignar("tipo",new OperacionSumarDimTipo(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"num")))},
	/*33.2.  iden INDIRECCION*/ 			{new AccionCondicionada
												(new CondicionHeredada(new OperacionClaseEntradaTS(new OperandoGramatica(0,"")),new OperandoDirecto("typedef"),"igual"),
												new Accion[]{new AccionAsignar("tipo",new OperandoCrearTipo(new OperandoGramatica(0,""),new OperandoGramatica(1,"num")))},
												new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 33.2: El tipo no existe en la TS."))})}
					},
/*34. INDIRECCION -> */ {
	/*34.1. * INDIRECCION */ {new AccionAsignar("num",new OperacionHeredada(new OperandoGramatica(1,"num"),new OperandoDirecto(1),"suma"))},
	/*34.2.  ?*/ {new AccionAsignar("num",0)}},
/*35. L_MODIFICADORES -> */ {
	/*35.1. MODIFICADOR L_MODIFICADORES */ {},
	/*35.2.  ?*/ {}
							},
/*36. MODIFICADOR ->*/ {
	/*36.1.  auto */ {},
	/*36.2.  volatile */ {},
	/*36.3.  register */ {},
	/*36.4.  extern*/ {},
	/*36.5. const */ {},
	/*36.6.  unsigned */ {},
	/*36.7.  signed */ {},
	/*36.8.  static*/ {}},
/*37. TIPO_PRIMITIVO -> */ {
	/*37.1. void */ {new AccionAsignar("tipo",new OperandoCrearTipo("void",0))},
	/*37.2. int */ {new AccionAsignar("tipo",new OperandoCrearTipo("int",0))},
	/*37.3. char */ {new AccionAsignar("tipo",new OperandoCrearTipo("char",0))},
	/*37.4.  float */ {new AccionAsignar("tipo",new OperandoCrearTipo("float",0))},
	/*37.5. double*/ {new AccionAsignar("tipo",new OperandoCrearTipo("double",0))}},


/* - - Definicion de Sentencias - -*/

/*38. L_SENTENCIAS -> */ {
	/*38.1. { M_AMBITO BLOQUE_SENTENCIAS} */ {new AccionCerrarAmbito()},
	/*38.2.  M_AMBITO SENTENCIA*/ {new AccionCerrarAmbito()}},
/*39. BLOQUE_SENTENCIAS  ->*/ {
	/*39.1.  SENTENCIA BLOQUE_SENTENCIAS */ {new AccionCondicionada(0,"listaTipoRet","distinto",null, 
												new AccionAsignar("listaTipoRet",new OperacionAgregarALista(new OperandoGramatica(1,"listaTipoRet"),new OperandoGramatica(0,"listaTipoRet"))),
												new AccionCondicionada(0,"tipo","distinto",null, 
																new AccionAsignar("listaTipoRet",new OperacionAgregarALista(new OperandoGramatica(1,"listaTipoRet"),new OperandoGramatica(0,"tipo")))
												)
											)},
	/*39.2. { M_AMBITO BLOQUE_SENTENCIAS} */ {new AccionCerrarAmbito()},
	/* 39.3. ?*/ {new AccionAsignar("listaTipoRet",new OperandoCrearArrayList())}},
/*40. SENTENCIA ->  */ {
	/*40.1.  ; */ {},
	/*40.2.  SENTENCIA_IF */ {},
	/*40.3.  SENTENCIA_BUCLE */ {},
	/*40.4.  REXP4 */ {},
	/*40.5.  SENTENCIA_CASE */ {},
	/*40.6.  OTRAS_SENTENCIAS ; */ {},
	/*40.7.  MACROS */ {},
	/*40.8.  DEFINICION_STRUCT */ {},
	/*40.9.  DEFINICION_ENUM */ {},
	/*40.10.  DEFINICION_UNION */ {},
	/*40.11.  DEFINICION_TYPEDEF*/ {}},
/*41. OTRAS_SENTENCIAS  -> */ {
	/*41.1. break */ {},
	/*41.2.  continue */ {},
	/*41.3.  printf(EXP) */ {},
	/*41.4.  scanf(iden) */ {},
	/*41.5.  return EXP*/ {}},

/*42. ENTRECOMILLADO -> */ {
	/*42.1. “(caracter)*” */ {new AccionAsignar("tipo",new OperandoCrearTipo("char",1))}},
/*44. REFERENCIA -> */ {
	/*44.1. & */ { new AccionAsignar("tieneAmp",true)},
	/*44.2.   ?*/ {new AccionAsignar("tieneAmp",false)}},
/*48. SENTENCIA_IF -> */ {
	/*48.1. if (EXP) RSENTENCIA_IF*/  {new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(2,"tipo"),new OperandoCrearTipo("int",0)),new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 48.1 Tipo en la condición inválido. ")})} 
						},
/*49. RSENTENCIA_IF -> */ {
	/*49.1. L_SENTENCIAS SENTENCIA_ELSE*/ {}},
/*50. SENTENCIA_ELSE -> */ {
	/*50.1. else L_SENTENCIAS */ {},
	/*50.2.  ?*/ {}},
	
/*51. SENTENCIA_BUCLE -> */			{
	/*51.1. do L_SENTENCIAS while(EXP) */	{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(4,"tipo"),new OperandoCrearTipo("int",0)),new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 51.1 Tipo en la condición inválido. ")})},
	/*51.2.  while(EXP) L_SENTENCIAS */		{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(2,"tipo"),new OperandoCrearTipo("int",0)),new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 51.2 Tipo en la condición inválido. ")})},
	/*51.3.  for(CAMPO;CAMPO;CAMPO) L_SENTENCIAS*/	{/*Los campos del for sí pueden ser vacíos*/}
									},
/*52. CAMPO  -> */	{
	/*52.1. ? */			{},
	/*52.2.   EXP*/		{}},
/*53. SENTENCIA_CASE -> */	{
	/*53.1. switch (EXP) M_AMBITO L_CASES*/	{new AccionCondicionada(new CondicionHeredada(
													new CondicionHeredada(new OperandoGramatica(2,"tipo"),new OperandoCrearTipo("int",0),"igual"),
													new CondicionHeredada(new OperandoGramatica(2,"tipo"),new OperandoCrearTipo("char",0),"igual"),"or"),
														new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 53.1 Tipo en el switch inválido. ")}), 
														new AccionCerrarAmbito()}
							},
/*54. L_CASES  -> */ {
	/*54.1. CASE */	{},
	/*54.2.  {CASES}*/	{}},
/*55. CASES  ->  */	{
	/*55.1. case EXP_COND: RCASES */	{new AccionCondicionada(1,"esConstante","igual",true,
											new AccionCondicionada(new CondicionHeredada(
													new CondicionHeredada(new OperandoGramatica(1,"tipo"),new OperandoCrearTipo("int",0),"igual"),
													new CondicionHeredada(new OperandoGramatica(1,"tipo"),new OperandoCrearTipo("char",0),"igual"),"or"),
														new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 55.1 Tipo en el case inválido. ")}),
														new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 55.1 Se esperaba una constante. "))},
										
	/*55.2.  default: RCASES2 */	{},
	/*55.3.  ?*/		{}},
/*56. RCASES  ->  */{
	/*56.1. BLOQUE_SENTENCIAS CASES */	{},
	/*56.2.  ?*/	{}},
/*57. RCASES2  -> */{
	/*57.1.  BLOQUE_SENTENCIAS */	{},
	/*57.2.  ?*/	{}},
	/*57.3. */ //esta regla no existe
/*58. CASE ->  */{
	/*58.1. case EXP_COND: BLOQUE_SENTENCIAS */	{new AccionCondicionada(1,"esConstante","igual",true,
													new AccionCondicionada(new CondicionHeredada(
															new CondicionHeredada(new OperandoGramatica(1,"tipo"),new OperandoCrearTipo("int",0),"igual"),
															new CondicionHeredada(new OperandoGramatica(1,"tipo"),new OperandoCrearTipo("char",0),"igual"),"or"),
																new Accion[]{},new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 58.1 Tipo en el case inválido. ")}),
															new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 58.1 Se esperaba una constante. "))},
	/*58.2.  default: BLOQUE_SENTENCIAS*/	{}},


/* - - Definicion de Expresiones - -*/

/*59. EXP -> */{
	/*59.1. EXP_COND REXP*/	{
						new AccionCondicionada(1,"tipo","distinto",null, //Hacemos está condición para que si la parte "derecha" de es vacia, interprete la parte izquierda como una parte derecha. (Se podría hacer en todas, pero aquí es obligatorio por la asignación).
							new AccionCondicionada(
								new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
								new AccionCondicionada(0,"esConstante","igual",false,
									new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo"))),
									new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 59.1 El termino a la izquierda de la asignación no puede ser constante. ")),
								new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 59.1 Los tipos no son compatibles. ")
								)),
								
							new AccionCondicionada(
								new CondicionHeredada(
										new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
										,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
								new AccionAsignar("esConstante",0,"esConstante"),
								new AccionAsignar("esConstante",false))}},
/*60. REXP -> */{
	/*60.1. OP_ASIG EXP */	{new AccionAsignar("tipo",1,"tipo")},
	/*60.2.  ?*/	{}},
/*61. OP_ASIG  -> */{
	/*61.1. =  */	{},
	/*61.2.  += */	{},
	/*61.3.  -= */	{},
	/*61.4.  *= */	{},
	/*61.5.  /= */	{},
	/*61.6.  <<= */	{},
	/*61.7.  >>= */	{},
	/*61.8.  &= */	{},
	/*61.9.  |= */	{},
	/*61.10.  ^= */	{},
	/*61.11.  %=*/	{}},
/*62. EXP_COND  -> */{
	/*62.1. EXP_ORL REXP_COND*/	{new AccionCondicionada(
			new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
			new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
			new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 64.1 Los tipos no son compatibles. ")}
),
new AccionCondicionada(
new CondicionHeredada(
new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
new AccionAsignar("esConstante",0,"esConstante"),
new AccionAsignar("esConstante",false))}},
/*63. REXP_COND ->*/{
	/*63.1.  ? EXP : EXP REXP_COND*/	{/*rellenar*/},
	/*63.2.  ? */	{}
					},
/*64. EXP_ORL  ->  */{
	/*64.1. EXP_ANDL REXP_ORL*/	{new AccionCondicionada(
									new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
									new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
									new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 64.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*65. REXP_ORL -> */{
	/*65.1. |EXP_ORL*/		{new AccionAsignar("tipo",1,"tipo")},
	/*65.2.  ?*/	{}},	
/*66. EXP_ANDL -> */			{
	/*66.1. EXP_ORB REXP_ANDL*/		{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 66.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*67. REXP_ANDL -> */		{
	/*67.1. && EXP_ANDL */		{new AccionAsignar("tipo", 1, "tipo")},
	/*67.2.  ?*/			    { }},
/*68. EXP_ORB  -> */			{
	/*68.1. EXP_XORB REXP_ORB*/		{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 68.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*69. REXP_ORB ->  */			{
	/*69.1. | EXP_ORB */ 			{new AccionAsignar("tipo", 1, "tipo")},
	/*69.2.  ?*/					{} 
								},
/*70. EXP_XORB  ->  */			{
	/*70.1. EXP_ANDB REXP_XORB*/	{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 70.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*71. REXP_XORB -> */			{
	/*71.1. ^ EXP_XORB */			{new AccionAsignar("tipo", 1, "tipo")},
	/*71.2.  ?*/					{} 
								},
/*72. EXP_ANDB  ->  */			{
	/*72.1. EXP_REL REXP_ANDB */	{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 72.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*73. REXP_ANDB -> */			{
	/*73.1. & EXP_ANDB */			{new AccionAsignar("tipo", 1, "tipo")},
	/*73.2.  ?*/					{} 
								},
/*74. EXP_REL -> */				{
	/*74.1. EXP_COMP REXP_REL*/		{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 74.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*75. REXP_REL -> */			{
	/*75.1. OP_REL EXP_REL */		{new AccionAsignar("tipo", 1, "tipo")},
	/*75.2.  ?*/					{}
								}, 
/*76. OP_REL -> */				{
	/*76.1. != */					{},
	/*76.2.  == */					{}
								},
/*77. EXP_COMP -> */			{
	/*77.1. EXP_DESPL  REXP_COMP */	{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 77.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*78. REXP_COMP -> */			{
	/*78.1. OP_COMP   EXP_COMP  */	{new AccionAsignar("tipo", 1, "tipo")},
	/*78.2.   ?*/					{}
								},
/*79. OP_COMP ->   */			{
	/*79.1. < */					{},
	/*79.2. <= */					{},
	/*79.3.  > */					{},
	/*79.4.  >= */					{}
								},
/*80. EXP_DESPL -> */			{
	/*80.1. EXP_AD REXP_DESPL*/		{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 80.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*81. REXP_DESPL -> */			{
	/*81.1. OP_DESPL  EXP_DESPL */	{new AccionAsignar("tipo", 1, "tipo")},
	/*81.2.  ?*/					{}
								},
/*82. OP_DESPL ->  */			{
	/*82.1.  >> */					{},
	/*82.2.  << */					{}
								},
/*83. EXP_AD  ->  */			{
	/*83.1. EXP_MULT REXP_AD*/		{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 83.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*84. REXP_AD  ->  */			{
	/*84.1. OP_AD EXP_AD */			{new AccionAsignar("tipo", 1, "tipo")},
	/*84.2.  ?*/					{}
								},
								
/*85. OP_AD  ->   */{
	/*85.1. + */						{},
	/*85.2.  -*/						{}},
/*86. EXP_MULT -> */{
	/*86.1. EXP1 REXP_MULT*/	{new AccionCondicionada(
										new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
										new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
										new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 86.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},	
/*87. REXP_MULT -> */{
	/*87.1. OP_MULT EXP_MULT */			{new AccionAsignar("tipo",1,"tipo")},
	/*87.2.  ?*/						{}},
/*88. OP_MULT ->  */{
	/*88.1. * */						{},
	/*88.2.  / */						{},
	/*88.3.  %*/						{}},
/*89. EXP1 ->   */{
	/*89.1. EXP2 */						{new AccionAsignar("tipo",0,"tipo")},
	/*89.2.  OP_UNARIOS EXP1 */			{new AccionAsignar("tipo",1,"tipo")},
	/*89.3.  sizeof(EXP1)*/				{new AccionAsignar("tipo",2,"tipo")}},
/*90. OP_UNARIOS  ->  */{
	/*90.1. ! */						{},
	/*90.2.  ~ */						{},
	/*90.3.  * */						{},
	/*90.4.  & */						{},
	/*90.5. OP_AD*/						{},
	/*90.6.  OP_INC*/					{}},
/*91. OP_INC ->   */{
	/*91.1. ++ */						{},
	/*91.2.  --*/						{}},
/*92. EXP2 ->  */{
	/*92.1. EXP3  REXP2*/				{/*rellenar- aqui no es esCompatible*/ new AccionCondicionada(
											new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")),
											new Accion[]{new AccionAsignar("tipo",new OperacionCompatibilizarTipos(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")))},
											new Accion[]{new AccionAsignar("error",true),new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"regla 92.1 Los tipos no son compatibles. ")}
			),
			new AccionCondicionada(
				new CondicionHeredada(
						new CondicionHeredada(new OperandoGramatica(0,"esConstante"),new OperandoDirecto(false),"distinto")
						,new CondicionHeredada(new OperandoGramatica(1,"esConstante"),new OperandoDirecto(false),"distinto"),"and"),
				new AccionAsignar("esConstante",0,"esConstante"),
				new AccionAsignar("esConstante",false))}},
/*93. REXP2  -> */{
	/*93.1. OP_INC */					{new AccionAsignar("esInc",true)},
	/*93.2.  OP_SELECCION EXP3 */		{new AccionAsignar("esInc",false)},
	/*93.3.  ?*/						{}},
/*94. OP_SELECCION  ->    */{
	/*94.1. . */						{new AccionAsignar("esPunto",true)},
	/*94.2.  ->*/						{new AccionAsignar("esPunto",false)}},
/*95. EXP3 -> */{
	/*95.1. IDENTIFICADOR */			{new AccionAsignar("tipo",0,"tipo"),new AccionAsignar("esConstante",false)},
	/*95.2.  entero */					{new AccionAsignar("tipo",new OperandoCrearTipo("int",0)),new AccionAsignar("esConstante",true)},
	/*95.3. real */						{new AccionAsignar("tipo",new OperandoCrearTipo("float",0)),new AccionAsignar("esConstante",true)},
	/*95.4.  ( REXP3 */					{new AccionAsignar("esConstante",false)},
	/*95.5.  NULL */					{new AccionAsignar("tipo","null"),new AccionAsignar("esConstante",true)},
	/*95.6.  { LISTA_EXP} */			{new AccionAsignar("esConstante",false)},
	/*95.7.  comillasChar */			{new AccionAsignar("tipo",new OperandoCrearTipo("char",0)),new AccionAsignar("esConstante",true)},
	/*95.8.  entrecomillado*/			{new AccionAsignar("tipo",new OperandoCrearTipo("char",1)),new AccionAsignar("esConstante",false)}},
/*96. LISTA_EXP ->  */{
	/*96.1.  ? */						{},
	/*96.2.  EXP , LISTA_EXP*/			{}
						},

/*97. REXP3 ->  */{
	/*97.1. TIPO_PRIMITIVO INDIRECCION) EXP */			{/*rellenar*//*sePuedeHacerCasting*/},
	/*97.2.  EXP_SIN_IDEN REXP) */						{new AccionCondicionada(new CondicionEsCompatible(new OperandoGramatica(0,"tipo"),new OperandoGramatica(1,"tipo")), new Accion[]{new AccionAsignar("tipo",0,"tipo")}, new Accion[]{new AccionAsignar("tipo","error"),new AccionGenError (new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),new OperandoDirecto("Regla 97.2: Los tipos no son compatibles."))})},
	/*97.3.  MODIFICADOR L_MODIFICADORES RTIPO) EXP */	{/*rellenar*//*sePuedeHacerCasting*/},
	/*97.4.  iden REXP3_2 ) AUX*/						{/*rellenar*//*esTipoDefinidoPorUsuario, esCompatible, esMayor*/new AccionCondicionada(/*si*/1,"esCasting","igual","true",/*entonces0*/new AccionCondicionada(/*si1*/4,"tipo","igual","vacio",/*entonces1*/new AccionAsignar("tipo",1,"tipo"),/*si no1*/new AccionCondicionada(/*si2*/4,"tipo","igual","error",/*entonces2*/new AccionAsignar("tipo",1,"tipo"),/*si no2*/new AccionAsignar("tipo","error"))),/*si no0*//*esCompatible*/null)}},
/*98. EXP_SIN_IDEN -> */{
	/*98.1. & INDIRECCION iden RIDENTIFICADOR */	{/*rellenar*//*puntero-TS*/new AccionAsignar("esConstante",false)},
	/*98.2.  INDIRECCION2 iden RIDENTIFICADOR */	{/*rellenar*//*puntero-TS*/new AccionAsignar("esConstante",false)},
	/*98.3.  entero */					{new AccionAsignar("tipo",new OperandoCrearTipo("int",0)),new AccionAsignar("esConstante",true)},
	/*98.4. real */						{new AccionAsignar("tipo",new OperandoCrearTipo("float",0)),new AccionAsignar("esConstante",true)},
	/*98.5.  ( REXP3 */					{new AccionAsignar("esConstante",false)},
	/*98.6.  NULL */					{new AccionAsignar("tipo","null"),new AccionAsignar("esConstante",true)},
	/*98.7.  { LISTA_EXP} */			{new AccionAsignar("esConstante",false)},
	/*98.8.  comillasChar */			{new AccionAsignar("tipo",new OperandoCrearTipo("char",0)),new AccionAsignar("esConstante",true)},
	/*98.9.  ENTRECOMILLADO*/			{new AccionAsignar("esConstante",false)}},
/*99. REXP3_2 -> */{
	/*99.1. INDIRECCION2 */				{new AccionAsignar("esOperacion",false)},
	/*99.2.  RIDENTIFICADOR REXPRESIONES*/		{new AccionAsignar("esOperacion",true),new AccionAsignar ("num",0,"num"),new AccionAsignar("esFuncion",0,"esFuncion")}}, //Forzamos a esFuncion y numCorchetes del 0 porque también existe un esFuncion y numCorchetes en el 1.
/*100. REXPRESIONES -> */{
	/*100.1. REXP */					{new AccionAsignar("esOperacion",true)}, //Para la decision en la regla 105.4. (cuando esOperación deba ser false simplemente no lo asignamos al hashmap).
	/*100.2.  REXP_COND */				{new AccionAsignar("esOperacion",true)},
	/*100.3.  REXP_ORL */				{new AccionAsignar("esOperacion",true)},
	/*100.4.  REXP_ANDL */				{new AccionAsignar("esOperacion",true)},
	/*100.5.  REXP_ORB */				{new AccionAsignar("esOperacion",true)},
	/*100.6.  REXP_XORB */				{new AccionAsignar("esOperacion",true)},
	/*100.7.  REXP_ANDB */				{new AccionAsignar("esOperacion",true)},
	/*100.8.  REXP_REL */				{new AccionAsignar("esOperacion",true)},
	/*100.9.  REXP_COMP */				{new AccionAsignar("esOperacion",true)},
	/*100.10.  REXP_DESPL */			{new AccionAsignar("esOperacion",true)},
	/*100.11.  REXP_AD */				{new AccionAsignar("esOperacion",true)},
	/*100.12.  REXP_MULT */				{new AccionAsignar("esOperacion",true)},
	/*100.13.  OP_SELECCION EXP3 */		{new AccionAsignar("esOperacion",true)},
	/*100.14.  OP_INC*/					{new AccionAsignar("esOperacion",true)}},
/*101. INDIRECCION2 -> */{
	/*101.1. * INDIRECCION*/			{new AccionAsignar("num",0,"num","suma",1)}},
/*102. L_PARAMS_LLAMADA -> */{
	/*102.1.  EXP  RES_LISTA_PARAMS_LLAMDA */	{new AccionAsignar("lista",new OperacionAgregarALista(new OperandoGramatica(1,"lista"), new OperandoGramatica(0,"tipo")))},
	/*102.2.  ?*/								{new AccionAsignar("lista",new OperandoCrearArrayList())}},
/*103. RES_LISTA_PARAMS_LLAMADA ->  */{
	/*103.1. ,EXP  RES_LISTA_PARAMS_LLAMADA */	{new AccionAsignar("lista",new OperacionAgregarALista(new OperandoGramatica(2,"lista"), new OperandoGramatica(1,"tipo")))},
	/*103.2.  ?*/								{new AccionAsignar("lista",new OperandoCrearArrayList())}},
/*104. AUX-> */{
	/*104.1. EXP */								{},
	/*104.2.  ?*/								{}},
/*105. REXP4 -> */{
	/*105.1. TIPO_PRIMITIVO INDIRECCION  RDEFINICION */											{new AccionR105_1()},
	/*105.2.  EXP_SIN_IDEN REXP; */																{new AccionCondicionada(0,"esConstante","false",new AccionCondicionada(1,"esAsignacion","true",new AccionAsignar("tipo","error"),new AccionAsignar("tipo","vacio")),new AccionAsignar("tipo","error"))},
	/*105.3.  MODIFICADOR L_MODIFICADORES RTIPO RDEFINICION */									{/*rellenar*//*new AccionCondicionada(2,"tipo","igual","error",new AccionAsignar("tipo","error"),new AccionCondicionada("estaEnTS",3,"lexema",new AccionAsignar("tipo","error"), new Action[] {new AccionAsignar("tipo","vacio"),new AccionAsignar("tipo",terminar,sdffdw,"tipo")}))}*/},
	/*105.4.  iden REXP3_2 RDEFINICION  // Definir variable con tipo definido por el usuario*/	{new AccionR105_4()},
	/*105.5.  OP_INC IDENTIFICADOR*/	{new AccionCondicionada(
											new CondicionHeredada(
													new CondicionEsCompatible(new OperandoGramatica(1,"tipo"),new OperandoCrearTipo("int",0)),
													new CondicionHeredada(new OperandoGramatica(1,"esFuncion"),new OperandoDirecto(false),"igual"),
													"and"),
											new Accion[]{},
											new Accion[]{new AccionGenError(new OperandoGramatica(-1,"filaInicio"),new OperandoGramatica(-1,"colInicio"),"No se puede aplicar el opeando a este identificador.")})
										}
					},
/*106. M_AMBITO->*/{
	/*106.1. (lambda)*/			{new AccionAbrirAmbito()}}
	
	};
}

