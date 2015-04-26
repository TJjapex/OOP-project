package jumpingalien.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal height values for sprites.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class IllegalHeightException extends RuntimeException{

	/**
	 * Initialize this new illegal height exception with the given height.
	 * 
	 * @param 	height
	 * 				The height value for this exception.
	 * @post	The height value for this exception is set to the given height.
	 * 			| new.getHeight() == height
	 */
	public IllegalHeightException(int height){
		this.height = height;
	}
	
	/**
	 * Returns the height value for this exception.
	 * 
	 * @return	The height value for this exception.
	 */
	@Basic @Immutable
	public double getHeight(){
		return this.height;
	}
	
	/**
	 * Variable referencing the height of this illegal height exception.
	 */
	private final int height;
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -167299129520942749L;

}
