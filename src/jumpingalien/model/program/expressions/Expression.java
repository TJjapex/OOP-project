package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T> {
	
	protected Expression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public abstract T getResult();
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;
}
