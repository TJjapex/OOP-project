package jumpingalien.model.program.statements;


import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment extends Statement {

	public Assignment(String variableName, Type variableType,
			Expression<? extends Type> value, SourceLocation sourceLocation){
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
		this.sourceLocation = sourceLocation;
	}
	
	private final String variableName;
	private final Type variableType;
	private final Expression<? extends Type> value;
	private final SourceLocation sourceLocation;
	
	@Override
	void execute(Program program){
		program.setVariable(this.variableName, this.value.execute(program));
	}
}
