package analizadorSintactico;

import tablaSimbolos.PalRes;
import token.Token;
import token.Token.TipoToken;
import token.TokenAsig.TipoTokenAsig;
import token.TokenCorchetes.TipoTokenCorchetes;
import token.TokenLlaves.TipoTokenLlaves;
import token.TokenOpTernario.TipoTokenOpTernario;
import token.TokenOpUnario.TipoTokenOpUnario;
import token.TokenParentesis.TipoTokenParentesis;
import token.TokenRelComp.TipoTokenRelComp;

public class Primeros {
	
	/*Se mira si un token pertenece a los primeros de un no terminal para que si tenemos las reglas
	 * A ::= B | CD
	 * B ::= w
	 * C ::= (lambda) | s
	 * D ::= r
	 * 
	 * y estamos evaluando en A hacia donde construir el ‡rbol y leemos el token r, hacemos:
	 * 
	 * Àr pertenece a los primeros de B? No.
	 * Àlambda pertenece a los primeros de B? No. Entonces desechamos la regla A ::= B
	 * 
	 * Àr pertenece a los primeros de C? No.
	 * Àlambda pertenece a los primeros de C? S’, pues entonces seguimos con esa regla
	 * Àr pertenece a los primeros de D? S’, pues usamos la regla A ::= CD
	 * 
	 */
	public static boolean main(NT estado, Token tActual){
		boolean ret = false;
		switch(estado){
			case RES_LISTA_LLAMADA: //Primeros = TokenComa y Lambda
				//Si estamos en las derivaciones de este no terminal
				//si el token que viene es , o lambda pertenece a los primeros, entonces continuamos con la regla
				if(tActual.getTipo().equals(TipoToken.COMA) || tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}
				break; //si no es ninguno de esos, salimos del switch y se devuelve false
			case L_PARAMS_LLAMADA:// Primeros = Primeros de EXP y lambda.
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){ //Si es lambda, bien
					ret = true;
				}else{
					ret = main(NT.EXP, tActual); //Si el token pertenece a los primeros de EXP, devolver‡ true, si no false
				}
				break;
			case REXP3_2:
				if(main(NT.EXP, tActual) || main(NT.TIPO, tActual)){
					ret = true;
				}
				break;
			case REXP3: // Primeros = TokenParentesis.ABIERTO y Lambda
				if(tActual.getTipo().equals(TipoToken.PARENTESIS)){ //Si es un parentesis
					if(tActual.getAtributo().equals(TipoTokenParentesis.ABIERTO)){ //Tiene que ser abierto
						ret = true;
					}
				}else if(tActual.getTipo().equals(TipoToken.LAMBDA)){ //Si es lambda
					ret = true;
				}
				break;
			case LISTA_EXP:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.EXP, tActual); 
				}
				break;
			case EXP3:
				if(tActual.getTipo().equals(TipoToken.ID) || tActual.getTipo().equals(TipoToken.NUM_ENTERO) ||
						tActual.getTipo().equals(TipoToken.NUM_REAL)){
					ret = true;
				} else if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_ESP_NULL)){
						ret = true;
					}
				}else if(tActual.getTipo().equals(TipoToken.PARENTESIS)){
					if(tActual.getAtributo().equals(TipoTokenParentesis.ABIERTO)){
						ret = true;
					}
				}else if(tActual.getTipo().equals(TipoToken.LLAVE)){
					if(tActual.getAtributo().equals(TipoTokenLlaves.ABIERTO)){
						ret = true;
					}
				}else if(tActual.getTipo().equals(TipoToken.COMILLAS_CHAR)){
					ret = true;
				}else{
					ret = main(NT.ENTRECOMILLADO, tActual);
				}
				break;
			case OP_SELECCION:
				if(tActual.getTipo().equals(TipoToken.OP_SELECCION)){
					ret = true;
				}
				break;
			case REXP2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_INC, tActual) || main(NT.OP_SELECCION, tActual);
				}
				break;
			case EXP2:
				ret = main(NT.EXP3, tActual);
				break;
			case OP_INC:
				if(tActual.getTipo().equals(TipoToken.OP_UNARIO)){
					if(tActual.getAtributo().equals(TipoTokenOpUnario.DECREMENTO) ||
							tActual.getAtributo().equals(TipoTokenOpUnario.INCREMENTO)){
						ret = true;
					}
				}
				break;
			case OP_UNARIOS:
				if(main(NT.OP_INC, tActual) || main(NT.OP_AD, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.AMPERSAND) || tActual.getTipo().equals(TipoToken.ASTERISCO)){
					ret = true;
				}else if(tActual.getAtributo().equals(TipoTokenOpUnario.NOT_L) || tActual.getAtributo().equals(TipoTokenOpUnario.NOT_B)){
					ret = true;
				}
				break;
			case EXP1:
				if(main(NT.EXP2, tActual) || main(NT.OP_UNARIOS, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getTipo().equals(PalRes.PAL_RES_sizeof)){
						ret = true;
					}
				}
				break;
			case OP_MULT:
				if(tActual.getAtributo().equals(TipoToken.ASTERISCO) || tActual.getTipo().equals(TipoToken.OP_MULT)){
					ret = true;
				}
				break;
			case REXP_MULT:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_MULT, tActual);
				}
				break;
			case EXP_MULT:
				ret = main(NT.EXP1, tActual);
				break;
			case OP_AD:
				ret = tActual.getTipo().equals(TipoToken.SIMBOLO_ADICION);
				break;
			case REXP_AD:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_AD, tActual);
				}
				break;
			case EXP_AD:
				ret = main(NT.EXP_MULT, tActual);
				break;
			case OP_DESPL:
				ret = tActual.getTipo().equals(TipoToken.OP_DESPL);
				break;
			case REXP_DESPL:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_DESPL, tActual);
				}
				break;
			case EXP_DESPL:
				ret = main(NT.EXP_AD, tActual);
				break;
			case OP_COMP:
				ret = tActual.getTipo().equals(TipoToken.REL_COMP);
				break;
			case REXP_COMP:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_COMP, tActual);
				}
				break;
			case EXP_COMP:
				ret = main(NT.EXP_DESPL, tActual);
				break;
			case OP_REL:
				ret = tActual.getTipo().equals(TipoToken.REL_IGUAL);
				break;
			case REXP_REL:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.OP_REL, tActual);
				}
				break;
			case EXP_REL:
				ret = main(NT.EXP_COMP, tActual);
				break;
			case REXP_ANDB:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.AMPERSAND);
				}
				break;
			case EXP_ANDB:
				ret = main(NT.EXP_REL, tActual);
				break;
			case REXP_XORB:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.XOR_B);
				}
				break;
			case EXP_XORB:
				ret = main(NT.EXP_ANDB, tActual);
				break;
			case REXP_ORB:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.OR_B);
				}
				break;
			case EXP_ORB:
				ret = main(NT.EXP_XORB, tActual);
				break;
			case REXP_ANDL:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.AND_L);
				}
				break;
			case EXP_ANDL:
				ret = main(NT.EXP_ORB, tActual);
				break;
			case REXP_ORL:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.OR_L);
				}
				break;
			case EXP_ORL:
				ret = main(NT.EXP_ANDL, tActual);
				break;
			case REXP_COND:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					if(tActual.getTipo().equals(TipoToken.OP_TERNARIO) &&
							tActual.getAtributo().equals(TipoTokenOpTernario.INTERROGACION)){
						return true;
					}
				}
				break;
			case EXP_COND:
				ret = main(NT.EXP_ORL, tActual);
				break;
			case REXP:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(main(NT.OP_ASIG, tActual)){
					ret = true;
				}else if(main(NT.REXP_COND, tActual)){
					ret = true;
				}
				break;
			case EXP:
				if(tActual.getTipo().equals(TipoToken.NUM_ENTERO) ||
						tActual.getTipo().equals(TipoToken.NUM_REAL) || tActual.getTipo().equals(TipoToken.ID)){
					ret = true;
				}else{
					if(tActual.getTipo().equals(TipoToken.PAL_RES)){
						if(tActual.getAtributo().equals(PalRes.PAL_RES_sizeof) ||
								tActual.getAtributo().equals(PalRes.PAL_ESP_NULL)){
							ret = true;
						}
					}else if(tActual.getTipo().equals(TipoToken.PARENTESIS)){
							if(tActual.getAtributo().equals(TipoTokenParentesis.ABIERTO)){
								ret = true;
							}
					}else{
						ret = main(NT.OP_UNARIOS, tActual);
					}
				}
				break;
			case CONSTANTE:
				ret = main(NT.EXP_COND, tActual);
				break;
			case CASE:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_case) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_default)){
						ret = true;
					}
				}
				break;
			case RCASES2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.BLOQUE_SENTENCIAS, tActual);
				}
				break;
			case RCASES:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(main(NT.BLOQUE_SENTENCIAS, tActual)){
					ret = true;
				}else{
					ret = main(NT.CASES, tActual);
				}
				break;
			case CASES:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_case) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_default)){
						ret = true;
					}
				}else{
					ret = tActual.getTipo().equals(TipoToken.LAMBDA);
				}
				break;
			case L_CASES:
				if(tActual.getTipo().equals(TipoToken.LLAVE)){
					ret = tActual.getAtributo().equals(TipoTokenLlaves.ABIERTO);
					
				}else{
					ret = main(NT.CASE, tActual);
				}
				break;
			case SENTENCIA_CASE:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_switch);
				}
				break;
			case RSENTENCIA_ASIG:
				ret = main(NT.EXP, tActual);
				break;
			case OP_ASIG:
				ret = tActual.getTipo().equals(TipoToken.ASIGNACION);
				break;
			case SENTENCIA_ASIG:
				ret = tActual.getTipo().equals(TipoToken.ID);
				break;
			case CAMPO:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.EXP, tActual);
				}
				break;
			case SENTENCIA_BUCLE:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_do) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_while) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_for)){
						ret = true;
					}
				}
				break;
			case SENTENCIA_ELSE:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_else);
				}
				break;
			case RSENTENCIA_IF:
				ret = main(NT.L_SENTENCIAS, tActual);
				break;
			case SENTENCIA_IF:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_if);
				}
				break;
			case RSCANF2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.ID);
				}
				break;
			case RSCANF:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case RPRINTF2:
				ret = main(NT.EXP, tActual);
				break;
			case INDIRECCION:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.ASTERISCO);
				}
				break;
			case REFERENCIA:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.AMPERSAND);
				}
				break;
			case RPRINTF:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case ENTRECOMILLADO:
				ret = tActual.getTipo().equals(TipoToken.ENTRECOMILLADO);
				break;
			case OTRAS_SENTENCIAS:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_break) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_continue) ||
							tActual.getAtributo().equals(PalRes.PAL_FUN_printf) ||
							tActual.getAtributo().equals(PalRes.PAL_FUN_scanf) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_return)){
						ret = true;
					}
				}
				break;
			case SENTENCIA:
				if(tActual.getTipo().equals(TipoToken.PUNTOYCOMA)){
					ret = true;
				}else if(main(NT.SENTENCIA_IF, tActual)){
					ret = true;
				}else if(main(NT.SENTENCIA_BUCLE, tActual)){
					ret = true;
				}else if(main(NT.EXP, tActual)){
					ret = true;
				}else if(main(NT.SENTENCIA_CASE, tActual)){
					ret = true;
				}else if(main(NT.OTRAS_SENTENCIAS, tActual)){
					ret = true;
				}
				break;
			case BLOQUE_SENTENCIAS:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(main(NT.SENTENCIA, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.LLAVE)){
					ret = tActual.getAtributo().equals(TipoTokenLlaves.ABIERTO);
				}
				break;
			case L_SENTENCIAS:
				if(main(NT.SENTENCIA, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.LLAVE)){
					ret = tActual.getAtributo().equals(TipoTokenLlaves.ABIERTO);
				}
				break;
			case RTIPO2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.ASTERISCO);
				}
				break;
			case TIPO_PRIMITIVO:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_int) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_float) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_double) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_char) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_void)){
						ret = true;
					}
				}
				break;
			case MODIFICADOR:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_auto) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_const) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_volatile) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_register) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_extern) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_unsigned) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_signed) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_static)){
						ret = true;
					}
				}
				break;
			case L_MODIFICADORES:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.MODIFICADOR,tActual);
				}
				break;
			case RTIPO:
				if(tActual.getTipo().equals(TipoToken.ID)){
					ret = true;
				}else{
					ret = main(NT.TIPO_PRIMITIVO, tActual);
				}
				break;
			case TIPO:
				if(main(NT.L_MODIFICADORES, tActual)){
					ret = true;
				}else{
					ret = main(NT.RTIPO, tActual);
				}
				break;
			case RL_PARAMS2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case RL_PARAMS:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.ID)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case L_PARAMS:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.TIPO, tActual);
				}
				break;
			case RDEF_FUNCION:
				if(tActual.getTipo().equals(TipoToken.LLAVE)){
					ret = tActual.getAtributo().equals(TipoTokenLlaves.ABIERTO);
				}else{
					ret = tActual.getTipo().equals(TipoToken.PUNTOYCOMA);
				}
				break;
			case RDEF_TYPEDEF:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_struct) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_union) ||
							tActual.getAtributo().equals(PalRes.PAL_RES_enum)){
						ret = true;
					}
				}else{
					ret = tActual.getTipo().equals(TipoToken.ID);
				}
				break;
			case DEFINICION_TYPEDEF:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret =tActual.getAtributo().equals(PalRes.PAL_RES_typedef);
				}
				break;
			case DEF_VAR:
				ret = main(NT.TIPO, tActual);
				break;
			case L_VARIABLES:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(main(NT.DEFINICION_STRUCT, tActual) ||
							main(NT.DEF_VAR, tActual) ||
							main(NT.DEFINICION_ENUM, tActual) ||
							main(NT.DEFINICION_UNION, tActual)){
					ret = true;
				}
				break;
			case DEFINICION_STRUCT:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_struct);
				}
				break;
			case RENUM2:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case RENUM:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.ASIGNACION)){
					ret = tActual.getAtributo().equals(TipoTokenAsig.ASIG);
				}else{
					ret = tActual.getTipo().equals(TipoToken.COMA);
				}
				break;
			case DEFINICION_ENUM:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_enum);
				}
				break;
			case CONTENIDO:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.EXP, tActual);
				}
				break;
			case CORCHETES:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.CORCHETE)){
					ret = tActual.getAtributo().equals(TipoTokenCorchetes.ABIERTO);
				}
				break;
			case LISTA_IDENS:
				if(tActual.getTipo().equals(TipoToken.ID)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.PUNTOYCOMA);
				}
				break;
			case DEFINICION_UNION:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					ret = tActual.getAtributo().equals(PalRes.PAL_RES_union);
				}
				break;
			case RIFDEF2:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_RES_else) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_elif)){
						ret = true;
					}
				}else{
					ret = main(NT.RMACRO, tActual);
				}
				break;
			case RIFDEF:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else if(main(NT.L_SENTENCIAS, tActual)){
					ret = true;
					
				}else{
					ret = tActual.getTipo().equals(TipoToken.ALMOHADILLA);
				}
				break;
			case RINCLUDE:
				if(main(NT.ENTRECOMILLADO, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.REL_COMP)){
					ret = tActual.getAtributo().equals(TipoTokenRelComp.MENOR);
				}
				break;
			case RMACRO:
				if(tActual.getTipo().equals(TipoToken.PAL_RES)){
					if(tActual.getAtributo().equals(PalRes.PAL_MAC_define) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_include) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_ifdef) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_undef) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_ifndef) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_error) ||
							tActual.getAtributo().equals(PalRes.PAL_MAC_pragma)){
						ret = true;
					}
				}
				break;
			case MACROS:
				ret = tActual.getTipo().equals(TipoToken.ALMOHADILLA);
				break;
			case RDEF_VARIABLE2:
				if(tActual.getTipo().equals(TipoToken.COMA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.PUNTOYCOMA);
				}
				break;
			case RDEF_VARIABLE:
				if(tActual.getTipo().equals(TipoToken.ASIGNACION)){
					ret = true;
				}else{
					ret = main(NT.RDEF_VARIABLE2, tActual);
				}
				break;
			case RDEFINICION2:
				if(main(NT.CORCHETES, tActual) || main(NT.RDEF_VARIABLE, tActual)){
					ret = true;
				}else if(tActual.getTipo().equals(TipoToken.PARENTESIS)){
					ret = tActual.getAtributo().equals(TipoTokenParentesis.ABIERTO);
				}
				break;
			case RDEFINICION:
				if(tActual.getTipo().equals(TipoToken.PUNTOYCOMA)){
					ret = true;
				}else{
					ret = tActual.getTipo().equals(TipoToken.ID);
				}
				break;
			case DEFINICION:
				if(main(NT.TIPO, tActual) ||
						main(NT.DEFINICION_STRUCT, tActual) ||
						main(NT.DEFINICION_UNION, tActual) ||
						main(NT.DEFINICION_ENUM, tActual)){
					ret = true;
				}
				break;
			case DEFINICION_GLOBAL:
				if(main(NT.DEFINICION_TYPEDEF, tActual) ||
						main(NT.DEFINICION, tActual) ||
						main(NT.MACROS, tActual)){
					ret = true;
				}
				break;
			case L_DEFINICIONES:
				if(tActual.getTipo().equals(TipoToken.LAMBDA)){
					ret = true;
				}else{
					ret = main(NT.DEFINICION_GLOBAL, tActual);
				}
				break;
			case PROGRAMA:
				if(tActual.getTipo().equals(TipoToken.FIN)){
					ret = true;
				}else{
					ret = main(NT.L_DEFINICIONES, tActual);
				}
				break;
		}
		return ret;
		
	}

}
