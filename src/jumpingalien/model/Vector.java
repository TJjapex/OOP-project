package jumpingalien.model;

import jumpingalien.util.Util;

public class Vector {
	public Vector(int x, int y){
		setX((double) x);
		setY((double) y);
	}
	
	public Vector(double x, double y){
		setX(x);
		setY(y);
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	private double x;
	private double y;
	
	@Override
	public int hashCode() {
		return (int) (this.getX() + this.getY());
	}

	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof Vector)){
			return false;
		}
		
		return  Util.fuzzyEquals(this.getX(), ((Vector) other).getX()) && 
				Util.fuzzyEquals(this.getY(), ((Vector) other).getY());
	}
}
