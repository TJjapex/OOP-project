package jumpingalien.model;

/**
 * A class for signaling illegal time step amounts 
 * for the advanceTime function of the class Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 *
 */
public class IllegalTimeAmountException extends RuntimeException {
	
	/**
	 * Initialize this new illegal time step exception with the given time amount.
	 * @param 	dt
	 * 				The time step amount for this new illegal time step amount exception.
	 * @post	The time step amount dt of this new illegal time step amount exception
	 * 			is equal to the given dt.
	 *			| new.getTimeAmount() == dt
	 */
	public IllegalTimeAmountException(double dt){
		this.dt = dt;
	}
	
	
	/**
	 * Return the time amount registered for this illegal time step amount exception.
	 * @return	 The time amount registered for this illegal time step amount exception.
	 */
	public double getTimeAmount(){
		return this.dt;
	}
	
	private final double dt;
	

	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 */
	private static final long serialVersionUID = -1610886134470398884L;
}
