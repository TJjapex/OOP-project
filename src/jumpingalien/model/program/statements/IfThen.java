package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType> ) condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	Expression<BooleanType> condition;
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	Statement ifBody;
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	Statement elseBody;
	
	@Override
	public void execute(Program program){
		System.out.println("IfThen, executing, evaluating condition result: "+ this.getCondition().execute(program));
		if( this.getCondition().execute(program).getValue() ){
			this.getIfBody().execute(program);
		} else{
			this.getElseBody().execute(program);
		}
	}
	
}
