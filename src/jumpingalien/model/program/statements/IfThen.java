package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of If Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class IfThen extends Statement implements IConditionedStatement{

	/* Constructor */
	
	public IfThen(final Expression<BooleanType> condition, final Statement ifBody, final Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	/* Condition */
	
	@Basic @Immutable @Override
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}

	private final Expression<BooleanType> condition;
	
	
	/* Condition result */
	
	@Basic @Override
	public boolean isConditionTrue() {
		return conditionResult;
	}

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
	
	/* If body */
	
	@Basic @Immutable
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	private final Statement ifBody;
	
	/* Else body */
	
	@Basic @Immutable
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	public boolean hasElseBody(){
		return getElseBody() != null;
	}
	
	private final Statement elseBody;
	
	/* Execution */
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		
		if (!isConditionChecked()){
			setConditionResult(getCondition().execute(program).getValue());
			setConditionChecked(true);
		}else{
			if(this.iterator().hasNext())
				this.iterator().next().execute(program);
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
				
				if (!isConditionChecked())
					return true;
				else if (isConditionTrue())
					return getIfBody().iterator().hasNext();
				else if(hasElseBody())
					return getElseBody().iterator().hasNext();
				return false;
				
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				
				if ( !isConditionChecked())
					return IfThen.this;
				else if (isConditionTrue()){
					return IfThen.this.getIfBody();
				}else if(hasElseBody()){
					return IfThen.this.getElseBody();
				}else{
					throw new NoSuchElementException();
				}
				
			}
			
		};
		
	}
	
	@Override
	public void resetIterator(){
		setConditionChecked(false);
		if (isConditionTrue()){
			this.getIfBody().resetIterator();
		} else if (this.hasElseBody()){
			this.getElseBody().resetIterator();
		}
	}
	
	/* Children statements */
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		childrenStatements.add(this.getIfBody());
		if(this.hasElseBody())
			childrenStatements.add(this.getElseBody());
		return childrenStatements;
	}
	
}
