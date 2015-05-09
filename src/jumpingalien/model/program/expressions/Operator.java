package jumpingalien.model.program.expressions;

import jumpingalien.model.program.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Operator<T> extends Expression<T> {
	
	protected Operator(T result, SourceLocation sourceLocation){
		super(result, sourceLocation);
	}
	
	public abstract int getNbOperands();
	
	public abstract String getOperatorSymbol();

}
