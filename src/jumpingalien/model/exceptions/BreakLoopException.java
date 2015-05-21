package jumpingalien.model.exceptions;

/**
 * A class for signaling a Break Statement in a loop of a Program.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class BreakLoopException extends RuntimeException {

	/**
	 * Initialize this new break loop exception.
	 * 
	 */
	public BreakLoopException(){
		
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = 2631772928248478764L;
	
}
