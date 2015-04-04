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
import jumpingalien.model.helper.Animation;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Timer;

// All aspects shall be specified both formally and informally.

/**
 * A class of Mazubs, characters for a 2D platform game with several properwwssqz					 ties. This class has been worked out
 * for a project of the course Object Oriented Programming at KULeuven.
 *
 *
 * @author 	Thomas Verelst	:	r0457538, Ingenieurswetenschappen: Elektrotechniek - Computerwetenschappen
 * 			Hans Cauwenbergh:	r0449585, Ingenieurswetenschappen: Computerwetenschappen - Elektrotechniek
 * 	
 * @note	The test file of this class is located in tests/jumpingaline.part1.tests/TestCase.java
 * 
 * @note	The source of this project is hosted in a private GIT repository on Bitbucket.
 * 			The repository is only available to invited users. In case access to this 
 * 			repository is needed, please contact thomas.verelst1@student.kuleuven.be
 * 
 * 			The link (which is not accessible for unauthorized users) of the repository is:
 * 				https://bitbucket.org/thmz/oop-project/
 *
 * 
 * @invar	The x position must be valid.
 * 			|	isValidPositionX( this.getPositionX() )
 * @invar	The y position must be valid.
 * 			|	isValidPositionY( this.getPositionY() )
 * @invar	The rounded x position must be valid.
 * 			|	isValidRoundedPositionX( this.getRoundedPositionX() )
 * @invar	The rounded y position must be valid.
 * 			|	isValidRoundedPositionY( this.getRoundedPositionY() )
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
 * @invar	The current number of Mazub's hit-points is valid.
 * 			|	isValidNbHitPoints( this.getNbHitPoints() )
 * 
 * @version 2.0
 */
public class Mazub extends GameObject{
		
	/************************************************** GENERAL ***********************************************/	
	
	/**
	 * Constant reflecting the maximal horizontal velocity for Mazubs when ducking.
	 * 
	 * @return	The maximal horizontal velocity of Mazubs when ducking is equal to 1.0 m/s.
	 * 			| result == 1.0
	 */
	public static final double VELOCITY_X_MAX_DUCKING = 1.0;
	
	
	/**
	 * Constant reflecting the maximal horizontal velocity for Mazubs when running.
	 * 
	 * @return	The maximal horizontal velocity of Mazubs when running.
	 */
	private static double VELOCITY_X_MAX_RUNNING;
		
	/************************************************ CONSTRUCTOR *********************************************/

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
	 * @param	hitPoints
	 * 				The number of Mazub's hit-points.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0) 
	 * @post	The initial ducking status of Mazub is equal to false.
	 * 			| new.isDucking() == false
	 * @post	The X position of Mazub is equal to pixelLeftX.
	 * 			| new.getPositionX() == pixelLeftX
	 * @post	The Y position of Mazub is equal to pixelBottomY.
	 * 			| new.getPositionY() == pixelBottomY
	 * @post	The value of the initial horizontal velocity is equal to velocityXInit.
	 * 			| new.getVelocityXInit() == velocityXInit
	 * @effect	If VELOCITY_X_MAX_RUNNING is greater than the initial horizontal velocity, the initial maximal
	 *  		horizontal velocity is equal to VELOCITY_X_MAX_RUNNING. Otherwise, it's equal to the initial 
	 *  		horizontal velocity.
	 *  		| setVelocityXMax(VELOCITY_X_MAX_RUNNING)
	 * @effect	The initial orientation of Mazub is equal to right.
	 * 			| setOrientation(Orientation.RIGHT)
	 * @post	The animation is initiated.
	 * 			| new.getAnimation() != null
	 * @post	The timer is initiated.
	 * 			| new.getTimer() != null
	 * @effect	If nbHitPoints is smaller or equal to MAX_NB_HITPOINTS, the number of Mazub's hit-points is equal
	 * 		 	to nbHitPoints. Otherwise, it's equal to MAX_NB_HITPOINTS.
	 * 			| setNbHitPoints(nbHitPoints)
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
	public Mazub(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityXMaxRunning,
				 Sprite[] sprites, int nbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit, 8.0, velocityXMaxRunning, 0.9, sprites, nbHitPoints,500);
		
		VELOCITY_X_MAX_RUNNING = velocityXMaxRunning;
		
		this.setDucking(false);
		
		// Separate timer and animation for all game object types?
		this.setTimer( new Timer() );
		this.setAnimation( new Animation(sprites) );
		
	}
	
	/**
	 * Initialize Mazub with default velocityXInit and velocityXMaxRunning.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Mazub's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Mazub.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @effect  Construct Mazub with velocityXInit = 1.0 and velocityXMaxRunning  = 3.0
	 * 			| this(pixelLeftX, pixelBottomY, 1.0, 3.0, sprites)
	 * @throws	IllegalPositionXException
	 * 				The X position of Mazub is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Y position of Mazub is not a valid Y position.
	 * 				| ! isValidPositionY(positionY)
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws IllegalPositionXException,
				IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 1.0, 3.0, sprites, 100);
	}
	
	/************************************************* HELPER CLASSES *****************************************/
	
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
	
	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	// X position 
	
	
	
	// Y position
	
	
	
	// Width
	
	
	
	// Height
	
	
	
	/************************************************ RUNNING *************************************************/
	
	// * move through tiles of passable terrain
	// * if there are multiple ongoing movements at the same time, the horizontal velocity shall not be set
	//	 to zero before all ongoing movements are terminated
	
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
	
	/********************************************* JUMPING AND FALLING ****************************************/
	
	// * once startJump has been invoked while Mazub is located on top of solid ground or another game object,
	//	 Mazub starts moving up with a velocity of 8 [m/s] -> NO MORE INFINITE JUMPING
	// * if Mazub overlaps with impassable terrain or other game objects while jumping, endJump will set the
	//	 vertical velocity to zero immediately
	// * when Mazub's bottom perimeter does not overlap with pixels belonging to impassable tiles or game 
	//	 objects, Mazub shall fall until Mazub's bottom perimeter reaches the top-most row of pixels of an
	//	 impassable tile, game object or leaves the map
	// * as Mazub stands on impassable terrain or another game object, the vertical acceleration shall be
	//	 set to zero
	
	
	
	/*************************************************** DUCKING **********************************************/
	
	// * if endDuck is invoked in a location where the appropriate non-ducking Yp would result in Mazub
	//	 overlappping with impassable terrain, Mazub shall continue to duck until appropriate space is 
	//	 available. Thus, startDuck may be invoked while Mazub is still trying to stand up from previously
	//	 ducking. -> wrong defensive implementation at the moment?
	
	/**
	 * Make Mazub start ducking. Set the maximal horizontal velocity for ducking.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VELOCITY_X_MAX_DUCKING.
	 * 			| new.getVelocityXMax() == VELOCITY_X_MAX_DUCKING
	 * @post	The ducking status of Mazub is true.
	 * 			| new.isDucking() == true
	 * @throws	IllegalStateException
	 * 				Mazub is already ducking.
	 * 				| this.isDucking()
	 */
	public void startDuck(){
		if( this.isDucking())
			throw new IllegalStateException("Mazub already ducking!");
		
		this.setVelocityXMax(VELOCITY_X_MAX_DUCKING);
		this.setDucking(true);
	}
	
	/**
	 * Make Mazub end ducking. Reset the maximal horizontal velocity.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VELOCITY_X_MAX_RUNNING.
	 * 			| new.getVelocityXMax() == VELOCITY_X_MAX_RUNNING
	 * @post	The ducking status of Mazub is false.
	 * 			| new.isDucking() == false
	 * @post	If Mazub is moving, set the acceleration to the default value
	 * 			| new.getAccelerationX() == this.getOrientation().getSign() * ACCELERATION_X
	 * @throws	IllegalStateException
	 * 				Mazub is not ducking.
	 * 				| !this.isDucking()
	 */
	public void endDuck() throws IllegalStateException{
		// Check
		if(this.doesCollide())
			throw new IllegalStateException("Colission before invocation");
		
		if(!this.isDucking())
			throw new IllegalStateException("Mazub not ducking!");		
		
		this.setVelocityXMax(VELOCITY_X_MAX_RUNNING);		
		if(this.isMoving()){
			this.setAccelerationX(this.getOrientation().getSign() * this.getAccelerationXInit());
		}
		
		this.setShouldEndDucking(false);
		this.setDucking(false);
		
		if(this.doesCollide()){
			this.setDucking(true);
			this.setShouldEndDucking(true);
		}
		
	}	
	
	/**
	 * Checks whether Mazub is ducking.
	 * 
	 * @return	A boolean that represents if Mazub is ducking or not.
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
	
	
	@Basic
	public boolean shouldEndDucking(){
		return this.shouldEndDucking;
	}
	
	@Basic
	public void setShouldEndDucking(boolean shouldEndDucking){
		this.shouldEndDucking = shouldEndDucking;
	}
	
	private boolean shouldEndDucking;
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// * values for the initial velocity, the maximum horizontal velocity and the horizontal acceleration
	//	 may change with respect to interaction with other game objects, terrain and actions performed by 
	//	 Mazub
	
	// Position
	
	
	
	// Velocity

	
	
	// Initial velocity
	
	
	
	// Maximal velocity
	
	
	
	// Acceleration
	
	
	
	// Orientation
	
	
	
	/******************************************* CHARACTER SIZE AND ANIMATION *********************************/
	
	/**
	 * Return the correct sprite of Mazub, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of Mazub.
	 * @note	No formal documentation was required for this method.
	 */
	@Override
	public Sprite getCurrentSprite() {
		return this.getAnimation().getCurrentSprite(this);	
	}
	
	
	/************************************************ ADVANCE TIME ********************************************/
	
	// * Mazub shall not move if its side perimeter in the direction of movements overlaps with impassable
	//	 terrain or another game object
	
	/**
	 * Advance time and update Mazub's position and velocity accordingly.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	The horizontal position of Mazub is equal to the result of the formula used in the method
	 * 			updatePositionX.
	 * 			| updatePositionX(dt)
	 * @effect	The vertical position of Mazub is equal to the result of the formula used in the method
	 * 			updatePositionY.
	 * 			| updatePositionY(dt)
	 * @effect	The horizontal velocity of Mazub is equal to the result of the formula used in the method 
	 * 			updateVelocityX.
	 * 			| updateVelocityX(dt)
	 * @effect	The vertical velocity of Mazub is equal to the result of the formula used in the method	
	 * 			updateVelocityY.
	 * 			| updateVelocityY(dt)
	 * @post	If Mazub wasn't moving, the time since his last move is increased by dt.
	 * 			| if ( !this.isMoving() )
	 * 			|	then (new timer).getSinceLastMove() == this.getTimer().getSinceLastMove() + dt
	 * @post	The time since the last sprite of Mazub was activated is increased by dt.
	 * 			| (new timer).getSinceLastSprite() == this.getTimer().getSinceLastSprite() + dt
	 * @throws	IllegalArgumentException
	 * 				The given time dt is either negative or greater than 0.2s.
	 * 				| (dt > 0.2) || (dt < 0)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");
		if( !hasProperWorld())
			throw new IllegalStateException(" This Mazub is not in world!");
		if( this.doesCollide())
			throw new IllegalStateException(" Colission before movement! "); // May NOT happen
		
		
		double oldPositionX = this.getPositionX();
		double oldPositionY = this.getPositionY();
		
	
		// Update horizontal position
		this.updatePositionX(dt);
		
		// Update horizontal velocity
		this.updateVelocityX(dt);
		
		if( this.doesCollide() ){
			this.setPositionX(oldPositionX);
			this.endMove(this.getOrientation());
		}
				
		// Update vertical position
		this.updatePositionY(dt);		
		
		// Update vertical velocity
		this.updateVelocityY(dt);
		
		if( this.doesCollide() ) {
			this.setPositionY(oldPositionY);
			
			if(this.getVelocityY() > 0){
				this.endJump();
			}else{
				this.stopFall();
			}
			
		}else{
			
			// Ugly...
			this.setAccelerationY(-10);
		}
		
		// Ducking
		if(this.shouldEndDucking()){
			this.endDuck();
		}
		
//		Set<Orientation> obstacleOrientations = new HashSet<Orientation>();
//		obstacleOrientations = getWorld().collidesWith(this);
//		
		//Y axis
//		for(Orientation obstacleOrientation: obstacleOrientations){
//			// horizontal
//			if (this.orientation == obstacleOrientation){
//				this.setPositionX(oldPositionX);
//				//this.setVelocityX(0);
//			}
//			// vertical
//			if ((this.getVelocityY() > 0 && obstacleOrientation == Orientation.TOP) ||
//				(this.getVelocityY() < 0 && obstacleOrientation == Orientation.BOTTOM)){
//				this.setPositionY(oldPositionY);
//				this.setVelocityY(0);
//			}
//		}
		
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		// Sprites
		this.getTimer().increaseSinceLastSprite(dt);
		this.getAnimation().updateAnimationIndex(this.getTimer());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		return false;
	}
}
