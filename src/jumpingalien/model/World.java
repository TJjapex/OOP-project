package jumpingalien.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.helper.VectorInt;
import jumpingalien.util.ModelException;
import jumpingalien.util.Util;

/**
 * A class of Worlds for Mazubs to move around in. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * 
 * @invar 	| getNbGameObjects() <= 101
 * @invar	| getNbSchools() <= 10
 * @invar	| canHaveAsDisplayPositionX(getDisplayPositionX())
 * @invar	| canHaveAsDisplayPositionY(getDisplayPositionY())	
 * @invar	| canHaveAsTargetTileX(getTargetTileX())
 * @invar	| canHaveAsTargetTileY(getTargetTileY())	
 * @invar	| isValidTileLength(getTileLength())
 * 			
 * 
 * @version 1.0
 */
public class World {

	/******************************************************* GENERAL ***************************************************/
	/**
	 * Returns whether this Game world is started
	 * @return
	 * 		|	this.hasStarted
	 */
	@Basic @Raw
	private boolean hasStarted(){
		return this.hasStarted;
	}
	
	/**
	 * Starts this Game world.
	 * @post	Sets hasStarted to true.
	 * 			| new.hasStarted() == true
	 */
	@Basic @Raw
	public void start(){
		this.hasStarted = true;
	}
	
	/**
	 * Variable registering whether the current world is already started or not.
	 */
	private boolean hasStarted = false;
	
	/***************************************************** CONSTRUCTOR *************************************************/
	/**
	 * A constructor for a world
	 * 
	 * @param tileSize
	 * 			The length of a square tile side
	 * @param nbTilesX
	 * 			The total number of tiles in the horizontal direction
	 * @param nbTilesY
	 * 			The total number of tiles in the vertical direction
	 * @param visibleWindowWidth
	 * 			The width of the visible Game window in pixels
	 * @param visibleWindowHeight
	 * 			The Height of the visible Game window in pixels
	 * @param targetTileX
	 * 			The horizontal tile-coordinate of the target tile.
	 * @param targetTileY
	 * 			The Vertical tile-coordinate of the target tile.
	 */
	@Raw
	public World(int tileSize, int nbTilesX, int nbTilesY, int visibleWindowWidth, 
			int visibleWindowHeight, int targetTileX, int targetTileY) {
		
		assert isValidTileLength(tileSize);
		this.tileLength = tileSize;
		
		assert nbTilesX > 0;
		assert nbTilesY > 0;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		
		assert canHaveAsDisplayWidth(visibleWindowWidth);
		assert canHaveAsDisplayHeight(visibleWindowHeight);
		this.displayWidth = visibleWindowWidth;
		this.displayHeight = visibleWindowHeight;
		
		this.setDisplayPositionX(0);
		this.setDisplayPositionY(0);
		
		assert canHaveAsTargetTileX(targetTileX);
		assert canHaveAsTargetTileY(targetTileY);
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
	}

	/******************************************************** SIZE *****************************************************/
	
	/**
	 * Returns the width of this world.
	 * 
	 * @return	The width of this world
	 * 			| result ==  ( this.getNbTilesX()) * getTileLength();
	 */ 
	@Raw
	public int getWorldWidth() {
		return ( this.getNbTilesX()) * getTileLength();
	}
	
	/**
	 * Returns the height of this world.
	 * 
	 * @return	The height of this world
	 * 			| result ==  ( this.getNbTilesY()) * getTileLength();
	 */
	@Raw
	public int getWorldHeight() {
		return ( this.getNbTilesY()) * getTileLength();
	}
	
	/*************************************************** DISPLAY WINDOW ************************************************/	
	
	/* Size */
	
	/**
	 * Returns the width of the visible game window.
	 *  
	 * @return	The width of the visible game window.
	 * 			| result ==  displayWidth;
	 */
	@Basic @Immutable @Raw
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	/**
	 * Checks whether the given visible game window width if is valid.
	 * 
	 * @param displayWidth
	 * 			The width of the visible game window
	 * @return
	 *			| displayWidth <= this.getWorldWidth() && displayWidth > 0;
	 */
	@Raw
	public boolean canHaveAsDisplayWidth(int displayWidth){
		return displayWidth <= this.getWorldWidth() && displayWidth > 0;
	}
	
	private final int displayWidth;
	
	/**
	 * Returns the height of the visible game window.
	 *  
	 * @return	The height of the visible game window.
	 * 			| result ==  displayHeight;
	 */
	@Basic @Immutable @Raw
	public int getDisplayHeight() {
		return displayHeight;
	}
	
	/**
	 * Checks whether the given visible game window height if is valid.
	 * 
	 * @param displayHeight
	 * 			The height of the visible game window
	 * @return
	 *			| displayHeight <= this.getWorldHeight() && displayHeight > 0;
	 */
	@Raw
	public boolean canHaveAsDisplayHeight(int displayHeight){
		return displayHeight <= this.getWorldHeight() && displayHeight > 0;
	}
	
	private final int displayHeight;
	
	/* Position */
	
	/**
	 * Updates the horizontal and vertical position of the visible game window.
	 * 
	 * @effect
	 * 			| updateDisplayPositionX()
	 * @effect
	 * 			| updateDisplayPositionY()
	 */
	public void updateDisplayPosition(){
		updateDisplayPositionX();
		updateDisplayPositionY();
	}
	
	// X
	
	/**
	 * Returns the horizontal position of the visible game window.
	 * @return
	 * 			| result == this.displayPositionX
	 */
	@Basic @Raw
	public int getDisplayPositionX(){
		return this.displayPositionX;
	}
	
	/**
	 * Sets the horizontal position of the visible game window to the given value.
	 * @param displayPositionX
	 * 			The horizontal position of the visible game window
	 * @post
	 * 			| new.getDisplayPositionX() == displayPositionX
	 */
	@Basic @Raw
	private void setDisplayPositionX(int displayPositionX) {
		assert canHaveAsDisplayPositionX(displayPositionX);
		
		this.displayPositionX = displayPositionX;
	}
	
	/**
	 * Checks whether the given horizontal position of the visible game window is valid for this game world.
	 * 
	 * @param displayPositionX
	 * 			The horizontal position that needs to be checked
	 * @return
	 * 			| result == (displayPositionX >= 0 && displayPositionX + this.getDisplayWidth() <= this.getWorldWidth())
	 */
	@Raw
	public boolean canHaveAsDisplayPositionX(int displayPositionX){
		return displayPositionX >= 0 && displayPositionX + this.getDisplayWidth() <= this.getWorldWidth(); 
	}
	
	/**
	 * Updates the horizontal position of the visible game window. 
	 * TODO meer docs
	 */
	private void updateDisplayPositionX(){
		int newDisplayPositionX = this.getDisplayPositionX();
		
		// Check right side of game window
		if ( newDisplayPositionX + this.getDisplayWidth() - 200 < this.getMazub().getRoundedPositionX() + this.getMazub().getWidth() ){
			newDisplayPositionX = this.getMazub().getRoundedPositionX() + this.getMazub().getWidth() + 200 - this.getDisplayWidth(); 
		}
		
		// Check left side of game window
		if ( newDisplayPositionX + 200 > this.getMazub().getRoundedPositionX() ){
			newDisplayPositionX = this.getMazub().getRoundedPositionX() - 200; 
		}
		
		// Check borders of gameworld 
		newDisplayPositionX =  Math.max( 0, newDisplayPositionX );
		newDisplayPositionX =  Math.min( this.getWorldWidth() - this.getDisplayWidth(), newDisplayPositionX );
		
		this.setDisplayPositionX(newDisplayPositionX);
	}
	
	private int displayPositionX;

	// Y
	
	/**
	 * Returns the vertical position of the visible game window.
	 * @return
	 * 			| result == this.displayPositionY
	 */
	@Basic @Raw
	public int getDisplayPositionY(){
		return this.displayPositionY;
	}
	
	/**
	 * Sets the vertical position of the visible game window to the given value.
	 * @param displayPositionY
	 * 			The vertical position of the visible game window
	 * @post
	 * 			| new.getDisplayPositionY() == displayPositionY
	 */
	@Basic @Raw
	private void setDisplayPositionY(int displayPositionY) {
		assert canHaveAsDisplayPositionY(displayPositionY);
		
		this.displayPositionY = displayPositionY;
	}
	
	/**
	 * Checks whether the given vertical position of the visible game window is valid for this game world.
	 * 
	 * @param displayPositionY
	 * 			The vertical position that needs to be checked
	 * @return
	 * 			| result == (displayPositionY >= 0 && displayPositionY + this.getDisplayHeight() <= this.getWorldHeight())
	 */
	@Raw
	private boolean canHaveAsDisplayPositionY(int displayPositionY){
		return displayPositionY >= 0 && displayPositionY + this.getDisplayHeight() <= this.getWorldHeight(); 
	}
	
	/**
	 * Updates the vertical position of the visible game window. 
	 * TODO meer docs
	 */
	private void updateDisplayPositionY(){		
		int newDisplayPositionY = this.getDisplayPositionY();
		
		if ( newDisplayPositionY + this.getDisplayHeight() - 200 < this.getMazub().getRoundedPositionY() + this.getMazub().getHeight() ){
			newDisplayPositionY = this.getMazub().getRoundedPositionY() + this.getMazub().getHeight() + 200 - this.getDisplayHeight(); 
		}
		
		if ( newDisplayPositionY + 200 > this.getMazub().getRoundedPositionY() ){
			newDisplayPositionY = this.getMazub().getRoundedPositionY() - 200; 
		}
		
		newDisplayPositionY = Math.max( 0, newDisplayPositionY );
		newDisplayPositionY = Math.min( this.getWorldHeight() - this.getDisplayHeight(), newDisplayPositionY );
		
		this.setDisplayPositionY(newDisplayPositionY);
	}
	
	private int displayPositionY;
	
	/******************************************************** TILES ****************************************************/
	
	/* Number of tiles */
	
	/**
	 * Returns the number of horizontal tiles of the game world
	 * 
	 * @return
	 * 		The number of horizontal tiles of the game world
	 * 		| this.getWorldWidth() / this.getTileLength();
	 */
	@Basic @Immutable @Raw
	public int getNbTilesX() {
		return this.nbTilesX;
	}
	
	private final int nbTilesX ;
	
	/**
	 * Returns the number of vertical tiles of the game world
	 * 
	 * @return
	 * 		The number of vertical tiles of the game world
	 * 		| this.getWorldHeight() / this.getTileLength();
	 */
	@Basic @Immutable @Raw
	public int getNbTilesY(){
		return this.nbTilesY;
	}
	
	private final int nbTilesY;
		
	/* Target tile */
	
	@Basic @Immutable @Raw
	public int getTargetTileX() {
		return this.targetTileX;
	}
	
	@Raw
	public boolean canHaveAsTargetTileX(int targetTileX){
		return targetTileX > 0 && targetTileX < this.getWorldWidth();
	}	
	
	private final int targetTileX;
	
	@Basic  @Immutable @Raw
	public int getTargetTileY() {
		return this.targetTileY;
	}
	
	@Raw
	public boolean canHaveAsTargetTileY(int targetTileY){
		return targetTileY > 0 && targetTileY < this.getWorldHeight();
	}	
	
	private final int targetTileY;
	
	/* Tile length */
	
	/**
	 * Returns the length of the side of a square tile
	 * @return
	 */
	@Basic
	@Immutable
	public int getTileLength() {
		return this.tileLength;
	}
	
	public static boolean isValidTileLength(int tileLength){
		return tileLength > 0;
	}
	
	private final int tileLength;
	
	/* Position of tiles */
	
	/**
	 * Returns the horizontal position in pixels for a given x position of a tile.
	 * @param tileX
	 * @return
	 */
	@Raw
	public int getPositionXOfTile(int tileX){
		return tileX * getTileLength();
	}

	/**
	 * Checks whether the given horizontal position between in the game world boundaries.
	 * 
	 * @param positionX
	 * 			The horizontal position that needs to be checked
	 * @return
	 * 			| positionX >= 0 && positionX < this.getWorldWidth()
	 */
	@Raw
	public boolean canHaveAsPositionX(int positionX){
		return positionX >= 0 && positionX < this.getWorldWidth();
	}
	
	/**
	 * Returns the horizontal position in pixels for a given y position of a tile.
	 * @param tileY
	 * @return
	 */
	@Raw
	public int getPositionYOfTile(int tileY){
		return tileY * getTileLength();
	}	
	
	/**
	 * Checks whether the given vertical position between in the game world boundaries.
	 * 
	 * @param positionY
	 * 			The vertical position that needs to be checked
	 * @return
	 * 			| positionY >= 0 && positionY < this.getWorldHeight()
	 */
	@Raw
	public boolean canHaveAsPositionY(int positionY){
		return positionY >= 0 && positionY < this.getWorldHeight();
	}
	
	/**
	 * Returns the horizontal tile position for a given horizontal pixel position.
	 *
	 * @param positionX
	 * 			The pixel's horizontal position
	 * @return
	 * 			The horizontal tile position for the given horizontal pixel position
	 */
	@Raw
	public int getTileX(int positionX){
		return (int) Math.floor( positionX / this.getTileLength());
	}
	
	/**
	 * Returns the vertical tile position for a given vertical pixel position.
	 *
	 * @param positionY
	 * 			The pixel's vertical position
	 * @return
	 * 			The vertical tile position for the given vertical pixel position
	 */
	@Raw
	public int getTileY(int positionY){
		return (int) Math.floor( positionY / this.getTileLength());
	}
	
	/**
	 * Returns the tile positions of all tiles within the given rectangular
	 * region.
	 *
	 * @param pixelLeft
	 *            The x-coordinate of the left side of the rectangular region.
	 * @param pixelBottom
	 *            The y-coordinate of the bottom side of the rectangular region.
	 * @param pixelRight
	 *            The x-coordinate of the right side of the rectangular region.
	 * @param pixelTop
	 *            The y-coordinate of the top side of the rectangular region.
	 * 
	 * @return An array of tile positions, where each position (x_T, y_T) is
	 *         represented as an array of 2 elements, containing the horizontal
	 *         (x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         The returned array is ordered from left to right,
	 *         bottom to top: all positions of the bottom row (ordered from
	 *         small to large x_T) precede the positions of the row above that.
	 * 
	 */	
	@Raw
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom, int pixelRight, int pixelTop) {

		ArrayList<int[]> positions = new ArrayList<int[]>(); 
		
		/* Loop trough all positions inside the rectangle */
		for(int row = getTileY(pixelBottom); row <= getTileY(pixelTop); row++){
			for(int col = getTileX(pixelLeft); col <= getTileX(pixelRight); col++){ // <= of < ?
				positions.add(new int[]{ col, row} );
			}
		}

		return  positions.toArray(new int[positions.size()][2]);
	}
	
	/**************************************************** ADVANCE TIME *************************************************/
	
	/**
	 * advanceTime to iteratively invoke advanceTime of all game objects in the world, starting with Mazub
	 * 
	 * @note No documentation must be worked out for this method
	 * @param dt
	 * @throws IllegalArgumentException
	 */
	public void advanceTime( double dt) throws IllegalArgumentException{
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");	
		
		for(Mazub alien: this.getAllMazubs()){
			try{
				alien.advanceTime(dt);	
			}catch(IllegalStateException exc){
				throw new ModelException("Mazub is not in a world anymore!");
			}
								
		}

		for(GameObject object: this.getAllEnemies()){
			try{
				object.advanceTime(dt);
			}catch(IllegalStateException exc){
				throw new ModelException("Game object is not in a world anymore!");
			}
		}
		
		updateDisplayPosition();
	}
	
	/************************************************* GEOLOGICAL FEATURES *********************************************/	
	
	/**
	 * Modify the geological type of a specific tile in the this world to a
	 * given type.
	 * 
	 * @param tileX
	 *            The x-position x_T of the tile for which the type needs to be
	 *            modified
	 * @param tileY
	 *            The y-position y_T of the tile for which the type needs to be
	 *            modified
	 * @param terrainType
	 *            The new type for the given tile, where:
	 *            	- the value 0 is returned for an air tile
	 *         		- the value 1 is returned for a solid ground tile
	 *         		- the value 2 is returned for a water tile
	 *         		- the value 3 is returned for a magma tile
	 *         
	 * @throws IllegalStateException
	 * 				If world has already started
	 * 				| this.hasStarted()
	 * @throws IllegalPositionXException
	 * 				If horizontal position is not valid
	 * 				| !canHaveAsPositionX(getPositionXOfTile(tileX))
	 * @throws IllegalPositionYException
	 * 				If vertical position is not valid
	 * 				| !canHaveAsPositionY(getPositionYOfTile(tileY))
	 */
	public void setGeologicalFeature(int tileX, int tileY, Terrain terrainType) 
			throws IllegalStateException, IllegalPositionXException, IllegalPositionYException{
		
		if (this.hasStarted())
			throw new IllegalStateException("World already started!");
		if (!canHaveAsPositionX(getPositionXOfTile(tileX)))
			throw new IllegalPositionXException(getPositionXOfTile(tileX));
		if (!canHaveAsPositionY(getPositionYOfTile(tileY)))
			throw new IllegalPositionYException(getPositionYOfTile(tileY));
		
		
		geologicalFeatures.put(new VectorInt(tileX,  tileY), terrainType);
	}
	
	/**
	 * Returns the geological feature of the tile with its bottom left pixel at
	 * the given position.
	 *
	 * @param pixelX
	 *            The x-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * @param pixelY
	 *            The y-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * 
	 * @return The type of the tile with the given bottom left pixel position,
	 *         where:
	 *         	- the value 0 is returned for an air tile
	 *         	- the value 1 is returned for a solid ground tile
	 *         	- the value 2 is returned for a water tile
	 *         	- the value 3 is returned for a magma tile
	 * 
	 * @note This method must return its result in constant time.
	 * 
	 * @throw IllegalArgumentException if the given position does not correspond to the
	 *        bottom left pixel of a tile.
	 */
	public Terrain getGeologicalFeature(int pixelX, int pixelY) throws IllegalArgumentException{
		if(pixelX % getTileLength() != 0 || pixelY % getTileLength() != 0)
			throw new IllegalArgumentException("Given position does not correspond to the bottom left pixel of a tile");
		
		if(this.geologicalFeatures.containsKey( new VectorInt(getTileX(pixelX), getTileY(pixelY)))){
			return this.geologicalFeatures.get( new VectorInt(getTileX(pixelX), getTileY(pixelY)));
		}else{
			return Terrain.AIR;
		}
		
	}
	
	private Map<VectorInt, Terrain> geologicalFeatures = new HashMap<VectorInt, Terrain>();
	
	/**************************************************** GAME OBJECTS *************************************************/
	
	/**
	 * Checks whether this world is in the right state to add a Game object. 
	 * The world may not be started or termintated.
	 * 
	 * @return
	 * 		result ==  !this.hasStarted() && !this.isTerminated(); 
	 */
	@Raw
	public boolean canAddGameObject(){
		return !this.hasStarted() && !this.isTerminated(); 
	}
	
	/**
	 * Checks whether this world can have the given Game object as Game Object
	 * 
	 * @param gameObject
	 * 			The game object that needs to be checked
	 * @return
	 * 			| result == this.canAddGameObject() && gameObject != null; 
	 */
	@Raw
	public boolean canHaveAsGameObject(GameObject gameObject){
		return this.canAddGameObject() && gameObject != null;
	}
	
	@Raw
	public boolean hasProperGameObjects(){
		if( (this.getNbMazubs() < 1) || (this.getNbGameObjects() - 1 > 100 )){
			return false;
		}
		
		for(GameObject object : getAllGameObjects()){
			if(object.getWorld() != this){
				return false;
			}
		}
		return true;
	}
	
	@Basic
	void addAsGameObject(GameObject gameObject) throws IllegalArgumentException{
		if(!canHaveAsGameObject(gameObject))
			throw new IllegalArgumentException("World can not have this game object!");
		assert gameObject.getWorld() == this;
		
		if (gameObject instanceof Mazub)
			mazubs.add((Mazub) gameObject);
		else if (gameObject instanceof Shark)
			sharks.add((Shark) gameObject);	
		else if (gameObject instanceof Slime)
			slimes.add((Slime) gameObject);
		else if (gameObject instanceof Plant)
			plants.add((Plant) gameObject);
	}
	
	void removeAsGameObject(GameObject gameObject){
		assert gameObject != null && !gameObject.hasWorld();
		
		if (gameObject instanceof Mazub){
			assert hasAsMazub((Mazub) gameObject);
			mazubs.remove((Mazub) gameObject);
		}else if (gameObject instanceof Shark){
			assert hasAsShark((Shark) gameObject);
			sharks.remove((Shark) gameObject);	
		}else if (gameObject instanceof Slime){
			assert hasAsSlime((Slime) gameObject);
			slimes.remove((Slime) gameObject);
		}else if (gameObject instanceof Plant){
			assert hasAsPlant((Plant) gameObject);
			plants.remove((Plant) gameObject);
		}
	}
	
	// Getters
	
	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param alien
	 *            The alien to be set as the player's character.
	 */
	public Mazub getMazub(){
		if(this.getNbMazubs() == 1){
			return this.getAllMazubs().iterator().next();
		}else{
			throw new IllegalStateException("Not defined in assignment how to handle multiple Mazubs!");
		}
	}
	
	@Basic
	public Set<Mazub> getAllMazubs(){
		HashSet<Mazub> mazubsClone =  new HashSet<Mazub>(this.mazubs);
		return mazubsClone;
	}
	
	public Set<GameObject> getAllGameObjects(){
		Set<GameObject> allGameObjects= new HashSet<GameObject>(this.getAllMazubs());
		allGameObjects.addAll(this.getAllPlants());
		allGameObjects.addAll(this.getAllSlimes());
		allGameObjects.addAll(this.getAllSharks());
		return allGameObjects;
	}
	
	public Set<GameObject> getAllEnemies(){
		Set<GameObject> allEnemies = new HashSet<GameObject>(this.getAllPlants());
		allEnemies.addAll(this.getAllSlimes());
		allEnemies.addAll(this.getAllSharks());
		return allEnemies;
	}

	@Basic
	public Set<Plant> getAllPlants(){
		Set<Plant> plantsClone =  new HashSet<Plant>(this.plants);
		return plantsClone;
	}
	
	@Basic
	public Set<Shark> getAllSharks(){
		Set<Shark> sharksClone =  new HashSet<Shark>(this.sharks);
		return sharksClone;
	}
	
	@Basic
	public Set<Slime> getAllSlimes(){
		Set<Slime> slimesClone =  new HashSet<Slime>(this.slimes);
		return slimesClone;
	}
	
	// checkers
	
	@Basic @Raw
	public boolean hasAsMazub(@Raw Mazub mazub){
		return this.mazubs.contains(mazub);
	}
	
	@Basic @Raw
	public boolean hasAsPlant(@Raw Plant plant){
		return this.plants.contains(plant);
	}
	
	@Basic @Raw
	public boolean hasAsShark(@Raw Shark shark){
		return this.sharks.contains(shark);
	}
	
	@Basic @Raw
	public boolean hasAsSlime(@Raw Slime slime){
		return this.slimes.contains(slime);
	}
	
	/**
	 * Checks whether this world has the given game object as object.
	 * 
	 * @pre		The given game object must be an instance of Mazub, Shark, Slime or Plant.
	 * 			| gameObject instanceof Mazub || gameObject instanceof Shark || gameObject instanceof Slime || gameObject instanceof Plant 
	 * @param gameObject 
	 * 			The game object to check
	 * @return
	 * 		if (gameObject instanceof Mazub)
	 *			return hasAsMazub((Mazub) gameObject);
	 *		else if (gameObject instanceof Shark)
	 *			return hasAsShark((Shark) gameObject);
	 *		else if (gameObject instanceof Slime)
	 *			return hasAsSlime((Slime) gameObject);
	 *		else if (gameObject instanceof Plant)
	 *			return hasAsPlant((Plant) gameObject);
	 */
	@Raw
	public boolean hasAsGameObject(@Raw GameObject gameObject){
		if (gameObject instanceof Mazub)
			return hasAsMazub((Mazub) gameObject);
		else if (gameObject instanceof Shark)
			return hasAsShark((Shark) gameObject);
		else if (gameObject instanceof Slime)
			return hasAsSlime((Slime) gameObject);
		else if (gameObject instanceof Plant)
			return hasAsPlant((Plant) gameObject);
		else 
			assert(false);
		return false;
	}
	
	// Count
	
	public int getNbGameObjects(){
		return this.getNbMazubs() + this.getNbPlants() + this.getNbSharks() + this.getNbSlimes();
	}
	
	@Basic
	public int getNbMazubs(){
		return this.mazubs.size();
	}
	@Basic
	public int getNbPlants(){
		return this.plants.size();
	}	
	@Basic	
	public int getNbSharks(){
		return this.sharks.size();
	}
	@Basic
	public int getNbSlimes(){
		return this.slimes.size();
	}
	
	public int getNbSchools(){
		Set<School> schools = new HashSet<School>();
		for ( Slime slime: this.getAllSlimes() ){
			schools.add(slime.getSchool());
		}
		return schools.size();
	}
	
	// Vars
	
	private Set<Mazub> mazubs = new HashSet<Mazub>();
	private Set<Plant> plants = new HashSet<Plant>();
	private Set<Shark> sharks = new HashSet<Shark>();
	private Set<Slime> slimes = new HashSet<Slime>();
	
	/******************************************************* PLAYER ****************************************************/
	
	/**
	 * Checks if the game is over.
	 * 
	 * @return | (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() )
	 * 			
	 */
	@Raw
	public boolean isGameOver() {
		return (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() );
	}
	
	/**
	 * Checks if the Mazub related to this world has won.
	 * 
	 * @return | this.getMazub().isOnTargetTile()
	 * 			
	 */
	@Raw
	public boolean didPlayerWin() {	
		return this.getMazub().isOnTargetTile();	
	}
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Terminated this world.
	 * 
	 * @post
	 * 		| new.isTerminated() == true
	 */
	@Basic
	private void terminate(){
		this.terminated = true;
	}
	
	/**
	 * Checks if this world is terminated.
	 * @return
	 * 		| result == this.terminated
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
}
