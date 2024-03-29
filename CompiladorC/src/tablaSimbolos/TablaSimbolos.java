package tablaSimbolos;


public class TablaSimbolos {
	TablaAmbito global;
	TablaAmbito actual;
	TablaAmbito reservadas;
	
	
	
	
	
	
	
	
	
	
	public void setGlobal(TablaAmbito global) {
		this.global = global;
	}


	public void setActual(TablaAmbito actual) {
		this.actual = actual;
	}


	public TablaSimbolos(){
		global=new TablaAmbito();
		actual=global;
		reservadas= new TablaAmbito();
		for (int cont=0;cont<PalRes.values().length;cont++){
			EntradaTabla entrada= new EntradaTabla(PalRes.values()[cont].toString().substring(8));
			entrada.setAtt(new AtributosTablaPalRes(PalRes.values()[cont]));
			reservadas.tabla.put(PalRes.values()[cont].toString().substring(8), entrada);

		}
		
	}
	
	
	public EntradaTabla busquedaPalabraReservada (String lexema){
	
		return reservadas.tabla.get(lexema);
	}
	
	public EntradaTabla busquedaAmbito (String lexema){
		EntradaTabla buscado= busquedaPalabraReservada(lexema);
		if (buscado==null) return actual.tabla.get(lexema);
		else return buscado;
	}
	
	public EntradaTabla busquedaCompleta (String lexema){
		
		EntradaTabla buscado= busquedaPalabraReservada(lexema);
		if (buscado!=null) return buscado;
		else{
			TablaAmbito busquedaAct=actual;
			while(buscado==null&&busquedaAct!=null){
				buscado=busquedaAct.tabla.get(lexema);
				busquedaAct=busquedaAct.contenedor; //Actualizamos por si no la ha encontrado.
			}
			return buscado;
		}
	}
	
	
public int busquedaCompletaProfundidad (String lexema){
		
		EntradaTabla buscado= busquedaPalabraReservada(lexema);
		if (buscado!=null) return -1;
		else{
			TablaAmbito busquedaAct=actual;
			int prof=0;
			while(buscado==null&&busquedaAct!=null){
				buscado=busquedaAct.tabla.get(lexema);
				busquedaAct=busquedaAct.contenedor; //Actualizamos por si no la ha encontrado.
				prof++;
			}
			return prof-1;
		}
	}
	
	public EntradaTabla insertar (String lexema){
		actual.tabla.put(lexema, new EntradaTabla(lexema));
		return actual.tabla.get(lexema);
	}
	
	public EntradaTabla aņadirAtributos (String lexema,Atributo atributos){
		actual.tabla.get(lexema).setAtt(atributos);
		return actual.tabla.get(lexema);
	}
	
	public void nuevoAmbito(){
		TablaAmbito nuevaTabla =new TablaAmbito();
		actual.contenido.add(nuevaTabla);
		nuevaTabla.contenedor=actual;
		actual=nuevaTabla;
	}
	
	public void cerrarAmbito(){
		actual=actual.contenedor;
	}
	
	
	
	
	
	
	//FUNCIONES PARA LA INTERFAZ
	
	public TablaAmbito getGlobal() {
		return global;
	}


	public TablaAmbito getActual() {
		return actual;
	}


	public TablaAmbito getReservadas() {
		return reservadas;
	}
	
	
	
	public String tablaGlobalToString(){
		
		String cadena= "TablaGlobal:\n"+global.toString()+"\n";
		return cadena;
	}
	
	public String tablaReservadasToString(){
		
		String cadena= "TablaReservadas:\n"+reservadas.toString()+"\n";
		return cadena;
	}
	
}
