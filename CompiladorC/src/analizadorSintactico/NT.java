package analizadorSintactico;


/*
 * Este enumerado debe conservar siempre el orden en el que las reglas para cada NT
 * aparecen en la gramatica.
 */






public enum NT {
	/*0*/	PROGRAMA,
	/*1*/	   L_DEFINICIONES,
	/*2*/	   DEFINICION_GLOBAL,
	/*3*/	   RDEFINICION,
	/*4*/	   RDEFINICION2,
	/*5*/	   RDEF_VARIABLE,
	/*6*/	   RDEF_VARIABLE2,
		   
	/*7*/	   MACROS,
	/*8*/	   RMACRO, 
	/*9*/	   RINCLUDE,
	/*10*/	   RIFDEF, 
	/*11*/	   RIFDEF2,
		
	/*12*/	   DEFINICION_UNION,
	/*13*/	   LISTA_IDENS,
	/*14*/	   CORCHETES,
	/*15*/	   CONTENIDO,
	/*16*/	   DEFINICION_ENUM,
	/*17*/	   RENUM,
	/*18*/	   RENUM2,
	/*19*/	   DEFINICION_STRUCT,
	/*20*/	   L_VARIABLES,
	/*21*/	   DEF_VAR,
	/*22*/	   DEFINICION_TYPEDEF,
	/*23*/	   RDEF_TYPEDEF,
		
	/*24*/	   RDEF_FUNCION,
	/*25*/	   L_PARAMS,
	/*26*/	   RL_PARAMS,
	/*27*/	   RL_PARAMS2,
	/*28*/	   RL_PARAMS3,
		
	/*29*/	   TIPO,
	/*30*/	   RTIPO,
	/*31*/	   L_MODIFICADORES,
	/*32*/	   MODIFICADOR,
	/*33*/	   TIPO_PRIMITIVO,
	/*34*/	   RTIPO2,
		
	/*35*/	   L_SENTENCIAS,
	/*36*/	   BLOQUE_SENTENCIAS,
	/*37*/	   SENTENCIA,
	/*38*/	   OTRAS_SENTENCIAS,
	/*39*/	   ENTRECOMILLADO, 
	/*40*/	   RPRINTF,
	/*41*/	   REFERENCIA,
	/*42*/	   INDIRECCION,
	/*43*/	   RPRINTF2,
	/*44*/	   RSCANF,
	/*45*/	   RSCANF2,
	/*46*/	   SENTENCIA_IF,
	/*47*/	   RSENTENCIA_IF,
	/*48*/	   SENTENCIA_ELSE,
	/*49*/	   SENTENCIA_BUCLE,
	/*50*/	   CAMPO,
	/*51*/	   OP_ASIG,
	/*52*/	   RSENTENCIA_ASIG,
	/*53*/	   SENTENCIA_CASE,
	/*54*/	   L_CASES,
	/*55*/	   CASES,
	/*56*/	   RCASES,
	/*57*/	   RCASES2,
	/*58*/	   CASE,
		   
	/*59*/	   EXP,
	/*60*/	   REXP,
	/*61*/	   EXP_COND,
	/*62*/	   REXP_COND,
	/*63*/	   EXP_ORL,
	/*64*/	   REXP_ORL,
	/*65*/	   EXP_ANDL,
	/*66*/	   REXP_ANDL,
	/*67*/	   EXP_ORB,
	/*68*/	   REXP_ORB,
	/*69*/	   EXP_XORB,
	/*70*/	   REXP_XORB,
	/*71*/	   EXP_ANDB, 
	/*72*/	   REXP_ANDB,
	/*73*/	   EXP_REL,
	/*74*/	   REXP_REL,
	/*75*/	   OP_REL, 
	/*76*/	   EXP_COMP, 
	/*77*/	   REXP_COMP,
	/*78*/	   OP_COMP, 
	/*79*/	   EXP_DESPL,
	/*80*/	   REXP_DESPL,
	/*81*/	   OP_DESPL, 
	/*82*/	   EXP_AD,
	/*83*/	   REXP_AD,
	/*84*/	   OP_AD,
	/*85*/	   EXP_MULT,
	/*86*/	   REXP_MULT,
	/*87*/	   OP_MULT,
	/*88*/	   EXP1,
	/*89*/	   OP_UNARIOS,
	/*90*/	   OP_INC,
	/*91*/	   EXP2,
	/*92*/	   REXP2,
	/*93*/	   OP_SELECCION,
	/*94*/	   EXP3,
	/*95*/	   LISTA_EXP,
	/*96*/	   RIDEN,
	/*97*/	   REXP3,
	/*98*/	   L_PARAMS_LLAMADA,
	/*99*/	   RES_LISTA_PARAMS_LLAMADA

			
	 };
