package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T> {
	
	protected Expression(T result, SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
		this.result = result;
	}
	
	public T getResult(){
		return this.result;
	}
	
	private T result;
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	private final SourceLocation sourceLocation;
}
