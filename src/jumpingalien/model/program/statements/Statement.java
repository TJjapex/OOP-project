package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.antlr.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;

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
	
	public void resetIterator(){ // moet kunnen aangeroepen worden vanuit Program?
		this.statementUsed = false;
	}
	
	protected boolean statementUsed = false;
	
	public abstract void execute(Program program);	
	
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
	
	public List<Statement> getChildrenStatements(){
		return null;
	}
	
	public boolean hasChildren(){
		return this.getChildrenStatements() != null;
	}
	
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
