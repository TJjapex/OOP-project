package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Type;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryOperator<T> extends Operator<T>{
	
	public BinaryOperator(Expression<T> left, Expression<T> right, BiFunction<T, T, T> operator, SourceLocation sourceLocation){
		super( operator.apply(right.getResult(), left.getResult()), sourceLocation);

		this.leftOperand = left;
		this.rightOperand = right;
		this.operator = operator;
	}

	@Basic @Immutable
	public Expression<T> getLeftOperand() {
		return leftOperand;
	}

	private final Expression<T> leftOperand;
	
	@Basic @Immutable
	public Expression<T> getRightOperand() {
		return rightOperand;
	}
	
	private final Expression<T> rightOperand;
	
	private final BiFunction<T, T, T> operator;
	
	public T getResult() {
		 return operator.apply(getLeftOperand().getResult(), getRightOperand().getResult());
	}
	
	@Override @Immutable @Basic
	public final int getNbOperands() {
		return 2;
	}

	@Override @Immutable
	public final String getOperatorSymbol() {
		return "+";
	}
}
