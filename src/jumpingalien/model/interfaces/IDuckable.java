package jumpingalien.model.interfaces;

/**
 * An interface for duckable Game objects.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public interface IDuckable {
	
	public boolean isDucking();
	
	public void setDucking(boolean ducking);
	
	public void startDuck();
	
	public void endDuck();
	
}
