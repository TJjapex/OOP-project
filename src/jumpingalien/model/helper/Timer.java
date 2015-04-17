package jumpingalien.model.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
		this.setSinceLastPeriod(0);
		
		for(Terrain terrain : Terrain.getAllTerrainTypes()){
			// Eventueel check om alleen impassable tiles hier toe te veogen
			this.sinceLastTerrainCollision.put(terrain, Double.POSITIVE_INFINITY);
		}
		
		this.setSinceEnemyCollision(Double.POSITIVE_INFINITY);
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
	public void setSinceLastMove(double dt) {
		this.sinceLastMove = dt;
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
	public void setSinceLastSprite(double dt) {
		this.sinceLastSprite = dt;
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
	
	// Kill
	public double getSinceKilled() {
		return sinceKilled;
	}

	public void setSinceKilled(double dt) {
		this.sinceKilled = dt;
	}
	
	public void increaseSinceKilled(double dt){
		setSinceKilled(getSinceKilled() + dt);
	}
	
	private double sinceKilled;
	
	
	/* Terrain Collision */
	private Map<Terrain, Double> sinceLastTerrainCollision = new HashMap<Terrain, Double>();
	
	public double getSinceTerrainCollision(Terrain terrain){
		if(!this.sinceLastTerrainCollision.containsKey(terrain))
			throw new IllegalArgumentException("Terrain not in collision map!");
		return this.sinceLastTerrainCollision.get(terrain);
	}
	
	public void setSinceTerrainCollision(Terrain terrain, double dt){
		this.sinceLastTerrainCollision.put(terrain, dt);
	}
	
	/** Increases all **/ 
	public void increaseSinceTerrainCollision(double dt){
		for(Terrain terrain : this.sinceLastTerrainCollision.keySet()){
			setSinceTerrainCollision(terrain, getSinceTerrainCollision(terrain) + dt);
		}
	}
	
	/* Enemy Collision */
	private double sinceEnemyCollision;
	
	public double getSinceEnemyCollision() {
		return sinceEnemyCollision;
	}

	public void setSinceEnemyCollision(double sinceEnemyCollision) {
		this.sinceEnemyCollision = sinceEnemyCollision;
	}
	
	public void increaseSinceEnemyCollision(double dt){
		this.setSinceEnemyCollision( this.getSinceEnemyCollision() + dt);
	}
	

	/* Shark movement */
	
	public double getSinceLastPeriod() {
		return this.sinceLastPeriod;
	}
	
	public void setSinceLastPeriod(double dt) {
		this.sinceLastPeriod = dt;
	}
	
	public void increaseSinceLastPeriod(double dt){
		this.setSinceLastPeriod(this.getSinceLastPeriod()+dt);
	}
	
	private double sinceLastPeriod;
	
	public double getRandomPeriodTime(double min, double max){
		Random random = new Random();
		return min + (max - min)*random.nextDouble();
	}


	
	
	
}