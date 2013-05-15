package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InsCuarteto;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class CodigoR3_2 extends Accion {

/*3. DEFINICION_GLOBAL -> */
	/*3.2. TIPO RDEFINICION*/
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		
		Object t0 = ((HashMap)listaAtrib.get(0)).get("tipo");
		
		Boolean esFun = (Boolean)((HashMap)listaAtrib.get(1)).get("esFuncion");
		int colErr = (Integer)(atribActual.get("colInicio"));
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		
				
			if (!esFun){	
				
				int numCorchetes = (Integer)((HashMap)listaAtrib.get(1)).get("num");
	
				
				//Aqui habría un error en la gramatica por el que solo se puede poner corchetes en la primera variable.
				
				ArrayList<String> listaVar = (ArrayList<String>)((HashMap)listaAtrib.get(1)).get("listaVar");
				ArrayList<EntradaTabla> listaLugar= (ArrayList<EntradaTabla>)((HashMap)listaAtrib.get(1)).get("listaLugar");
				
				ArrayList<InstruccionIntermedio> listaCodigo= (ArrayList<InstruccionIntermedio>)((HashMap)listaAtrib.get(1)).get("codigo");
				
				for (int i=0; i<listaLugar.size();i++){
					String lexV=listaVar.get(i);
					EntradaTabla et= ts.busquedaAmbito(lexV);
					if (listaLugar.get(i)!=null){
						InsCuarteto cuarteto=new InsCuarteto();
						cuarteto.setRes(et);
						cuarteto.setOp1(listaLugar.get(i));
						listaCodigo.add(cuarteto);
					}
				}
				
				atribActual.put("codigo", listaCodigo);
				
				
				
				
				
				
			}else{ // esFun
				
				}
		
			
		
		return listErr;
		
		
		
	}

}
