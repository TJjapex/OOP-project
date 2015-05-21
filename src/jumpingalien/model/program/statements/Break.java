package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Break extends Statement {

	public Break(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	@Override
	public void execute(Program program) throws IllegalStateException{
		// This could be done more easily by throwing a BreakException and catching it in the foreach and while statements.
		while( this.wrapStatement.hasParentStatement() && !this.outerLoopFound){
			this.wrapStatement = this.wrapStatement.getParentStatement();
			if ( this.wrapStatement instanceof ILoop ){
				System.out.println("Break!");
				((WhileDo) this.wrapStatement).breakLoop();
				this.outerLoopFound = true;
			}
		}
		
		if (!this.outerLoopFound){
			System.out.println("Break not in a loop!");
			throw new IllegalStateException();
		}	
	}
	
	// TODO waarvoor dienen deze variabelen eigenlijk, moeten die opgeslagen worden? Kunnen die niet altijd in execute terug gezet worden?
	// + statementUsed gebruiken? + getters/setters? :/
	
	private Statement wrapStatement = this;
	private boolean outerLoopFound;
	
	@Override
	public void resetIterator(){
		this.wrapStatement = this;
		this.outerLoopFound = false;
	}
	
}
