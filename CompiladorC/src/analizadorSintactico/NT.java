package analizadorSintactico;


/*
 * Este enumerado debe conservar siempre el orden en el que las reglas para cada NT
 * aparecen en la gramatica.
 */






public enum NT {
	PROGRAMA,
	L_DEFINICIONES,
	DEFINICION_GLOBAL,
	RDEFINICION,
	RDEFINICION2,
	RDEF_VARIABLE,
	RDEF_VARIABLE2,

	MACROS,
	RMACRO, 
	RINCLUDE,
	RIFDEF, 
	RIFDEF2,

	DEFINICION_UNION,
	LISTA_IDENS,
	IDENTIFICADOR,
	RIDENTIFICADOR,
	CORCHETES,
	CONTENIDO,
	DEFINICION_ENUM,
	RENUM,
	RENUM2,
	DEFINICION_STRUCT,
	L_VARIABLES,
	DEF_VAR,
	DEFINICION_TYPEDEF,
	RDEF_TYPEDEF,
	
	RDEF_FUNCION,
	L_PARAMS,
	RL_PARAMS,
	RL_PARAMS2,
	RL_PARAMS3,
	
	TIPO,
	RTIPO,
	INDIRECCION,
	L_MODIFICADORES,
	MODIFICADOR,
	TIPO_PRIMITIVO,

	L_SENTENCIAS,
	BLOQUE_SENTENCIAS,
	SENTENCIA,
	OTRAS_SENTENCIAS,
	ENTRECOMILLADO, 
	RPRINTF,
	REFERENCIA,
	RPRINTF2,
	RSCANF,
	RSCANF2,
	SENTENCIA_IF,
	RSENTENCIA_IF,
	SENTENCIA_ELSE,
	SENTENCIA_BUCLE,
	CAMPO,
	SENTENCIA_CASE,
	L_CASES,
	CASES,
	RCASES,
	RCASES2,
	CASE,

	EXP,
	REXP,
	OP_ASIG,
	EXP_COND,
	REXP_COND,
	EXP_ORL,
	REXP_ORL,
	EXP_ANDL,
	REXP_ANDL,
	EXP_ORB,
	REXP_ORB,
	EXP_XORB,
	REXP_XORB,
	EXP_ANDB, 
	REXP_ANDB,
	EXP_REL,
	REXP_REL,
	OP_REL, 
	EXP_COMP, 
	REXP_COMP,
	OP_COMP, 
	EXP_DESPL,
	REXP_DESPL,
	OP_DESPL, 
	EXP_AD,
	REXP_AD,
	OP_AD,
	EXP_MULT,
	REXP_MULT,
	OP_MULT,
	EXP1,
	OP_UNARIOS,
	OP_INC,
	EXP2,
	REXP2,
	OP_SELECCION,
	EXP3,
	LISTA_EXP,
	REXP3,
	L_PARAMS_LLAMADA,
	RES_LISTA_PARAMS_LLAMADA,

			
	 };
