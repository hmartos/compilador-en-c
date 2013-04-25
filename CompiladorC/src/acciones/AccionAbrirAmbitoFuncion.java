package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

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
			ts.nuevoAmbito();
			ts.insertar((String)valor);
			ts.añadirAtributos((String)valor, tabla.getAtt());
			
		}
		return new ArrayList<ErrorCompilador>();
	}

}
