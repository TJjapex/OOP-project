package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.*;

public class Assignment extends Statement {

	public Assignment(Variable variable, Expression expression){
		this.variable = variable;
		this.expression = expression;
	}
	
	public Variable getVariable(){
		return this.variable;
	}
	
	Variable variable;
	
	public Expression getExpression(){
		return this.expression;
	}
	
	Expression expression;
	
	void execute(){
		this.getVariable().setValue(expression);
	}
	
}
