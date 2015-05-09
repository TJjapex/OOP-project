package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Operator<T> extends Expression<T> {
	
	protected Operator(T result, SourceLocation sourceLocation){
		super(sourceLocation);
		this.result = result;
	}
	
	public T getResult(){
		return this.result;
	}
	
	private T result;
	
	public abstract int getNbOperands();
	
	public abstract String getOperatorSymbol();

}
