package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment extends Statement {
//
//	public Assignment(String name, Type type,
//			Expression<? extends Type> value, SourceLocation sourceLocation){
//		super(sourceLocation);
//		this.name = name;
//		this.type = type;
//		this.value = value;
//	}
//	
//	public Type getType(){
//		return this.type;
//	}
//	
//	private final Type type;
//	
//	public String getName(){
//		return this.name;
//	}
//	
//	private final String name;
//	
//	public Expression<? extends Type> getValue(){
//		return this.value;
//	}
//	
//	private final Expression<? extends Type> value;
//	
//	@Override
//	void execute(Program program){
//		program.setVariable(this.getName(), new Object[]{ this.getType() , 
//		                                                  new Variable<>(this.getValue(), this.getSourceLocation())});
//	}
	
	public Assignment(String variableName, Type variableType,
			Expression<? extends Type> value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
	}
	
	private final String variableName;
	private final Type variableType;
	private final Expression<? extends Type> value;
	
	@Override
	public void execute(Program program){
		program.setVariable(this.variableName, this.value.execute(program));
	}
}
