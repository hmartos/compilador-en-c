package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperandoCrearTipo implements Operando {

	String tipo;
	int dim;
	public OperandoCrearTipo(String tipo, int dim){
		this.tipo=tipo;
		this.dim=dim;
	}
	
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		return new Tipo(tipo,dim);
	}

}
