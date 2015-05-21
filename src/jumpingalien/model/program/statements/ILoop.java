package jumpingalien.model.program.statements;

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
	
	public void breakLoop();
	
	public boolean isBroken();
	
	/* Loop body */
	
	public Statement getBody();
	
}
