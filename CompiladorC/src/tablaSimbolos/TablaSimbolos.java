package tablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TablaSimbolos {
	TablaAmbito global;
	TablaAmbito actual;
	TablaAmbito reservadas;
	
	public static enum PalabraReservada{
		 PAL_RES_auto	  ,PAL_RES_double	  ,PAL_RES_int	  ,PAL_RES_struct	
		  ,PAL_RES_break	  ,PAL_RES_else	  ,PAL_RES_long	  ,PAL_RES_switch	
		  ,PAL_RES_case	  ,PAL_RES_enum	  ,PAL_RES_register	  ,PAL_RES_typedef	
		  ,PAL_RES_char	  ,PAL_RES_extern	  ,PAL_RES_return	  ,PAL_RES_union	
		  ,PAL_RES_const	  ,PAL_RES_float	  ,PAL_RES_short	  ,PAL_RES_unsigned	
		  ,PAL_RES_continue	  ,PAL_RES_for	  ,PAL_RES_signed	  ,PAL_RES_void	
		  ,PAL_RES_default	  ,PAL_RES_goto	  ,PAL_RES_sizeof	  ,PAL_RES_volatile	
		  ,PAL_RES_do	  ,PAL_RES_if	  ,PAL_RES_static	  ,PAL_RES_while,
		  
		  PAL_ESP_NULL,
		  
		  PAL_FUN_printf, PAL_FUN_scanf, PAL_MAC_include, PAL_MAC_ifdef, PAL_MAC_elif,
		  PAL_MAC_define, PAL_MAC_ifndef, PAL_MAC_error, PAL_MAC_pragma,PAL_MAC_undef
	}
	
	
	public TablaSimbolos(){
		global=new TablaAmbito();
		actual=global;
		reservadas= new TablaAmbito();
		for (int cont=0;cont<PalabraReservada.values().length;cont++){
			EntradaTabla entrada= new EntradaTabla();
			entrada.setAtt(new AtributosTablaPalRes(PalabraReservada.values()[cont]));
			if (PalabraReservada.values()[cont].toString().substring(4,7).compareTo("MAC")!=0)
					reservadas.tabla.put(PalabraReservada.values()[cont].toString().substring(8), entrada);
			else 	reservadas.tabla.put("#"+PalabraReservada.values()[cont].toString().substring(8), entrada);

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
			while(buscado==null && busquedaAct!=global){
				buscado=busquedaAct.tabla.get(lexema);
				busquedaAct=busquedaAct.contenedor; //Actualizamos por si no la ha encontrado.
			}
			return buscado;
		}
	}
	
	public EntradaTabla insertar (String lexema){
		actual.tabla.put(lexema, new EntradaTabla());
		return actual.tabla.get(lexema);
	}
	
	public EntradaTabla añadirAtributos (String lexema,Atributo atributos){
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
