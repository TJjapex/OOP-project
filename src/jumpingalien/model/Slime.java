package jumpingalien.model;

import java.util.Random;

import javax.print.attribute.standard.MediaSize.Other;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

// All aspects shall ONLY be specified in a formal way.

/*
 * Algemene uitwerking:
 * 
 * schools:
 * 	Extra klasse die dan een variabele heeft 'member'. Als 
 */

/**
 * A class of Slimes, enemy characters in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Slime extends GameObject {
	
	/************************************************** GENERAL ***********************************************/
	
	public static final double MIN_PERIOD_TIME = 2.0;
	public static final double MAX_PERIOD_TIME = 6.0;
	
	private double currentPeriodTime = timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME);
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 100 hit-points
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school, int nbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, 1.0, 0.0, 2.5, 0.7, sprites, nbHitPoints, 100);

		if (this.canHaveAsSchool(school))
			school.addAsSlime(this);
		
		this.startMove(this.getRandomOrientation());
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2));
	}
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{	
		this(pixelLeftX, pixelBottomY, sprites, school, 100);
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
	public void takeDamage(int damageAmount){
		this.decreaseNbHitPoints(damageAmount);
		if ( this.getSchool() !=  null )
			this.getSchool().mutualDamage(this);
	}
	
	public void decreaseNbHitPoints(int damageAmount){
		System.out.println("slime taking damage:"+damageAmount);
		this.increaseNbHitPoints(-damageAmount);
	}
	
	
	/**************************************************** MOVEMENT ********************************************/
	
	// * move randomly to the left or right
	// * movement periods have a duration of 2s to 6s
	// * do not attack each other but block each others' movement
	// * Plants do not block Slimes	
	public void doMove(double dt){
		if( this.doesCollide())
			throw new IllegalStateException(" Colission before movement! ");	
		
		Orientation currentOrientation;
		
		// Randomized movement
		
		if (!this.isKilled()){
			if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime){
				
				this.endMove(this.getOrientation());
				this.startMove(this.getRandomOrientation());
				
				this.getTimer().setSinceLastPeriod(0);
				
				currentPeriodTime = timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME);
			}
				
			double oldPositionX = this.getPositionX();
			double oldPositionY = this.getPositionY();
			
			// Update horizontal position
			this.updatePositionX(dt);
			
			// Update horizontal velocity
			this.updateVelocityX(dt);
			
			this.processOverlap(); 
			
			if( this.doesCollide() ) {
				this.setPositionX(oldPositionX);
				currentOrientation = this.getOrientation();
				this.endMove(currentOrientation);
				if (currentOrientation != Orientation.RIGHT){
					this.startMove(Orientation.RIGHT);
				} else {
					this.startMove(Orientation.LEFT);
				}
			}
			
			// Update vertical position
			this.updatePositionY(dt);
			
			// Update vertical velocity
			this.updateVelocityY(dt);
			
			this.processOverlap(); 
			
			if( this.doesCollide() ) {
				this.setPositionY(oldPositionY);
				this.stopFall();
			}else{
				
				// Ugly... TODO: de acceleratie verspringt nu heel snel als mazub op de grond staat (check game met debug options) -> moet beter gefixt worden
				this.setAccelerationY(-10);
			}
				
		}
		
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
		return this.getSchool() != null;
	}

	void setSchool(School school) {
		this.school = school;
	}
	
	public boolean canHaveAsSchool(School school){
		// TODO Auto-generated method stub
		return true;
	}	
	
	private School school;
	
	@Override
	protected void terminate(){
		this.getWorld().removeGameObject(this);
		this.setWorld(null); // vervangen door method World removeAs?
		if (this.getSchool() != null)
			this.getSchool().removeAsSlime(this);
		
		this.terminated = true;
	}
	
	@Override
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
	
	
	
	
	/******************************************** Colission **********************************************/
	public void processMazubOverlap(Mazub mazub){
		System.out.println("slime colission with mazub");
		if(!mazub.isKilled() && getTimer().getSinceEnemyCollision() > 0.6){
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}	
	
	public void processSlimeOverlap(Slime slime){

		if(slime != this){
			if ( slime.getSchool().getNbSlimes() > this.getSchool().getNbSlimes() ){
				this.getSchool().switchSchool( this, slime.getSchool() );
				System.out.println("Slime changed school!");
			} else if ( slime.getSchool().getNbSlimes() < this.getSchool().getNbSlimes() ) {
				slime.getSchool().switchSchool( slime, this.getSchool() );
				System.out.println("Slime changed school!");
			}
		}
		
	}
	
	public void processSharkOverlap(Shark shark){
		
		if(!shark.isKilled() && this.getTimer().getSinceEnemyCollision() > 0.6){
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
		
	}
	
}
