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
	
	public Expression<BooleanType> getCondition(){
		return this.condition;
	}
	
	private final Expression<BooleanType> condition;
	
	public Statement getBody(){
		return this.body;
	}
	
	private final Statement body;
	
	@Override
	public void execute(Program program) throws ProgramRuntimeException{
		
		if (!conditionChecked){
			this.conditionResult = this.getCondition().execute(program).getValue();
			this.conditionChecked = true;
			System.out.println("WhileDo, checked condition: "+ this.conditionResult);
		}else{
			if(this.iterator().hasNext())
				this.iterator().next().execute(program);
			else
				throw new ProgramRuntimeException("Statement executed while not having next useful statement!");
			
		}
		
	}
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				
				if (WhileDo.this.stop)
					return false;
				
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
				
				if (WhileDo.this.stop)
					throw new NoSuchElementException();
				
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
		this.stop = false;
	}
	
	private boolean conditionResult;
	private boolean conditionChecked = false;
	
	@Override
	public List<Statement> getChildrenStatements(){
		List<Statement> childrenStatements = new ArrayList<>();
		childrenStatements.add(this.getBody());
		return childrenStatements;
	}
	
	public void breakLoop(){
		this.stop = true;
	}
	
	public boolean isBroken(){
		return this.stop;
	}
	
	private boolean stop;
	
}
