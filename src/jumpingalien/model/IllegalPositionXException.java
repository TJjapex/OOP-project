package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal x positions for Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class IllegalPositionXException extends RuntimeException {
	
	/**
	 * Initialize this new illegal x position exception with the given x position .
	 * @param 	positionX
	 * 				The x position for this exception.
	 * @post	The x position for this exception is set to the given x position.
	 * 			| new.getPositionX() == positionX
	 */
	public IllegalPositionXException(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * Returns the horizontal position for this exception.
	 * 
	 * @return	The horizontal position for this exception.
	 * 
	 */
	@Basic @Immutable
	public double getPositionX() {
		return this.positionX;
	}

	private final double positionX;
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -1859511137434604743L;

}