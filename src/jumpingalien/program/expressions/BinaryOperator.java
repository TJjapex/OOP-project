package jumpingalien.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;
import jumpingalien.program.types.*;

/**
 * A class of Binary Operators as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class BinaryOperator<I extends Type, T extends Type> extends Expression<T>{
	
	/* Constructor */
	
	public BinaryOperator(Expression<I> left, Expression<I> right, BiFunction<I, I, T> operator, SourceLocation sourceLocation){
		super( sourceLocation);

		this.leftOperand = left;
		this.rightOperand = right;
		this.operator = operator;
	}

	/* Operands */
	
	@Basic @Immutable
	public Expression<I> getLeftOperand() {
		return leftOperand;
	}

	private final Expression<I> leftOperand;
	
	@Basic @Immutable
	public Expression<I> getRightOperand() {
		return rightOperand;
	}
	
	private final Expression<I> rightOperand;
	
	/* Operator */
	
	@Basic @Immutable
	public BiFunction<I, I, T> getOperator(){
		return this.operator;
	}
	
	private final BiFunction<I, I, T> operator;
	
	/* Execution */
	
	@Override
	public T execute(Program program) {
		 return getOperator().apply(getLeftOperand().execute(program), getRightOperand().execute(program));
	}

}
