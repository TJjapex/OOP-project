package jumpingalien.model;

/**
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 *
 */
public class IllegalTimeAmountException extends RuntimeException {
	

	public IllegalTimeAmountException(double dt){
		this.dt = dt;
	}
	
	public double getTimeAmount(){
		return this.dt;
	}
	
	private final double dt;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1610886134470398884L;
}
