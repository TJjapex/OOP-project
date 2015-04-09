package jumpingalien.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;

import jumpingalien.model.helper.Orientation;
import jumpingalien.model.helper.Terrain;
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
		return ( this.getNbTilesX()) * getTileLength(); // waarom die +1?
	}
	
	public int getWorldHeight() {
		return ( this.getNbTilesY()) * getTileLength();
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
	
	/* inspect position (bottom-left corner) of the display window */	
	
	// X
	public int getDisplayPositionX(){
		return this.displayPositionX;
	}
	
	public void setDisplayPositionX(int displayPositionX) {
		this.displayPositionX = displayPositionX;
	}
	
	public void updateDisplayPositionX(){
		// TODO getters en setters implementeren + opschonenen, die ifs kunnen eventueel met min/max
		if(displayPositionX + getDisplayWidth() - 200 < this.mainMazub.getRoundedPositionX()){
			this.displayPositionX = this.mainMazub.getRoundedPositionX() + 200 - getDisplayWidth(); 
		}
		
		if(displayPositionX + 200 > this.mainMazub.getRoundedPositionX()){
			this.displayPositionX = this.mainMazub.getRoundedPositionX() - 200; 
		}
		
		displayPositionX = Math.max(0, displayPositionX);
		displayPositionX = Math.min(getWorldWidth() - getDisplayWidth(), displayPositionX);
	}
	
	private int displayPositionX = 0;

	// Y

	public int getDisplayPositionY(){
		return this.displayPositionY;
	}
	
	public void setDisplayPositionY(int displayPositionY) {
		this.displayPositionY = displayPositionY;
	}
	
	public void updateDisplayPositionY(){
		if(displayPositionY + getDisplayHeight() - 200 < this.mainMazub.getRoundedPositionY()){
			this.displayPositionY = this.mainMazub.getRoundedPositionY() + 200 - getDisplayHeight(); 
		}
		
		if(displayPositionY + 200 > this.mainMazub.getRoundedPositionY()){
			this.displayPositionY = this.mainMazub.getRoundedPositionY() - 200; 
		}
		
		displayPositionY = Math.max(0, displayPositionY);
		displayPositionY = Math.min(getWorldHeight() - getDisplayHeight(), displayPositionY);
		
	}
	
	public void updateDisplayPosition(){
		updateDisplayPositionX();
		updateDisplayPositionY();
	}
	
	private int displayPositionY = 0;
	
	
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
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom, int pixelRight, int pixelTop) {

		ArrayList<int[]> positions = new ArrayList<int[]>(); 
		// In plaats van het aantal elementen op voorhand te bepalen kan dit ook met een ArrayList, ik weet nogn iet wat het beste is dus effe in comments laten staan

		//int nbCols = getTileX(pixelRight) - getTileX(pixelLeft)   + 2 ;
		//int nbRows = getTileY(pixelTop)   - getTileY(pixelBottom) + 2 ;
		//int nbPositions = nbRows * nbCols + 1;
		
		//int[][] positions = new int[nbPositions][2];
		
		/* Loop trough all positions inside the rectangle */
		for(int row = getTileY(pixelBottom); row <= getTileY(pixelTop); row++){
			for(int col = getTileX(pixelLeft); col <= getTileX(pixelRight); col++){ // <= of < ?
				//positions[nbCols * row + col] = new int[] {col, row};
				
				// Voor arrayList
				positions.add(new int[]{ col, row} );
			}
		}
		
		// Om array te printen; Arrays.deepToString(theArray)
		// System.out.println(Arrays.deepToString(positions.toArray(new int[positions.size()][2])));
		
		//return positions;
		
		// Voor arrayList -> omzetten naar gewone array
		return  positions.toArray(new int[positions.size()][2]);
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
		
		for(Mazub alien: this.getAllMazubs()){
			
			// determine minDt			
			minDt = Math.min( dt,  0.01 / (alien.getVelocityMagnitude() + alien.getAccelerationMagnitude()* dt) );
			
			// iteratively advance time;
			for(int i=0; i<(dt/minDt); i++){
				alien.advanceTime(minDt);
	        }
		}
		
		for(GameObject object: this.getAllEnemies()){
			
			// determine minDt			
			minDt = Math.min( dt,  0.01 / ( object.getVelocityMagnitude() + object.getAccelerationMagnitude()* dt) );
			
			// iteratively advance time;
			for(int i=0; i<(dt/minDt); i++){
				object.advanceTime(minDt);
	        }
		}
		
		updateDisplayPosition();
		
	}

// Nog effe houden, misschien moeten we later nog voor iets weten aan welke kant er collision was
//	public Set<Orientation> collidesWith(GameObject object){ // argument can be other than a Mazub (i.e. Shark, Slime, Plant)
//		
//		Set<Orientation> obstacleOrientations = new HashSet<Orientation>();
//		
//		for (Entry<VectorInt, Integer> feature: geologicalFeatures.entrySet()){
//			if (feature.getValue() == 1){
//				VectorInt featureVector = feature.getKey(); 
//				
//				if ( (object.getPositionX() + (object.getWidth()-1) >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() + (object.getWidth()-1) <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1)) &&
//					 // check right-bottom pixel
//				    (((object.getPositionY()+1 >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY()+1 <= getPositionOfTileY(featureVector.getY()) + (this.tileLength-1))) ||
//					 // check right-top pixel
//					((object.getPositionY() + (object.getHeight()-1) >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY() + (object.getHeight()-1) <= getPositionOfTileY(featureVector.getY()) + (this.tileLength-1))))){
//					obstacleOrientations.add(Orientation.RIGHT);
//					}
//				
//				if ( (object.getPositionX() >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1)) &&
//					 // check left-bottom pixel
//				   (((object.getPositionY()+1 >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY()+1 <= getPositionOfTileY(featureVector.getY()) + (this.tileLength-1))) ||
//					 // check left-top pixel
//					((object.getPositionY() + (object.getHeight()-1) >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY() + (object.getHeight()-1) <= getPositionOfTileY(featureVector.getY()) + (this.tileLength-1))))){
//					obstacleOrientations.add(Orientation.LEFT);
//				}
//				
//				if ( (object.getPositionY()+(object.getHeight()-1) >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY()+(object.getHeight()-1) <= getPositionOfTileY(featureVector.getY())+ (this.tileLength-1)) &&
//					 // check left-top pixel
//				   (((object.getPositionX() >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1))) ||
//					 // check right-top pixel
//					((object.getPositionX() +(object.getWidth()-1) >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() +(object.getWidth()-1) <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1))))){
//					obstacleOrientations.add(Orientation.TOP);
//				}
//				
//				if ( (object.getPositionY() >= getPositionOfTileY(featureVector.getY())) &&
//					 (object.getPositionY() <= getPositionOfTileY(featureVector.getY())+ (this.tileLength-1)) &&
//					 // check left-bottom pixel
//				   (((object.getPositionX() >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1))) ||
//					 // check right-bottom pixel
//					((object.getPositionX() +(object.getWidth()-1) >= getPositionOfTileX(featureVector.getX())) &&
//					 (object.getPositionX() +(object.getWidth()-1) <= getPositionOfTileX(featureVector.getX()) + (this.tileLength-1))))){
//					obstacleOrientations.add(Orientation.BOTTOM);
//					}
//							
//			}
//		}
//		//System.out.println(obstacleOrientations);
//		
//		return obstacleOrientations;
//	}
	
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
	// Volgens mij is dit wat er onder geological features wordt bedoeld.

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean didPlayerWin() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/********************************************* GEOLOGICAL FEATURES *****************************************/	
	
	public static Terrain tileTypeIndexToType(int tileTypeIndex){ // Slechte naam	
		switch(tileTypeIndex){
			case 0:
				return Terrain.AIR;
			case 1:
				return Terrain.SOLID;
			case 2:
				return Terrain.WATER;
			case 3:
				return Terrain.MAGMA;
				
			default:
				return Terrain.AIR;
		}
	}
	
	
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
	public void setGeologicalFeature(int tileX, int tileY, Terrain tileType) {
		geologicalFeatures.put(new VectorInt(tileX,  tileY), tileType);
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
	public Terrain getGeologicalFeature(int pixelX, int pixelY) {
		
		// Checken of gegeven pixel in game-world ligt?
		
		if(pixelX % getTileLength() != 0 || pixelY % getTileLength() != 0){
			throw new IllegalArgumentException("Given position does not correspond to the bottom left pixel of a tile");
		}
		
		// Opmerking: map.get() geeft null als de key niet bestaat, dus misschien kan dat ook gebruikt worden ipv containsKey. (efficienter) Maar bij
		// int value = this.gameObjects.get(... ), kan value nooit null worden want das geen geldige integer.
		if(this.geologicalFeatures.containsKey( new VectorInt(getTileX(pixelX), getTileY(pixelY)))){
			return this.geologicalFeatures.get( new VectorInt(getTileX(pixelX), getTileY(pixelY)));
		}else{
			return Terrain.AIR;
		}
		
	}
	
	private Map<VectorInt, Terrain> geologicalFeatures = new HashMap<VectorInt, Terrain>();
	
	/********************************************* RELATIONS *****************************************/
	

	
	public boolean canHaveAsGameObject(GameObject gameObject){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canHaveAsMazub(Mazub mazub){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canHaveAsShark(Shark shark){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canHaveAsSlime(Slime slime){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canHaveAsPlant(Plant plant){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean hasProperGameObjects(){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean hasAsGameObject(GameObject gameObject){
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param alien
	 *            The alien to be set as the player's character.
	 */
	public void addAsMazub(Mazub alien){
		alien.setWorld(this);
		mazubs.add(alien);
		this.mainMazub = alien;
	}
	
	private Mazub mainMazub = null;
	
	public void removeAsMazub(Mazub alien){
		// TODO Auto-generated method stub
	}
	
	public void addAsPlant(Plant plant){
		plant.setWorld(this);
		plants.add(plant);
	}
	
	public void addAsSlime(Slime slime){
		slime.setWorld(this);
		slimes.add(slime);
	}
	
	public void addAsShark(Shark shark){
		shark.setWorld(this);
		sharks.add(shark);
	}
	
	public void addAsGameObject(GameObject gameObject){
		// TODO Auto-generated method stub
	}
	
	public void removeAsGameObject(GameObject gameObject){
		// TODO Auto-generated method stub
	}
	
	
	// Getters
	
	public Set<Mazub> getAllMazubs(){
		HashSet<Mazub> mazubsClone =  new HashSet<Mazub>(this.mazubs);
		return mazubsClone;
	}
	
	public Set<GameObject> getAllEnemies(){
		Set<GameObject> allEnemies = new HashSet<GameObject>(this.getAllPlants());
		allEnemies.addAll(this.getAllSlimes());
		allEnemies.addAll(this.getAllSharks());
		return allEnemies; // + nog sharks toevoegen enzo, behoren plants tot enemies?
	}

	public Set<Plant> getAllPlants(){
		Set<Plant> newPlants =  new HashSet<Plant>(this.plants);
		return newPlants;
	}
	
	public Set<Shark> getAllSharks(){
		return this.sharks;
	}
	
	public Set<Slime> getAllSlimes(){
		return this.slimes;
	}
	
	
	// Count
	
	public int getNbGameObjects(){
		return getNbMazubs() + getNbPlants();
	}
	
	public int getNbMazubs(){
		return mazubs.size();
	}
	
	public int getNbPlants(){
		return plants.size();
	}	
		
	public int getNbSharks(){
		return sharks.size();
	}
	
	public int getNbSlimes(){
		return slimes.size();
	}

	// removers
	
	public void removeGameObject(GameObject gameObject){
		
		// Ugly & tricky maar werkt... of isinstance gebruiken...
		mazubs.remove(gameObject);
		plants.remove(gameObject);
		sharks.remove(gameObject);
		slimes.remove(gameObject);
	}
	
	// Vars
	
	public Set<Mazub> mazubs = new HashSet<Mazub>(); // Geen idee of hashset hier wel het juiste type voor is...
	public Set<Plant> plants = new HashSet<Plant>();
	public Set<Shark> sharks = new HashSet<Shark>();
	public Set<Slime> slimes = new HashSet<Slime>();
	
	
	
	
	// Termination
	
	public void terminate(){
		this.terminated = true;
	}
	
	private boolean terminated = false;
}
