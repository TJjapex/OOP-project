package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
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
public class Slime {
	
	/************************************************** GENERAL ***********************************************/

	public static final double ACCELERATION_X = 0.7;
	public static final double VELOCITY_X_MAX_MOVING = 2.5;
	public static final double VELOCITY_X_INIT = 1.0;
	public static final double ACCELERATION_Y = -10.0;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 768;
	public static final int MAX_NB_HITPOINTS = 100;
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// * possess 100 hit-points
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, int nbHitPoints){
		this.setPositionX(pixelLeftX);
		this.setPositionY(pixelBottomY);
		
		this.setOrientation(Orientation.RIGHT);
		
		this.setNbHitPoints(nbHitPoints);
	}
	
	public Slime(int pixelLeftX, int pixelBottomY, Sprite[] sprites){		
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
		this.setAccelerationX( orientation.getSign() * ACCELERATION_X);
	}
	
	public void endMove(Orientation orientation) {
		assert (this.getOrientation() != null);
		
		if(orientation == this.getOrientation()){
			this.setVelocityX(0);
			this.setAccelerationX(0);
		}
	}
	
	/*************************************************** FALLING **********************************************/
	
	private void stopFall() {
		this.setVelocityY( 0 );
		this.setAccelerationY( 0 );
	}
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// * horizontal acceleration is equal to 0.7 [m/s^2] 
	// * maximal horizontal velocity is equal to 2.5 [m/s]
	
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
	
	// * lose 50 hit-points when making contact with Mazub or Shark
	// * Slimes lose hit-points upon touching water/magma (same as Mazub)
	
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
	
	// * move randomly to the left or right
	// * movement periods have a duration of 2s to 6s
	// * do not attack each other but block each others' movement
	// * Plants do not block Slimes
	
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
