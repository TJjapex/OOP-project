package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
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
public class Plant {
	
	/************************************************** GENERAL ***********************************************/

	public static final double VELOCITY_X_MAX_MOVING = 0.5;
	public static final double VELOCITY_X_INIT = 0.5;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 768;
	public static final int MAX_NB_HITPOINTS = 1;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 1 hit-point
	// * destroyed upon contact with a hungry Mazub
	
	public Plant(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints){
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);
		
		this.setOrientation(Orientation.RIGHT);
		
		this.setNbHitPoints(nbHitPoints);
	}
	
	public Plant(int pixelLeftX, int pixelBottomY, Sprite[] sprites){		
		this(pixelLeftX, pixelBottomY, sprites, MAX_NB_HITPOINTS);
	}
	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	public int getRoundedPositionX(){
		return (int) Math.floor(this.getPositionX());
	}
	
	public static boolean isValidRoundedPositionX(int positionX){
		return positionX >= 0 && positionX < GAME_WIDTH;
	}
	
	public int getRoundedPositionY(){
		return (int) Math.floor(this.getPositionY());
	}
	
	public static boolean isValidRoundedPositionY(int positionY){
		return positionY >= 0 && positionY < GAME_HEIGHT;
	}
	
	public int getWidth(){
		return this.getCurrentSprite().getWidth();
	}
	
	public static boolean isValidWidth(int width){
		return width > 0 && width < GAME_WIDTH;
	}
	
	public int getHeight(){
		return this.getCurrentSprite().getHeight();
	}
	
	public static boolean isValidHeight(int height){
		return height > 0 && height < GAME_HEIGHT;
	}
	
	/*************************************************** MOVING ***********************************************/
	
	public void startMove(Orientation orientation){		
		assert (this.getOrientation() != null);
		
		this.setOrientation(orientation);
		this.setVelocityX( orientation.getSign() * VELOCITY_X_INIT );
	}
	
	public void endMove(Orientation orientation) {
		assert (this.getOrientation() != null);
		
		if(orientation == this.getOrientation()){
			this.setVelocityX(0);
		}
	}
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// Position
	
	public double getPositionX(){
		return this.positionX;
	}
	
	public double getPositionY(){
		return this.positionY;
	}
	
	private void setPositionX(double positionX) throws IllegalPositionXException{
		if( !isValidPositionX(positionX)) 
			throw new IllegalPositionXException(positionX);
		this.positionX = positionX;
	}
	
	private void setPositionY(double positionY) throws IllegalPositionYException{
		if( !isValidPositionY(positionY)) 
			throw new IllegalPositionYException(positionY);
		this.positionY = positionY;
	}	
	
	public static boolean isValidPositionX(double positionX) {
		return isValidRoundedPositionX((int) Math.floor(positionX));
	}
	
	public static boolean isValidPositionY(double positionY) {
		return isValidRoundedPositionY((int) Math.floor(positionY));
	}
	
	private double positionX;
	
	private double positionY;
	
	// Velocity
	
	public double getVelocityX(){
		return this.velocityX;
	}
	
	private void setVelocityX(double velocityX){			
		this.velocityX = Math.max( Math.min( velocityX , VELOCITY_X_MAX_MOVING), -VELOCITY_X_MAX_MOVING);
	}
	
	public boolean isValidVelocityX(double velocityX){
		return Math.abs(velocityX) <= VELOCITY_X_MAX_MOVING;
	}
	
	private double velocityX;
	
	// Orientation
	
	public Orientation getOrientation(){
		return this.orientation;
	}
	
	private void setOrientation(Orientation orientation){
		this.orientation = orientation;
	}	
	
	public static boolean isValidOrientation(Orientation orientation){
		return (orientation == Orientation.LEFT) || (orientation == Orientation.RIGHT);
	}
	
	private Orientation orientation;
	
	/*************************************************** ANIMATION ********************************************/
	
	public Sprite getCurrentSprite(){		
		return null; // TO DO
	}
	
	/*************************************************** HIT-POINTS *******************************************/
	
	public int getNbHitPoints() {
		return this.nbHitPoints;
	}
	
	public void setNbHitPoints(int nbHitPoints) {
		this.nbHitPoints = Math.max( Math.min(nbHitPoints, MAX_NB_HITPOINTS), 0);
	}
	
	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints <= MAX_NB_HITPOINTS);
	}
	
	private int nbHitPoints;
	
	/**************************************************** MOVEMENT ********************************************/
	
	// * isnt influenced by contact with other game objects or water/magma
	// * alternate moving left and right with a constant horizontal velocity of 0.5 [m/s] for 0.5s
	
	public void advanceTime(double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");
		
		// NEEDS RANDOMIZED MOVEMENT
		
		// Update horizontal position
		this.updatePositionX(dt);
		
		// COLLIDES WITH
			
	}
	
	private void updatePositionX(double dt){
		try{
			double sx = this.getVelocityX() * dt;
			this.setPositionX( this.getPositionX() + 100 * sx );
		}catch( IllegalPositionXException exc){
			if(exc.getPositionX() < 0 ){
				this.setPositionX( 0 );
				endMove(Orientation.LEFT);
			}else{ // > GAME_WIDTH - 1 
				this.setPositionX( GAME_WIDTH - 1 );
				endMove(Orientation.RIGHT);
			}
		}
	}
	
}
