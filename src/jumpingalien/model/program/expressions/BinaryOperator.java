package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class BinaryOperator<T> extends Operator<T>{
	
	public BinaryOperator(Expression<T> left, Expression<T> right, BiFunction<T, T, T> operator, SourceLocation sourceLocation){
		super( sourceLocation);

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
	
	@Override
	public T execute(Program program) {
		 return operator.apply(getLeftOperand().execute(program), getRightOperand().execute(program));
	}
	
	@Override @Immutable @Basic
	public final int getNbOperands() {
		return 2;
	}

	@Override @Immutable
	public final String getOperatorSymbol() {
		return "+"; // method abstract maken? ook toepassen voor subtraction, division en multiplication
					// denk niet dat deze methode mogelijk is voor alle gevallen? symbool kunt ge nie uit operator halen?
	}
}
