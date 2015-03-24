package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
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
public class Shark {
	
	/************************************************** GENERAL ***********************************************/
	
	public static final double ACCELERATION_X = 1.5;
	public static final double VELOCITY_X_MAX_MOVING = 4;
	public static final double VELOCITY_X_INIT = 1.0;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 768;
	public static final double ACCELERATION_Y = -10.0;
	public static final double VELOCITY_Y_INIT = 2.0;
	public static final int MAX_NB_HITPOINTS = 100;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * start the game with 100 hit-points
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints){
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);
		
		this.setOrientation(Orientation.RIGHT);
		
		this.setNbHitPoints(nbHitPoints);
	}
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite[] sprites){		
		this(pixelLeftX, pixelBottomY, sprites, MAX_NB_HITPOINTS);
	}
	
	/******************************************** SIZE AND POSITIONING ****************************************/
	
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
		this.setAccelerationX( orientation.getSign() * ACCELERATION_X);
	}
	
	public void endMove(Orientation orientation) {
		assert (this.getOrientation() != null);
		
		if(orientation == this.getOrientation()){
			this.setVelocityX(0);
			this.setAccelerationX(0);
		}
	}
	
	/********************************************* JUMPING AND FALLING ****************************************/
	
	// * capable of jumping while their bottom perimeter is overlapping with water or impassable terrain
	// * Sharks fall while their bottom perimeter is not overlapping with impassable terrain or other game objects
	// * Sharks stop falling as soon as they are submerged in water (top perimeter is overlapping with a water tile)
	
	public void startJump(){
		this.setVelocityY( VELOCITY_Y_INIT );
		this.setAccelerationY( ACCELERATION_Y ); 
	}
	
	public void endJump() throws IllegalStateException{
		if( Util.fuzzyGreaterThanOrEqualTo(this.getVelocityY(), 0 )){
			this.setVelocityY(0);
		}else{
			throw new IllegalStateException("Shark does not have a positive vertical velocity!");
		}
	}
	
	public boolean isJumping(){
		return !Util.fuzzyEquals(this.getPositionY(), 0);
	}
	
	private void stopFall() {
		this.setVelocityY( 0 );
		this.setAccelerationY( 0 );
	}
	
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
	
	// * horizontal acceleration is equal to 1.5 [m/s^2]
	// * maximal horizontal velocity is equal to 4 [m/s]
	// * initial vertical velocity is equal to 2 [m/s]
	// REDUNDANT CODE
	
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
	
	public double getVelocityY(){
		return this.velocityY;
	}
	
	private void setVelocityX(double velocityX){
		if(Util.fuzzyGreaterThanOrEqualTo(Math.abs(velocityX), VELOCITY_X_MAX_MOVING)){
			this.setAccelerationX(0);
		}
			
		this.velocityX = Math.max( Math.min( velocityX , VELOCITY_X_MAX_MOVING), -VELOCITY_X_MAX_MOVING);
	}
	
	private void setVelocityY(double velocityY){
		this.velocityY = velocityY;
	}
	
	public boolean isValidVelocityX(double velocityX){
		return Math.abs(velocityX) <= VELOCITY_X_MAX_MOVING;
	}
	
	private double velocityX;
	
	private double velocityY;
	
	// Acceleration
	
	public double getAccelerationX(){
		return this.accelerationX;
	}
	
	public double getAccelerationY(){
		return this.accelerationY;
	}
	
	private void setAccelerationX(double accelerationX){
		if (Double.isNaN(accelerationX)){
			this.accelerationX = 0;
		} else
			this.accelerationX = accelerationX;
	}
	
	private void setAccelerationY(double accelerationY){
		if (Double.isNaN(accelerationY)){
			this.accelerationY = 0;
		} else
			this.accelerationY = accelerationY;
	}
	
	private double accelerationX;
	
	private double accelerationY;
	
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
	
	// * typically appear in water tiles and do not lose hit-points while submerged in water
	// * after 0.2s they lose 6 hit-points per 0.2s while in contact with air
	// * lose hit-points upon touching magma (same as Mazub)
	// * lose 50 hit-points when making contact with Mazub or Slimes
	
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
	
	private void updatePositionX(double dt){
		try{
			double sx = this.getVelocityX() * dt + 0.5 * this.getAccelerationX() * Math.pow( dt , 2 );
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
	
	private void updatePositionY(double dt){
		try{
			double sy = this.getVelocityY() * dt + 0.5 * this.getAccelerationY() * Math.pow( dt , 2 );
			this.setPositionY( this.getPositionY() + 100 * sy );
		}catch( IllegalPositionYException exc){
			if(exc.getPositionY() < 0 ){
				this.setPositionY(0);
				this.stopFall();
			}else{ // > GAME_HEIGHT - 1 
				this.setPositionY( GAME_HEIGHT - 1);
			}
		}
	}
	
	private void updateVelocityX(double dt) {
		double newVx = this.getVelocityX() + this.getAccelerationX() * dt;
		this.setVelocityX( newVx );
	}
	
	private void updateVelocityY(double dt) {
		double newVy = this.getVelocityY() + this.getAccelerationY() * dt;
		this.setVelocityY( newVy );
	}
	
}
