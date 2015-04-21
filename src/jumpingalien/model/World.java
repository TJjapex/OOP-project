package jumpingalien.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jumpingalien.model.helper.Terrain;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Worlds for Mazubs to move around in. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */

/**
 * 
 *		QUESTIONS:
 *
 *			It isn’t clear to us if game objects other than Mazub should be immune for 0.6s after they’ve taken damage due to contact with another game object.
 *
 *			Should Plants be removed directly from the World or only after a 0.6s delay?
 *
 *			Should Plants be considered as impassable for a Mazub?
 *
 *			Do we already need to consider the creation of multiple Mazubs? If so, how should we derive which Mazub is controllable by the player and which ones are not?
 *
 * 			Are Plants impassable for other Plants?
 * 
 *			In the assignment is stated the following: Contact with other game objects or terrain features has no impact on the duration of random actions performed by a game object. 
 *			This leads to rather odd behavior of the game objects (e.g. Slimes tend to flock together and remain still for a considerable amount of time). Are we free to change this and
 *			have the game objects change direction every time they make contact with other game objects or terrain features?
 *
 *			Are killed game objects impassable or not?
 *
 *			Can an immune Mazub still eat Plants?
 *
 *			Do we have to use the tag @Override for methods of a class that are abstract in their superclass?
 *
 *			( startGame? )
 *			( check if slime is in two schools? )
 *
 */


public class World {

/************************************************** GENERAL ***********************************************/

	private boolean hasStarted(){
		return this.hasStarted;
	}
	
	public void start(){
		this.hasStarted = true;
	}
	
	private boolean hasStarted = false;
	
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
		
		// Asserts staan hier maar tijdelijk =) of toch niet...
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
		return ( this.getNbTilesX()) * getTileLength();
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
		// TODO beter / compacter uitwerken
		
		// ifs kunnen eventueel met min/max
		
		// Check right side of game window
		if ( this.getDisplayPositionX() + this.getDisplayWidth() - 200 < this.getMazub().getRoundedPositionX() + this.getMazub().getWidth() ){
			this.setDisplayPositionX( this.getMazub().getRoundedPositionX() + this.getMazub().getWidth() + 200 - this.getDisplayWidth() ); 
		}
		
		// Check left side of game window
		if ( this.getDisplayPositionX() + 200 > this.getMazub().getRoundedPositionX() ){
			this.setDisplayPositionX( this.getMazub().getRoundedPositionX() - 200 ); 
		}
		
		// Check borders of gameworld 
		this.setDisplayPositionX( Math.max( 0, this.getDisplayPositionX() ) );
		this.setDisplayPositionX( Math.min( this.getWorldWidth() - this.getDisplayWidth(), this.getDisplayPositionX() ) );
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
		// TODO beter uitwerken
		
		if ( this.getDisplayPositionY() + this.getDisplayHeight() - 200 < this.getMazub().getRoundedPositionY() + this.getMazub().getHeight() ){
			this.setDisplayPositionY( this.getMazub().getRoundedPositionY() + this.getMazub().getHeight() + 200 - getDisplayHeight() ); 
		}
		
		if ( this.getDisplayPositionY() + 200 > getMazub().getRoundedPositionY() ){
			this.setDisplayPositionY( this.getMazub().getRoundedPositionY() - 200 ); 
		}
		
		this.setDisplayPositionY( Math.max( 0, this.getDisplayPositionY() ) );
		this.setDisplayPositionY( Math.min( this.getWorldHeight() - this.getDisplayHeight(), this.getDisplayPositionY() ) );
		
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
	public int getPositionXOfTile(int tileX){
		return tileX * getTileLength();
	}

	/**
	 * Returns the horizontal position in pixels for a given y position of a tile.
	 * @param tileY
	 * @return
	 */
	public int getPositionYOfTile(int tileY){
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

		for(Mazub alien: this.getAllMazubs()){
			alien.advanceTime(dt);						
		}

		for(GameObject object: this.getAllEnemies()){	
			object.advanceTime(dt);
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

	public boolean isGameOver() {
		return (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() );
	}

	public boolean didPlayerWin() {	
		return this.getMazub().isOnTargetTile();	
	}
	
	/********************************************* GEOLOGICAL FEATURES *****************************************/	
	
	public static Terrain terrainIndexToType(int terrainTypeIndex){ // Slechte naam	
		switch(terrainTypeIndex){
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
	 * @param terrainType
	 *            The new type for the given tile, where:
	 *            	- the value 0 is returned for an air tile
	 *         		- the value 1 is returned for a solid ground tile
	 *         		- the value 2 is returned for a water tile
	 *         		- the value 3 is returned for a magma tile
	 */
	public void setGeologicalFeature(int tileX, int tileY, Terrain terrainType) throws IllegalStateException{
		
		if (this.hasStarted){
			throw new IllegalStateException("World already started!");
		}
		
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
	public Terrain getGeologicalFeature(int pixelX, int pixelY) {
		// Checken of gegeven pixel in game-world ligt?
		
		if(pixelX % getTileLength() != 0 || pixelY % getTileLength() != 0){
			throw new IllegalArgumentException("Given position does not correspond to the bottom left pixel of a tile");
		}
		
		if(this.geologicalFeatures.containsKey( new VectorInt(getTileX(pixelX), getTileY(pixelY)))){
			return this.geologicalFeatures.get( new VectorInt(getTileX(pixelX), getTileY(pixelY)));
		}else{
			return Terrain.AIR;
		}
		
	}
	
	private Map<VectorInt, Terrain> geologicalFeatures = new HashMap<VectorInt, Terrain>();
	
	/********************************************* RELATIONS *****************************************/
	

	
	public boolean canHaveAsGameObject(GameObject gameObject){
		return this.canAddGameObject() && gameObject != null;
	}
	
	public boolean canHaveAsMazub(Mazub mazub){
		return this.canHaveAsGameObject(mazub);
	}
	
	public boolean canHaveAsShark(Shark shark){
		return this.canHaveAsGameObject(shark);
	}
	
	public boolean canHaveAsSlime(Slime slime){
		return this.canHaveAsGameObject(slime);
	}
	
	public boolean canHaveAsPlant(Plant plant){
		return this.canHaveAsGameObject(plant);
	}
	
	public boolean hasProperGameObjects(){
		if(getNbGameObjects() > 100){
			return false;
		}
		
		for(GameObject object : getAllGameObjects()){
			if(object.getWorld() != this){
				return false;
			}
		}
		return true;
	}

// 	Waar komt deze methode vandaan? Auto generated?
//
//	public boolean hasAsGameObject(GameObject gameObject){
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param alien
	 *            The alien to be set as the player's character.
	 */
	void addAsMazub(Mazub alien) throws IllegalStateException{
//		
//		if (this.hasStarted){
//			throw new IllegalStateException("World already started!");
//		}
		assert canHaveAsMazub(alien);
		// assert slime.canHaveAsWorld(this); met deze assert werkt het niet omdat die checkt of world null is (beetje zoals die TA zei)
		assert alien.getWorld() == this;
		
		mazubs.add(alien);
		
	}
	
	public Mazub getMazub(){
		if(this.getNbMazubs() == 1){
			return this.getAllMazubs().iterator().next();
		}else{
			throw new IllegalStateException("Not defined in assignment how to handle multiple Mazubs!");
		}
	}
	
	void addAsPlant(Plant plant) throws IllegalStateException{
		assert canHaveAsPlant(plant);		
		assert plant.getWorld() == this;
		
		plants.add(plant);	
		
	}
	
	void addAsSlime(Slime slime) throws IllegalStateException, IllegalArgumentException{;
		assert canHaveAsSlime(slime);
		// assert slime.canHaveAsWorld(this); met deze assert werkt het niet omdat die checkt of world null is (beetje zoals die TA zei)
		assert slime.getWorld() == this;
		
		slimes.add(slime);	
		
	}
	
	void addAsShark(Shark shark) throws IllegalStateException{
		assert canHaveAsShark(shark);
		// assert slime.canHaveAsWorld(this); met deze assert werkt het niet omdat die checkt of world null is (beetje zoals die TA zei)
		assert shark.getWorld() == this;
		
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
	
	public Set<GameObject> getAllGameObjects(){
		Set<GameObject> allGameObjects= new HashSet<GameObject>(this.getAllMazubs());
		allGameObjects.addAll(this.getAllPlants());
		allGameObjects.addAll(this.getAllSlimes());
		allGameObjects.addAll(this.getAllSharks());
		return allGameObjects;
	}
	
//	public Set<GameObject> getAllImpassableGameObjects(){
//		Set<GameObject> allNonPassableGameObjects= new HashSet<GameObject>(this.getAllMazubs());
//		allNonPassableGameObjects.addAll(this.getAllSlimes());
//		allNonPassableGameObjects.addAll(this.getAllSharks());
//		//allNonPassableGameObjects.addAll(this.getAllPlants());
//		return allNonPassableGameObjects;
//	}

	
	public Set<GameObject> getAllEnemies(){
		Set<GameObject> allEnemies = new HashSet<GameObject>(this.getAllPlants());
		allEnemies.addAll(this.getAllSlimes());
		allEnemies.addAll(this.getAllSharks());
		return allEnemies;
	}

	public Set<Plant> getAllPlants(){
		Set<Plant> plantsClone =  new HashSet<Plant>(this.plants);
		return plantsClone;
	}
	
	public Set<Shark> getAllSharks(){
		Set<Shark> sharksClone =  new HashSet<Shark>(this.sharks);
		return sharksClone;
	}
	
	public Set<Slime> getAllSlimes(){
		Set<Slime> slimesClone =  new HashSet<Slime>(this.slimes);
		return slimesClone;
	}
	// checkers
	
	public boolean hasAsMazub(Mazub mazub){
		return this.mazubs.contains(mazub);
	}
	
	public boolean hasAsPlant(Plant plant){
		return this.plants.contains(plant);
	}
	
	public boolean hasAsShark(Shark shark){
		return this.sharks.contains(shark);
	}
	
	public boolean hasAsSlime(Slime slime){
		return this.slimes.contains(slime);
	}
	
	// Count
	
	public int getNbGameObjects(){
		return this.getNbMazubs() + this.getNbPlants() + this.getNbSharks() + this.getNbSlimes();
	}
	
	public int getNbMazubs(){
		return this.getAllMazubs().size();
	}
	
	public int getNbPlants(){
		return this.getAllPlants().size();
	}	
		
	public int getNbSharks(){
		return this.getAllSharks().size();
	}
	
	public int getNbSlimes(){
		return this.getAllSlimes().size();
	}

	// removers
	public void removeAsMazub(Mazub mazub) {
		assert mazub != null && !mazub.hasWorld();
		assert hasAsMazub(mazub);
		
		mazubs.remove(mazub);
	}
	
	public void removeAsPlant(Plant plant) {
		assert plant != null && !plant.hasWorld();
		assert hasAsPlant(plant);
		
		plants.remove(plant);
	}
	
	public void removeAsShark(Shark shark) {
		assert shark != null && !shark.hasWorld();
		assert hasAsShark(shark);
		
		sharks.remove(shark);
	}
	
	public void removeAsSlime(Slime slime) {
		assert slime != null && !slime.hasWorld();
		assert hasAsSlime(slime);
		
		slimes.remove(slime);
	}
	
	
	
	// Niet meer nodig dnek ik
	public void removeGameObject(GameObject gameObject){
		
		// Ugly & tricky maar werkt... of isinstance gebruiken... of aparte methods voor elk type instance
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
	
	public boolean isTerminated(){
		return this.terminated;
	}
	
	public boolean canAddGameObject(){
		return !this.hasStarted() && !this.isTerminated(); 
	}
	
	private boolean terminated = false;
}
