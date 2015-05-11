package jumpingalien.model.program.expressions;

import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Operator<T> extends Expression<T> {
	
	protected Operator(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public abstract int getNbOperands();
	
	public abstract String getOperatorSymbol();

}