package jumpingalien.model.program.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {

	public Statement(final SourceLocation sourceLocation ){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	/* Iterator */
	
	public Iterator<Statement> iterator() {
		
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return !isStatementUsed();
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
	
	public void resetIterator(){
		setStatementUsed(false);
	}
	
	/* Statement used */
	
	protected boolean isStatementUsed(){
		return this.statementUsed;
	}
	
	protected void setStatementUsed(boolean statementUsed){
		this.statementUsed = statementUsed;
	}
	
	protected boolean statementUsed = false;
	
	/* Execution */
	
	public abstract void execute(Program program) throws ProgramRuntimeException;	
	
	/* Parent statement */
	
	public Statement getParentStatement(){
		return this.parentStatement;
	}
	
	protected void setParentStatement(Statement statement){
		this.parentStatement = statement;
	}
	
	protected boolean hasParentStatement(){
		return this.getParentStatement() != null;
	}
	
	protected Statement parentStatement = null;
	
	/* Children statement */
	
	public List<Statement> getChildrenStatements(){
		return null;
	}
	
	public boolean hasChildren(){
		return this.getChildrenStatements() != null;
	}
	
	/* Well formed */
	
	// Checks if this statement and its children are well formed.
	public boolean isWellFormed(boolean inWhile, boolean inForEach){
		
		if (this instanceof ForEachDo)
			inForEach = true;
		else if (this instanceof WhileDo)
			inWhile = true;
		if (!(inWhile || inForEach) && this instanceof Break)
			return false;
		else if (inForEach && this instanceof Action)
			return false;
		
		if (this.hasChildren()){
			for (Statement childStatement : this.getChildrenStatements()){
				
				if (!childStatement.isWellFormed(inWhile, inForEach))
					return false;
			}
		}
		
		return true;
		
	}	
	
}
