package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfThen extends Statement {

	public IfThen(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType> ) condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		this.ifBodyIterator = this.getIfBody().iterator();
		if (this.hasElseBody()){
			this.elseBodyIterator = this.getElseBody().iterator();
		}
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
			conditionResult = this.getCondition().execute(program).getValue();
		}
		
		System.out.println("IfThen, condition result: "+ this.conditionResult);
		
		if( conditionResult && this.ifBodyIterator.hasNext() ){
			System.out.println("IfThen, if body");
			((Statement) this.ifBodyIterator.next()).execute(program);
		} else if( !conditionResult && this.hasElseBody() && this.elseBodyIterator.hasNext()){
			System.out.println("IfThen, else body");
			((Statement) this.elseBodyIterator.next()).execute(program);
		}
		
	}
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return ifBodyIterator.hasNext();
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if ( this.hasNext() ){
					return IfThen.this;
				} else {
					throw new NoSuchElementException();		
				}
			}
			
		};
		
	}
	
	@Override
	public void resetIterator(){
		conditionChecked = false;
		this.getIfBody().resetIterator();
		this.ifBodyIterator = this.getIfBody().iterator();
		if (this.hasElseBody()){
			this.elseBodyIterator = this.getElseBody().iterator();
			this.getElseBody().resetIterator();
		}
	}
	
	private boolean conditionResult;
	private Iterator<Statement> ifBodyIterator;
	private Iterator<Statement> elseBodyIterator;
	private boolean conditionChecked = false;
}
