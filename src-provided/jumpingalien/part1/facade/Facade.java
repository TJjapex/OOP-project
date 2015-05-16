package jumpingalien.part1.facade;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.model.Mazub;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;

public class Facade implements IFacade {
	/**
	 * Create an instance of Mazub.
	 * 
	 * @param 	pixelLeftX
	 *            	The x-location of Mazub's bottom left pixel.
	 * @param 	pixelBottomY
	 *            	The y-location of Mazub's bottom left pixel.
	 * @param	sprites
	 *        		The array of sprite images for Mazub.
	 * @throws 	ModelException
	 * 			 	The given horizontal or vertical position is not valid, or the sprite size of at least one
	 * 				sprite is not valid.
	 * 			 	| !Mazub.isValidRoundedPositionX(pixelLeftX) || !Mazub.isValidRoundedPositionY(pixelLeftY) ||
	 * 				| ( for some sprite in sprites: !Mazub.isValidWidth(sprite.getWidth()) ) ||
	 * 				| ( for some sprite in sprites: !Mazub.isValidHeight(sprite.getHeight()) )
	 */
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws ModelException{
		try{
			return new Mazub(pixelLeftX, pixelBottomY, sprites);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
		
	}

	/**
	 * Return the current location of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the location.
	 * 
	 * @return an array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given alien's bottom left pixel in the world.
	 */
	public int[] getLocation(Mazub alien){
		return new int[] {alien.getRoundedPositionX(), alien.getRoundedPositionY()};
	}

	/**
	 * Return the current velocity (in m/s) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the velocity.
	 * 
	 * @return an array, consisting of 2 doubles {vx, vy}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         velocity, in units of m/s.
	 */
	public double[] getVelocity(Mazub alien){
		return new double[] {alien.getVelocityX(), alien.getVelocityY()};	
	}

	/**
	 * Return the current acceleration (in m/s^2) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the acceleration.
	 * 
	 * @return an array, consisting of 2 doubles {ax, ay}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         acceleration, in units of m/s^2.
	 */
	public double[] getAcceleration(Mazub alien){
		return new double[] {alien.getAccelerationX(), alien.getAccelerationY()};
	}

	/**
	 * Return the current size of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the size.
	 * 
	 * @return An array, consisting of 2 integers {w, h}, that represents the
	 *         current width and height of the given alien, in number of pixels.
	 */
	public int[] getSize(Mazub alien){
		return new int[] {alien.getWidth(), alien.getHeight()};	
	}

	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @param alien
	 *            The alien for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given alien, determined by its
	 *         state as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Mazub alien){
		return alien.getCurrentSprite();
		
	}

	/**
	 * Make the given alien jump.
	 * 
	 * @param alien
	 *            The alien that has to start jumping.
	 */
	public void startJump(Mazub alien){
		alien.startJump();
	}

	/**
	 * End the given alien's jump.
	 * 
	 * @param alien
	 *            The alien that has to stop jumping.
	 */
	public void endJump(Mazub alien){
		try{
			alien.endJump();
		}catch( IllegalStateException exc ){
		}
		
	}

	/**
	 * Make the given alien move left.
	 * 
	 * @param alien
	 *            The alien that has to start moving left.
	 */
	public void startMoveLeft(Mazub alien){
		alien.startMove(Orientation.LEFT);
	}

	/**
	 * End the given alien's left move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving left.
	 */
	public void endMoveLeft(Mazub alien){
		alien.endMove(Orientation.LEFT);
	}

	/**
	 * Make the given alien move right.
	 * 
	 * @param alien
	 *            The alien that has to start moving right.
	 */
	public void startMoveRight(Mazub alien){
		alien.startMove(Orientation.RIGHT);
	}

	/**
	 * End the given alien's right move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving right.
	 */
	public void endMoveRight(Mazub alien){
		alien.endMove(Orientation.RIGHT);
	}

	/**
	 * Make the given alien duck.
	 * 
	 * @param alien
	 *            The alien that has to start ducking.
	 */
	public void startDuck(Mazub alien){
		try{
			alien.startDuck();
		}catch(IllegalStateException exc){
		}
		
	}

	/**
	 * End the given alien's ducking.
	 * 
	 * @param alien
	 *            The alien that has to stop ducking.
	 */
	public void endDuck(Mazub alien){
		try{
			alien.endDuck();
		}catch(IllegalStateException exc){
		}
		
	}

	/**
	 * Advance the state of the given alien by the given time period.
	 * 
	 * @param alien
	 *            The alien whose time has to be advanced.
	 * @param dt
	 *            The time interval (in seconds) by which to advance the given
	 *            alien's time.
	 * @throws ModelException
	 * 				The given dt is greater than 0.2 or smaller than 0
	 * 				| dt < 0 || dt > 0.2
	 */
	public void advanceTime(Mazub alien, double dt) throws ModelException{
		try{
			//alien.advanceTime(dt);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal time amount given!");
		}
		
	}


}
