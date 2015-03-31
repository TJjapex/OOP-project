package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Sharks, enemy characters in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Shark extends GameObject{
	
	/************************************************** GENERAL ***********************************************/
	
	
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * start the game with 100 hit-points
	// * horizontal acceleration is equal to 1.5 [m/s^2]
	// * maximal horizontal velocity is equal to 4 [m/s]
	// * initial vertical velocity is equal to 2 [m/s]
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX,pixelBottomY, 1.0, 2.0, 4.0, 1.5, sprites, nbHitPoints, 100);
		
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
		// TO DO
	}
	
	public void endDiveRise(){
		// TO DO
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
	
	public void advanceTime(double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");
		
		// NEEDS RANDOMIZED MOVEMENT
		
		// Update horizontal position
		this.updatePositionX(dt);
				
		// Update vertical position
		this.updatePositionY(dt);
				
		// Update horizontal velocity
		this.updateVelocityX(dt);
		
		// Update vertical velocity
		this.updateVelocityY(dt);
		
		// COLLIDES WITH
		
	}
	
}
