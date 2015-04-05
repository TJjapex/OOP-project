package jumpingalien.model.helper;
import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration of directions. The direction can be left or right.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

// NOT USED YET

@Value
public enum TileType {
	AIR(0), 
	SOLID(1),
	WATER(2),
	MAGMA(3);
	
	private int id;

	private TileType(int id) {
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
}
