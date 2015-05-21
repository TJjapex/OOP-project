package jumpingalien.model.program.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class ReadVariable<T extends Type> extends Expression<Type>{	

	public ReadVariable(String variableName, Type variableType, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	@Basic @Immutable
	public String getVariableName() {
		return this.variableName;
	}
	
	private final String variableName;

	@Override
	public Type execute(final Program program) {
		return program.getVariable(getVariableName());
	}
	
}
