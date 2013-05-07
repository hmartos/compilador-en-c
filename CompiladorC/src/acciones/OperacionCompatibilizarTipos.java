package acciones;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;

public class OperacionCompatibilizarTipos extends ElemBinario implements Operacion {

	public OperacionCompatibilizarTipos(Operacion op1, Operacion op2) {
		super();
		oper1=new OperacionTipoTS(op1);
		oper2=new OperacionTipoTS(op2);
	}
	
	@Override
	public Object getValor(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts) {
		// TODO Auto-generated method stub
		
		Object op1=oper1.getValor(listaAtrib, atribActual, ts);
		Object op2=oper2.getValor(listaAtrib, atribActual, ts);
		
		
		if (op1==null ) return op2;
		else if (op2==null) return op1;
		
		
		Tipo t1, t2;
		
		t1=(Tipo) op1;
		t2=(Tipo) op2;
		if (t1.equals(t2)){
			return t1;
			// Habria que pensar si implementar operaciones entre punteros, como sumar un entero a un puntero.
		}else if (t1.getDim()==0 && t2.getDim()==0){
			String[][] tablaCompatibles=
			         //int  //char  //float  //double
			/*int*/{{ "int","char"  , "float", "double"},
			/*char*/{ "char", "char",  "float",    "double"},
			/*float*/{"float", "float",  "float","double"},
			/*double*/{"double","double","double","double"}};
			int i1=-1,i2=-1;
			if (t1.getTipo().equals("int")) i1=0;
			else if (t1.getTipo().equals("char")) i1=1;
			else if (t1.getTipo().equals("float")) i1=2;
			else if (t1.getTipo().equals("double")) i1=3;
			
			if (t2.getTipo().equals("int")) i2=0;
			else if (t2.getTipo().equals("char")) i2=1;
			else if (t2.getTipo().equals("float")) i2=2;
			else if (t2.getTipo().equals("double")) i2=3;
			
			if (i1>-1 && i1<tablaCompatibles.length && i2>-1 && i2<tablaCompatibles.length){
				String s=tablaCompatibles[i1][i2];
				if (s!=null) return new Tipo(s,0);
				else return null;
			}
		}
		return null;
		
	}

}
