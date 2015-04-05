package jumpingalien.model.helper;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration of directions. The direction can be left or right.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

@Value
public enum Terrain {
	AIR(0, true, 0, Double.POSITIVE_INFINITY),
	SOLID(1, false, 0, Double.POSITIVE_INFINITY),
	WATER(2, true, 2, 0.2),
	MAGMA(3, true, 50, 0.2);
	
	private final int id;
	private final boolean passable;
	private final int damage;
	private final double damageTime;

	private Terrain(int id, boolean passable, int damage, double damageTime) {
		this.id = id;
		this.passable = passable;
		this.damage = damage;
		this.damageTime = damageTime;
	}
	
	public int getId(){
		return this.id;
	}
	
	public boolean isPassable(){
		return this.passable;
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public double getDamageTime(){
		return this.damageTime;
	}
	
	/* Returns all terrain types */
	public static List<Terrain> getAllTerrainTypes(){
		return new ArrayList<Terrain>(EnumSet.allOf(Terrain.class));
	}
	
}
