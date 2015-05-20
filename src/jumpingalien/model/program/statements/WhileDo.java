package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class WhileDo extends Statement implements ILoop{

	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = Expression.cast(condition);
		this.body = body;
		this.getBody().setParentStatement(this);
	}
		
	/* Condition */
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	/* Condition result */
	
	public boolean isConditionTrue() {
		return conditionResult;
	}

	private void setConditionResult(boolean conditionResult) {
		this.conditionResult = conditionResult;
	}
	
	private boolean conditionResult;
	
	/* Condition checked */
	
	public boolean isConditionChecked() {
		return conditionChecked;
	}

	private void setConditionChecked(boolean conditionChecked) {
		this.conditionChecked = conditionChecked;
	}
	
	private boolean conditionChecked = false;
	
	/* Body */
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	/* Execute */
	
	@Override
	public void execute(Program program) throws ProgramRuntimeException{
		
		if (!isConditionChecked()){
			setConditionResult(this.getCondition().execute(program).getValue());
			setConditionChecked(true);
			//System.out.println("WhileDo, checked condition: "+ this.conditionResult);
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
		this.stop = false;
	}
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		childrenStatements.add(this.getBody());
		return childrenStatements;
	}
	
	/* Break */
	
	public void breakLoop(){
		this.stop = true;
	}
	
	public boolean isBroken(){
		return this.stop;
	}
	
	private boolean stop;
	
}
