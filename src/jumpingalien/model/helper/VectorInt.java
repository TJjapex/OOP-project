package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for vectors with integer coordinates. 
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class VectorInt {
	
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
	public VectorInt(int x, int y){
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
	public int getX() {
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
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Variable registering the x coordinate of the vector.
	 */
	private int x;
	
	/* Y component */
	
	/**
	 * Return the y coordinate of the vector.
	 * 
	 * @return	An integer representing the y coordinate of the vector.
	 */
	@Basic
	public int getY() {
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
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Variable registering the y coordinate of the vector.
	 */
	private int y;
	
	/**
	 * Convert the x and y coordinate of this vector to an array with as first element the x coordinate and
	 * second element the y coordinate.
	 * 
	 * @return 	An array with as first element the x coordinate and
	 * 			second element the y coordinate.
	 */
	public int[] toArray(){
		return new int[]{getX(), getY()};
	}
	
	/**
	 * Return a hash code for this vector.
	 * 
	 * @return	An integer that could serve as a hash code.
	 */
	@Override
	public int hashCode() {
		return this.getX() + this.getY();
	}

	/**
	 * Check whether this vector is equal to another given vector.
	 * 
	 * @param 	other
	 * 				A vector of the class VectorInt to compare with this vector.
	 * @return	| result == ( this.getX() == ((VectorInt) other).getX() && this.getY() == ((VectorInt) other).getY() )
	 */
	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof VectorInt)){
			return false;
		}
		
		return  this.getX() == ((VectorInt) other).getX() && 
				this.getY() == ((VectorInt) other).getY();
	}

}
