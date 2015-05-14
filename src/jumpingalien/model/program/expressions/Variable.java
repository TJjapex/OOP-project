package jumpingalien.model.program.expressions;

import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Variable<T extends Type> extends Expression<T>{	

	public Variable(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	public T getValue(){
		return this.value;
	}
	
	private final T value;

	@Override
	public T execute(Program program) {
		return this.getValue();
	}
	
}
