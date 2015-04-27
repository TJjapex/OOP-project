package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.terrain.TerrainProperties;
import jumpingalien.util.Sprite;

/**
 * A class of Plants, game objects in the game world of Mazub. 
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/****************************************************** CONSTANTS **************************************************/
	
	/**
	 * Constant reflecting the period time for a periodic movement of a Plant.
	 * 
	 * @return	| result == 0.5
	 */
	public static final double PERIOD_TIME = 0.5;

	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Plant.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Plant's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Plant's bottom left pixel.
	 * @param 	velocityXInit
	 * 				The initial horizontal velocity of a Plant.
	 * @param 	velocityYInit
	 * 				The initial vertical velocity of a Plant.
	 * @param 	velocityXMax
	 * 				The maximal horizontal velocity of a Plant.
	 * @param 	accelerationXInit
	 * 				The initial horizontal acceleration of a Plant.
	 * @param 	sprites
	 * 				The array of sprite images for a Plant.
	 * @param 	nbHitPoints
	 * 				The number of hit points of a Plant.
	 * @param 	maxNbHitPoints
	 * 				The maximal number of hit points of a Plant.
	 * @pre		| Array.getLength(sprites) == 2
	 * @effect	| super(pixelLeftX, pixelBottomY, velocityXInit,velocityYInit, velocityXMax, accelerationXInit,
	 * 			|	 	sprites, nbHitPoints, maxNbHitPoints)
	 * @effect	| configureTerrain()
	 * @throws 	IllegalPositionXException
	 * 				| ! canHaveAsXPosition(pixelLeftX)
	 * @throws 	IllegalPositionYException
	 * 				| ! canHaveAsYPosition(pixelBottomY)
	 * @throws 	IllegalWidthException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws 	IllegalHeightException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 */
	public Plant(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
			  	 double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints, int maxNbHitPoints)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit,velocityYInit, velocityXMax, accelerationXInit, sprites, 
			  nbHitPoints, 1);

		assert sprites.length == 2;
		
		this.configureTerrain();

	}	
	
	/**
	 * Initialize a Plant with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, number of hit points and maximal number of hit points.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Plant's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Plant's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for a Plant.
	 * @pre		| Array.getLength(sprites) == 2
	 * @effect	| this(pixelLeftX, pixelBottomY, 0.5, 0, 0.5, 0, sprites, 1, 1)
	 * @throws 	IllegalPositionXException
	 * 				| ! canHaveAsXPosition(pixelLeftX)
	 * @throws 	IllegalPositionYException
	 * 				| ! canHaveAsYPosition(pixelBottomY)
	 * @throws 	IllegalWidthException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws 	IllegalHeightException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 */
	public Plant(int pixelLeftX, int pixelBottomY, Sprite[] sprites) 
			throws IllegalPositionXException, IllegalPositionYException,IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 0.5, 0, 0.5, 0, sprites, 1, 1);
	}
	
	/******************************************************* TERRAIN ***************************************************/
	
	/**
	 * Configure the terrain properties for a Plant. 
	 * 
	 * @effect	| setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 0, 0, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 0, 0, false))
	 */
	@Override
	public void configureTerrain(){
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 0, 0, false));
	}
	
	/******************************************************** WORLD ****************************************************/
	
	/**
	 * Add the Plant to his World.
	 * 
	 * @post	| new.getWorld().hasAsGameObject(this) == true
	 */
	@Override
	protected void addToWorld(){
		this.getWorld().plants.add(this);
	}
	
	/**
	 * Remove the Plant from the given World.
	 * 
	 * @param	world
	 * 				The World to remove the Plant from.
	 * @pre		| this != null && !this.hasWorld()
	 * @pre		| world.hasAsGameObject(this)
	 * @post	| world.hasAsGameObject(this) == false
	 */
	@Override
	protected void removeFromWorld(World world){
		assert this != null && !this.hasWorld();
		assert world.hasAsGameObject(this);
		
		world.plants.remove(this);
	}
	
	/**
	 * Check whether or not the Plant has the given World as its World.
	 * 
	 * @param	world
	 * 				The World to check.
	 * @return	| result == ( Plant.getAllInWorld(world).contains(this) )
	 */
	@Override
	protected boolean hasAsWorld(World world){
		return Plant.getAllInWorld(world).contains(this);
	}
	
	/**
	 * Return the number of Plants in the given World.
	 * 
	 * @param 	world
	 * 				The World to check the number of Plants for.
	 * @return	| result == ( Plant.getAllInWorld(world).size() )
	 */
	protected static int getNbInWorld(World world){
		return Plant.getAllInWorld(world).size();
	}
	
	/**
	 * Return all Plants in the given World.
	 * 
	 * @param 	world
	 * 				The World to check.
	 * @return	A Hashset containing all Plants in the given World.
	 */
	public static Set<Plant> getAllInWorld(World world){
		HashSet<Plant> plantsClone =  new HashSet<Plant>(world.plants);
		return plantsClone;
	}

	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Initiate a new periodic movement, if needed, and update the Plant's horizontal position for the given time
	 * interval.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= PERIOD_TIME)
	 * 			|	then this.periodMovement()
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= PERIOD_TIME)
	 * 			|	then this.getTimer().setSinceLastPeriod(0)
	 * @effect	| updatePositionX(dt)
	 */
	@Override
	public void doMove(double dt){		

		/* Periodic movement */
		if (this.getTimer().getSinceLastPeriod() >= PERIOD_TIME){ 
			this.periodicMovement();
			this.getTimer().setSinceLastPeriod(0);
		}
		
		/* Horizontal */
		this.updatePositionX(dt);
	}
	
	/**
	 * Start the periodic movement of a Plant.
	 * 
	 * @effect	| changeDirection()
	 */
	public void periodicMovement(){
		this.changeDirection();
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Process the horizontal collision of a Plant.
	 * 
	 * @note	As an optional implementation, a Plant changes his direction when he collides.
	 * 
	 * @effect	changeDirection()
	 */
	@Override
	protected void processHorizontalCollision() {		
		this.changeDirection();
	}
	
	/**
	 * Return all impassable Game objects for a Plant.
	 * 
	 * @return	A Hashset that contains all Mazubs and Plants in the Plant's world.
	 */
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(Mazub.getAllInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Plant.getAllInWorld(this.getWorld()));
		return allImpassableGameObjects;
	}
	
	/******************************************************* OVERLAP **************************************************/

	/**
	 * Process an overlap of a Plant with a Mazub.
	 * 
	 * @param	mazub
	 * 				The Mazub with whom this Plant overlaps.
	 */
	@Override
	public void processMazubOverlap(Mazub mazub) {
		
	}

	/**
	 * Process an overlap of a Plant with another Plant.
	 * 
	 * @param	plant
	 * 				The other Plant with which this Plant overlaps.
	 */
	@Override
	public void processPlantOverlap(Plant plant) {

	}

	/**
	 * Process an overlap of a Plant with a Shark.
	 * 
	 * @param	shark
	 * 				The shark with which this Plant overlaps.
	 */
	@Override
	public void processSharkOverlap(Shark shark) {

	}

	/**
	 * Process an overlap of a Plant with a Slime.
	 * 
	 * @param	slime
	 * 				The slime with which this Plant overlaps.
	 */
	@Override
	public void processSlimeOverlap(Slime slime) {

	}
		
}