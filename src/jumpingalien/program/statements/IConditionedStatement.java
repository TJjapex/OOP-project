package jumpingalien.program.statements;

import jumpingalien.program.expressions.Expression;
import jumpingalien.program.types.BooleanType;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * An interface for conditioned Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
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
