package codigoFinal;

public class LugarRM {

	int descriptDirReg=-1; //-1 => no hay registro.
	int descriptDirMem=-1; //-1 => no está mem.
	int descriptDirPila=-1; //-1 => no está en la pila.
	boolean actualizadoReg;
	
	
	
	
	
	
	public boolean isActualizadoReg() {
		return actualizadoReg;
	}


	public void setActualizadoReg(boolean actualizadoReg) {
		this.actualizadoReg = actualizadoReg;
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
	public int getDescriptDirPila() {
		return descriptDirPila;
	}
	public void setDescriptDirPila(int descriptDirPila) {
		this.descriptDirPila = descriptDirPila;
	}
	
	
	
}
