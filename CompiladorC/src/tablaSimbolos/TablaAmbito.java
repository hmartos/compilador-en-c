package tablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class TablaAmbito{
	TablaAmbito(){
		tabla=new HashMap<String,EntradaTabla>();
		contenido = new ArrayList<TablaAmbito>();
		contenedor=null;
	}
	
	public HashMap<String,EntradaTabla> tabla;
	public TablaAmbito contenedor;
	public ArrayList<TablaAmbito> contenido;
	
	
	
	
	
	public String toString(){
		
		String s="";
		for (Iterator<String> it=tabla.keySet().iterator();it.hasNext();){
			s+=it.next()+"\n";
		}
		s+="Contenidas:"+ contenido.size()+"\n";
		
		return s;
		
	}
}
