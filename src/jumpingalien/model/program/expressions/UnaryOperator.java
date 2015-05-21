package jumpingalien.model.program.expressions;

import java.util.function.Function;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class UnaryOperator<I extends Type, T extends Type> extends Expression<T>{
	
	public UnaryOperator(final Expression<I> expr, final Function<I, T> operator, final SourceLocation sourceLocation){
		super( sourceLocation);

		this.operand = expr;
		this.operator = operator;
	}

	@Basic @Immutable
	public Expression<I> getOperand() {
		return this.operand;
	}

	private final Expression<I> operand;
	
	@Basic @Immutable
	public Function<I, T> getOperator(){
		return this.operator;
	}
	
	private final Function<I, T> operator;
	
	@Override
	public T execute(final Program program) {
		 return getOperator().apply(getOperand().execute(program));
	}
	
}
