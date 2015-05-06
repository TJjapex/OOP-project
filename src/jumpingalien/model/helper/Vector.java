package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for vectors with integer coordinates. 
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class Vector<T extends Number> {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class VectorInt.
	 * 
	 * @param 	x
	 * 				The x coordinate of the vector.
	 * @param 	y
	 * 				The y coordinate of the vector.
	 * @effect	| setX(x)
	 * @effect	| setY(y)
	 */
	public Vector(final T x,final T y){
		this.x = x;
		this.y = y;
	}
	
	/******************************************************** VECTOR ***************************************************/
	
	/* X component */
	
	/**
	 * Return the x coordinate of the vector.
	 * 
	 * @return	An integer representing the x coordinate of the vector.
	 */
	@Basic @Immutable
	public T getX() {
		return x;
	}
	
	/**
	 * Variable registering the x coordinate of the vector.
	 */
	private final T x;
	
	/* Y component */
	
	/**
	 * Return the y coordinate of the vector.
	 * 
	 * @return	An integer representing the y coordinate of the vector.
	 */
	@Basic @Immutable
	public T getY() {
		return y;
	}
	
	/**
	 * Variable registering the y coordinate of the vector.
	 */
	private final T y;
	
	/**
	 * Convert the x and y coordinate of this vector to an array with as first element the x coordinate and
	 * second element the y coordinate.
	 * 
	 * @return 	An array with as first element the x coordinate and
	 * 			second element the y coordinate.
	 */
	@SuppressWarnings("unchecked")
	@Immutable
	public T[] toArray(){
		return  (T[]) new Object[]{getX(), getY()};
	}
	
	/**
	 * Return a hash code for this vector.
	 * 
	 * @return	An integer that could serve as a hash code.
	 */
	@Override @Immutable
	public int hashCode() {
		return (int) getX() + (int) getY();
	}

	/**
	 * Check whether this vector is equal to another given vector.
	 * 
	 * @param 	other
	 * 				A vector of the class VectorInt to compare with this vector.
	 * @return	| result == ( this.getX() == ((VectorInt) other).getX() && this.getY() == ((VectorInt) other).getY() )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object other) {
		
		if(!(other instanceof Vector)){
			return false;
		}
		
		return  this.getX() == ((Vector<T>) other).getX() && 
				this.getY() == ((Vector<T>) other).getY();
	}

}

