package jumpingalien.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jumpingalien.model.program.Program;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.exceptions.CollisionException;
import jumpingalien.model.exceptions.IllegalEndJumpException;
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
import be.kuleuven.cs.som.annotate.*;

/**
 * A superclass for Game objects in the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * 
 * @invar	The width of the Game object must be valid.
 * 			|	isValidWidth( this.getWidth() )
 * @invar	The height of the Game object must be valid.
 * 			|	isValidHeight( this.getHeight() )
 * @invar	The horizontal position of the Game object must be valid
 * 			|	canHaveAsPositionX( getRoundedPositionX())
 * @invar	The vertical position of the Game object must be valid
 * 			|	canHaveAsPositionY( getRoundedPositionY())
 * @invar	The horizontal velocity must be valid.
 * 			|	canHaveAsVelocityX( this.getVelocityX() )
 * @invar	The Game object can have this maximal horizontal velocity as its maximal horizontal velocity.
 * 			|	canHaveAsVelocityXMax( this.getVelocityXMax() )
 * @invar	The timer object linked to a Game object instance is not null.
 * 			| 	this.getTimer() != null
 * @invar	The animation object linked to a Game object instance is not null.
 * 			| 	this.getAnimation() != null
 * @invar	The current orientation of this Game object is not null.
 * 			|	this.getOrientation() != null
 * @invar	The current orientation is valid.
 * 			|	isValidOrientation( this.getOrientation() )
 * @invar	The current number of this Game object's hit points is valid.
 * 			|	canHaveAsNbHitPoints( this.getNbHitPoints() )
 * @invar	The game object does not collide
 * 			| 	!doesCollide()
 * @invar	This game object is not in a world, or the world is valid
 * 			|	!hasWorld() || hasProperWorld()
 * 
 * @version 1.0
 */
public abstract class GameObject implements IKind{
	
	/****************************************************** CONSTANTS **************************************************/
	
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
	 * @pre		The length of the given array sprites should not be null and should be greater or equal to 2.
	 * 			| (Array.getLength(sprites) >= 2) && sprites != null
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
	 * TODO: program documentation
	 * @throws	IllegalPositionXException
	 * 				The Game object can't have positionX as his horizontal position.
	 * 				| ! canHaveAsPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Game object can't have positionY as his vertical position.
	 * 				| ! canHaveAsPositionY(positionY)
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
					  int maxNbHitPoints, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{			
		assert sprites != null && sprites.length >= 2;
		
		this.setTimer(new Timer());	
		this.setAnimation(new Animation(this, sprites));

		if(!canHaveAsPositionX(pixelLeftX))
			throw new IllegalPositionXException(pixelLeftX);
		if(!canHaveAsPositionY(pixelBottomY))
			throw new IllegalPositionYException(pixelBottomY);
		
		this.positionX = pixelLeftX;
		this.positionY = pixelBottomY;

		this.velocityXInit = velocityXInit;
		this.velocityYInit = velocityYInit;
		
		this.setVelocityXMax(velocityXMax);
		
		this.accelerationXInit = accelerationXInit;
		
		this.setOrientation(Orientation.RIGHT);		
		
		this.maxNbHitPoints = maxNbHitPoints;
		this.setNbHitPoints(nbHitPoints);
		
				
		// Program
		this.program = program;
		if(program != null){
			//System.out.println("GameObject: got program in constructor");
			program.setGameObject(this);
		}
	
	}

	/****************************************************** ANIMATION **************************************************/
	
	/**
	 * Return the animation object for a Game object.
	 * 
	 * @return	An animation that consists of consecutive sprites.
	 */
	@Basic @Raw
	public Animation getAnimation() {
		return this.animation;
	}
	
	/**
	 * Set the animation object for a Game object.
	 * 
	 * @pre		The given animation object is not null.
	 * 			| animation != null
	 * @param 	animation
	 * 				An animation that consists of consecutive sprites.
	 * @post	The new animation for a Game object is equal to the given animation.
	 * 			| new.getAnimation() == animation
	 */
	@Basic
	protected void setAnimation(Animation animation) {
		assert animation != null;
		
		this.animation = animation;
	}
	
	/**
	 * Variable registering the animation of this Game object.
	 */
	private Animation animation;
	
	/**
	 * Return the correct sprite of the Game object, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of the Game object.
	 * @note	No formal documentation was required for this method.
	 */
	public Sprite getCurrentSprite(){
		return this.getAnimation().getCurrentSprite();
	}
	
	/******************************************************** TIMER ****************************************************/

	/**
	 * Return the time properties of a Game object.
	 * 
	 * @return	A timer that keeps track of several times involving the behavior of a Game object.
	 */
	@Basic
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Set the time properties of a Game object.
	 * 
	 * @pre		The given timer object is not null.
	 * 			| timer != null
	 * @param 	timer
	 * 				A timer that keeps track of several times involving the behavior of a Game object.
	 * @post	The new time of a Game object is equal to timer.
	 * 			| new.getTimer() == timer
	 */
	@Basic
	protected void setTimer(Timer timer) {
		assert timer != null;
		
		this.timer = timer;
	}

	/**
	 * Variable registering the timer of this Game object.
	 */
	protected Timer timer;
	
	/**
	 * Increase the timers of this Game object with the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	If the Game object isn't moving, increase the time since his last move.
	 * 			| getTimer().increaseSinceLastMove(dt)
	 * @effect	Increase the time since the last sprite.
	 * 			| getTimer().increaseSinceLastSprite(dt)
	 * @effect	Increase the terrain overlap duration.
	 * 			| getTimer().increaseTerrainOverlapDuration(dt)
	 * @effect	Increase the time since the last terrain damage.
	 * 			| getTimer().increaseSinceLastTerrainDamage(dt)
	 * @effect	Increase the time since the last enemy collision.
	 * 			| getTimer().increaseSinceLastTerrainDamage(dt)
	 * @effect	Increase the time since the last period.
	 * 			| getTimer().increaseSinceLastPeriod(dt)
	 * @effect	Increase the time since the last program execution.
	 * 			| getTimer().increaseSinceLastProgram(dt)
	 * @effect	Reset the terrain overlap duration.
	 * 			| resetTerrainOverlapDuration()
	 */
	protected void updateTimers(double dt){
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		this.getTimer().increaseSinceLastSprite(dt);
		this.getTimer().increaseTerrainOverlapDuration(dt);
		this.getTimer().increaseSinceLastTerrainDamage(dt);
		this.getTimer().increaseSinceEnemyCollision(dt);
		this.getTimer().increaseSinceLastPeriod(dt);
		this.getTimer().increaseSinceLastProgram(dt);
		
		this.resetTerrainOverlapDuration();
	}
	
	/******************************************************* PROGRAM ***************************************************/
	
	public Program getProgram(){
		return this.program;
	}
	
	public boolean hasProgram(){
		return this.getProgram() != null;
	}
	
	protected final Program program; 
	
	/******************************************************* TERRAIN ***************************************************/
	
	/**
	 * A map containing the properties for the different types of terrain. 
	 */
	private Map<Terrain, TerrainProperties> allTerrainProperties = new HashMap<Terrain, TerrainProperties>();
	
	/**
	 * Return the map of terrain properties for the different types of terrain.
	 * 
	 * @return	The map of terrain properties for the different types of terrain.
	 */
	@Basic
	public Map<Terrain, TerrainProperties> getAllTerrainProperties() {
		return this.allTerrainProperties;
	}
	
	/**
	 * Return the terrain properties for the given terrain type.
	 * 
	 * @param 	terrain
	 * 				The terrain type, as an element of the Terrain enumeration.
	 * @return	The terrain properties, as instance of the class TerrainProperties.
	 */
	@Basic
	public TerrainProperties getTerrainPropertiesOf(Terrain terrain) {
		return this.allTerrainProperties.get(terrain);
	}
	
	/**
	 * Set the terrain properties for the given terrain type to the given terrain properties.
	 * 
	 * @param 	terrain
	 * 				The terrain type, as element of the Terrain enumeration.
	 * @param 	terrainProperties
	 * 				The terrain properties, as instance of the class TerrainProperties.
	 * @post	The new terrain properties for the given terrain type are equal to the given
	 * 			terrain properties.
	 * 			| new.getTerrainPropertiesOf(terrain) == terrainProperties
	 */
	@Basic
	protected void setTerrainPropertiesOf(Terrain terrain, TerrainProperties terrainProperties){
		this.allTerrainProperties.put(terrain, terrainProperties);
	}
	
	/**
	 * Check whether the properties for the given terrain are set.
	 * 
	 * @param 	terrain
	 * 				The terrain type, as element of the Terrain enumeration.
	 * @return	True if and only if the given terrain type is configured.
	 * 			| result == ( this.allTerrainProperties.containsKey(terrain) )
	 */
	@Basic
	public boolean hasTerrainPropertiesOf(Terrain terrain){
		return this.allTerrainProperties.containsKey(terrain);
	}
	
	/**
	 * Set the terrain properties for each terrain type. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void configureTerrain();
	
	/**
	 * Set the overlap duration timer for any terrain overlap timer to 0 if this Game object does
	 * not overlap with that terrain. Iterates over each terrain type, which are elements of the
	 * Terrain enumeration.
	 * 
	 * @effect	If a Game object is overlapping with a type of terrain, the timer of the terrain
	 * 			overlap duration is set to 0.
	 * 			| for terrain in Terrain.getAllTerrainTypes():
	 * 			|	if (! this.getOverlappingTerrainTypes().contains(terrain) )
	 * 			|		then this.getTimer().setTerrainOverlapDuration(terrain, 0)
	 */
	@Model
	private void resetTerrainOverlapDuration(){
		Set<Terrain> overlappingTerrainTypes = this.getOverlappingTerrainTypes();
		for(Terrain terrain : Terrain.getAllTerrainTypes()){
			if(!overlappingTerrainTypes.contains(terrain)){
				this.getTimer().setTerrainOverlapDuration(terrain, 0);
			}
		}
	}
	
	/**
	 * Check whether this Game object is submerged in the given type of terrain.
	 * 
	 * @param 	terrain
	 * 				The terrain type to check, as element of the Terrain enumeration.
	 * @return	True if and only if the tiles in which the Game object is located, are all of the given
	 * 			Terrain type.
	 * 			| result == ( for all tile in 
	 * 			|				this.getWorld().getTilePositionsIn(	this.getRoundedPositionX() + 1,
	 *			|													this.getRoundedPositionY() + 1,
	 *			|													this.getRoundedPositionX() + (this.getWidth() - 1) - 1,
	 *			|													this.getRoundedPositionY() + (this.getHeight() - 1) ):
	 *			|				this.getWorld().getGeologicalFeature(world.getPositionXOfTile(tile[0]),
	 *			|													 world.getPositionYOfTile(tile[1])) == terrain			)											
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
	 * Return the world of this Game object.
	 * 
	 * @return	The world of this Game object.
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Check whether this Game object is related to a proper Game world.
	 * 
	 * @return 	True if and only if this game object has a world and the world has this object as Game object
	 * 			| result == ( this.hasWorld() )  && this.getWorld().hasAsGameObject(this)
	 */
	public boolean hasProperWorld(){
		return this.hasWorld() && this.getWorld().hasAsGameObject(this);
	}
	
	/**
	 * Check whether this Game object has a Game world. 
	 * 
	 * @return	True if and only if the Game world of this Game object is not null.
	 * 			| result == ( this.getWorld() != null )
	 */
	public boolean hasWorld(){
		return this.getWorld() != null;
	}
	
	/**
	 * Set the world of this Game object to the given world.
	 * 
	 * @param 	world
	 * 				The world to which the world of this Game object will be set.
	 * @post 	The new world of this Game object will be the given world.
	 * 			| this.getWorld() == world
	 */
	@Basic
	protected void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Make a relation between a Game object and the given world.
	 * 
	 * @param	world
	 * 				The world to which the Game object must be set.
	 * @effect	The world of the Game object is set to the given world.
	 * 			| setWorld(world)
	 * @effect  The Game object is added to the given World.
	 * 			| world.addAsGameObject(this)
	 * @throws	IllegalArgumentException
	 * 				| ( ! canHaveAsWorld(world) ) || ( ! world.canHaveAsGameObject(this) )
	 */
	public void setWorldTo(World world) throws IllegalArgumentException{
		
		if(!this.canHaveAsWorld(world))
			throw new IllegalArgumentException("This Game object cannot have given world as world!");
		if(!world.canHaveAsGameObject(this))
			throw new IllegalArgumentException("Given world cannot have this Game object as Game object!");
		
		this.setWorld(world);
		world.addAsGameObject(this);
		
	}
	
	/**
	 * Unset the world, if any, from this Game object.
	 * 
	 * 
	 * @post	The former world of the Game object will not have this game object anymore
	 * 			|	this.getWorld().hasAsGameObject(this) == false
	 * @post	The new world of this Game object is null
	 * 			|	new.getWorld() == null
	 * 
	 */
	@Model
	protected void unsetWorld() {
		if(this.hasWorld()){
			World formerWorld = this.getWorld();
			this.setWorld(null);
			formerWorld.removeAsGameObject(this);
		}
	}
	
	/**
	 * Check if the Game object can have the given world as his World.
	 * 
	 * @param 	world
	 * 				The world to check.
	 * @return	True if and only if the given world is not null and the Game object
	 * 			is not terminated and has no World yet.
	 * 			| result == ( world != null && !this.isTerminated() && !this.hasWorld() )
	 */
	public boolean canHaveAsWorld(World world){
		return ( world != null && !this.isTerminated() && !this.hasWorld() );
	}
	
	/**
	 * Add the Game object to his World. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void addToWorld();
	
	/**
	 * Remove the Game object from the given World. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void removeFromWorld(World world);
	
	/**
	 * Check whether or not the Game object has the given World as its World. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract boolean hasAsWorld(World world);
		
	/**
	 * Variable registering the World of a Game object.
	 */
	private World world;

	/******************************************************** SIZE *****************************************************/
	
	/**
	 * Return the width of a Game object, depending on the active sprite.
	 * 
	 * @return	An integer that represents the width of the Game object's active sprite.
	 */
	@Override
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}

	/**
	 * Check whether the given width is a valid width for any Game object instance.
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
	 * Return the height of a Game object, depending on the active sprite.
	 * 
	 * @return	An integer that represents the height of the Game object's active sprite.
	 */
	@Override
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}

	/**
	 * Check whether the given height is a valid height for any Game object instance.
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
	 * Return the x-location of a Game object's bottom left pixel.
	 * 
	 * @return	A double that represents the x-coordinate of a Game object's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	public double getPositionX() {
		return this.positionX;
	}

	/**
	 * Return the rounded down x-location of a Game object's bottom left pixel.
	 * 
	 * @return 	An integer that represents the x-coordinate of a Game object's
	 * 			bottom left pixel in the world.
	 */
	@Raw @Override
	public int getRoundedPositionX() {
		return (int) Math.floor(this.getPositionX());
	}
	
	/**
	 * Check whether a Game object can have the given X position as his horizontal position.
	 * 
	 * @param 	positionX
	 * 				An integer that represents the horizontal position to check.
	 * @return	True if and only if the given X position is not negative and if the Game object has
	 * 			a proper World, the given X position should be smaller than the width of that World.
	 * 			| result == ( positionX >= 0 && 
	 * 			|			  (!this.hasProperWorld() || positionX + this.getWidth() < this.getWorld().getWorldWidth()) )
	 */
	@Raw
	public boolean canHaveAsPositionX(int positionX){
		return positionX >= 0 && (!this.hasProperWorld() || positionX + this.getWidth() <= this.getWorld().getWorldWidth());
	}
	
	/**
	 * Check whether a Game object can have the given X position as his horizontal position.
	 * 
	 * @param 	positionX
	 * 				A double that represents an x-coordinate.
	 * @return	True if and only if the Game object can have the rounded down given position
	 * 			as his horizontal position.
	 * 			| result == ( canHaveAsPositionX((int) Math.floor(positionX)) )
	 */
	@Raw
	public boolean canHaveAsPositionX(double positionX) {
		return this.canHaveAsPositionX((int) Math.floor(positionX));
	}

	/**
	 * Set the x-location of a Game object's bottom left pixel.
	 * 
	 * @param	positionX
	 * 				A double that represents the desired x-location of a Game object's bottom left pixel.
	 * @effect	The active sprite gets updated according to the current status of the Game object.
	 * 			| getAnimation().updateSpriteIndex()
	 * @effect	Process the overlap of the Game object.
	 * 			| processOverlap()
	 * @post	The X position of a Game object is equal to positionX if the Game object doesn't collide
	 *  		after the change.
	 * 			| if (! new.doesCollide() )
	 * 			| 	then new.getPositionX() == positionX
	 * @throws	IllegalPositionXException
	 * 				The Game object can't have the given position as his horizontal position.
	 * 				| ! canHaveAsPositionX(positionX)
	 * @throws	IllegalStateException
	 * 				A collision before updating the Game object's position is not allowed.
	 * 				| doesCollide()
	 * @throws	CollisionException
	 * 				The game object does collide after changing his horizontal position.
	 * 				| doesCollide()
	 */
	protected void setPositionX(double positionX) 
					throws IllegalStateException, IllegalPositionXException, CollisionException {
		
		if( !canHaveAsPositionX(positionX)) 
			throw new IllegalPositionXException(positionX);
		if( this.doesCollide())
			throw new IllegalStateException("Collision before updating x position!");
			
		double oldPositionX = this.positionX;
		this.positionX = positionX;
		
		processOverlap();
		this.getAnimation().updateSpriteIndex();
		if(this.doesCollide()){
			this.positionX = oldPositionX;
			throw new CollisionException();
		}	
	}
	
	/**
	 * Variable registering the horizontal position of this Game object.
	 */
	private double positionX;
	
	/* Vertical position*/
	
	/**
	 * Return the y-location of a Game object's bottom left pixel.
	 * 
	 * @return	A double that represents the y-coordinate of a Game object's
	 * 			bottom left pixel in the world.
	 */
	@Basic @Raw
	public double getPositionY() {
		return this.positionY;
	}
	
	/**
	 * Return the rounded down y-location of a Game object's bottom left pixel.
	 * 
	 * @return 	An integer that represents the y-coordinate of a Game object's
	 * 			bottom left pixel in the world.
	 */
	@Raw @Override
	public int getRoundedPositionY() {
		return (int) Math.floor(this.getPositionY());
	}

	/**
	 * Check whether a Game object can have the given Y position as his vertical position.
	 * 
	 * @param 	positionY
	 * 				An integer that represents the vertical position to check.
	 * @return	True if and only if the given Y position is not negative and if the Game object has
	 * 			a proper World, the given Y position should be smaller than the height of that World.
	 * 			| result == ( positionY >= 0 && 
	 * 			|			  (!hasProperWorld() || positionY + this.getHeight() < this.getWorld().getWorldHeight()) )
	 */
	@Raw
	public boolean canHaveAsPositionY(int positionY){
		return positionY >= 0 && (!hasProperWorld() || positionY + this.getHeight() <= this.getWorld().getWorldHeight());
	}
	
	/**
	 * Check whether a Game object can have the given Y position as his vertical position.
	 * 
	 * @param 	positionY
	 * 				A double that represents an y-coordinate.
	 * @return	True if and only if the Game object can have the rounded down given position
	 * 			as his vertical position.
	 * 			| result == ( canHaveAsPositionY((int) Math.floor(positionY)) )
	 */
	@Raw
	public boolean canHaveAsPositionY(double positionY) {
		return canHaveAsPositionY((int) Math.floor(positionY));
	}

	/**
	 * Set the y-location of a Game object's bottom left pixel.
	 * 
	 * @param	positionY
	 * 				A double that represents the desired y-location of a Game object's bottom left pixel.
	 * @effect	The active sprite gets updated according to the current status of the Game object.
	 * 			| getAnimation().updateSpriteIndex()
	 * @post	The Y position of a Game object is equal to positionY if the Game object doesn't collide
	 *  		after the change.
	 * 			| if (! new.doesCollide() )
	 * 			| 	then new.getPositionY() == positionY
	 * @effect	Process the overlap of the Game object.
	 * 			| processOverlap()
	 * @throws	IllegalPositionYException
	 * 				The Game object can't have the given position as his vertical position.
	 * 				| ! canHaveAsPositionY(positionY)
	 * @throws	IllegalStateException
	 * 				A collision before updating the Game object's position is not allowed.
	 * 				| doesCollide()
	 * @throws	CollisionException
	 * 				The game object does collide after changing his vertical position.
	 * 				| doesCollide()
	 */
	protected void setPositionY(double positionY) 
			throws IllegalPositionYException, IllegalStateException, CollisionException {
		
		if( !canHaveAsPositionY(positionY)) 
			throw new IllegalPositionYException(positionY);
		if( this.doesCollide())
			throw new IllegalStateException("Collision before updating y position!");
		
		double oldPositionY = this.positionY;
		this.positionY = positionY;
		
		processOverlap();
		this.getAnimation().updateSpriteIndex();
		if(this.doesCollide()){
			this.positionY = oldPositionY;
			throw new CollisionException();
		}
	}
	
	/**
	 * Variable registering the vertical position of this Game object.
	 */
	private double positionY;

	/* Horizontal velocity */
	
	/**
	 * Return the horizontal velocity of a Game object.
	 * 
	 * @return	A double that represents the horizontal velocity of a Game object.
	 */
	@Basic
	public double getVelocityX() {
		return this.velocityX;
	}
	
	/**
	 * Check whether the horizontal velocity of a Game object is a valid velocity.
	 * 
	 * @param 	velocityX
	 * 				A double that represents the horizontal velocity of a Game object.
	 * @return	True if and only if the absolute value of the given horizontal velocity is smaller or equal to
	 *  		the maximal horizontal velocity.
	 * 			| result == ( Math.abs(velocityX) <= this.getVelocityXMax() )
	 */
	@Raw
	public boolean canHaveAsVelocityX(double velocityX) {
		return Math.abs(velocityX) <= this.getVelocityXMax();
	}

	/**
	 * Set the horizontal velocity of a Game object.
	 * 
	 * @param 	velocityX
	 * 				A double that represents the desired horizontal velocity of a Game object.
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
	@Basic @Model
	protected void setVelocityX(double velocityX) {
		if(Util.fuzzyGreaterThanOrEqualTo(Math.abs(velocityX), this.getVelocityXMax())){
			this.setAccelerationX(0);
		}
			
		this.velocityX = Math.max( Math.min( velocityX , this.getVelocityXMax()), -this.getVelocityXMax());
	}
	
	/**
	 * Variable registering the horizontal velocity of this Game object.
	 */
	private double velocityX;
	
	/* Vertical velocity */
	
	/**
	 * Return the vertical velocity of a Game object.
	 * 
	 * @return	A double that represents the vertical velocity of a Game object.
	 */
	@Basic @Raw
	public double getVelocityY() {
		return this.velocityY;
	}

	/**
	 * Set the vertical velocity of this Game object.
	 * 
	 * @param 	velocityY
	 * 				A double that represents the desired vertical velocity of a Game object.
	 * @post	The vertical velocity is equal to velocityY.
	 * 			| new.getVelocityY() == velocityY
	 */
	@Basic	@Raw
	protected void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	/**
	 * Variable registering the vertical velocity of this Game object.
	 */
	private double velocityY;
	
	/* Velocity magnitude */
	
	/**
	 * Return the magnitude of the velocity of this Game object.
	 * @return	The magnitude of the velocity of this Game object.
	 */
	@Raw
	protected double getVelocityMagnitude(){
		return Math.sqrt( Math.pow(this.getVelocityX(), 2) + Math.sqrt( Math.pow(this.getVelocityY(), 2)));
	}

	/* Initial velocity */ 
	
	/**
	 * Return the initial horizontal velocity of a Game object. The initial horizontal velocity is used when the
	 * Game object starts moving.
	 *  
	 * @return	A double that represents the initial horizontal velocity of a Game object.
	 */
	@Basic @Immutable @Raw
	public double getVelocityXInit() {
		return this.velocityXInit;
	}
	
	/**
	 * Variable registering the initial horizontal velocity of this Game object.
	 */
	protected final double velocityXInit;
	
	/**
	 * Return the initial vertical velocity of a Game object. The initial vertical velocity is used when the
	 * Game object starts jumping.
	 *  
	 * @return	A double that represents the initial vertical velocity of a Game object.
	 */
	@Basic @Immutable @Raw
	public double getVelocityYInit(){
		return this.velocityYInit;
	}
	
	/**
	 * Variable registering the initial vertical velocity of this Game object.
	 */
	protected final double velocityYInit;

	/* Maximal velocity */
	
	/**
	 * Return the maximal horizontal velocity of a Game object.
	 * 
	 * @return	A double that represents the maximal horizontal velocity of a Game object.
	 */
	@Basic
	public double getVelocityXMax() {
		return this.velocityXMax;
	}

	/**
	 * Set the maximal horizontal velocity of a Game object.
	 * 
	 * @param 	velocityXMax
	 * 				A double that represents the desired maximal horizontal velocity of a Game object.
	 * @post	If velocityXMax is greater than the initial horizontal velocity, the maximal horizontal
	 * 			velocity is equal to velocityXMax. Otherwise, it's equal to the initial horizontal velocity.
	 * 			| if ( velocityXMax > this.getVelocityXInit() )
	 * 			| 	then new.getVelocityXMax() == velocityXMax
	 * 			| else
	 * 			|	new.getVelocityXMax() == this.getVelocityXInit()
	 */
	@Basic @Model
	protected void setVelocityXMax(double velocityXMax) {
		this.velocityXMax = Math.max( this.getVelocityXInit() , velocityXMax );
	}

	/**
	 * Check whether the given maximum velocity is a valid value for this Game object.
	 * 
	 * @param 	velocityXMax
	 * 				A double that represents the maximal horizontal velocity that needs to be checked.
	 * @return	True if and only if the given velocityXMax is greater than or equal to the current initial
	 * 			velocity of a Game object.
	 * 			| result == ( velocityXMax >= this.getVelocityXIinit() )
	 */
	public boolean canHaveAsVelocityXMax(double velocityXMax) {
		return  velocityXMax >= this.getVelocityXInit();
	}

	/**
	 * Variable registering the maximal horizontal velocity of this Game object.
	 */
	private double velocityXMax;

	/* Horizontal acceleration */
	
	/**
	 * Return the horizontal acceleration of a Game object.
	 * 
	 * @return	A double that represents the horizontal acceleration of a Game object.
	 */
	@Basic @Raw
	public double getAccelerationX() {
		return this.accelerationX;
	}

	/**
	 * Set the horizontal acceleration of a Game object.
	 * 
	 * @param 	accelerationX
	 * 				A double that represents the desired horizontal acceleration of a Game object.
	 * @post	The horizontal acceleration is equal to accelerationX. However, if accelerationX is equal
	 * 			to NaN, the horizontal acceleration is set to 0 instead.
	 * 			| if ( Double.isNaN(accelerationX) ) 
	 * 			| 	then new.getAccelerationX() == 0
	 * 			| else
	 * 			| 	new.getAccelerationX() == accelerationX
	 */
	@Basic @Model
	protected void setAccelerationX(double accelerationX) {
		if (Double.isNaN(accelerationX)){
			this.accelerationX = 0;
		} else
			this.accelerationX = accelerationX;
	}
	
	/**
	 * Variable registering the horizontal acceleration of this Game object.
	 */
	private double accelerationX;
	
	/* Vertical acceleration */
	
	/**
	 * Return the vertical acceleration of a Game object.
	 * 
	 * @return	If the Game object is on the ground, return 0. Else return ACCELERATION_Y.
	 */
	@Basic @Raw @Immutable
	public double getAccelerationY() {
		return (this.isOnGround()) ? 0 : ACCELERATION_Y;
	}

	/* Acceleration magnitude */ 
	
	/**
	 * Return the magnitude of the acceleration of this Game object.
	 * 
	 * @return	The magnitude of the acceleration of this Game object.
	 */
	@Raw
	protected double getAccelerationMagnitude(){
		 return Math.sqrt( Math.pow(this.getAccelerationX(), 2) + Math.sqrt( Math.pow(this.getAccelerationY(), 2)));
	}
	
	/* Initial acceleration */

	/**
	 * Return the initial horizontal acceleration of this Game object.
	 * 
	 * @return	A double that represents the initial horizontal acceleration of this Game object.
	 */
	@Basic @Raw @Immutable
	public double getAccelerationXInit() {
		return this.accelerationXInit;
	}
	
	/**
	 * Variable registering the horizontal initial acceleration of this Game object.
	 */
	protected final double accelerationXInit;

	/* Orientation */
	
	/**
	 * Return the orientation of a Game object.
	 * 
	 * @return	An orientation that represents the current orientation of the Game object.
	 */
	@Basic @Raw
	public Orientation getOrientation() {
		return this.orientation;
	}

	/**
	 * Set the orientation of a Game object.
	 * 
	 * @param 	orientation
	 * 				An orientation that represents the desired orientation of the Game object.
	 * @post	The orientation of the Game object is equal to the given orientation.
	 * 			| new.getOrientation() == orientation
	 */
	@Basic @Raw @Model
	protected void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Checks if the given orientation is valid
	 * 
	 * @param	orientation
	 * 				An orientation that represents the orientation to check.
	 * @return	True if and only if the orientation is valid, which means it should be LEFT or RIGHT.
	 * 			| result == ( (orientation == Orientation.LEFT) || orientation == Orientation.RIGHT) )
	 */
	public static boolean isValidOrientation(Orientation orientation) {
		return (orientation == Orientation.LEFT) || (orientation == Orientation.RIGHT);
	}
	
	/**
	 * Return a random, valid Orientation for this Game object.
	 * 
	 * @return	A random, valid Orientation for this Game object. 
	 */
	public Orientation getRandomOrientation(){
		Random random = new Random();
		return (random.nextBoolean()) ? Orientation.RIGHT : Orientation.LEFT;
	}

	/**
	 * Variable registering the orientation of this Game object.
	 */
	protected Orientation orientation;
	
	/**
	 * Check if the GameObject is ducking. 
	 * @return
	 * 		result == false
	 */
	public boolean isDucking(){
		return false;
	}
	
	public void startDuck() throws IllegalStateException{
		throw new IllegalStateException("This object cannot duck!");
	}
	
	public void endDuck() throws IllegalStateException{
		throw new IllegalStateException("This object cannot duck!");
	}

	/****************************************************** HIT POINTS *************************************************/
	
	/**
	 * Return the current number of hit points of this Game object.
	 * 
	 * @return	An integer that represents the current number of hit points of this Game object.
	 */
	@Basic
	public int getNbHitPoints() {
		return this.nbHitPoints;
	}

	/**
	 * Set the current number of hit points for this Game object.
	 * 
	 * @param 	nbHitPoints
	 * 				The desired number of hit points for this Game object.
	 * @post	If the given number of hit points is between 0 and the maximal number of hit points,
	 * 			the number of hit points of this Game object is equal to nbHitPoints. If it is negative, 
	 * 			the number of hit points of this Game object is equal to 0. If it is greater than the maximal
	 * 			number of hit points for this Game object, the number of hit points of this Game object is 
	 * 			equal to the maximal number of hit points for this Game object.
	 * 			| if ( nbHitPoints < 0)
	 * 			| 	then new.getNbHitPoints() == 0
	 * 			| else if ( nbHitPoints > this.getMaxNbHitPoints() )
	 * 			|	then new.getNbHitPoints() == this.getMaxNbHitPoints
	 * 			| else
	 * 			| 	new.getNbHitPoints() == nbHitPoints
	 */
	@Model
	protected void setNbHitPoints(int nbHitPoints) {
		this.nbHitPoints = Math.max( Math.min(nbHitPoints, this.getMaxNbHitPoints()), 0);
	}
	
	/**
	 * Modify the number of hit points of this Game object.
	 * 
	 * @param 	nbHitPoints
	 * 				The number of hit points to modify.
	 * @effect	Set the number of hit points of this Game object to his current number of hit points added to
	 * 			the given number of hit points to modify.
	 * 			| setNbHitPoints(this.getNbHitPoints() + nbHitPoints)
	 */
	protected void modifyNbHitPoints(int nbHitPoints){
		this.setNbHitPoints(this.getNbHitPoints() + nbHitPoints);
	}
	
	/**
	 * Let the Game object take an amount of damage.
	 * 
	 * @param 	damageAmount
	 * 				The desired amount of damage for the Game object to take.
	 * @effect	The number of hit points for this Game object gets modified with the negative given amount
	 * 			of damage.
	 * 			| modifyNbHitPoints(-damageAmount)
	 */
	protected void takeDamage(int damageAmount){
		this.modifyNbHitPoints(-damageAmount);
	}

	/**
	 * Check whether or not the given number of hit points is a valid number of hit points for this Game object.
	 * 
	 * @param 	nbHitPoints
	 * 				The number of hit points to check.
	 * @return	True if and only if the given number of hit points is in the range of 0 to the maximal
	 * 			number of hit points for this Game object.
	 * 			| result == (0 <= nbHitPoints && nbHitPoints <= this.getMaxNbHitPoints())
	 */
	public boolean canHaveAsNbHitPoints(int nbHitPoints) {
		return (0 <= nbHitPoints && nbHitPoints <= this.getMaxNbHitPoints());
	}

	/**
	 * Variable registering the number of hit points of this Game object.
	 */
	private int nbHitPoints;
	
	/**
	 * Return the maximal number of hit points for this Game object.
	 * 
	 * @return	An integer that represents the maximal number of hit points for this Game object.
	 */
	@Basic
	@Immutable
	public int getMaxNbHitPoints(){
		return this.maxNbHitPoints;
	}
	
	/**
	 * Variable registering the maximal number of hit points of this Game object.
	 */
	protected final int maxNbHitPoints;
	
	/**
	 * Return whether or not the Game object has full hit points.
	 * 
	 * @return	True if and only if the current number of hit points of the Game object is equal to
	 * 			the maximal number of hit points for the Game object.
	 * 			| result == ( this.getNbHitPoints() == this.getMaxNbHitPoints() )
	 */
	public boolean isFullHitPoints(){
		return ( this.getNbHitPoints() == this.getMaxNbHitPoints() );
	}
	
	/****************************************************** IMMUNITY ***************************************************/
	
	/**
	 * Return whether the given alien is currently immune against enemies.
	 * 
	 * @return	A boolean that represents whether or not the Game object is currently immune.
	 * 			| result == ( this.immune )
	 */
	@Basic
	public boolean isImmune() {
		return this.immune;
	}
	
	/**
	 * Set the immunity status of this Game object.
	 * 
	 * @param 	immune
	 * 				A boolean that represents the desired immunity status of this Game object.
	 * @post	The immunity status of this Game object is equal to immune.
	 * 			| new.isImmun() == immune
	 */
	@Basic
	protected void setImmune( boolean immune ){
		this.immune = immune;
	}
	
	/**
	 * Variable registering the current immunity status of this Game object.
	 */
	protected boolean immune;
	
	/**
	 * Get all impassable Game objects for this Game object. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract Set<GameObject> getAllImpassableGameObjects();
	
	/******************************************************* RUNNING ***************************************************/

	/**
	 * Make the Game object start moving.
	 * 
	 * @param 	orientation
	 * 				The direction in which a Game object starts moving.
	 * @pre		The given orientation should be a valid orientation.
	 * 			| isValidOrientation(orientation)
	 * @effect	The orientation of the Game object is set to orientation.
	 * 			| setOrientation(orientation)
	 * @effect	The horizontal velocity of a Game object is set to the initial horizontal velocity provided with the sign
	 *  		of the given orientation.
	 * 			| setVelocityX( orientation.getSign() * this.getVelocityXInit() )
	 * @effect	The horizontal acceleration of a Game object is set to the initial horizontal acceleration provided with
	 * 			the sign of the given orientation.
	 * 			| setAccelerationX( orientation.getSign() * accelerationXInit)
	 */	
	public void startMove(Orientation orientation){
		assert isValidOrientation(orientation);
		
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * this.getVelocityXInit() );
		this.setAccelerationX( orientation.getSign() * this.getAccelerationXInit());
	}

	/**
	 * Make the Game object end moving.
	 * 
	 * @pre		The given orientation should be a valid orientation.
	 * 			| isValidOrientation(orientation)
	 * @effect	The horizontal velocity of a Game object is set to zero. 
	 * 			| setVelocityX(0)
	 * @effect	The horizontal acceleration of a Game object is set to zero.
	 * 			| setAccelerationX(0)
	 * @effect	The time since the last move was made is set to 0.
	 *			| getTimer().setSinceLastMove(0)
	 */
	public void endMove(Orientation orientation){	
		assert isValidOrientation(orientation);
		
		this.setVelocityX(0);
		this.setAccelerationX(0);
		this.getTimer().setSinceLastMove(0);
	}

	/**
	 * Check whether the Game object is moving.
	 * 
	 * @return 	True if and only if the horizontal velocity is not equal to 0. (up to a certain epsilon)
	 * 			| result == ( !Util.fuzzyEquals(this.getVelocityX(), 0) )
	 */
	@Raw
	public boolean isMoving() {
		return !Util.fuzzyEquals(this.getVelocityX(), 0);
	}
	
	/**
	 * Check whether the Game object is moving in the given direction.
	 * 
	 * @param orientation
	 * 			The direction to check for
	 * 
	 * @return 	True if and only if the horizontal velocity is not equal to 0 and the current direction is the given orientation.
	 * 			| result == ( !Util.fuzzyEquals(this.getVelocityX(), 0) ) && this.getOrientation
	 */
	@Raw
	public boolean isMoving(Orientation orientation) {
		return this.getOrientation() == orientation;
	}
	
	
	/************************************************* JUMPING AND FALLING *********************************************/

	/**
	 * Make a Game object start jumping.
	 * 
	 * @effect	If the Game object is on the ground, set the vertical velocity to the initial vertical velocity.
	 * 			| setVelocityY( this.getVelocityYInit() )
	 */

	public void startJump() {
		if(this.isOnGround()){
			this.setVelocityY( this.getVelocityYInit() );
		}		
	}

	/**
	 * Make a Game object end jumping.
	 * 
	 * @effect	Set the vertical velocity of the Game object to 0.
	 * 			| setVelocityY(0)
	 * @throws 	IllegalEndJumpException
	 * 				The game object does not have a positive vertical velocity. (up to a certain epsilon)
	 * 				| ! Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )
	 */
	public void endJump() throws IllegalEndJumpException {
		if(! Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 ))
			throw new IllegalEndJumpException();
		
		this.setVelocityY(0);
	}

	/**
	 * Check whether the Game object is jumping.
	 * 
	 * @return	True if and only if the Game object is standing on impassable terrain or another impassable.
	 * 			Game object.
	 * 			| result == ( doesInteractWithTerrain(TerrainInteraction.STAND_ON, Orientation.BOTTOM) || 
	 *		   	|			  doesInteractWithGameObjects(TerrainInteraction.STAND_ON, Orientation.BOTTOM) )
	 * @throws	IllegalStateException
	 *				The Game object has no proper World.
	 *				| ! hasProperWorld()
	 */
	public boolean isOnGround() throws IllegalStateException{		
		if(! this.hasProperWorld()){
			System.out.println(this);
			throw new IllegalStateException("GameObject not in proper world!");}
		
		return doesInteractWithTerrain(TerrainInteraction.STAND_ON, Orientation.BOTTOM) || 
			   doesInteractWithGameObjects(TerrainInteraction.STAND_ON, Orientation.BOTTOM);
	}
	
	/**
	 * Make a Game object stop falling.
	 * 
	 * @effect	The vertical velocity of the Game object is set to 0.
	 * 			| setVelocityY( 0 )
	 */
	@Raw
	protected void stopFall() {
		this.setVelocityY( 0 );
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Advance time for this Game object in general.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @note	No further documentation was required for this method.
	 * @throws 	IllegalArgumentException
	 * 				The given dt is not in range of 0 to 0.2. (up to a certain epsilon)
	 * 				| !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2)
	 * @throws 	IllegalStateException
	 * 				The Game object is already terminated or it has no proper World when advanceTimeOnce is invoked.
	 * 				| !this.isTerminated() && !this.hasProperWorld()
	 */
	void advanceTime(double dt) throws IllegalArgumentException, IllegalStateException{
		
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");	
		
		if (this.hasProgram() && dt < 0.001)
			this.getProgram().executeNext();
		
		double minDt;
		
		while(!Util.fuzzyGreaterThanOrEqualTo(0, dt)){
			if(this.isTerminated()){
				break;
			}
			minDt = Math.min( dt,  0.01 / (this.getVelocityMagnitude() + this.getAccelerationMagnitude()* dt) );
			this.advanceTimeOnce(minDt);
			dt -= minDt;	
		}
	}
	
	/**
	 * Advance time for this Game object once.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @note	No further documentation was required for this method.
	 * @throws 	IllegalArgumentException
	 * 				The given dt is not in range of 0 to 0.2. (up to a certain epsilon)
	 * 				| !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2)
	 * @throws 	IllegalStateException
	 * 				The Game object is already terminated or it has no proper World.
	 * 				| !this.isTerminated() && !this.hasProperWorld()
	 */
	@Model
	protected void advanceTimeOnce(double dt) throws IllegalArgumentException, IllegalStateException{
		
		if(this.hasProgram()){
			this.advanceProgram();
		}
		
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");	
		if( !this.isTerminated() && !this.hasProperWorld())
			throw new IllegalStateException("This object is not in a proper world!");
		
		if(this.isKilled() && !this.isTerminated()){
			this.processKilled(dt);
		}
				
		if(!this.isKilled()){
			
			// Check last enemy collision and reset immunity status if needed
			if (this.isImmune() && this.getTimer().getSinceEnemyCollision() > IMMUNE_TIME ){
				this.setImmune(false);
			}
			
			this.updateTimers(dt);
			
			this.doMove(dt);
			
			this.getAnimation().updateSpriteIndex();
		}	
	
	}
	
	protected void advanceProgram(){
		// TODO moet nog met tijd enzo rekening gehouden worden, maar kzal al blij zijn als het gewoon iets doet nu
		//System.out.println(this.getTimer().getSinceLastProgram()/0.001);
		for (int i = 0; i < this.getTimer().getSinceLastProgram()/0.001; i++){
			this.getProgram().executeNext();
		}
		
		this.getTimer().setSinceLastProgram( this.getTimer().getSinceLastProgram()%0.001 );
				
	}

	/**
	 * Process the killed status of a Game object. 0.6s after the moment the Game object was killed,
	 * it gets terminated.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	If the time since the Game object was killed is greater than 0.6s, the Game object
	 * 			gets terminated.
	 * 			| if ( this.getTimer().getSinceKilled() >= 0.6 )
	 * 			|	then this.terminate()
	 * @effect	If the time since the Game object was killed is smaller than 0.6s, the time since the
	 * 			Game object was killed is increased with the given dt.
	 * 			| if ( this.getTimer().getSinceKilled() <= 0.6 )
	 * 			|	then this.getTimer().increaseSinceKilled(dt)
	 */
	protected void processKilled(double dt) {
		if(this.getTimer().getSinceKilled() >= 0.60){
			this.terminate();
		}else{
			this.getTimer().increaseSinceKilled(dt);
		}
	}
	
	/**
	 * Make the Game object execute his movement. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void doMove(double dt);
	
	/**
	 * Make a Game object change direction.
	 * 
	 * @effect	End his current movement.
	 * 			| if (this.getOrientation() == Orientation.RIGHT)
	 * 			|	then this.endMove(Orientation.RIGHT)
	 * 			| else
	 * 			|	this.endMove(Orientation.LEFT)
	 * @effect	Start the opposite movement.
	 * 			| if (this.getOrientation() == Orientation.RIGHT)
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
	 * Update the Game object's horizontal position according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	The horizontal position of a Game object is set to the previous horizontal position incremented 
	 * 			with the product of the horizontal velocity and dt, and half of the product of the horizontal 
	 * 			acceleration and the second power of dt, all multiplied with a scaling factor which in this 
	 * 			case is equal to 100.
	 * 			| setPositionX( this.getPositionX() + 100*( this.getVelocityX() * dt +
	 * 			| 						0.5 * this.getAccelerationX() * Math.pow( dt , 2 ) ) )
	 * @effect	If an IllegalPositionXException is thrown in the try part of this method, catch it and 
	 * 			kill the Game object.
	 * 			| kill();
	 * @effect	If a CollisionException is thrown in the try part of this method,  catch it and process 
	 * 			the horizontal collision.
	 * 			| processHorizontalCollision()	
	 */
	protected void updatePositionX(double dt) {
		try{
			double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
			this.setPositionX( this.getPositionX() + 100 * sx );
		}catch( IllegalPositionXException exc){
			this.kill();
		}catch( CollisionException exc ){
			processHorizontalCollision();
		}
	}
	
	/**
	 * Update the Game object's horizontal velocity according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time.
	 * @effect	The horizontal velocity of a Game object is set to the previous horizontal velocity incremented 
	 * 			with the product of the horizontal acceleration and dt.
	 * 			| setVelocityX( this.getVelocityX() + this.getAccelerationX() * dt )
	 */
	protected void updateVelocityX(double dt) {
		double newVx = this.getVelocityX() + this.getAccelerationX() * dt;
		this.setVelocityX( newVx );
	}

	/* Vertical */
	
	/**
	 * Update the Game object's vertical position according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	The vertical position of the Game object is set to the previous vertical position incremented 
	 * 			with the product of the vertical velocity and dt, and half of the product of the vertical 
	 * 			acceleration and the second power of dt, all multiplied with a scaling factor which in this is
	 * 			equal to 100.
	 * 			| setPositionY( this.getPositionY() + 100*( this.getVelocityY() * dt +
	 * 			| 						0.5 * this.getAccelerationY() * Math.pow( dt , 2 ) ) )
	 * @effect	If an IllegalPositionYException is thrown in the try part of this method, catch it and 
	 * 			kill the Game object.
	 * 			| kill();
	 * @effect	If a CollisionException is thrown in the try part of this method,  catch it and process 
	 * 			the vertical collision.
	 * 			| processVerticalCollision()	
	 */
	protected void updatePositionY(double dt) {
		try{
			double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
			this.setPositionY( this.getPositionY() + 100 * sy );
		}catch( IllegalPositionYException exc){
			this.kill();
		}catch( CollisionException exc){
			processVerticalCollision();
		}
	}

	/**
	 * Update the Game object's vertical velocity according to the given dt.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed time.
	 * @effect	The vertical velocity of a Game object is set to the previous vertical velocity incremented 
	 * 			with the product of the vertical acceleration and dt.
	 * 			| setVelocityY( this.getVelocityY() + this.getAccelerationY() * dt )
	 */
	protected void updateVelocityY(double dt) {
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
	}
		
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Check if this object collides with impassable Terrain or any other impassable Game object. Colliding means that
	 * not only the outer perimeter of the Game object overlaps with the other Game object or Terrain.
	 * 
	 * @return	True if and only if this object collides with impassable Terrain or an impassable Game object in any
	 * 			direction.
	 * 			| result == ( doesCollide(Orientation.ALL) )
	 */
	public boolean doesCollide(){
		return doesCollide(Orientation.ALL);
	}
	
	/**
	 * Check if this Game object collides with impassable Terrain or any other impassable Game object in the
	 * given direction.
	 * 
	 * @param 	orientation	
	 * 				The orientation to check collision with.
	 * @return	True if and only if this Game object collides with impassable Terrain or an impassable Game object in
	 *  		the given direction.
	 * 			|	result == ( doesInteractWithTerrain(TerrainInteraction.COLLIDE, orientation) || 
				|				doesInteractWithGameObjects(TerrainInteraction.COLLIDE, orientation) )
	 */
	public boolean doesCollide(Orientation orientation){
		return  doesInteractWithTerrain(TerrainInteraction.COLLIDE, orientation) || 
				doesInteractWithGameObjects(TerrainInteraction.COLLIDE, orientation);	
	}

	/**
	 * Check if this Game object collides with a given Game object in the given orientation.
	 * 
	 * @param 	other
	 * 				An instance of Game object to check collision with.
	 * @param	orientation
	 * 				The orientation to check collision with.
	 * @return	True if and only if this object and the given Game object collides.
	 * 			|	result == ( doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), 
				|					 			other.getWidth(), other.getHeight(), orientation) 			)
	 */
	public boolean doesCollideWith(GameObject other, Orientation orientation){
		return this.doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), 
									other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Check if this Game object collides with the given rectangular region in the given orientation.
	 * 
	 * @param 	x
	 * 				The horizontal position of the left bottom corner of the rectangular region.
	 * @param 	y
	 * 				The vertical position of the left bottom corner of the rectangular region.
	 * @param 	width
	 * 				The width of the region.
	 * @param 	height
	 * 				The height of the region.
	 * @param 	orientation
	 * 				The orientation to check collision with.
	 * @return	True if and only if the object collides with the given region, in the given orientation.
	 * 			| result == ( GameObject.doRegionsOverlap( this.getRoundedPositionX() + 1 , this.getRoundedPositionY() + 1, 
	 * 			|										   this.getWidth() - 2, this.getHeight() - 2, 
	 * 			|											x, y, width, height, orientation)				)
	 */
	public boolean doesCollideWith(int x, int y, int width, int height, Orientation orientation){
		return GameObject.doRegionsOverlap(
				this.getRoundedPositionX() + 1 , this.getRoundedPositionY() + 1, 
				this.getWidth() - 2, this.getHeight() - 2, 
				x, y, width, height, orientation);		
	}
	
	/**
	 * Defines the actions that need to be executed when the Game object collides horizontally.
	 * 
	 * @effect	| endMove(this.getOrientation())
	 */
	protected void processHorizontalCollision(){
		this.endMove(this.getOrientation());
	}
	
	/**
	 * Defines the actions that need to be executed when the Game object collides vertically.
	 * 
	 * @effect	| stopFall()
	 */
	protected void processVerticalCollision(){
		this.stopFall();
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Check whether or not a Game object overlaps with another impassable Game object or impassable Terrain in any
	 * direction. This means that the method will return true if and only if at least one pixel overlaps.
	 * 
	 * @return	True if and only if this Game object does overlap with another Game object or impassable Terrain in
	 * 			any direction.
	 * 			| result == ( doesOverlap(Orientation.ALL) )
	 */
	public boolean doesOverlap(){
		return this.doesOverlap(Orientation.ALL);
	}
	
	/**
	 * Check whether or not a Game object overlaps with another impassable Game object or impassable Terrain in a
	 * given direction. This means that the method will return true if and only if at least one pixel overlaps.
	 * 
	 * @param 	orientation
	 * 				The orientation to check overlap with.
	 * @return	True if and only if this Game object does overlap with another Game object or impassable Terrain in
	 * 			the given direction.
	 * 			| result == ( doesInteractWithTerrain(TerrainInteraction.OVERLAP, orientation) || 
			   	|			  doesInteractWithGameObjects(TerrainInteraction.OVERLAP, orientation) )
	 */
	public boolean doesOverlap(Orientation orientation){
		return doesInteractWithTerrain(TerrainInteraction.OVERLAP, orientation) || 
			   doesInteractWithGameObjects(TerrainInteraction.OVERLAP, orientation);
	}
	
	/**
	 * Check if this object overlaps with the given Game object.
	 * 
	 * @param 	other
	 * 				The Game object to check collision with.
	 * @return	True if and only if this Game object overlaps with the other Game object.
	 * 			| result == ( doesOverlapWith(other, Orientation.ALL) )
	 */
	public boolean doesOverlapWith(GameObject other){
		return this.doesOverlapWith(other, Orientation.ALL);
	}
	
	/**
	 * Check if this object overlaps with the given Game object, in the given orientation.
	 * 
	 * @param 	other
	 * 				The Game object to check collision with.
	 * @param 	orientation
	 * 				The orientation to check overlap with.
	 * @return	True if and only if this Game object overlaps with the other Game object.
	 *	 		| result == ( doesOverlapWith(other, Orientation.ALL) )
	 */
	public boolean doesOverlapWith(GameObject other, Orientation orientation){
		return this.doesOverlapWith(other.getRoundedPositionX(), other.getRoundedPositionY(),
									other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Check if this Game object overlaps with the given object, in the given direction.
	 * 
	 * @param 	x
	 * 				The horizontal position of the left bottom corner of the region.
	 * @param 	y
	 * 				The vertical position of the left bottom corner of the region.
	 * @param 	width
	 * 				The width of the region.
	 * @param 	height
	 * 				The height of the region.
	 * @param 	orientation
	 * 				The direction to check overlap with.
	 * @return	True if and only if the object overlaps with the given region, in the given orientation.
	 * 			| result == ( GameObject.doRegionsOverlap( getRoundedPositionX(), getRoundedPositionY(), 
	 * 			| 										   getWidth(), getHeight(), x, y, width, height, orientation)	)
	 */
	public boolean doesOverlapWith(int x, int y, int width, int height, Orientation orientation){
		return GameObject.doRegionsOverlap(getRoundedPositionX(), getRoundedPositionY(), 
				getWidth(), getHeight(), x, y, width, height, orientation);
	}
	
	/** 
	 * Return a set containing the overlapping Terrain types.
	 *  
	 * @return	A set containing the overlapping terrain types.
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
	
	/**
	 * Check if one given region (region 1) overlaps with another given region (region 2), in the given direction.
	 * 
	 * @param 	x1
	 * 				The horizontal position of the left bottom corner of the first region.
	 * @param 	y1
	 * 				The vertical position of the left bottom corner of the first region.
	 * @param 	width1
	 * 				The width of the first region.
	 * @param 	height1
	 * 				The height of the first region.
	 * @param 	x2
	 * 				The horizontal position of the left bottom corner of the second region.
	 * @param 	y2
	 * 				The vertical position of the left bottom corner of the second region.
	 * @param 	width2
	 * 				The width of the second region.
	 * @param 	height2
	 * 				The height of the second region.
	 * @param 	orientation
	 * 				The orientation to check overlap in. 
	 * @return	In case orientation is Orientation.ALL, this method will check if at least one pixel of region 1 overlaps
	 *  			with one pixel of region 2.
	 * 	 		In case orientation is Orientation.BOTTOM, this method will check if the bottom perimeter overlaps with any
	 *  			pixel of region 2. 
	 * 			In case orientation is Orientation.TOP, this method will check if the top perimeter overlaps with any pixel
	 *  			of region 2.
	 * 			In case orientation is Orientation.RIGHT, this method will check if the right perimeter overlaps with any pixel
	 *  			of region 2.
	 * 			In case orientation is Orientation.LEFT, this method will check if the left perimeter overlaps with any pixel
	 *  			of region 2.
	 * @note	The perimeters in the assignment are defined as follows:
	 * 				bottom: x..x + Xg - 1, y
	 * 				top:	x..x + Xg - 1, y + Yg - 1 
	 * 				left:	x, y+1..y+Yg-2
	 * 				right:	x+Xg-1, y+1..y+Yg-2
	 * @throws 	IllegalArgumentException
	 * 				The given orientation is not implemented.
	 * 				| orientation != Orientation.RIGHT &&
	 * 				| orientation != Orientation.LEFT &&
	 * 				| orientation != Orientation.TOP &&
	 * 				| orientation != Orientation.BOTTOM &&
	 * 				| orientation != Orientation.ALL &&
	 */
	public static boolean doRegionsOverlap(int x1, int y1, int width1, int height1, int x2, int y2,
										   int width2, int height2, Orientation orientation) 
							throws IllegalArgumentException{
		
		switch (orientation) {
		
			case RIGHT: 
				return  x1 + width1 > x2 &&
						x1 + width1 <= x2 + width2 &&
						GameObject.doPixelsOverlap(y1, height1, y2, height2);
			case LEFT:
				return  x1 >= x2 &&
						x1 < x2 + width2 &&
						GameObject.doPixelsOverlap(y1, height1, y2, height2);
			case TOP:
				return 	y1 + height1 > y2 && 
						y1 + height1 <= y2 + height2 &&
						GameObject.doPixelsOverlap(x1, width1, x2, width2);				
			case BOTTOM:
				return 	y1 >= y2 &&
						y1 < y2 + height2 &&
						GameObject.doPixelsOverlap(x1, width1, x2, width2);
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
	
	/**
	 * Check whether two lines of pixels do overlap. The lines are defined by their most-left pixel (x) and their width. 
	 * This can also be used for vertical lines, since the condition is the same.
	 * 
	 * @param 	x1
	 * 				The first pixel of the first line.
	 * @param 	width1
	 * 				The width of the first line.
	 * @param 	x2
	 * 				The first pixel of the second line
	 * @param 	width2
	 * 				The width of the second line
	 * @return	True if and only if the given lines of pixels overlap, which means they have at least one overlapping pixel.
	 * 			| result == ( x1 < x2 + width2 && x1 + width1 > x2 )
	 */
	public static boolean doPixelsOverlap(int x1, int width1, int x2, int width2){
		return	x1 < x2 + width2 && x1 + width1 > x2;
	}
	
	/***************************************************** INTERACTION ************************************************/
	
	/**
	 * Check if this Game object interacts with any other impassable Game object, in the given direction.
	 * 
	 * @param	interaction
	 * 				The desired type of interaction to check, as an element of the TerrainInteraction enumeration.
	 * @param	orientation
	 * 				The desired direction in which the interaction should be checked.
	 * @pre		The game object must have a proper world.
	 * 			| hasProperWorld()
	 * @return	In case the interaction is COLLIDE, return true if and only if this object collides with impassable
	 * 			Terrain.
	 * 			In case the interaction is OVERLAP, return true if and only if this object ovlerlaps with impassable
	 * 			Terrain.
	 * 			In case the interaction is STAND_ON, return true if and only if this object stands on the impassable
	 * 			Terrain.
	 */
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
	 * Check if this Game object interacts with any other impassable Game object, in the given direction.
	 * 
	 * @param	interaction
	 * 				The desired type of interaction to check, as an element of the TerrainInteraction enumeration.
	 * @param	orientation
	 * 				The desired direction in which the interaction should be checked.
	 * @pre		The game object must have a proper world.
	 * 			| hasProperWorld()
	 * @return	In case the interaction is COLLIDE, return true if and only if this object collides with any
	 * 			other impassable Game object.
	 * 			In case the interaction is OVERLAP, return true if and only if this object overlaps with any
	 * 			other impassable Game object.
	 * 			In case the interaction is STAND_ON, return true if and only if this object stands on an
	 * 			impassable Game object.
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
	
	/************************************************** OVERLAP PROCESSING ********************************************/
	
	/**
	 * Process an overlap of the Game object in general.
	 * 
	 * @effect	Process overlap with tiles.
	 * 			| processTileOverlap()
	 * @effect	Process overlap with other Game objects.
	 * 			| processGameObjectOverlap()
	 */
	protected void processOverlap(){
		this.processTerrainOverlap();
		this.processGameObjectOverlap();
	}

	/**
	 * Process an overlap of the Game object with Terrain.
	 * 
	 * @effect	If the Game object is overlapping with Terrain that should damage him, invoke
	 * 			doTerrainDamage.
	 * 			| for overlappingTerrain in this.getOverlappingTerrainTypes():
	 * 			|	if ( this.hasTerrainPropertiesOf(overlappingTerrain) )
	 * 			|		then this.doTerrainDamage( overlappingTerrain )
	 */
	protected void processTerrainOverlap(){
		Set<Terrain> overlappingTerrainTypes = this.getOverlappingTerrainTypes();
		
		for(Terrain overlappingTerrain : overlappingTerrainTypes){
			if(this.hasTerrainPropertiesOf(overlappingTerrain)){
				this.doTerrainDamage(overlappingTerrain);
			}
		}
	}	
	
	/**
	 * Make the Game object take damage according to the specifications of the given overlapping Terrain.
	 * 
	 * @param 	overlappingTerrain
	 * 				The Terrain with which the Game object overlaps.
	 * @effect	If the given overlapping Terrain does damage to the Game object, the time since the last damage
	 * 			he took from the terrain is greater than the damage time and either the Terrain deals instant
	 * 			damage or the terrain overlap duration is greater than the damage time, make the Game object
	 * 			take damage and set the time since the last terrain damage to 0.
	 * 			| if ( this.getTerrainPropertiesOf(overlappingTerrain).getDamage() != 0 		&&
	 * 			|	   this.getTimer().getSinceLastTerrainDamage(overlappingTerrain) >
	 * 			|	   this.getTerrainPropertiesOf(overlappingTerrain).getDamageTime() 			&&
	 * 			|	   ( this.getTerrainPropertiesOf(overlappingTerrain).isInstantDamage() 		||
	 * 			|		 this.getTimer().getTerrainOverlapDuration(overlappingTerrain) > 
	 * 			| 		 this.getTerrainPropertiesOf(overlappingTerrain).getDamageTime() ) )
	 * 			|	then this.takeDamage( this.getTerrainPropertiesOf(overlappingTerrain).getDamage() )
	 * 			|		 this.getTimer().setSinceLastTerrainDamage(overlappingTerrain, 0.0)
	 */
	protected void doTerrainDamage(Terrain overlappingTerrain){
		// Get configuration for this overlapping terrain type
		TerrainProperties terrainProperties = this.getTerrainPropertiesOf(overlappingTerrain);
		
		// if the gameobject can lose hit points due to contact with this terrain type
		if(  terrainProperties.getDamage() != 0 ){ 
			// If the time since the last hitpoints detection is greater than the configured damage time
			if( this.getTimer().getSinceLastTerrainDamage(overlappingTerrain) >= terrainProperties.getDamageTime() ){ 
				// Do damage if the terrain should do damage immediately after contact (for example, magma) 
				// or if the overlapping duration is longer than the configured damage time
				if(terrainProperties.isInstantDamage() || 
				   this.getTimer().getTerrainOverlapDuration(overlappingTerrain) >= terrainProperties.getDamageTime() ){
					
					this.takeDamage( terrainProperties.getDamage() );
					this.getTimer().setSinceLastTerrainDamage(overlappingTerrain, 0.0);
					
				}
			}
		}
	}
	
	/**
	 * Process the overlap of another Game object with this Game object.
	 * 
	 * @effect	Process overlaps of Mazubs with this Game object.
	 *  TODO aanpassen
	 * 			| for mazub in this.getWorld().getAllMazubs():
	 * 			|	if ( this.doesOverlapWith(mazub) ) 
	 * 			|		then this.processMazubOverlap(mazub)
	 * @effect	Process overlaps of Plants with this Game object.
	 * 			| for plant in this.getWorld().getAllPlants():
	 * 			|	if ( this.doesOverlapWith(plant) ) 
	 * 			|		then this.processPlantOverlap(plant)
	 * @effect	Process overlaps of Sharks with this Game object.
	 * 			| for shark in this.getWorld().getAllSharks():
	 * 			|	if ( this.doesOverlapWith(shark) ) 
	 * 			|		then this.processSharkOverlap(shark)
	 * @effect	Process overlaps of Slimes with this Game object.
	 * 			| for slime in this.getWorld().getAllSlimes():
	 * 			|	if ( this.doesOverlapWith(slime) ) 
	 * 			|		then this.processSlimeOverlap(slime) 	
	 */
	protected void processGameObjectOverlap(){
		World world = this.getWorld();
		

		if( Mazub.getInWorld(world) != this && this.doesOverlapWith(Mazub.getInWorld(world))){
			this.processMazubOverlap(Mazub.getInWorld(world));
		}
		
		for(Buzam buzam : Buzam.getAllInWorld(world)){
			if(buzam != this && this.doesOverlapWith(buzam)){
				this.processMazubOverlap(buzam);
			}
		}
		
		for(Plant plant :  Plant.getAllInWorld(world)){
			if(plant != this && this.doesOverlapWith(plant)){
				this.processPlantOverlap(plant);
			}
		}
		
		for(Shark shark :  Shark.getAllInWorld(world)){
			if(shark != this && this.doesOverlapWith(shark)){
				processSharkOverlap(shark);
			}
		}
		
		for(Slime slime :  Slime.getAllInWorld(world)){
			if(slime != this && this.doesOverlapWith(slime)){
				processSlimeOverlap(slime);
			}
		}
		
	}
	
	/**
	 * Process an overlap of the Game object with a given Mazub. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void processMazubOverlap(Mazub alien);
	
	/**
	 * Process an overlap of the Game object with a given Plant. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void processPlantOverlap(Plant plant);
	
	/**
	 * Process an overlap of the Game object with a given Shark. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void processSharkOverlap(Shark shark);
	
	/**
	 * Process an overlap of the Game object with a given Slime. As this is an abstract method, this must
	 * be implemented in the subclasses of this class, according to their specifications.
	 */
	protected abstract void processSlimeOverlap(Slime slime);	

	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Kill this Game object.
	 * 
	 * @effect	Set the number of hit points of this Game object to 0.
	 * 			| setNbHitPoints(0)
	 */
	void kill(){
		this.setNbHitPoints(0);
	}
	
	/**
	 * Check whether or not a Game object is killed.
	 * 
	 * @return	True if and only if the current number of hit points of the Game object is equal to 0.
	 * 			| result == ( this.getNbHitPoints() == 0 )
	 */
	public boolean isKilled(){
		return this.getNbHitPoints() == 0;
	}
	
	/**
	 * Terminate this Game object.
	 * 
	 * @effect	Break the relation between the Game object and his current World.
	 * 			| unsetWorld()
	 * @post	The terminated status of the Game object is equal to true.
	 * 			| new.isTerminated == true
	 */
	protected void terminate(){
		this.unsetWorld();
		this.terminated = true;
	}
	
	/**
	 * Check if a Game object is terminated.
	 * 
	 * @return	| result == ( this.terminated )
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * Variable registering the terminated status of a Game object.
	 */
	protected boolean terminated = false;
	
	
	
	/**
	 * Returns a string representation of the object
	 */
	@Override
	public String toString(){
		return getClassName()+" hp: " + getNbHitPoints() + " ";
	}
	
	public abstract String getClassName();
	
}
