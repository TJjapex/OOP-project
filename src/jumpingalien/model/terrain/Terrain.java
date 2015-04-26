package jumpingalien.model.terrain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration of Terrain types.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
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
	 * @param 	id
	 * 				The ID of the Terrain type
	 * @post	| new.getId() == id
	 */
	private Terrain(int id) {
		this.id = id;
	}
	
	/**
	 * Return the ID for this Terrain type.
	 * 
	 * @return	The ID for this Terrain type.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * A variable registering the ID of this Terrain type.
	 */
	private final int id;
	
	/**
	 * Return all different Terrain types in an ArrayList.
	 * 
	 * @return An ArrayList of all different Terrain types.
	 */
	public static List<Terrain> getAllTerrainTypes(){
		return new ArrayList<Terrain>(EnumSet.allOf(Terrain.class));
	}
	
	/**
	 * Return the Terrain enumeration element that has the given ID.
	 * 
	 * @param 	terrainTypeId
	 * 				The ID of the Terrain type.
	 * @return	The Terrain type that has the given ID.
	 * @throws 	IllegalArgumentException
	 * 				If no Terrain type with the given ID exists, an IllegalArgumentException will be thrown.
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
