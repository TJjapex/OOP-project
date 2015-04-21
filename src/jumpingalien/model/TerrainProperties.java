package jumpingalien.model;

public class TerrainProperties {
	public TerrainProperties(boolean passable, int damage, double damageTime, boolean instantDamage) {
		this.passable = passable;
		this.damage = damage;
		this.damageTime = damageTime;
		this.instantDamage = instantDamage;
	}
	
	// Variables
	private final boolean passable;
	private final int damage;
	private final double damageTime;	
	private final boolean instantDamage;
	
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
	public boolean isInstantDamage(){
		return this.instantDamage;
	}	
}
