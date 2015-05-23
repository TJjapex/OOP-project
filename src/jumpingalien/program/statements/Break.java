package jumpingalien.program.statements;

import jumpingalien.model.exceptions.BreakLoopException;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;

/**
 * A class of Break Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Break extends Statement {

	/* Constructor */
	
	public Break(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	/* Iterator */
	
	@Override
	public void resetIterator(){

	}
	
	/* Execution */
	
	@Override
	public void execute(Program program) throws BreakLoopException{	
		throw new BreakLoopException();
	}
		
}
