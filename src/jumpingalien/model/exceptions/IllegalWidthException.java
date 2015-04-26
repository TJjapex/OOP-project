package jumpingalien.model.exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal width values for sprites.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class IllegalWidthException extends RuntimeException{
	
	/**
	 * Initialize this new illegal width exception with the given width.
	 * 
	 * @param 	width
	 * 				The width value for this exception.
	 * @post	The width value for this exception is set to the given width.
	 * 			| new.getWidth() == width
	 */
	public IllegalWidthException(int width){
		this.width = width;
	}
	
	/**
	 * Returns the width value for this exception.
	 * 
	 * @return	The width value for this exception.
	 */
	@Basic @Immutable
	public double getWidth(){
		return this.width;
	}
	
	/**
	 * Variable referencing the width of this illegal width exception.
	 */
	private final int width;
	
	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -7258598777043975894L;
	
}
