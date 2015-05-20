package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Binary Operators as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class BinaryOperator<I extends Type, T extends Type> extends Operator<T>{
	
	public BinaryOperator(Expression<I> left, Expression<I> right, BiFunction<I, I, T> operator, SourceLocation sourceLocation){
		super( sourceLocation);

		this.leftOperand = left;
		this.rightOperand = right;
		this.operator = operator;
	}

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
	
	@Basic @Immutable
	public BiFunction<I, I, T> getOperator(){
		return this.operator;
	}
	
	private final BiFunction<I, I, T> operator;
	
	@Override
	public T execute(Program program) {
		 return getOperator().apply(getLeftOperand().execute(program), getRightOperand().execute(program));
	}

}
