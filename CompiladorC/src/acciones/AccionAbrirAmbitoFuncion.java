package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class AccionAbrirAmbitoFuncion extends Accion {

	Operacion op;
	public AccionAbrirAmbitoFuncion(Operacion op) {
		this.op=op;
	}
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		Object valor =op.getValor(listaAtrib, atribActual, ts);
		if (valor instanceof String){
			EntradaTabla tabla= ts.busquedaCompleta((String)valor);
			if (tabla!=null && tabla.getAtt() instanceof AtributosTablaFuncion) {
				AtributosTablaFuncion attF=(AtributosTablaFuncion) tabla.getAtt();
				ts.nuevoAmbito();
				ts.insertar("0tipoRet"); //Que feo!!
				ts.añadirAtributos("0tipoRet", new AtributosTablaVariable(attF.getTipoRet(),attF.getDimRet(),null));
			}
		}
		return new ArrayList<ErrorCompilador>();
	}

}

