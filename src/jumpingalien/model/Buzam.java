package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.program.Program;
import jumpingalien.util.Sprite;

/**
 * A class of Buzams, game objects in the game world of Mazub. 
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Buzam extends Mazub{

	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * TODO: documentation
	 * 
	 * @param pixelLeftX
	 * @param pixelBottomY
	 * @param velocityXInit
	 * @param velocityYInit
	 * @param velocityXMaxRunning
	 * @param accelerationXInit
	 * @param sprites
	 * @param nbHitPoints
	 * @param maxNbHitPoints
	 * @param program
	 * @throws IllegalPositionXException
	 * @throws IllegalPositionYException
	 * @throws IllegalWidthException
	 * @throws IllegalHeightException
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit, double velocityXMaxRunning,
				 double accelerationXInit, Sprite[] sprites, int nbHitPoints, int maxNbHitPoints, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException {
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMaxRunning, accelerationXInit, sprites,
			  nbHitPoints, maxNbHitPoints, program);
		
	}
	
	/**
	 * TODO: documentation
	 * 
	 * @param pixelLeftX
	 * @param pixelBottomY
	 * @param sprites
	 * @param program
	 * @throws IllegalPositionXException
	 * @throws IllegalPositionYException
	 * @throws IllegalWidthException
	 * @throws IllegalHeightException
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 500, 500, program);
	}
	
	/**
	 * TODO: documentation
	 * 
	 * @param pixelLeftX
	 * @param pixelBottomY
	 * @param sprites
	 * @throws IllegalPositionXException
	 * @throws IllegalPositionYException
	 * @throws IllegalWidthException
	 * @throws IllegalHeightException
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, sprites, null);
	}
	
	/**
	 * Returns a string class name of the object, used for toString method in GameObject
	 */
	@Override
	public String getClassName() {
		return "Buzam";
	}
	
	/******************************************************** WORLD ****************************************************/
	
	@Override
	protected void addToWorld(){
		this.getWorld().buzams.add(this);
	}
	
	@Override
	protected void removeFromWorld(World world){
		assert this != null && !this.hasWorld();
		assert world.hasAsGameObject(this);
		System.out.println("Buzam removed from World!");
		world.buzams.remove(this);
	}
	
	@Override
	protected boolean hasAsWorld(World world){
		return Buzam.getAllInWorld(world).contains(this);
	}
	
	public static int getNbInWorld(World world){
		return Buzam.getAllInWorld(world).size();
	}
	
	public static Set<Buzam> getAllInWorld(World world){
		HashSet<Buzam> buzamsClone =  new HashSet<Buzam>(world.buzams);
		return buzamsClone;
	}
	
}
