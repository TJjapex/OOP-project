package jumpingalien.program.statements;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * An interface for loop Statements as defined in a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public interface ILoop {
	
	/* Loop control */
	
	@Basic
	public void breakLoop();
	
	@Basic
	public boolean isBroken();
	
	/* Loop body */
	
	@Basic
	public Statement getBody();
	
}
