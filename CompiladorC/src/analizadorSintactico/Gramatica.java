package analizadorSintactico;

import tablaSimbolos.PalRes;
import token.Token;
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
import token.TokenLambda;
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
import token.TokenParentesis;
import token.TokenPuntoyComa;
import token.TokenRelComp;
import token.TokenRelIgual;
import token.TokenSimboloAdicion;
import token.TokenXorB;

/* IMPORTANTE, MODIFICACIONES
 * 
 * - Desde Sentencia , Tipo y Exp no están factorizados para TokenID
 * - Creemos que en OP_UNARIOS debe ir tokenLambda como produccion.
 * 		para que desde Exp pueda  bajar hasta exp1 y etc.
 * 
 */









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
/*PROGRAMA,*/ 			{{NT.L_DEFINICIONES,new TokenFin()} , {new TokenFin()}},
/* L_DEFINICIONES,*/	{{NT.DEFINICION_GLOBAL, NT.L_DEFINICIONES} ,{new TokenLambda()}},
/* DEFINICION_GLOBAL,*/	{{NT.MACROS},{NT.TIPO, NT.RDEFINICION},{NT.DEFINICION_STRUCT}, {NT.DEFINICION_ENUM},{NT.DEFINICION_UNION}, {NT.DEFINICION_TYPEDEF},},
/* RDEFINICION,*/		{{new TokenPuntoyComa()},	{new TokenId(null),NT.RDEFINICION2}},
/* RDEFINICION2,*/		{{NT.CORCHETES, NT.RDEF_VARIABLE},	{parentesisAbierto,NT.L_PARAMS,parentesisCerrado,NT.RDEF_FUNCION}},
/* RDEF_VARIABLE,*/		{{NT.OP_ASIG,NT.EXP,NT.RDEF_VARIABLE2},	{NT.RDEF_VARIABLE2}},	
/* RDEF_VARIABLE2,*/	{{new TokenComa(),new TokenId(null),NT.RDEF_VARIABLE},	{new TokenPuntoyComa()}},

/* MACROS,*/			{{new TokenAlmohadilla(),NT.RMACRO}},
/* RMACRO,*/			{{PalRes.PAL_MAC_define,NT.IDENTIFICADOR, NT.EXP},	{PalRes.PAL_MAC_include,NT.RINCLUDE},	
						{PalRes.PAL_MAC_ifdef,new TokenId(null), NT.RIFDEF,new TokenAlmohadilla(),PalRes.PAL_MAC_endif},
						{PalRes.PAL_MAC_undef,new TokenId(null)},	{PalRes.PAL_MAC_ifndef,new TokenId(null)},	
						{PalRes.PAL_MAC_error},{PalRes.PAL_MAC_pragma}}, /*Aqui no ponemos nada, pero en lŽxico cuando generamos uno de estos tokens, leemos hasta EOL*/

/* RINCLUDE,*/			{{NT.ENTRECOMILLADO},{menor,new TokenId(null),punto,new TokenId(null),mayor}},
/* RIFDEF,*/			{{new TokenAlmohadilla(),NT.RIFDEF2,NT.RIFDEF},	{NT.L_SENTENCIAS},{new TokenLambda()}},	
/* RIFDEF2,*/			{{PalRes.PAL_MAC_elif,new TokenId(null),NT.RIFDEF},	{PalRes.PAL_RES_else, NT.L_SENTENCIAS},	{NT.RMACRO}},

	
/* DEFINICION_UNION,*/  { {PalRes.PAL_RES_union,new TokenId(null),llaveAbierta, NT.L_VARIABLES,llaveCerrada, NT.LISTA_IDENS}} ,
/*LISTA_IDENS,*/		{ {new TokenId(null), NT.CORCHETES,NT.RDEF_VARIABLE}, {new TokenPuntoyComa()}},
/*IDENTIFICADOR,*/ 		{ {NT.REFERENCIA,NT.INDIRECCION,new TokenId(null),NT.RIDENTIFICADOR}},
/*RIDENTIFICADOR*/		{ {NT.CORCHETES} , {parentesisAbierto,NT.L_PARAMS_LLAMADA,parentesisCerrado}},
/* CORCHETES,*/			{ {new TokenLambda()} , {corcheteAbierto,NT.CONTENIDO, corcheteCerrado,NT.CORCHETES}},
/* CONTENIDO,*/			{ {new TokenLambda()} , {NT.EXP}},
/* DEFINICION_ENUM,*/	{ {PalRes.PAL_RES_enum, new TokenId(null),llaveAbierta,new TokenId(null),NT.RENUM, llaveCerrada, new TokenPuntoyComa()} },
/* RENUM,*/				{ {new TokenAsig(TokenAsig.TipoTokenAsig.ASIG),NT.OP_ASIG,NT.EXP,NT.RENUM2},{new TokenComa(),new TokenId(null),NT.RENUM},{new TokenLambda()}},
/* RENUM2,*/			{ {new TokenComa(),new TokenId(null),NT.RENUM}, {new TokenLambda()}},
/* DEFINICION_STRUCT,*/	{ {PalRes.PAL_RES_struct, new TokenId(null),llaveAbierta, NT.L_VARIABLES,llaveCerrada, NT.LISTA_IDENS}},
/* L_VARIABLES,*/		{ {NT.DEFINICION_STRUCT, NT.L_VARIABLES}, {NT.DEF_VAR, NT.L_VARIABLES}, {NT.DEFINICION_ENUM,NT.L_VARIABLES}, {NT.DEFINICION_UNION, NT.L_VARIABLES},{new TokenLambda()} },
/* DEF_VAR,*/			{ {NT.TIPO,NT.LISTA_IDENS}},
/* DEFINICION_TYPEDEF,*/{ {PalRes.PAL_RES_typedef,NT.RDEF_TYPEDEF} },
/* RDEF_TYPEDEF,*/		{ {PalRes.PAL_RES_struct,new TokenId(null),NT.INDIRECCION,new TokenId(null),new TokenPuntoyComa()}, {NT.TIPO,new TokenId(null),new TokenPuntoyComa()} ,{PalRes.PAL_RES_union,new TokenId(null),NT.INDIRECCION,new TokenId(null),new TokenPuntoyComa()} , {PalRes.PAL_RES_enum,new TokenId(null),NT.INDIRECCION,new TokenId(null),new TokenPuntoyComa()} },

/* RDEF_FUNCION,*/		{ {new TokenPuntoyComa()}, {llaveAbierta,NT.BLOQUE_SENTENCIAS,llaveCerrada} },
/* L_PARAMS,*/			{ {new TokenLambda()}, {NT.TIPO,NT.RL_PARAMS} },
/* RL_PARAMS,*/			{ {new TokenLambda()}, {new TokenComa(),NT.TIPO,NT.RL_PARAMS2}, {new TokenId(null), NT.RL_PARAMS3} },
/* RL_PARAMS2,*/		{ {new TokenLambda()} , {new TokenComa(),NT.TIPO,NT.RL_PARAMS2} },
/* RL_PARAMS3,*/		{ {new TokenLambda()} , {new TokenComa(),NT.TIPO, new TokenId(null), NT.RL_PARAMS3} },

/* TIPO,*/				{ {NT.L_MODIFICADORES,NT.RTIPO} },
/* RTIPO,*/				{ {NT.TIPO_PRIMITIVO, NT.INDIRECCION} , {new TokenId(null), NT.INDIRECCION} },
/*INDIRECCION*/			{ {new TokenAsterisco(),NT.INDIRECCION},{new TokenLambda()} },
/*L_MODIFICADORES*/		{{NT.MODIFICADOR, NT.L_MODIFICADORES}, {new TokenLambda()}},
/* MODIFICADOR,*/		{ {PalRes.PAL_RES_auto}, {PalRes.PAL_RES_volatile}, {PalRes.PAL_RES_register}, {PalRes.PAL_RES_extern}, {PalRes.PAL_RES_const}, {PalRes.PAL_RES_unsigned},{PalRes.PAL_RES_signed},{PalRes.PAL_RES_static}},
/* TIPO_PRIMITIVO,*/	{ {PalRes.PAL_RES_void}, {PalRes.PAL_RES_int}, {PalRes.PAL_RES_char}, {PalRes.PAL_RES_float}, {PalRes.PAL_RES_double} },

/* L_SENTENCIAS,*/		{ {llaveAbierta, NT.BLOQUE_SENTENCIAS,llaveCerrada}, {NT.SENTENCIA} },
/* BLOQUE_SENTENCIAS,*/ { {NT.SENTENCIA,NT.BLOQUE_SENTENCIAS} , {llaveAbierta, NT.BLOQUE_SENTENCIAS,llaveCerrada} , {new TokenLambda()} },
/* SENTENCIA,*/			{ {new TokenPuntoyComa()},{NT.SENTENCIA_IF},{NT.SENTENCIA_BUCLE},{NT.REXP4},{NT.SENTENCIA_CASE},{NT.OTRAS_SENTENCIAS, new TokenPuntoyComa()}, {NT.MACROS},{NT.DEFINICION_STRUCT},{NT.DEFINICION_ENUM},{NT.DEFINICION_UNION},{NT.DEFINICION_TYPEDEF}},
/* OTRAS_SENTENCIAS,*/	{ {PalRes.PAL_RES_break},{PalRes.PAL_RES_continue},{PalRes.PAL_FUN_printf,parentesisAbierto,NT.ENTRECOMILLADO,NT.RPRINTF,parentesisCerrado},{PalRes.PAL_FUN_scanf,parentesisAbierto,NT.ENTRECOMILLADO,NT.RSCANF,parentesisCerrado},{PalRes.PAL_RES_return,NT.EXP} },
/* ENTRECOMILLADO,*/    { {new TokenEntrecomillado(null)} },
/* RPRINTF,*/			{ {new TokenComa(),NT.REFERENCIA,NT.INDIRECCION,NT.RPRINTF2}, {new TokenLambda()} },
/* REFERENCIA,*/		{ {new TokenAmpersand()} , {new TokenLambda()} },

/* RPRINTF2,*/			{ {NT.EXP,NT.RPRINTF} },
/* RSCANF,*/			{ {new TokenComa(),NT.REFERENCIA,NT.INDIRECCION,NT.RSCANF2}, {new TokenLambda()} },
/* RSCANF2,*/			{ {new TokenId(null),NT.CORCHETES} ,{new TokenLambda()} },
/* SENTENCIA_IF,*/		{ {PalRes.PAL_RES_if,parentesisAbierto,NT.EXP,parentesisCerrado,NT.RSENTENCIA_IF} },
/* RSENTENCIA_IF,*/		{ {NT.L_SENTENCIAS,NT.SENTENCIA_ELSE} },
/* SENTENCIA_ELSE,*/	{ {PalRes.PAL_RES_else,NT.L_SENTENCIAS}, {new TokenLambda()} },
/* SENTENCIA_BUCLE,*/	{ {PalRes.PAL_RES_do,NT.L_SENTENCIAS,PalRes.PAL_RES_while,parentesisAbierto,NT.EXP,parentesisCerrado}, {PalRes.PAL_RES_while,parentesisAbierto,NT.EXP,parentesisCerrado,NT.L_SENTENCIAS}, {PalRes.PAL_RES_for,parentesisAbierto,NT.CAMPO,new TokenPuntoyComa(),NT.CAMPO,new TokenPuntoyComa(),NT.CAMPO,parentesisCerrado,NT.L_SENTENCIAS} },
/* CAMPO,*/				{ {new TokenLambda()} ,{NT.EXP} },



/* SENTENCIA_CASE,*/	{ {PalRes.PAL_RES_switch,parentesisAbierto,NT.EXP,parentesisCerrado,NT.L_CASES} },
/* L_CASES,*/			{ {NT.CASE} ,{llaveAbierta,NT.CASES,llaveCerrada} },
/* CASES,*/				{ {PalRes.PAL_RES_case,NT.EXP,dosPuntos,NT.RCASES}, {PalRes.PAL_RES_default,dosPuntos,NT.RCASES2},{new TokenLambda()} },
/* RCASES,*/			{ {NT.BLOQUE_SENTENCIAS,NT.CASES}, {new TokenLambda()} },
/* RCASES2,*/			{ {NT.BLOQUE_SENTENCIAS}, {new TokenLambda()} },
/* CASE,*/				{ {PalRes.PAL_RES_case,NT.EXP_COND,dosPuntos,NT.BLOQUE_SENTENCIAS}, {PalRes.PAL_RES_default,dosPuntos,NT.BLOQUE_SENTENCIAS} },

/* EXP,*/				{{NT.EXP_COND, NT.REXP}},
/* REXP,*/				{{NT.OP_ASIG, NT.EXP} , {new TokenLambda()}},
/*OP_ASIG*/				{{asig}, {suma_Asig},{resta_Asig},{mult_Asig},{div_Asig},{desp_izq_Asig},{desp_der_Asig},
						 {andB_Asig},{orB_Asig},{xorB_Asig},{mod_Asig} },
/* EXP_COND,*/			{{NT.EXP_ORL, NT.REXP_COND}},
/* REXP_COND,*/			{{new TokenOpTernario(TokenOpTernario.TipoTokenOpTernario.INTERROGACION), NT.EXP,new TokenOpTernario(TokenOpTernario.TipoTokenOpTernario.DOSPUNTOS), NT.EXP,NT.REXP_COND},{new TokenLambda()}},
/* EXP_ORL,*/			{{NT.EXP_ANDL, NT.REXP_ORL}},
/* REXP_ORL,*/			{{new TokenOrL(), NT.EXP_ORL}, {new TokenLambda()}},
/* EXP_ANDL,*/			{{NT.EXP_ORB, NT.REXP_ANDL}},
/* REXP_ANDL,*/			{{new TokenAndL(), NT.EXP_ANDL}, {new TokenLambda()}},
/* EXP_ORB,	*/			{{NT.EXP_XORB, NT.REXP_ORB}},
/* REXP_ORB,*/			{{new TokenOrB(), NT.EXP_ORB}, {new TokenLambda()}},
/* EXP_XORB,*/			{{NT.EXP_ANDB, NT.REXP_XORB}},
/* REXP_XORB,*/			{{new TokenXorB(), NT.EXP_XORB}, {new TokenLambda()}},
/* EXP_ANDB,*/			{{NT.EXP_REL, NT.REXP_ANDB}},
/* REXP_ANDB,*/			{{new TokenAmpersand(), NT.EXP_ANDB}, {new TokenLambda()}},
/* EXP_REL,*/			{{NT.EXP_COMP, NT.REXP_REL}},
/* REXP_REL,*/			{{NT.OP_REL, NT.EXP_REL}, {new TokenLambda()}},
/* OP_REL,*/			{{new TokenRelIgual(TokenRelIgual.TipoTokenRelIgual.DISTINTO)}, {new TokenRelIgual(TokenRelIgual.TipoTokenRelIgual.IGUAL)}},
/* EXP_COMP,*/			{{NT.EXP_DESPL, NT.REXP_COMP}},
/* REXP_COMP,*/			{{NT.OP_COMP, NT.EXP_COMP}, {new TokenLambda()}},
/* OP_COMP,*/			{{new TokenRelComp(TokenRelComp.TipoTokenRelComp.MENOR)}, {new TokenRelComp(TokenRelComp.TipoTokenRelComp.IGUAL_MENOR)}, {new TokenRelComp(TokenRelComp.TipoTokenRelComp.MAYOR)}, {new TokenRelComp(TokenRelComp.TipoTokenRelComp.IGUAL_MAYOR)}},
/* EXP_DESPL,*/			{{NT.EXP_AD, NT.REXP_DESPL}},
/* REXP_DESPL,*/		{{NT.OP_DESPL, NT.EXP_DESPL}, {new TokenLambda()}},
/* OP_DESPL,*/			{{new TokenOpDespl(TokenOpDespl.TipoTokenOpDespl.DESPL_DER)},{new TokenOpDespl(TokenOpDespl.TipoTokenOpDespl.DESPL_IZQ)}},
/* EXP_AD,*/			{{NT.EXP_MULT, NT.REXP_AD}},
/* REXP_AD,*/			{{NT.OP_AD, NT.EXP_AD}, {new TokenLambda()}},
/* OP_AD,*/				{{new TokenSimboloAdicion(TokenSimboloAdicion.TipoTokenSimboloAdicion.SUMA)}, {new TokenSimboloAdicion(TokenSimboloAdicion.TipoTokenSimboloAdicion.RESTA)}},
/* EXP_MULT,*/			{{NT.EXP1, NT.REXP_MULT}},
/* REXP_MULT,*/			{{NT.OP_MULT, NT.EXP_MULT},{new TokenLambda()}},
/* OP_MULT,*/			{{new TokenAsterisco()}, {new TokenOpMult(TokenOpMult.TipoTokenOpMult.DIV)}, {new TokenOpMult(TokenOpMult.TipoTokenOpMult.MOD)}},
/* EXP1,*/				{{NT.EXP2}, {NT.OP_UNARIOS, NT.EXP1}, {PalRes.PAL_RES_sizeof, parentesisAbierto, NT.EXP1, parentesisCerrado}},
/* OP_UNARIOS,*/		{{new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.NOT_B)}, {new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.NOT_L)}, {new TokenAsterisco()}, {new TokenAmpersand()}, {NT.OP_AD},{NT.OP_INC}},
/* OP_INC,*/			{{new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.INCREMENTO)},{new TokenOpUnario(TokenOpUnario.TipoTokenOpUnario.DECREMENTO)}},
/* EXP2,*/				{{NT.EXP3, NT.REXP2}},
/* REXP2,*/				{{NT.OP_INC},{NT.OP_SELECCION,NT.EXP3}, {new TokenLambda()}},
/* OP_SELECCION,*/		{{new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.PUNTO)},{new TokenOpSeleccion(TokenOpSeleccion.TipoTokenOpSeleccion.FLECHA)}},
/* EXP3,*/				{{NT.IDENTIFICADOR},{new TokenNumEntero(0)}, {new TokenNumReal(0)}, {parentesisAbierto, NT.REXP3}, {PalRes.PAL_ESP_NULL},{llaveAbierta,NT.LISTA_EXP,llaveCerrada},{new TokenComillasChar()}, {new TokenEntrecomillado(null)}},
/* LISTA_EXP,*/			{{new TokenLambda()}, {NT.EXP, new TokenComa(), NT.LISTA_EXP}},
/* REXP3,*/				{{NT.TIPO_PRIMITIVO,NT.INDIRECCION,parentesisCerrado,NT.EXP},{NT.EXP_SIN_IDEN,NT.REXP,parentesisCerrado},{NT.MODIFICADOR,NT.L_MODIFICADORES,NT.RTIPO,parentesisCerrado,NT.EXP},{new TokenId(null),NT.REXP3_2,parentesisCerrado,NT.AUX}},
/*EXP_SIN_IDEN*/		{ {new TokenAmpersand(),NT.INDIRECCION,new TokenId(null),NT.RIDENTIFICADOR},{NT.INDIRECCION2,new TokenId(null),NT.RIDENTIFICADOR},
     	                  {new TokenNumEntero(0)},{new TokenNumReal(0)},{parentesisAbierto,NT.REXP3},{PalRes.PAL_ESP_NULL},{llaveAbierta,NT.LISTA_EXP,llaveCerrada},{new TokenComillasChar()},{NT.ENTRECOMILLADO} },
/*REXP3_2*/				{ {NT.INDIRECCION2}, {NT.RIDENTIFICADOR,NT.REXPRESIONES} },

/*REXPRESIONES*/				{ {NT.OP_ASIG,NT.EXP}, {NT.REXP}, {NT.REXP_COND},{NT.REXP_ORL},{NT.REXP_ANDL},{NT.REXP_ORB},{NT.REXP_XORB},{NT.REXP_ANDB},{NT.REXP_REL},{NT.REXP_COMP},{NT.REXP_DESPL},{NT.REXP_AD},{NT.REXP_MULT},{NT.OP_SELECCION,NT.EXP3}},
/*INDIRECCION2*/		{ {new TokenAsterisco(),NT.INDIRECCION} },
/* L_PARAMS_LLAMADA,*/	{{NT.EXP, NT.RES_LISTA_PARAMS_LLAMADA},{new TokenLambda()}},
/* RES_LISTA_PARAMS_LLAMADA*/ 	{{new TokenComa(), NT.EXP, NT.RES_LISTA_PARAMS_LLAMADA}, {new TokenLambda()}},
/*AUX*/					{ {NT.EXP}, {new TokenLambda()} },	

/*REXP4*/				{ {NT.TIPO_PRIMITIVO,NT.INDIRECCION,NT.RDEFINICION},{NT.EXP_SIN_IDEN,NT.REXP, new TokenPuntoyComa()},{NT.MODIFICADOR,NT.L_MODIFICADORES,NT.RTIPO,NT.RDEFINICION},{new TokenId(null),NT.REXP3_2,NT.RDEFINICION}}
	
	
	};
	
	
	

	
	
	
	
	
	
	
	
	
	

	
}
