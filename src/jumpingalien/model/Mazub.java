package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.MazubAnimation;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.model.helper.TerrainProperties;

/**
 * A class of Mazubs, characters for a 2D platform game with several properties. This class has been worked out
 * for a project of the course Object Oriented Programming at KULeuven.
 *
 *
 * @author 	Thomas Verelst	:	r0457538, Ingenieurswetenschappen: Elektrotechniek - Computerwetenschappen
 * 			Hans Cauwenbergh:	r0449585, Ingenieurswetenschappen: Computerwetenschappen - Elektrotechniek
 * 	
 * @note	The test file of this class is located in tests/jumpingaline.part2.tests/TestCase.java
 * 
 * @note	The source of this project is hosted in a private GIT repository on Bitbucket.
 * 			The repository is only available to invited users. In case access to this 
 * 			repository is needed, please contact thomas.verelst1@student.kuleuven.be
 * 
 * 			The link (which is not accessible for unauthorized users) of the repository is:
 * 				https://bitbucket.org/thmz/oop-project/
 *
 * @invar	The width of the character must be valid.
 * 			|	isValidWidth( this.getWidth() )
 * @invar	The height of the character must be valid.
 * 			|	isValidHeight( this.getHeight() )
 * @invar	The horizontal velocity must be valid.
 * 			|	isValidVelocityX( this.getVelocityX() )
 * @invar	The maximal horizontal velocity must be greater than the initial horizontal velocity.
 * 			|	canHaveAsVelocityXMax( this.getVelocityXMax() )
 * @invar	The timer object linked to a Mazub instance is not null.
 * 			| 	this.getTimer() != null
 * @invar	The animation object linked to a Mazub instance is not null.
 * 			| 	this.getAnimation() != null
 * @invar	The current orientation of Mazub is not null.
 * 			|	this.getOrientation() != null
 * @invar	The current orientation is valid.
 * 			|	isValidOrientation( this.getOrientation() )
 * @invar	The current number of Mazub's hit points is valid.
 * 			|	isValidNbHitPoints( this.getNbHitPoints() )
 *
 * @version 2.0
 */
public class Mazub extends GameObject{
		
	/******************************************************* GENERAL ***************************************************/	
	
	/**
	 * Constant reflecting the amount of hit points a Mazub gets when he overlaps with a Plant.
	 * 
	 * @return	The amount of hit points a Mazub gets when he overlaps with a Plant is equal to 50.
	 * 			| result == 50
	 */
	private static final int PLANT_HP_INCREASE = 50;

	/**
	 * Constant reflecting the amount of damage a Mazub should take when he overlaps with a Shark.
	 * 
	 * @return	The amount of damage a Mazub should take when he overlaps with a Shark is equal to 50.
	 * 			| result == 50
	 */
	private static final int SHARK_DAMAGE = 50;
	
	/**
	 * Constant reflecting the amount of damage a Mazub should take when he overlaps with a Slime.
	 * 
	 * @return	The amount of damage a Mazub should take when he overlaps with a Slime is equal to 50.
	 * 			| result == 50
	 */
	private static final int SLIME_DAMAGE = 50;


	/**
	 * Constant reflecting the maximal horizontal velocity for a Mazub when ducking.
	 * 
	 * @return	The maximal horizontal velocity of a Mazub when ducking is equal to 1.0 m/s.
	 * 			| result == 1.0
	 */
	public static final double VELOCITY_X_MAX_DUCKING = 1.0;
	
	
	/**
	 * Constant reflecting the maximal horizontal velocity for a Mazub when running.
	 * 
	 * @return	The maximal horizontal velocity of a Mazub when running.
	 */
	private static double VELOCITY_X_MAX_RUNNING;
		
	/***************************************************** CONSTRUCTOR *************************************************/

	/**
	 * Constructor for the class Mazub.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Mazub's bottom left pixel.
	 * @param	velocityXInit
	 * 				The initial horizontal velocity of Mazub.
	 * @param	velocityXMaxRunning
	 * 				The maximal horizontal velocity of Mazub when he's running.
	 * @param 	sprites
	 * 				The array of sprite images for Mazub.
	 * @param	nbHitPoints
	 * 				The number of Mazub's hit points.
	 * @effect	The Mazub is initiated with the constructor of his superclass GameObject.
	 * 			| super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, 
	 * 					sprites,nbHitPoints, maxNbHitPoints) 
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @post	The constant VELOCITY_X_MAX_RUNNING is equal to velocityXMaxRunning.
	 *  		| VELOCITY_X_MAX_RUNNING == velocityXMaxRunning
	 * @post	The initial ducking status of Mazub is set to false.
	 * 			| setDucking(false)
	 * @post	The animation is initiated.
	 * 			| new.getAnimation() != null
	 * @effect 	The terrain is configured for a Mazub.
	 * 			| configureTerrain()
	 * @throws	IllegalPositionXException
	 * 				The X position of Mazub is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Y position of Mazub is not a valid Y position.
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
	public Mazub(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
				 double velocityXMaxRunning, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
				 int maxNbHitPoints)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMaxRunning, accelerationXInit, sprites,
			  nbHitPoints, maxNbHitPoints);
		
		assert sprites.length >= 10 && sprites.length % 2 == 0;
		
		VELOCITY_X_MAX_RUNNING = velocityXMaxRunning;
		
		this.setDucking(false);
		
		this.setAnimation(new MazubAnimation(this, sprites));
		
		this.configureTerrain();
			
	}
	
	/**
	 * Initialize a Mazub with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, number of hit points and maximal number of hit points.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Mazub's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Mazub.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @effect  Construct Mazub with velocityXInit = 1.0, velocityYInit = 8.0, velocityXMaxRunning  = 3.0,
	 * 			accelerationXInit = 0.9, nbHitPoints = 100 and maxNbHitPoints = 500.
	 * 			| this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 100, 500)
	 * @throws	IllegalPositionXException
	 * 				The X position of Mazub is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Y position of Mazub is not a valid Y position.
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
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 100, 500);
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
	protected void setAnimation(MazubAnimation animation) {
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
	public MazubAnimation getAnimation() {
		return this.animation;
	}
	
	/**
	 * Variable registering the animation of this Mazub.
	 */
	private MazubAnimation animation;
	
	/**
	 * Return the correct sprite of Mazub, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of Mazub.
	 * @note	No formal documentation was required for this method.
	 */
	@Override
	public Sprite getCurrentSprite() {
		return this.getAnimation().getCurrentSprite();	
	}	
	
	/******************************************************** TIMER ****************************************************/
	
	/**
	 * Update the timers of a Mazub and change his animation accordingly.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	The method updateTimers of Mazub's superclass GameObject is invoked.
	 * 			| super.updateTimers(dt)
	 * @effect	The animation of Mazub is updated accordingly to his timers.
	 * 			| getAnimation().updateAnimationIndex(this.getTimer())
	 */
	@Override
	protected void updateTimers(double dt){
		super.updateTimers(dt);
		
		this.getAnimation().updateAnimationIndex(this.getTimer());
	}
	
	/******************************************************* TERRAIN ***************************************************/

	/**
	 * Configure the terrain properties for a Mazub.
	 * 
	 * @effect	Configure the terrain properties of an air tile for a Mazub.
	 * 			| setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false))
	 * @effect	Configure the terrain properties of a solid tile for a Mazub.
	 * 			| setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false))
	 * @effect	Configure the terrain properties of a water tile for a Mazub.
	 * 			| setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false))
	 * @effect	Configure the terrain properties of a magma tile for a Mazub.
	 * 			| setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true))
	 */
	@Override
	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true));
		
	}
	
	/******************************************************* RUNNING ***************************************************/
	
	// TODO: commentary
	
	@Override
	public void startMove(Orientation orientation){
		assert isValidOrientation(orientation);
		
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * this.getVelocityXInit() );
		this.setAccelerationX( orientation.getSign() * accelerationXInit);
		
		if(orientation == Orientation.LEFT){
			this.setMoveLeft(true);
		}else{
			this.setMoveRight(true);
		}
	}
	
	@Override
	public void endMove(Orientation orientation){
		assert isValidOrientation(orientation);
		
		if(orientation == Orientation.LEFT){
			this.setMoveLeft(false);
			this.setShouldMoveLeft(false);
		}else{
			this.setMoveRight(false);
			this.setShouldMoveRight(false);
		}
		
		if(!this.isMoveLeft() && !this.isMoveRight()){
			this.setVelocityX(0);
			this.setAccelerationX(0);
			this.getTimer().setSinceLastMove(0);
		}
		
		if(this.isMoveLeft() && getOrientation() != Orientation.LEFT){
			startMove(Orientation.LEFT);
		}else
		if(this.isMoveRight() && getOrientation() != Orientation.RIGHT){
			startMove(Orientation.RIGHT);
		}
	}
	
	
	public boolean isMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}
	
	boolean moveLeft = false;

	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	boolean moveRight = false;
	
	
	/* Prolonged horizontal movement */
	
	/**
	 * Checks whether Mazub has moved in the last second.
	 * 
	 * @return	True if and only if the time since the last move by Mazub was made is less or equal to 1.
	 * 			(up to a certain epsilon)
	 * 			| result == ( this.getTime().getSinceLastMove() <= 1.0 )
	 */
	@Raw
	public boolean hasMovedInLastSecond(){
		return Util.fuzzyLessThanOrEqualTo(this.getTimer().getSinceLastMove(), 1.0);
	}
	
	public boolean shouldMoveRight(){
		return this.shouldMoveRight;
	}
	
	public boolean shouldMoveLeft(){
		return this.shouldMoveLeft;
	}
	
	private void setShouldMoveRight(boolean shouldMoveRight){
		this.shouldMoveRight = shouldMoveRight;
	}
	
	private void setShouldMoveLeft(boolean shouldMoveLeft){
		this.shouldMoveLeft = shouldMoveLeft;
	}
	
	private boolean shouldMoveRight;
	private boolean shouldMoveLeft;
	
	/******************************************************* DUCKING ***************************************************/
	
	/**
	 * Make Mazub start ducking. Set the maximal horizontal velocity for ducking.
	 * 
	 * @effect	The maximal horizontal velocity of Mazub is set to VELOCITY_X_MAX_DUCKING.
	 * 			| setVelocityXMax(VELOCITY_X_MAX_DUCKING)
	 * @effect	The ducking status of Mazub is set to true.
	 * 			| setDucking(true)
	 */
	public void startDuck(){	
		this.setVelocityXMax(VELOCITY_X_MAX_DUCKING);
		this.setDucking(true);
	}
	
	/**
	 * Make Mazub end ducking.
	 * 
	 * @effect	The maximal horizontal velocity of Mazub is set to VELOCITY_X_MAX_RUNNING.
	 * 			| setVelocityXMax(VELOCITY_X_MAX_RUNNING)
	 * @effect	If Mazub is moving, set his horizontal acceleration back to the default value.
	 * 			| if ( this.isMoving() )
	 * 			|	then this.setAccelerationX(this.getOrientation().getSign() * this.getAccelerationXInit())
	 * @effect	Mazub should not end his duck anymore after this method is invoked.
	 * 			| setShouldEndDucking(false)
	 * @effect	The ducking status of Mazub is set to false.
	 * 			| setDucking(false)
	 * TODO: commentary
	 * @throws	IllegalStateException
	 * 				Mazub is not ducking.
	 * 				| !this.isDucking()
	 */
	public void endDuck() throws IllegalStateException{
		
		if(!this.isDucking())
			throw new IllegalStateException("Mazub not ducking!");		
		
		this.setVelocityXMax(VELOCITY_X_MAX_RUNNING);
		
		if(this.isMoving()){
			this.setAccelerationX(this.getOrientation().getSign() * this.getAccelerationXInit());
		}
		
		this.setShouldEndDucking(false);
		this.setDucking(false);
		
		Sprite oldSprite = this.getCurrentSprite();
		this.getAnimation().updateSpriteIndex();
		
		if(oldSprite == this.getCurrentSprite()){ // if sprite didn't change, a collision prevented the change
			this.setDucking(true);
			this.setShouldEndDucking(true);
		}
		
	}	
	
	/**
	 * Checks whether Mazub is ducking.
	 * 
	 * @return	A boolean that represents if Mazub is ducking or not.
	 * 			| result == ( this.ducking )
	 */
	@Basic
	public boolean isDucking(){
		return this.ducking;
	}
	
	/**
	 * Set Mazub's status to ducking.
	 * 
	 * @param 	ducking
	 * 				A boolean that represents if Mazub's status should be changed to ducking or not.
	 * @post	The ducking status of Mazub is equal to the given boolean value of ducking.
	 * 			| new.isDucking() == ducking
	 */
	@Basic
	public void setDucking(boolean ducking){
		this.ducking = ducking;
	}
	
	/**
	 * Variable registering the ducking status of this Mazub.
	 */
	private boolean ducking;
	
	
	/**
	 * Return whether or not Mazub should end ducking.
	 * 
	 * @return	A boolean that represents whether or not Mazub should end ducking.
	 * 			| result == ( this.shouldEndDucking )
	 */
	@Basic
	public boolean shouldEndDucking(){
		return this.shouldEndDucking;
	}
	
	/**
	 * Set whether or not Mazub should end ducking.
	 * 
	 * @param 	shouldEndDucking
	 * 				A boolean that represents whether or not Mazub should end ducking.
	 */
	@Basic
	public void setShouldEndDucking(boolean shouldEndDucking){
		this.shouldEndDucking = shouldEndDucking;
	}
	
	/**
	 * Variable registering whether or not Mazub should end ducking.
	 */
	private boolean shouldEndDucking;

	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Update the Mazub's horizontal and vertical position and velocity for the given time interval along
	 * with adjusting his sprites according to his current status. Consider prolonged movement of a 
	 * Mazub and adjust his status.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	The horizontal position of Mazub is equal to the result of the formula used in the method
	 * 			updatePositionX.
	 * 			| updatePositionX(dt)
	 * @effect	The horizontal velocity of Mazub is equal to the result of the formula used in the method 
	 * 			updateVelocityX.
	 * 			| updateVelocityX(dt)
	 * @effect	The vertical position of Mazub is equal to the result of the formula used in the method
	 * 			updatePositionY.
	 * 			| updatePositionY(dt)
	 * @effect	The vertical velocity of Mazub is equal to the result of the formula used in the method	
	 * 			updateVelocityY.
	 * 			| updateVelocityY(dt)
	 * @effect	If Mazub should end ducking, make Mazub end ducking.
	 * 			| if ( this.shouldEndDucking() )
	 * 			|	then this.endDuck()
	 * TODO: commentary
	 * @effect	Update Mazub's sprite according to his current status.
	 * 			| getAnimation().updateSpriteIndex()
	 */

	@Override
	public void doMove(double dt) {	

		/* Horizontal */
		this.updatePositionX(dt);
		this.updateVelocityX(dt);
		
		/* Prolonged horizontal movement */
		if(this.shouldMoveRight() && !this.doesOverlap(Orientation.RIGHT)){
			this.startMove(Orientation.RIGHT);
			this.setShouldMoveRight(false);
		} else if (this.shouldMoveLeft() && !this.doesOverlap(Orientation.LEFT)) {
			this.startMove(Orientation.LEFT);
			this.setShouldMoveLeft(false);
		}
		
		/* Vertical */		
		this.updatePositionY(dt);		
		this.updateVelocityY(dt);		
		
		/* Prolonged ducking */
		if(this.shouldEndDucking()){
			this.endDuck();
		}
			
		/* Update sprite */
		this.getAnimation().updateSpriteIndex();
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Process the horizontal collision of a Mazub.
	 * 
	 * TODO: commentary
	 */
	@Override
	protected void processHorizontalCollision(){
		this.endMove(this.getOrientation());
		
		if (this.getOrientation() == Orientation.RIGHT){
			this.setShouldMoveRight(true);
		} else {
			this.setShouldMoveLeft(true);
		}
			
	}
	
	/**
	 * Process the vertical collision of a Mazub.
	 * 
	 * @effect	If Mazub is moving in a positive y direction, make him end his jump.
	 * 			| if ( this.getVelocityY() > 0)
	 * 			|	then this.endJump()
	 * @effect	If Mazub is moving in a negative y direction, make him stop falling.
	 * 			| if ( this.getVelocityY() <= 0)
	 * 			|	then this.stopFall()
	 */
	@Override
	protected void processVerticalCollision() {
		if(this.getVelocityY() > 0){ // is going up
			this.endJump();
		} else { // is going down
			this.stopFall();
		}
	}
	
	/**
	 * Return all impassable Game objects for a Mazub.
	 * 
	 * @return	A Hashset that contains all Mazubs, Slimes, Sharks and Plants in the Mazub's world.
	 */
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(this.getWorld().getAllMazubs());
		allImpassableGameObjects.addAll(this.getWorld().getAllSlimes());
		allImpassableGameObjects.addAll(this.getWorld().getAllSharks());
		allImpassableGameObjects.addAll(this.getWorld().getAllPlants());
		return allImpassableGameObjects;
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Process an overlap of this Mazub with another Mazub.
	 * 
	 * @param	mazub
	 * 				The other Mazub with whom this Mazub overlaps.
	 */
	@Override
	protected void processMazubOverlap(Mazub alien){
		
	}
	
	/**
	 * Process an overlap of a Mazub with a Shark.
	 * 
	 * @param	shark
	 * 				The Shark with which this Mazub overlaps.
	 * @effect	TODO: final implementation
	 */
	@Override
	protected void processSharkOverlap(Shark shark){
		if(!shark.isKilled()){
			if(!this.doesOverlapWith(shark, Orientation.BOTTOM)){
				this.takeDamage(SHARK_DAMAGE);
				this.setImmune(true);
				this.getTimer().setSinceEnemyCollision(0);
			}	
		}
	}
	
	/**
	 * Process an overlap of a Mazub with a Slime.
	 * 
	 * @param	slime
	 * 				The Slime with which this Mazub overlaps.
	 * @effect	TODO: final implementation
	 */
	@Override
	protected void processSlimeOverlap(Slime slime){
		if(!slime.isKilled()){
			if(!this.doesOverlapWith(slime, Orientation.BOTTOM)){
				this.takeDamage(SLIME_DAMAGE);
				this.setImmune(true);
				this.getTimer().setSinceEnemyCollision(0);
			}			
		}
	}
	
	/**
	 * Process an overlap of a Mazub with a Plant.
	 * 
	 * @param	plant
	 * 				The Plant with which this Mazub overlaps.
	 * @effect	TODO: final implementation
	 */
	@Override
	protected void processPlantOverlap(Plant plant){
		System.out.println("plant overlap");
		if(!plant.isKilled() && !this.isFullHitPoints()){
			this.modifyNbHitPoints(PLANT_HP_INCREASE);
			plant.kill(); // Mss is het eigenlijk niet goed dat een Mazub zo maar andere objecten kan killen. Mss in .kill() een extra check doen of ze overlappen ofzo?
		}
	}
	
	/**
	 * Check whether or not Mazub is on the target tile of his world.
	 * 
	 * @return	True if and only if the target tile of his world is in the array of tiles from
	 * 			getTilePositionsIn with his parameters.
	 * 			| result == ( for some tile in this.getWorld().getTilePositionsIn(this.getRoundedPositionX(),
	 *			|				   			   this.getRoundedPositionY(),
	 *			|				           	   this.getRoundedPositionX() + this.getWidth(),
	 *			|				  			   this.getRoundedPositionY() + this.getHeight() ):
	 *			| 			  	tile[0] == this.getWorld().getTargetTileX() &&
	 *			|				tile[1] == this.getWorld().getTargetTileY() 					)
	 */
	public boolean isOnTargetTile(){
		
		for(int[] tile: this.getWorld().getTilePositionsIn(this.getRoundedPositionX(),
				this.getRoundedPositionY(),
				this.getRoundedPositionX() + this.getWidth(),
				this.getRoundedPositionY() + this.getHeight())){
	
			if((tile[0] == this.getWorld().getTargetTileX()) && (tile[1] == this.getWorld().getTargetTileY()))
				return true;

		}
		
		return false;
		
	}
	
}
