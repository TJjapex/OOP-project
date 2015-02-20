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
	
	
	// Ik denk dat we beter losse variabelen gebruiken ipv arrays, is simpeler? Voor bv positie en snelheid
	
	
	//  losse variabelen?
//  public int getX(){
//		return this.x;
//	}
//	private void setX(int x){
//		this.x = x;
//	}
//	private int x;
//	
//	public int getY(){
//		return this.y;
//	}
//	private void setY(int y){
//		this.y = y;
//	}
//	private int y;
//	
//	
//	
//	public int getWidth(){
//		
//	}
//	public int getHeight(){
//		
//	}
//	private void setWidth(){
//		
//	}
//	private void setHeight(){
//		
//	}
//	
	
	
// of arrays?
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
//	public double getVx(){
//		return this.vx;
//	}
//	public void setVx(double vx){
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
