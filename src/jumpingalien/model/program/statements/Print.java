package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.*;

public class Print extends Statement {

	public Print(Expression<?> expression){
		this.expression = expression;
	}
	
	public Expression<?> getExpression(){
		return this.expression;
	}
	
	Expression<?> expression;
	
	void execute(){
		System.out.println(expression.getResult());
	}
	
}
