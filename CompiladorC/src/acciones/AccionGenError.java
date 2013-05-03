package acciones;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class AccionGenError extends Accion {

	Operacion op,opRow,opCol;
	
	
	
	public AccionGenError(Operacion op1,Operacion op2, String s){
		this.op= new OperandoDirecto(s);
		opRow=op1;
		opCol=op2;
	}
	public AccionGenError(Operacion op1,Operacion op2, Operacion op){
		this.op= op;
		opRow=op1;
		opCol=op2;
	}
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		
		ArrayList<ErrorCompilador> l= new ArrayList<ErrorCompilador>( );
		l.add(new ErrorSemantico((Integer)opRow.getValor(listaAtrib, atribActual, ts),((Integer)opCol.getValor(listaAtrib, atribActual, ts)),op.getValor(listaAtrib, atribActual, ts).toString()));
		
		
		atribActual.put("error", true);
		return l;
		
	}

}
