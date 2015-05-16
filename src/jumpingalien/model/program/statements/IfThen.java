package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(final Expression<BooleanType> condition, final Statement ifBody, final Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	private boolean conditionResult;
	private boolean conditionChecked = false;
	
	
	/* If body */
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	private final Statement ifBody;
	
	/* Else body */
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	public boolean hasElseBody(){
		return this.elseBody != null;
	}
	
	private final Statement elseBody;
	
	/* Execute */
	
	@Override
	public void execute(Program program){
		
		if (!conditionChecked){
			this.conditionResult = this.getCondition().execute(program).getValue();
			this.conditionChecked = true;
			System.out.println("IfThen, checked condition: "+ this.conditionResult);
		}else{
			if(this.iterator().hasNext())
				this.iterator().next().execute(program);
		}

	}
	
	/* Iterator */
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				if (!IfThen.this.conditionChecked)
					return true;
				else if (IfThen.this.conditionResult)
					return getIfBody().iterator().hasNext();
				else if(hasElseBody())
					return getElseBody().iterator().hasNext();
				return false;
				
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				
				if ( !IfThen.this.conditionChecked )
					return IfThen.this;
				else if (IfThen.this.conditionResult){
					return IfThen.this.getIfBody();
				} else{
					return IfThen.this.getElseBody();
				}		
				
			}
			
		};
		
	}
	
	@Override
	public void resetIterator(){
		conditionChecked = false;
		if (this.conditionResult){
			this.getIfBody().resetIterator();
		} else if (this.hasElseBody()){
			this.getElseBody().resetIterator();
		}
	}
}
