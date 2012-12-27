package gestorErrores;



public class ErrorLexico  extends ErrorCompilador{
	
	TipoErrorLexico tipoLexico;
	String buffer;
	char leido;
	
	//Constructor estandar con el ultimo caracter leido
	public ErrorLexico(int row,int col,String s,TipoErrorLexico tipoLexico,char leido){
		super(row,col,s);
		super.tipo=ErrorCompilador.TipoError.LEXICO;
		this.tipoLexico=tipoLexico;
		this.buffer=null;
		this.leido=leido;
		
	}
	//Constructor extra que a�ade el buffer.
	public ErrorLexico(int row,int col,String s,TipoErrorLexico tipoLexico,char leido,String buffer){
		super(row,col,s);
		super.tipo=ErrorCompilador.TipoError.LEXICO;
		this.tipoLexico=tipoLexico;
		this.buffer=buffer;
		this.leido=leido;
		
	}
	static public enum TipoErrorLexico{
		TIPO1,TIPO2,TIPO3,TIPO4,TIPO5
	};
	public String toString (){
		String cadena=super.toString()+": ";
		switch (tipoLexico){
		case TIPO1: 
			cadena+="Car�cter de entrada no permitido \'"+leido +"\'.";
			break;
		case TIPO2: 
			cadena+="Constante num�rica mal formada \'"+leido +"\'.";
			break;
		case TIPO3: 
			cadena+="Car�cter inesperado \'"+leido +"\' en este contexto.";
			break;
		case TIPO4: 
			cadena+="Entrecomillado mal formado \'"+leido +"\'.";
			break;
		case TIPO5: 
			cadena+="Car�cter mal formado \'"+leido +"\'.";
			break;
		}
		cadena+= " ("+row+","+column+")";
		if (buffer!=null) cadena+=" Buffer previo:  \""+buffer+ "\"";
		if (description!=null && description!="") cadena+= "\nDescripci�n: "+description;
		return cadena+="\n";
	}
	
	
}
