package jumpingalien.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.exceptions.CollisionException;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Animation;
import jumpingalien.model.helper.Timer;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.terrain.TerrainInteraction;
import jumpingalien.model.terrain.TerrainProperties;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A superclass for Game objects in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * 
 * TODO: invars
 * 
 * @version 1.0
 */
public abstract class GameObject {
	
	/******************************************************* GENERAL ***************************************************/
	
	/**
	 * Constant reflecting the duration that an object should be immune after losing hit points due to contact
	 * with some other Game object.
	 * 
	 * @return	the duration that an object should be immune after losing hit points due to contact with some other
	 *  		Game object is equal to 0.6s.
	 * 			| result == 0.6
	 */
	private static final double IMMUNE_TIME = 0.6;

	/**
	 * Constant reflecting the vertical acceleration for Game objects.
	 * 
	 * @return	The vertical acceleration of Game objects is equal to -10.0 m/s^2.
	 * 			| result == -10.0
	 */
	public static final double ACCELERATION_Y = -10.0;
	
	/***************************************************** CONSTRUCTOR *************************************************/

	/**
	 * Constructor for the superclass Game object.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Game object's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Game object's bottom left pixel.
	 * @param 	velocityXInit
	 * 				The initial horizontal velocity of a Game object.
	 * @param 	velocityYInit
	 * 				The initial vertical velocity of a Game object.
	 * @param 	velocityXMax
	 * 				The maximal horizontal velocity of a Game object.
	 * @param 	accelerationXInit
	 * 				The initial horizontal acceleration of a Game object.
	 * @param 	sprites
	 * 				The array of sprite images for a Game object.
	 * @param 	nbHitPoints
	 * 				The number of hit points of a Game object.
	 * @param 	maxNbHitPoints
	 * 				The maximal number of hit points of a Game object.
	 * @effect	Set a new Timer for a Game object.
	 * 			| setTimer(new Timer())
	 * @effect	Set a new Animation for a Game object.
	 * 			| setAnimation(new Animation(this, sprites))
	 * @post	The horizontal position is equal to pixelLeftX.
	 * 			| new.getPositionX == pixelLeftX
	 * @post	The vertical position is equal to pixelBottomY.
	 * 			| new.getPositionY == pixelBottomY
	 * @post	The initial horizontal velocity is equal to velocityXInit.
	 * 			| new.velocityXInit == velocityXInit
	 * @post	The initial vertical velocity is equal to velocityYInit.
	 * 			| new.velocityYInit == velocityYInit
	 * @effect	The maximal horizontal velocity is set to velocityXMax.
	 * 			| setVelocityXMax(velocityXMax)
	 * @post	The initial horizontal acceleration is equal to accelerationXInit.
	 * 			| new.accelerationXInit == accelerationXInit
	 * @effect	Set the initial Orientation to the right.
	 * 			| setOrientation(Orientation.RIGHT)
	 * @post	The maximal number of hit points is equal to maxNbHitPoints.
	 * 			| new.maxNbHitPoints == maxNbHitPoints
	 * @effect	Set the initial number of hit points to nbHitPoints.
	 * 			| setNbHitPoints(nbHitPoints)
	 * @throws	IllegalPositionXException
	 * 				The X position of a Game object is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Y position of a Game object is not a valid Y position.
	 * 				| ! isValidPositionY(positionY)
	 * @throws	IllegalWidthException
	 * 				The width of at least one sprite in the given array sprites is not a valid width.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				The height of at least one sprite in the given array sprites is not a valid height.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public GameObject(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
					  double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
					  int maxNbHitPoints)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{			
		
		this.setTimer(new Timer());	
		this.setAnimation(new Animation(this, sprites));
		
		this.positionX = pixelLeftX;
		this.positionY = pixelBottomY;

		this.velocityXInit = velocityXInit;
		this.velocityYInit = velocityYInit;
		
		this.setVelocityXMax(velocityXMax);
		
		this.accelerationXInit = accelerationXInit;
		
		this.setOrientation(Orientation.RIGHT);		
		
		this.maxNbHitPoints = maxNbHitPoints;
		this.setNbHitPoints(nbHitPoints);
		
	}

	/****************************************************** ANIMATION **************************************************/
	
	/**
	 * Set the animation object for Mazub.
	 * 
	 * @pre		The given animation object is not null.
	 * 			| animation != null
	 * @param 	animation
	 * 				An animation that consists of consecutive sprites.
	 * @post	The new animation for Mazub is equal to the given animation.
	 * 			| new.getAnimation() == animation
	 */
	@Basic
	protected void setAnimation(Animation animation) {
		assert animation != null;
		
		this.animation = animation;
	}

	/**
	 * Return the animation object for Mazub.
	 * 
	 * @return	An animation that consists of consecutive sprites.
	 */
	@Basic
	@Raw
	public Animation getAnimation() {
		return this.animation;
	}
	
	/**
	 * Variable registering the animation of this Mazub.
	 */
	private Animation animation;
	
	public Sprite getCurrentSprite(){
		return this.getAnimation().getCurrentSprite();
	}
	
	/******************************************************** TIMER ****************************************************/

	/**
	 * Set the time properties of Mazub.
	 * 
	 * @pre		The given timer object is not null.
	 * 			| timer != null
	 * @param 	timer
	 * 				A timer that keeps track of several times involving the behaviour of Mazub.
	 * @post	The new time of Mazub is equal to timerClass.
	 * 			| new.getTimer() == timer
	 */
	@Basic
	protected void setTimer(Timer timer) {
		assert timer != null;
		
		this.timer = timer;
	}

	/**
	 * Return the time properties of Mazub.
	 * 
	 * @return	A timer that keeps track of several times involving the behaviour of Mazub.
	 */
	@Basic
	@Raw
	public Timer getTimer() {
		return this.timer;
	}

	protected Timer timer;
	
	/**
	 * Increase the timers of this gameobject with the given dt
	 * 
	 * @param dt
	 * 		
	 */
	protected void updateTimers(double dt){
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		this.getTimer().increaseSinceLastSprite(dt);
		this.getTimer().increaseTerrainOverlapDuration(dt);
		this.getTimer().increaseSinceLastTerrainDamage(dt);
		this.getTimer().increaseSinceEnemyCollision(dt);
		this.getTimer().increaseSinceLastPeriod(dt);
		
		resetTerrainOverlapDuration();
	}
	
	/******************************************************* TERRAIN ***************************************************/
	/**
	 * A map containing the properties for the different types of terrain. 
	 */
	public Map<Terrain, TerrainProperties> allTerrainProperties = new HashMap<Terrain, TerrainProperties>();
	
	/**
	 * Returns the map of terrain properties for the different types of terrain.
	 * 
	 * @return
	 * 			The map of terrain properties for the different types of terrain.
	 */
	public Map<Terrain, TerrainProperties> getAllTerrainProperties() {
		return this.allTerrainProperties;
	}
	
	/**
	 * Returns the terrain properties for the given terrain type.
	 * 
	 * @param terrain
	 * 			The terrain type, as an element of the Terrain enumeration.
	 * @return
	 * 			The terrain properties, as instance of the class TerrainProperties.
	 */
	public TerrainProperties getTerrainPropertiesOf(Terrain terrain) {
		return this.allTerrainProperties.get(terrain);
	}
	
	/**
	 * Sets the terrain properties for the given terrain type to the given properties.
	 * 
	 * @param terrain
	 * 			The terrain type, as element of the Terrain enumeration.
	 * @param terrainProperties
	 * 			The terrain properties, as instance of the class TerrainProperties.
	 */
	protected void setTerrainPropertiesOf(Terrain terrain, TerrainProperties terrainProperties){
		this.allTerrainProperties.put(terrain, terrainProperties);
	}
	
	/**
	 * Checks whether the properties for the given terrain are set.
	 * 
	 * @param terrain
	 * 			The terrain type, as element of the Terrain enumeration.
	 * @return
	 * 			True if and only if the given terrain type is configured.
	 * 
	 */
	public boolean hasTerrainPropertiesOf(Terrain terrain){
		return this.allTerrainProperties.containsKey(terrain);
	}
	
	/**
	 * Sets the terrain properties. Must be implemented in subclass, according to specifications.
	 */
	protected abstract void configureTerrain();
	
	/**
	 * Sets the overlap duration timer for any terrain overlap timer to zero,
	 * if this GameObject does not overlap with that terrain.
	 * Does this for each terrain type, which are elements of the Terrain enumeration.
	 * 
	 */
	private void resetTerrainOverlapDuration(){
		// If character does not collide with terrain type, set the overlapping duration with the terrain type to 0
		Set<Terrain> overlappingTerrainTypes = getOverlappingTerrainTypes();
		for(Terrain terrain : Terrain.getAllTerrainTypes()){
			if(!overlappingTerrainTypes.contains(terrain)){
				getTimer().setTerrainOverlapDuration(terrain, 0);
			}
		}
	}
	
	/**
	 * Checks whether this GameObject overlaps with the given Terrain type.
	 * 
	 * TODO beter documenteren, ik begrijp niet exact de details van die randjes enzo (waarvoor die -1 enzo dienen)
	 * 
	 * @param terrain
	 * 			The given terrain type, as element of the Terrain enumeration.
	 * @return
	 * 			True if and only if this object overlaps with the given Terrain type.
	 */
	public boolean isSubmergedIn(Terrain terrain){		
		World world = this.getWorld();
		
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX() + 1, 						// overlap left
													this.getRoundedPositionY() + 1,							// overlap bottom
													this.getRoundedPositionX() + (this.getWidth() - 1) - 1, // overlap right
													this.getRoundedPositionY() + (this.getHeight() - 1) );	// may not overlap top (otherwise sharks take damage while submerged in water)
		
		for(int[] tile : tiles){
			if( world.getGeologicalFeature(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1])) != terrain){
				return false;	
			}
		}
		
		return true;	
	}
	
	/******************************************************** WORLD ****************************************************/
	
	/**
	 * Returns the world of this Game object.
	 * 
	 * @return
	 * 		The world of this Game object.
	 */
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Checks whether this Game object is related to a proper Game world.
	 * 	 * 
	 * @return
	 * 		True if and only if this game object has a proper world.
	 */
	public boolean hasProperWorld(){
		return this.hasWorld();
	}
	
	/**
	 * Checks whether this Game object has a Game world. 
	 * 
	 * @return
	 * 		True if and only if the Game world of this Game object is not null.
	 */
	public boolean hasWorld(){
		return this.getWorld() != null;
	}
	
	/**
	 * Sets the world of this Game object to the given world
	 * 
	 * @param world
	 * 			The world to which the world of this Game object will be set
	 * @post 	The new world of this Game object will be the given world.
	 * 			| this.getWorld() == world
	 */
	protected void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Set the world of a Game object to world.
	 * 
	 * @param	world
	 * 				The world to which the Game object must be set.
	 * @effect	| setWorld(world)
	 * @effect  | world.addAsGameObject(this)
	 * @throws	IllegalArgumentException
	 * 				| ! canHaveAsWorld(world) or  ! world.canHaveAsGameObject(this)
	 */
	public void setWorldTo(World world) throws IllegalArgumentException{
		
		if(!this.canHaveAsWorld(world))
			throw new IllegalArgumentException("This plant cannot have given world as world!");
		if(!world.canHaveAsGameObject(this))
			throw new IllegalArgumentException("Given world cannot have this plant as plant!");
		
		this.setWorld(world);
		world.addAsGameObject(this);
	}
	
	/**
	 * Unset the world, if any, from this Game object.
	 * 
	 * @effect	| if ( this.hasWorld() )
	 * 			| 	then this.getWorld().removeAsGameObject(this)
	 * @effect	| if ( this.hasWorld() )
	 * 			|	then this.setWorld(null)
	 * 
	 * TODO afwerken, zal ik later doen meot nu weg
	 */
	
	/*
	 * @post   This ownable no longer has an owner.
	 *       | ! new.hasOwner()
	 * @post   The former owner of this owning, if any, no longer
	 *         has this owning as one of its ownings.
	 *       |    (getOwner() == null)
	 *       | || (! (new getOwner()).hasAsOwning(owning))
	 */
	protected void unsetWorld() {
		if(this.hasWorld()){
			World formerWorld = this.getWorld();
			this.setWorld(null);
			formerWorld.removeAsGameObject(this);
		}
	}
	
	public boolean canHaveAsWorld(World world){
		return world != null && !this.isTerminated() && !this.hasWorld();
	}
	
	private World world;

	/******************************************************** SIZE *****************************************************/
	
	/**
	 * Return the width of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the width of Mazub's active sprite.
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}

	/**
	 * Check whether the given width is a valid width for any Mazub instance.
	 * 
	 * @param 	width
	 * 				The width to check.
	 * @return	True if and only if the given width is positive and smaller than the width of the game world.
	 * 			| result == ( (width > 0) && (width < GAME_WIDTH) )
	 */
	public static boolean isValidWidth(int width) {
		return width > 0;
	}

	/**
	 * Return the height of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the height of Mazub's active sprite.
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}

	/**
	 * Check whether the given height is a valid height for any Mazub instance.
	 * 
	 * @param 	height
	 * 				The height to check.
	 * @return	True if and only if the given height is positive and smaller than the height of the game world.
	 * 			| result == ( (height > 0) && (height < GAME_HEIGHT) )
	 */
	public static boolean isValidHeight(int height) {
		return height > 0;
	}
	
	/*************************************************** CHARACTERISTICS ***********************************************/
	
	/* Horizontal position */
	
	/**
	 * Return the rounded down x-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Raw
	public int getRoundedPositionX() {
		return (int) Math.floor(this.getPositionX());
	}
	
	public boolean canHaveAsPositionX(int positionX){
		return positionX >= 0 && (!hasProperWorld() || positionX < this.getWorld().getWorldWidth());
	}

	/**
	 * Return the x-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	@Raw
	public double getPositionX() {
		return this.positionX;
	}

	/**
	 * Check whether the given X position is a valid horizontal position.
	 * 
	 * @param 	positionX
	 * 				A double that represents an x-coordinate.
	 * @return	True if and only if the given x-position positionX is between the boundaries of the game world, 
	 * 			which means that the x-coordinate must be greater than or equal to 0 and smaller or equal to
	 * 			GAME_WIDTH.
	 * 			|  result == ( (positionX >= 0) && (positionX <= GAME_WIDTH-1) )
	 */
	public boolean canHaveAsPositionX(double positionX) {
		return canHaveAsPositionX((int) Math.floor(positionX));
	}

	/**
	 * Set the x-location of Mazub's bottom left pixel.
	 * 
	 * @param	positionX
	 * 				A double that represents the desired x-location of Mazub's bottom left pixel.
	 * @post	The X position of Mazub is equal to positionX.
	 * 			| new.getPositionX() == positionX
	 * @throws	IllegalPositionXException
	 * 				The X position of Mazub is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 */
	@Basic
	@Raw
	protected void setPositionX(double positionX) throws IllegalStateException, IllegalPositionXException, CollisionException {
		if( !canHaveAsPositionX(positionX)) 
			throw new IllegalPositionXException(positionX);
		if( this.doesCollide())
			throw new IllegalStateException("Collision before updating x position");
			
		double oldPositionX = this.positionX;
		this.positionX = positionX;
		
		this.getAnimation().updateSpriteIndex();
		if(this.doesCollide()){
			this.positionX = oldPositionX;
			throw new CollisionException();
		}	
	}
	
	/**
	 * Variable registering the horizontal position of this Mazub.
	 */
	private double positionX;
	
	/* Vertical position*/
	
	/**
	 * Return the rounded down y-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Raw
	public int getRoundedPositionY() {
		return (int) Math.floor(this.getPositionY());
	}

	public boolean canHaveAsPositionY(int positionY){
		return positionY >= 0 && (!hasProperWorld() || positionY < this.getWorld().getWorldHeight());
	}
	
	/**
	 * Return the y-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	@Raw
	public double getPositionY() {
		return this.positionY;
	}
	
	/**
	 * Check whether the given Y position is a valid vertical position.
	 * 
	 * @param 	positionY
	 * 				A double that represents a y-coordinate.
	 * @return 	True if and only if the given y-position positionY is between the boundaries of the game world, 
	 * 			which means that the y-coordinate must be greater than or equal to 0 and smaller or equal to 
	 * 			GAME_HEIGHT. 
	 * 			|  result == ( (positionY >= 0) && (positionY <= GAME_HEIGHT-1) )
	 */
	public boolean canHaveAsPositionY(double positionY) {
		return canHaveAsPositionY((int) Math.floor(positionY));
	}

	/**
	 * Set the y-location of Mazub's bottom left pixel.
	 * 
	 * @param 	positionY
	 * 				A double that represents the desired y-location of Mazub's bottom left pixel.  
	 * @post	The Y position of Mazub is equal to positionY.
	 * 			| new.getPositionY() == positionY
	 * @throws	IllegalPositionYException
	 * 				The Y position of Mazub is not a valid Y position.
	 * 				| ! isValidPositionY(positionY)
	 */
	@Basic
	@Raw
	protected void setPositionY(double positionY) throws IllegalPositionYException, CollisionException {
		if( !canHaveAsPositionY(positionY)) 
			throw new IllegalPositionYException(positionY);
		if( this.doesCollide())
			throw new IllegalStateException("Collision before updating y position");
		
		double oldPositionY = this.positionY;
		this.positionY = positionY;
		
		this.getAnimation().updateSpriteIndex();
		if(this.doesCollide()){
			this.positionY = oldPositionY;
			throw new CollisionException();
		}
	}
	
	/**
	 * Variable registering the vertical position of this Mazub.
	 */
	private double positionY;

	/* Horizontal velocity */
	
	/**
	 * Return the horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the horizontal velocity of Mazub.
	 */
	@Basic
	@Raw
	public double getVelocityX() {
		return this.velocityX;
	}
	
	/**
	 * Checks whether the horizontal velocity of Mazub is a valid velocity.
	 * 
	 * @param 	velocityX
	 * 				A double that represents the horizontal velocity of Mazub.
	 * @return	True if and only if the absolute value of the given horizontal velocity is smaller or equal to
	 *  		the maximal horizontal velocity.
	 * 			| result == ( Math.abs(velocityX) <= this.getVelocityXMax() )
	 */
	public boolean isValidVelocityX(double velocityX) {
		return Math.abs(velocityX) <= this.getVelocityXMax();
	}

	/**
	 * Set the horizontal velocity of Mazub.
	 * 
	 * @param 	velocityX
	 * 				A double that represents the desired horizontal velocity of Mazub.
	 * @post	If the absolute value of the given velocityX is smaller than the maximal horizontal velocity,
	 * 			the horizontal velocity is equal to the given velocityX. Else, the horizontal velocity is equal
	 * 			to the maximal horizontal velocity provided with the sign of velocityX.
	 * 			| if (Math.abs(velocityX) < this.getVelocityXMax())
	 * 			|	then new.getVelocityX() == velocityX
	 * 			| else
	 * 			|	new.getVelocityX() == Math.signum(velocityX)*this.getVelocityXMax()
	 * @post	If the absolute value of the given velocityX is greater than or equal to
	 * 			the maximum horizontal velocity, the horizontal acceleration will be zero.
	 * 			| if(Math.abs(velocityX) >= this.getVelocityXMax())
	 * 			|	then new.getAccelerationX() == 0
	 */
	@Basic
	protected void setVelocityX(double velocityX) {
		if(Util.fuzzyGreaterThanOrEqualTo(Math.abs(velocityX), this.getVelocityXMax())){
			this.setAccelerationX(0);
		}
			
		this.velocityX = Math.max( Math.min( velocityX , this.getVelocityXMax()), -this.getVelocityXMax());
	}
	
	/**
	 * Variable registering the horizontal velocity of this Mazub.
	 */
	private double velocityX;
	
	/* Vertical velocity */
	
	/**
	 * Return the vertical velocity of Mazub.
	 * 
	 * @return	A double that represents the vertical velocity of Mazub.
	 */
	@Basic
	@Raw
	public double getVelocityY() {
		return this.velocityY;
	}

	/**
	 * Set the vertical velocity of this object.
	 * 
	 * @param 	velocityY
	 * 				A double that represents the desired vertical velocity of Mazub.
	 * @post	The vertical velocity is equal to velocityY.
	 * 			| new.getVelocityY() == velocityY
	 */
	@Basic
	@Raw
	protected void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	/**
	 * Variable registering the vertical velocity of this Mazub.
	 */
	private double velocityY;
	
	/* Velocity magnitude */
	
	/**
	 * Returns the magnitude of the velocity of this object.
	 * @return
	 * 		the magnitude of the velocity of this object
	 */
	protected double getVelocityMagnitude(){
		return Math.sqrt( Math.pow(this.getVelocityX(), 2) + Math.sqrt( Math.pow(this.getVelocityY(), 2)));
	}

	/* Initial velocity */ 
	
	/**
	 * Return the initial horizontal velocity of Mazub. The initial horizontal velocity is used when Mazub
	 * starts walking.
	 *  
	 * @return	A double that represents the initial horizontal velocity of Mazub.
	 */
	@Basic
	@Raw
	public double getVelocityXInit() {
		return this.velocityXInit;
	}
	
	public double getVelocityYInit(){
		return this.velocityYInit;
	}

	/**
	 * Variable registering the initial horizontal velocity of this Mazub.
	 */
	protected final double velocityXInit;
	
	protected final double velocityYInit;

	/* Maximal velocity */
	
	/**
	 * Return the maximal horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the maximal horizontal velocity of Mazub.
	 */
	@Basic
	@Raw
	public double getVelocityXMax() {
		return this.velocityXMax;
	}

	/**
	 * Set the maximal horizontal velocity of Mazub.
	 * 
	 * @param 	velocityXMax
	 * 				A double that represents the desired maximal horizontal velocity of Mazub.
	 * @post	If velocityXMax is greater than the initial horizontal velocity, the maximal horizontal
	 * 			velocity is equal to velocityXMax. Otherwise, it's equal to the initial horizontal velocity.
	 * 			| if ( velocityXMax > this.getVelocityXInit() )
	 * 			| 	then new.getVelocityXMax() == velocityXMax
	 * 			| else
	 * 			|	new.getVelocityXMax() == this.getVelocityXInit()
	 */
	@Basic
	@Raw
	protected void setVelocityXMax(double velocityXMax) {
		this.velocityXMax = Math.max( this.getVelocityXInit() , velocityXMax );
	}

	/**
	 * Checks wether the given maximum velocity is a valid value for this instance of Mazub
	 * 
	 * @param 	velocityXMax
	 * 				A double that represents the maximal horizontal velocity that needs to be checked.
	 * @return	True if and only if the given velocityXMax is greater than or equal to the current initial
	 * 			velocity of Mazub.
	 * 			| result == ( velocityXMax >= this.getVelocityXIinit() )
	 */
	public boolean canHaveAsVelocityXMax(double velocityXMax) {
		return  velocityXMax >= this.getVelocityXInit();
	}

	/**
	 * Variable registering the maximal horizontal velocity of this Mazub.
	 */
	private double velocityXMax;

	/* Horizontal acceleration */
	
	/**
	 * Return the horizontal acceleration of Mazub.
	 * 
	 * @return	A double that represents the horizontal acceleration of Mazub.
	 */
	@Basic
	@Raw
	public double getAccelerationX() {
		return this.accelerationX;
	}

	/**
	 * Set the horizontal acceleration of Mazub.
	 * 
	 * @param 	accelerationX
	 * 				A double that represents the desired horizontal acceleration of Mazub.
	 * @post	The horizontal acceleration is equal to accelerationX. However, if accelerationX is equal
	 * 			to NaN, the horizontal acceleration is set to 0 instead.
	 * 			| if ( Double.isNaN(accelerationX) ) 
	 * 			| 	then new.getAccelerationX() == 0
	 * 			| else
	 * 			| 	new.getAccelerationX() == accelerationX
	 */
	@Basic
	@Raw
	protected void setAccelerationX(double accelerationX) {
		if (Double.isNaN(accelerationX)){
			this.accelerationX = 0;
		} else
			this.accelerationX = accelerationX;
	}
	
	/**
	 * Variable registering the horizontal acceleration of this Mazub.
	 */
	private double accelerationX;
	
	/* Vertical acceleration */
	
	/**
	 * Return the vertical acceleration of Mazub.
	 * 
	 * @return	A double that represents the vertical acceleration of Mazub.
	 */
	@Basic
	@Raw
	@Immutable
	public double getAccelerationY() {
		if(this.isOnGround()){
			return 0;
		}else{
			return ACCELERATION_Y;
		}
	}

	/* Acceleration magnitude */ 
	
	/**
	 * Returns the magnitude of the acceleration of this object.
	 * @return
	 * 		the magnitude of the acceleration of this object
	 */
	protected double getAccelerationMagnitude(){
		 return Math.sqrt( Math.pow(this.getAccelerationX(), 2) + Math.sqrt( Math.pow(this.getAccelerationY(), 2)));
	}
	
	/* Initial acceleration */

	public double getAccelerationXInit() {
		return this.accelerationXInit;
	}
	
	protected final double accelerationXInit;

	/* Orientation */
	
	/**
	 * Return the orientation of Mazub.
	 * 
	 * @return	An orientation that represents the current orientation of Mazub.
	 */
	@Basic
	@Raw
	public Orientation getOrientation() {
		return this.orientation;
	}

	/**
	 * Set the orientation of Mazub.
	 * 
	 * @param 	orientation
	 * 				An orientation that represents the desired orientation of Mazub.
	 * @post	The orientation of Mazub is equal to the given orientation.
	 * 			| new.getOrientation() == orientation
	 */
	@Basic
	@Raw
	@Model
	protected void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Checks if the given orientation is valid
	 * 
	 * @param	orientation
	 * 				An orientation that represents the desired orientation of Mazub.
	 * @return	True if and only if the orientation is valid, which means it should be LEFT or RIGHT.
	 * 			| result == ( (orientation == Orientation.LEFT) || orientation == Orientation.RIGHT) )
	 */
	public static boolean isValidOrientation(Orientation orientation) {
		return (orientation == Orientation.LEFT) || (orientation == Orientation.RIGHT);
	}
	
	public Orientation getRandomOrientation(){
		Random random = new Random();
		if (random.nextBoolean())
			return Orientation.RIGHT;
		else {
			return Orientation.LEFT;
		}
	}

	/**
	 * Variable registering the orientation of this Mazub.
	 */
	protected Orientation orientation;
	
	/****************************************************** HIT POINTS *************************************************/
	
	public int getNbHitPoints() {
		return this.nbHitPoints;
	}

	protected void setNbHitPoints(int nbHitPoints) {
		this.nbHitPoints = Math.max( Math.min(nbHitPoints, getMaxNbHitPoints()), 0);
	}
	
	protected void modifyNbHitPoints(int nbHitPoints){
		this.setNbHitPoints(this.getNbHitPoints() + nbHitPoints);
	}
	
	protected void takeDamage(int damageAmount){
		this.modifyNbHitPoints(-damageAmount);
	}

	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints <= getMaxNbHitPoints());
	}

	private int nbHitPoints;
	
	public int getMaxNbHitPoints(){
		return this.maxNbHitPoints;
	}
	
	protected final int maxNbHitPoints;
	
	public boolean isFullHitPoints(){
		return ( this.getNbHitPoints() == this.getMaxNbHitPoints() );
	}
	
	/****************************************************** IMMUNITY ***************************************************/
	
	/**
	 * Returns whether the given alien is currently immune against enemies (see
	 * section 1.2.5 of the assignment).
	 * 
	 * @param alien
	 *            The alien for which to retrieve the immunity status.
	 * @return True if the given alien is immune against other enemies (i.e.,
	 *         there are no interactions between the alien and enemy objects).
	 */
	public boolean isImmune() {
		return this.immune;
	}
	
	protected void setImmune( boolean immune ){
		this.immune = immune;
	}
	
	protected boolean immune;
	
	protected abstract Set<GameObject> getAllImpassableGameObjects();
	
	/******************************************************* RUNNING ***************************************************/

	/**
	 * Make Mazub start moving. Set the initial horizontal velocity and acceleration of Mazub,
	 * depending on his orientation.
	 * 
	 * @param 	orientation
	 * 				The direction in which Mazub starts moving.
	 * @pre		The given orientation is not null.
	 * 			| orientation != null
	 * @post	The orientation of Mazub is equal to the given orientation.
	 * 			| new.getOrientation() == orientation
	 * @post	The horizontal velocity of Mazub is equal to the initial horizontal velocity. It's positive 
	 * 			if the orientation of Mazub is right, negative if the orientation of Mazub is left.
	 * 			| if (this.getOrientation() == RIGHT)
	 * 			|	then new.getVelocityX() == this.getVelocityXInit
	 * 			| else if (this.getOrientation() == LEFT)
	 * 			| 	then new.getVelocityX() == -this.getVelocityXInit
	 * @post	The horizontal acceleration of Mazub is equal to the initial horizontal acceleration. It's
	 * 			positive if the orientation of Mazub is right, negative if the orientation of Mazub is left.
	 * 			| if (this.getOrientation() == RIGHT)
	 * 			|	then new.getAccelerationX() == this.ACCELERATION_X
	 * 			| else if (this.getOrientation() == LEFT)
	 * 			| 	then new.getAccelerationX() == -this.ACCELERATION_X
	 */	
	public void startMove(Orientation orientation){
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * this.getVelocityXInit() );
		this.setAccelerationX( orientation.getSign() * this.getAccelerationXInit());
	}

	/**
	 * Make Mazub end moving. Set the horizontal velocity and acceleration of Mazub to 0.
	 * @pre		The given orientation is not null.
	 * 			| orientation != null
	 * @post	The horizontal velocity of Mazub is equal to zero. 
	 * 			| new.getVelocityX() == 0
	 * @post	The horizontal acceleration of Mazub is equal to zero.
	 * 			| new.getAccelerationX() == 0
	 * @post	The time since the last move was made is reset to 0.
	 *			| (new timer).getSinceLastMove() == 0
	 */
	public void endMove(Orientation orientation){		
		this.setVelocityX(0);
		this.setAccelerationX(0);
		this.getTimer().setSinceLastMove(0);
	}

	/**
	 * Checks whether Mazub is moving.
	 * 
	 * @return 	True if and only if the horizontal velocity is not equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getVelocityX() != 0 )
	 */
	@Raw
	public boolean isMoving() {
		return !Util.fuzzyEquals(this.getVelocityX(), 0);
	}
	
	/************************************************* JUMPING AND FALLING *********************************************/

	/**
	 * Make Mazub start jumping. Set the vertical initial velocity and gravitational acceleration of Mazub.
	 * 
	 * @post	The vertical velocity of Mazub is equal to VELOCITY_Y_INIT.
	 * 			| new.getVelocityY() == VELOCITY_Y_INIT
	 * @post	The vertical acceleration of Mazub is equal to ACCELERATION_Y.
	 * 			| new.getAccelerationY() == ACCELERATION_Y
	 */

	public void startJump() {
		if(this.isOnGround()){
			this.setVelocityY( this.getVelocityYInit() );
		}		
	}

	/**
	 * Make Mazub end jumping. Set the vertical velocity of Mazub to 0 when he's still moving upwards.
	 * 
	 * @post	If the vertical velocity of Mazub was greater than 0 (up to a certain epsilon) ,
	 * 			it is now equal to 0.
	 * 			| if (this.getVelocityY() > 0)
	 * 			|	then new.getVelocityY() == 0
	 * @throws 	IllegalStateException
	 * 				Mazub does not have a positive vertical velocity.
	 * 				| this.getVelocityY() < 0
	 */
	public void endJump() throws IllegalStateException {
		if(! Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 ))
			throw new IllegalStateException("GameObject does not have a positive vertical velocity!");
		
		this.setVelocityY(0);
	}

	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	True if and only if the vertical position of Mazub is equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getPositionY() == 0 )
	 */
	public boolean isOnGround() throws IllegalStateException{		
		if(! hasProperWorld())
			throw new IllegalStateException("GameObject not in proper world!");
		
		return doesInteractWithTerrain(TerrainInteraction.STAND_ON, Orientation.BOTTOM) || 
			   doesInteractWithGameObjects(TerrainInteraction.STAND_ON, Orientation.BOTTOM);
	}
	
	/**
	 * Make Mazub stop falling. Set the vertical velocity and acceleration of Mazub to 0.
	 * 
	 * @post	The vertical velocity of Mazub is equal to 0.
	 * 			| new.getVelocityY() == 0
	 * @post	The vertical acceleration of Mazub is equal to 0.
	 * 			| new.getAccelerationY() == 0
	 */
	@Raw
	protected void stopFall() {
		this.setVelocityY( 0 );
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	public void advanceTime(double dt){
		// determine minDt		
		double minDt;
		
		// iteratively advance time;
		while(!Util.fuzzyGreaterThanOrEqualTo(0, dt)){
			if(this.isTerminated()){
				break;
			}
			minDt = Math.min( dt,  0.01 / (getVelocityMagnitude() + getAccelerationMagnitude()* dt) );
			advanceTimeOnce(minDt);
			dt -= minDt;	
		}
	}
	
	protected void advanceTimeOnce(double dt) throws IllegalArgumentException, IllegalStateException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");	
		if(!this.isTerminated() && !this.hasProperWorld())
			throw new IllegalStateException("This object is not in a proper world!");
		
		processKilledButNotTerminated_NameMustBeChanged(dt);
				
		if(this.isKilled()){ // Als het geterminate is is het sowieso gekilled...
			// Dit is noodzakelijk, want soms wordt advanceTime nog door World uitgevoerd door die minDt gedoe (stel dat in de eerste
			// uitvoer van advanceTime het object geterminate wordt dan blijft die nog effe doorgaan
			return; // Zo gewoon return doen is nie echt proper
		}
		
		// Check last enemy collision and reset immunity status if needed
		if (this.isImmune() && this.getTimer().getSinceEnemyCollision() > IMMUNE_TIME ){
			this.setImmune(false);
		}
		
		updateTimers(dt);
		doMove(dt);
		getAnimation().updateSpriteIndex();
	}

	protected void processKilledButNotTerminated_NameMustBeChanged(double dt) {
		if(this.isKilled() && !this.isTerminated()){
			if(this.getTimer().getSinceKilled() > 0.6){
				this.terminate();
			}else{
				this.getTimer().increaseSinceKilled(dt);
			}
		}
	}
	
	public abstract void doMove(double dt);
	
	/**
	 * Make a Game object change direction.
	 * 
	 * @effect	| if (this.getOrientation() == Orientation.RIGHT)
	 * 			|	then this.endMove(Orientation.RIGHT)
	 * 			| else
	 * 			|	this.endMove(Orientation.LEFT)
	 * @effect	| if (this.getOrientation() == Orientation.RIGHT)
	 * 			|	then this.startMove(Orientation.LEFT)
	 * 			| else
	 * 			|	this.startMove(Orientation.RIGHT)
	 */
	public void changeDirection(){
		
		if (this.getOrientation() == Orientation.RIGHT){
			this.endMove(Orientation.RIGHT);
			this.startMove(Orientation.LEFT);
		}
		else {
			this.endMove(Orientation.LEFT);
			this.startMove(Orientation.RIGHT);
		}
		
	}
	
	/* Horizontal */
	
	/**
	 * Update Mazub's horizontal position according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @post	The horizontal position of Mazub is equal to the previous horizontal position incremented 
	 * 			with the product of the horizontal velocity and dt, and half of the product of the horizontal 
	 * 			acceleration and the second power of dt, all multiplied with a scaling factor which in this 
	 * 			case is equal to 100.
	 * 			| new.getPositionX() == this.getPositionX() + 100*( this.getVelocityX() * dt +
	 * 			| 						0.5 * this.getAccelerationX() * Math.pow( dt , 2 ) )
	 */
	@Model
	protected void updatePositionX(double dt) {
		try{
			double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
			this.setPositionX( this.getPositionX() + 100 * sx );
			this.processOverlap();
		}catch( IllegalPositionXException exc){
			this.kill();
		}catch( CollisionException exc ){
			processHorizontalCollision();
		}
	}
	
	/**
	 * Update Mazub's horizontal velocity according to the given dt.
	 * 
	 * @param dt
	 * 			A double that represents the elapsed time.
	 * @post	The horizontal velocity of Mazub is equal to the previous horizontal velocity incremented 
	 * 			with the product of the horizontal acceleration and dt
	 * 			| new.getVelocityX() == this.getVelocityX() + this.getAccelerationX() * dt
	 */
	protected void updateVelocityX(double dt) {
		double newVx = this.getVelocityX() + this.getAccelerationX() * dt;
		this.setVelocityX( newVx );
	}

	/* Vertical */
	
	/**
	 * Update Mazub's vertical position according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @post	The vertical position of Mazub is equal to the previous vertical position incremented 
	 * 			with the product of the vertical velocity and dt, and half of the product of the vertical 
	 * 			acceleration and the second power of dt, all multiplied with a scaling factor which in this is
	 * 			equal to 100.
	 * 			| new.getPositionY() == this.getPositionY() + 100*( this.getVelocityY() * dt +
	 * 			| 						0.5 * this.getAccelerationY() * Math.pow( dt , 2 ) )
	 */
	@Model
	protected void updatePositionY(double dt) {
		try{
			double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
			this.setPositionY( this.getPositionY() + 100 * sy );
			this.processOverlap();
		}catch( IllegalPositionYException exc){
			this.kill();
		}catch( CollisionException exc){
			processVerticalCollision();
		}
	}

	/**
	 * Update Mazub's vertical velocity according to the given dt.
	 * @param dt
	 * 			A double that represents the elapsed time.
	 * @post	The vertical velocity of Mazub is equal to the previous horizontal velocity incremented 
	 * 			with the product of the vertical acceleration and dt
	 * 			| new.getVelocityY() == this.getVelocityY() + this.getAccelerationY() * dt
	 */
	protected void updateVelocityY(double dt) {
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
	}
		
	/****************************************************** COLLISION **************************************************/
	
	public boolean doesCollide(){
		return doesCollide(Orientation.ALL);
	}
	
	/**
	 * Checks if this object collides with impassable terrain or any other impassable object.
	 * 
	 * @return
	 * 		True if and only if this object collides with impassable terrain or an impassable object.
	 */
	public boolean doesCollide(Orientation orientation){
		return  doesInteractWithTerrain(TerrainInteraction.COLLIDE, orientation) || 
				doesInteractWithGameObjects(TerrainInteraction.COLLIDE, orientation);	}

	/**
	 * Checks if this object collides with a given gameobject.
	 * 
	 * @param other
	 * 		An instance of GameObject to check collision with
	 * @return
	 * 		True if and only if this object and the given gameobject collides.
	 */
	public boolean doesCollideWith(GameObject other, Orientation orientation){
		return this.doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), 
									other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Checks if this object collides with the given region.
	 * 
	 * @param x
	 * 		The horizontal position of the left bottom corner of the region
	 * @param y
	 * 		The vertical position of the left bottom corner of the region
	 * @param width
	 * 		The width of the region
	 * @param height
	 * 		The height of the region
	 * @return
	 */
	public boolean doesCollideWith(int x, int y, int width, int height, Orientation orientation){
		return GameObject.doRegionsOverlap(
				this.getRoundedPositionX() + 1 , this.getRoundedPositionY() + 1, 
				this.getWidth() - 2, this.getHeight() - 2, 
				x, y, width, height, orientation);		
	}
	
	protected void processHorizontalCollision(){
		this.endMove(this.getOrientation());
	}
	
	protected void processVerticalCollision(){
		this.stopFall();
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	public boolean doesOverlap(){
		return this.doesOverlap(Orientation.ALL);
	}
	
	public boolean doesOverlap(Orientation orientation){
		return doesInteractWithTerrain(TerrainInteraction.OVERLAP, orientation) || 
			   doesInteractWithGameObjects(TerrainInteraction.OVERLAP, orientation);
	}
	
	/**
	 * Checks if this object overlaps with the given gameobject
	 * @param other
	 * @return
	 */
	public boolean doesOverlapWith(GameObject other){
		return this.doesOverlapWith(other, Orientation.ALL);
	}
	
	public boolean doesOverlapWith(GameObject other, Orientation orientation){
		return this.doesOverlapWith(other.getRoundedPositionX(), other.getRoundedPositionY(),
									other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Checks if this object overlaps in the given direction with the given object.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param orientation
	 * @return
	 */
	public boolean doesOverlapWith(int x, int y, int width, int height, Orientation orientation){
		return GameObject.doRegionsOverlap(getRoundedPositionX(), getRoundedPositionY(), getWidth(), getHeight(), x, y, width, height, orientation);
	}
	
	/** 
	 * Returns a set containing the overlapping terrain types 
	 */
	public Set<Terrain> getOverlappingTerrainTypes(){
		World world = this.getWorld();
		
		Set<Terrain> overlappingTerrainTypes = new HashSet<Terrain>();
		
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX(), 
													this.getRoundedPositionY(),
													this.getRoundedPositionX() + this.getWidth(), 
													this.getRoundedPositionY() + this.getHeight()  );
		
		for(int[] tile : tiles){
			if(this.doesCollideWith(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1]), world.getTileLength(), world.getTileLength(), orientation)){
				overlappingTerrainTypes.add(world.getGeologicalFeature(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1])));	
			}
		}
		
		return overlappingTerrainTypes;
	}
	
	/** Checks if the region 1 overlaps with the region 2 in the given direction*/
	public static boolean doRegionsOverlap(int x1, int y1, int width1, int height1, int x2, int y2,
										   int width2, int height2, Orientation orientation) 
							throws IllegalArgumentException{
		
		switch (orientation) {
		
			case RIGHT: 
				return  x1 + width1 > x2 &&
						x1 + width1 <= x2 + width2 &&
						GameObject.getYOverlap(y1, height1, y2, height2);
			case LEFT:
				return  x1 >= x2 &&
						x1 < x2 + width2 &&
						GameObject.getYOverlap(y1, height1, y2, height2);
			case TOP:
				return 	y1 + height1 > y2 && 
						y1 + height1 <= y2 + height2 &&
						GameObject.getXOverlap(x1, width1, x2, width2);				
			case BOTTOM:
				return 	y1 >= y2 &&
						y1 < y2 + height2 &&
						GameObject.getXOverlap(x1, width1, x2, width2);
			case ALL:
				return ! ( // Dus geeft true als elke deelexpressie false geeft
						   (x1 + (width1 - 1) < x2) 
						|| (x2 + (width2 - 1) < x1)
						|| (y1 + (height1 - 1) < y2) // top
						|| (y2 + (height2 - 1) < y1) //bottom
					
				);
			default:
				throw new IllegalArgumentException("Given orientation not implemented!");
		}
	}
	
	public static boolean getXOverlap(int x1, int width1, int x2, int width2){
		return	x1 < x2 + width2 && x1 + width1 > x2;
	}
	
	public static boolean getYOverlap(int y1, int height1, int y2, int height2){
		return	y1 < y2 + height2 && y1 + height1 > y2;
	}
	
	protected void processOverlap(){
		this.processTileOverlap();
		this.processGameObjectOverlap();
	}

	protected void processTileOverlap(){
		Set<Terrain> overlappingTerrainTypes = this.getOverlappingTerrainTypes();
		
		// Check all terrain types that are overlapping
		for(Terrain overlappingTerrain : overlappingTerrainTypes){
			
			// If this terrain type isn't configured, don't care
			if(!this.hasTerrainPropertiesOf(overlappingTerrain)){
				break;
			}
			
			// Get configuration for this overlapping terrain type
			TerrainProperties terrainProperties = this.getTerrainPropertiesOf(overlappingTerrain);
			
			// if the gameobject can lose hit points due to contact with this terrain type
			if(  terrainProperties.getDamage() != 0 ){ 
				// If the time since the last hitpoints detection is greater than the configured damage time
				if( getTimer().getSinceLastTerrainDamage(overlappingTerrain) > terrainProperties.getDamageTime() ){ 
					
					// Do damage if the terrain should do damage immediately after contact (for example, magma) 
					// or if the overlapping duration is longer than the configured damage time
					if(terrainProperties.isInstantDamage() || getTimer().getTerrainOverlapDuration(overlappingTerrain) > terrainProperties.getDamageTime() ){
						this.takeDamage( terrainProperties.getDamage() );
						getTimer().setSinceLastTerrainDamage(overlappingTerrain, 0.0);
					}
				}
			}
		}
	}	
	
	protected void processGameObjectOverlap(){
		World world = this.getWorld();
		
		for(Mazub alien:  world.getAllMazubs()){
			if(this.doesOverlapWith(alien) && !this.isImmune()){
				this.processMazubOverlap(alien);
			}
		}
		
		for(Plant plant :  world.getAllPlants()){
			if(this.doesOverlapWith(plant)){
				this.processPlantOverlap(plant);
			}
		}
		
		for(Shark shark :  world.getAllSharks()){
			if(this.doesOverlapWith(shark) && !this.isImmune()){
				processSharkOverlap(shark);
			}
		}
		
		for(Slime slime :  world.getAllSlimes()){
			if(this.doesOverlapWith(slime) && !this.isImmune()){
				processSlimeOverlap(slime);
			}
		}
		
	}
	
	protected abstract void processMazubOverlap(Mazub alien);
	protected abstract void processPlantOverlap(Plant plant);
	protected abstract void processSharkOverlap(Shark shark);
	protected abstract void processSlimeOverlap(Slime slime);
	
	/***************************************************** INTERACTION ************************************************/
	
	public boolean doesInteractWithTerrain(TerrainInteraction interaction, Orientation orientation){
		assert hasProperWorld(); 
		
		World world = this.getWorld();
		
		// Check overlap with tiles
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX(), 
													this.getRoundedPositionY(),
			 										this.getRoundedPositionX() + this.getWidth(), 
													this.getRoundedPositionY() + this.getHeight());

		for(int[] tile : tiles){
			// Check if that tile is passable 
			// and if the given object interacts with a tile
			Terrain terrain = world.getGeologicalFeature(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1]));
			if( !getTerrainPropertiesOf( terrain ).isPassable() ){
				switch(interaction){
					case COLLIDE:
						if( this.doesCollideWith(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1]),
												 world.getTileLength(), world.getTileLength(), orientation))
							return true;
					break;
					case OVERLAP:
						if( this.doesOverlapWith(world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1]),
												 world.getTileLength(), world.getTileLength(), orientation))
							return true;
					break;
					case STAND_ON:
						if( GameObject.doRegionsOverlap(getRoundedPositionX() + 1, getRoundedPositionY(),
													   getWidth() - 2, getHeight(),
													   world.getPositionXOfTile(tile[0]), world.getPositionYOfTile(tile[1]),
													   world.getTileLength(), world.getTileLength(), orientation))
								return true;
					break;
				}	
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if this object collides in the given direction with any other impassable game object.
	 * 
	 * @return
	 * 		True if and only if this object collides with an impassable object.
	 */
	public boolean doesInteractWithGameObjects(TerrainInteraction interaction, Orientation orientation){
		assert hasProperWorld();
		
		for(GameObject object : this.getAllImpassableGameObjects()){
			if(object != this){
				switch(interaction){
					case COLLIDE:
						if(this.doesCollideWith(object, orientation))
							return true;
						break;
					case OVERLAP:
						if(this.doesOverlapWith(object, orientation))
							return true;
						break;
					case STAND_ON:
						assert orientation == Orientation.BOTTOM;
						if(GameObject.doRegionsOverlap(getRoundedPositionX() + 1, getRoundedPositionY(), getWidth() - 2, getHeight(),
								object.getRoundedPositionX(), object.getRoundedPositionY(), object.getWidth(), object.getHeight(), orientation))
								return true;
						break;
				}
			}
		}
		return false;
	}


	/***************************************************** TERMINATION *************************************************/
	
	void kill(){
		this.setNbHitPoints(0);
	}
	
	public boolean isKilled(){
		return this.getNbHitPoints() == 0;
	}
	
	protected void terminate(){
		this.unsetWorld();
		this.terminated = true;
	}
	
	/**
	 * Check if a Game object is terminated.
	 * 
	 * @return	| result == ( this.terminated )
	 */
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * Variable registering the terminated status of a Game object.
	 */
	protected boolean terminated = false;
	
}
