package jumpingalien.model.helper;

import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Timer class, implemented with methods to serve as a helper class for the class GameObject.
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
		
		for(Terrain terrain : Terrain.getAllTerrainTypes()){
			// Eventueel check om alleen impassable tiles hier toe te veogen
			this.sinceLastCollision.put(terrain, Double.POSITIVE_INFINITY);
		}
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
	
	/**
	 * Variable registering the time since the last move was made for this Timer.
	 */
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
	

	/**
	 * Variable registering the time since the last sprite was activated for this Timer.
	 */
	private double sinceLastSprite;	
	
	
//	// Water
//	public double getSinceWaterCollission() {
//		return this.sinceWaterCollision;
//	}
//
//	public void setSinceWaterCollision(double sinceWaterCollision) {
//		this.sinceWaterCollision = sinceWaterCollision;
//	}
//
//	public void increaseSinceWaterCollision(double dt) {
//		setSinceWaterCollision(getSinceWaterCollission() + dt);
//	}
//	
//	private double sinceWaterCollision;
//	
//	// Magma
//	public double getSinceMagmaCollission() {
//		return this.sinceMagmaCollision;
//	}
//
//	public void setSinceMagmaCollision(double sinceMagmaCollision) {
//		this.sinceMagmaCollision = sinceMagmaCollision;
//	}
//
//	public void increaseSinceMagmaCollision(double dt) {
//		setSinceMagmaCollision(getSinceMagmaCollission() + dt);
//	}

	//private double sinceMagmaCollision;
	
	// Kill
	public double getSinceKilled() {
		return sinceKilled;
	}

	public void setSinceKilled(double sinceKilled) {
		this.sinceKilled = sinceKilled;
	}
	
	public void increaseSinceKilled(double dt){
		setSinceKilled(getSinceKilled() + dt);
	}
	
	private double sinceKilled;
	
	
	// Colission
	public double getSinceLastCollision(Terrain terrain){
		if(!this.sinceLastCollision.containsKey(terrain))
			throw new IllegalArgumentException("Terrain not in collision map!");
		return this.sinceLastCollision.get(terrain);
	}
	
	public double setSinceLastCollision(Terrain terrain, double dt){
		return this.sinceLastCollision.put(terrain, dt);
	}
	
	// Increasees all 
	public void increaseSinceLastCollision(double dt){
		for(Terrain terrain : this.sinceLastCollision.keySet()){
			setSinceLastCollision(terrain, getSinceLastCollision(terrain) + dt);
		}
	}
	
	private Map<Terrain, Double> sinceLastCollision = new HashMap<Terrain, Double>();
	
	
	
}
