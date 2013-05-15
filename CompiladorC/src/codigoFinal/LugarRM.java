package codigoFinal;

public class LugarRM {

	int descriptDirReg=-1; //-1 => no hay registro.
	int descriptDirMem=-1; //-1 => no está mem.
	String descriptDirGlobal=""; //-1 => no está en la pila.
	
	boolean actualizadoReg=true;
	
	
	
	
	
	
	public String getDescriptDirGlobal() {
		return descriptDirGlobal;
	}


	public void setDescriptDirGlobal(String descriptDirGlobal) {
		this.descriptDirGlobal = descriptDirGlobal;
	}


	public boolean isActualizadoReg() {
		if (descriptDirReg!=-1)return actualizadoReg;
		else return true;
	}


	public void setActualizadoReg(boolean actualizadoReg) {
		if (descriptDirReg!=-1)this.actualizadoReg = actualizadoReg;
		else this.actualizadoReg=true;
	}


	public LugarRM(boolean estaEnReg,int descriptDir) {
		super();
		if (estaEnReg)this.descriptDirReg = descriptDir;
		else {
			this.descriptDirReg=-1;
			this.descriptDirMem=descriptDir;
		}
	}
	
	
	public LugarRM(int descriptDirReg) {
		super();
		this.descriptDirReg = descriptDirReg;
	}


	public boolean estaEnRegistro(){
		return descriptDirReg!=-1;
	}
	
	
	public int getDescriptDirReg() {
		return descriptDirReg;
	}
	public void setDescriptDirReg(int descriptDirReg) {
		this.descriptDirReg = descriptDirReg;
	}
	public int getDescriptDirMem() {
		return descriptDirMem;
	}
	public void setDescriptDirMem(int descriptDirMem) {
		this.descriptDirMem = descriptDirMem;
	}
	
	
	
	
	
	
}
