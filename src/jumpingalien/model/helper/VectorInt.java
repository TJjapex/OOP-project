package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for vectors with integer coordinates. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * 
 * @version 1.0
 *
 */

public class VectorInt {
	/**
	 * Constructor for Vector
	 * 
	 * @param x
	 * 		The x coordinate
	 * @param y
	 * 		The y coordinate
	 */
	public VectorInt(int x, int y){
		setX(x);
		setY(y);
	}
	
	/* X component */
	
	/**
	 * Returns the x coordinate of the vector
	 */
	@Basic
	public int getX() {
		return x;
	}
	/**
	 * Sets the x coordinate of the vector
	 * @param x
	 * 		The x coordinate
	 * @post
	 * 		| new.getX() = x
	 */
	@Basic
	public void setX(int x) {
		this.x = x;
	}
	private int x;
	
	/* Y component */
	
	/**
	 * Returns the y coordinate of the vector
	 */
	@Basic
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y coordinate of the vector
	 * @param y
	 * 		The y coordinate
	 * @post
	 * 		| new.getY() = y
	 */
	@Basic
	public void setY(int y) {
		this.y = y;
	}
	private int y;
	
	/**
	 * Converts the x and y coordinate of this vector to an array with as first element the x coordinate and second element the y coordinate
	 * 
	 * @return
	 * 			| result ==  new int[]{getX(), getY()};
	 */
	public int[] toArray(){
		return new int[]{getX(), getY()};
	}
	
	/**
	 * Returns a hashcode for this vector.
	 */
	@Override
	public int hashCode() {
		return getX() + getY();
	}

	/**
	 * Checks wether this vector is equal to another given vector.
	 * 
	 * @param other
	 * 			A vector of the class VectorInt
	 * @return
	 * 		| this.getX() == ((VectorInt) other).getX() && this.getY() == ((VectorInt) other).getY();
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
