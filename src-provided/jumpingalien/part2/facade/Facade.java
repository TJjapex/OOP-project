package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.helper.Orientation;

public class Facade implements IFacadePart2 {

	
	/*************************************** PART 1 *****************************************/
	
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
	public int[] getLocation(GameObject alien){
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
	public double[] getVelocity(GameObject alien){
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
	public double[] getAcceleration(GameObject alien){
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
	public int[] getSize(GameObject alien){
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
	public Sprite getCurrentSprite(GameObject alien){
		return alien.getCurrentSprite();
		
	}

	/**
	 * Make the given alien jump.
	 * 
	 * @param alien
	 *            The alien that has to start jumping.
	 */
	public void startJump(GameObject alien){
		alien.startJump();
	}

	/**
	 * End the given alien's jump.
	 * 
	 * @param alien
	 *            The alien that has to stop jumping.
	 */
	public void endJump(GameObject alien){
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
	public void startMoveLeft(GameObject alien){
		alien.startMove(Orientation.LEFT);
	}

	/**
	 * End the given alien's left move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving left.
	 */
	public void endMoveLeft(GameObject alien){
		alien.endMove(Orientation.LEFT);
	}

	/**
	 * Make the given alien move right.
	 * 
	 * @param alien
	 *            The alien that has to start moving right.
	 */
	public void startMoveRight(GameObject alien){
		alien.startMove(Orientation.RIGHT);
	}

	/**
	 * End the given alien's right move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving right.
	 */
	public void endMoveRight(GameObject alien){
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
	public void advanceTime(GameObject alien, double dt) throws ModelException{
		try{
			alien.advanceTime(dt);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal time amount given!");
		}
		
	}
	
	
	
	
	/*************************************** PART 2 *****************************************/
	
	
	
	@Override
	public int getNbHitPoints(GameObject alien) {
		return alien.getNbHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		return new World(tileSize, nbTilesX, nbTilesY,
			visibleWindowWidth, visibleWindowHeight, targetTileX,
			targetTileY);
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		return new int[] { world.getWorldWidth(), world.getWorldHeight() };
	}

	@Override
	public int getTileLength(World world) {
		return world.getTileLength();
	}

	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGameOver(World world) {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) {
		return world.didPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) {
		world.advanceTime(dt);	
	}

	@Override
	public int[] getVisibleWindow(World world) {
		return new int[] 
			{ 
				world.getDisplayPositionX(), 
				world.getDisplayPositionY(), 
				world.getDisplayPositionX() + world.getDisplayWidth(), 
				world.getDisplayPositionY() + world.getDisplayHeight()
			};
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return new int[] { world.getPositionOfTileX(tileX), world.getPositionOfTileY(tileY) };
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
		//return null;
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		return world.getGeologicalFeature(pixelX, pixelY);
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		world.setGeologicalFeature(tileX, tileY, tileType);		
	}

	@Override
	public void setMazub(World world, Mazub alien) {
		world.setMazub(alien);
	}

	@Override
	public boolean isImmune(Mazub alien) {
		return alien.isImmune();
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlant(World world, Plant plant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Plant> getPlants(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addShark(World world, Shark shark) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Shark> getSharks(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School createSchool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSlime(World world, Slime slime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School getSchool(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	 */
}