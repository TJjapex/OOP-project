package jumpingalien.program.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.model.exceptions.ProgramRuntimeException;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;

/**
 * A class of Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public abstract class Statement {

	/* Constructor */
	
	public Statement(final SourceLocation sourceLocation ){
		this.sourceLocation = sourceLocation;
	}
	
	/* Source location */
	
	@Basic @Immutable
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
	
	@Basic
	protected boolean isStatementUsed(){
		return this.statementUsed;
	}
	
	@Basic
	protected void setStatementUsed(boolean statementUsed){
		this.statementUsed = statementUsed;
	}
	
	protected boolean statementUsed = false;
	
	/* Execution */
	
	public abstract void execute(Program program) throws ProgramRuntimeException;	
	
	/* Children statements */
	
	public List<Statement> getChildrenStatements(){
		return null;
	}
	
	public boolean hasChildren(){
		return this.getChildrenStatements() != null;
	}
	
	/* Well formed */
	
	public boolean isWellFormed(boolean inWhile, boolean inForEach){
		if (this instanceof ForEachDo){
			inForEach = true;
		}else if (this instanceof WhileDo)
			inWhile = true;
		
		if (!(inWhile || inForEach) && this instanceof Break)
			return false;
		else if (inForEach && this instanceof Action){
			return false;
		}
			
		if (this.hasChildren()){
			for (Statement childStatement : this.getChildrenStatements()){
				if (!childStatement.isWellFormed(inWhile, inForEach))
					return false;
			}
		}
		
		return true;
		
	}	
	
}
