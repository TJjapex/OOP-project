package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.GameObject;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.*;
import jumpingalien.part3.programs.SourceLocation;

public class Checker extends Operator<BooleanType>{
	
	public Checker(Expression<GameObjectType> expr, BiFunction<GameObject, Program, Boolean> operator, SourceLocation sourceLocation){
		super( sourceLocation);

		this.operand = expr;
		this.operator = operator;
	}

	@Basic @Immutable
	public Expression<GameObjectType> getOperand() {
		return this.operand;
	}

	private final Expression<GameObjectType> operand;
	
	@Basic @Immutable
	public BiFunction<GameObject, Program, Boolean> getOperator(){
		return this.operator;
	}
	
	private final BiFunction<GameObject, Program, Boolean> operator;
	
	@Override
	public BooleanType execute(Program program) {
		 return new BooleanType( getOperator().apply(getOperand().execute(program).getValue(), program) );
	}
}
