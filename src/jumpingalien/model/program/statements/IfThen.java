package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(Expression<BooleanType> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		//this.ifBodyIterator = this.getIfBody().iterator();
		//if (this.hasElseBody()){
			//this.elseBodyIterator = this.getElseBody().iterator();
		//}
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private Expression<BooleanType> condition;
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	private Statement ifBody;
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	public boolean hasElseBody(){
		return this.elseBody != null;
	}
	
	private Statement elseBody;
	
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
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				if (!IfThen.this.conditionChecked)
					return true;
				else if (IfThen.this.conditionResult)
					//return ifBodyIterator.hasNext();
					return getIfBody().iterator().hasNext();
				else
					if(hasElseBody()){
						return getElseBody().iterator().hasNext();
					}
				return false;
					//return elseBodyIterator.hasNext();
				
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
	
//	private Iterator<Statement> ifBodyIterator;
//	private Iterator<Statement> elseBodyIterator;
	
	@Override
	public void resetIterator(){
		conditionChecked = false;
		//System.out.println(this.hasElseBody());
		if (this.conditionResult){
			//this.ifBodyIterator = this.getIfBody().iterator();
			this.getIfBody().resetIterator();
		} else if (this.hasElseBody()){
			//this.elseBodyIterator = this.getElseBody().iterator();
			this.getElseBody().resetIterator();
		}
	}
	
	private boolean conditionResult;
	private boolean conditionChecked = false;
	
}
