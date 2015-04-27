package jumpingalien.model.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jumpingalien.model.terrain.Terrain;
import be.kuleuven.cs.som.annotate.*;

/**
 * A Timer class, implemented with methods to serve as a helper class for the class GameObject.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 2.0
 * 
 * @invar	| this.getSinceLastMove() >= 0
 * @invar 	| this.getSinceLastLastSprite() >= 0
 * @invar 	| this.getSinceKilled() >= 0
 * @invar	|	 this.getSinceEnemyCollision() >= 0
 * @invar 	| this.getSinceLastPeriod() >= 0
 * @invar	| for each Terrain.getAllTerrainTypes() as terrain: 
 * 			|		this.getTerrainOverlapDuration(terrain) >= 0
 * @invar	| for each Terrain.getAllTerrainTypes() as terrain: 
 * 			|		this.getSinceLastTerrainDamage(terrain) >= 0
 * 
 */
public class Timer {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Timer.
	 * 
	 * @effect	The initial time since the last move of a Game object was made, is set to infinity.
	 * 			| setSinceLastMove(Double.POSITIVE_INFINITY)
	 * @effect	The initial time since the last sprite of a Game object was activated, is set to 0.
	 * 			| setSinceLastSprite(0)
	 * @effect 	The initial time since the last period of a Game object was initiated, is set to infinity.
	 * 			| setSinceLastPeriod(Double.POSITIVE_INFINITY)
	 * @effect 	The initial time since the collision with an enemy of a Game object, is set to infinity.
	 * 			| setSinceLastPeriod(Double.POSITIVE_INFINITY)
	 * @effect 	Set the terrain overlap duration for each Terrain to 0.
	 * 			| for ( terrain in Terrain.getAllTerrainTypes() )
	 * 			| 	setTerrainOverlapDuration(terrain, 0.0)
	 * @effect 	The initial time since the Game object took Terrain damage, is set to infinity for each Terrain.
	 * 			| for ( terrain in Terrain.getAllTerrainTypes() )
	 * 			| 	setSinceLastTerrainDamage(terrain, Double.POSITIVE_INFINITY)
	 */
	public Timer(){
		
		this.setSinceLastMove(Double.POSITIVE_INFINITY);
		this.setSinceLastSprite(0);
		this.setSinceLastPeriod(Double.POSITIVE_INFINITY);
		this.setSinceEnemyCollision(Double.POSITIVE_INFINITY);
		
		for(Terrain terrain : Terrain.getAllTerrainTypes()){
			this.setTerrainOverlapDuration(terrain, 0.0);
			this.setSinceLastTerrainDamage(terrain, Double.POSITIVE_INFINITY);
		}
		
	}
	
	/******************************************************* TIMERS ****************************************************/
	
	/* Last move */

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
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since the last move was made.
	 * @post	The time since the last move of a Game object was made, is equal to dt.
	 * 			| new.getSinceLastMove() == dt
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
	 * @effect	The time since the last move of a Game object was made, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceLastMove(this.getSinceLastMove() + dt)
	 */
	public void increaseSinceLastMove(double dt){
		this.setSinceLastMove( getSinceLastMove() + dt);
	}
	
	/**
	 * Variable registering the time since the last move was made for this Timer.
	 */
	private double sinceLastMove;
	
	/* Last sprite */
	
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
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since the last sprite was activated.
	 * @post	The time since the last sprite of a Game object was activated, is equal to dt.
	 * 			| new.getSinceLastSprite() == dt
	 */
	@Basic
	public void setSinceLastSprite(double dt) {
		this.sinceLastSprite = dt;
	}
	
	/**
	 * Increase the elapsed time since the last sprite was activated.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the last sprite of a Game object was activated, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceLastSprite(this.getSinceLastSprite() + dt)
	 */
	public void increaseSinceLastSprite(double dt){
		this.setSinceLastSprite(getSinceLastSprite() + dt);
	}
	
	/**
	 * Variable registering the time since the last sprite was activated for this Timer.
	 */
	private double sinceLastSprite;	
	
	/* Kill */
	
	/**
	 * Return the elapsed time since the Game object was killed.
	 * 
	 * @return	A double that represents the elapsed time since the Game object was killed.
	 */
	@Basic
	public double getSinceKilled() {
		return this.sinceKilled;
	}

	/**
	 * Set the elapsed time since the Game object was killed.
	 * 
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since the Game object was killed.
	 * @post	The time since the Game object was killed is equal to dt.
	 * 			| new.getSinceKilled() == dt
	 */
	@Basic
	public void setSinceKilled(double dt) {
		this.sinceKilled = dt;
	}
	
	/**
	 * Increase the elapsed time since the Game object was killed.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the Game object was killed, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceKilled(this.getSinceKilled() + dt)
	 */
	public void increaseSinceKilled(double dt){
		this.setSinceKilled(getSinceKilled() + dt);
	}
	
	/**
	 * Variable registering the time since the Game object was killed.
	 */
	private double sinceKilled;
	
	/* Enemy Collision	*/
	
	/**
	 * Return the elapsed time since the Game object collided with an enemy.
	 * 
	 * @return	A double that represents the elapsed time since the Game object collided with an enemy.
	 */
	@Basic
	public double getSinceEnemyCollision() {
		return sinceEnemyCollision;
	}

	/**
	 * Set the elapsed time since the Game object collided with an enemy.
	 * 
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since the Game object collided with an enemy.
	 * @post	The time since the Game object collided with an enemy is equal to dt.
	 * 			| new.getSinceEnemyCollision() == dt
	 */
	@Basic
	public void setSinceEnemyCollision(double dt) {
		this.sinceEnemyCollision = dt;
	}
	
	/**
	 * Increase the elapsed time since the Game object collided with an enemy.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the Game object collided with an enemy is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceEnemyCollision(this.getSinceEnemyCollision() + dt)
	 */
	public void increaseSinceEnemyCollision(double dt){
		this.setSinceEnemyCollision( this.getSinceEnemyCollision() + dt);
	}
	
	/**
	 * Variable registering the time since the Game object collided with an enemy.
	 */
	private double sinceEnemyCollision;

	/* Period */
	
	/**
	 * Return the elapsed time since the last period of a Game object was initiated.
	 * 
	 * @return	A double that represents the elapsed time since the last period of a Game object was initiated.
	 */
	@Basic
	public double getSinceLastPeriod() {
		return this.sinceLastPeriod;
	}
	
	/**
	 * Set the elapsed time since the last period of a Game object was initiated.
	 * 
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since the last period of a Game object was initiated.
	 * @post	The time since the last period of a Game object was initiated, is equal to dt.
	 * 			| new.getSinceLastPeriod() == dt
	 */
	@Basic
	public void setSinceLastPeriod(double dt) {
		this.sinceLastPeriod = dt;
	}
	
	/**
	 * Increase the elapsed time since the last period of a Game object was initiated.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	The time since the last period of a Game object was initiated, is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| setSinceLastPeriod(this.getSinceLastPeriod() + dt)
	 */
	public void increaseSinceLastPeriod(double dt){
		this.setSinceLastPeriod(this.getSinceLastPeriod()+dt);
	}
	
	/**
	 * Variable registering the time since the last period of a Game object was initiated.
	 */
	private double sinceLastPeriod;
	
	/**
	 * Return a random period time in a range.
	 * 
	 * @param 	min
	 * 				The minimal random period time.
	 * @param 	max
	 * 				The maximal random period time.
	 * @return	A random double in a range representing the period time.
	 */
	public double getRandomPeriodTime(double min, double max){
		Random random = new Random();
		return min + (max - min)*random.nextDouble();
	}
	
	/* Terrain overlap duration */
	
	/**
	 * Return the terrain overlap duration for a given Terrain.
	 * 
	 * @param 	terrain
	 * 				The Terrain to check the terrain overlap duration for.
	 * @return	The terrain overlap duration for a given Terrain.
	 * @throws 	IllegalArgumentException
	 * 				The given Terrain is not in the collision map.
	 */
	@Basic
	public double getTerrainOverlapDuration(Terrain terrain) throws IllegalArgumentException{
		
		if(!this.terrainOverlapDuration.containsKey(terrain))
			throw new IllegalArgumentException("Terrain not in collision map!");
		
		return this.terrainOverlapDuration.get(terrain);
	}
	
	/**
	 * Set the terrain overlap duration of a given Terrain.
	 * 
	 * @param 	terrain
	 * 				The Terrain to set the terrain overlap duration for.
	 * @param 	dt
	 * 				A double that represents the desired terrain overlap duration.
	 * @post	The terrain overlap duration of the given Terrain is equal to dt.
	 * 			| new.getTerrainOverlapDuration(terrain) == dt
	 */
	@Basic
	public void setTerrainOverlapDuration(Terrain terrain, double dt){
		this.terrainOverlapDuration.put(terrain, dt);
	}
	
	/**
	 * Increase the terrain overlap duration of all terrains.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	For each Terrain, the terrain overlap duration is increased with the amount of dt or
	 * 			decreased with the amount of dt if dt is a negative value.
	 * 			| for terrain in this.terrainOverlapDuration.keySet():
	 * 			|	setTerrainOverlapDuration(terrain, getTerrainOverlapDuration(terrain) + dt)
	 */
	public void increaseTerrainOverlapDuration(double dt){
		for(Terrain terrain : this.terrainOverlapDuration.keySet()){
			this.setTerrainOverlapDuration(terrain, getTerrainOverlapDuration(terrain) + dt);
		}
	}
	
	/**
	 * Map registering the terrain overlap duration for each Terrain.
	 */
	private Map<Terrain, Double> terrainOverlapDuration = new HashMap<Terrain, Double>();
		
	/* Terrain overlap damage */
	
	/**
	 * Return the elapsed time since a Game object has taken damage from the given Terrain 
	 * for the last time.
	 * 
	 * @param 	terrain
	 * 				The Terrain to check the elapsed time since a Game object has taken 
	 * 				terrain damage for the last time for.
	 * @return	The elapsed time since a Game object has taken terrain damage for the last time.
	 * @throws 	IllegalArgumentException
	 * 				The given Terrain is not in the collision map.
	 */
	@Basic
	public double getSinceLastTerrainDamage(Terrain terrain) throws IllegalArgumentException{
		if(!this.sinceLastTerrainDamage.containsKey(terrain))
			throw new IllegalArgumentException("Terrain not in collision map!");
		
		return this.sinceLastTerrainDamage.get(terrain);
	}
	
	/**
	 * Set the elapsed time since a Game object has taken damage from the given Terrain for the last time.
	 * 
	 * @param 	terrain
	 * 				The Terrain to set the elapsed time since a Game object has taken terrain damage
	 * 				for the last time for.
	 * @param 	dt
	 * 				A double that represents the desired elapsed time since a Game object has taken terrain damage
	 * 				for the last time.
	 * @post	The elapsed time since a Game object has taken damage from the given Terrain for the last time
	 *  		is equal to dt.
	 * 			| new.getSinceLastTerrainDamage(terrain) == dt
	 */
	@Basic
	public void setSinceLastTerrainDamage(Terrain terrain, double dt){
		this.sinceLastTerrainDamage.put(terrain, dt);
	}
	
	/**
	 * Increase the elapsed time since a Game object has taken damage from all terrains for the last time.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time that should be added.
	 * @effect	For each Terrain, the elapsed time since a Game object has taken terrain damage for the last time,
	 *  		is increased with the amount of dt or decreased with the amount of dt if dt is a negative value.
	 * 			| for terrain in this.sinceLastTerrainDamage.keySet():
	 * 			|	setSinceLastTerrainDamage(terrain, getSinceLastTerrainDamage(terrain) + dt)
	 */
	public void increaseSinceLastTerrainDamage(double dt){
		for(Terrain terrain : this.sinceLastTerrainDamage.keySet()){
			this.setSinceLastTerrainDamage(terrain, getSinceLastTerrainDamage(terrain) + dt);
		}
	}
	
	/**
	 * Map registering the elapsed time since a Game object has taken damage from each terrain for the last time.
	 */
	private Map<Terrain, Double> sinceLastTerrainDamage = new HashMap<Terrain, Double>();
	
}
