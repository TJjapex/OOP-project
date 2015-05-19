package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment extends Statement {

	public Assignment(String variableName, Type variableType,
			Expression<? extends Type> value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
	}
	
	/* Variable name */
	
	public String getVariableName(){
		return this.variableName;
	}
	
	private final String variableName;
	
	/* Type */
	
	public Type getType(){
		return this.variableType;
	}
		
	private final Type variableType;
	
	/* Value */
	
	public Expression<? extends Type> getValue(){
		return this.value;
	}
		
	private final Expression<? extends Type> value;
	
	/* Execution */
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			program.setVariable(getVariableName(), getValue().execute(program));
			setStatementUsed(true);
		}else{
			throw new IllegalStateException("Statement executed while not having next useful statement!");
		}
		
	}
	
}
