package jumpingalien.program.expressions;

import java.util.function.BiFunction;
import java.util.function.Function;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;
import jumpingalien.program.types.*;

/**
 * A class of Binary Program Operators as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class CoordinatesOperator<T extends Type> extends Expression<T>{
	
	/* Constructor */
	
	public CoordinatesOperator( Expression<DoubleType> x, Expression<DoubleType> y,
								BiFunction<Double, Double, Function<Program, T>> operator, SourceLocation sourceLocation){
		super( sourceLocation);

		this.xOperand = x;
		this.yOperand = y;
		this.operator = operator;
	}
	
	/* Operands */
	
	@Basic @Immutable
	public Expression<DoubleType> getXOperand() {
		return this.xOperand;
	}

	private final Expression<DoubleType> xOperand;
	
	@Basic @Immutable
	public Expression<DoubleType> getYOperand() {
		return this.yOperand;
	}
	
	private final Expression<DoubleType> yOperand;
	
	/* Operator */
	
	@Basic @Immutable
	public BiFunction<Double, Double, Function<Program,T>> getOperator(){
		return this.operator;
	}
	
	private final BiFunction<Double, Double, Function<Program,T>> operator;
	
	/* Execution */
	
	@Override
	public T execute(Program program) {
		return this.getOperator().apply( this.getXOperand().execute(program).getValue(),
										 this.getYOperand().execute(program).getValue())
								 .apply(program);
	}
	
}
