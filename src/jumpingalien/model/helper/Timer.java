package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Timer class, implemented with methods to serve as a helper class for the class Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Timer {
	
	/**
	 * Constructor for the class Timer.
	 * 
	 * @post	The initial time since the last move of a Mazub was made, is equal to infinity.
	 * 			| new.getSinceLastMove() == Double.POSITIVE_INFINITY
	 * @post	The initial time since the last sprite of a Mazub was activated, is equal to 0.
	 * 			| new.getSinceLastSprite() == 0
	 */
	public Timer(){
		this.setSinceLastMove(Double.POSITIVE_INFINITY);
		this.setSinceLastSprite(0);
	}
	
	// Last move
	
	/**
	 * Return the elapsed time since the last move was made.
	 * 
	 * @return	A double that represents the elapsed time since the last move was made.
	 */
	@Basic
	public double getSinceLastMove() {
		return this.sinceLastMove;
	}

	/**
	 * Set the elapsed time since the last move was made.
	 * 
	 * @param 	sinceLastMove
	 * 				A double that represents the desired elapsed time since the last move was made.
	 * @post	The time since the last move of a Mazub was made, is equal to sinceLastMove.
	 * 			| new.getSinceLastMove() == sinceLastMove
	 */
	@Basic
	public void setSinceLastMove(double sinceLastMove) {
		this.sinceLastMove = sinceLastMove;
	}
	
	/**
	 * Increases the elapsed time since the last move was activated.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the last move of a Mazub was made, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceLastMove(this.getSinceLastMove() + dt)
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
	@Basic
	public double getSinceLastSprite() {
		return this.sinceLastSprite;
	}
	
	/**
	 * Set the elapsed time since the last sprite was activated.
	 * 
	 * @param 	sinceLastSprite
	 * 				A double that represents the desired elapsed time since the last sprite was activated.
	 * @post	The time since the last sprite of a Mazub was activated, is equal to sinceLastSprite.
	 * 			| new.getSinceLastSprite() == sinceLastSprite
	 */
	@Basic
	public void setSinceLastSprite(double sinceLastSprite) {
		this.sinceLastSprite = sinceLastSprite;
	}
	
	/**
	 * Increases the elapsed time since the last sprite was activated.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the last sprite of a Mazub was activated, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceLastSprite(this.getSinceLastSprite() + dt)
	 */
	public void increaseSinceLastSprite(double dt){
		setSinceLastSprite(getSinceLastSprite() + dt);
	}

	private double sinceLastSprite;
		
}
