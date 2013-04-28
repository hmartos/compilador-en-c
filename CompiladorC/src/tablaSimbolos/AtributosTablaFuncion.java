package tablaSimbolos;

import java.util.ArrayList;

public class AtributosTablaFuncion extends Atributo {

	String tipoRet;
	int dimRet;
	int nCampos;
	ArrayList<String> listaTipos;
	ArrayList<String> listaIden; //Solo para dentro del cuerpo de la funcion
	
	
	
	public AtributosTablaFuncion(String tipoRet, int dimRet, int nCampos,
			ArrayList<String> listaTipos, ArrayList<String> listaIden,
			ArrayList<Integer> listaDim) {
		super();
		this.tipoRet = tipoRet;
		this.dimRet = dimRet;
		this.nCampos = nCampos;
		this.listaTipos = listaTipos;
		this.listaIden = listaIden;
		this.listaDim = listaDim;
	}
	public ArrayList<String> getListaIden() {
		return listaIden;
	}
	public void setListaIden(ArrayList<String> listaIden) {
		this.listaIden = listaIden;
	}
	ArrayList<Integer> listaDim;
	
	public String getTipoRet() {
		return tipoRet;
	}
	public void setTipoRet(String tipoRet) {
		this.tipoRet = tipoRet;
	}
	public int getDimRet() {
		return dimRet;
	}
	public void setDimRet(int dimRet) {
		this.dimRet = dimRet;
	}
	public int getnCampos() {
		return nCampos;
	}
	public void setnCampos(int nCampos) {
		this.nCampos = nCampos;
	}
	public ArrayList<String> getListaTipos() {
		return listaTipos;
	}
	public void setListaTipos(ArrayList<String> listaTipos) {
		this.listaTipos = listaTipos;
	}
	public ArrayList<Integer> getListaDim() {
		return listaDim;
	}
	public void setListaDim(ArrayList<Integer> listaDim) {
		this.listaDim = listaDim;
	}
	
	
	
	
	@Override
	public String toString() {
		return "AtributosTablaFuncion [tipoRet=" + tipoRet + ", dimRet="
				+ dimRet + ", nCampos=" + nCampos + ", listaTipos="
				+ listaTipos + ", listaIden=" + listaIden + ", listaDim="
				+ listaDim + "]";
	}
	
}
