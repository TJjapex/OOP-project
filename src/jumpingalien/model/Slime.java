package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.terrain.TerrainProperties;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Slimes, enemy characters in the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 * @note Class invariants of the class GameObject also apply to this subclass.
 * @invar	| hasProperSchool()
 */
public class Slime extends GameObject {

	/****************************************************** CONSTANTS **************************************************/
	
	/**
	 * Constant reflecting the amount of damage a Slime should take when it overlaps with a Mazub.
	 * 
	 * @return	| result == 50
	 */
	private static final int MAZUB_DAMAGE = 50;
	
	/**
	 * Constant reflecting the amount of damage a Slime should take when it overlaps with a Shark.
	 * 
	 * @return	| result == 50
	 */
	private static final int SHARK_DAMAGE = 50;

	/**
	 * Constant reflecting the minimal period time for a periodic movement of a Slime.
	 * 
	 * @return	| result == 2.0
	 */
	public static final double MIN_PERIOD_TIME = 2.0;
	
	/**
	 * Constant reflecting the maximal period time for a periodic movement of a Slime.
	 * 
	 * @return	| result == 6.0
	 */
	public static final double MAX_PERIOD_TIME = 6.0;
	
	/**
	 * Constant reflecting the mutual school damage of a Slime in a school.
	 * 
	 * @return	| result == 1
	 */
	public static final int MUTUAL_SCHOOL_DAMAGE = 1;
	
	/**
	 * Constant reflecting the damage that a Slime takes upon switching schools per Slime member of his old School
	 * and gives to each Slime member of his new School.
	 * 
	 * @return	| result == 1
	 */
	public static final int SWITCH_SCHOOL_DAMAGE = 1;
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Slime.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Slime's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Slime's bottom left pixel.
	 * @param 	velocityXInit
	 * 				The initial horizontal velocity of a Slime.
	 * @param 	velocityYInit
	 * 				The initial vertical velocity of a Slime.
	 * @param 	velocityXMax
	 * 				The maximal horizontal velocity of a Slime.
	 * @param 	accelerationXInit
	 * 				The initial horizontal acceleration of a Slime.
	 * @param 	sprites
	 * 				The array of sprite images for a Slime.
	 * @param 	school
	 * 				The school to which the Slime belongs upon initialization.
	 * @param 	nbHitPoints
	 * 				The number of hit points of a Slime.
	 * @param	maxNbHitPoints
	 * 				The maximal number of hit points of a Slime.
	 * @pre		| Array.getLength(sprites) == 2
	 * @post	| new.getCurrentPeriodTime() == timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME)
	 * @effect	| super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, 
	 * 					sprites,nbHitPoints, maxNbHitPoints)
	 * @effect	| setSchoolTo(school)
	 * @effect	| startMove( this.getRandomOrientation() )
	 * @effect 	| configureTerrain()
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
	@Raw
	public Slime(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
		  		 double velocityXMax, double accelerationXInit, Sprite[] sprites, School school,
		  		 int nbHitPoints, int maxNbHitPoints)
		  	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, sprites,
			  nbHitPoints, maxNbHitPoints);

		this.setSchoolTo(school);
		
		this.setCurrentPeriodTime( timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME) );
		this.startMove(this.getRandomOrientation());
		
		this.configureTerrain();
		
	}
	
	/**
	 * Initialize a Slime with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, number of hit points and maximal number of hit points.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of a Slime's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of a Slime's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for a Slime.
	 * @param 	school
	 * 				The school to which the Slime belongs upon initialization.
	 * @pre		| Array.getLength(sprites) == 2
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
	@Raw
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{	
		this(pixelLeftX, pixelBottomY, 1.0, 0.0, 2.5, 0.7, sprites, school, 100, 100);
	}
	
	/******************************************************** TIMER ****************************************************/
	
	/**
	 * Return the current period time of a Slime.
	 * 
	 * @return	A double representing the current period time of a Slime.
	 */
	@Basic
	public double getCurrentPeriodTime(){
		return this.currentPeriodTime;
	}
	
	/**
	 * Set the current period time of a Slime.
	 * 
	 * @param 	newPeriodTime
	 * 				The new period time of a Slime.
	 * @post	| new.getCurrentPeriodTime() == newPeriodTime
	 */
	@Basic
	private void setCurrentPeriodTime( double newPeriodTime ){
		this.currentPeriodTime = newPeriodTime;
	}
	
	/**
	 * Variable registering the current random period time of a Slime.
	 */
	private double currentPeriodTime;
	
	/******************************************************* TERRAIN ***************************************************/
	
	/**
	 * Configure the terrain properties for a Slime.
	 * 
	 * @effect 	| setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false))
	 * @effect 	| setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false))
	 * @effect 	| setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false))
	 * @effect 	| setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true))
	 */
	@Override
	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true));
		
	}
	
	/******************************************************** WORLD ****************************************************/
	
	/**
	 * Add the Slime to his World.
	 * 
	 * @post	| new.getWorld().hasAsGameObject(this) == true
	 */
	@Override @Raw
	protected void addToWorld(){
		this.getWorld().slimes.add(this);
	}
	
	/**
	 * Remove the Slime from the given World.
	 * 
	 * @param	world
	 * 				The World to remove the Slime from.
	 * @pre		| this != null && !this.hasWorld()
	 * @pre		| world.hasAsGameObject(this)
	 * @post	| world.hasAsGameObject(this) == false
	 */
	@Override @Raw
	protected void removeFromWorld(World world){
		assert this != null && !this.hasWorld();
		assert world.hasAsGameObject(this);
		
		world.slimes.remove(this);
	}

	/**
	 * Check whether or not the Slime has the given World as its World.
	 * 
	 * @param	world
	 * 				The World to check.
	 * @return	| result == ( Slime.getAllInWorld(world).contains(this) )
	 */
	@Override
	protected boolean hasAsWorld(World world){
		return Slime.getAllInWorld(world).contains(this);
	}
	
	/**
	 * Return the number of Slimes in the given World.
	 * 
	 * @param 	world
	 * 				The World to check the number of Slimes for.
	 * @return	| result == ( Slime.getAllInWorld(world).size() )
	 */
	public static int getNbInWorld(World world){
		return Slime.getAllInWorld(world).size();
	}
	
	/**
	 * Return all Slimes in the given World.
	 * 
	 * @param 	world
	 * 				The World to check.
	 * @return	A Hashset containing all Slimes in the given World.
	 */
	public static Set<Slime> getAllInWorld(World world){
		HashSet<Slime> slimesClone =  new HashSet<Slime>(world.slimes);
		return slimesClone;
	}
	
	/******************************************************** SCHOOL ***************************************************/
	
	/**
	 * Return the School of a Slime.
	 * 
	 * @return	The School to which a Slime belongs.
	 */
	@Basic
	public School getSchool() {
		return this.school;
	}

	/**
	 * Checks if a Slime belongs to a proper School.
	 * 
	 * @return 	result == ( this.isTerminated() || this.hasSchool() )
	 */
	@Raw
	public boolean hasProperSchool(){
		return this.isTerminated() || this.hasSchool();
	}
	
	/**
	 * Checks if a Slime belongs to a School.
	 * 
	 * @return 	result == ( this.getSchool() != null )
	 */
	@Raw
	public boolean hasSchool(){
		return this.getSchool() != null;
	}

	/**
	 * Set the School of a Slime.
	 * 
	 * @param 	school
	 * 				The School to which a Slime must belong.
	 * @post	new.getSchool() == school
	 */
	@Basic
	private void setSchool(School school) {
		this.school = school;
	}
	
	/**
	 * Make a relation between a Slime and the desired School.
	 * 
	 * @param 	school
	 * 				The School to which a Slime must belong.
	 * @throws 	IllegalArgumentException
	 * 				| ( ! canHaveAsSchool(school) ) || ( ! school.canHaveAsSlime(this) )
	 * @effect	| setSchool(school)
	 * @effect	| school.addAsSlime(this)
	 */
	@Model
	private void setSchoolTo(School school) throws IllegalArgumentException{ // In ownable voorbeeld public ?	
		
		if(!canHaveAsSchool(school))
			throw new IllegalArgumentException("Cannot have given school as school!");
		if (!school.canHaveAsSlime(this))
			throw new IllegalArgumentException("Given school cannot have this slime as member!");
		
		this.setSchool(school);
		school.addAsSlime(this);
	}
	
	/**
	 * Tear down the relation between a Slime and his School.
	 * 
	 * @effect	| if (this.hasSchool())
	 * 			|	then this.getSchool().removeAsSlime(this)
	 * @effect	| if (this.hasSchool())
	 * 			|	then this.setSchool(null)
	 */
	@Model
	private void unsetSchool(){
		if(this.hasSchool()){
			School formerSchool = this.getSchool();
			this.setSchool(null);
			formerSchool.removeAsSlime(this);
		}
	}
	
	/**
	 * Checks whether or not a Slime can have school as his School.
	 * 
	 * @param 	school
	 * 				The School to check.
	 * @return 	result == ( ! this.hasSchool() ) && ( ! this.isTerminated() ) 
	 */
	@Raw
	public boolean canHaveAsSchool(School school){
		return !this.hasSchool() && !this.isTerminated();
	}	
	
	/**
	 * Switch a Slime to a new School.
	 * 
	 * @param 	newSchool
	 * 				The new School to which a Slime must belong.
	 * @effect	| if (this.getSchool() != newSchool)
	 * 			| 	then for slime in this.getSchool().getAllSlimes()
	 * 			|			if (!slime.equals(this)
	 * 			|				then slime.modifyNbHitPoints(SWITCH_SCHOOL_DAMAGE)
	 * @effect	| if (this.getSchool() != newSchool)
	 * 			| 	then this.modifyNbHitPoints( - SWITCH_SCHOOL_DAMAGE * this.getSchool().getNbSlimes()
	 * 			|								 + SWITCH_SCHOOL_DAMAGE * newSchool.getNbSlimes() )
	 * @effect	| if (this.getSchool() != newSchool)
	 * 			|	then this.unsetSchool()
	 * @effect	| if (this.getSchool() != newSchool)
	 * 			|	then for slime in newSchool.getAllSlimes()
	 * 			|			slime.modifyNbHitPoints( - SWITCH_SCHOOL_DAMAGE )
	 * @effect	| if (this.getSchool() != newSchool)
	 * 			|	then this.setSchoolTo(newSchool)
	 */
	public void switchSchool(School newSchool){
		
		if ( this.getSchool() != newSchool) {
			
			for (Slime slime: this.getSchool().getAllSlimes()){
				if (!slime.equals(this))
					slime.modifyNbHitPoints(SWITCH_SCHOOL_DAMAGE);
			}
			this.modifyNbHitPoints( - SWITCH_SCHOOL_DAMAGE * this.getSchool().getNbSlimes() );
			
			this.unsetSchool();
			
			for (Slime slime: newSchool.getAllSlimes()){
				slime.modifyNbHitPoints( - SWITCH_SCHOOL_DAMAGE );
			}
			this.modifyNbHitPoints( SWITCH_SCHOOL_DAMAGE * newSchool.getNbSlimes() );
			
			this.setSchoolTo(newSchool);
		}

	}
	
	/**
	 * Variable registering the School to which a Slime belongs at the moment.
	 */
	private School school;
	
	/****************************************************** HIT POINTS *************************************************/
	
	/**
	 * Make a Slime take damage.
	 * 
	 * @param	damageAmount
	 * 				The amount of damage that a Slime needs to take.
	 * @effect	| modifyNbHitPoints( - damageAmount)
	 * @effect	| if ( this.getSchool() != null)
	 * 			|	then this.mutualDamage()
	 */
	@Override
	protected void takeDamage(int damageAmount){
		this.modifyNbHitPoints( - damageAmount );
		this.mutualDamage();
	}
	
	/**
	 * Make the other Slimes of a school take mutual damage because one Slime in the School took some damage.
	 * 
	 * @effect	| for Slime in this.getSchool().getAllSlimes()
	 * 			|	if ( ! slime.equals(this) )
	 * 			| 		then slime.modifyNbHitPoints( - MUTUAL_SCHOOL_DAMAGE )
	 */
	protected void mutualDamage(){
		for (Slime slime: this.getSchool().getAllSlimes()){
			if (!slime.equals(this))
				slime.modifyNbHitPoints( - MUTUAL_SCHOOL_DAMAGE );
		}
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Initiate a new periodic movement, if needed, and update the Slime's horizontal and vertical position and velocity
	 * for the given time interval.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @post 	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then new.getCurrentPeriodTime() == timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME)
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then this.periodicMovement();
	 * @effect	| if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime)
	 * 			|	then this.getTimer().setSinceLastPeriod(0)
	 * @effect	| updatePositionX(dt)
	 * @effect	| updateVelocityX(dt)
	 * @effect	| updatePositionY(dt)
	 * @effect	| updateVelocityY(dt)
	 */
	@Override
	protected void doMove(double dt){
		
		/* Periodic movement */
		if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime){
			
			this.periodicMovement();
			
			this.getTimer().setSinceLastPeriod(0);
			this.setCurrentPeriodTime( timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME) );
		}
		
		/* Horizontal */
		this.updatePositionX(dt);
		this.updateVelocityX(dt);

		/* Vertical */
		this.updatePositionY(dt);
		this.updateVelocityY(dt);	
		
	}
	
	/**
	 * Start the periodic movement of a Plant.
	 * 
	 * @effect	| endMove(this.getOrientation())
	 * @effect	| startMove(this.getRandomOrientation())
	 */
	private void periodicMovement(){
		this.endMove(this.getOrientation());
		this.startMove(this.getRandomOrientation());
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Process the horizontal collision of a Slime.
	 * 
	 * @note	As an optional implementation, a Slime changes his direction when he collides.
	 * 
	 * @effect	| changeDirection()
	 */
	@Override
	protected void processHorizontalCollision() {		
		this.changeDirection();
	}
	
	/**
	 * Return all impassable Game objects for a Slime.
	 * 
	 * @return	A Hashset that contains all Mazubs, Slimes and Sharks in the Slime's world.
	 */
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		assert hasProperWorld();
		
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(Mazub.getAllInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Slime.getAllInWorld(this.getWorld()));
		allImpassableGameObjects.addAll(Shark.getAllInWorld(this.getWorld()));
		return allImpassableGameObjects;
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Process an overlap of a Slime with a Mazub.
	 * 
	 * @param	mazub
	 * 				The Mazub with whom this Slime overlaps.
	 * @effect	If the given Mazub isn't killed, this Slime isn't immune and this Slime doesn't overlap with the
	 * 		 	given Mazub with his bottom perimeter, make the Slime take damage.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.takeDamage(MAZUB_DAMAGE)
	 * @effect	If the given Mazub isn't killed, this Slime isn't immune and this Slime doesn't overlap with the
	 * 		 	given Mazub with his bottom perimeter, set the immunity status of the Slime to true.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.setImmune(true)
	 * @effect	If the given Mazub isn't killed, this Slime isn't immune and this Slime doesn't overlap with the
	 * 		 	given Mazub with his bottom perimeter, set the Slime's time since an enemy collision to 0.
	 * 			| if ( !mazub.isKilled() && !this.isImmune() && !this.doesOverlapWith(mazub, Orientation.BOTTOM) )
	 * 			|	then this.getTimer().setSinceEnemyCollision(0)
	 */
	@Override
	protected void processMazubOverlap(Mazub mazub){
		if(!mazub.isKilled() && !this.isImmune()){
			this.takeDamage(MAZUB_DAMAGE);
			this.setImmune(true);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}	
	
	/**
	 * Process an overlap of a Slime with another Slime.
	 * 
	 * @param	slime
	 * 				The other Slime with which this Slime overlaps.
	 * @effect	If the given Slime is not equal to this Slime and the number of Slimes in the school of the 
	 * 			given Slime is greater than the number of Slimes in the school of this Slime, switch this Slime
	 * 			to the School of the given Slime.
	 * 			| if ( slime != this && slime.getSchool().getNbSlimes() > this.getSchool().getNbSlimes() )
	 * 			|	then this.switchSchool( slime.getSchool() )
	 * @effect	If the given Slime is not equal to this Slime and the number of Slimes in the school of the 
	 * 			given Slime is smaller than the number of Slimes in the school of this Slime, switch the given
	 * 			Slime to the School of this Slime.
	 * 			| if ( slime != this && slime.getSchool().getNbSlimes() < this.getSchool().getNbSlimes() )
	 * 			|	then slime.switchSchool( this.getSchool() )
	 */
	@Override
	protected void processSlimeOverlap(Slime slime){
		if(slime != this){
			if ( slime.getSchool().getNbSlimes() > this.getSchool().getNbSlimes() ){
				this.switchSchool( slime.getSchool() );
			} else if ( slime.getSchool().getNbSlimes() < this.getSchool().getNbSlimes() ) {
				slime.switchSchool( this.getSchool() );
			}
		}
	}
	
	/**
	 * Process an overlap of a Slime with a Shark.
	 * 
	 * @param	shark
	 * 				The Shark with which this Slime overlaps.
	 * @effect	If the given Shark isn't killed, this Slime isn't immune and this Slime doesn't overlap with 
	 * 			the Shark with his bottom perimeter, make the Slime take damage.
	 * 			| if ( !shark.isKilled() && !this.isImmune() && !this.doesOverlapWith(shark, Orientation.BOTTOM) )
	 * 			|	then this.takeDamage(SHARK_DAMAGE)
	 * @effect	If the given Shark isn't killed, this Slime isn't immune and this Slime doesn't overlap with 
	 * 			the Shark with his bottom perimeter, set the immunity status of the Slime to true.
	 * 			| if ( !shark.isKilled() && !this.isImmune() && !this.doesOverlapWith(shark, Orientation.BOTTOM) )
	 * 			|	then this.setImmune(true)
	 * @effect	If the given Shark isn't killed, this Slime isn't immune and this Slime doesn't overlap with 
	 * 			the Shark with his bottom perimeter, set the Slime's time since an enemy collision to 0.
	 * 			| if ( !shark.isKilled() && !this.isImmune() && !this.doesOverlapWith(shark, Orientation.BOTTOM) )
	 * 			|	then this.getTimer().setSinceEnemyCollision(0)
	 */
	@Override
	protected void processSharkOverlap(Shark shark){
		if(!shark.isKilled() && !this.isImmune()){
			this.takeDamage(SHARK_DAMAGE);
			this.setImmune(true);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
	/**
	 * Process an overlap of a Slime with a Plant.
	 * 
	 * @param	plant
	 * 				The Plant with which this Slime overlaps.
	 */
	@Override
	protected void processPlantOverlap(Plant plant) {
		
	}

	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Terminate a Slime.
	 * 
	 * @effect	Break the relation of the Slime with his World.
	 * 			| this.unsetWorld()
	 * @effect	Break the relation of the Slime with his School.
	 * 			| this.unsetSchool()
	 * @post	| new.isTerminated == true
	 */
	@Override
	protected void terminate(){
		this.unsetWorld();
		this.unsetSchool();
		
		this.terminated = true;
	}
	
}
