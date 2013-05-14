package acciones;

import gestorErrores.ErrorCompilador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import codigoFinal.LugarRM;
import codigoIntermadio.CodigoIntermedio;
import codigoIntermadio.InsIni;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;

public class AccionCerrarAmbito extends Accion {

	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		//Generacion de codigo
		int nParam=0;
		
		Collection<EntradaTabla> values=ts.getActual().tabla.values();
		//Estos son los parametros (con preasignacion de memoria en la regla 28.2)
		for (Iterator<EntradaTabla> itCont=values.iterator();itCont.hasNext();){
			EntradaTabla et= itCont.next();
			if (et.getDescriptDir()!=null) nParam++;
			
		}
		ArrayList<InstruccionIntermedio>listaCodNuevo= new ArrayList<InstruccionIntermedio>();
		
		// Estas variables son las locales (aun no tienen preasignacion de memoria)
		for (Iterator<EntradaTabla> itVarL=values.iterator();itVarL.hasNext();){
			EntradaTabla et= itVarL.next();
			if (et.getDescriptDir()==null){ 
				et.setDescriptDir(new LugarRM(false,nParam+12));
				
				//generacion de codigo (push para las variables locales)
				InsIni push=new InsIni();
				push.setValorIni(0);
				listaCodNuevo.add(push);
				
				nParam++;
			}
		}
	//generacion de codigo (push para las variables locales)
		
		new AccionGenericaCodigo().ejecutar(listaAtrib, atribActual, ts, ci);
		
		if (atribActual.get("codigo")!=null){
				((ArrayList<InstruccionIntermedio>)atribActual.get("codigo")).addAll(0,listaCodNuevo);
		}
		else {
			atribActual.put("codigo", listaCodNuevo);
		}
		//Aqui invertimos los valores de la pila para que sean desplazamientos hacia arriba con respecto al SP.
		for (Iterator<EntradaTabla> itDesc=values.iterator();itDesc.hasNext();){
			EntradaTabla et= itDesc.next();
			LugarRM lug= et.getDescriptDir();
			lug.setDescriptDirMem((values.size()+12)-lug.getDescriptDirMem()-1);
			et.setDescriptDir(lug);
			
		} 
		
		
		((ArrayList<InstruccionIntermedio>)atribActual.get("codigo")).add(0,new InstruccionIntermedio("openAmbito"+ci.ambitoNuevo()));
		
		((ArrayList<InstruccionIntermedio>)atribActual.get("codigo")).add(new InstruccionIntermedio("closeAmbito"+ci.ambitoNuevo()));

		ts.cerrarAmbito();
		return new ArrayList<ErrorCompilador>();
	}

}
