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
 * A class of Plants, game objects in the game world of Mazub. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Plant extends GameObject {
	
	/************************************************** GENERAL ***********************************************/

	@Override
	public void configureTerrain(){
		
		this.setTerrainPropertiesOf(Terrain.AIR,   new TerrainProperties(true, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.SOLID, new TerrainProperties(false, 0, 0, false));
		this.setTerrainPropertiesOf(Terrain.WATER, new TerrainProperties(true, 2, 0.2, false));
		this.setTerrainPropertiesOf(Terrain.MAGMA, new TerrainProperties(true, 50, 0.2, false));
		
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
	
	/********************************************** WORLD RELATION ********************************************/
	@Override
	public void setWorldTo(World world){
		if(!this.canHaveAsWorld(world))
			throw new IllegalArgumentException("This plant cannot have given world as world!");
		if(!world.canHaveAsGameObject(this))
			throw new IllegalArgumentException("Given world cannot have this plant as plant!");
		
		setWorld(world);
		world.addAsPlant(this);
	}
	
	@Override
	protected void unsetWorld() {
		if(this.hasWorld()){
			World formerWorld = this.getWorld();
			this.setWorld(null);
			formerWorld.removeAsPlant(this);
		}
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
			if(this.getTimer().getSinceKilled() > 0.6){ 
				this.terminate();
			}else{
				this.getTimer().increaseSinceKilled(dt);
			}	
		}
	}
	
	@Override
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
	
	// OPTIONAL IMPLEMENTATION: Plant verandert van richting als hij collide met een andere plant of de muur, ik weet niet of dit beter is..
	
	@Override
	protected void processHorizontalCollision() {		
		Orientation currentOrientation = this.getOrientation();
		this.endMove(currentOrientation);
		if (currentOrientation != Orientation.RIGHT){
			this.startMove(Orientation.RIGHT);
		} else {
			this.startMove(Orientation.LEFT);
		}
	}
	
	
	/******************************************* COLISSION ********************************************/
//	public void processMazubOverlap(Mazub alien){
//		if(!alien.isKilled()){
//			this.kill();
//		}
//	}
	
	@Override
	public void processMazubOverlap(Mazub alien) {
		 this.takeDamage(1);
	}

	@Override
	public void processPlantOverlap(Plant plant) {

	}

	@Override
	public void processSharkOverlap(Shark shark) {

	}

	@Override
	public void processSlimeOverlap(Slime slime) {

	}
	
	
	@Override
	protected Set<GameObject> getAllImpassableGameObjects(){
		Set<GameObject> allImpassableGameObjects= new HashSet<GameObject>(this.getWorld().getAllMazubs());
		allImpassableGameObjects.addAll(this.getWorld().getAllPlants());
		return allImpassableGameObjects;
	}
	
}