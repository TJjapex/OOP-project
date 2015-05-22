package jumpingalien.model;

/**
 * An interface for Tiles and Game objects joined together.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public interface IKind {
	
	public int getRoundedPositionX();
	
	public int getRoundedPositionY();
	
	public int getWidth();
	
	public int getHeight();
	
}
