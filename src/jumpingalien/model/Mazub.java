package jumpingalien.model;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

/**
 * Object-oriented Programming: Mazub
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

public class Mazub {
	
	private static int WINDOW_WIDTH = 1024; // Mss beter niet in hoofdletters?
	private static int WINDOW_HEIGHT = 768;
	private static double GRAVITY = -10.0;
	
	private boolean ducking = false;
	
	// Moeten voor onderstaande vars ook setters en getters? Kdenk van wel eigenlijk :s
	private int currentSpriteIteration = 0;
	private double timeTillLastSprite = 0;
	private double timeTillLastMove = 0; // Time until last movement
	
	/* General */
	
	// the class Util provides methods for comparing doubles up to a fixed epsilon
	// write a test suite
	// connect graphical user interface (write class Facade)
	
	/* Constructor */
	
	// accepts an array of n images as parameter (n even, n >= 10)

	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		this.setPx(pixelLeftX);
		this.setPy(pixelBottomY);
		
		this.setOrientation(Orientation.LEFT);
		
		// nog check doen of een even aantal is? Nominaal, totaal of defensief?
		this.sprites = sprites;
	}
	
	
	/* Size and positioning */
	
	// All methods here must be worked out defensively (using integer numbers) 
	// Wat moeten we dan doen? :p
	
	
	// Game world (X,Y) : 1024x768 pixels (origin bottom-left)
	// each pixel = 0.01m
	// Position of Mazub: bottom-left pixel of Mazub
	// Mazub's dimension (X_p,Y_p), depends on active sprite
	
	//inspect Mazub's location
	
	public int getX(){
		return (int) Math.round(this.getPx());
	}
	
	public int getY(){
		return (int) Math.round(this.getPy());
	}
	
	//variables of Mazub's location
	
	private int x;
	private int y;
	
	//inspect Mazub's dimenion
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	//set Mazub's dimension
	
	private void setWidth(){
		this.width = width;
	}
	
	private void setHeight(){
		this.width = height;
	}
	
	//variables of Mazub's dimension
	
	private int width;
	private int height;
	
	/* Running */
	
	// All methods here must be worked out nominally
	// vx_init = 1 m/s	(may change in future but never below 1 m/s, instance variable)
	// ax = 0.9 m/s^2	(may change in future, class variable)
	// vx_max = 3 m/s	(may change in future but never below vx_init, instance variable)
	// startMove:
	// 	vx_new = vx_curr + ax*delta_t (for vx_init < vx_curr < vx_max)
	// endMove:
	// 	vx_new = 0
	
	public void startMove(Orientation orientation){
		this.setOrientation(orientation);
		this.setVx( orientation.getDirection() * this.vx_init );
		this.setAx( orientation.getDirection() * 0.9 );
	}
	
	public void endMove() {
		this.setVx(0);
		this.setAx(0);
		
		this.timeTillLastMove = 0;
	}

	public boolean isMoving(){
		return !Util.fuzzyEquals(this.getVx(), 0);
	}
	
	public boolean hasMovedInLastSecond(){ // Slechte naam
		return Util.fuzzyLessThanOrEqualTo(timeTillLastMove, 1.0);
	}
	
	
	
	/* Jumping and falling */
	
	// All methods here must be worked out defensively
	// startJump:
	//	vy_init = 8 m/s 	(will not change in future)
	//	vy_new = vy_current + ay*delta_t
	// endJump:
	//  vy_new = 0 m/s 	(if vy_curr > 0)
	// while (y != 0):
	//	ay = -10 m/s^2	(will not change in future)
	
	public void startJump(){
		this.setVy( 8.0 ); // Deze waarden in constanten steken?
		this.setAy( GRAVITY ); 
	}
	
	public void endJump() {
		if( this.getVy() > 0 ){
			this.setVy(0);
		}
	}
	
	public boolean isJumping(){
		return !Util.fuzzyEquals(this.getVy(), 0);
	}
	
	/* Ducking */
	
	// All methods here must be worked out defensively
	// affects Mazub's dimension (X_p,Y_p)
	// restricts vx_max to 1 m/s (no acceleration possible)
	
	public void startDuck(){
		this.setVxMax(1.0);
		this.setDucking(true);
	}
	
	public void endDuck(){
		this.setVxMax(3.0);
		this.setDucking(false);
	}	
	
	public boolean  isDucking(){
		return this.ducking;
	}
	
	public void setDucking(boolean ducking){
		this.ducking = ducking;
	}
	
	/* Characteristics */
	
	// All methods here must be worked out totally
	// velocity, acceleration, orientation, timing 
	// type double (not NaN, may be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY)
	// rounding down to integer value (at the end!) to determine Mazub's effective position
	
	// Position
	public double getPx(){
		return this.px;
	}
	
	public double getPy(){
		return this.py;
	}

	private void setPx(double px) throws IllegalPxException{
		//if(! isValidPx(px)) oeps positie in doubles moest totaal, niet defensief
		//	throw new IllegalPxException(px);
		this.px = Math.min(Math.max(px, 0), WINDOW_WIDTH - 1);
	}
	
	private void setPy(double py) throws IllegalPyException{
		//if(! isValidPy(py))
		//	throw new IllegalPyException(py);
		this.py = Math.min(Math.max(py, 0), WINDOW_HEIGHT - 1);
	}
	
	
	
	// ?? Uiteindelijk niet nodig omdat X en Y gewoon afgeronde px en py zijn?
	public static boolean isValidPx(double px) {
		return Util.fuzzyGreaterThanOrEqualTo(px, 0) && Util.fuzzyLessThanOrEqualTo(px, WINDOW_WIDTH);
	}
	
	public static boolean isValidPy(double py) {
		return Util.fuzzyGreaterThanOrEqualTo(py, 0) && Util.fuzzyLessThanOrEqualTo(py, WINDOW_HEIGHT);
	}
	
	

	private double px;
	private double py;

	// Velocity
	public double getVx(){
		return this.vx;
	}
	
	public double getVy(){
		return this.vy;
	}
	
	private void setVx(double vx){
		this.vx = Math.max(Math.min( vx , this.getVxMax()), -this.getVxMax());
	}
	
	private void setVy(double vy){
		this.vy = vy;
	}
	
	private double vx;
	private double vy;
	private double vx_init = 1.0;
	private double vy_init;	
	
	// Maximum velocity
	public double getVxMax(){
		return this.vx_max;
	}
	
	private void setVxMax(double vx_max){ // Slechte naam
		this.vx_max = vx_max;
	}
	
	private double vx_max = 3.0;
	
	// Acceleration
	public double getAx(){
		return this.ax;
	}
	
	public double getAy(){
		return this.ay;
	}
	
	private void setAx(double ax){
		this.ax = ax;
	}
	
	private void setAy(double ay){
		this.ay = ay;
	}
		
	private double ax;
	private double ay;
	
	// Orientation
	public Orientation getOrientation(){ // Output of which type? 
		return this.orientation;
	}
	
	public void setOrientation(Orientation orientation){ // Output of which type? 
		this.orientation = orientation;
	}	
	
	private Orientation orientation;
	
	/* Character size and animation */
	
	// All methods here must be worked out nominally
	// no formal documentation required
	// class Sprite is provided
	// multiple sprites for moving to the right/left (same amount), alternate (75ms) and repeat
	// it must be possible to turn to other algorithms for displaying successive images of a Mazub during some period of time
	
	public Sprite getCurrentSprite(){
		
		// Moet mooier/efficienter/korter
		
		//if(this.sprites != null){
		//	return this.sprites[0]; // returns Sprite of (X_p,Y_p) pixels
		//}
		
		// m bepalen
		
		int m = ( this.sprites.length - 8) / 2; // Als length even is geeft dit altijd een correct getal -> moeten nog check doen
		
		// Voor animatie
		while(this.timeTillLastSprite > 0.075){ // Util fuzzy?
			this.currentSpriteIteration += 1; // Setters and getters...
			this.currentSpriteIteration %= m;
			timeTillLastSprite -= 0.075;
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
			
		}else{ // MOVIÈNG
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
					index = 8 + this.currentSpriteIteration;
				}else{ // LEFT
					index = 8 + m + this.currentSpriteIteration;
				}
			}
		}
		
		
		
		return this.sprites[index];
		
	}
	
	private Sprite[] sprites;	

	/* Advance time */
	
	// 	All methods here must be worked out defensively
	//	updates position and velocity of Mazub based on the current position, velocity, acceleration
	//	and a given time duration delta_t in seconds (0 < delta_t < 0.2)
	//	sx = vx_curr*delta_t (no acceleration, formula may change in future but never above the value described by this formula)
	//	sx = vx_curr*delta_t + 0.5*ax*delta_t^2 (with acceleration, formula may change in future but never above the value described by this formula)
	//	x_new = x_curr + sx
	//	sy = vy_curr*delta_t (no acceleration, formula may change in future but never above the value described by this formula)
	//	sy = vy_curr*delta_t + 0.5*ay*delta_t^2 (with acceleration, formula may change in future but never above the value described by this formula)
	//	y_new = y_curr + sy
	//	ensure that the bottom-left pixel of Mazub stays at all times within the boundaries of the game world
	
	public void advanceTime(double dt){
		// To do : update position, velocity
		
		// Update horizontal velocity
		double newVx = this.getVx() + this.getAx() * dt;
		this.setVx( newVx );
		
		// Update vertical velocity
		//if( this.getPy() > 0 ){ // If Mazub is not on the ground, update vertical velocity ->not needed anymore
		double newVy = this.getVy() + this.getAy() * dt;
		this.setVy( newVy );
		//}
		
		// Update  horizontal position
		double sx = this.getVx() * dt + 0.5 * this.getAx() * Math.pow( dt , 2 );
		this.setPx( this.getPx() + sx *100 );
		
		// Update vertical position
		this.setPy( Math.max( this.getPy() + 100*this.getVy()*dt + 100*0.5*this.getAy()*Math.pow(dt, 2) , 0) );
		if( Util.fuzzyEquals(this.getY(), 0) ){ // If Mazub hits the ground, stop falling
			this.setVy( 0 );
			this.setAy( 0 );
		}
		
		if(!this.isMoving())
			this.timeTillLastMove += dt; // Needs setter
		this.timeTillLastSprite += dt;
	}
}
