package jumpingalien.model.program.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Read Variables as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class ReadVariable<T extends Type> extends Expression<Type>{	

	/* Constructor */
	
	public ReadVariable(String variableName, Type variableType, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	/* Variable name */
	
	@Basic @Immutable
	public String getVariableName() {
		return this.variableName;
	}
	
	private final String variableName;

	/* Execution */
	
	@Override
	public Type execute(final Program program) {
		return program.getVariable(getVariableName());
	}
	
}
