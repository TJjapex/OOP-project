package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Timer;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

// Superclass for Mazub, Shark, Slime, Plant

public abstract class GameObject {
	/************************************************** GENERAL ***********************************************/
	
	/**
	 * Constant reflecting the vertical acceleration for game objects.
	 * 
	 * @return	The vertical acceleration of GameObjects is equal to -10.0 m/s^2.
	 * 			| result == -10.0
	 */
	public static final double ACCELERATION_Y = -10.0;
	
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 768;

	/************************************************ CONSTRUCTOR *********************************************/

	public GameObject(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
					  double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
					  int maxNbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		assert sprites.length >= 10 && sprites.length % 2 == 0;
		
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);

		this.velocityXInit = velocityXInit;
		this.velocityYInit = velocityYInit;
		
		this.setVelocityXMax(velocityXMax);
		
		this.accelerationXInit = accelerationXInit;
		
		this.setOrientation(Orientation.RIGHT);		
		
		this.maxNbHitPoints = maxNbHitPoints;
		this.setNbHitPoints(nbHitPoints);
		
	}

	
	/************************************************ WORLD RELATION *****************************************/
	
	public World getWorld() {
		return this.world;
	}

	public boolean hasProperWorld(){
		return this.getWorld() != null;
	}

	void setWorld(World world) {
		this.world = world;
	}
	
	public boolean canHaveAsWorld(World world){
		// TODO Auto-generated method stub
		return false;
	}
	
	private World world;
	
	public void terminate(){
		this.isTerminated = true;
	}
	
	private boolean isTerminated = false;
	
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

	private Timer timer;	

	/********************************************* SIZE AND POSITIONING ***************************************/
	
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

//	Dit gaat niet meer omdat de wereldgrootte geen constante is :(
//	/**
//	 * Check whether the given X position is a valid X position.
//	 * 
//	 * @param 	positionX
//	 * 				An integer that represents an x-coordinate.
//	 * @return	True if and only if the given x-position positionX is between the boundaries of the game world, 
//	 * 			which means that the x-coordinate must be greater than or equal to 0 and smaller than GAME_WIDTH.
//	 * 			|  result == ( (positionX >= 0) && (positionX < GAME_WIDTH) )
//	 */
//	@Basic
//	@Raw
//	public static boolean isValidRoundedPositionX(int positionX) {
//		return positionX >= 0 && positionX < this.getWorld().getWorldWidth();
//	}
	
	public boolean canHaveAsPositionX(int positionX){
		return positionX >= 0 && (!hasProperWorld() || positionX < this.getWorld().getWorldWidth());
	}

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

//	/**
//	 * Check whether the given Y position is a valid Y position.
//	 * 
//	 * @param 	positionY
//	 * 				An integer that represents a y-coordinate.
//	 * @return	True if and only if the given y-position positionY is between the boundaries of the game world, 
//	 * 			which means that the y-coordinate must be greater than or equal to 0 and smaller than
//	 * 			GAME_HEIGHT.
//	 * 			|  result == ( (positionY >= 0) && (positionY < GAME_HEIGHT) )
//	 */
//	public static boolean isValidRoundedPositionY(int positionY) {
//		return positionY >= 0 && positionY < GAME_HEIGHT;
//	}
	public boolean canHaveAsPositionY(int positionY){
		return positionY >= 0 && (!hasProperWorld() || positionY < this.getWorld().getWorldHeight());
	}
	

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
	
	/************************************************ MOVING *************************************************/

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
	public void startMove(Orientation orientation) {		
		assert (this.getOrientation() != null);
		
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * this.getVelocityXInit() );
		this.setAccelerationX( orientation.getSign() * accelerationXInit);
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
	public boolean isMoving() {
		return !Util.fuzzyEquals(this.getVelocityX(), 0);
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
	public void startJump() {
		this.setVelocityY( this.getVelocityYInit() );
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
	public void endJump() throws IllegalStateException {
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}else{
			throw new IllegalStateException("GameObject does not have a positive vertical velocity!");
		}
	}

	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	True if and only if the vertical position of Mazub is equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getPositionY() == 0 )
	 */
	public boolean isJumping() {
		//return !Util.fuzzyEquals(this.getPositionY(), 0);
		
		// Kan ook mss met collission gedaan worden (trager maar mss wel beter)
		//if(!hasProperWorld())
		//	return false;		
		//return getWorld().collidesWith(this).contains(Orientation.BOTTOM);
		return !Util.fuzzyEquals(this.getVelocityY(), 0);
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
		this.setAccelerationY( 0 );
	}

	/************************************************ CHARACTERISTICS *****************************************/
	
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
	protected void setPositionX(double positionX)
			throws IllegalPositionXException {
				if( !canHaveAsPositionX(positionX)) 
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
	@Basic
	@Raw
	protected void setPositionY(double positionY)
			throws IllegalPositionYException {
				if( !canHaveAsPositionY(positionY)) 
					throw new IllegalPositionYException(positionY);
				this.positionY = positionY;
			}

//	/**
//	 * Check whether the given X position is a valid horizontal position.
//	 * 
//	 * @param 	positionX
//	 * 				A double that represents an x-coordinate.
//	 * @return	True if and only if the given x-position positionX is between the boundaries of the game world, 
//	 * 			which means that the x-coordinate must be greater than or equal to 0 and smaller or equal to
//	 * 			GAME_WIDTH.
//	 * 			|  result == ( (positionX >= 0) && (positionX <= GAME_WIDTH-1) )
//	 */
//	public static boolean isValidPositionX(double positionX) {
//		return isValidRoundedPositionX((int) Math.floor(positionX));
//	}
	public boolean canHaveAsPositionX(double positionX) {
		return canHaveAsPositionX((int) Math.floor(positionX));
	}

//	/**
//	 * Check whether the given Y position is a valid vertical position.
//	 * 
//	 * @param 	positionY
//	 * 				A double that represents a y-coordinate.
//	 * @return 	True if and only if the given y-position positionY is between the boundaries of the game world, 
//	 * 			which means that the y-coordinate must be greater than or equal to 0 and smaller or equal to 
//	 * 			GAME_HEIGHT. 
//	 * 			|  result == ( (positionY >= 0) && (positionY <= GAME_HEIGHT-1) )
//	 */
	public boolean canHaveAsPositionY(double positionY) {
		return canHaveAsPositionY((int) Math.floor(positionY));
	}

	/**
	 * Variable registering the horizontal position of this Mazub.
	 */
	private double positionX;
	/**
	 * Variable registering the vertical position of this Mazub.
	 */
	private double positionY;

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
	 * Set the vertical velocity of Mazub.
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
	 * Variable registering the horizontal velocity of this Mazub.
	 */
	private double velocityX;
	/**
	 * Variable registering the vertical velocity of this Mazub.
	 */
	private double velocityY;

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
	 * Return the vertical acceleration of Mazub.
	 * 
	 * @return	A double that represents the vertical acceleration of Mazub.
	 */
	@Basic
	@Raw
	@Immutable
	public double getAccelerationY() {
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
	@Basic
	@Raw
	protected void setAccelerationX(double accelerationX) {
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
	@Basic
	@Raw
	protected void setAccelerationY(double accelerationY) {
		if (Double.isNaN(accelerationY)){
			this.accelerationY = 0;
		} else
			this.accelerationY = accelerationY;
	}

	/**
	 * Variable registering the horizontal acceleration of this Mazub.
	 */
	private double accelerationX;
	/**
	 * Variable registering the vertical acceleration of this Mazub.
	 */
	private double accelerationY;

	public double getAccelerationXInit() {
		return this.accelerationXInit;
	}
	
	protected final double accelerationXInit;

	
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

	/**
	 * Variable registering the orientation of this Mazub.
	 */
	protected Orientation orientation;

	
	/****************************************************** SPRITES *******************************************/
	
	public Sprite getCurrentSprite(){
		if(getOrientation() == Orientation.RIGHT){
			return getSpriteAt(0);
		}else{
			return getSpriteAt(1);
		}
	};
	
	private Sprite getSpriteAt(int index){
		return this.sprites[index];
	}
	
	private Sprite[] sprites;

	
	
	/*********************************************** CHARACTERISTICS UPDATERS *********************************/
	
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
		}catch( IllegalPositionXException exc){
			if(exc.getPositionX() < 0 ){
				this.setPositionX( 0 );
				endMove(Orientation.LEFT);
			}else{ // > GAME_WIDTH - 1 
				this.setPositionX( world.getWorldWidth() - 1 );
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
	protected void updatePositionY(double dt) {
		try{
			double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
			this.setPositionY( this.getPositionY() + 100 * sy );
		}catch( IllegalPositionYException exc){
			if(exc.getPositionY() < 0 ){
				this.setPositionY(0);
				this.stopFall();
			}else{ // > GAME_HEIGHT - 1 
				this.setPositionY( getWorld().getWorldHeight() - 1);
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
	protected void updateVelocityX(double dt) {
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
	protected void updateVelocityY(double dt) {
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
	}

	/*************************************************** HIT-POINTS *******************************************/
	
	public int getNbHitPoints() {
		System.out.println(this.nbHitPoints);
		return this.nbHitPoints;
	}

	public void setNbHitPoints(int nbHitPoints) {
		this.nbHitPoints = Math.max( Math.min(nbHitPoints, this.getMaxNbHitPoints()), 0);
	}

	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints <= this.getMaxNbHitPoints());
	}

	private int nbHitPoints;
	
	public int getMaxNbHitPoints(){
		return this.maxNbHitPoints;
	}
	
	protected final int maxNbHitPoints;
	
	
	
	
	

	
	/******************************************** COLLISION *************************/
	
	public boolean doesCollide(){
		return this.getWorld().objectCollides(this);

		
		/* Onderstaande is nu geimplementeerd in World, maar eigenlijk is die objectCollides(this) toch nie echt mooi */
		
//		// Check colision with tiles
//		int[][] tiles = this.getWorld().getTilePositionsIn(	this.getRoundedPositionX(), 
//															this.getRoundedPositionY(),
//															this.getRoundedPositionX() + this.getWidth(), 
//															this.getRoundedPositionY() + this.getHeight());
//		for(int[] tile : tiles){
//			
//			if(this.getWorld().getGeologicalFeature(this.getWorld().getPositionOfTileX(tile[0]), this.getWorld().getPositionOfTileY(tile[1])) == 1 &&					
//					this.doesCollideWith(this.getWorld().getPositionOfTileX(tile[0]), 
//										this.getWorld().getPositionOfTileY(tile[1]), 
//										this.getWorld().getTileLength(), 
//										this.getWorld().getTileLength())){
//				//System.out.println(this.getRoundedPositionX()+ " " + this.getRoundedPositionY());
//				//System.out.println(this.getWorld().getPositionOfTileX(tile[0]) + " "+this.getWorld().getPositionOfTileY(tile[1]) );
//				return true;
//			}	
//		}
//		
//		return false;
		
	}
	
	public boolean doesCollideWith(GameObject other){
//		return (this.getPositionX() + ( this.getWidth() - 1) <  other.getPositionX()) 
//				|| ( other.getPositionX() + (other.getWidth() - 1) < this.getPositionX())
//				|| ( this.getPositionY() + (this.getHeight() - 1 ) < other.getPositionY())
//				|| (other.getPositionY() + (other.getHeight() - 1) < this.getPositionY() )
		
		return doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), other.getWidth(), other.getHeight());
	}
	
	public boolean doesCollideWith(int x, int y, int width, int height){
		return ! ( 
				   (this.getPositionX() + ( this.getWidth() - 2) <  x) 
				|| (x + (width - 1) < this.getPositionX())
				|| ( this.getPositionY() + (this.getHeight() - 2 ) < y)
				|| (y + (height - 2) < this.getPositionY() ) // -2 want mag 1 pixel overlappen, maar alleen met TILES?!
				
				);
	}

}