package jumpingalien.model.interfaces;

import jumpingalien.model.helper.Orientation;

/**
 * An interface for movable Game objects.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public interface IMovable {
	
	public void startMove(Orientation orientation);
	
	public void endMove(Orientation orientation);
	
	public boolean isMoving();
	
	public boolean isMoving(Orientation orientations);
	
}
