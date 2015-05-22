package jumpingalien.model.program.statements;

import java.util.function.BiConsumer;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.IllegalEndJumpException;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of Action Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Action extends Statement {

	/* Constructor */
	
	public Action(BiConsumer<Object, Program> operator, Expression<ObjectType> gameObject, SourceLocation sourceLocation){
		super(sourceLocation);
		
		this.operator = operator;
		this.gameObject = gameObject;
	}
	
	/* Operator */
	@Basic @Immutable
	public BiConsumer<Object, Program> getOperator(){
		return this.operator;
	}
	
	private final BiConsumer<Object, Program> operator;
	
	/* Game object */
	
	@Basic @Immutable
	public Expression<ObjectType> getGameObject(){
		return this.gameObject;
	}
	
	private final Expression<ObjectType> gameObject;
	
	/* Execution */
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		if(this.iterator().hasNext()){
			try {
				getOperator().accept( this.getGameObject().execute(program).getValue(), program);
			} catch (IllegalEndJumpException exc) {
				
			}
			setStatementUsed(true);
		}else{
			throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
		}
	
	}
	
}
