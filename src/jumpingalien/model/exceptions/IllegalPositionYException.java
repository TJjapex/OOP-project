package jumpingalien.model.exceptions;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal y positions for Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class IllegalPositionYException extends RuntimeException {

	/**
	 * Initialize this new illegal y position exception with the given y position.
	 * 
	 * @param 	positionY
	 * 				The y position for this exception.
	 * @post	The y position for this exception is set to the given y position.
	 * 			| new.getPositionY() == positionY
	 */
	public IllegalPositionYException(double positionY) {
		this.positionY = positionY;
	}
	
	/**
	 * Returns the y position for this exception.
	 * 
	 * @return	The y position for this exception.
	 * 
	 */
	@Basic @Immutable
	public double getPosition() {
		return this.positionY;
	}
	
	/**
	 * Returns the position related to this exception.
	 */
	public String getMessage(){
		return "Invalid y position: " + Double.toString(this.getPosition());
	}

	/**
	 * Variable referencing the vertical position of this illegal position Y exception.
	 */
	private final double positionY;
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -3910437603953458609L;

}