package analizadorSintactico;

import tablaSimbolos.PalRes;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import token.TokenAlmohadilla;
import token.TokenComa;
import token.TokenFin;
import token.TokenId;
import token.TokenLambda;
import token.TokenOpSeleccion;
import token.TokenPalRes;
import token.TokenParentesis;
import token.TokenPuntoyComa;
import token.TokenRelComp;

public class Gramatica {
	
	/*ATENCION:
	 * Las reglas de la gramatica se cargan en el orden del enumerado NT.
	 * Así si para obtener las reglas de un NT basta con pasar su indice a este Array.
	 * Por tanto si se modifica la gramatica, y cambia el orden de las reglas, debe
	 * de ser modificado el enumerado NT.
	 */
	
	/*Se ha implementado así para que las reglas de cada NT se vean de forma mas natural
	 * en vez de siendo introducidas por su indice en el array.
	 * De esta forma las modificaciones son mas intuitivas. 	 
	 */
	
	/*Como no podemos generar tokens de palabra reservada, que necesitan la referencia a
	 * la Tabla de Simbolos, ponemos en su lugar el enumerado PAL_RES
	 */
	
	/*Para mejorar la legibilidad ponemos los tokens que necesitan un argumento en su
	 * constructor como static aparte, para en la definicion de la gramatica poner
	 * unicamente un nombre corto.
	 */
	/*El token de los id los inicializamos a null, pues en la gramatica nos da igual su lexema
	 * 
	 */
	
	
	static Token parentesisAbierto= new TokenParentesis(TokenParentesis.TipoTokenParentesis.ABIERTO);
	static Token parentesisCerrado= new TokenParentesis(TokenParentesis.TipoTokenParentesis.CERRADO);
	static Token mayor= new TokenRelComp(TokenRelComp.TipoTokenRelComp.MAYOR);
	static Token menor= new TokenRelComp(TokenRelComp.TipoTokenRelComp.MENOR);
	static Token punto= new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.PUNTO);
	
	static Object[][][] reglasGramatica={
/*PROGRAMA,*/ {{NT.L_DEFINICIONES,new TokenFin()}},
/* L_DEFINICIONES,*/{{new TokenLambda()},	{NT.DEFINICION_GLOBAL, NT.L_DEFINICIONES}},
/* DEFINICION_GLOBAL,*/{{NT.MACROS,},	{NT.DEFINICION},{NT.DEFINICION_TYPEDEF}},
/* DEFINICION,*/{{NT.TIPO,NT.RDEFINICION},{NT.DEFINICION_STRUCT},{NT.DEFINICION_ENUM},{NT.DEFINICION_UNION}},
/* RDEFINICION,*/{{new TokenPuntoyComa()},	{new TokenId(null),NT.RDEFINICION2}},
/* RDEFINICION2,*/{{NT.CORCHETES, NT.RDEF_VARIABLE},	{parentesisAbierto,NT.L_PARAMS,parentesisCerrado,NT.RDEF_FUNCION}},
/* RDEF_VARIABLE,*/{{NT.OP_ASIG,NT.RSENTENCIA_ASIG,NT.RDEF_VARIABLE2},	{NT.RDEF_VARIABLE2}},	
/* RDEF_VARIABLE2,*/{{new TokenComa(),new TokenId(null),NT.RDEF_VARIABLE},	{new TokenPuntoyComa()}},

/* MACROS,*/{{new TokenAlmohadilla(),NT.RMACRO}},
/* RMACRO,*/{{PalRes.PAL_MAC_define,new TokenId(null), NT.EXP},		{PalRes.PAL_MAC_include,NT.RINCLUDE},	
			{PalRes.PAL_MAC_ifdef,new TokenId(null), NT.RIFDEF2,new TokenAlmohadilla(),PalRes.PAL_MAC_endif},
			{PalRes.PAL_MAC_undef,new TokenId(null)},	{PalRes.PAL_MAC_ifndef,new TokenId(null)},	
			{PalRes.PAL_MAC_error, parentesisAbierto}}, /*falta saber lo que es caracter*/

/* RINCLUDE,*/{{NT.ENTRECOMILLADO},		{menor,new TokenId(null),punto,new TokenId(null),mayor}},
/* RIFDEF,*/{{new TokenAlmohadilla(),NT.RIFDEF2,NT.RIFDEF},		{NT.DEFINICION,NT.RIFDEF},	{NT.DEFINICION_TYPEDEF,NT.RIFDEF},{new TokenLambda()}},	
/* RIFDEF2,*/{{PalRes.PAL_MAC_elif,new TokenId(null),NT.RIFDEF},	{PalRes.PAL_RES_else,NT.CADENA},	{NT.RMACRO}}, //CAdena = entrecomillado??

/* DEFINICION_UNION,*/
/*LISTA_IDENS,*/
/* CORCHETES,*/
/* CONTENIDO,*/
/* DEFINICION_ENUM,*/
/* RENUM,*/
/* RENUM2,*/
/* DEFINICION_STRUCT,*/
/* L_VARIABLES,*/
/* DEF_VAR,*/
/* DEFINICION_TYPEDEF,*/
/* RDEF_TYPEDEF,*/

/* RDEF_FUNCION,*/
/* L_PARAMS,*/
/* RL_PARAMS,*/
/* RL_PARAMS2,*/

/* TIPO,*/
/* RTIPO,*/
/* MODIFICADOR,*/
/* TIPO_PRIMITIVO,*/
/* RTIPO2,*/

/* L_SENTENCIAS,*/
/* BLOQUE_SENTENCIAS,*/
/* SENTENCIA,*/
/* OTRAS_SENTENCIAS,*/
/* ENTRECOMILLADO,*/
/* RPRINTF,*/
/* REFERENCIA,*/
/* INDIRECCION,*/
/* RPRINTF2,*/
/* RSCANF,*/
/* RSCANF2,*/
/* SENTENCIA_IF,*/
/* RSENTENCIA_IF,*/
/* SENTENCIA_ELSE,*/
/* SENTENCIA_BUCLE,*/
/* CAMPO,*/
/* SENTENCIA_ASIG,*/
/* OP_ASIG,*/
/* RSENTENCIA_ASIG,*/
/* SENTENCIA_CASE,*/
/* L_CASES,*/
/* CASES,*/
/* RCASES,*/
/* RCASES2,*/
/* CASE,*/
/* CONSTANTE,*/

/* EXP,*/
/* REXP,*/
/* EXP_COND,*/
/* REXP_COND,*/
/* EXP_ORL,*/
/* REXP_ORL,*/
/* EXP_ANDL,*/
/* REXP_ANDL,*/
/* EXP_ORB,
/* REXP_ORB,*/
/* EXP_XORB,*/
/* REXP_XORB,*/
/* EXP_ANDB,*/
/* REXP_ANDB,*/
/* EXP_REL,*/
/* REXP_REL,*/
/* OP_REL,*/
/* EXP_COMP,*/
/* REXP_COMP,*/
/* OP_COMP,
/* EXP_DESPL,*/
/* REXP_DESPL,*/
/* OP_DESPL,*/
/* EXP_AD,*/
/* REXP_AD,*/
/* OP_AD,*/
/* EXP_MULT,*/
/* REXP_MULT,*/
/* OP_MULT,*/
/* EXP1,*/
/* OP_UNARIOS,*/
/* OP_INC,*/
/* EXP2,*/
/* REXP2,*/
/* OP_SELECCION,*/
/* EXP3,*/
/* LISTA_EXP,*/
/* REXP3,*/
/* REXP3_2,*/
/* L_PARAMS_LLAMADA,*/
/* RES_LISTA_LLAMADA	*/
		
		
		
		
		
		
		
		
		
	};
	
	
	
	static Object[][] regla_PROGRAMA = 
		{{NT.L_DEFINICIONES, new TokenFin()}};
	
	static Object[][] regla_L_DEFINICIONES = 
		{{new TokenLambda()},{NT.DEFINICION_GLOBAL, NT.L_DEFINICIONES}};

	
	Object[][] aaa;
	
	
	
	
	
	
	
	
	
	

	
}
