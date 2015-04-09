package jumpingalien.model;

public class TerrainProperties {
	public TerrainProperties(boolean passable, int damage, double damageTime) {
		this.passable = passable;
		this.damage = damage;
		this.damageTime = damageTime;
	}
	
	// Variables
	private final boolean passable;
	private final int damage;
	private final double damageTime;	
	
	// Getters	
	public boolean isPassable(){
		return this.passable;
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public double getDamageTime(){
		return this.damageTime;
	}	
}
