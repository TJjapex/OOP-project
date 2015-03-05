package jumpingalien.model;

public class Time {
	
	/**
	 * 
	 */
	public Time(){
		this.setSinceLastMove(0);
		this.setSinceLastSprite(0);
	}
	
	// Last move
	
	/**
	 * Return the elapsed time since the last move was made.
	 * 
	 * @return	A double that represents the elapsed time since the last move was made.
	 */
	public double getSinceLastMove() {
		return this.sinceLastMove;
	}

	/**
	 * Set the elapsed time since the last move was made.
	 * 
	 * @param time
	 * 			A double that represents the desired elapsed time since the last move was made.
	 */
	public void setSinceLastMove(double sinceLastMove) {
		this.sinceLastMove = sinceLastMove;
	}
	
	/**
	 * Increases the elapsed time since the last move was activated.
	 * 
	 * @param dt
	 * 		A double that represents the elapsed time that should be added.
	 */
	public void increaseSinceLastMove(double dt){
		setSinceLastMove( getSinceLastMove() + dt);
	}
	
	private double sinceLastMove;
	
	// Last sprite
	
	/**
	 * Return the elapsed time since the last sprite was activated.
	 * 
	 * @return	A double that represents the elapsed time since the last sprite was activated.
	 */
	public double getSinceLastSprite() {
		return this.sinceLastSprite;
	}
	
	/**
	 * Set the elapsed time since the last sprite was activated.
	 * 
	 * @param time
	 * 			A double that represents the desired elapsed time since the last sprite was activated.
	 */
	public void setSinceLastSprite(double sinceLastSprite) {
		this.sinceLastSprite = sinceLastSprite;
	}
	
	/**
	 * Increases the elapsed time since the last sprite was activated.
	 * 
	 * @param dt
	 * 		A double that represents the elapsed time that should be added.
	 */
	public void increaseSinceLastSprite(double dt){
		setSinceLastSprite(getSinceLastSprite() + dt);
	}

	private double sinceLastSprite;
		
}
