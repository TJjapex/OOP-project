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
	
	// Values
	AIR		(0),
	SOLID	(1),
	WATER	(2),
	MAGMA	(3);
	
	
	// Variables
	private final int id;
	
	// Constructor
	private Terrain(int id) {
		this.id = id;
	}
	
	
	// Getters
	public int getId(){
		return this.id;
	}
	
	/* Returns all terrain types */
	public static List<Terrain> getAllTerrainTypes(){
		return new ArrayList<Terrain>(EnumSet.allOf(Terrain.class));
	}
}
