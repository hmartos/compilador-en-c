package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.HashMap;

import tablaSimbolos.TablaSimbolos;
import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InstruccionIntermedio;

public class AccionAsignarEtiqueta extends Accion {

	Operacion opListaCod;
	Operacion opEtiqueta;
	int pos;
	
	
	
	public AccionAsignarEtiqueta(Operacion listaCod, Operacion etiqueta,
			int pos) {
		super();
		this.opListaCod = listaCod;
		this.opEtiqueta = etiqueta;
		this.pos = pos;
	}



	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,
			CodigoIntermedio ci) {

		Object listaCodAux=(Object)opListaCod.getValor(listaAtrib, atribActual, ts, ci);
		ArrayList<InstruccionIntermedio> listaCod=null;
		int nCod=0;
		
		if (listaCodAux!=null){ //Si hay codigo lo cogemos
			listaCod=(ArrayList<InstruccionIntermedio>) listaCodAux;
			nCod=listaCod.size();
		}else{//Si no hay creamos una lista nueva
			listaCod=new ArrayList<InstruccionIntermedio>();
			nCod=0;
			atribActual.put("codigo", listaCod);
		}
		
		String etiqueta=(String)opEtiqueta.getValor(listaAtrib, atribActual, ts, ci);
		
		if (pos<0)pos =listaCod.size()+pos; // con posiciones negativas contamos desde el final
		
		if (pos>=nCod||pos<0){ //Si no hay tantas instrucciones como pos metemos una instruccion vacía con la etiqueta al final.
			listaCod.add(listaCod.size(), new InstruccionIntermedio(etiqueta));
		}
		//si no ponemos la etiqueta donde corresponde.
		else listaCod.get(pos).setEtiqueta(etiqueta);
		
		return new ArrayList<ErrorCompilador>();
	}

}
