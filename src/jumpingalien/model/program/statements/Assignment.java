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
	
	public String getVariableName(){
		return this.variableName;
	}
	
	private final String variableName;
	
	public Type getType(){
		return this.variableType;
	}
		
	private final Type variableType;
	
	public Expression<? extends Type> getValue(){
		return this.value;
	}
		
	private final Expression<? extends Type> value;
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			program.setVariable(this.variableName, this.value.execute(program));
			this.statementUsed = true;
		}else{
			throw new IllegalStateException("Statement executed while not having next useful statement!");
		}
		
	}
	
}
