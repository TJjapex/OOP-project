package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Variable<T> extends Expression<T>{

	public Variable(T value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
	}
}
