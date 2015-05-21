package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.BooleanType;
import be.kuleuven.cs.som.annotate.Basic;

public interface IConditionedStatement {
	/* Condition */
	@Basic
	public Expression<BooleanType> getCondition();
	
	/* Condition result */
	@Basic
	public boolean isConditionTrue();

	/* Condition checked */
	@Basic 
	public boolean isConditionChecked();
}
