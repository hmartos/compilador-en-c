package accionesEspecificas;

import gestorErrores.ErrorCompilador;
import gestorErrores.ErrorSemantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import codigoIntermadio.CodigoIntermedio;

import tablaSimbolos.Atributo;
import tablaSimbolos.AtributosTablaFuncion;
import tablaSimbolos.AtributosTablaVariable;
import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaSimbolos;
import token.Token;
import acciones.Accion;
import acciones.Tipo;

public class AccionR3_2 extends Accion {

/*3. DEFINICION_GLOBAL -> */
	/*3.2. TIPO RDEFINICION*/
	
	@Override
	public ArrayList<ErrorCompilador> ejecutar(ArrayList<Object> listaAtrib,
			HashMap<String, Object> atribActual, TablaSimbolos ts,CodigoIntermedio ci) {
		
		ArrayList<ErrorCompilador> listErr= new ArrayList<ErrorCompilador>();

		
		Object t0 = ((HashMap)listaAtrib.get(0)).get("tipo");
		//String t1 = (String)((HashMap)listaAtrib.get(1)).get("tipo");
		Boolean esFun = (Boolean)((HashMap)listaAtrib.get(1)).get("esFuncion");
		int colErr = (Integer)(atribActual.get("colInicio"));
		int rowErr = (Integer)(atribActual.get("filaInicio"));
		
		if (t0 instanceof Tipo ){
			Tipo tipo0=(Tipo) t0;
			
			if (!esFun){	
				
				int numCorchetes = (Integer)((HashMap)listaAtrib.get(1)).get("num");
	
				tipo0.setDim(tipo0.getDim()+numCorchetes);
				//Aqui habría un error en la gramatica por el que solo se puede poner corchetes en la primera variable.
				
				ArrayList<Object> listaVar = (ArrayList<Object>)((HashMap)listaAtrib.get(1)).get("listaVar");
				ArrayList<Object> listaValor = (ArrayList<Object>)((HashMap)listaAtrib.get(1)).get("listaValor");
				boolean valido =true;
				for (Iterator<Object> itVal=listaValor.iterator();itVal.hasNext();){
					Object o= itVal.next();
					if (o!=null && !tipo0.equals(o)){
						valido=false;
						listErr.add(new ErrorSemantico(rowErr,colErr,"No coinciden los tipos: "+tipo0.toString()+" y "+ o.toString()));
						atribActual.put("error", true);
					}
				}
				if (valido){
					for (Iterator<Object> itVar=listaVar.iterator();itVar.hasNext();){
						Object lex= itVar.next();
						if(ts.busquedaAmbito((String)lex)==null){
							ts.insertar((String)lex);
							ts.añadirAtributos((String)lex, new AtributosTablaVariable(tipo0.getTipo(),tipo0.getDim(),null));
						}else {
							atribActual.put("error", true);
							listErr.add(new ErrorSemantico(rowErr,colErr,"Ya existe la variable: "+lex.toString()+" en este contexto."));
						}

					}
				
				}
				
				
				
			}else{ // esFun
				
				Boolean esProt = (Boolean)((HashMap)listaAtrib.get(1)).get("esPrototipo");
				String nombre  = (String)((HashMap)listaAtrib.get(1)).get("nombreFun");

				if (!esProt){
					ArrayList<Tipo> listaTipo = (ArrayList<Tipo>)((HashMap)listaAtrib.get(1)).get("listaTipo");
					ArrayList<String> listaIden = (ArrayList<String>)((HashMap)listaAtrib.get(1)).get("listaIden");
					EntradaTabla et=ts.busquedaAmbito(nombre); //Ambito o global?
					if (et!=null){
						Atributo at =et.getAtt();
						if (at instanceof AtributosTablaFuncion) {
							AtributosTablaFuncion attF= (AtributosTablaFuncion) at;
							boolean valido=(attF.getDimRet()==tipo0.getDim() && attF.getTipoRet().equals(tipo0.getTipo()));
							if (valido){
								int listaMax= Math.max(attF.getListaTipos().size(), listaTipo.size());
								for(int i=0;i<listaMax;i++){
									Tipo t=null;
									if (i<listaTipo.size())t=listaTipo.get(i);
									String s=null;
									Integer d=null;
									if (i<attF.getListaTipos().size()){
										s =attF.getListaTipos().get(i);
										d= attF.getListaDim().get(i);
									}
									if ((t==null ||s==null||d==null)||!(t.getTipo().equals(s)&& t.getDim()==d))
									{
										valido=false;
									}
								}
								if (valido){
									ts.añadirAtributos(nombre, new AtributosTablaFuncion(tipo0.getTipo(), tipo0.getDim(), attF.getnCampos(), attF.getListaTipos(), listaIden, attF.getListaDim()));
								} else{
									atribActual.put("error", true);
									listErr.add(new ErrorSemantico(rowErr,colErr,"No coinciden los tipos de los parametros en el prototipo y el cuerpo de la funcion")); 
								}
							}else {
								atribActual.put("error", true);
								listErr.add(new ErrorSemantico(rowErr,colErr,"No coinciden los tipos de retorno en el prototipo y el cuerpo de la funcion")); 
							}
						}else{
							atribActual.put("error", true);
							listErr.add(new ErrorSemantico(rowErr,colErr,"El identificador "+nombre+" no está declarado como función.")); 
						}
					} else{
						atribActual.put("error", true);
						listErr.add(new ErrorSemantico(rowErr,colErr,"Debe de declararse un prototipo antes del cuerpo de la función.")); 
					}
					
				}else { //es prototipo
					ArrayList<Tipo> listaTipo = (ArrayList<Tipo>)((HashMap)listaAtrib.get(1)).get("listaTipo");
					EntradaTabla et=ts.busquedaCompleta(nombre);
					if (et==null){
						ts.insertar(nombre);
						ArrayList<Integer> listaDim =new ArrayList<Integer>();
						ArrayList<String> listaStringTipo =new ArrayList<String>();
						for (Iterator<Tipo> itT=listaTipo.iterator();itT.hasNext();){
							Tipo t=itT.next();
							if (!t.getTipo().equals("void")){ //Evita que se creen tipos si es void.
								listaDim.add(t.getDim());
								listaStringTipo.add(t.getTipo());
							}
						}
						ts.añadirAtributos(nombre, new AtributosTablaFuncion(tipo0.getTipo(), tipo0.getDim(), listaStringTipo.size(), listaStringTipo, null, listaDim));
					
					}else {
						atribActual.put("error", true);
						listErr.add(new ErrorSemantico(rowErr,colErr,"Ya se ha declarado un identificador "+nombre.toString())); 	
					}
				}

			}
		}else{atribActual.put("tipo", "error");
			
		}
		return listErr;
		
		
		
	}

}
