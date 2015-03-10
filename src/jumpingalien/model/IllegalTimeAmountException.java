package jumpingalien.model;

/**
 * A class for singaling illegal timestep amounts 
 * for the advanceTime function of the class Mazub
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 *
 */
public class IllegalTimeAmountException extends RuntimeException {
	
	/**
	 * Initialize this new illegal timestep exception with the given time amount
	 * @param dt
	 * 			The timestep amount for this new illegal timestep amount exception
	 * @post	The temestep amount dt of this new illegal timestep amount exception
	 * 			is equal to the given dt
	 *			| new.getTimeAmount() == dt
	 */
	public IllegalTimeAmountException(double dt){
		this.dt = dt;
	}
	
	
	/**
	 * Return the time amount registered for this illegal timestep amount exception
	 * @return	 The time amount registered for this illegal timestep amount exception
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
