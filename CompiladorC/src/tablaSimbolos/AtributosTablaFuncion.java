package tablaSimbolos;

import java.util.ArrayList;

public class AtributosTablaFuncion extends Atributo {

	String tipoRet;
	int dimRet;
	int nCampos;
	ArrayList<String> listaTipos;
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
	
	
	
}
