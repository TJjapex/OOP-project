package jumpingalien.model.terrain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration of Terrain types.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

@Value
public enum Terrain {
	AIR		(0),
	SOLID	(1),
	WATER	(2),
	MAGMA	(3);
	
	/**
	 * Constructor for the Terrain enumeration.
	 * 
	 * @param id
	 * 		The ID of the Terrain type
	 * @post
	 * 		new.getId() == id
	 */
	private Terrain(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the ID for this Terrain type.
	 * 
	 * @return
	 * 		The id for this Terrain type.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * A variable registering the id of this Terrain type.
	 */
	private final int id;
	
	/**
	 * Returns all different Terrain types in an ArrayList.
	 * 
	 * @return
	 * 			All different Terrain types in an ArrayList
	 * 
	 */
	public static List<Terrain> getAllTerrainTypes(){
		return new ArrayList<Terrain>(EnumSet.allOf(Terrain.class));
	}
	
	/**
	 * Returns the Terrain enumeration element that has the given id
	 * @param terrainTypeId
	 * 			The id of the Terrain type
	 * @return
	 * 			The Terrain type that has the given id
	 * 			| result.getId() == terrainTypeId
	 * @throws IllegalArgumentException
	 * 			If no Terrain type with the given id exists, an IllegalArgumentException will be thrown.
	 */
	public static Terrain idToType(int terrainTypeId) throws IllegalArgumentException{
		for(Terrain terrain : getAllTerrainTypes()){
			if(terrain.getId() == terrainTypeId){
				return terrain;
			}
		}
		throw new IllegalArgumentException("Terrain type for given terrain type index not defined!");
	}
}
