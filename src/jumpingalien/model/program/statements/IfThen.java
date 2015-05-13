package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<?> getCondition(){
		return this.condition;
	}
	
	Expression<?> condition;
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	Statement ifBody;
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	Statement elseBody;
	
	@Override
	void execute(Program program){
		if( (Boolean) this.getCondition().execute(program) ){
			this.getIfBody().executeAll(program);
		} else{
			this.getElseBody().executeAll(program);
		}
	}
	
}
