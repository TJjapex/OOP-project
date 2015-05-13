package jumpingalien.model.program.expressions;

import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class ReadVariable<T extends Type> extends Expression<Type>{	

	public ReadVariable(String variableName, Type variableType, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
		this.variableType = variableType;
	}
	
//	public Variable(T value, SourceLocation sourceLocation) {
//		super(sourceLocation);
//	}
	
//	public void setValue(T newValue){ // moet accessible zijn voor assignment
//		this.value = newValue; // probleem om variabele van waarde te veranderen door mee te geven aan constructor??
//	}
	
//	private final T value;

//	public void setType(Type newType){
//		this.type = newType;
//	}
	
//	private Type type;
	
	
	private final String variableName;
	private final Type variableType;

	@Override
	public Type execute(Program program) {
		return program.getVariable(variableName);
	}
	
}
