package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Terrain;
import jumpingalien.model.helper.TerrainProperties;
import jumpingalien.util.Sprite;

/**
 * A class of Plants, game objects in the game world of Mazub. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/******************************************************* GENERAL ***************************************************/
	
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
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(this.getWorld().getAllMazubs());
		allImpassableGameObjects.addAll(this.getWorld().getAllPlants());
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