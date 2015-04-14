package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
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
		
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2));

	}	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	
	
	/*************************************************** MOVING ***********************************************/
	
	
	
	/************************************************ CHARACTERISTICS *****************************************/
	

	
	/*************************************************** ANIMATION ********************************************/
	

	
	/*************************************************** HIT-POINTS *******************************************/
	
	
	
	/**************************************************** MOVEMENT ********************************************/
	
	// * isnt influenced by contact with other game objects or water/magma
	// * alternate moving left and right with a constant horizontal velocity of 0.5 [m/s] for 0.5s
	
	protected void processKilledButNotTerminated_NameMustBeChanged(double dt){
		if(this.isKilled() && !this.isTerminated()){
//			if(this.getTimer().getSinceKilled() > 0){ // Niet echt duidelijk of die nu direct moet verdwijnen of na 0.6 sec
				this.terminate();
//			}else{
//				this.getTimer().increaseSinceKilled(dt);
//			}
			
		}
	}
	
	public void doMove(double dt){		
		//if( this.doesCollide())
		//	throw new IllegalStateException(" Colission before movement! ");	
		
		if (this.getTimer().getSinceLastPeriod() >= 0.5){ // fuzzy?
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
		
		//this.processOverlap();// -> niet echt nodig bij plants?
		
		// Iedereen mag door plants dus in plants geen collision checken -> plants mogen niet door impassable terrain!
		if( this.doesCollide() ) 
			this.setPositionX(oldPositionX);
	}
	
	/******************************************* COLISSION ********************************************/
//	public void processMazubOverlap(Mazub alien){
//		if(!alien.isKilled()){
//			this.kill();
//		}
//	}
}