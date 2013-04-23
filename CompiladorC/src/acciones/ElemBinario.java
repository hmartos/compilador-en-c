package acciones;

public abstract class  ElemBinario {
	Operacion oper1;
	Operacion oper2;
	
	
	public ElemBinario(){
		oper1=null;
		oper2=null;
	}
	
	public ElemBinario(Operando op1,Operando op2){
		oper1=op1;
		oper2=op2;
	}
}
