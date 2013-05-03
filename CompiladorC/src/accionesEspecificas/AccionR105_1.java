package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR105_1 extends Accion {

/*3. DEFINICION_GLOBAL -> */
	/*3.2. TIPO RDEFINICION*/
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		
		Object t0 = ((HashMap)listaAtrib.get(0)).get("tipo");
		int n1 = (Integer)((HashMap)listaAtrib.get(1)).get("num");
		Boolean esFun = (Boolean)((HashMap)listaAtrib.get(2)).get("esFuncion");
		
		if (t0 instanceof Tipo ){
			Tipo tipo0=(Tipo) t0;
			// Metemos las indirecciones en el tipo
			tipo0.setDim(tipo0.getDim()+n1);
			
			
			if (!esFun){	// Es una declaracion de variable.
				
				int numCorchetes = (Integer)((HashMap)listaAtrib.get(1)).get("num");
	
				tipo0.setDim(tipo0.getDim()+numCorchetes);
				//Aqui habría un error en la gramatica por el que solo se puede poner corchetes en la primera variable.
				
				ArrayList<Object> listaVar = (ArrayList<Object>)((HashMap)listaAtrib.get(2)).get("listaVar");
				ArrayList<Object> listaValor = (ArrayList<Object>)((HashMap)listaAtrib.get(2)).get("listaValor");
				boolean valido =true;
				for (Iterator<Object> itVal=listaValor.iterator();itVal.hasNext();){
					Object o= itVal.next();
					if (o!=null && !tipo0.equals(o)){
						valido=false;
						int colErr = (Integer)(atribActual.get("filaInicio"));
						int rowErr = (Integer)(atribActual.get("colInicio"));

						listErr.add(new ErrorSemantico(rowErr,colErr,"No coinciden los tipos: "+tipo0.toString()+" y "+ o.toString()));
						atribActual.put("error", true);
					}
				}
				if (valido){
					for (Iterator<Object> itVar=listaVar.iterator();itVar.hasNext();){
						Object lex= itVar.next();
						if(ts.busquedaAmbito((String)lex)==null){
							ts.insertar((String)lex);
							ts.añadirAtributos((String)lex, new AtributosTablaVariable(tipo0.getTipo(),tipo0.getDim(),null));
						}else {
							atribActual.put("error", true);
							listErr.add(new ErrorSemantico("Ya existe la variable: "+lex.toString()+" en este contexto."));
						}

					}
				
				}
				
				
				
			}else{ // Es una declaracion de funcion.
				atribActual.put("error", true);
				listErr.add(new ErrorSemantico("No se puede definir una función en este contexto."));
			}
		}else{atribActual.put("tipo", "error");
			
		}
		return listErr;
		
		
		
	}

}
