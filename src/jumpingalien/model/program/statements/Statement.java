package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.NoSuchElementException;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {

	public Statement( SourceLocation sourceLocation ){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return !Statement.this.statementUsed;
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if ( this.hasNext() ){
					Statement.this.statementUsed = true;
					return Statement.this;
				} else {
					throw new NoSuchElementException();		
				}
			}
	
		};
		
	}
	
	private boolean statementUsed = false;
	
	public abstract void execute(Program program);
	
	public void resetIterator(){
		this.statementUsed = false;
	}
	
}
