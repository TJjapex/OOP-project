package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.*;

public class WhileDo extends Statement {

	public WhileDo(Expression condition, Statement Body){
		this.condition = condition;
		this.body = body;
	}
	
	public Expression getCondition(){
		return this.condition;
	}
	
	Expression condition;
	
	public Statement getBody(){
		return this.body;
	}
	
	Statement body;
	
	void execute(){
		while ( this.getCondition().getResult() == true ){
			this.getBody().executeAll();
		}
	}
	
}
