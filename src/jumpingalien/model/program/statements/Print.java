package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class Print extends Statement {

	public Print(Expression<?> expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
	}
	
	public Expression<?> getExpression(){
		return this.expression;
	}
	
	Expression<?> expression;
	
	@Override
	public void execute(Program program){
		System.out.println("Print " + this.getExpression().execute(program));
	}
	
}
