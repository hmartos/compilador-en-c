package tablaSimbolos;



public class AtributosTablaPalRes extends Atributo {

	PalRes palabra;
	
	public AtributosTablaPalRes(PalRes palabrasReservadas) {
		palabra=palabrasReservadas;
	}

	public PalRes getPalabra() {
		return palabra;
	}

	public String getLexema(){
		return palabra.toString().substring(8);
	}
}
