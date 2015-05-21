package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.program.Program;
import jumpingalien.model.terrain.Terrain;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.terrain.TerrainProperties;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

/**
 * A class of Sharks, enemy characters in the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 * @note Class invariants of the class GameObject also apply to this subclass.
 */
public class Shark extends GameObject implements IJumpable, IProgrammable{
	
	/****************************************************** CONSTANTS **************************************************/
	
	/**
	 * Constant reflecting the amount of damage a Shark should take when it overlaps with a Mazub.
	 * 
	 * @return	| result == 50
	 */
	private static final int MAZUB_DAMAGE = 50;

	/**
	 * Constant reflecting the amount of damage a Shark should take when it overlaps with a Slime.
	 * 
	 * @return	| result == 50
	 */
	private static final int SLIME_DAMAGE = 50;
	
	/**
	 * Constant reflecting the minimal period time for a periodic movement of a Shark.
	 * 
	 * @return	| result == 1.0
	 */
	public static final double MIN_PERIOD_TIME = 1.0;
	
	/**
	 * Constant reflecting the maximal period time for a periodic movement of a Shark.
	 * 
	 * @return	| result == 4.0
	 */
	public static final double MAX_PERIOD_TIME = 4.0;
	
	/**
	 * Constant reflecting the initial vertical velocity for a Shark when jumping.
	 * 
	 * @return	| result == 2.0
	 */
	public static final double VELOCITY_Y_INIT_JUMPING = 2.0;
	
	/**
	 * Constant reflecting the minimal acceleration of a Shark while diving in water.
	 * 
	 * @return	| result == - 0.2
	 */
	public static final double ACCELERATION_DIVING = - 0.2;
	
	/**
	 * Constant reflecting the maximal acceleration of a Shark while rising in water.
	 * 
	 * @return	| result == 0.2
	 */
	public static final double ACCELERATION_RISING = 0.2;
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Shark.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Shark's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Shark's bottom left pixel.
	 * @param 	velocityXInit
	 * 				The initial horizontal velocity of a Shark.
	 * @param 	velocityYInit
	 * 				The initial vertical velocity of a Shark.
	 * @param 	velocityXMax
	 * 				The maximal horizontal velocity of a Shark.
	 * @param 	accelerationXInit
	 * 				The initial horizontal acceleration of a Shark.
	 * @param 	sprites
	 * 				The array of sprite images for a Shark.
	 * @param 	nbHitPoints
	 * 				The number of hit points of a Shark.
	 * @param	maxNbHitPoints
	 * 				The maximal number of hit points of a Shark.
	 * @pre		| Array.getLength(sprites) == 2	 * 
	 * @post	| new.getNbNonJumpingPeriods == 0
	 * @post	| new.getCurrentPeriodTime() == timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME)
	 * @post	| new.isJumping == false
	 * @effect	| super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, 
	 * 					sprites,nbHitPoints, maxNbHitPoints)
	 * @effect	| startMove( this.getRandomOrientation() )
	 * @effect	| startDiveRise()
	 * @effect 	| configureTerrain()
	 * TODO: documentation program
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
	public Shark(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
			  	double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
			  	int maxNbHitPoints, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, sprites,
			  nbHitPoints, maxNbHitPoints, program);
		
		this.setNbNonJumpingPeriods(0);
		this.setJumping(false);
	
		this.configureTerrain();
		
	}
	
	/**
	 * TODO: documentation
	 * 
	 * @param pixelLeftX
	 * @param pixelBottomY
	 * @param velocityXInit
	 * @param velocityYInit
	 * @param velocityXMax
	 * @param accelerationXInit
	 * @param sprites
	 * @param nbHitPoints
	 * @param maxNbHitPoints
	 * @throws IllegalPositionXException
	 * @throws IllegalPositionYException
	 * @throws IllegalWidthException
	 * @throws IllegalHeightException
	 */
	public Shark(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
		  	double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints,
		  	int maxNbHitPoints)
		throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
	
		this(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, sprites,
			 nbHitPoints, maxNbHitPoints, null);
		
	}
	
	/**
	 * Initialize a Shark with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, number of hit points and maximal number of hit points.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Shark's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Shark's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for a Shark.
	 * @pre		| Array.getLength(sprites) == 2
	 * TODO: documentation program
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
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites, Program program) 
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{		
		this(pixelLeftX, pixelBottomY, 0.0, 0.0, 4.0, 1.5, sprites, 100, 100, program);
	}
	
	/**
	 * TODO: documentation
	 * 
	 * @param pixelLeftX
	 * @param pixelBottomY
	 * @param sprites
	 * @throws IllegalPositionXException
	 * @throws IllegalPositionYException
	 * @throws IllegalWidthException
	 * @throws IllegalHeightException
	 */
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites) 
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{		
		this(pixelLeftX, pixelBottomY, sprites, null);
	}
	
	/**
	 * Returns a string class name of the object, used for toString method in GameObject
	 */
	@Override
	public String getClassName() {
		return "Shark";
	}
	
	
	/******************************************************** TIMER ****************************************************/
	
	/**
	 * Return the current period time of a Shark.
	 * 
	 * @return	A double representing the current period time of a Shark.
	 */
	@Basic
	public double getCurrentPeriodTime(){
		return this.currentPeriodTime;
	}
	
	/**
	 * Set the current period time of a Shark.
	 * 
	 * @param 	newPeriodTime
	 * 				The new period time of a Shark.
	 * @post	| new.getCurrentPeriodTime() == newPeriodTime
	 */
	@Basic
	private void setCurrentPeriodTime( double newPeriodTime ){
		this.currentPeriodTime = newPeriodTime;
	}
	
	/**
	 * Variable registering the current random period time of a Shark.
	 */
	private double currentPeriodTime;
	
	/******************************************************* TERRAIN ***************************************************/
	
	/**
	 * Configure the terrain properties for a Shark.
	 * 
	 * @effect	| setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 6, 0.2, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 0, 0.2, false))
	 * @effect	| setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true))
	 */
	@Override
	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 6, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 0, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true));
		
	}
	
	/******************************************************** WORLD ****************************************************/
	
	/**
	 * Add the Shark to his World.
	 * 
	 * @post	| new.getWorld().hasAsGameObject(this) == true
	 */
	@Override
	protected void addToWorld(){
		this.getWorld().sharks.add(this);
	}
	
	/**
	 * Remove the Shark from the given World.
	 * 
	 * @param	world
	 * 				The World to remove the Shark from.
	 * @pre		| this != null && !this.hasWorld()
	 * @pre		| world.hasAsGameObject(this)
	 * @post	| world.hasAsGameObject(this) == false
	 */
	@Override
	protected void removeFromWorld(World world){
		assert this != null && !this.hasWorld();
		assert world.hasAsGameObject(this);
		
		world.sharks.remove(this);
	}
	
	/**
	 * Check whether or not the Shark has the given World as its World.
	 * 
	 * @param	world
	 * 				The World to check.
	 * @return	| result == ( Shark.getAllInWorld(world).contains(this) )
	 */
	@Override
	protected boolean hasAsWorld(World world){
		return Shark.getAllInWorld(world).contains(this);
	}
	
	/**
	 * Return the number of Sharks in the given World.
	 * 
	 * @param 	world
	 * 				The World to check the number of Sharks for.
	 * @return	| result == ( Shark.getAllInWorld(world).size() )
	 */
	public static int getNbInWorld(World world){
		return Shark.getAllInWorld(world).size();
	}
	
	/**
	 * Return all Sharks in the given World.
	 * 
	 * @param 	world
	 * 				The World to check.
	 * @return	A Hashset containing all Sharks in the given World.
	 */
	public static Set<Shark> getAllInWorld(World world){
		HashSet<Shark> sharksClone =  new HashSet<Shark>(world.sharks);
		return sharksClone;
	}
	
	/***************************************************** ACCELERATION ************************************************/
	
	/**
	 * Return the vertical acceleration of a Shark.
	 * 
	 * @return	A double that represents the vertical acceleration of a Shark.
	 */
	@Override @Basic @Raw @Immutable
	public double getAccelerationY() {
		return this.accelerationY;
	}
	
	/**
	 * Set the vertical acceleration of a Shark.
	 * 
	 * @param 	accelerationY
	 * 				A double that represents the desired vertical acceleration of this Shark.
	 * @post	| if ( Double.isNaN(accelerationY) )
	 * 			| 	then new.getAccelerationY() == 0
	 * 			| else
	 * 			| 	new.getAccelerationY() == accelerationY
	 */
	@Basic @Raw
	protected void setAccelerationY(double accelerationY) {
		if (Double.isNaN(accelerationY)){
			this.accelerationY = 0;
		} else
			this.accelerationY = accelerationY;
	}
	
	/**
	 * Variable registering the vertical acceleration of a Shark.
	 */
	private double accelerationY;
	
	/**
	 * Return the random acceleration of a Shark for diving or rising.
	 * 
	 * @return	A double representing the random acceleration of a Shark for diving or rising.
	 */
	@Basic
	public double getRandomAcceleration(){
		return this.randomAcceleration;
	}
	
	/**
	 * Set the random acceleration of a Shark for diving or rising.
	 * 
	 * @param 	newRandomAcceleration
	 * 				The new random acceleration of a Shark for diving or rising.
	 * @post	| new.getRandomAcceleration() == newRandomAcceleration
	 */
	@Basic
	private void setRandomAcceleration( double newRandomAcceleration ){
		this.randomAcceleration = newRandomAcceleration;
	}
	
	/**
	 * Variable registering the random acceleration of a Shark for diving or rising.
	 */
	private double randomAcceleration;
	
	/************************************************* JUMPING AND FALLING *********************************************/

	/**
	 * Return whether or not a Shark is jumping at the moment.
	 * 
	 * @return	result = ( this.jumping)
	 */
	@Override @Basic
	public boolean isJumping(){
		return this.jumping;
	}
	
	/**
	 * Set the jumping status of a Shark.
	 * 
	 * @param 	jumping
	 * 				The new jumping status of a Shark.
	 * @post	| new.isJumping() == jumping
	 */
	@Basic
	private void setJumping(boolean jumping){
		this.jumping = jumping;
	}
	
	/**
	 * Variable registering the jumping status of a Shark.
	 */
	private boolean jumping;
	
	/**
	 * Make a Slime start jumping.
	 * 
	 * @effect	| setVelocityY( this.getVelocityYInit() )
	 * @post	| new.isJumping() == true
	 */
	@Override
	public void startJump() {
		this.setVelocityY( VELOCITY_Y_INIT_JUMPING );
		this.setJumping(true);
	}
	
	/**
	 * Make a Slime stop jumping.
	 * 
	 * @effect	| if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 ))
	 * 			|	then this.setVelocity(0)
	 * @post	| new.isJumping() == false
	 */
	@Override
	public void endJump(){
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}
		this.setJumping(false);
	}	

	/************************************************** DIVING AND RISING **********************************************/
	
	/**
	 * Make a Shark start diving or rising in water.
	 * 
	 * @effect	| setRandomAcceleration( ACCELERATION_DIVING + (ACCELERATION_RISING - ACCELERATION_DIVING)*Math.random())
	 * @effect	| setVelocityY( Math.signum( this.getRandomAcceleration() )*this.getVelocityYInit() )
	 * @effect	| setAccelerationY( this.getRandomAcceleration() )
	 */
	public void startDiveRise(){
		this.setRandomAcceleration( ACCELERATION_DIVING + (ACCELERATION_RISING - ACCELERATION_DIVING)*Math.random() );
		this.setVelocityY( Math.signum( this.getRandomAcceleration() )*this.getVelocityYInit() );
		this.setAccelerationY( this.getRandomAcceleration() ); 
	}
	
	/**
	 * Make a Shark stop diving or rising in water.
	 * 
	 * @effect	| setVelocity(0)
	 * @effect	| setAccelerationY(0)
	 */
	public void endDiveRise(){
		this.setVelocityY(0);
		this.setAccelerationY(0);
	}
	
	/**
	 * Check if a Shark has been out of the water.
	 * 
	 * @return	result == ( this.hasBeenOutWater )
	 */
	@Basic
	public boolean getHasBeenOutWater(){
		return this.hasBeenOutWater;
	}
	
	/**
	 * Set if a Shark has been out of the water.
	 * 
	 * @param 	hasBeenOutWater
	 * 				A boolean representing if a Shark has been out of the water.
	 * @post	| new.getHasBeenOutWater() == hasBeenOutWater
	 */
	private void setHasBeenOutWater(boolean hasBeenOutWater){
		this.hasBeenOutWater = hasBeenOutWater;
	}
	
	/**
	 * Variable registering if a Shark has been out of the water.
	 */
	private boolean hasBeenOutWater = false;
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Initiate a new periodic movement, if needed, and update the Shark's horizontal and vertical position and velocity
	 * for the given time interval along with his gravitational acceleration.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then this.periodicMovement();
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then this.getTimer().setSinceLastPeriod(0)
	 * @post 	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then this.getCurrentPeriodTime() == timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME)
	 * @effect	| adjustGravitationalAcceleration()
	 * @effect	| updatePositionX(dt)
	 * @effect	| updateVelocityX(dt)
	 * @effect	| updatePositionY(dt)
	 * @effect	| updateVelocityY(dt)
	 */
	@Override
	protected void doMove(double dt){

		/* Initialize periodic movement */
		if (!this.isInitializedPeriodicMovement()){
			this.setCurrentPeriodTime( timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME) );
			this.startMove(this.getRandomOrientation());
			this.startDiveRise();
			this.setInitializedPeriodicMovement(true);
		}
		
		/* Periodic movement */
		if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime){
			this.periodicMovement();
					
			this.getTimer().setSinceLastPeriod(0);		
			this.setCurrentPeriodTime( timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME) );
		}
		
		/* Adjust gravitational acceleration */
		this.adjustGravitationalAcceleration();
		
		/* Update position and velocity */
		this.update(dt);
		
	}
	
	// TODO: commentary
	public void doMoveProgram(double dt){
		
		/* Advance Program */
		this.advanceProgram();
		
		/* Adjust gravitational acceleration */
		this.adjustGravitationalAcceleration();
		
		/* Update position and velocity */
		this.update(dt);
		
	}
	
	/**
	 * Start the periodic movement of a Shark.
	 * 
	 * @effect	| endMove(this.getOrientation())
	 * @effect	| if ( this.isJumping())
	 * 			|	then this.endJump()
	 * @effect	| if ( ! this.isJumping())
	 * 			|	then new.getNbNonJumpingPeriods == this.getNbNonJumpingPeriods() + 1
	 * @effect	| if ( ! this.isJumping())
	 * 			|	then this.endDiveRise()
	 * @effect	| startMove(this.getRandomOrientation())
	 * @effect	| if ( (this.getNbNonJumpingPeriods() >= 4) && (Math.random() < 0.5) )
	 * 			|	then this.startJump()
	 * @effect	| if ( (this.getNbNonJumpingPeriods() >= 4) && (Math.random() < 0.5) )
	 * 			|	then new.getNbNonJumpingPeriods() == 0
	 * @effect	| if ( ( (!this.getNbNonJumpingPeriods() >= 4) || (Math.random() >= 0.5) ) 
	 * 			|	   && this.isSubmergedIn(Terrain.WATER) )
	 * 			|	then this.startDiveRise()
	 */
	private void periodicMovement(){
		
		this.endMove(this.getOrientation());
		
		if (this.isJumping()){
			this.endJump();
		} else {
			this.setNbNonJumpingPeriods( this.getNbNonJumpingPeriods() + 1 );
			this.endDiveRise();
		}	
		
		this.startMove(this.getRandomOrientation());
		
		if ((this.getNbNonJumpingPeriods() >= 4) && (Math.random() < 0.5)){
			this.startJump();
			this.setNbNonJumpingPeriods(0);
		} else if (this.isSubmergedIn(Terrain.WATER)) {
			this.startDiveRise();
		}
		
	}
	
	/**
	 * Return the number of non-jumping periods of a Shark.
	 * 
	 * @return	The number of non-jumping periods of a Shark.
	 */
	@Basic
	public int getNbNonJumpingPeriods(){
		return this.nbNonJumpingPeriods;
	}
	
	/**
	 * Set the number of non-jumping periods of a Shark.
	 * 
	 * @param 	newNbNonJumpingPeriods
	 * 				The new number of non-jumping periods.
	 * @post	| new.getNbNonJumpingPeriods() == newNbNonJumpingPeriods
	 */
	private void setNbNonJumpingPeriods( int newNbNonJumpingPeriods ){
		this.nbNonJumpingPeriods = newNbNonJumpingPeriods;
	}
	
	/**
	 * Variable registering the number of non-jumping periods of a Shark.
	 */
	private int nbNonJumpingPeriods;
	
	/**
	 * Adjust the vertical acceleration of a Shark according to whether or not he's in water.
	 * 
	 * @effect	| if ( ! this.isSubmergedIn(Terrain.WATER) )
	 * 			|	then this.setAccelerationY(ACCELERATION_Y)
	 * @effect	| if ( ! this.isSubmergedIn(Terrain.WATER) )
	 * 			|	then this.setHasBeenOutWater(true)
	 * @effect	| if ( this.isSubmergedIn(Terrain.WATER) && this.getHasBeenOutWater() )
	 * 			|	then this.endDiveRise()
	 * @effect	| if ( this.isSubmergedIn(Terrain.WATER) && this.getHasBeenOutWater() )
	 * 			|	then this.setHasBeenOutWater(false)
	 */
	public void adjustGravitationalAcceleration(){
		
		if (!this.isSubmergedIn(Terrain.WATER)){
			this.setAccelerationY(ACCELERATION_Y);
			this.setHasBeenOutWater(true);
		} else if ( this.getHasBeenOutWater() ){
			this.endDiveRise();
			this.setHasBeenOutWater(false);
		}
		
	}
	
	/**
	 * Update a Shark's vertical position according to the given dt.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	| super.updatePositionY(dt)
	 * @effect	| if (!this.isSubmergedIn(Terrain.WATER) && !this.isJumping() && !this.getHasBeenOutWater())
	 * 			|	then new.setPositionY( this.getPositionY() )
	 * @effect	| if (!this.isSubmergedIn(Terrain.WATER) && !this.isJumping() && !this.getHasBeenOutWater())
	 * 			|	then this.endDiveRise()
	 */
	@Override
	public void updatePositionY(double dt){
		
		double oldPositionY = this.getPositionY();
		super.updatePositionY(dt);
		
		if (!this.isSubmergedIn(Terrain.WATER) && !this.isJumping() && !this.getHasBeenOutWater()){
			this.setPositionY(oldPositionY);
			this.endDiveRise();
		}
		
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Process the horizontal collision of a Shark.
	 * 
	 * @note	As an optional implementation, a Shark changes his direction when he collides.
	 * 
	 * @effect	| changeDirection()
	 */
	@Override
	public void processHorizontalCollision() {
		this.changeDirection();
	}
	
	/**
	 * Return all impassable Game objects for a Shark.
	 * 
	 * @return	A Hashset that contains the Mazub and all Buzams, Slimes and Sharks in the Shark's world.
	 */
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>();
		allImpassableGameObjects.add(Mazub.getInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Buzam.getAllInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Slime.getAllInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Shark.getAllInWorld(this.getWorld()));
		return allImpassableGameObjects;
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Process an overlap of a Shark with a Mazub.
	 * 
	 * @param	mazub
	 * 				The Mazub with whom this Slime overlaps.
	 * @effect	If the given Mazub isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 		 	given Mazub with his bottom perimeter, make the Shark take damage.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.takeDamage(MAZUB_DAMAGE)
	 * @effect	If the given Mazub isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 	 		given Mazub with his bottom perimeter, set the immunity status of the Shark to true.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.setImmune(true)
	 * @effect	If the given Mazub isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 	 		given Mazub with his bottom perimeter, set the Shark's time since an enemy collision to 0.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.getTimer().setSinceEnemyCollision(0)
	 */
	@Override
	public void processMazubOverlap(Mazub mazub) {
		if(!mazub.isKilled() && !this.isImmune()){
			this.takeDamage(MAZUB_DAMAGE);
			this.setImmune(true);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
	/**
	 * Process an overlap of a Shark with a Slime.
	 * 
	 * @param	slime
	 * 				The Slime with which this Shark overlaps.
	 * @effect	If the given Slime isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 		 	given Slime with his bottom perimeter, make the Shark take damage.
	 * 			| if ( !slime.isKilled() && !this.isImmune() && !this.doesOverlapWith(slime, Orientation.BOTTOM) )
	 * 			|	then this.takeDamage(SLIME_DAMAGE)
	 * @effect	If the given Slime isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 	 		given Slime with his bottom perimeter, set the immunity status of the Shark to true.
	 * 			| if ( !slime.isKilled() && !this.isImmune() && !this.doesOverlapWith(slime, Orientation.BOTTOM) )
	 * 			|	then this.setImmune(true)
	 * @effect	If the given Slime isn't killed, this Shark isn't immune and this Shark doesn't overlap with the
	 * 	 		given Slime with his bottom perimeter, set the Shark's time since an enemy collision to 0.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.getTimer().setSinceEnemyCollision(0)
	 */
	@Override
	public void processSlimeOverlap(Slime slime){
		if(!slime.isKilled()  && !this.isImmune()){
			this.takeDamage(SLIME_DAMAGE);
			this.setImmune(true);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
	/**
	 * Process an overlap of a Shark with another Shark.
	 * 
	 * @param	shark
	 * 				The other Shark with which this Shark overlaps.
	 */
	@Override
	public void processSharkOverlap(Shark shark){
		
	}
	
	/**
	 * Process an overlap of a Shark with a Plant.
	 * 
	 * @param	plant
	 * 				The Plant with which this Shark overlaps.
	 */
	@Override
	public void processPlantOverlap(Plant plant) {

	}
	
}
