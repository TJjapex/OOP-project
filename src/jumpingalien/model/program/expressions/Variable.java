package jumpingalien.model.program.expressions;

import jumpingalien.model.program.*;

import jumpingalien.part3.programs.SourceLocation;

public class Variable<T> extends Expression<T>{

	public Variable(Type type, String name, T value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
	}
	
	public void setValue(Expression newValue){ // moet accessible zijn voor assignment
		this.value = newValue; // probleem om variabele van waarde te veranderen door mee te geven aan constructor??
	}
}
