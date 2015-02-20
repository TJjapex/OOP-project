/**
 * 
 * @author
 *
 */
public class Mazub {

	public Mazub() {
		
	}
	
	/* Positioning */
	// All methods here must be worked out defensively
	
	
	//  arrays?
//	private int getX(){
//		return this.x;
//	}
//	private void setX(int x){
//		this.x = x;
//	}
//	private int x;
//	
//	private int getY(){
//		return this.y;
//	}
//	private void setY(int y){
//		this.y = y;
//	}
//	private int y;
//	
//	
//	
//	private int getWidth(){
//		
//	}
//	private int getHeight(){
//		
//	}
//	private void setWidth(){
//		
//	}
//	private void setHeight(){
//		
//	}
//	
//	public int[] getLocation(){
//		return location;
//	}
//	public void setLocation(int[] location) {
//		this.location = location;
//	}
//	private int[] location;
//	
//	
//	public int[] getSize(){
//		return size;
//	}
//	public void setSize(int[] size){
//		this.size = size;
//	}
//	private int[] size;
//	
	
	/* Moving */
	// Nominally
	public void startMoveLeft(){
			
	}
	public void startMoveRight(){
		
	}
	public void endMove() {
		
	}
		
	// Acceleration - horizontal and verticla in arrays?
	// TOTAL
//	public double getVelocity(){
//		return this.vx;
//	}
//	public void setVelocity(double vx){
//		this.vx = vx;
//	}
//	private double vinit;
//	private double vmax;
//	private double vx;
//	

	
	/* Jumping */
	// DEFENSIVE
	public void startJump(){
		
	}
	public void endJump() {
		
	}
	
	// Vertical acceleration
	// TOTAL
	//private double vy;
	
	/* Ducking */
	// DEFENSIVE
	public void startDuck(){
		
	}
	public void endDuck(){
		
	}	
	
	/**
	 * DEFENSIVE
	 * @param amount
	 */
	public void advanceTime(double amount){ // Double or float?
		// To do : update position, velocity
	}
	
	/* Character size and animation ¨*/
	/**
	 * NOMINAL
	 * @return
	 */
	public int getCurrentSprite(){
		return -1;
	}
	
	
 
}
