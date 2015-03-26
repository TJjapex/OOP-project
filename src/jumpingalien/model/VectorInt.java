package jumpingalien.model;

import jumpingalien.util.Util;

public class VectorInt {
	public VectorInt(int x, int y){
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	private int x;
	private int y;
	
	public int[] toArray(){
		return new int[]{getX(), getY()};
	}
	
	@Override
	public int hashCode() {
		return this.getX() + this.getY();
	}

	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof VectorInt)){
			return false;
		}
		
		return  this.getX() == ((VectorInt) other).getX() && 
				this.getY() == ((VectorInt) other).getY();
	}

}
