package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class CondicionParametrosFuncionTS extends ElemBinario implements Condicion {

	

	public CondicionParametrosFuncionTS(Operando op1, Operando op2) {
		super(op1, op2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper2.getValor(listaAtrib, atribActual, ts);
		
		if (op1 instanceof String && op2 instanceof ArrayList){
			Atributo t=ts.busquedaCompleta((String)op1).getAtt();
			ArrayList<Tipo> listParam= (ArrayList<Tipo>) op2;
			if (t instanceof AtributosTablaFuncion){
				AtributosTablaFuncion tf=(AtributosTablaFuncion)t;
				boolean correcto=true;
				int i=0;
				while (correcto && i<tf.getnCampos()){
					correcto=tf.getListaTipos().get(i).equals(listParam.get(i).getTipo()) 
							&& tf.getListaDim().get(i).equals(listParam.get(i).getDim());
					i++;
				}
				return correcto;
			}
		}
		return false;
		
	}

}
