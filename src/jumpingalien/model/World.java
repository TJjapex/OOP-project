package jumpingalien.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jumpingalien.model.helper.Orientation;
import jumpingalien.util.ModelException;
import jumpingalien.util.Util;

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
	
	public World(int tileSize, VectorInt nbTiles, int visibleWindowWidth, int visibleWindowHeight, VectorInt targetTile) {
		this.tileLength = tileSize;
		this.nbTiles = nbTiles;
		
		// Asserts staan hier maar tijdelijk =)
		assert canHaveAsDisplayWidth(visibleWindowWidth);
		assert canHaveAsDisplayHeight(visibleWindowHeight);
		
		this.displayWidth = visibleWindowWidth;
		this.displayHeight = visibleWindowHeight;
		
		this.targetTile = targetTile;
	}
	
	/**************************************************** WORLD SIZE ************************************************/
	
	/* world dimensions */
	public int getWorldWidth() {
		return this.getNbTiles().getX() * getTileLength();
	}
	
	public int getWorldHeight() {
		return this.getNbTiles().getY() * getTileLength();
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
	 * Returns a vector with the number of tiles of the game world
	 * 
	 * @return
	 * 		
	 */
	public VectorInt getNbTiles() {
		return nbTiles;
	}
	private final VectorInt nbTiles;
	
	
	/* Target tile */
	
	public VectorInt getTargetTile() {
		return targetTile;
	}
	private final VectorInt targetTile;
	
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
	 * Returns the position in pixels for a given vector position of a tile.
	 * @param tileX
	 * @return
	 */
	public VectorInt getPositionOfTile(VectorInt vectorInt){
		return new VectorInt(vectorInt.getX() * getTileLength(),  vectorInt.getY() * getTileLength());
	}

	
	/**
	 * Returns the horizontal tile position for a given horizontal pixel position.
	 *
	 * @param positionX
	 * 			The pixel's horizontal position
	 * @return
	 * 			The horizontal tile position for the given horizontal pixel position
	 */
	public VectorInt getTile(VectorInt rightTopPixel){
		return new VectorInt((int) Math.floor( rightTopPixel.getX() / getTileLength()), (int) Math.floor( rightTopPixel.getY() / getTileLength()));
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
	
	public int[][] getTilePositionsIn(VectorInt leftBottomPixel, VectorInt rightTopPixel) {

		//ArrayList<int[]> positions = new ArrayList<int[]>(); 
		// In plaats van het aantal elementen op voorhand te bepalen kan dit ook met een ArrayList, ik weet nogn iet wat het beste is dus effe in comments laten staan

		int nbCols = getTile(rightTopPixel).getX() - getTile(leftBottomPixel).getX()   + 1 ;
		int nbRows = getTile(rightTopPixel).getY()   - getTile(leftBottomPixel).getY() + 1 ;
		int nbPositions = nbRows * nbCols;
		
		int[][] positions = new int[nbPositions][2];
		
		/* Loop trough all positions inside the rectangle */
		for(int row = getTile(leftBottomPixel).getX(); row <= getTile(rightTopPixel).getX(); row++){
			for(int col = getTile(leftBottomPixel).getX(); col <= getTile(rightTopPixel).getX(); col++){ // <= of < ?
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
		
		double minDt;
		
		for(GameObject alien: this.getAliens()){
				
			// determine minDt
			
			minDt = Math.min(dt,Math.min(
					 1/(Math.abs(alien.getVelocityX()/100)),
					 1/(Math.abs(alien.getVelocityY()/100))));
			if (!Util.fuzzyEquals(alien.getAccelerationX(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(alien.getAccelerationX())/100 +
				Math.pow(alien.getVelocityX()/100, 2)) - Math.abs(alien.getVelocityX()/100))/
				Math.abs(alien.getAccelerationX()/100) );
			if (!Util.fuzzyEquals(alien.getAccelerationY(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(alien.getAccelerationY())/100 +
				Math.pow(alien.getVelocityY()/100, 2)) - Math.abs(alien.getVelocityY()/100))/
				Math.abs(alien.getAccelerationY()/100) );
			
			// iteratively advance time
			System.out.println(minDt);
			for(int i=0; i<(dt/minDt); i++){
				
				alien.advanceTime(minDt);
				
	        }
		}
					
		for(Shark shark: this.getSharks()){
			
			// determine minDt
			
			minDt = Math.min(dt,Math.min(
					 1/(Math.abs(shark.getVelocityX()/100)),
					 1/(Math.abs(shark.getVelocityY()/100))));
			if (!Util.fuzzyEquals(shark.getAccelerationX(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(shark.getAccelerationX())/100 +
				Math.pow(shark.getVelocityX()/100, 2)) - Math.abs(shark.getVelocityX()/100))/
				Math.abs(shark.getAccelerationX()/100) );
			if (!Util.fuzzyEquals(shark.getAccelerationY(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(shark.getAccelerationY())/100 +
				Math.pow(shark.getVelocityY()/100, 2)) - Math.abs(shark.getVelocityY()/100))/
				Math.abs(shark.getAccelerationY()/100) );
			
			// iteratively advance time
			
			for(int i=0; i<(dt/minDt); i++){
				
				shark.advanceTime(minDt);
				
	        }
		}
		
		for(Slime slime: this.getSlimes()){
			
			// determine minDt
			
			minDt = Math.min(dt,Math.min(
					 1/(Math.abs(slime.getVelocityX()/100)),
					 1/(Math.abs(slime.getVelocityY()/100))));
			if (!Util.fuzzyEquals(slime.getAccelerationX(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(slime.getAccelerationX())/100 +
				Math.pow(slime.getVelocityX()/100, 2)) - Math.abs(slime.getVelocityX()/100))/
				Math.abs(slime.getAccelerationX()/100) );
			if (!Util.fuzzyEquals(slime.getAccelerationY(), 0))
				minDt = Math.min(minDt, (Math.sqrt(2*Math.abs(slime.getAccelerationY())/100 +
				Math.pow(slime.getVelocityY()/100, 2)) - Math.abs(slime.getVelocityY()/100))/
				Math.abs(slime.getAccelerationY()/100) );
			
			// iteratively advance time
			
			for(int i=0; i<(dt/minDt); i++){
				
				slime.advanceTime(minDt);
					
	        }
		}
		
		for(Plant plant: this.getPlants()){
			
			// determine minDt
			
			minDt = Math.min(dt,1/(Math.abs(plant.getVelocityX()/100)));
			
			// iteratively advance time
			
			for(int i=0; i<(dt/minDt); i++){
				
				plant.advanceTime(minDt);
				
	        }
		}
		
		// the separate for statements for each type of game object results in redundant code...
		
	}
	
//	public static List<Orientation> collidesWith(Mazub object){ // argument can be other than a Mazub (i.e. Shark, Slime, Plant)
//		
//		// needs to iterate over all possible obstacles ( near game objects and geological features)
//		
//		List<Orientation> obstacleOrientations = new ArrayList<Orientation>();
//		
//		// check if object overlaps with obstacles to the right
//		
//		if (!(object.getPositionX() + (object.getWidth()-1) < obstacle.getPositionX()))
//			obstacleOrientations.add(Orientation.RIGHT);
//		
//		// check if object overlaps with obstacles to the left
//		
//		if (!(obstacle.getPositionX() + (obstacle.getWidth()-1) < object.getPositionX()))
//			obstacleOrientations.add(Orientation.LEFT);
//				
//		// check if object overlaps with obstacles above
//		
//		if (!(object.getPositionY() + (object.getHeight()-1) < obstacle.getPositionY()))
//			obstacleOrientations.add(Orientation.UP);
//		
//		// check if object overlaps with objects underneath
//		
//		if (!(obstacle.getPositionY() + (obstacle.getHeight()-1) < object.getPositionY()))
//			obstacleOrientations.add(Orientation.DOWN);
//		
//		return obstacleOrientations;
//	}
	
	public Set<Orientation> collidesWith(GameObject object){ // argument can be other than a Mazub (i.e. Shark, Slime, Plant)
		
		Set<Orientation> obstacleOrientations = new HashSet<Orientation>();
		
		for (Entry<VectorInt, Integer> feature: geologicalFeatures.entrySet()){
			if (feature.getValue() == 1){
				VectorInt featurePosition = feature.getKey();
				
				// Minkowski sum
//				double featureX = feature.getKey()[0];
//				double featureY = feature.getKey()[1];
//				
//				double wy = (object.getWidth() + getTileLength()) * ( ( object.getPositionY() + 0.5*object.getHeight() ) - (featureY + 0.5*getTileLength()) );
//				double hx = (object.getHeight() + getTileLength()) * ( ( object.getPositionX() + 0.5*object.getWidth() ) - (featureX + 0.5*getTileLength()) );
//				
//				if(wy > hx){
//					if(wy > -hx){
//						obstacleOrientations.add(Orientation.LEFT);
//					}else{
//						obstacleOrientations.add(Orientation.RIGHT);
//					}
//				}else{
//					if( wy > - hx){
//						obstacleOrientations.add(Orientation.BOTTOM);
//					}else{
//						obstacleOrientations.add(Orientation.TOP);
//					}
//				}
				
				
				
//				if ( (getPositionOfTileX(feature.getKey()[0]) > object.getPositionX()) &&
//					!(object.getPositionX() + (object.getWidth()-1) < getPositionOfTileX(feature.getKey()[0])))
//					obstacleOrientations.add(Orientation.RIGHT);
//				if ( (getPositionOfTileX(feature.getKey()[0]) < object.getPositionX()) &&
//					!(getPositionOfTileX(feature.getKey()[0]) + (this.tileLength-1) < object.getPositionX()))
//					obstacleOrientations.add(Orientation.LEFT);
//				if ( (getPositionOfTileY(feature.getKey()[1]) > object.getPositionY()) &&
//					!(object.getPositionY() + (object.getHeight()-1) < getPositionOfTileY(feature.getKey()[1])))
//					obstacleOrientations.add(Orientation.UP);
//				if ( (getPositionOfTileY(feature.getKey()[1]) < object.getPositionY()) &&
//					!(getPositionOfTileY(feature.getKey()[1]) + (this.tileLength-1) < object.getPositionY()))
//					obstacleOrientations.add(Orientation.DOWN);
				
//				if ( (object.getPositionX()+(object.getWidth()-1) >= getPositionOfTileX(feature.getKey()[0])) &&
//					 (object.getPositionX()+(object.getWidth()-1) <= getPositionOfTileX(feature.getKey()[0]) + (this.tileLength-1)) &&
//					  (object.getPositionY() > getPositionOfTileY(feature.getKey()[1])) &&
//					  (object.getPositionY() < getPositionOfTileY(feature.getKey()[1]) + (this.tileLength-1))){
//					obstacleOrientations.add(Orientation.RIGHT);pr
//				}
//				
//				if ( (object.getPositionX() >= getPositionOfTileX(feature.getKey()[0])) &&
//					 (object.getPositionX() <= getPositionOfTileX(feature.getKey()[0]) + (this.tileLength-1)) &&
//					 (object.getPositionY() > getPositionOfTileY(feature.getKey()[1])) &&
//					 (object.getPositionY() < getPositionOfTileY(feature.getKey()[1]) + (this.tileLength-1))){
//					obstacleOrientations.add(Orientation.LEFT);
//				}
				
				if ( (object.getPositionY()+(object.getHeight()-1) >= getPositionOfTile(featurePosition).getY()) &&
					 (object.getPositionY()+(object.getHeight()-1) <= getPositionOfTile(featurePosition).getY() + (this.tileLength-1)) &&
					 (object.getPositionX() > getPositionOfTile(featurePosition).getX()) &&
					 (object.getPositionX() < getPositionOfTile(featurePosition).getX() + (this.tileLength-1))){
					obstacleOrientations.add(Orientation.TOP);
				}
				
				if ( (object.getPositionY() >= getPositionOfTile(featurePosition).getY()) &&
					 (object.getPositionY() <= getPositionOfTile(featurePosition).getY() + (this.tileLength-1)) &&
					 (object.getPositionX() > getPositionOfTile(featurePosition).getX()) &&
					 (object.getPositionX() < getPositionOfTile(featurePosition).getX() + (this.tileLength-1))){
					obstacleOrientations.add(Orientation.BOTTOM);
				}
				
			}
		}
		System.out.println(obstacleOrientations);
		
		return obstacleOrientations;
	}
	
	
	// Help functions
	
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
	
	//  * determine whose bottom-left pixel is positioned on a given position (constant time! -> Map(key,value) 
//	public int getGameObjectOnPosition(int positionX, int positionY){
//		// TODO Auto-generated method stub
//		return 0;
//	}
	// Volgens mij istttttttt dit wat er onder geological features wordt bedoeld.

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
		alien.setWorld(this);
		aliens.add(alien);
	}
	
	public HashSet<Mazub> getAliens(){
		return this.aliens;
	}
	
	public HashSet<Shark> getSharks(){
		return this.sharks;
	}
	
	public HashSet<Slime> getSlimes(){
		return this.slimes;
	}
	
	public HashSet<Plant> getPlants(){
		return this.plants;
	}
	
	public HashSet<Mazub> aliens = new HashSet<Mazub>(); // Geen idee of hashset hier wel het juiste type voor is...
	public HashSet<Shark> sharks = new HashSet<Shark>();
	public HashSet<Slime> slimes = new HashSet<Slime>();
	public HashSet<Plant> plants = new HashSet<Plant>();
	
	
	
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
	 *            The new type for the given tile, where:
	 *            	- the value 0 is returned for an air tile
	 *         		- the value 1 is returned for a solid ground tile
	 *         		- the value 2 is returned for a water tile
	 *         		- the value 3 is returned for a magma tile
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType) {
		if( tileType == 0 ){
			return;
		}
		geologicalFeatures.put(new VectorInt(tileX,  tileY), tileType);
		System.out.println(this.geologicalFeatures.containsKey(new Vector(tileX, tileY)));
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
	public int getGeologicalFeature(VectorInt pixel) {
		
		// Checken of gegeven pixel in game-world ligt?
		
		if(pixel.getX() % getTileLength() != 0 || pixel.getY() % getTileLength() != 0){
			throw new IllegalArgumentException("Given position does not correspond to the bottom left pixel of a tile");
		}
		
		// Opmerking: map.get() geeft null als de key niet bestaat, dus misschien kan dat ook gebruikt worden ipv containsKey. (efficienter) Maar bij
		// int value = this.gameObjects.get(... ), kan value nooit null worden want das geen geldige integer.
		if(this.geologicalFeatures.containsKey( getTile(pixel))){
			return this.geologicalFeatures.get( getTile(pixel));
		}else{
			return 0;
		}
		
	}
	
	private HashMap<VectorInt, Integer> geologicalFeatures = new HashMap<VectorInt, Integer>();
	
}
