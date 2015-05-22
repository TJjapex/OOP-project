package jumpingalien.model;

/**
 * An interface for jumpable Game objects.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public interface IJumpable {

	public boolean isJumping();	
	
	public void setJumping(boolean jumping);
	
	public void startJump();
	
	public void endJump();
	
}
