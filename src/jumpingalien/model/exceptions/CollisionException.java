package jumpingalien.model.exceptions;

/**
 * A class for signaling a collision exception of a Game object.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 2.0
 */
public class CollisionException extends RuntimeException {

	/**
	 * Initialize this new collision exception.
	 * 
	 */
	public CollisionException(){
		
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -2071054747391186196L;

}
