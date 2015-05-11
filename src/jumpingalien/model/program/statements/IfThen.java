package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.*;

public class IfThen extends Statement {

	public IfThen(Expression condition, Statement ifBody, Statement elseBody){
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression getCondition(){
		return this.condition;
	}
	
	Expression condition;
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	Statement ifBody;
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	Statement elseBody;
	
	void execute(){
		if( this.getCondition().execute() == true){
			this.getIfBody().executeAll();
		} else{
			this.getElseBody().executeAll();
		}
	}
	
}
