package jumpingalien.model.program.statements;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Break extends Statement {

	public Break(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	@Override
	public void execute(Program program) throws IllegalStateException{

		while( this.wrapStatement.hasParentStatement() && !this.outerLoopFound){
			this.wrapStatement = this.wrapStatement.getParentStatement();
			if ( this.wrapStatement instanceof WhileDo ){				// Hier kunnen we misschien van een "Loop" interface gebruik maken?
				System.out.println("Break!");
				((WhileDo) this.wrapStatement).breakLoop();
				this.outerLoopFound = true; // kan ook met echte break maar is misschien niet echt een mooie oplossing
			} else if ( this.wrapStatement instanceof ForEachDo ){
				System.out.println("Break!");
				//((ForEachDo) this.wrapStatement).breakLoop();
				this.outerLoopFound = true;
			}
		}
		
		if (!this.outerLoopFound){
			System.out.println("Break not in a loop!");
			throw new IllegalStateException();
		}	
		
	}
	
	private Statement wrapStatement = this;
	private boolean outerLoopFound;
	
	@Override
	public void resetIterator(){
		this.wrapStatement = this;
		this.outerLoopFound = false;
	}
	
}
