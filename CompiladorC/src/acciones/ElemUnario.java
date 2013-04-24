package acciones;

public abstract class  ElemUnario {
	Operacion oper;
	;
	
	public ElemUnario(){
		oper=null;
		
	}
	
	public ElemUnario(Operando op1){
		oper=op1;
		
	}
}
