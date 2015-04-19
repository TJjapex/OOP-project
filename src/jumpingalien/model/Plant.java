package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
import jumpingalien.util.Sprite;
// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Plants, game objects in the game world of Mazub. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/************************************************** GENERAL ***********************************************/

	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2));
		
	}
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 1 hit-point
	// * destroyed upon contact with a hungry Mazub
	
	public Plant(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit,
			  double velocityXMax, double accelerationXInit, Sprite[] sprites, int nbHitPoints, int maxNbHitPoints)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		
		super(pixelLeftX, pixelBottomY, velocityXInit,velocityYInit, velocityXMax, accelerationXInit, sprites, nbHitPoints, maxNbHitPoints);
		
		this.configureTerrain();

	}	
	
	public Plant(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		this(pixelLeftX, pixelBottomY, 0.5, 0, 0.5, 0, sprites, 1, 1);
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

		if (this.getTimer().getSinceLastPeriod() >= 0.5){ 
			
			this.periodMovement();
			
			this.getTimer().setSinceLastPeriod(0);
		}
		
		// Update horizontal position
		this.updatePositionX(dt);
	}
	
	public void periodMovement(){
		
		if (this.getOrientation() == Orientation.RIGHT){
			this.endMove(Orientation.RIGHT);
			this.startMove(Orientation.LEFT);
		}
		else {
			this.endMove(Orientation.LEFT);
			this.startMove(Orientation.RIGHT);
		}
		
	}
	
	
	/******************************************* COLISSION ********************************************/
//	public void processMazubOverlap(Mazub alien){
//		if(!alien.isKilled()){
//			this.kill();
//		}
//	}
	
	@Override
	public boolean doesCollide() {
		return doesCollideWithTerrain();
	}

	public void processMazubOverlap(Mazub alien) {

	}

	public void processPlantOverlap(Plant plant) {

	}

	public void processSharkOverlap(Shark shark) {

	}

	public void processSlimeOverlap(Slime slime) {

	}
}