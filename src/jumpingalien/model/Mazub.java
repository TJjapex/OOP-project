package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

/**
 * A class of Mazubs, characters for a platform game with several properties. This class has been worked out
 * for a project of the course Object Oriented Programming at KULeuven.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

public class Mazub {
		
	/************************************************** GENERAL ***********************************************/
	
	// 			the class Util provides methods for comparing doubles up to a fixed epsilon
	// 			write a test suite
	//			class invariants?
	
	private static int GAME_WIDTH = 1024; // Mss beter niet in hoofdletters? 
											// -> OK volgens conventie (coding rule 34 p.72), beter GAME dan WINDOW
											// -> want de screen size is ook nog aan te passen in de GUI
	private static int GAME_HEIGHT = 768;
	private static double AY = -10.0;
	private static double VY_INIT = 8.0;
	private static double VX_MAX_MOVING = 3.0;
	private static double VX_MAX_DUCKING = 1.0;

	private boolean ducking = false; // -> initialiseren in constructor met setter?

	private int currentSpriteIteration = 0;
	private double timeTillLastSprite = 0;
	private double timeTillLastMove = 0; // Time until last movement
										 // -> ook allemaal initialiseren in constructor dan met setter?
	
	/************************************************ CONSTRUCTOR *********************************************/
	
	// 			accepts an array of n images as parameter (n even, n >= 10)
	//			TOTAAL, NOMINAAL OF DEFENSIEF???
	//				 voorlopig als totaal behandeld in postcondities

	/**
	 * Constructor for the class Mazub.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Mazub's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Mazub.
	 * @post	If the given pixelLeftX is within the boundaries of the game world, the initial Px is equal to
	 * 			the given value of pixelLeftX.
	 * 			| if (0 <= pixelLeftX < GAME_WIDTH)
	 * 			|	new.getPx() == pixelLeftX
	 * 			| else
	 * 			| 	new.getPx() == 0
	 * @post	If the given pixelBottomY is within the boundaries of the game world, the initial Py is equal to
	 * 			the given value of pixelBottomY.
	 * 			| if (0 <= pixelLeftX < GAME_HEIGHT)
	 * 			|	new.getPy() == pixelBottomY
	 * 			| else
	 * 			| 	new.getPy() == 0
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		this.setPx(pixelLeftX);
		this.setPy(pixelBottomY);
		// als het een ongeldige positie is, gewoon op (0,0) initialiseren?
		
		this.setOrientation(Orientation.LEFT); // beter RIGHT ?
		
		// nog check doen of een even aantal is? Nominaal, totaal of defensief?
		//  -> misschien kunnen we dit gewoon nominaal doen door dit in de commentaar als preconditie te stellen
		this.sprites = sprites;
	}
	
	
	/********************************************* SIZE AND POSITIONING ***************************************/
	
	// 			All methods here must be worked out defensively (using integer numbers) 
	// 			Wat moeten we dan doen? :p 
	//			 -> hier moet sowieso wel iets met errors gedaan worden, ik denk enkel als één van de 
	// 			 -> coordinaten negatief zou worden als geheel getal dat er dan een error moet gethrowd worden
	
	// 			Game world (X,Y) : 1024x768 pixels (origin bottom-left)
	// 			each pixel = 0.01m
	// 			Position of Mazub: bottom-left pixel of Mazub
	// 			Mazub's dimension (X_p,Y_p), depends on active sprite
	
	/**
	 * Return the round x-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	public int getX(){
		return (int) Math.round(this.getPx());
	}
	
	/**
	 * Return the round y-location of Mazub's bottom left pixel.
	 * 
	 * @return 	An integer that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	public int getY(){
		return (int) Math.round(this.getPy());
	}
	
	/**
	 * Return the width of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the width of Mazub's active sprite.
	 */
	@Basic
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Return the height of Mazub, depending on the active sprite.
	 * 
	 * @return	An integer that represents the height of Mazub's active sprite.
	 */
	@Basic
	public int getHeight(){
		return this.height;
	}
		
	private void setWidth(){
		this.width = width;
	}
	
	private void setHeight(){
		this.width = height;
	}
	
	private int width;
	private int height;
	
	/************************************************ RUNNING *************************************************/
	
	// 			All methods here must be worked out nominally
	// 			vx_init = 1 m/s	(may change in future but never below 1 m/s, instance variable)
	// 			ax = 0.9 m/s^2	(may change in future, class variable)
	// 			vx_max = 3 m/s	(may change in future but never below vx_init, instance variable)
	// 			startMove:
	// 				vx_new = vx_curr + ax*delta_t (for vx_init < vx_curr < vx_max)
	// 			endMove:
	// 				vx_new = 0
	
	/**
	 * Make Mazub start moving. Set the initial horizontal velocity and acceleration of Mazub,
	 * depending on his orientation.
	 * 
	 * @param orientation
	 * 				The direction in which Mazub starts moving.
	 * @pre
	 * @post
	 */
	public void startMove(Orientation orientation){
		this.setOrientation(orientation);
		this.setVx( orientation.getDirection() * this.vx_init );
		this.setAx( orientation.getDirection() * this.ax_init );
	}
	
	/**
	 * Make Mazub end moving. Set the horizontal velocity and acceleration of Mazub to 0.
	 * 
	 * @post	The horizontal velocity and acceleration of Mazub is equal to zero. Also the time since the
	 * 			last move was made is reset to 0.
	 * 			| new.getVx() == 0
	 * 			| new.getAx() == 0
	 * 			| new.getTimeTillLastMove() == 0
	 */
	public void endMove() {
		this.setVx(0);
		this.setAx(0);
		
		this.setTimeTillLastMove(0);
	}

	/**
	 * Checks whether Mazub is moving.
	 * 
	 * @return 	A boolean that represents if Mazub is moving or not.
	 */
	public boolean isMoving(){
		return !Util.fuzzyEquals(this.getVx(), 0);
	}
	
	/**
	 * Checks whether Mazub has moved in the last second.
	 * 
	 * @return	A boolean that represents if Mazub has moved in the last second or not.
	 */
	public boolean hasMovedInLastSecond(){ // Slechte naam
		return Util.fuzzyLessThanOrEqualTo(this.getTimeTillLastMove(), 1.0);
	}
	
	/********************************************* JUMPING AND FALLING ****************************************/
	
	// 			All methods here must be worked out defensively
	// 			startJump:
	//				vy_init = 8 m/s 	(will not change in future)
	//				vy_new = vy_current + ay*delta_t
	// 			endJump:
	//  			vy_new = 0 m/s 	(if vy_curr > 0)
	// 			while (y != 0):
	//				ay = -10 m/s^2	(will not change in future)
	
	/**
	 * Make Mazub start jumping. Set the vertical initial velocity and gravitational acceleration of Mazub.
	 * 
	 * @post	The vertical velocity and acceleration of Mazub is equal to respectively VY_INIT and AY.
	 * 			| new.getVy() == VY_INIT
	 * 			| new.getAy() == AY
	 * @throws
	 */
	public void startJump(){
		this.setVy( VY_INIT );
		this.setAy( AY ); 
	}
	
	/**
	 * Make Mazub end jumping. Set the vertical velocity of Mazub to 0 when he's still moving upwards.
	 * 
	 * @post	If the vertical velocity of Mazub was greater than 0, it is now set to 0.
	 * 			| if (this.getVy() > 0)
	 * 			|	new.getVy() == 0
	 * @throws 
	 */
	public void endJump() {
		if( this.getVy() > 0 ){
			this.setVy(0);
		}
	}
	
	/**
	 * Checks whether Mazub is jumping.
	 * 
	 * @return	A boolean that represents if Mazub is jumping or not.
	 */
	public boolean isJumping(){
		return !Util.fuzzyEquals(this.getVy(), 0);
	}
	
	/**
	 * Make Mazub stop falling. Set the vertical velocity and acceleration of Mazub to 0.
	 * 
	 * @post	The vertical velocity and acceleration of Mazub is equal to 0.
	 * 			| new.getVy() == 0
	 * 			| new.getAy() == 0
	 * @throws
	 */
	private void stopFall() {
		this.setVy( 0 );
		this.setAy( 0 );
	}
	
	/**
	 * Checks whether Mazub is on the ground.
	 * 
	 * @return	A boolean that represents if Mazub is on the ground or not.
	 */
	private boolean isOnGround() {
		return Util.fuzzyEquals(this.getY(), 0);
	}
	
	/*************************************************** DUCKING **********************************************/
	
	// 				All methods here must be worked out defensively
	// 				affects Mazub's dimension (X_p,Y_p)
	// 				restricts vx_max to 1 m/s (no acceleration possible)
	
	/**
	 * Make Mazub start ducking. Set the maximal horizontal velocity for ducking.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VX_MAX_DUCKING and the ducking status of
	 * 			Mazub is true.
	 * 			| new.getVxMax() == VX_MAX_DUCKING
	 * 			| new.isDucking() == true
	 * @throws
	 */
	public void startDuck(){
		this.setVxMax(VX_MAX_DUCKING);
		this.setDucking(true);
	}
	
	/**
	 * Make Mazub end ducking. Reset the maximal horizontal velocity.
	 * 
	 * @post	The maximal horizontal velocity of Mazub is equal to VX_MAX_MOVING and the ducking status of
	 * 			Mazub is false.
	 * 			| new.getVxMax() == VX_MAX_MOVING
	 * 			| new.isDucking() == false
	 * @throws
	 */
	public void endDuck(){
		this.setVxMax(VX_MAX_MOVING);
		this.setDucking(false);
	}	
	
	/**
	 * Checks whether Mazub is ducking.
	 * 
	 * @return	A boolean that represents if Mazub is ducking or not.
	 */
	@Basic
	public boolean isDucking(){
		return this.ducking;
	}
	
	/**
	 * Set Mazub's status to ducking.
	 * 
	 * @param ducking
	 * 			A boolean that represents if Mazub's status should be changed to ducking or not.
	 * @post	The ducking status of Mazub is equal to the given boolean value of ducking.
	 * 			| new.isDucking() == ducking
	 */
	public void setDucking(boolean ducking){
		this.ducking = ducking;
	}
	
	/************************************************ CHARACTERISTICS *****************************************/
	
	// 				All methods here must be worked out totally
	// 				velocity, acceleration, orientation, timing 
	// 				type double (not NaN, may be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY)
	// 				rounding down to integer value (at the end!) to determine Mazub's effective position

	// Position
	
	/**
	 * Return the x-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the x-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	public double getPx(){
		return this.px;
	}
	
	/**
	 * Return the y-location of Mazub's bottom left pixel.
	 * 
	 * @return	A double that represents the y-coordinate of Mazub's
	 * 			bottom left pixel in the world.
	 */
	@Basic
	public double getPy(){
		return this.py;
	}

	/**
	 * Set the x-location of Mazub's bottom left pixel.
	 * 
	 * @param px
	 * 			A double that represents the desired x-location of Mazub's bottom left pixel.
	 */
	private void setPx(double px) {
		this.px = Math.min(Math.max(px, 0), GAME_WIDTH - 1);
	}
	
	/**
	 * Set the y-location of Mazub's bottom left pixel.
	 * 
	 * @param py
	 * 			A double that represents the desired y-location of Mazub's bottom left pixel. 
	 */
	private void setPy(double py) {
		this.py = Math.min(Math.max(py, 0), GAME_HEIGHT - 1);
	}	
	
// ?? Uiteindelijk niet nodig omdat X en Y gewoon afgeronde px en py zijn?
//	public static boolean isValidPx(double px) {
//		return Util.fuzzyGreaterThanOrEqualTo(px, 0) && Util.fuzzyLessThanOrEqualTo(px, WINDOW_WIDTH);
//	}
//	
//	public static boolean isValidPy(double py) {
//		return Util.fuzzyGreaterThanOrEqualTo(py, 0) && Util.fuzzyLessThanOrEqualTo(py, WINDOW_HEIGHT);
//	}
//	
//  -> zit nu al verwerkt in die setters? dan is deze totale uitwerking in orde volgens mij
		
	private double px;
	private double py;
	
	// Velocity

	/**
	 * Return the horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the horizontal velocity of Mazub.
	 */
	@Basic
	public double getVx(){
		return this.vx;
	}
	
	/**
	 * Return the vertical velocity of Mazub.
	 * 
	 * @return	A double that represents the vertical velocity of Mazub.
	 */
	@Basic
	public double getVy(){
		return this.vy;
	}
	
	/**
	 * Set the horizontal velocity of Mazub.
	 * 
	 * @param vx
	 * 			A double that represents the desired horizontal velocity of Mazub.
	 * @post
	 */
	private void setVx(double vx){
		this.vx = Math.max(Math.min( vx , this.getVxMax()), -this.getVxMax());
	}
	
	/**
	 * Set the vertical velocity of Mazub.
	 * 
	 * @param vy
	 * 			A double that represents the desired vertical velocity of Mazub.
	 * @post
	 */
	private void setVy(double vy){
		this.vy = vy;
	}
	
	private double vx;
	private double vy;
	private double vx_init = 1.0;
//	private double vy_init;	 -> is al gedefinieerd hierboven? zie VY_INIT
	
	// Maximum velocity
	
	/**
	 * Return the maximal horizontal velocity of Mazub.
	 * 
	 * @return	A double that represents the maximal horizontal velocity of Mazub.
	 */
	@Basic
	public double getVxMax(){
		return this.vx_max;
	}
	
	/**
	 * Set the maximal horizontal velocity of Mazub.
	 * 
	 * @param vx_max
	 * 			A double that represents the desired maximal horizontal velocity of Mazub.
	 */
	private void setVxMax(double vx_max){ // Slechte naam -> waarom?
		this.vx_max = vx_max;
	}
	
	private double vx_max = VX_MAX_MOVING;
	
	// Acceleration
	
	/**
	 * Return the horizontal acceleration of Mazub.
	 * 
	 * @return	A double that represents the horizontal acceleration of Mazub.
	 */
	@Basic
	public double getAx(){
		return this.ax;
	}
	
	/**
	 * Return the vertical acceleration of Mazub.
	 * 
	 * @return	A double that represents the vertical acceleration of Mazub.
	 */
	@Basic@Immutable
	public double getAy(){
		return this.ay;
	}
	
	/**
	 * Set the horizontal acceleration of Mazub.
	 * 
	 * @param ax
	 * 			A double that represents the desired horizontal acceleration of Mazub.
	 */
	private void setAx(double ax){
		this.ax = ax;
	}
	
	/**
	 * Set the vertical acceleration of Mazub.
	 * 
	 * @param ay
	 * 			A double that represents the desired vertical acceleration of Mazub.
	 */
	private void setAy(double ay){
		this.ay = ay;
	}
		
	private double ax;
	private double ay;
	private double ax_init = 0.9;
	
	// Orientation
	
	/**
	 * Return the orientation of Mazub.
	 * 
	 * @return	An orientation that represents the current orientation of Mazub.
	 */
	@Basic
	public Orientation getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Set the orientation of Mazub.
	 * 
	 * @param orientation
	 * 			An orientation that represents the desired orientation of Mazub.
	 */
	public void setOrientation(Orientation orientation){
		this.orientation = orientation;
	}	
	
	private Orientation orientation;
	
	/******************************************* CHARACTER SIZE AND ANIMATION *********************************/
	
	// 				All methods here must be worked out nominally
	// 				no formal documentation required
	// 				class Sprite is provided
	// 				multiple sprites for moving to the right/left (same amount), alternate (75ms) and repeat
	// 				it must be possible to turn to other algorithms for displaying successive images of a Mazub
	//					during some period of time
	
	/**
	 * Return the correct sprite of Mazub, depending on his current status.
	 * 
	 * @return	A sprite that fits the current status of Mazub.
	 */
	public Sprite getCurrentSprite(){
		
		// Moet mooier/efficienter/korter

		
		// m bepalen -> mss beter gewoon een keer doen in constructor en dan opslaan?
		int m = ( this.sprites.length - 8) / 2; // Als length even is geeft dit altijd een correct getal 
												//	-> moeten nog check doen
		
		// Voor animatie
		while(this.getTimeTillLastSprite() > 0.075){ // Util fuzzy?
			this.setCurrentSpriteIteration(this.getCurrentSpriteIteration() + 1);
			this.setCurrentSpriteIteration(this.getCurrentSpriteIteration() % m);
			this.setTimeTillLastSprite(this.getTimeTillLastSprite() - 0.075);
		}
		
		
		int index = 0;
		
		if(!this.isMoving()){
			
			if(!this.hasMovedInLastSecond()){
				if(!this.isDucking()){
					index = 0;
				}else{
					index = 1;
				}
			}else{
				if(!this.isDucking()){
					if(this.getOrientation() == Orientation.RIGHT){
						index = 2;
					}else{ // LEFT
						index = 3;
					}
				}else{
					if(this.getOrientation() == Orientation.RIGHT){
						index = 6;
					}else{ // LEFT
						index = 7;
					}
				}
			}
			
		}else{ // MOVING
			if(this.isJumping()){
				if(!this.isDucking()){
					if(this.getOrientation() == Orientation.RIGHT){
						index = 4;
					}else{ // LEFT
						index = 5;
					}
				}
			}
			
			if(this.isDucking()){
				if(this.getOrientation() == Orientation.RIGHT){
					index = 6;
				}else{ // LEFT
					index = 7;
				}
			}
			if(!this.isDucking() && !this.isJumping()){
				if(this.getOrientation() == Orientation.RIGHT){
					index = 8 + this.getCurrentSpriteIteration();
				}else{ // LEFT
					index = 8 + m + this.getCurrentSpriteIteration();
				}
			}
		}
		
		return this.sprites[index];
		
	}
	
	/**
	 * Return the number referring to the current sprite in an iterative process of sprites.
	 * 
	 * @return	An integer that represents the current sprite in an iterative process of sprites.
	 */
	public int getCurrentSpriteIteration(){
		return this.currentSpriteIteration;
	}
	
	/**
	 * Set the number referring to the current sprite in an iterative process of sprites.
	 * 
	 * @param currentSprite
	 * 			An integer that represents the desired sprite in an iterative process of sprites.
	 */
	public void setCurrentSpriteIteration(int currentSprite){
		this.currentSpriteIteration = currentSprite;
	}
	
	private Sprite[] sprites;	
	
	/**
	 * Return the elapsed time since the last sprite was activated.
	 * 
	 * @return	A double that represents the elapsed time since the last sprite was activated.
	 */
	public double getTimeTillLastSprite(){
		return this.timeTillLastSprite;
	}
	
	/**
	 * Set the elapsed time since the last sprite was activated.
	 * 
	 * @param time
	 * 			A double that represents the desired elapsed time since the last sprite was activated.
	 */
	public void setTimeTillLastSprite(double time){
		this.timeTillLastSprite = time;
	}

	/************************************************ ADVANCE TIME ********************************************/
	
	// 				All methods here must be worked out defensively
	//				updates position and velocity of Mazub based on the current position, velocity, acceleration
	//				and a given time duration delta_t in seconds (0 < delta_t < 0.2)
	//				sx = vx_curr*delta_t (no acceleration, formula may change in future but never above the
	//					value described by this formula)
	//				sx = vx_curr*delta_t + 0.5*ax*delta_t^2 (with acceleration, formula may change in future but
	// 					never above the value described by this formula)
	//				x_new = x_curr + sx
	//				sy = vy_curr*delta_t (no acceleration, formula may change in future but never above the
	//					value described by this formula)
	//				sy = vy_curr*delta_t + 0.5*ay*delta_t^2 (with acceleration, formula may change in future but
	//					never above the value described by this formula)
	//				y_new = y_curr + sy
	//				ensure that the bottom-left pixel of Mazub stays at all times within the boundaries of the
	//					game world
	
	/**
	 * Advance time and update Mazub's position and velocity accordingly.
	 * 
	 * @param dt
	 * 			A double that represents the elapsed time.
	 * @post
	 */
	public void advanceTime(double dt){
		
		// Update horizontal velocity
		double newVx = this.getVx() + this.getAx() * dt;
		this.setVx( newVx );
		
		// Update vertical velocity
		double newVy = this.getVy() + this.getAy() * dt;
		this.setVy( newVy );
		
		// Update  horizontal position
		double sx = this.getVx() * dt + 0.5 * this.getAx() * Math.pow( dt , 2 );
		this.setPx( this.getPx() + 100 * sx );
		
		// Update vertical position
		double sy = this.getVy() * dt + 0.5 * this.getAy() * Math.pow( dt , 2 );
		this.setPy( this.getPy() + 100 * sy );
		
		// If Mazub hits the ground, stop falling
		if( isOnGround() ){
			stopFall();
		}
		
		if(!this.isMoving())
			this.setTimeTillLastMove(this.getTimeTillLastMove() + dt);
		this.setTimeTillLastSprite(this.getTimeTillLastSprite() + dt);
	}
	
	/**
	 * Return the elapsed time since the last move was made.
	 * 
	 * @return	A double that represents the elapsed time since the last move was made.
	 */
	public double getTimeTillLastMove(){
		return this.timeTillLastMove;
	}
	
	/**
	 * Set the elapsed time since the last move was made.
	 * 
	 * @param time
	 * 			A double that represents the desired elapsed time since the last move was made.
	 */
	public void setTimeTillLastMove(double time){
		this.timeTillLastMove = time;
	}

}
