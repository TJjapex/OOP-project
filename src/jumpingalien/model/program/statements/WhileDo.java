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
		this.condition = Expression.cast(condition);
		this.body = body;
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
		}else{
			if(this.iterator().hasNext()){
				this.iterator().next().execute(program);
			}
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
				
				if ( !WhileDo.this.conditionChecked )
					return WhileDo.this;
				else if (WhileDo.this.conditionResult){
					if(WhileDo.this.getBody().iterator().hasNext()){
						return WhileDo.this.getBody();
					}	
				}
					
				throw new NoSuchElementException();
				
			}
			
		};
		
	}
		
	@Override
	public void resetIterator(){
		this.conditionChecked = false;
		this.getBody().resetIterator();
	}
	
	private boolean conditionResult;
	private boolean conditionChecked = false;
	
}
