package jumpingalien.model.program.expressions;

import java.util.function.Function;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Unary Operators as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class UnaryOperator<I extends Type, T extends Type> extends Expression<T>{
	
	/* Constructor */
	
	public UnaryOperator(final Expression<I> expr, final Function<I, T> operator, final SourceLocation sourceLocation){
		super( sourceLocation);

		this.operand = expr;
		this.operator = operator;
	}

	/* Operand */
	
	@Basic @Immutable
	public Expression<I> getOperand() {
		return this.operand;
	}

	private final Expression<I> operand;
	
	/* Operator */
	
	@Basic @Immutable
	public Function<I, T> getOperator(){
		return this.operator;
	}
	
	private final Function<I, T> operator;
	
	/* Execution */
	
	@Override
	public T execute(final Program program) {
		 return getOperator().apply(getOperand().execute(program));
	}
	
}
