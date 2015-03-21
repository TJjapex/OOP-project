package jumpingalien.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import jumpingalien.util.ModelException;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Worlds for Mazubs to move around in. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class World {

	/************************************************** GENERAL ***********************************************/

	
	/************************************************ CONSTRUCTOR *********************************************/
	
	//	Upon world creation:
	//	* at least one player character (Mazub)
	//	* no more than 100 other game objects
	//	* world may at all times contain no other objects than these
	//	* initial positions of objects are passed at time of their creation
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		this.tileLength = tileSize;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		
		// Asserts staan hier maar tijdelijk =)
		assert canHaveAsDisplayWidth(visibleWindowWidth);
		assert canHaveAsDisplayHeight(visibleWindowHeight);
		
		this.displayWidth = visibleWindowWidth;
		this.displayHeight = visibleWindowHeight;
		
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
	}
	
	/**************************************************** WORLD SIZE ************************************************/
	
	/* world dimensions */
	public int getWorldWidth() {
		return this.getNbTilesX() * getTileLength();
	}
	
	public int getWorldHeight() {
		return this.getNbTilesY() * getTileLength();
	}
	
	/*********************************************** DISPLAY WINDOW *******************************************/	
	
	// width
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	public boolean canHaveAsDisplayWidth(int displayWidth){
		return displayWidth <= this.getWorldWidth() && displayWidth > 0;
	}
	
	private final int displayWidth;

	// height
	
	public int getDisplayHeight() {
		return displayHeight;
	}
	
	public boolean canHaveAsDisplayHeight(int displayHeight){
		return displayHeight <= this.getWorldHeight() && displayHeight > 0;
	}
	
	private final int displayHeight;
	
	//  * inspect position (bottom-left corner) of the display window
	public int getDisplayPositionX(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getDisplayPositionY(){
		// TODO Auto-generated method stub
		return 0;
	}

	
	/*************************************************** TILES ************************************************/

	// 	* bottom-left pixel (0,0) to top-right pixel (X-1,Y-1)
	// 	* each pixel has a side length of 1 [cm] = 0.01 [m]
	// 	* pixels are grouped in TILES of size: length [pixel] x length [pixel]
	// 	  such that: (X % length == 0) and (Y % length == 0)
	// 	* bottom-left tile (0,0) to top-right tile (xTmax,yTmax)
	// 	  such that: (xTmax * length == X) and (yTmax * length == Y)
	
	
	
	/* Number of tiles */
	
	/**
	 * Returns the number of horizontal tiles of the game world
	 * 
	 * @return
	 * 		The number of horizontal tiles of the game world
	 * 		| this.getWorldWidth() / this.getTileLength();
	 */
	public int getNbTilesX() {
		return nbTilesX;
	}
	private final int nbTilesX ;
	
	/**
	 * Returns the number of vertical tiles of the game world
	 * 
	 * @return
	 * 		The number of vertical tiles of the game world
	 * 		| this.getWorldHeight() / this.getTileLength();
	 */
	public int getNbTilesY(){
		return nbTilesY;
	}
	private final int nbTilesY;
	
	
	/* Target tile */
	
	public int getTargetTileX() {
		return targetTileX;
	}
	private final int targetTileX;
	
	public int getTargetTileY() {
		return targetTileY;
	}
	private final int targetTileY;
	
	/* Tile length */
	
	/**
	 * Returns the length of the side of a square tile
	 * @return
	 */
	public int getTileLength() {
		return this.tileLength;
	}
	private final int tileLength;
	
	/* Position of tiles */
	
	/**
	 * Returns the horizontal position in pixels for a given x position of a tile.
	 * @param tileX
	 * @return
	 */
	public int getPositionOfTileX(int tileX){
		return tileX * getTileLength();
	}

	/**
	 * Returns the horizontal position in pixels for a given y position of a tile.
	 * @param tileY
	 * @return
	 */
	public int getPositionOfTileY(int tileY){
		return tileY * getTileLength();
	}	
	
	/**
	 * Returns the horizontal tile position for a given horizontal pixel position.
	 *
	 * @param positionX
	 * 			The pixel's horizontal position
	 * @return
	 * 			The horizontal tile position for the given horizontal pixel position
	 */
	public int getTileX(int positionX){
		return (int) Math.floor( positionX / getTileLength());
	}
	
	/**
	 * Returns the vertical tile position for a given vertical pixel position.
	 *
	 * @param positionY
	 * 			The pixel's vertical position
	 * @return
	 * 			The vertical tile position for the given vertical pixel position
	 */
	public int getTileY(int positionY){
		return (int) Math.floor( positionY / getTileLength());
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
	
	// Bij deze methode moet nog eens goed nagedacht worden over de randgevallen (zie opmerking <= of <). Of misschien moet getTileX(pixelTop - 1 ) ipv getTileX(pixelTop)
	// dat ga ik later eens checken
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
	int pixelRight, int pixelTop) {

		//ArrayList<int[]> positions = new ArrayList<int[]>(); 
		// In plaats van het aantal elementen op voorhand te bepalen kan dit ook met een ArrayList, ik weet nogn iet wat het beste is dus effe in comments laten staan

		int nbCols = getTileX(pixelRight) - getTileX(pixelLeft)   + 1 ;
		int nbRows = getTileY(pixelTop)   - getTileY(pixelBottom) + 1 ;
		int nbPositions = nbRows * nbCols;
		
		int[][] positions = new int[nbPositions][2];
		
		/* Loop trough all positions inside the rectangle */
		for(int row = getTileX(pixelBottom); row <= getTileX(pixelTop); row++){
			for(int col = getTileX(pixelLeft); col <= getTileX(pixelRight); col++){ // <= of < ?
				positions[nbCols * row + col] = new int[] {col, row};
				
				// Voor arrayList
				//positions.add(new int[]{ col, row} );
			}
		}
		
		// Om array te printen; Arrays.deepToString(theArray)
		// System.out.println(Arrays.deepToString(positions));
		
		return positions;
		
		// Voor arrayList -> omzetten naar gewone array
		//return  positions.toArray(new int[positions.size()][2]);
	}

	/******************************************** CHARACTERISTICS ********************************************/
	
	// 	* bottom-left pixel (0,0) to top-right pixel (X-1,Y-1)
	// 	* each pixel has a side length of 1 [cm] = 0.01 [m]
	// 	* pixels are grouped in TILES of size: length [pixel] x length [pixel]
	// 	  such that: (X % length == 0) and (Y % length == 0)
	// 	* bottom-left tile (0,0) to top-right tile (xTmax,yTmax)
	// 	  such that: (xTmax * length == X) and (yTmax * length == Y)
	
	// Deels uitgewerkt onder tiles...
	
	
	/************************************************ ADVANCE TIME ********************************************/
	
	//  * advanceTime to iteratively invoke advanceTime of all game objects in the world, starting with Mazub
	// NO DOCUMENTATION MUST BE WORKED OUT FOR THIS METHOD
	public void advanceTime( double dt) {
		// TODO Auto-generated method stub
	}
	
	/************************************************ GAME OBJECTS ********************************************/
	
	//	- player character (Mazub)
	//	- enemy characters
	//	- collectable items
	//
	//	* typically rectangular, occupy multiple pixels
	//	* position is determined by means of the position of the bottom-left pixel (x,y)
	//	* affects pixels, not always whole tiles
	//	* may occupy any pixel of passable terrain
	//	* may only occupy the top-most row of pixels of solid terrain tiles
	//	* numeric aspects of game world and positions shall be worked out using integers
	//	* may not pass through other game objects
	//	* size: 		Xg [pixel] x Yg [pixel]
	//	* left side: 	(x			, y+1 .. y+Yg-2)
	//	* right side: 	(x+Xg-1		, y+1 .. y+Yg-2)
	//	* bottom side:	(x .. x+Xg-1, y)
	//	* top side:		(x .. x+Xg-1, y+Yg-1)
	//	* objects are removed if their position leaves the boundaries of the game world
	//	* death objects will: - not move
	//						  - be removed from the world with a delay of 0.6s
	//						  - passively interact with other objects
	//	* game terminates when Mazub is removed from the world or reaches a target tile
	
	//  * determine whose bottom-left pixel is positioned on a given position (constant time! -> Map(key,value) )
	public int getGameObjectOnPosition(int positionX, int positionY){
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean didPlayerWin() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param mazub
	 *            The alien to be set as the player's character.
	 */
	public void setMazub(Mazub alien){
		
	}
	
	
	/********************************************* GEOLOGICAL FEATURES *****************************************/	
	
	//	- passable terrain (air=default, water, magma)
	//	- impassable terrain (solid ground)
	//
	//	* position is determined by means of the position of a tile (xT,yT)
	
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
	 * @param tileType
	 *            The new type for the given tile, where
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType) {
		// TODO Auto-generated method stub
		
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
	 *         where
	 *         <ul>
	 *         <li>the value 0 is returned for an <b>air</b> tile;</li>
	 *         <li>the value 1 is returned for a <b>solid ground</b> tile;</li>
	 *         <li>the value 2 is returned for a <b>water</b> tile;</li>
	 *         <li>the value 3 is returned for a <b>magma</b> tile.</li>
	 *         </ul>
	 * 
	 * @note This method must return its result in constant time.
	 * 
	 * @throw ...Exception if the given position does not correspond to the
	 *        bottom left pixel of a tile.
	 */
	public int getGeologicalFeature(int pixelX, int pixelY) {
		// TODO Auto-generated method stub
		return 0;
	}

}
