package jumpingalien.model.program.statements;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class Print extends Statement {

	public Print(final Expression<?> expression, final SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
	}
	
	@Basic @Immutable
	public Expression<?> getExpression(){
		return this.expression;
	}
	
	private final Expression<?> expression;
	
	@Override
	public void execute(Program program){
		System.out.println("Print " + this.getExpression().execute(program));
		this.statementUsed = true;
	}
	
}
