package jumpingalien.model.exceptions;

/**
 * A class for signaling an overlap exception of a Game object.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class OverlapException extends RuntimeException {

	/**
	 * Initialize this new overlap exception.
	 * 
	 */
	public OverlapException(){
		
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = 3706381145526872169L;
	
}
