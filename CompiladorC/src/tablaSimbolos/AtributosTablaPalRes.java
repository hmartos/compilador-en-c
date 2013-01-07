package tablaSimbolos;



public class AtributosTablaPalRes extends Atributo {

	PalRes palabra;
	
	public AtributosTablaPalRes(PalRes palabrasReservadas) {
		palabra=palabrasReservadas;
	}

	public PalRes getPalabra() {
		return palabra;
	}

}
