package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.util.Sprite;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Sharks, enemy characters in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Shark extends GameObject{
	
	/************************************************** GENERAL ***********************************************/
	
	public static final double MIN_PERIOD_TIME = 1.0;
	public static final double MAX_PERIOD_TIME = 4.0;
	
	private double currentPeriodTime = timer.getRandomPeriodTime(MIN_PERIOD_TIME, MAX_PERIOD_TIME);
	
	private int nbNonJumpingPeriods = 4;
	Random random = new Random();
	
	public static final double ACCELERATION_DIVING = -2.0;
	public static final double ACCELERATION_RISING = 2.0;
	
	private double randomAcceleration;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * start the game with 100 hit-points
	// * horizontal acceleration is equal to 1.5 [m/s^2]
	// * maximal horizontal velocity is equal to 4 [m/s]
	// * initial vertical velocity is equal to 2 [m/s]
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX,pixelBottomY, 1.0, 2.0, 4.0, 1.5, sprites, nbHitPoints, 100);
		this.startMove(this.getRandomOrientation());
		this.startDiveRise();
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 6, 0.2));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 0, 0.2));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 0, 0.2));
		
	}
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites) 
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{		
		this(pixelLeftX, pixelBottomY, sprites, 100);
	}
	
	/******************************************** SIZE AND POSITIONING ****************************************/
	
	
	
	/*************************************************** MOVING ***********************************************/
	
	
	
	/********************************************* JUMPING AND FALLING ****************************************/
	
	// * capable of jumping while their bottom perimeter is overlapping with water or impassable terrain
	// * Sharks fall while their bottom perimeter is not overlapping with impassable terrain or other game objects
	// * Sharks stop falling as soon as they are submerged in water (top perimeter is overlapping with a water tile)
	
	
	
	/*********************************************** DIVING AND RISING ****************************************/
	
	// * when submerged in water, capable of diving and rising: - each non-jumping movement period they shall have
	// 															  a random vertical acceleration between or equal to
	//															  - 0.2 [m/s^2] and 0.2 [m/s^2]
	//															- vertical acceleration set back to 0 when top or
	//															  bottom perimeter are not overlapping with a
	//															  water tile any more and at the end of the 
	//															  movement period
	
	public void startDiveRise(){
		randomAcceleration = ACCELERATION_DIVING + (ACCELERATION_RISING - ACCELERATION_DIVING)*random.nextDouble();
		this.setVelocityY( Math.signum(randomAcceleration)*this.getVelocityYInit() );
		this.setAccelerationY( randomAcceleration); 
	}
	
	public void endDiveRise(){
		this.setVelocityY(0);
		this.setAccelerationY(0);
	}
	
	/************************************************ CHARACTERISTICS *****************************************/

	
	
	/*************************************************** ANIMATION ********************************************/
	

	
	/*************************************************** HIT-POINTS *******************************************/
	
	// * typically appear in water tiles and do not lose hit-points while submerged in water
	// * after 0.2s they lose 6 hit-points per 0.2s while in contact with air
	// * lose hit-points upon touching magma (same as Mazub)
	// * lose 50 hit-points when making contact with Mazub or Slimes
	

	
	/**************************************************** MOVEMENT ********************************************/
	
	// * jump will occur at the start of a horizontal movement period and the Shark stops jumping at the end of
	//   that period
	// * movement periods have a duration of 1s to 4s
	// * at least 4 non-jumping periods of random movement in between the end of one jump and the start
	//   of the next one
	// * do not attack each other but block each others' movement
	// * Plants do not block Sharks
	public void doMove(double dt){
		if( this.doesCollide())
			throw new IllegalStateException(" Colission before movement! ");	
		
		Orientation currentOrientation;
		
		// Randomized movement
		if (this.getTimer().getSinceLastPeriod() >= currentPeriodTime){
			
			this.endMove(this.getOrientation());
			
			if (!this.isOnGround()){
				this.endJump();
				this.stopFall();
			} else {
				nbNonJumpingPeriods += 1;
				this.endDiveRise();
			}	
			
			this.startMove(this.getRandomOrientation());
			
			if ((nbNonJumpingPeriods >= 4) && random.nextBoolean()){
				this.startJump();
				nbNonJumpingPeriods = 0;
			} else if (this.isSubmergedIn(Terrain.WATER)) {
				this.startDiveRise();
			}
					
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
			
			currentOrientation = this.getOrientation();		// dit gedeelte is eigenlijk niet gevraagd in de opdracht maar maakt de bewegingen wel logischer
			this.endMove(currentOrientation);
			if (currentOrientation != Orientation.RIGHT){
				this.startMove(Orientation.RIGHT);
				if (this.isSubmergedIn(Terrain.WATER))
					this.startDiveRise();
			} else {
				this.startMove(Orientation.LEFT);
				if (this.isSubmergedIn(Terrain.WATER))
					this.startDiveRise();
			}
			
		}
		
		// TODO: the vertical acceleration of a non-jumping Shark shall be set to zero if the Shark's top or bottom perimeters are not overlapping with
		//		  a water tile any more, and at the end of the movement period. -> requires change in definition of isJumping()
				
		// Update vertical position
		this.updatePositionY(dt);
				
		// Update vertical velocity
		this.updateVelocityY(dt);
				
		this.processOverlap(); 
				
		if( this.doesCollide()) {
			this.setPositionY(oldPositionY);
			this.stopFall();
		}else if (! this.isSubmergedIn(Terrain.WATER)){
			this.setAccelerationY(-10);
		}		
	}
	
	/****************************************************************** ACCELERATION Y *****************************************************/
	
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
	 * Variable registering the vertical acceleration of this Mazub.
	 */
	private double accelerationY;
	
	/*************************************************************** COLLISION ******************************************************/
	
	@Override
	public void processMazubOverlap(Mazub alien) {
		if(!alien.isKilled() && this.getTimer().getSinceEnemyCollision() > 0.6){
		//	System.out.println("deduced hitpoints");
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
	public void processSlimeOverlap(Slime slime){
		if(!slime.isKilled() && this.getTimer().getSinceEnemyCollision() > 0.6){
			this.takeDamage(50);
			this.getTimer().setSinceEnemyCollision(0);
		}
	}
	
}
