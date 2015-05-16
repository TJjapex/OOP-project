package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class WhileDo extends Statement {

	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType>) condition;
		this.body = body;
		this.bodyIterator = WhileDo.this.getBody().iterator();
	}
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	@Override
	public void execute(Program program){
		
		if (!conditionChecked){
			this.conditionResult = this.getCondition().execute(program).getValue();
			this.conditionChecked = true;
			System.out.println("WhileDo, checked condition: "+ this.conditionResult);
		} 
		
	}
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				if (!WhileDo.this.conditionChecked)
					return true;
				else if (WhileDo.this.conditionResult)
					if (bodyIterator.hasNext())
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
				
				if ( !WhileDo.this.conditionChecked )
					return WhileDo.this;
				else if (WhileDo.this.conditionResult){
					return WhileDo.this.bodyIterator.next();
				} else
					throw new NoSuchElementException();
				
			}
			
		};
		
	}
	
	private Iterator<Statement> bodyIterator;
	
	@Override
	public void resetIterator(){
		this.conditionChecked = false;
		this.bodyIterator = this.getBody().iterator();
		this.getBody().resetIterator();
	}
	
	private boolean conditionResult;
	private boolean conditionChecked = false;
	
}
