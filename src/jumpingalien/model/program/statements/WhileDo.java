package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.antlr.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.types.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class WhileDo extends Statement {

	public WhileDo(Expression<?> condition, Statement body, SourceLocation sourceLocation){
		super(sourceLocation);
		this.condition = (Expression<BooleanType>) condition;
		this.body = body;
		this.bodyIterator = this.getBody().iterator();
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
			conditionResult = this.getCondition().execute(program).getValue();
		}
		
		System.out.println("WhileDo, condition result: "+ this.conditionResult);
		
		if ( conditionResult && this.bodyIterator.hasNext() ){
			System.out.println("WhileDo, body");
			((Statement) this.bodyIterator.next()).execute(program);
			
			if (!this.bodyIterator.hasNext()){
				conditionResult = this.getCondition().execute(program).getValue();
				if (conditionResult){
					this.resetIterator();
					this.bodyIterator = this.getBody().iterator();
				}
				System.out.println("while loop reset: " + this.bodyIterator.hasNext());
				System.out.println(this.bodyIterator.next());
			}
			
		}
	}
	
	@Override
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return bodyIterator.hasNext();
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if ( this.hasNext() ){
					return WhileDo.this;
				} else {
					throw new NoSuchElementException();		
				}
			}

			
		};
		
	}
	
	@Override
	public void resetIterator(){
		this.getBody().resetIterator();
		this.bodyIterator = this.getBody().iterator();
	}
	
	private boolean conditionResult;
	private Iterator<Statement> bodyIterator;
	private boolean conditionChecked = false;
}
