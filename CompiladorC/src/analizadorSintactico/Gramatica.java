package analizadorSintactico;

import tablaSimbolos.PalRes;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import token.TokenAlmohadilla;
import token.TokenAmpersand;
import token.TokenAsig;
import token.TokenAsterisco;
import token.TokenComa;
import token.TokenComillasChar;
import token.TokenCorchetes;
import token.TokenEntrecomillado;
import token.TokenFin;
import token.TokenId;
import token.TokenLambda;
import token.TokenLlaves;
import token.TokenOpSeleccion;
import token.TokenOpTernario;
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
	static Token corcheteAbierto= new TokenCorchetes(TokenCorchetes.TipoTokenCorchetes.ABIERTO);
	static Token corcheteCerrado= new TokenCorchetes(TokenCorchetes.TipoTokenCorchetes.CERRADO);
	static Token llaveAbierta = new TokenLlaves(TokenLlaves.TipoTokenLlaves.ABIERTO);
	static Token llaveCerrada= new TokenLlaves(TokenLlaves.TipoTokenLlaves.CERRADO);
	
	static Token mayor= new TokenRelComp(TokenRelComp.TipoTokenRelComp.MAYOR);
	static Token menor= new TokenRelComp(TokenRelComp.TipoTokenRelComp.MENOR);
	static Token punto= new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.PUNTO);
	
	static Token asig = new TokenAsig(TokenAsig.TipoTokenAsig.ASIG);
	static Token suma_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.SUMA_ASIG);
	static Token resta_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.RESTA_ASIG);
	static Token mult_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.MULT_ASIG);
	static Token div_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.DIV_ASIG);
	static Token desp_izq_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.DESP_IZQ_ASIG);
	static Token desp_der_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.DESP_DER_ASIG);
	static Token andB_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.ANDB_ASIG);
	static Token orB_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.ORB_ASIG);
	static Token xorB_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.XORB_ASIG);
	static Token mod_Asig = new TokenAsig(TokenAsig.TipoTokenAsig.MOD_ASIG);
	
	static Token dosPuntos = new TokenOpTernario (TokenOpTernario.TipoTokenOpTernario.DOSPUNTOS);
	
	
	
	static Object[][][] reglasGramatica={
/*PROGRAMA,*/ {{NT.L_DEFINICIONES,new TokenFin()}},
/* L_DEFINICIONES,*/ { {new TokenLambda()},	{NT.DEFINICION_GLOBAL, NT.L_DEFINICIONES} },
/* DEFINICION_GLOBAL,*/{{NT.MACROS},	{NT.DEFINICION} ,{NT.DEFINICION_TYPEDEF}},
/* DEFINICION,*/{{NT.TIPO,NT.RDEFINICION},	{NT.DEFINICION_STRUCT},{NT.DEFINICION_ENUM},{NT.DEFINICION_UNION}},
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
/* RIFDEF,*/{{new TokenAlmohadilla(),NT.RIFDEF2,NT.RIFDEF},		{NT.L_SENTENCIAS},{new TokenLambda()}},	
/* RIFDEF2,*/{{PalRes.PAL_MAC_elif,new TokenId(null),NT.RIFDEF},	{PalRes.PAL_RES_else, NT.L_SENTENCIAS},	{NT.RMACRO}}, //CAdena = entrecomillado??

	
/* DEFINICION_UNION,*/  { {PalRes.PAL_RES_union,new TokenId(null),llaveAbierta, NT.L_VARIABLES,corcheteCerrado,NT.LISTA_IDENS}} ,
/*LISTA_IDENS,*/		{ {new TokenId(null), NT.CORCHETES,NT.RDEF_VARIABLE}, {new TokenPuntoyComa()}},
/* CORCHETES,*/			{ {new TokenLambda()} , {corcheteAbierto,NT.CONTENIDO, corcheteCerrado,NT.CORCHETES}},
/* CONTENIDO,*/			{ {new TokenLambda()} , {NT.EXP}},
/* DEFINICION_ENUM,*/	{ {PalRes.PAL_RES_enum, new TokenId(null),llaveAbierta,new TokenId(null),NT.RENUM, new TokenPuntoyComa()} },
/* RENUM,*/				{ {NT.OP_ASIG,NT.RSENTENCIA_ASIG,NT.RENUM2},{new TokenComa(),new TokenId(null),NT.RENUM},{new TokenLambda()}},
/* RENUM2,*/			{ {new TokenComa(),new TokenId(null),NT.RENUM}, {new TokenLambda()}},
/* DEFINICION_STRUCT,*/	{ {PalRes.PAL_RES_struct, new TokenId(null),corcheteAbierto, NT.L_VARIABLES,corcheteCerrado, NT.LISTA_IDENS}},
/* L_VARIABLES,*/		{ {NT.DEFINICION_STRUCT, NT.L_VARIABLES}, {NT.DEF_VAR, NT.L_VARIABLES}, {NT.DEFINICION_ENUM,NT.L_VARIABLES}, {NT.DEFINICION_UNION, NT.L_VARIABLES},{new TokenLambda()} },
/* DEF_VAR,*/			{ {NT.TIPO,NT.LISTA_IDENS}},
/* DEFINICION_TYPEDEF,*/{ {PalRes.PAL_RES_typedef, NT.TIPO,NT.RDEF_TYPEDEF} },
/* RDEF_TYPEDEF,*/		{ {PalRes.PAL_RES_struct,new TokenId(null),new TokenId(null),new TokenPuntoyComa()}, {new TokenId(null),new TokenPuntoyComa()} ,{PalRes.PAL_RES_union,new TokenId(null),new TokenId(null),new TokenPuntoyComa()} , {PalRes.PAL_RES_enum,new TokenId(null),new TokenId(null),new TokenPuntoyComa()} },

/* RDEF_FUNCION,*/		{ {new TokenPuntoyComa()}, {llaveAbierta,NT.BLOQUE_SENTENCIAS,llaveCerrada} },
/* L_PARAMS,*/			{ {new TokenLambda()}, {NT.TIPO,NT.RL_PARAMS} },
/* RL_PARAMS,*/			{ {new TokenLambda()}, {new TokenComa(),NT.TIPO,NT.RL_PARAMS}, {new TokenId(null), NT.RL_PARAMS2} },
/* RL_PARAMS2,*/		{ {new TokenLambda()} , {new TokenComa(),NT.TIPO,NT.RL_PARAMS} },

/* TIPO,*/				{ {NT.MODIFICADOR,NT.RTIPO} },
/* RTIPO,*/				{ {NT.TIPO, NT.TIPO_PRIMITIVO, NT.RTIPO2} , {new TokenId(null), NT.RTIPO2} },
/* MODIFICADOR,*/		{ {PalRes.PAL_RES_auto}, {PalRes.PAL_RES_volatile}, {PalRes.PAL_RES_register}, {PalRes.PAL_RES_extern}, {PalRes.PAL_RES_const}, {PalRes.PAL_RES_unsigned},{PalRes.PAL_RES_signed},{PalRes.PAL_RES_static},{new TokenLambda()} },
/* TIPO_PRIMITIVO,*/	{ {PalRes.PAL_RES_void}, {PalRes.PAL_RES_int}, {PalRes.PAL_RES_char}, {PalRes.PAL_RES_float}, {PalRes.PAL_RES_double} },
/* RTIPO2,*/			{ {new TokenLambda()}, {new TokenAsterisco(),NT.RTIPO2} },

/* L_SENTENCIAS,*/		{ {llaveAbierta, NT.BLOQUE_SENTENCIAS,llaveCerrada}, {NT.SENTENCIA} },
/* BLOQUE_SENTENCIAS,*/ { {NT.SENTENCIA,NT.BLOQUE_SENTENCIAS} , {llaveAbierta, NT.BLOQUE_SENTENCIAS,llaveCerrada} , {new TokenLambda()} },
/* SENTENCIA,*/			{ {new TokenPuntoyComa()},{NT.SENTENCIA_IF},{NT.SENTENCIA_BUCLE},{NT.EXP,new TokenPuntoyComa()},{NT.SENTENCIA_CASE},{NT.OTRAS_SENTENCIAS} },
/* OTRAS_SENTENCIAS,*/	{ {PalRes.PAL_RES_break},{PalRes.PAL_RES_continue},{PalRes.PAL_FUN_printf,parentesisAbierto,NT.ENTRECOMILLADO,NT.RPRINTF,parentesisCerrado},{PalRes.PAL_FUN_scanf,parentesisAbierto,NT.ENTRECOMILLADO,NT.RSCANF,parentesisCerrado},{PalRes.PAL_RES_return,NT.EXP} },
/* ENTRECOMILLADO,*/    { {new TokenEntrecomillado(null)} },
/* RPRINTF,*/			{ {new TokenComa(),NT.REFERENCIA,NT.INDIRECCION,NT.RPRINTF2}, {new TokenLambda()} },
/* REFERENCIA,*/		{ {new TokenAmpersand()} , {new TokenLambda()} },
/* INDIRECCION,*/		{ {new TokenAsterisco(),NT.INDIRECCION}, {new TokenLambda()} },
/* RPRINTF2,*/			{ {NT.EXP,NT.RPRINTF} },
/* RSCANF,*/			{ {new TokenComa(),NT.REFERENCIA,NT.INDIRECCION,NT.RSCANF2}, {new TokenLambda()} },
/* RSCANF2,*/			{ {new TokenId(null),NT.CORCHETES} ,{new TokenLambda()} },
/* SENTENCIA_IF,*/		{ {PalRes.PAL_RES_if,parentesisAbierto,NT.EXP,parentesisCerrado,NT.RSENTENCIA_IF} },
/* RSENTENCIA_IF,*/		{ {NT.L_SENTENCIAS,NT.SENTENCIA_ELSE} },
/* SENTENCIA_ELSE,*/	{ {PalRes.PAL_RES_else,NT.L_SENTENCIAS}, {new TokenLambda()} },
/* SENTENCIA_BUCLE,*/	{ {PalRes.PAL_RES_do,NT.L_SENTENCIAS,PalRes.PAL_RES_while,parentesisAbierto,NT.EXP,parentesisCerrado}, {PalRes.PAL_RES_while,parentesisAbierto,NT.EXP,parentesisCerrado,NT.L_SENTENCIAS}, {PalRes.PAL_RES_for,parentesisAbierto,NT.CAMPO,new TokenPuntoyComa(),NT.CAMPO,new TokenPuntoyComa(),NT.CAMPO,parentesisCerrado,NT.L_SENTENCIAS} },
/* CAMPO,*/				{ {new TokenLambda()} ,{NT.EXP} },
/* SENTENCIA_ASIG,*/	{ {new TokenId(null), NT.CORCHETES,NT.OP_ASIG,NT.RSENTENCIA_ASIG} },
/* OP_ASIG,*/			{ {asig},{suma_Asig},{resta_Asig},{mult_Asig},{div_Asig},{desp_izq_Asig},{desp_der_Asig},{andB_Asig},{orB_Asig},{xorB_Asig},{mod_Asig} },
/* RSENTENCIA_ASIG,*/	{ {NT.EXP} },
/* SENTENCIA_CASE,*/	{ {PalRes.PAL_RES_switch,parentesisAbierto,NT.EXP,parentesisCerrado,NT.L_CASES} },
/* L_CASES,*/			{ {NT.CASE} ,{llaveAbierta,NT.CASES,llaveCerrada} },
/* CASES,*/				{ {PalRes.PAL_RES_case,NT.CONSTANTE,dosPuntos,NT.RCASES}, {PalRes.PAL_RES_default,dosPuntos,NT.RCASES2},{new TokenLambda()} },
/* RCASES,*/			{ {NT.BLOQUE_SENTENCIAS,NT.CASES}, {new TokenLambda()} },
/* RCASES2,*/			{ {NT.BLOQUE_SENTENCIAS}, {new TokenLambda()} },
/* CASE,*/				{ {PalRes.PAL_RES_case,NT.CONSTANTE,dosPuntos,NT.BLOQUE_SENTENCIAS}, {PalRes.PAL_RES_default,dosPuntos,NT.BLOQUE_SENTENCIAS} },
/* CONSTANTE,*/			{ {NT.EXP_COND} },

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
