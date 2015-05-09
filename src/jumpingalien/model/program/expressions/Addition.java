// This is not used anymore

package jumpingalien.model.program.expressions;

import java.util.function.BiFunction;

import jumpingalien.part3.programs.SourceLocation;

public class Addition<T> extends BinaryOperator<T>{

	public Addition(Expression<T> left, Expression<T> right,
			BiFunction<T, T, T> operator, SourceLocation sourceLocation) {
		super(left, right, operator, sourceLocation);
		// TODO Auto-generated constructor stub
	}
	//public Addition( Expression leftOperand, Expression rightOperand, SourceLocation sourceLocation){
		//super( leftOperand, rightOperand, sourceLocation );
	//}
//
//	@Override
//	public Object getResult() {
//		return (int) getLeftOperand().getResult() + (int) getRightOperand().getResult();
//	}
}
