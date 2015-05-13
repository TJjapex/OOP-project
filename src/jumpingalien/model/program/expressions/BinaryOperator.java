package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryOperator<T extends Type, I extends Type> extends Operator<T>{
	
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
	
	private final BiFunction<I, I, T> operator;
	
	@Override
	public T execute(Program program) {
		 return operator.apply(getLeftOperand().execute(program), getRightOperand().execute(program));
	}
//	
//	@Override @Immutable @Basic
//	public final int getNbOperands() {
//		return 2;
//	}
//
//	@Override @Immutable
//	public final String getOperatorSymbol() {
//		return "+"; // method abstract maken? ook toepassen voor subtraction, division en multiplication
//					// denk niet dat deze methode mogelijk is voor alle gevallen? symbool kunt ge nie uit operator halen?
//	}
}
