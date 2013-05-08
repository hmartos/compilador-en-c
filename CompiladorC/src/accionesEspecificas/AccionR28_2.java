package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaTypeDef;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR28_2 extends Accion {
	

	/*28. L_PARAMS -> */
		/*28.2.  TIPO RL_PARAMS*/
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		int colErr = (Integer)(atribActual.get("colInicio"));
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		
		Boolean esProto = (Boolean)((HashMap)listaAtrib.get(1)).get("esPrototipo");
		if (!esProto){
			ArrayList<Tipo> listaTipo=(ArrayList<Tipo>) (atribActual.get("listaTipo"));
			ArrayList<String> listaIden= (ArrayList<String>)(atribActual.get("listaIden"));
			
			for (int i=0;i<listaIden.size();i++){
				String lex=listaIden.get(i);
				if (ts.busquedaAmbito(lex)==null){
					ts.insertar(lex);
					ts.añadirAtributos(lex, new AtributosTablaVariable(listaTipo.get(i).getTipo(),listaTipo.get(i).getDim(), null));
				}else {
					atribActual.put("error", true);
					listErr.add(new ErrorSemantico(rowErr,colErr,"El parametro "+lex.toString()+ " está duplicado.")); 
				}
			}
			
		}
		
		return listErr;
		
		
		
	}

}
