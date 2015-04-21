package jumpingalien.model;



import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.util.Sprite;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Slimes, enemy characters in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 * 
 * 
 * 
 * @invar
 * 			| hasProperSchool()
 */



public class Slime extends GameObject {
	
	/************************************************** GENERAL ***********************************************/
	
	public static final double MIN_PERIOD_TIME = 2.0;
	public static final double MAX_PERIOD_TIME = 6.0;
	
	private double currentPeriodTime = timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME);
	
	public static final int MUTUAL_SCHOOL_DAMAGE = 1;
	public static final int SWITCH_SCHOOL_DAMAGE = 1;
	
	@Override
	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, true));
		
	}
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 100 hit-points
	
	public Slime(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
		  	double velocityXMax, double accelerationXInit, Sprite[] sprites, School school, int nbHitPoints)
		  			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMax, accelerationXInit, sprites, nbHitPoints, 100);

//		if (this.canHaveAsSchool(school))
//			school.addAsSlime(this);
		this.setSchoolTo(school);
		
		this.startMove(this.getRandomOrientation());
		
		this.configureTerrain();
		
	}
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{	
		this(pixelLeftX, pixelBottomY, 1.0, 0.0, 2.5, 0.7, sprites, school, 100);
	}
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	
	
	/*************************************************** MOVING ***********************************************/
	
	
	
	/*************************************************** FALLING **********************************************/
	
	
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	
	
	/*************************************************** ANIMATION ********************************************/
	
	
	
	/*************************************************** HIT-POINTS *******************************************/
	
	// * lose 50 hit-points when making contact with Mazub or Shark
	// * Slimes lose hit-points upon touching water/magma (same as Mazub)
	
	@Override
	protected void takeDamage(int damageAmount){
		this.decreaseNbHitPoints(damageAmount);
		if ( this.getSchool() !=  null )
			this.mutualDamage();
	}
	
	protected void mutualDamage(){
		for (Slime slime: this.getSchool().getAllSlimes()){
			if (!slime.equals(this))
				slime.decreaseNbHitPoints(MUTUAL_SCHOOL_DAMAGE);
		}
	}
		
	/**************************************************** MOVEMENT ********************************************/
	
	// * move randomly to the left or right
	// * movement periods have a duration of 2s to 6s
	// * do not attack each other but block each others' movement
	// * Plants do not block Slimes	
	
	@Override
	public void doMove(double dt){
		if( this.doesCollide())
			throw new IllegalStateException(" Colission before movement! ");	
		

		// Randomized movement
		if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime){
			
			this.periodMovement();
			
			this.getTimer().setSinceLastPeriod(0);
			currentPeriodTime = timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME);
		}
		
		
		// Update horizontal position
		this.updatePositionX(dt);
		
		// Update horizontal velocity
		this.updateVelocityX(dt);

		// Update vertical position
		this.updatePositionY(dt);
		
		// Update vertical velocity
		this.updateVelocityY(dt);			
	}
	
	@Override
	protected void processHorizontalCollision() {		
		Orientation currentOrientation = this.getOrientation();		// dit gedeelte is eigenlijk niet gevraagd in de opdracht maar maakt de bewegingen wel logischer
		this.endMove(currentOrientation);
		if (currentOrientation != Orientation.RIGHT){
			this.startMove(Orientation.RIGHT);
		} else {
			this.startMove(Orientation.LEFT);
		}
	}
	
	private void periodMovement(){
		this.endMove(this.getOrientation());
		this.startMove(this.getRandomOrientation());
	}

	/***************************************************** SCHOOL *********************************************/
	
	// * Slimes are organised in groups, called schools: - each Slime belongs to exactly one school
	// 													 - Slimes may switch from one school to another
	//													 - when a Slime loses hit-points, all other Slimes of that
	//													   school lose 1 hit-point
	//													 - upon switching from school a Slime hands over 1 hit-point
	//													   to every Slime of the old school and every Slime of the 
	//													   new school hands over 1 hit-point to the joining Slime
	//													 - Slimes switch from school when they collide with a 
	//													   Slime of a larger school
	//													 - no more than 10 schools in a game world
	
	public School getSchool() {
		return this.school;
	}

	public boolean hasProperSchool(){
		return this.hasSchool();
	}
	
	public boolean hasSchool(){
		return this.getSchool() != null;
	}

	private void setSchool(School school) {
		assert canHaveAsSchool(school);
		this.school = school;
	}
	
	private void setSchoolTo(School school) throws IllegalArgumentException, IllegalStateException { // In ownable voorbeeld public ?		
		if (school == null || !school.canHaveAsSlime(this))
			throw new IllegalArgumentException("bad school");
		if (this.hasSchool())
			throw new IllegalStateException("Already has school!");
		setSchool(school);
		school.addAsSlime(this);
	}
	
	private void unsetSchool(){
		if(this.hasSchool()){
			School formerSchool = this.getSchool();
			this.school = null;
			formerSchool.removeAsSlime(this);
		}
	}
	
	public boolean canHaveAsSchool(School school){
		return !this.hasSchool() && school.canHaveAsSlime(this) && !this.isTerminated();
	}	
	
	public void switchSchool(School newSchool){
		
		if ( this.getSchool() != newSchool) {
			
			for (Slime slime: this.getSchool().getAllSlimes()){
				slime.increaseNbHitPoints(SWITCH_SCHOOL_DAMAGE);
			}
			this.decreaseNbHitPoints( SWITCH_SCHOOL_DAMAGE * this.getSchool().getNbSlimes() );
			
			this.unsetSchool();
			
			for (Slime slime: newSchool.getAllSlimes()){
				slime.decreaseNbHitPoints(SWITCH_SCHOOL_DAMAGE);
			}
			this.increaseNbHitPoints( SWITCH_SCHOOL_DAMAGE * newSchool.getNbSlimes() );
			
			this.setSchoolTo(newSchool);
		}

	}
	
	private School school;
	
	@Override
	protected void terminate(){
		this.getWorld().removeGameObject(this);
		this.setWorld(null); // TODO: vervangen door method World removeAs?

		this.unsetSchool();
		
		this.terminated = true;
	}
	
	@Override
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
	
	
	/****************************************************** OVERLAP PROCESSING *************************************************************/
	
	@Override
	protected void processMazubOverlap(Mazub mazub){
		if(!mazub.isKilled() && getTimer().getSinceEnemyCollision() > 0.6){
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}	
	
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
	
	@Override
	protected void processSharkOverlap(Shark shark){
		if(!shark.isKilled() && this.getTimer().getSinceEnemyCollision() > 0.6){
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
	@Override
	protected void processPlantOverlap(Plant plant) {
		
	}
	
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(this.getWorld().getAllMazubs());
		allImpassableGameObjects.addAll(this.getWorld().getAllSlimes());
		allImpassableGameObjects.addAll(this.getWorld().getAllSharks());
		return allImpassableGameObjects;
	}
	
}
