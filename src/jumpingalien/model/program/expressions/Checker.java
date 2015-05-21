package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Checkers as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Checker extends Expression<BooleanType>{
	
	public Checker(Expression<ObjectType> expr, BiFunction<Object, Program, Boolean> operator, SourceLocation sourceLocation){
		super( sourceLocation);

		this.operand = expr;
		this.operator = operator;
	}

	@Basic @Immutable
	public Expression<ObjectType> getOperand() {
		return this.operand;
	}

	private final Expression<ObjectType> operand;
	
	@Basic @Immutable
	public BiFunction<Object, Program, Boolean> getOperator(){
		return this.operator;
	}
	
	private final BiFunction<Object, Program, Boolean> operator;
	
	@Override
	public BooleanType execute(Program program) {
		return new BooleanType( getOperator().apply(getOperand().execute(program).getValue(), program) );
	}
	
}
