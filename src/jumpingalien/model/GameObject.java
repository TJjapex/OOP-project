package jumpingalien.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jumpingalien.model.exceptions.CollisionException;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Timer;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.part2.internal.tmxfile.data.Tileset;
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
	
	/************************************************ CONSTRUCTOR *********************************************/

	public GameObject(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
					  double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
					  int maxNbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{		
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);

		this.velocityXInit = velocityXInit;
		this.velocityYInit = velocityYInit;
		
		this.setVelocityXMax(velocityXMax);
		
		this.accelerationXInit = accelerationXInit;
		
		this.setOrientation(Orientation.RIGHT);		
		
		this.maxNbHitPoints = maxNbHitPoints;
		this.setNbHitPoints(nbHitPoints);
		
		this.setSprites(sprites);
		this.setTimer(new Timer());
		
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
	
	// Invoked to remove object after 0.6s
	void kill(){
		this.killed = true;
	}
	
	public boolean isKilled(){
		return this.killed;
	}
	
	private boolean killed = false;
	
	// Will remove object from world
	protected void terminate(){
		this.getWorld().removeGameObject(this);
		this.setWorld(null);
		
		this.terminated = true;
	}
	
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
	
	
	
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

	protected Timer timer;	

	/************************************************ TERRAIN *************************************************/
	public Map<Terrain, TerrainProperties> allTerrainProperties = new HashMap<Terrain, TerrainProperties>();
	
	public Map<Terrain, TerrainProperties> getAllTerrainProperties() {
		return allTerrainProperties;
	}
	
	public TerrainProperties getTerrainPropertiesOf(Terrain terrain) {
		return allTerrainProperties.get(terrain);
	}
	
	public void setTerrainPropertiesOf(Terrain terrain, TerrainProperties terrainProperties){
		allTerrainProperties.put(terrain, terrainProperties);
	}
	
	public boolean hasTerrainPropertiesOf(Terrain terrain){
		return allTerrainProperties.containsKey(terrain);
	}
	
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
	
	// TODO update docs because now it checks if mazub is not already jumping
	public void startJump() {
		if(this.isOnGround()){
			this.setVelocityY( this.getVelocityYInit() );
			// this.setAccelerationY( ACCELERATION_Y ); 
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
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}else{
			// throw new IllegalStateException("GameObject does not have a positive vertical velocity!");
		}
	}

	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	True if and only if the vertical position of Mazub is equal to 0. (up to a certain epsilon)
	 * 			| result == ( this.getPositionY() == 0 )
	 */
	public boolean isOnGround() {
		//return !Util.fuzzyEquals(this.getPositionY(), 0);
		
		//if(!hasProperWorld())
		//	return false;		
		//return getWorld().collidesWith(this).contains(Orientation.BOTTOM);
		
		// TODO: zou nog beter zijn als we hier checken of onder mazub een tile is. Deze check is eigenlijk niet voldoende
		//return (this.getVelocityY() != 0);
		
		// return !(this.doesOverlap(Orientation.BOTTOM));
		
		if(this.doesOverlap(Orientation.BOTTOM)){
			return true;
		}else{
			return false;
		}
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
	 * Returns the magnitude of the velocity of this object.
	 * @return
	 * 		the magnitude of the velocity of this object
	 */
	protected double getVelocityMagnitude(){
		return Math.sqrt( Math.pow(this.getVelocityX(), 2) + Math.sqrt( Math.pow(this.getVelocityY(), 2)));
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
		if(this.isOnGround()){
			return 0;
		}else{
			return ACCELERATION_Y;
		}
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
	 * Returns the magnitude of the acceleration of this object.
	 * @return
	 * 		the magnitude of the acceleration of this object
	 */
	protected double getAccelerationMagnitude(){
		 return Math.sqrt( Math.pow(this.getAccelerationX(), 2) + Math.sqrt( Math.pow(this.getAccelerationY(), 2)));
	}

	/**
	 * Variable registering the horizontal acceleration of this Mazub.
	 */
	private double accelerationX;

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

	
	/****************************************************** SPRITES *******************************************/
	
	public Sprite getCurrentSprite(){
		//if(getOrientation() == Orientation.LEFT){
			return getSpriteAt(0);
		//}else{
		//	return getSpriteAt(1);
		//}
	};
	
	private Sprite getSpriteAt(int index){
		return this.sprites[index];
	}
	
	protected void setSprites(Sprite[] sprites){
		this.sprites = sprites;
	}
	
	private Sprite[] sprites;

	
	
	/*********************************************** CHARACTERISTICS UPDATERS *********************************/
	
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
	
	public void advanceTimeOnce(double dt) throws IllegalArgumentException, IllegalStateException{
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
		
		// Check if still alive...
		if(this.getNbHitPoints() == 0){
			this.kill();
		}		
		
		updateTimers(dt);
		doMove(dt);
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
	
	public void updateTimers(double dt){
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		// Sprites
		this.getTimer().increaseSinceLastSprite(dt);
		
		this.getTimer().increaseSinceTerrainCollision(dt);
		this.getTimer().increaseSinceEnemyCollision(dt);
		this.getTimer().increaseSinceLastPeriod(dt);
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
	protected void updatePositionX(double dt) {
		try{
			double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
			this.setPositionX( this.getPositionX() + 100 * sx );
		}catch( IllegalPositionXException exc){
			this.kill();
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
			this.kill();
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
		return this.nbHitPoints;
	}

	public void setNbHitPoints(int nbHitPoints) {
		this.nbHitPoints = Math.max( Math.min(nbHitPoints, getMaxNbHitPoints()), 0);
	}
	
	public void increaseNbHitPoints(int nbHitPoints){
		this.setNbHitPoints(this.getNbHitPoints() + nbHitPoints);
	}
	
	public void decreaseNbHitPoints(int nbHitPoints){
		this.setNbHitPoints(this.getNbHitPoints() - nbHitPoints);
	}
	
	public void takeDamage(int damageAmount){
		this.increaseNbHitPoints(-damageAmount);
	}

	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints <= getMaxNbHitPoints());
	}

	private int nbHitPoints;
	
	public int getMaxNbHitPoints(){
		return this.maxNbHitPoints;
	}
	
	protected final int maxNbHitPoints;
	
	/********************************************************** COLLISION ****************************************************/
	
	/* Checking */
	
	/**
	 * Checks if this object collides with any other impassable object
	 * 
	 * @return
	 */
	public boolean doesCollide(){
		return this.doesCollide(Orientation.ALL);
	}
	
	public boolean doesCollide(Orientation orientation){
//		System.out.println("doesCollide()"+this);
		World world = this.getWorld();
		
		// Check collision with tiles
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX(), 
													this.getRoundedPositionY(),
			 										this.getRoundedPositionX() + this.getWidth(), 
													this.getRoundedPositionY() + this.getHeight());

		for(int[] tile : tiles){
			// Check if that tile is passable 
			// and if the given object collides with a tile...
			Terrain terrain = world.getGeologicalFeature(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1]));
			if( !getTerrainPropertiesOf( terrain ).isPassable() ){
				if( this.doesCollideWith(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1]), world.getTileLength(), world.getTileLength(), orientation)){
					return true;
				}
			}
		}		
		
		// Check colission with gameObjects
		for(GameObject object : world.getAllNonPassableGameObjects()){
			//System.out.println(object);
			if(object != this && doesCollideWith(object, orientation)){
				//System.out.println("this"+ this.getPositionX()+" "+this.getPositionY() + " "+this.getWidth() + " "+this.getHeight());
				//System.out.println("other"+ object.getPositionX()+" "+object.getPositionY() + " "+object.getWidth() + " "+object.getHeight());
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * Checks if this object collides with given gameobject
	 * @param other
	 * @return
	 */
	public boolean doesCollideWith(GameObject other){
		return this.doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), other.getWidth(), other.getHeight(), Orientation.ALL);
	}
	
	public boolean doesCollideWith(GameObject other, Orientation orientation){
		return this.doesCollideWith(other.getRoundedPositionX(), other.getRoundedPositionY(), other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Checks if this object collides with the given region
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean doesCollideWith(int x, int y, int width, int height){
		return this.doesCollideWith(x,y,width,height,Orientation.ALL);
	}
	
	public boolean doesCollideWith(int x, int y, int width, int height, Orientation orientation){
		switch (orientation) {
//			case RIGHT: 
//				return ! (this.getRoundedPositionX() + ( this.getWidth() - 2) <  x);
//			case LEFT:
//				return ! (x + (width - 2) < this.getRoundedPositionX());
//			case TOP:
//				return ! ( this.getRoundedPositionY() + (this.getHeight() - 2) < y);
//			case BOTTOM:
//				return ! (y + (height - 2) < this.getRoundedPositionY() );
//				
			default:
				return ! ( // Dus geeft true als elke deelexpressie false geeft
						   (this.getRoundedPositionX() + ( this.getWidth() - 2) <  x) 
						|| (x + (width - 2) < this.getRoundedPositionX())
						|| ( this.getRoundedPositionY() + (this.getHeight() - 2) < y) // top
						|| (y + (height - 2) < this.getRoundedPositionY() ) //bottom
						
						);
		}
	}
	
	public boolean doesOverlap(){
		return this.doesOverlap(Orientation.ALL);
	}
	
	public boolean doesOverlap(Orientation orientation){
		World world = this.getWorld();
		
		// Check collision with tiles
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX(), 
													this.getRoundedPositionY(),
			 										this.getRoundedPositionX() + this.getWidth(), 
													this.getRoundedPositionY() + this.getHeight());

		for(int[] tile : tiles){
			// Check if that tile is passable 
			// and if the given object collides with a tile...
			Terrain terrain = world.getGeologicalFeature(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1]));
			if( !getTerrainPropertiesOf( terrain ).isPassable() ){
				if( this.doesOverlapWith(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1]), world.getTileLength(), world.getTileLength(), orientation)){
					return true;
				}
			}
		}		
		
		// Check colission with gameObjects
		for(GameObject object : world.getAllNonPassableGameObjects()){
			if(object != this && doesOverlapWith(object, orientation)){
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * Checks if this object overlaps with given gameobject
	 * @param other
	 * @return
	 */
	public boolean doesOverlapWith(GameObject other){
		return this.doesOverlapWith(other.getRoundedPositionX(), other.getRoundedPositionY(), other.getWidth(), other.getHeight());
	}
	
	public boolean doesOverlapWith(GameObject other, Orientation orientation){
		return this.doesOverlapWith(other.getRoundedPositionX(), other.getRoundedPositionY(), other.getWidth(), other.getHeight(), orientation);
	}
	
	/**
	 * Checks if this object overlaps with the given region
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean doesOverlapWith(int x, int y, int width, int height){
		return this.doesOverlapWith(x,y,width,height,Orientation.ALL);
	}
	
	public boolean doesOverlapWith(int x, int y, int width, int height, Orientation orientation){
		// return this.doesCollideWith(x+1, y+1, width-2, height -2) zou ook moeten werken (rekenkundig hetzelfde), minder redundant. Niet getest. TODO
		
		switch (orientation) {
//			case RIGHT: 
//				return ! (this.getRoundedPositionX() + ( this.getWidth() - 1) <  x);
//			case LEFT:
//				return ! (x + (width - 1) < this.getRoundedPositionX());
//			case TOP:
//				return ! ( this.getRoundedPositionY() + (this.getHeight() - 1) < y);
			case BOTTOM:
				// return ! (y + (height - 1) < this.getRoundedPositionY() ); --> klopt niet?
				
				// IK heb dit gecopypaste van de world class en wat aangepast zodat het (trial and error) zou kloppen, geen idee of het echt correct is. 
				return (getPositionY() > y &&
					   (getPositionY() < y + height &&
						 // check left-bottom pixel
					   (((getPositionX()+1 > x) &&
						 (getPositionX()+1 < x + width)) ||
						 // check right-bottom pixel
						((getPositionX() +(getWidth()-1)-1 > x)) &&
						 (getPositionX() +(getWidth()-1)-1 < x + width))));
			
			default:
				return ! ( // Dus geeft true als elke deelexpressie false geeft
						   (this.getRoundedPositionX() + ( this.getWidth() - 1) <  x) 
						|| (x + (width - 1) < this.getRoundedPositionX())
						|| ( this.getRoundedPositionY() + (this.getHeight() - 1) < y) // top
						|| (y + (height - 1) < this.getRoundedPositionY() ) //bottom
					
					);
		}
	}

	/* Processing */
	
	/**
	 * Checks the colliding tiles and objects and processes it
	 * 
	 */
	public void processOverlap(){
		
		// Process tiles
		this.processTileOverlap();
		
		// Process gameobjects 
		this.processGameObjectOverlap();
	}

	public void processTileOverlap(){
		Set<Terrain> collisionTileTypes = this.getColissionTileTypes();
		
		for(Terrain collisionTerrain : collisionTileTypes){
			if( this.hasTerrainPropertiesOf(collisionTerrain) && this.getTerrainPropertiesOf(collisionTerrain).getDamage() != 0 ){
				if( this.getTimer().getSinceTerrainCollision(collisionTerrain) > this.getTerrainPropertiesOf(collisionTerrain).getDamageTime() ){ // > of >=? fuzzy?
					this.takeDamage( this.getTerrainPropertiesOf(collisionTerrain).getDamage() );
					this.getTimer().setSinceTerrainCollision(collisionTerrain, 0);
				}
			}
		}
	}	
	
	public void processGameObjectOverlap(){
		World world = this.getWorld();
		
		for(Mazub alien:  world.getAllMazubs()){
			if(this.doesOverlapWith(alien)){
				this.processMazubOverlap(alien);
			}
		}
		
		for(Plant plant :  world.getAllPlants()){
			if(this.doesOverlapWith(plant)){
				this.processPlantOverlap(plant);
			}
		}
		
		for(Shark shark :  world.getAllSharks()){
			if(this.doesOverlapWith(shark)){
				processSharkOverlap(shark);
			}
		}
		
		for(Slime slime :  world.getAllSlimes()){
			if(this.doesOverlapWith(slime)){
				processSlimeOverlap(slime);
			}
		}
		
	}
	
	// TODO Onderstaande methodes worden nu uitgewerkt in de subklasses, dus misschien abstract maken. 
	// Misschien is het beter om da samen te voegen ofzo zoals bij die tiles
	public void processMazubOverlap(Mazub alien){
		
	}
	
	public void processPlantOverlap(Plant plant){
		
	}
	
	public void processSharkOverlap(Shark shark){
		
	}
	
	public void processSlimeOverlap(Slime slime){
		
	}


	
	/** 
	 * Returns a set containing the colliding terrain types 
	 * */
	public Set<Terrain> getColissionTileTypes(){
		World world = this.getWorld();
		
		Set<Terrain> colissionTileTypes = new HashSet<Terrain>();
		
		// Check collision with tiles
		int[][] tiles = world.getTilePositionsIn(	this.getRoundedPositionX(), 
													this.getRoundedPositionY(),
													this.getRoundedPositionX() + this.getWidth(), 
													this.getRoundedPositionY() + this.getHeight()  );
		
		for(int[] tile : tiles){
			if(this.doesCollideWith(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1]), world.getTileLength(), world.getTileLength())){
				colissionTileTypes.add(world.getGeologicalFeature(world.getPositionOfTileX(tile[0]), world.getPositionOfTileY(tile[1])));	
			}
		}
		
		return colissionTileTypes;
	}

	
	
	
	
	public boolean isSubmergedIn(Terrain terrain){
		
		int tileX = world.getTileX(this.getRoundedPositionX() + this.getWidth()/2); // to avoid boundary condition mistakes
		int tileY = world.getTileY(this.getRoundedPositionY() + this.getHeight());
				
		return world.getGeologicalFeature(world.getPositionOfTileX(tileX),world.getPositionOfTileY(tileY)) == terrain;
	}
	
	
}
