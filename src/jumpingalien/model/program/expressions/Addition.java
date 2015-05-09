package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Addition extends BinaryOperator<Type<Double>>{
	public Addition( Expression leftOperand, Expression rightOperand, SourceLocation sourceLocation){
		super( leftOperand, rightOperand, sourceLocation );
	}

	@Override
	public Object getResult() {
		return (int) getLeftOperand().getResult() + (int) getRightOperand().getResult();
	}
}
