package jumpingalien.model.exceptions;

/**
 * A class for signaling an illegal invocation of a Game object's end duck method.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class IllegalEndDuckException extends RuntimeException{	

	/**
	 * Initialize this new illegal end duck exception.
	 * 
	 */
	public IllegalEndDuckException(){
		
	}
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -9220407184141907868L;
	

}
