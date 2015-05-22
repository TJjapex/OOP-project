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
public class Buzam extends Mazub implements IProgrammable{

	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Buzam.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Buzam's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Buzam's bottom left pixel.
	 * @param	velocityXInit
	 * 				The initial horizontal velocity of Buzam.
	 * @param	velocityYInit
	 * 				The initial vertical velocity of Buzam.
	 * @param	velocityXMaxRunning
	 * 				The maximal horizontal velocity of Buzam when he's running.
	 * @param	accelerationXInit
	 * 				The initial horizontal acceleration of Buzam.
	 * @param 	sprites
	 * 				The array of sprite images for Buzam.
	 * @param	nbHitPoints
	 * 				The initial number of Buzam's hit points.
	 * @param	maxNbHitPoints
	 * 				The maximal number of Buzam's hit points.
	 * @param 	program
	 * 				The Program Buzam should execute.
	 * @effect	| super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMaxRunning, accelerationXInit, 
	 * 			|		sprites,nbHitPoints, maxNbHitPoints, program) 
	 * @throws	IllegalPositionXException
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				| ! isValidPositionY(positionY)
	 * @throws	IllegalWidthException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, double velocityXInit, double velocityYInit, double velocityXMaxRunning,
				 double accelerationXInit, Sprite[] sprites, int nbHitPoints, int maxNbHitPoints, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException {
		
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit, velocityXMaxRunning, accelerationXInit, sprites,
			  nbHitPoints, maxNbHitPoints, program);
		
	}
	
	/**
	 * Initialize a Buzam with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, initial number of hit points and maximal number of hit points.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Buzam's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Buzam's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Buzam.
	 * @param 	program
	 * 				The Program Buzam should execute.
	 * @effect	| this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 500, 500, program)
	 * @throws	IllegalPositionXException
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				| ! isValidPositionY(positionY)
	 * @throws	IllegalWidthException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites, Program program)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 500, 500, program);
	}
	
	/**
	 * Initialize a Buzam with default initial horizontal velocity, initial vertical velocity, maximal horizontal
	 * velocity, initial horizontal acceleration, initial number of hit points and maximal number of hit points and
	 * no Program.
	 * 
	 * @param 	pixelLeftX
	 * 				The x-location of Buzam's bottom left pixel.
	 * @param 	pixelBottomY
	 * 				The y-location of Buzam's bottom left pixel.
	 * @param 	sprites
	 * 				The array of sprite images for Buzam.
	 * @effect	| this(pixelLeftX, pixelBottomY, sprites, null)
	 * @throws	IllegalPositionXException
	 * 				| ! isValidPositionX(positionX)
	 * @throws	IllegalPositionYException
	 * 				| ! isValidPositionY(positionY)
	 * @throws	IllegalWidthException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, sprites, null);
	}
	
	/******************************************************** WORLD ****************************************************/
	
	/**
	 * Add a Buzam to the given World.
	 * 
	 * @post	| new.getWorld().hasAsGameObject(this) == true
	 */
	@Override
	protected void addToWorld(){
		this.getWorld().buzams.add(this);
	}
	
	/**
	 * Remove the Buzam from the given World.
	 * 
	 * @param	world
	 * 				The World to remove the Buzam from.
	 * @pre		| this != null && !this.hasWorld()
	 * @pre		| world.hasAsGameObject(this)
	 * @post	| world.hasAsGameObject(this) == false
	 */
	@Override
	protected void removeFromWorld(World world){
		assert this != null && !this.hasWorld();
		assert world.hasAsGameObject(this);
		
		world.buzams.remove(this);
	}
	
	/**
	 * Check whether or not the Buzam has the given World as its World.
	 * 
	 * @param	world
	 * 				The World to check.
	 * @return	| result == ( Buzam.getAllInWorld(world).contains(this) )
	 */
	@Override
	protected boolean hasAsWorld(World world){
		return Buzam.getAllInWorld(world).contains(this);
	}
	
	/**
	 * Return the number of Buzams in the given World.
	 * 
	 * @param 	world
	 * 				The World to check the number of Buzams for.
	 * @return	| result == ( Buzam.getAllInWorld(world).size() )
	 */
	public static int getNbInWorld(World world){
		return Buzam.getAllInWorld(world).size();
	}
	
	/**
	 * Return all the Buzams in the given World.
	 * 
	 * @param 	world
	 * 				The World to check.
	 * @return	A Hashset containing all Buzams in the given World.
	 */
	public static Set<Buzam> getAllInWorld(World world){
		HashSet<Buzam> buzamsClone =  new HashSet<Buzam>(world.buzams);
		return buzamsClone;
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Advance the Buzam's Program and update his horizontal and vertical position and velocity
	 * for the given time interval.
	 * 
	 * @param	dt
	 * 				A double that represents the elapsed in-game time.
	 * @effect	| advanceProgram()
	 * @effect	| update(dt)	
	 */
	@Override
	public void doMoveProgram(double dt){
		
		/* Advance Program */
		this.advanceProgram();
		
		/* Update position and velocity etc. */
		this.doMove(dt);
		
	}
	
	/******************************************************** STRING ***************************************************/

	/**
	 * Return the name of the Class as a String, used for the toString method in GameObject.
	 *
	 * @return	The name of the Class as a String.	
	 */
	@Override
	public String getClassName() {
		return "Buzam";
	}
	
}
