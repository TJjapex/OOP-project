package jumpingalien.model;

import java.util.Random;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
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
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, 1.0, 0.0, 2.5, 0.7, sprites, nbHitPoints, 100);
		this.startMove(this.getRandomOrientation());
		
	}
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{	
		this(pixelLeftX, pixelBottomY, sprites, 100);
	}
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	
	
	/*************************************************** MOVING ***********************************************/
	
	
	
	/*************************************************** FALLING **********************************************/
	
	
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	
	
	/*************************************************** ANIMATION ********************************************/
	
	
	
	/*************************************************** HIT-POINTS *******************************************/
	
	// * lose 50 hit-points when making contact with Mazub or Shark
	// * Slimes lose hit-points upon touching water/magma (same as Mazub)
	
	
	
	/**************************************************** MOVEMENT ********************************************/
	
	// * move randomly to the left or right
	// * movement periods have a duration of 2s to 6s
	// * do not attack each other but block each others' movement
	// * Plants do not block Slimes
	
	public void advanceTime(double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");
		if( !hasProperWorld())
			throw new IllegalStateException(" This Slime is not in world!");

		Orientation currentOrientation;
		
		// Timers
		
		this.getTimer().increaseSinceLastPeriod(dt);
		
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
			
			// this.processCollision(); 
			
			if( this.doesCollide() ) {
				this.setPositionX(oldPositionX);
				currentOrientation = this.getOrientation();
				this.endMove(currentOrientation);
				if (currentOrientation != Orientation.RIGHT){
					this.startMove(Orientation.RIGHT);
				}
				else {
					this.startMove(Orientation.LEFT);
				}
			}
			
			// Update vertical position
			this.updatePositionY(dt);
			
			// Update vertical velocity
			this.updateVelocityY(dt);
			
			// this.processCollision(); 
			
			if( this.doesCollide() ) {
				this.setPositionY(oldPositionY);
				this.stopFall();
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
	
	
	
}
