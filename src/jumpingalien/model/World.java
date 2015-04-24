package jumpingalien.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jumpingalien.model.helper.Terrain;
import jumpingalien.model.helper.VectorInt;

/**
 * A class of Worlds for Mazubs to move around in. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * 
 * @invar | getNbGameObjects() <= 101
 * 
 * @version 1.0
 */
public class World {

	/******************************************************* GENERAL ***************************************************/

	private boolean hasStarted(){
		return this.hasStarted;
	}
	
	public void start(){
		this.hasStarted = true;
	}
	
	private boolean hasStarted = false;
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		
		assert canHaveAsDisplayWidth(visibleWindowWidth);
		assert canHaveAsDisplayHeight(visibleWindowHeight);
		
		this.tileLength = tileSize;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		
		this.displayWidth = visibleWindowWidth;
		this.displayHeight = visibleWindowHeight;
		
		this.setDisplayPositionX(0);
		this.setDisplayPositionY(0);
		
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
	}

	/******************************************************** SIZE *****************************************************/
	
	public int getWorldWidth() {
		return ( this.getNbTilesX()) * getTileLength();
	}
	
	public int getWorldHeight() {
		return ( this.getNbTilesY()) * getTileLength();
	}
	
	/*************************************************** DISPLAY WINDOW ************************************************/	
	
	/* Size */
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	public boolean canHaveAsDisplayWidth(int displayWidth){
		return displayWidth <= this.getWorldWidth() && displayWidth > 0;
	}
	
	private final int displayWidth;
	
	public int getDisplayHeight() {
		return displayHeight;
	}
	
	public boolean canHaveAsDisplayHeight(int displayHeight){
		return displayHeight <= this.getWorldHeight() && displayHeight > 0;
	}
	
	private final int displayHeight;
	
	/* Position */
	
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
	
	private int displayPositionX;

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
	
	/**************************************************** ADVANCE TIME *************************************************/
	
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
	
	/************************************************* GEOLOGICAL FEATURES *********************************************/	
	
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
	
	/**************************************************** GAME OBJECTS *************************************************/
	
	public boolean canHaveAsGameObject(GameObject gameObject){
		return this.canAddGameObject() && gameObject != null;
	}
	
//	public boolean canHaveAsMazub(Mazub mazub){
//		return this.canHaveAsGameObject(mazub);
//	}
//	
//	public boolean canHaveAsShark(Shark shark){
//		return this.canHaveAsGameObject(shark);
//	}
//	
//	public boolean canHaveAsSlime(Slime slime){
//		return this.canHaveAsGameObject(slime);
//	}
//	
//	public boolean canHaveAsPlant(Plant plant){
//		return this.canHaveAsGameObject(plant);
//	}
//	
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
	
	public void addAsGameObject(GameObject gameObject){
		assert canHaveAsGameObject(gameObject);
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
	
	public void removeAsGameObject(GameObject gameObject){
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
		return this.mazubs.size();
	}
	
	public int getNbPlants(){
		return this.plants.size();
	}	
		
	public int getNbSharks(){
		return this.sharks.size();
	}
	
	public int getNbSlimes(){
		return this.slimes.size();
	}
	
	// Vars
	
	public Set<Mazub> mazubs = new HashSet<Mazub>(); // Geen idee of hashset hier wel het juiste type voor is...
	public Set<Plant> plants = new HashSet<Plant>();
	public Set<Shark> sharks = new HashSet<Shark>();
	public Set<Slime> slimes = new HashSet<Slime>();
	
	/******************************************************* PLAYER ****************************************************/

	public boolean isGameOver() {
		return (this.getMazub().isKilled()) || ( this.getMazub().isOnTargetTile() );
	}

	public boolean didPlayerWin() {	
		return this.getMazub().isOnTargetTile();	
	}
	
	/***************************************************** TERMINATION *************************************************/
	
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
