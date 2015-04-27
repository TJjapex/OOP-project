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
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 * @invar 	| getNbGameObjects() <= 101
 * @invar	| getNbSchools() <= 10
 * @invar	| canHaveAsDisplayPositionX(getDisplayPositionX())
 * @invar	| canHaveAsDisplayPositionY(getDisplayPositionY())	
 * @invar	| canHaveAsTargetTileX(getTargetTileX())
 * @invar	| canHaveAsTargetTileY(getTargetTileY())	
 * @invar	| isValidTileLength(getTileLength())
 */
public class World {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class World.
	 * 
	 * @param 	tileSize
	 * 				The length of a square tile side.
	 * @param 	nbTilesX
	 * 				The total number of tiles in the horizontal direction.
	 * @param 	nbTilesY
	 * 				The total number of tiles in the vertical direction.
	 * @param 	visibleWindowWidth
	 * 				The width of the visible game window in pixels.
	 * @param 	visibleWindowHeight
	 * 				The height of the visible game window in pixels.
	 * @param 	targetTileX
	 * 				The horizontal tile-coordinate of the target tile.
	 * @param 	targetTileY
	 * 				The vertical tile-coordinate of the target tile.
	 * @pre		| isValidTileLength(tileSize)
	 * @pre		| nbTilesX > 0
	 * @pre		| nbTilesY > 0
	 * @pre		| canHaveAsDisplayWidth(visibleWindowWidth)
	 * @pre		| canHaveAsDisplayHeight(visibleWindowHeight)
	 * @pre		| canHaveAsTargetTileX(targetTileX)
	 * @pre		| canHaveAsTargetTileY(targetTileY)
	 * @post	| new.tileLength == tileSize
	 * @post	| new.getNbTilesX() == nbTilesX
	 * @post	| new.getNbTilesY() == nbTilesY
	 * @post	| new.getDisplayWidth() == visibleWindowWidth
	 * @post	| new.getDisplayHeight() == visibleWindowHeight
	 * @effect	| setDisplayPositionX(0)
	 * @effect	| setDisplayPositionY(0)
	 * @post	| new.getTargetTileX() == targetTileX
	 * @post	| new.getTargetTileY() == targetTileY
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
	
	/******************************************************** START ****************************************************/
	
	/**
	 * Return whether this Game world has started.
	 * 
	 * @return	| result == ( this.hasStarted )
	 */
	@Basic @Raw
	public boolean hasStarted(){
		return this.hasStarted;
	}
	
	/**
	 * Start this Game world.
	 * 
	 * @post	Set hasStarted to true.
	 * 			| new.hasStarted() == true
	 */
	@Basic @Raw
	public void start(){
		this.hasStarted = true;
	}
	
	/**
	 * Variable registering whether or not the current World has already started.
	 */
	private boolean hasStarted = false;

	/******************************************************** SIZE *****************************************************/
	
	/**
	 * Return the width of this World.
	 * 
	 * @return	The width of this World.
	 * 			| result ==  ( this.getNbTilesX()) * getTileLength() )
	 */ 
	@Raw
	public int getWorldWidth() {
		return ( this.getNbTilesX()) * getTileLength();
	}
	
	/**
	 * Return the height of this World.
	 * 
	 * @return	The height of this World.
	 * 			| result ==  ( this.getNbTilesY()) * getTileLength() )
	 */
	@Raw
	public int getWorldHeight() {
		return ( this.getNbTilesY()) * getTileLength();
	}
	
	/*************************************************** DISPLAY WINDOW ************************************************/	
	
	/* Size */
	
	/**
	 * Return the width of the visible game window.
	 *  
	 * @return	| result ==  displayWidth
	 */
	@Basic @Immutable @Raw
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	/**
	 * Check whether or not the World can have the given visible game window width.
	 * 
	 * @param 	displayWidth
	 * 				The width of the visible game window.
	 * @return	| result == ( displayWidth <= this.getWorldWidth() && displayWidth > 0 )
	 */
	@Raw
	public boolean canHaveAsDisplayWidth(int displayWidth){
		return displayWidth <= this.getWorldWidth() && displayWidth > 0;
	}
	
	/**
	 * Variable registering the visible game window width.
	 */
	private final int displayWidth;
	
	/**
	 * Return the height of the visible game window.
	 *  
	 * @return	| result ==  displayHeight
	 */
	@Basic @Immutable @Raw
	public int getDisplayHeight() {
		return displayHeight;
	}
	
	/**
	 * Check whether or not the World can have the given visible game window height.
	 * 
	 * @param 	displayHeight
	 * 				The height of the visible game window.
	 * @return	| result == ( displayHeight <= this.getWorldHeight() && displayHeight > 0 )
	 */
	@Raw
	public boolean canHaveAsDisplayHeight(int displayHeight){
		return displayHeight <= this.getWorldHeight() && displayHeight > 0;
	}
	
	/**
	 * Variable registering the visible game window height.
	 */
	private final int displayHeight;
	
	/* Position */
	
	/**
	 * Update the horizontal and vertical position of the visible game window.
	 * 
	 * @effect	| updateDisplayPositionX()
	 * @effect	| updateDisplayPositionY()
	 */
	public void updateDisplayPosition(){
		updateDisplayPositionX();
		updateDisplayPositionY();
	}
	
	// X
	
	/**
	 * Return the horizontal position of the visible game window.
	 * 
	 * @return	| result == this.displayPositionX
	 */
	@Basic @Raw
	public int getDisplayPositionX(){
		return this.displayPositionX;
	}
	
	/**
	 * Set the horizontal position of the visible game window to the given value.
	 * 
	 * @param 	displayPositionX
	 * 				The horizontal position of the visible game window.
	 * @pre		| canHaveAsDisplayPositionX(displayPositionX)
	 * @post	| new.getDisplayPositionX() == displayPositionX
	 */
	@Basic @Raw
	private void setDisplayPositionX(int displayPositionX) {
		assert canHaveAsDisplayPositionX(displayPositionX);
		
		this.displayPositionX = displayPositionX;
	}
	
	/**
	 * Check whether or not the World can have the given horizontal position of the visible game window.
	 * 
	 * @param	displayPositionX
	 * 				The horizontal position that needs to be checked.
	 * @return	| result == ( displayPositionX >= 0 && displayPositionX + this.getDisplayWidth() <= this.getWorldWidth() )
	 */
	@Raw
	public boolean canHaveAsDisplayPositionX(int displayPositionX){
		return displayPositionX >= 0 && displayPositionX + this.getDisplayWidth() <= this.getWorldWidth(); 
	}
	
	/**
	 * Update the horizontal position of the visible game window. 
	 * 
	 * @effect	| if ( this.getDisplayPositionX() + this.getDisplayWidth() - 200 <
	 * 			|	   this.getMazub().getRoundedPositionX() + this.getMazub().getWidth() )
	 * 			|	then this.setDisplayPositionX( Math.min( this.getWorldWidth() - this.getDisplayWidth(),
	 * 			|											 Math.max( 0, this.getMazub().getRoundedPositionX()
	 *  		|														  + this.getMazub().getWidth() + 200
	 *  		|										 				  - this.getDisplayWidth() )	)	)
	 * @effect	| if ( newDisplayPositionX + 200 > this.getMazub().getRoundedPositionX() )
	 * 			|	then this.setDisplayPositionX( Math.min( this.getWorldWidth() - this.getDisplayWidth(),
	 * 			|											 Math.max( 0, this.getMazub().getRoundedPositionX() - 200 ) ) )
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
		
		// Check borders of game world 
		newDisplayPositionX =  Math.max( 0, newDisplayPositionX );
		newDisplayPositionX =  Math.min( this.getWorldWidth() - this.getDisplayWidth(), newDisplayPositionX );
		
		this.setDisplayPositionX(newDisplayPositionX);
	}
	
	/**
	 * Variable registering the horizontal position of the visible game window.
	 */
	private int displayPositionX;

	// Y
	
	/**
	 * Return the vertical position of the visible game window.
	 * 
	 * @return	| result == this.displayPositionY
	 */
	@Basic @Raw
	public int getDisplayPositionY(){
		return this.displayPositionY;
	}
	
	/**
	 * Set the vertical position of the visible game window to the given value.
	 * 
	 * @param 	displayPositionY
	 * 				The vertical position of the visible game window.
	 * @pre		| canHaveAsDisplayPositionY(displayPositionY)
	 * @post	| new.getDisplayPositionY() == displayPositionY
	 */
	@Basic @Raw
	private void setDisplayPositionY(int displayPositionY) {
		assert canHaveAsDisplayPositionY(displayPositionY);
		
		this.displayPositionY = displayPositionY;
	}
	
	/**
	 * Check whether or not the World can have the given vertical position of the visible game window.
	 * 
	 * @param 	displayPositionY
	 * 				The vertical position that needs to be checked.
	 * @return	| result == ( displayPositionY >= 0 && displayPositionY + this.getDisplayHeight() <= this.getWorldHeight() )
	 */
	@Raw
	private boolean canHaveAsDisplayPositionY(int displayPositionY){
		return displayPositionY >= 0 && displayPositionY + this.getDisplayHeight() <= this.getWorldHeight(); 
	}
	
	/**
	 * Updates the vertical position of the visible game window. 
	 *
	 * @effect	| if ( this.getDisplayPositionY() + this.getDisplayHeight() - 200 <
	 * 			|	   this.getMazub().getRoundedPositionY() + this.getMazub().getHeight() )
	 * 			|	then this.setDisplayPositionY( Math.min( this.getWorldHeight() - this.getDisplayHeight(),
	 * 			|											 Math.max( 0, this.getMazub().getRoundedPositionY()
	 *  		|														  + this.getMazub().getHeight() + 200
	 *  		|										 				  - this.getDisplayWHeight() )	)	)
	 * @effect	| if ( newDisplayPositionY + 200 > this.getMazub().getRoundedPositionY() )
	 * 			|	then this.setDisplayPositionY( Math.min( this.getWorldHeight() - this.getDisplayHeight(),
	 * 			|											 Math.max( 0, this.getMazub().getRoundedPositionY() - 200 ) ) )
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
	
	/**
	 * Variable registering the vertical position of the visible game window.
	 */
	private int displayPositionY;
	
	/******************************************************** TILES ****************************************************/
	
	/* Number of tiles */
	
	/**
	 * Return the number of horizontal tiles in the World.
	 * 
	 * @return	| result == ( this.nbTilesX )
	 */
	@Basic @Immutable @Raw
	public int getNbTilesX() {
		return this.nbTilesX;
	}
	
	/**
	 * Variable registering the number of horizontal tiles in the World.
	 */
	private final int nbTilesX;
	
	/**
	 * Return the number of vertical tiles in the World.
	 * 
	 * @return	| result == ( this.nbTilesY )
	 */
	@Basic @Immutable @Raw
	public int getNbTilesY(){
		return this.nbTilesY;
	}
	
	/**
	 * Variable registering the number of vertical tiles in the World.
	 */
	private final int nbTilesY;
		
	/* Target tile */
	
	/**
	 * Return the horizontal target tile of the World.
	 * 
	 * @return	| result == ( this.targetTileX )
	 */
	@Basic @Immutable @Raw
	public int getTargetTileX() {
		return this.targetTileX;
	}
	
	/**
	 * Check whether or not the World can have targetTileX as its horizontal target tile.
	 * 
	 * @param 	targetTileX
	 * 				The horizontal target tile to check.
	 * @return	| result == ( targetTileX > 0 && targetTileX < this.getWorldWidth() )
	 */
	@Raw
	public boolean canHaveAsTargetTileX(int targetTileX){
		return targetTileX > 0 && targetTileX < this.getWorldWidth();
	}	
	
	/**
	 * Variable registering the horizontal target tile of the World.
	 */
	private final int targetTileX;
	
	/**
	 * Return the vertical target tile of the World.
	 * 
	 * @return	| result == ( this.targetTileY )
	 */
	@Basic  @Immutable @Raw
	public int getTargetTileY() {
		return this.targetTileY;
	}
	
	/**
	 * Check whether or not the World can have targetTileY as its vertical target tile.
	 * 
	 * @param 	targetTileY
	 * 				The vertical target tile to check.
	 * @return	| result == ( targetTileY > 0 && targetTileY < this.getWorldHeight() )
	 */
	@Raw
	public boolean canHaveAsTargetTileY(int targetTileY){
		return targetTileY > 0 && targetTileY < this.getWorldHeight();
	}	
	
	/**
	 * Variable registering the vertical target tile of the World.
	 */
	private final int targetTileY;
	
	/* Tile length */
	
	/**
	 * Return the length of the side of a square tile.
	 * 
	 * @return	| result == ( this.tileLength )
	 */
	@Basic
	@Immutable
	public int getTileLength() {
		return this.tileLength;
	}
	
	/**
	 * Check whether or not the given tile length is a valid tile length for a World.
	 * 
	 * @param 	tileLength
	 * 				The tile length to check.
	 * @return	| result == ( tileLength > 0 )
	 */
	public static boolean isValidTileLength(int tileLength){
		return tileLength > 0;
	}
	
	/**
	 * Variable registering the tile length of tiles in the World.
	 */
	private final int tileLength;
	
	/* Position of tiles */
	
	/**
	 * Return the horizontal position of the bottom left pixel of a tile for a given x position of a tile.
	 * 
	 * @param 	tileX
	 * 				The x position of a tile.
	 * @return	| result == ( tileX * getTileLength() )
	 */
	@Raw
	public int getPositionXOfTile(int tileX){
		return tileX * getTileLength();
	}

	/**
	 * Check whether or not the given horizontal position is in between the World boundaries.
	 * 
	 * @param 	positionX
	 * 				The horizontal position that needs to be checked.
	 * @return	| result == ( positionX >= 0 && positionX < this.getWorldWidth() )
	 */
	@Raw
	public boolean canHaveAsPositionX(int positionX){
		return positionX >= 0 && positionX < this.getWorldWidth();
	}
	
	/**
	 * Return the vertical position of the bottom left pixel of a tile for a given y position of a tile.
	 * 
	 * @param 	tileY
	 * 				The y position of a tile.
	 * @return	| result == ( tileY * getTileLength() )
	 */
	@Raw
	public int getPositionYOfTile(int tileY){
		return tileY * getTileLength();
	}	
	
	/**
	 * Check whether or not the given vertical position is in between the World boundaries.
	 * 
	 * @param 	positionY
	 * 				The vertical position that needs to be checked.
	 * @return	| result == ( positionY >= 0 && positionY < this.getWorldHeight() )
	 */
	@Raw
	public boolean canHaveAsPositionY(int positionY){
		return positionY >= 0 && positionY < this.getWorldHeight();
	}
	
	/**
	 * Return the horizontal tile position for a given horizontal pixel position.
	 *
	 * @param 	positionX
	 * 				The pixel's horizontal position.
	 * @return	| result == ( (int) Math.floor( positionX / this.getTileLength()) )
	 */
	@Raw
	public int getTileX(int positionX){
		return (int) Math.floor( positionX / this.getTileLength());
	}
	
	/**
	 * Return the vertical tile position for a given vertical pixel position.
	 *
	 * @param 	positionY
	 * 				The pixel's vertical position.
	 * @return	| result == ( (int) Math.floor( positionY / this.getTileLength()) )
	 */
	@Raw
	public int getTileY(int positionY){
		return (int) Math.floor( positionY / this.getTileLength());
	}
	
	/**
	 * Return the tile positions of all tiles within the given rectangular region.
	 *
	 * @param 	pixelLeft
	 *            	The x-coordinate of the left side of the rectangular region.
	 * @param 	pixelBottom
	 *            	The y-coordinate of the bottom side of the rectangular region.
	 * @param 	pixelRight
	 *            	The x-coordinate of the right side of the rectangular region.
	 * @param 	pixelTop
	 *            	The y-coordinate of the top side of the rectangular region.
	 * @return 	An array of tile positions, where each position (x_T, y_T) is
	 *         	represented as an array of 2 elements, containing the horizontal
	 *         	(x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         	The returned array is ordered from left to right,
	 *         	bottom to top: all positions of the bottom row (ordered from
	 *         	small to large x_T) precede the positions of the row above that.
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
	 * Advance time to iteratively invoke advanceTime of all Game objects in the World, starting with Mazub.
	 * 
	 * @param 	dt
	 * 				A double that represents the elapsed in-game time.
	 * @note	No further documentation was required for this method.
	 * @throws 	IllegalArgumentException
	 * 				| !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2)
	 */
	public void advanceTime( double dt) throws IllegalArgumentException{
		
		if( !Util.fuzzyGreaterThanOrEqualTo(dt, 0) || !Util.fuzzyLessThanOrEqualTo(dt, 0.2))
			throw new IllegalArgumentException("Illegal time step amount given: "+ dt + " s");	
		
		for(Mazub alien: Mazub.getAllInWorld(this) ){
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
				throw new ModelException("Game object is not in a world anymore!"+object);
			}
		}
		
		updateDisplayPosition();
	}
	
	/************************************************* GEOLOGICAL FEATURES *********************************************/	
	
	/**
	 * Modify the geological type of a specific tile in the this world to a given type.
	 * 
	 * @param 	tileX
	 *            	The x-position x_T of the tile for which the type needs to be modified.
	 * @param 	tileY
	 *            	The y-position y_T of the tile for which the type needs to be modified.
	 * @param 	terrainType
	 *            	The new type for the given tile, where:
	 *            		- the value 0 is returned for an air tile.
	 *         			- the value 1 is returned for a solid ground tile.
	 *         			- the value 2 is returned for a water tile.
	 *         			- the value 3 is returned for a magma tile.
	 * @post	| new.getGeologicalFeature( this.getPositionXofTile(tileX), this.getPositionYofTile(tileY) ) == terrainType
	 * @throws 	IllegalStateException
	 * 				| this.hasStarted()
	 * @throws 	IllegalPositionXException
	 * 				| !canHaveAsPositionX(getPositionXOfTile(tileX))
	 * @throws 	IllegalPositionYException
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
		
		this.geologicalFeatures.put(new VectorInt(tileX,  tileY), terrainType);
	}
	
	/**
	 * Return the geological feature of the tile with its bottom left pixel at the given position.
	 *
	 * @param 	pixelX
	 *            	The x-position of the pixel at the bottom left of the tile for
	 *            	which the geological feature should be returned.
	 * @param 	pixelY
	 *           	The y-position of the pixel at the bottom left of the tile for
	 *            	which the geological feature should be returned.
	 * @note 	This method must return its result in constant time.
	 * @return 	The type of the tile with the given bottom left pixel position, where:
	 *         		- the value 0 is returned for an air tile.
	 *         		- the value 1 is returned for a solid ground tile.
	 *         		- the value 2 is returned for a water tile.
	 *         		- the value 3 is returned for a magma tile.
	 * @throw 	IllegalArgumentException
	 * 				| (pixelX % getTileLength() != 0 || pixelY % getTileLength() != 0)
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
	
	/**
	 * Map registering the geological features of this World.
	 */
	private Map<VectorInt, Terrain> geologicalFeatures = new HashMap<VectorInt, Terrain>();
	
	/**************************************************** GAME OBJECTS *************************************************/
	
	/**
	 * Check whether or not this World is in the right state to add a Game object. The world may not be started 
	 * or terminated.
	 * 
	 * @return	| result ==  ( !this.hasStarted() && !this.isTerminated() )
	 */
	@Raw
	public boolean canAddGameObject(){
		return !this.hasStarted() && !this.isTerminated(); 
	}
	
	/**
	 * Check whether or not this World can have the given Game object as its Game object.
	 * 
	 * @param 	gameObject
	 * 				The Game object that needs to be checked.
	 * @return	| result == ( this.canAddGameObject() && gameObject != null )
	 */
	@Raw
	public boolean canHaveAsGameObject(GameObject gameObject){
		return this.canAddGameObject() && gameObject != null;
	}
	
	/**
	 * Check whether or not this World has proper Game objects.
	 * 
	 * @return	| result == ( ( Mazub.getNbInWorld(this) >= 1) && (this.getNbGameObjects() - 1 <= 100 ) &&
	 * 			|			  ( for every object in this.getAllGameObjects(): object.getWorld() == this ) )
	 */
	@Raw
	public boolean hasProperGameObjects(){
		if( (Mazub.getNbInWorld(this) < 1) || (this.getNbGameObjects() - 1 > 100 )){
			return false;
		}
		
		for(GameObject object : this.getAllGameObjects()){
			if(object.getWorld() != this){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check whether or not the World has gameObject as one of its Game objects.
	 * 
	 * @param 	gameObject
	 * 				The Game object to check.
	 * @return	| gameObject.hasAsWorld(this)
	 */
	public boolean hasAsGameObject(GameObject gameObject){
		return gameObject.hasAsWorld(this);
	}
	
	
	/**
	 * Add the given Game object to the World.
	 * 
	 * @param 	gameObject
	 * 				The Game object to add to the World.
	 * @effect	| gameObject.addToWorld()
	 */
	void addAsGameObject(GameObject gameObject){
		gameObject.addToWorld();
	}

	
	/**
	 * Remove the given Game object from the World
	 * 
	 * @param 	gameObject
	 * 				The Game object to remove from the World.
	 * @effect	| gameObject.removeFromWorld(this)
	 */
	void removeAsGameObject(GameObject gameObject){
		gameObject.removeFromWorld(this);
	}
	
	/* Getters */
	
	/**
	 * Return the player's Mazub in the World.
	 * 
	 * @return	If there is only one Mazub in the World, return this Mazub. 
	 * 			If there is no Mazub in this world, return null.
	 * @throws	IllegalStateException
	 * 			| Mazub.getNbInWorld(this) != 1
	 */
	public Mazub getMazub() throws IllegalStateException{
		
		if(Mazub.getNbInWorld(this) == 1){
			return Mazub.getAllInWorld(this).iterator().next();
		}else if(Mazub.getNbInWorld(this) == 0){
			throw new IllegalStateException("No Mazub in this world!");
		}else{
			throw new IllegalStateException("Not defined in assignment how to handle multiple Mazubs!");
		}
	}
	
	/**
	 * Return all the Game objects in the World.
	 * 
	 * @return	A Hashset containing all Mazubs, Plants, Slimes and Sharks in this World.
	 */
	public Set<GameObject> getAllGameObjects(){
		Set<GameObject> allGameObjects= new HashSet<GameObject>( Mazub.getAllInWorld(this) );
		allGameObjects.addAll( Plant.getAllInWorld(this));
		allGameObjects.addAll( Slime.getAllInWorld(this));
		allGameObjects.addAll( Shark.getAllInWorld(this));
		return allGameObjects;
	}
	
	/**
	 * Return all the enemies of the player's Mazub in the World.
	 * 
	 * @return	A Hashset containing all Plants, Slimes and Sharks in this World.
	 */
	public Set<GameObject> getAllEnemies(){
		Set<GameObject> allEnemies = new HashSet<GameObject>( Plant.getAllInWorld(this) );
		allEnemies.addAll( Slime.getAllInWorld(this));
		allEnemies.addAll( Shark.getAllInWorld(this));
		return allEnemies;
	}	
	
	/* Counters */
	
	/**
	 * Return the number of Game objects in the World.
	 * 
	 * @return	The number of Mazubs, Plants, Sharks and Slimes in this World added together.
	 */
	public int getNbGameObjects(){
		return Mazub.getNbInWorld(this) + Plant.getNbInWorld(this) + Shark.getNbInWorld(this) + Slime.getNbInWorld(this);
	}
	
	/**
	 * Return the number of Schools in this World.
	 * 
	 * @return	The number of Schools in this World.
	 */
	public int getNbSchools(){
		Set<School> schools = new HashSet<School>();
		for ( Slime slime: Slime.getAllInWorld(this) ){
			schools.add(slime.getSchool());
		}
		return schools.size();
	}
	
	/* Variables */
	
	/**
	 * Set registering the Mazubs in this World.
	 */
	Set<Mazub> mazubs = new HashSet<Mazub>();
	
	/**
	 * Set registering the Plants in this World.
	 */
	Set<Plant> plants = new HashSet<Plant>();
	
	/**
	 * Set registering the Sharks in this World.
	 */
	Set<Shark> sharks = new HashSet<Shark>();
	
	/**
	 * Set registering the Slimes in this World.
	 */
	Set<Slime> slimes = new HashSet<Slime>();
	
	/******************************************************* PLAYER ****************************************************/
	
	/**
	 * Check if the game is over.
	 * 
	 * @return 	| result == ( (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() )	)
	 */
	@Raw
	public boolean isGameOver() {
		return (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() );
	}
	
	/**
	 * Check if the player has won (i.e. the Mazub related to this World is on the target tile of the World).
	 * 
	 * @return 	| result == ( this.getMazub().isOnTargetTile() )
	 */
	@Raw
	public boolean didPlayerWin() {	
		return this.getMazub().isOnTargetTile();	
	}
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Terminate this world.
	 * 
	 * @effect	| for GameObject gameObject in this.getAllGameObjects():
	 * 			|	gameObject.unsetWorld()
	 * @post	| new.isTerminated() == true
	 */
	@Basic
	private void terminate(){
		for (GameObject gameObject: this.getAllGameObjects()){
			gameObject.unsetWorld();
		}
		
		this.terminated = true;
	}
	
	/**
	 * Check if this world is terminated.
	 * 
	 * @return	| result == ( this.terminated )
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * Variable registering whether or not the World is terminated.
	 */
	private boolean terminated = false;
	
}
