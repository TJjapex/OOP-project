package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.helper.Animation;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Timer;

/**
 * A class of Mazubs, characters for a 2D platform game with several properties. This class has been worked out
 * for a project of the course Object Oriented Programming at KULeuven.
 * 
 * @note	The source of this project is hosted in a private GIT repository on Bitbucket.
 * 			The repository is only available to invited users. In case acces to this 
 * 			repository is needed, please contact thomas.verelst1@student.kuleuven.be
 * 
 * 			The link (which is not be accessible for unauthorized users) of the repository is:
 * 				https://bitbucket.org/thmz/oop-project/
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
 * @invar	The current orientation is valid
 * 			|	isValidOrientation( this.getOrientation() )
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Mazub {
		
	/************************************************** GENERAL ***********************************************/
	
	public static final int GAME_WIDTH = 1024; 					// The width of the game world
	public static final int GAME_HEIGHT = 768;					// The height of the game world
	public static final double ACCELERATION_Y = -10.0; 			// The vertical acceleration
	public static final double VELOCITY_Y_INIT = 8.0; 			// The initial vertical velocity when jumping
	public static final double VELOCITY_X_MAX_DUCKING = 1.0;	// The maximum horizontal velocity when ducked
	public static final double ACCELERATION_X = 0.9;	 		// The horizontal acceleration
	private static double VELOCITY_X_MAX_RUNNING;	 			// The maximum horizontal velocity when not ducked
		
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
	 *  		| setVelocityXMax(VEL0TY_X_MAX_RUNNING)
	 * @effect	The initial orientation of Mazub is equal to right.
	 * 			| setOrientation(Orientation.RIGHT)
	 * @post	The animation is initiated.
	 * 			| new.getAnimation() != null
	 * @post	The timer is initiated.
	 * 			| new.getTimer() != null
	 * @throws	IllegalPositionXException
	 * 				The X position of Mazub is not a valid X position.
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				The Y position of Mazub is not a valid Y position.
	 * 				| ! isValidPositionY(positionY)
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityXMaxRunning, Sprite[] sprites)
		throws IllegalPositionXException, IllegalPositionYException{
		assert sprites.length >= 10 && sprites.length % 2 == 0;
		
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);
		this.setDucking(false);
		
		this.velocityXInit = velocityXInit;
		VELOCITY_X_MAX_RUNNING = velocityXMaxRunning;
		this.setVelocityXMax(VELOCITY_X_MAX_RUNNING);
		
		this.setOrientation(Orientation.RIGHT);
		
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
				IllegalPositionYException{
		this(pixelLeftX, pixelBottomY, 1.0, 3.0, sprites);
	}
	
	/************************************************* HELPER CLASSES *****************************************/
	
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
	private void setTimer(Timer timer){
		assert timer != null;
		
		this.timer = timer;
	}
	
	/**
	 * Return the time properties of Mazub.
	 * 
	 * @return	A timer that keeps track of several times involving the behaviour of Mazub.
	 */
	@Basic @Raw
	public Timer getTimer(){
		return this.timer;
	}
	
	private Timer timer;
	
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
	private void setAnimation( Animation animation){
		assert animation != null;
		
		this.animation = animation;
	}
	
	/**
	 * Return the animation object for Mazub.
	 * 
	 * @return	An animation that consists of consecutive sprites.
	 */
	@Basic @Raw
	public Animation getAnimation(){
		return this.animation;
	}
	
	private Animation animation;
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	// X position 
	
	/**
	 * Return the rounded down x-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Raw
	public int getRoundedPositionX(){
		return (int) Math.floor(this.getPositionX());
	}
	
	/**
	 * Check whether the given X position is a valid X position.
	 * 
	 * @param 	positionX
	 * 				An integer that represents an x-coordinate.
	 * @return	True if and only if the given x-position positionX is between the boundaries of the game world, 
	 * 			which means that the x-coordinate must be greater than or equal to 0 and smaller than GAME_WIDTH.
	 * 			|  result == ( (positionX >= 0) && (positionX < GAME_WIDTH) )
	 */
	@Basic @Raw
	public static boolean isValidRoundedPositionX(int positionX){
		return positionX >= 0 && positionX < GAME_WIDTH;
	}
	
	// Y position
	
	/**
	 * Return the rounded down y-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Raw
	public int getRoundedPositionY(){
		return (int) Math.floor(this.getPositionY());
	}
	
	/**
	 * Check whether the given Y position is a valid Y position.
	 * 
	 * @param 	positionY
	 * 				An integer that represents a y-coordinate.
	 * @return	True if and only if the given y-position positionY is between the boundaries of the game world, 
	 * 			which means that the y-coordinate must be greater than or equal to 0 and smaller than
	 * 			GAME_HEIGHT.
	 * 			|  result == ( (positionY >= 0) && (positionY < GAME_HEIGHT) )
	 */
	public static boolean isValidRoundedPositionY(int positionY){
		return positionY >= 0 && positionY < GAME_HEIGHT;
	}
	
	// Width
	
	/**
	 * Return the width of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the width of Mazub's active sprite.
	 */
	public int getWidth(){
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
	public static boolean isValidWidth(int width){
		return width > 0 && width < GAME_WIDTH;
	}
	
	// Height
	
	/**
	 * Return the height of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the height of Mazub's active sprite.
	 */
	public int getHeight(){
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
	public static boolean isValidHeight(int height){
		return height > 0 && height < GAME_HEIGHT;
	}
	
	/************************************************ RUNNING *************************************************/
	
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
		assert (this.getOrientation() != null);
		
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * this.getVelocityXInit() );
		this.setAccelerationX( orientation.getSign() * ACCELERATION_X);
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
	public void endMove(Orientation orientation) {
		assert (this.getOrientation() != null);
		
		if(orientation == this.getOrientation()){
			this.setVelocityX(0);
			this.setAccelerationX(0);
			this.getTimer().setSinceLastMove(0);
		}
	}

	/**
	 * Checks whether Mazub is moving.
	 * 
	 * @return 	True if and only if the horizontal velocity is not equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getVelocityX() != 0 )
	 */
	@Raw
	public boolean isMoving(){
		return !Util.fuzzyEquals(this.getVelocityX(), 0);
	}
	
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
	
	/**
	 * Make Mazub start jumping. Set the vertical initial velocity and gravitational acceleration of Mazub.
	 * 
	 * @post	The vertical velocity of Mazub is equal to VELOCITY_Y_INIT.
	 * 			| new.getVelocityY() == VELOCITY_Y_INIT
	 * @post	The vertical acceleration of Mazub is equal to ACCELERATION_Y.
	 * 			| new.getAccelerationY() == ACCELERATION_Y
	 */
	public void startJump(){
		this.setVelocityY( VELOCITY_Y_INIT );
		this.setAccelerationY( ACCELERATION_Y ); 
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
	public void endJump() throws IllegalStateException{
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}else{
			throw new IllegalStateException("Mazub does not have a positive vertical velocity!");
		}
	}
	
	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	True if and only if the vertical position of Mazub is equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getPositionY() == 0 )
	 */
	public boolean isJumping(){
		return !Util.fuzzyEquals(this.getPositionY(), 0);
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
	private void stopFall() {
		this.setVelocityY( 0 );
		this.setAccelerationY( 0 );
	}
	
	/*************************************************** DUCKING **********************************************/
	
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
		if(!this.isDucking())
			throw new IllegalStateException("Mazub not ducking!");
		
		this.setVelocityXMax(VELOCITY_X_MAX_RUNNING);		
		if(this.isMoving()){
			this.setAccelerationX(this.getOrientation().getSign() * ACCELERATION_X);
		}
		
		this.setDucking(false);
		
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
	
	private boolean ducking;
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// Position
	
	/**
	 * Return the x-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic @Raw
	public double getPositionX(){
		return this.positionX;
	}
	
	/**
	 * Return the y-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic @Raw
	public double getPositionY(){
		return this.positionY;
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
	@Basic @Raw
	private void setPositionX(double positionX) throws IllegalPositionXException{
		if( !isValidPositionX(positionX)) 
			throw new IllegalPositionXException(positionX);
		this.positionX = positionX;
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
	@Basic @Raw
	private void setPositionY(double positionY) throws IllegalPositionYException{
		if( !isValidPositionY(positionY)) 
			throw new IllegalPositionYException(positionY);
		this.positionY = positionY;
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
	public static boolean isValidPositionX(double positionX) {
		return isValidRoundedPositionX((int) Math.floor(positionX));

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
	public static boolean isValidPositionY(double positionY) {
		return isValidRoundedPositionY((int) Math.floor(positionY));
	}
		
	private double positionX;
	private double positionY;
	
	// Velocity

	/**
	 * Return the horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the horizontal velocity of Mazub.
	 */
	@Basic @Raw
	public double getVelocityX(){
		return this.velocityX;
	}
	
	/**
	 * Return the vertical velocity of Mazub.
	 * 
	 * @return	A double that represents the vertical velocity of Mazub.
	 */
	@Basic @Raw
	public double getVelocityY(){
		return this.velocityY;
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
	private void setVelocityX(double velocityX){
		if(Util.fuzzyGreaterThanOrEqualTo(Math.abs(velocityX), this.getVelocityXMax())){
			this.setAccelerationX(0);
		}
			
		this.velocityX = Math.max( Math.min( velocityX , this.getVelocityXMax()), -this.getVelocityXMax());
	}
	
	/**
	 * Set the vertical velocity of Mazub.
	 * 
	 * @param 	velocityY
	 * 				A double that represents the desired vertical velocity of Mazub.
	 * @post	The vertical velocity is equal to velocityY.
	 * 			| new.getVelocityY() == velocityY
	 */
	@Basic @Raw
	private void setVelocityY(double velocityY){
		this.velocityY = velocityY;
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
	public boolean isValidVelocityX(double velocityX){
		return Math.abs(velocityX) <= this.getVelocityXMax();
	}
	
	private double velocityX;
	private double velocityY;
	
	// Initial velocity
	
	/**
	 * Return the initial horizontal velocity of Mazub. The initial horizontal velocity is used when Mazub
	 * starts walking.
	 *  
	 * @return	A double that represents the initial horizontal velocity of Mazub.
	 */
	@Basic @Raw
	public double getVelocityXInit(){
		return this.velocityXInit;
	}
	
	private final double velocityXInit;
	
	// Maximal velocity
	
	/**
	 * Return the maximal horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the maximal horizontal velocity of Mazub.
	 */
	@Basic @Raw
	public double getVelocityXMax(){
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
	@Basic @Raw
	private void setVelocityXMax(double velocityXMax){
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
	public boolean canHaveAsVelocityXMax(double velocityXMax){
		return  velocityXMax >= this.getVelocityXInit();
	}
	
	private double velocityXMax;
	
	// Acceleration
	
	/**
	 * Return the horizontal acceleration of Mazub.
	 * 
	 * @return	A double that represents the horizontal acceleration of Mazub.
	 */
	@Basic  @Raw
	public double getAccelerationX(){
		return this.accelerationX;
	}
	
	/**
	 * Return the vertical acceleration of Mazub.
	 * 
	 * @return	A double that represents the vertical acceleration of Mazub.
	 */
	@Basic @Raw @Immutable
	public double getAccelerationY(){
		return this.accelerationY;
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
	@Basic @Raw
	private void setAccelerationX(double accelerationX){
		if (Double.isNaN(accelerationX)){
			this.accelerationX = 0;
		} else
			this.accelerationX = accelerationX;
	}
	
	/**
	 * Set the vertical acceleration of Mazub.
	 * 
	 * @param 	accelerationY
	 * 				A double that represents the desired vertical acceleration of Mazub.
	 * @post	The vertical acceleration is equal to accelerationY. However, if accelerationY is equal
	 * 			to NaN, the vertical acceleration is set to 0 instead.
	 * 			| if ( Double.isNaN(accelerationY) )
	 * 			| 	then new.getAccelerationY() == 0
	 * 			| else
	 * 			| 	new.getAccelerationY() == accelerationY
	 */
	@Basic @Raw
	private void setAccelerationY(double accelerationY){
		if (Double.isNaN(accelerationY)){
			this.accelerationY = 0;
		} else
			this.accelerationY = accelerationY;
	}
		
	private double accelerationX;
	private double accelerationY;
	
	// Orientation
	
	/**
	 * Return the orientation of Mazub.
	 * 
	 * @return	An orientation that represents the current orientation of Mazub.
	 */
	@Basic @Raw
	public Orientation getOrientation(){
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
	@Basic @Raw @Model
	private void setOrientation(Orientation orientation){
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
	public static boolean isValidOrientation(Orientation orientation){
		return (orientation == Orientation.LEFT) || (orientation == Orientation.RIGHT);
	}
	
	private Orientation orientation;
	
	/******************************************* CHARACTER SIZE AND ANIMATION *********************************/
	
	/**
	 * Return the correct sprite of Mazub, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of Mazub.
	 * @note	No formal documentation was required for this method.
	 */
	public Sprite getCurrentSprite(){		
		return this.getAnimation().getCurrentSprite(this);	
	}
	
	
	/************************************************ ADVANCE TIME ********************************************/
	
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
	public void advanceTime(double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal timestep amount given: "+ dt + " s");
		
		// Update horizontal position
		this.updatePositionX(dt);
				
		// Update vertical position
		this.updatePositionY(dt);
				
		// Update horizontal velocity
		this.updateVelocityX(dt);
		
		// Update vertical velocity
		this.updateVelocityY(dt);
		
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		// Sprites
		this.getTimer().increaseSinceLastSprite(dt);
		this.getAnimation().updateAnimationIndex(this.getTimer());
	}
	
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
	private void updatePositionX(double dt){
		try{
			double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
			this.setPositionX( this.getPositionX() + 100 * sx );
		}catch( IllegalPositionXException exc){
			if(exc.getPositionX() < 0 ){
				this.setPositionX( 0 );
				endMove(Orientation.LEFT);
			}else{ // > GAME_WIDTH - 1 
				this.setPositionX( GAME_WIDTH - 1 );
				endMove(Orientation.RIGHT);
			}
		}
	}
	
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
	private void updatePositionY(double dt){
		try{
			double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
			this.setPositionY( this.getPositionY() + 100 * sy );
		}catch( IllegalPositionYException exc){
			if(exc.getPositionY() < 0 ){
				this.setPositionY(0);
				this.stopFall();
			}else{ // > GAME_HEIGHT - 1 
				this.setPositionY( GAME_HEIGHT - 1);
			}
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
	private void updateVelocityX(double dt) {
		double newVx = this.getVelocityX() + this.getAccelerationX() * dt;
		this.setVelocityX( newVx );
	}
	
	/**
	 * Update Mazub's vertical velocity according to the given dt.
	 * 
	 * @param dt
	 * 			A double that represents the elapsed time.
	 * @post	The vertical velocity of Mazub is equal to the previous horizontal velocity incremented 
	 * 			with the product of the vertical acceleration and dt
	 * 			| new.getVelocityY() == this.getVelocityY() + this.getAccelerationY() * dt
	 */
	private void updateVelocityY(double dt) {
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
	}
}
