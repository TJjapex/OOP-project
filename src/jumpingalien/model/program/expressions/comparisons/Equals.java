package jumpingalien.model.program.expressions.comparisons;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.Operator;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Equals<T> extends Operator<BooleanType>{
	
	public Equals(Expression<?> left, Expression<?> right, SourceLocation sourceLocation){
		super( sourceLocation);

		this.leftOperand = left;
		this.rightOperand = right;
	}

	@Basic @Immutable
	public Expression<?> getLeftOperand() {
		return leftOperand;
	}

	private final Expression<?> leftOperand;
	
	@Basic @Immutable
	public Expression<?> getRightOperand() {
		return rightOperand;
	}
	
	private final Expression<?> rightOperand;
	
	@Override
	public BooleanType execute(Program program) {
		return new BooleanType( getLeftOperand().execute(program).equals( getRightOperand().execute(program) ));
	}
	
	@Override @Immutable @Basic
	public final int getNbOperands() {
		return 2;
	}

	@Override @Immutable
	public final String getOperatorSymbol() {
		return "=="; // method abstract maken? ook toepassen voor subtraction, division en multiplication
					 // denk niet dat deze methode mogelijk is voor alle gevallen? symbool kunt ge nie uit operator halen?
	}
}
