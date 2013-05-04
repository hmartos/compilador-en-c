package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR25_1 extends Accion {
	

	/*25. DEFINICION_TYPEDEF -> */ 
		/*25.1. typedef RDEF_TYPEDEF*/
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		
		Object t0 = ((HashMap)listaAtrib.get(1)).get("tipo");
		Object lex = ((HashMap)listaAtrib.get(1)).get("lex");
		
		int colErr = (Integer)(atribActual.get("colInicio"));
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		
		if (t0 instanceof Tipo && lex instanceof String ){
			Tipo tipo = (Tipo) t0;
			String lexema = (String) lex;
			if (ts.busquedaAmbito(lexema)==null){
				ts.insertar(lexema);
				ts.añadirAtributos(lexema, new AtributosTablaTypeDef(tipo.getTipo(), tipo.getDim()));
			}else{
				atribActual.put("error", true);
				listErr.add(new ErrorSemantico(rowErr,colErr,"Ya se ha declarado un identificador "+lex.toString())); 	
			}
		}else{atribActual.put("tipo", "error");
			
		}
		return listErr;
		
		
		
	}

}
