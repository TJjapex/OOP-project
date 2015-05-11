package jumpingalien.model.program.expressions;

import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Variable<T> extends Expression<T>{	

	public Variable(Type type, T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
//		this.type = type;
	}
	
	public Variable(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
//	public void setValue(T newValue){ // moet accessible zijn voor assignment
//		this.value = newValue; // probleem om variabele van waarde te veranderen door mee te geven aan constructor??
//	}
	
	private final T value;

//	public void setType(Type newType){
//		this.type = newType;
//	}
	
//	private Type type;
	

	@Override
	public T getResult() {
		return this.value;
	}
	
}
