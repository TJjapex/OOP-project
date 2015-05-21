package jumpingalien.model.program.statements;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Print Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Print extends Statement {

	/* Constructor */
	
	public Print(final Expression<?> expression, final SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
	}
	
	/* Expression */
	
	@Basic @Immutable
	public Expression<?> getExpression(){
		return this.expression;
	}
	
	private final Expression<?> expression;
	
	/* Execution */
	
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
