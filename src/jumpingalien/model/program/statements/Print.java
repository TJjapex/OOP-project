package jumpingalien.model.program.statements;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.ProgramRuntimeException;
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
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			System.out.println(this.getExpression().execute(program));
			setStatementUsed(true);
		}else{
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
		}
		
	}
	
}
