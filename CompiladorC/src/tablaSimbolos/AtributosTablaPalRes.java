package tablaSimbolos;

import tablaSimbolos.TablaSimbolos.PalabraReservada;

public class AtributosTablaPalRes extends Atributo {

	PalabraReservada palabra;
	
	public AtributosTablaPalRes(PalabraReservada palabrasReservadas) {
		palabra=palabrasReservadas;
	}

	public PalabraReservada getPalabra() {
		return palabra;
	}

}
