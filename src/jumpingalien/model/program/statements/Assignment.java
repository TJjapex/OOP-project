package jumpingalien.model.program.statements;

import jumpingalien.model.program.VariableIdentifier;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment extends Statement {

	public Assignment(String variableName, Type variableType,
			Expression<?> value, SourceLocation sourceLocation){
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
		this.sourceLocation = sourceLocation;
	}
	
	private final String variableName;
	private final Type variableType;
	private final Expression<?> value;
	private final SourceLocation sourceLocation;
	
	void execute(){
		VariableIdentifier id = new VariableIdentifier(variableName, variableType);
		Class theClass = variableType.getClass();
		Variable<? extends Type> var = new Variable<theClass>(value.getResult(), sourceLocation);
	}
}
