/**
 * Object-oriented Programming: Mazub
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

public class Mazub {
	
	/* General */
	
	// the class Util provides methods for comparing doubles up to a fixed epsilon
	// write a test suite
	// connect graphical user interface (write class Facade)
	
	/* Constructor */
	
	// accepts an array of n images as parameter (n even, n >= 10)

	public Mazub() {
		
	}
	
	/* Size and positioning */
	
	// All methods here must be worked out defensively (using integer numbers)
	// Game world (X,Y) : 1024x768 pixels (origin bottom-left)
	// each pixel = 0.01m
	// Position of Mazub: bottom-left pixel of Mazub
	// Mazub's dimension (X_p,Y_p), depends on active sprite
	
	//inspect Mazub's location
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	//set Mazub's location
  
	private void setX(int x){
		this.x = x;
	}
	
	private void setY(int y){
		this.y = y;
	}
	
	//variables of Mazub's location
	
	private int x;
	private int y;
	
	//inspect Mazub's dimenion
	
	public int getWidth(){
		
	}
	
	public int getHeight(){
		
	}
	
	//set Mazub's dimension
	
	private void setWidth(){
		
	}
	
	private void setHeight(){
		
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
	
	public void startMoveLeft(){
		
	}
	public void startMoveRight(){
		
	}
	public void endMove() {
		
	}
	
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
	
	public void advanceTime(double amount){ // Double or float?
		// To do : update position, velocity
	}
	
	/* Characteristics */
	
	// All methods here must be worked out totally
	// velocity, acceleration, orientation, timing 
	// type double (not NaN, may be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY)
	// rounding down to integer value (at the end!) to determine Mazub's effective position
	
	public double getVx(){
		return this.vx;
	}
	
	public double getVy(){
		
	}
	
	public double getAx(){
		return this.ax;
	}
	
	public double getAy(){
		
	}
	
	public getOrientation(){ // Output of which type?
		
	}
	
	public void setVx(double vx){
		this.vx = vx;
	}
	
	private double vx_init;
	private double vx_max;
	private double vx;
	
	private double vy_init;
	private double vy;
	private double ay;
	
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
		
	}
	
	public void endJump() {
		
	}
	
	/* Ducking */
	
	// All methods here must be worked out defensively
	// affects Mazub's dimension (X_p,Y_p)
	// restricts vx_max to 1 m/s (no acceleration possible)
	
	public void startDuck(){
		
	}
	
	public void endDuck(){
		
	}	
	
	/* Character size and animation */
	
	// All methods here must be worked out nominally
	// no formal documentation required
	// class Sprite is provided
	// multiple sprites for moving to the right/left (same amount), alternate (75ms) and repeat
	// it must be possible to turn to other algorithms for displaying successive images of a Mazub during some period of time
	
	public int getCurrentSprite(){
		return -1; // returns Sprite of (X_p,Y_p) pixels
	}
	
}
