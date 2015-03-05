package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.model.Time;

/**
 * A class of Mazubs, characters for a platform game with several properties. This class has been worked out
 * for a project of the course Object Oriented Programming at KULeuven.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

public class Mazub {
		
	/************************************************** GENERAL ***********************************************/
	
	// 			the class Util provides methods for comparing doubles up to a fixed epsilon
	// 			TO DO:
	//				write a test suite
	//				class invariants
	//				annotations
	//				private/public
	
	private static int GAME_WIDTH = 1024;
	private static int GAME_HEIGHT = 768;
	private static double ACCELERATION_Y = -10.0;
	private static double VELOCITY_Y_INIT = 8.0;
	private static double VELOCITY_X_MAX_MOVING = 3.0;
	private static double VELOCITY_X_MAX_DUCKING = 1.0;
	
	private Time time = new Time(); // initialiseren in constructor?

	private int currentSpriteIteration = 0;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// 			accepts an array of n images as parameter (n even, n >= 10)
	//			chosen to be worked out nominally

	/**
	 * Constructor for the class Mazub.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Mazub's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Mazub.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @effect	If the given pixelLeftX is within the boundaries of the game world, the initial positionX is 
	 * 			equal to pixelLeftX. If the given pixelLeftX is negative, the initial positionX is equal to 0.
	 * 			Otherwise, if the given pixelLeftX is greater than GAME_WIDTH - 1, the initial positionX is
	 * 			equal to GAME_WIDTH - 1.
	 * 			| setPositionX(pixelLeftX)
	 * @effect	If the given pixelBottomY is within the boundaries of the game world, the initial positionY is
	 * 			equal to pixelBottomY. If the given pixelBottomY is negative, the initial positionY is equal to 0.
	 *  		Otherwise, if the given pixelBottomY is greater than GAME_HEIGHT - 1, the initial positionY is
	 *   		equal to GAME_HEIGHT - 1.
	 * 			| setPositionY(pixelBottomY)
	 * @post	The initial ducking status of Mazub is equal to false.
	 * 			| new.isDucking() == false
	 * @effect	If VELOCITY_X_MAX_MOVING is greater than the initial horizontal velocity, the initial maximal
	 *  		horizontal velocity is equal to VELOCITY_X_MAX_MOVING. Otherwise, it's equal to the initial 
	 *  		horizontal velocity.
	 *  		| setVelocityXMax(VELOCITY_X_MAX_MOVING)
	 * @effect	The initial orientation of Mazub is equal to right.
	 * 			| setOrientation(Orientation.RIGHT)
	 * @post	The initial sprites of Mazub are equal to the given array sprites.
	 * 			| this.sprites == sprites
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);
		this.setDucking(false);
		velocityXInit = 1.0;
		this.setVelocityXMax(VELOCITY_X_MAX_MOVING);
		accelerationXInit = 0.9;		
		this.setOrientation(Orientation.RIGHT);
		this.sprites = sprites;
	}
	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	// 			All methods here must be worked out defensively (using integer numbers) 
	//				error if coordinates are negative?
	
	// 			Game world (X,Y) : 1024x768 pixels (origin bottom-left)
	// 			each pixel = 0.01m
	// 			Position of Mazub: bottom-left pixel of Mazub
	// 			Mazub's dimension (X_p,Y_p), depends on active sprite
	
	/**
	 * Return the round x-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	public int getRoundedPositionX(){
		return (int) Math.floor(this.getPositionX());
	}
	
	/**
	 * Return the round y-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	public int getRoundedPositionY(){
		return (int) Math.floor(this.getPositionY());
	}
	
	/**
	 * Return the width of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the width of Mazub's active sprite.
	 */
	@Basic
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Return the height of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the height of Mazub's active sprite.
	 */
	@Basic
	public int getHeight(){
		return this.height;
	}
	
		
	/**
	 * Set the width of Mazub.
	 * 
	 * @post	The width of Mazub is equal to the given width.
	 * 			| new.getWidth() == width
	 * @throws	... (width < 0) || (width > GAME_WIDTH)
	 */
	private void setWidth(int width){
		this.width = width;
	}
	
	/**
	 * Set the height of Mazub.
	 * 
	 * @post	The height of Mazub is equal to the given height.
	 * 			| new.getHeight() == height
	 * @throws	... (height < 0) || (height > GAME_HEIGHT)
	 */
	private void setHeight(int height){
		this.height = height;
	}
	
	private int width;
	private int height;
	
	/************************************************ RUNNING *************************************************/
	
	// 			All methods here must be worked out nominally
	// 			vx_init = 1 m/s	(may change in future but never below 1 m/s, instance variable)
	// 			ax = 0.9 m/s^2	(may change in future, class variable)
	// 			vx_max = 3 m/s	(may change in future but never below vx_init, instance variable)
	// 			startMove:
	// 				vx_new = vx_curr + ax*delta_t (for vx_init < vx_curr < vx_max)
	// 			endMove:
	// 				vx_new = 0
	
	/**
	 * Make Mazub start moving. Set the initial horizontal velocity and acceleration of Mazub,
	 * depending on his orientation.
	 * 
	 * @param orientation
	 * 				The direction in which Mazub starts moving.
	 * @pre		...
	 * @post	...
	 */
	public void startMove(Orientation orientation){
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getDirection() * this.velocityXInit );
		this.setAccelerationX( orientation.getDirection() * this.accelerationXInit );
	}
	
	/**
	 * Make Mazub end moving. Set the horizontal velocity and acceleration of Mazub to 0.
	 * 
	 * @post	The horizontal velocity and acceleration of Mazub is equal to zero. Also the time since the
	 * 			last move was made is reset to 0.
	 * 			| new.getVelocityX() == 0
	 * 			| new.getAccelerationX() == 0
	 * 			| (new time).getSinceLastMove() == 0
	 */
	public void endMove() {
		this.setVelocityX(0);
		this.setAccelerationX(0);
		time.setSinceLastMove(0);
	}

	/**
	 * Checks whether Mazub is moving.
	 * 
	 * @return 	A boolean that represents if Mazub is moving or not.
	 */
	public boolean isMoving(){
		return !Util.fuzzyEquals(this.getVelocityX(), 0);
	}
	
	/**
	 * Checks whether Mazub has moved in the last second.
	 * 
	 * @return	A boolean that represents if Mazub has moved in the last second or not.
	 */
	public boolean hasMovedInLastSecond(){
		return Util.fuzzyLessThanOrEqualTo(time.getSinceLastMove(), 1.0);
	}
	
	/********************************************* JUMPING AND FALLING ****************************************/
	
	// 			All methods here must be worked out defensively
	// 			startJump:
	//				vy_init = 8 m/s 	(will not change in future)
	//				vy_new = vy_current + ay*delta_t
	// 			endJump:
	//  			vy_new = 0 m/s 	(if vy_curr > 0)
	// 			while (y != 0):
	//				ay = -10 m/s^2	(will not change in future)
	
	/**
	 * Make Mazub start jumping. Set the vertical initial velocity and gravitational acceleration of Mazub.
	 * 
	 * @post	The vertical velocity and acceleration of Mazub is equal to respectively VELOCITY_Y_INIT and
	 * 			ACCELERATION_Y.
	 * 			| new.getVelocityY() == VELOCITY_Y_INIT
	 * 			| new.getAccelerationY() == ACCELERATION_Y
	 * @throws	...
	 */
	public void startJump(){
		this.setVelocityY( VELOCITY_Y_INIT );
		this.setAccelerationY( ACCELERATION_Y ); 
	}
	
	/**
	 * Make Mazub end jumping. Set the vertical velocity of Mazub to 0 when he's still moving upwards.
	 * 
	 * @post	If the vertical velocity of Mazub was greater than 0, it is now set to 0.
	 * 			| if (this.getVelocityY() > 0)
	 * 			|	then new.getVelocityY() == 0
	 * @throws 	...
	 */
	public void endJump() {
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}
	}
	
	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	A boolean that represents if Mazub is jumping or not.
	 */
	public boolean isJumping(){
		return !Util.fuzzyEquals(this.getVelocityY(), 0);
	}
	
	/**
	 * Make Mazub stop falling. Set the vertical velocity and acceleration of Mazub to 0.
	 * 
	 * @post	The vertical velocity and acceleration of Mazub is equal to 0.
	 * 			| new.getVelocityY() == 0
	 * 			| new.getAccelerationY() == 0
	 * @throws	...
	 */
	private void stopFall() {
		this.setVelocityY( 0 );
		this.setAccelerationY( 0 );
	}
	
	/**
	 * Checks whether Mazub is on the ground.
	 * 
	 * @return	A boolean that represents if Mazub is on the ground or not.
	 */
	private boolean isOnGround() {
		return Util.fuzzyEquals(this.getRoundedPositionY(), 0);
	}
	
	/*************************************************** DUCKING **********************************************/
	
	// 				All methods here must be worked out defensively
	// 				affects Mazub's dimension (X_p,Y_p)
	// 				restricts vx_max to 1 m/s (no acceleration possible)
	
	/**
	 * Make Mazub start ducking. Set the maximal horizontal velocity for ducking.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VELOCITY_X_MAX_DUCKING and the ducking
	 * 			status of Mazub is true.
	 * 			| new.getVelocityXMax() == VELOCITY_X_MAX_DUCKING
	 * 			| new.isDucking() == true
	 * @throws	...
	 */
	public void startDuck(){
		this.setVelocityXMax(VELOCITY_X_MAX_DUCKING);
		this.setDucking(true);
	}
	
	/**
	 * Make Mazub end ducking. Reset the maximal horizontal velocity.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VELOCITY_X_MAX_MOVING and the ducking
	 * 			status of Mazub is false.
	 * 			| new.getVelocityXMax() == VELOCITY_X_MAX_MOVING
	 * 			| new.isDucking() == false
	 * @throws	...
	 */
	public void endDuck(){
		this.setVelocityXMax(VELOCITY_X_MAX_MOVING);
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
	 * @param ducking
	 * 			A boolean that represents if Mazub's status should be changed to ducking or not.
	 * @post	The ducking status of Mazub is equal to the given boolean value of ducking.
	 * 			| new.isDucking() == ducking
	 */
	public void setDucking(boolean ducking){
		this.ducking = ducking;
	}
	
	private boolean ducking;
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// 				All methods here must be worked out totally
	// 				velocity, acceleration, orientation, timing 
	// 				type double (not NaN, may be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY)
	// 				rounding down to integer value (at the end!) to determine Mazub's effective position

	// Position
	
	/**
	 * Return the x-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	public double getPositionX(){
		return this.positionX;
	}
	
	/**
	 * Return the y-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	public double getPositionY(){
		return this.positionY;
	}

	/**
	 * Set the x-location of Mazub's bottom left pixel.
	 * 
	 * @param px
	 * 			A double that represents the desired x-location of Mazub's bottom left pixel.
	 * @post	If the given px is within the boundaries of the game world, positionX is equal to px. 
	 * 			If the given px is negative, positionX is equal to 0. Otherwise, if the given px is
	 * 			greater than GAME_WIDTH - 1, positionX is equal to GAME_WIDTH - 1.
	 * 			| if ( (px >= 0) && (px <= GAME_WIDTH-1) )
	 * 			|	then new.getPositionX() == px
	 * 			| else if (px < 0)
	 * 			|	then new.getPositionX() == 0
	 * 			| else if (px > GAME_WIDTH-1)
	 * 			| 	then new.getPositionX() == GAME_WIDTH-1
	 */
	private void setPositionX(double px) {
		this.positionX = Math.min(Math.max(px, 0), GAME_WIDTH - 1);
	}
	
	/**
	 * Set the y-location of Mazub's bottom left pixel.
	 * 
	 * @param py
	 * 			A double that represents the desired y-location of Mazub's bottom left pixel. 
	 * @post	If the given py is within the boundaries of the game world, positionY is equal to py. 
	 * 			If the given py is negative, positionY is equal to 0. Otherwise, if the given py is
	 * 			greater than GAME_HEIGHT - 1, positionY is equal to GAME_HEIGHT - 1.
	 * 			| if ( (py >= 0) && (px <= GAME_HEIGHT-1) )
	 * 			|	then new.getPositionY() == py
	 * 			| else if (py < 0)
	 * 			|	then new.getPositionY() == 0
	 * 			| else if (py > GAME_HEIGHT-1)
	 * 			| 	then new.getPositionY() == GAME_HEIGHT-1
	 */
	private void setPositionY(double py) {
		this.positionY = Math.min(Math.max(py, 0), GAME_HEIGHT - 1);
	}	
		
	private double positionX;
	private double positionY;
	
	// Velocity

	/**
	 * Return the horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the horizontal velocity of Mazub.
	 */
	@Basic
	public double getVelocityX(){
		return this.velocityX;
	}
	
	/**
	 * Return the vertical velocity of Mazub.
	 * 
	 * @return	A double that represents the vertical velocity of Mazub.
	 */
	@Basic
	public double getVelocityY(){
		return this.velocityY;
	}
	
	/**
	 * Set the horizontal velocity of Mazub.
	 * 
	 * @param vx
	 * 			A double that represents the desired horizontal velocity of Mazub.
	 * @post	If the absolute value of the given vx is smaller than the maximal horizontal velocity,
	 * 			velocityX is equal to vx. Else, velocityX is equal to the maximal horizontal velocity
	 * 			provided with the sign of vx.
	 * 			| if (Math.abs(vx) < this.getVelocityXMax())
	 * 			|	then new.getVelocityX() == vx
	 * 			| else
	 * 			|	new.getVelocityX() == Math.signum(vx)*this.getVelocityXMax()
	 */
	private void setVelocityX(double vx){
		this.velocityX = Math.max(Math.min( vx , this.getVelocityXMax()), -this.getVelocityXMax());
	}
	
	/**
	 * Set the vertical velocity of Mazub.
	 * 
	 * @param vy
	 * 			A double that represents the desired vertical velocity of Mazub.
	 * @post	The vertical velocity is equal to vy.
	 * 			| new.getVelocityY() == vy
	 */
	private void setVelocityY(double vy){
		this.velocityY = vy;
	}
	
	private double velocityX;
	private double velocityY;
	private double velocityXInit;
	
	// Maximal velocity
	
	/**
	 * Return the maximal horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the maximal horizontal velocity of Mazub.
	 */
	@Basic
	public double getVelocityXMax(){
		return this.velocityXMax;
	}
	
	/**
	 * Set the maximal horizontal velocity of Mazub.
	 * 
	 * @param vx_max
	 * 			A double that represents the desired maximal horizontal velocity of Mazub.
	 * @post	If vx_max is greater than the initial horizontal velocity, the maximal horizontal
	 * 			velocity is equal to vx_max. Otherwise, it's equal to the initial horizontal velocity.
	 * 			| if (vx_max > this.velocityXInit)
	 * 			| 	then new.getVelocityXMax() == vx_max
	 * 			| else
	 * 			|	new.getVelocityXMax() == this.velocityXInit
	 */
	private void setVelocityXMax(double vx_max){
		this.velocityXMax = Math.max(this.velocityXInit, vx_max);
	}
	
	private double velocityXMax;
	
	// Acceleration
	
	/**
	 * Return the horizontal acceleration of Mazub.
	 * 
	 * @return	A double that represents the horizontal acceleration of Mazub.
	 */
	@Basic
	public double getAccelerationX(){
		return this.accelerationX;
	}
	
	/**
	 * Return the vertical acceleration of Mazub.
	 * 
	 * @return	A double that represents the vertical acceleration of Mazub.
	 */
	@Basic @Immutable
	public double getAccelerationY(){
		return this.accelerationY;
	}
	
	/**
	 * Set the horizontal acceleration of Mazub.
	 * 
	 * @param ax
	 * 			A double that represents the desired horizontal acceleration of Mazub.
	 * @post	The horizontal acceleration is equal to ax.
	 * 			| new.getAccelerationX() == ax
	 */
	private void setAccelerationX(double ax){
		this.accelerationX = ax;
	}
	
	/**
	 * Set the vertical acceleration of Mazub.
	 * 
	 * @param ay
	 * 			A double that represents the desired vertical acceleration of Mazub.
	 * @post	The vertical acceleration is equal to ay.
	 * 			| new.getAccelerationY() == ay
	 */
	private void setAccelerationY(double ay){
		this.accelerationY = ay;
	}
		
	private double accelerationX;
	private double accelerationY;
	private double accelerationXInit;
	
	// Orientation
	
	/**
	 * Return the orientation of Mazub.
	 * 
	 * @return	An orientation that represents the current orientation of Mazub.
	 */
	@Basic
	public Orientation getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Set the orientation of Mazub.
	 * 
	 * @param orientation
	 * 			An orientation that represents the desired orientation of Mazub.
	 * @post	The orientation of Mazub is equal to the given orientation.
	 * 			| new.getOrientation() == orientation
	 */
	public void setOrientation(Orientation orientation){
		this.orientation = orientation;
	}	
	
	private Orientation orientation;
	
	/******************************************* CHARACTER SIZE AND ANIMATION *********************************/
	
	// 				All methods here must be worked out nominally
	// 				no formal documentation required
	// 				class Sprite is provided
	// 				multiple sprites for moving to the right/left (same amount), alternate (75ms) and repeat
	// 				it must be possible to turn to other algorithms for displaying successive images of a Mazub
	//					during some period of time
	//				
	//				aparte klasse
	
	/**
	 * Return the correct sprite of Mazub, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of Mazub.
	 */
	public Sprite getCurrentSprite(){
		
		// Moet mooier/efficienter/korter
		
		// m bepalen -> mss beter gewoon een keer doen in constructor en dan opslaan?
		int m = ( this.sprites.length - 8) / 2; // Als length even is geeft dit altijd een correct getal 
												//	-> moeten nog check doen
		
		
		// Voor animatie
		
		while(time.getSinceLastSprite() > 0.075){
			this.setCurrentSpriteIteration(this.getCurrentSpriteIteration() + 1);
			this.setCurrentSpriteIteration(this.getCurrentSpriteIteration() % m);
			time.setSinceLastSprite(time.getSinceLastSprite() - 0.075);
		}
		
		int index = 0;
		
		if(!this.isMoving()){
			
			if(!this.hasMovedInLastSecond()){
				if(!this.isDucking()){
					index = 0;
				}else{
					index = 1;
				}
			}else{
				if(!this.isDucking()){
					if(this.getOrientation() == Orientation.RIGHT){
						index = 2;
					}else{ // LEFT
						index = 3;
					}
				}else{
					if(this.getOrientation() == Orientation.RIGHT){
						index = 6;
					}else{ // LEFT
						index = 7;
					}
				}
			}
			
		}else{ // MOVING
			if(this.isJumping()){
				if(!this.isDucking()){
					if(this.getOrientation() == Orientation.RIGHT){
						index = 4;
					}else{ // LEFT
						index = 5;
					}
				}
			}
			
			if(this.isDucking()){
				if(this.getOrientation() == Orientation.RIGHT){
					index = 6;
				}else{ // LEFT
					index = 7;
				}
			}
			if(!this.isDucking() && !this.isJumping()){
				if(this.getOrientation() == Orientation.RIGHT){
					index = 8 + this.getCurrentSpriteIteration();
				}else{ // LEFT
					index = 8 + m + this.getCurrentSpriteIteration();
				}
			}
		}
		
		return this.sprites[index];
		
	}
	
	/**
	 * Return the number referring to the current sprite in an iterative process of sprites.
	 * 
	 * @return	An integer that represents the current sprite in an iterative process of sprites.
	 */
	public int getCurrentSpriteIteration(){
		return this.currentSpriteIteration;
	}
	
	/**
	 * Set the number referring to the current sprite in an iterative process of sprites.
	 * 
	 * @param currentSprite
	 * 			An integer that represents the desired sprite in an iterative process of sprites.
	 */
	public void setCurrentSpriteIteration(int currentSprite){
		this.currentSpriteIteration = currentSprite;
	}
	
	private Sprite[] sprites;	
	
	/************************************************ ADVANCE TIME ********************************************/
	
	// 				All methods here must be worked out defensively
	//				updates position and velocity of Mazub based on the current position, velocity, acceleration
	//				and a given time duration delta_t in seconds (0 < delta_t < 0.2)
	//				sx = vx_curr*delta_t (no acceleration, formula may change in future but never above the
	//					value described by this formula)
	//				sx = vx_curr*delta_t + 0.5*ax*delta_t^2 (with acceleration, formula may change in future but
	// 					never above the value described by this formula)
	//				x_new = x_curr + sx
	//				sy = vy_curr*delta_t (no acceleration, formula may change in future but never above the
	//					value described by this formula)
	//				sy = vy_curr*delta_t + 0.5*ay*delta_t^2 (with acceleration, formula may change in future but
	//					never above the value described by this formula)
	//				y_new = y_curr + sy
	//				ensure that the bottom-left pixel of Mazub stays at all times within the boundaries of the
	//					game world
	
	/**
	 * Advance time and update Mazub's position and velocity accordingly.
	 * 
	 * @param dt
	 * 			A double that represents the elapsed time.
	 * @post	The horizontal position of Mazub is equal to ...
	 * 			| new.getPositionX() == this.getPositionX() + 100*( this.getVelocityX() * dt +
	 * 			| 						0.5 * this.getAccelerationX() * Math.pow( dt , 2 ) )
	 * @post	The vertical position of Mazub is equal to ...
	 * 			| new.getPositionY() == this.getPositionY() + 100*( this.getVelocityY() * dt +
	 * 			| 						0.5 * this.getAccelerationY() * Math.pow( dt , 2 ) )
	 * @post	The horizontal velocity of Mazub is equal to ...
	 * 			| new.getVelocityX() == this.getVelocityX() + this.getAccelerationX() * dt
	 * @post	The vertical velocity of Mazub is equal to ...
	 * 			| new.getVelocityY() == this.getVelocityY() + this.getAccelerationY() * dt
	 * @post	...
	 * @throws	error: delta_t > 0.2 or delta_t < 0
	 */
	public void advanceTime(double dt){
		
		// Update  horizontal position
		double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
		this.setPositionX( this.getPositionX() + 100 * sx );
				
		// Update vertical position
		double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
		this.setPositionY( this.getPositionY() + 100 * sy );
				
		// Update horizontal velocity
		double newVx = this.getVelocityX() + this.getAccelerationX() * dt;
		this.setVelocityX( newVx );
		
		// Update vertical velocity
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
		
		// If Mazub hits the ground, stop falling
		if( isOnGround() ){
			stopFall();
		}
		
		if(!this.isMoving())
			time.setSinceLastMove(time.getSinceLastMove() + dt);
		time.setSinceLastSprite(time.getSinceLastSprite() + dt);
	}

}
