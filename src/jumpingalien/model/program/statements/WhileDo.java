package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.BreakLoopException;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of While Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class WhileDo extends Statement implements ILoop, IConditionedStatement{

	/* Constructor */
	
	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = Expression.cast(condition);
		this.body = body;
	}
		
	/* Condition */
	
	@Basic @Override
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	/* Condition result */
	
	@Basic @Override
	public boolean isConditionTrue() {
		return conditionResult;
	}

	@Basic
	private void setConditionResult(boolean conditionResult) {
		this.conditionResult = conditionResult;
	}
	
	private boolean conditionResult;
	
	/* Condition checked */
	
	@Basic @Override
	public boolean isConditionChecked() {
		return conditionChecked;
	}

	@Basic
	private void setConditionChecked(boolean conditionChecked) {
		this.conditionChecked = conditionChecked;
	}
	
	private boolean conditionChecked = false;
	
	/* Loop body */
	
	@Basic @Immutable @Override
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	/* Execution */
	
	@Override
	public void execute(final Program program) throws ProgramRuntimeException{
		
		if (!isConditionChecked()){
			setConditionResult(this.getCondition().execute(program).getValue());
			setConditionChecked(true);
		}else{
			
			if(this.iterator().hasNext()){
				try{
					this.iterator().next().execute(program);
				} catch (BreakLoopException exc){
					this.breakLoop();
				}
			}
			else
				throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
			
		}
		
	}
	
	/* Iterator */
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				if (isBroken())
					return false;
				
				if (!isConditionChecked())
					return true;
				else if (isConditionTrue())
					if (getBody().iterator().hasNext())
						return true;
					else{
						WhileDo.this.resetIterator();
						return true;
					}
				else
					return false;
			}
			
			@Override
			public Statement next() throws NoSuchElementException{				
				
				if (isBroken())
					throw new NoSuchElementException();
				
				if ( !isConditionChecked() )
					return WhileDo.this;
				else if ( isConditionTrue() ){
					if(getBody().iterator().hasNext()){
						return getBody();
					}	
				}
					
				throw new NoSuchElementException();
				
			}
			
		};
		
	}
		
	@Override
	public void resetIterator(){
		setConditionChecked(false);
		this.getBody().resetIterator();
		this.broken = false;
	}
	
	/* Children statements */
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		childrenStatements.add(this.getBody());
		return childrenStatements;
	}
	
	/* Loop control */
	
	@Basic @Override
	public void breakLoop(){
		this.broken = true;
	}
	
	@Basic @Override
	public boolean isBroken(){
		return this.broken;
	}
	
	private boolean broken;
	
}
