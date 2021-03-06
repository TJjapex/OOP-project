package jumpingalien.model.exceptions;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal x positions for Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 3.0
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
	 * Returns the position where this exception related to this exception.
	 * @return
	 * 		the position where this exception related to this exception.
	 */
	@Basic @Immutable
	public double getPosition() {
		return this.positionX;
	}
	
	/**
	 * Returns the position related to this exception.
	 */
	@Override
	public String getMessage(){
		return "Invalid x position: " + Double.toString(this.getPosition());
	}

	/**
	 * Variable referencing the horizontal position of this illegal position X exception.
	 */
	private final double positionX;
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -1859511137434604743L;

}