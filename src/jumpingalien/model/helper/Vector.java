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
	public Vector(T x,T y){
		this.setX(x);
		this.setY(y);
	}
	
	/******************************************************** VECTOR ***************************************************/
	
	/* X component */
	
	/**
	 * Return the x coordinate of the vector.
	 * 
	 * @return	An integer representing the x coordinate of the vector.
	 */
	@Basic
	public T getX() {
		return x;
	}
	/**
	 * Set the x coordinate of the vector.
	 * 
	 * @param 	x
	 * 				The x coordinate of the vector.
	 * @post	| new.getX() = x
	 */
	@Basic
	public void setX(T x) {
		this.x = x;
	}
	
	/**
	 * Variable registering the x coordinate of the vector.
	 */
	private T x;
	
	/* Y component */
	
	/**
	 * Return the y coordinate of the vector.
	 * 
	 * @return	An integer representing the y coordinate of the vector.
	 */
	@Basic
	public T getY() {
		return y;
	}
	
	/**
	 * Set the y coordinate of the vector.
	 * 
	 * @param 	y
	 * 				The y coordinate of the vector.
	 * @post	| new.getY() = y
	 */
	@Basic
	public void setY(T y) {
		this.y = y;
	}
	
	/**
	 * Variable registering the y coordinate of the vector.
	 */
	private T y;
	
	/**
	 * Convert the x and y coordinate of this vector to an array with aT first element the x coordinate and
	 * second element the y coordinate.
	 * 
	 * @return 	An array with as first element the x coordinate and
	 * 			second element the y coordinate.
	 */
	public Object[] toArray(){
		return new Object[]{getX(), getY()};
	}
	
	/**
	 * Return a hash code for this vector.
	 * 
	 * @return	An integer that could serve as a hash code.
	 */
	@Override
	public int hashCode() {
		return (int) this.getX() + (int) this.getY();
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

