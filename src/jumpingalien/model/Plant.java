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
 * A class of Plants, game objects in the game world of Mazub. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/************************************************** GENERAL ***********************************************/

	
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 1 hit-point
	// * destroyed upon contact with a hungry Mazub
	
	public Plant(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
	throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, 0.5, 0, 0.5, 0, sprites, 1, 1);
		this.startMove(Orientation.RIGHT);

	}	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	
	
	/*************************************************** MOVING ***********************************************/
	
	
	
	/************************************************ CHARACTERISTICS *****************************************/
	

	
	/*************************************************** ANIMATION ********************************************/
	

	
	/*************************************************** HIT-POINTS *******************************************/
	
	
	
	/**************************************************** MOVEMENT ********************************************/
	
	// * isnt influenced by contact with other game objects or water/magma
	// * alternate moving left and right with a constant horizontal velocity of 0.5 [m/s] for 0.5s
	
	public void advanceTime(double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");
		if( isTerminated() )
			throw new IllegalStateException("Object terminated!");	
		
		// Killed
		if(this.isKilled() && !this.isTerminated()){
			if(this.getTimer().getSinceKilled() > 0){ // Niet echt duidelijk of die nu direct moet verdwijnen of na 0.6 sec
				this.terminate();
			}else{
				this.getTimer().increaseSinceKilled(dt);
			}
			
		}
		
		// Timers
		
		if(!this.isMoving())
			this.getTimer().increaseSinceLastMove(dt);
		
		this.getTimer().increaseSinceLastPeriod(dt);
		
		// Alternating movement
		
		if (!this.isKilled()){
			if (this.getTimer().getSinceLastPeriod() > 0.5){
				if (this.getOrientation() == Orientation.RIGHT){
					this.endMove(Orientation.RIGHT);
					this.startMove(Orientation.LEFT);
				}
				else {
					this.endMove(Orientation.LEFT);
					this.startMove(Orientation.RIGHT);
				}
				this.getTimer().setSinceLastPeriod(0);
			}
				
			double oldPositionX = this.getPositionX();
			
			// Update horizontal position
			this.updatePositionX(dt);
			
			// this.processCollision(); -> niet echt nodig bij plants?
			
			if( this.doesCollide() ) 
				this.setPositionX(oldPositionX);
		}
	}
	
}