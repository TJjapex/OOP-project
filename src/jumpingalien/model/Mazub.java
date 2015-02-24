package jumpingalien.model;
import jumpingalien.util.Sprite;

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

	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		setX(pixelLeftX + 10);
		setY(pixelBottomY+ 10);
		this.sprites = sprites;
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
	
	public void startMoveLeft(){
		this.setVx( this.vx_init );
		this.setAx( 0.9 );
		this.setOrientation(-1);
	}
	public void startMoveRight(){
		this.setVx( this.vx_init );
		this.setAx( 0.9 );
		this.setOrientation(1);
	}
	public void endMove() {
		this.setVx(0);
		this.setAx(0);
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
	
	public void advanceTime(double dt){
		// To do : update position, velocity
		
		// Update horizontal velocity
		double newVx = Math.max( this.getVx() + this.ax * dt, this.vx_max);
		this.setVx( newVx );
		
		// Update vertical velocity
		if( this.getY() > 0 ){
			double newVy = this.getVy() + this.ay * dt;
			this.setVy( newVy );
		}
		
		
		// Update  horizontal position
		double sx = this.getVx() * dt + 0.5 * this.ax * Math.pow( dt , 2 );
		this.setPx( this.getPx() + this.getOrientation() * sx );
		this.setX( (int) Math.round( this.getPx() ) );
		
		// Update vertical position
		if( this.getY() > 0 ){
			this.setPy( Math.max( this.getPy() + this.getVy()*dt + 0.5*this.getAy()*Math.pow(dt, 2) , 0.0) );
			this.setY( (int) Math.round( this.getPy() ) );
		}else{
			this.setY( 0 ); // Is normaal gezien overbodig aangezien Y >= 0;
			this.setVy( 0 );
			this.setAy( 0 );
		}
	}
	
	/* Characteristics */
	
	// All methods here must be worked out totally
	// velocity, acceleration, orientation, timing 
	// type double (not NaN, may be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY)
	// rounding down to integer value (at the end!) to determine Mazub's effective position
	public double getPx(){
		return this.px;
	}
	
	public double getPy(){
		return this.py;
	}

	public void setPx(double px){
		this.px = px;
	}
	
	public void setPy(double py){
		this.py = py;
	}

	public double getVx(){
		return this.vx;
	}
	
	public double getVy(){
		return this.vy;
	}
	
	public double getAx(){
		return this.ax;
	}
	
	public double getAy(){
		return this.ay;
	}
	
	public int getOrientation(){ // Output of which type? 
		return this.orientation;
	}
	
	public void setOrientation(int orientation){ // Output of which type? 
		this.orientation = orientation;
	}
	
	public void setVx(double vx){
		this.vx = vx;
	}
	
	public void setVy(double vy){
		this.vy = vy;
	}
	
	public void setAx(double ax){
		this.ax = ax;
	}
	
	public void setAy(double ay){
		this.ay = ay;
	}
	
	
	public void setVxMax(double vx_max){
		this.vx_max = vx_max;
	}
	
	public double getVxMax(){
		return this.vx_max;
	}
	
	private double px;
	private double py;
	
	private double vx_init = 1.0;
	private double vx_max = 3.0; // Getters en setters nodig?
	private double vx;
	private double ax = 0; // Getters en setters nodig?
	
	private double vy_init;
	private double vy;
	private double ay = -10.0;
	
	private int orientation; // -1 = links, 1 = rechts? Da's handig bij formules 
	
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
		this.setVy( 8.0 );
		this.setAy( -10 );
	}
	
	public void endJump() {
		if( this.getVy() > 0 ){
			this.setVy(0);
		}
	}
	
	/* Ducking */
	
	// All methods here must be worked out defensively
	// affects Mazub's dimension (X_p,Y_p)
	// restricts vx_max to 1 m/s (no acceleration possible)
	
	public void startDuck(){
		setVxMax(1.0);
	}
	
	public void endDuck(){
		setVxMax(3.0);
	}	
	
	/* Character size and animation */
	
	// All methods here must be worked out nominally
	// no formal documentation required
	// class Sprite is provided
	// multiple sprites for moving to the right/left (same amount), alternate (75ms) and repeat
	// it must be possible to turn to other algorithms for displaying successive images of a Mazub during some period of time
	
	public Sprite getCurrentSprite(){
		return null; // returns Sprite of (X_p,Y_p) pixels
		
		
	}
	
	private Sprite[] sprites;
	
}
