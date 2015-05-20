package jumpingalien.model.exceptions;

/**
 * A class for signaling an illegal invocation of a Game object's end jump method.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class IllegalEndJumpException extends RuntimeException{

	/**
	 * Initialize this new illegal end jump exception.
	 * 
	 */
	public IllegalEndJumpException(){
		
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = 2713957546604990835L;

}
