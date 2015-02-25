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
		this.setPx(pixelLeftX);
		this.setPy(pixelBottomY);
		
		this.setOrientation(Orientation.LEFT);
		
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
		this.setVy( 8.0 );
		this.setAy( -10.0 );
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

	private void setPx(double px){
		this.px = px;
	}
	
	private void setPy(double py){
		this.py = py;
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
		this.vx = vx;
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
		
		// Dit is gewoon om een sprite te hebben om te debuggen.
		if(this.sprites != null){
			return this.sprites[0]; // returns Sprite of (X_p,Y_p) pixels
		}
		return null;		
		
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
		double newVx = Math.min( this.getVx() + this.ax * dt, this.vx_max);
		this.setVx( newVx );
		
		// Update vertical velocity
		if( this.getY() > 0 ){
			double newVy = this.getVy() + this.ay * dt;
			this.setVy( newVy );
		}
		
		// Update  horizontal position
		double sx = this.getVx() * dt + 0.5 * this.ax * Math.pow( dt , 2 );
		this.setPx( this.getPx() + sx *100 );
		
		// Update vertical position
		this.setPy( Math.max( this.getPy() + 100*this.getVy()*dt + 100*0.5*this.getAy()*Math.pow(dt, 2) , 0) );
		if(this.getY() == 0 ){
			this.setVy( 0 );
			this.setAy( 0 );
		}
	}
}
