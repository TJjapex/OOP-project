package jumpingalien.model.program.expressions;

import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Variable<T> extends Expression<T>{	

	public Variable(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	private final T value;

	@Override
	public T execute(Program program) {
		return this.value;
	}
	
}
