package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.model.interfaces.IKind;
import jumpingalien.model.terrain.Terrain;

/**
 * A class of Tiles, geological features in the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 */
public class Tile implements IKind {

	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the Class Tile.
	 * 
	 * @param 	tileLength
	 * 				The length of a square side of the Tile.
	 * @param 	positionX
	 * 				The horizontal pixel position of the Tile in a World.
	 * @param 	positionY
	 * 				The vertical pixel position of the Tile in a World.
	 * @param 	type
	 * 				The Terrain type of the Tile.
	 * @post	| new.getTileLength() == tileLength
	 * @post	| new.getRoundedPositionX() == positionX
	 * @post	| new.getRoundedPositionY() == positionY
	 * @post	| new.getTerrainType() == type
	 */
	public Tile(int tileLength, int positionX, int positionY, Terrain type){
		this.tileLength = tileLength;
		this.positionX = positionX;
		this.positionY = positionY;
		this.type = type;
	}
	
	/*************************************************** CHARACTERISTICS ***********************************************/
	
	/* Tile length */
	
	/**
	 * Return the length of a square side of the Tile.
	 * 
	 * @return	| result == ( this.tileLength )
	 */
	@Basic
	public int getTileLength(){
		return this.tileLength;
	}
	
	/**
	 * Variable registering the length of a square side of the Tile.
	 */
	private int tileLength;
	
	/**
	 * Return the width of the Tile.
	 * 
	 * @return	| result == ( this.getTileLength() )
	 */
	@Override
	public int getWidth() {
		return this.getTileLength();
	}

	/**
	 * Return the height of the Tile.
	 * 
	 * @return	| result == ( this.getTileLength() )
	 */
	@Override
	public int getHeight() {
		return this.getTileLength();
	}
	
	/* Horizontal position */
	
	/**
	 * Return the rounded horizontal position of the Tile.
	 * 
	 * @return	| result == ( this.positionX )
	 */
	@Override @Basic
	public int getRoundedPositionX() {
		return this.positionX;
	}
	
	/**
	 * Variable registering the horizontal pixel position of the Tile in a World.
	 */
	private int positionX;

	/* Vertical Position */
	
	/**
	 * Return the rounded vertical position of the Tile.
	 * 
	 * @return	| result == ( this.positionY )
	 */
	@Override @Basic
	public int getRoundedPositionY() {
		return this.positionY;
	}
	
	/**
	 * Variable registering the horizontal pixel position of the Tile in a World.
	 */
	private int positionY;
	
	/* Terrain Type */
	
	/**
	 * Return the Terrain type of the Tile.
	 * 
	 * @return	| result == ( this.type )
	 */
	@Basic
	public Terrain getTerrainType(){
		return this.type;
	}
	
	/**
	 * Variable registering the Terrain type of the Tile.
	 */
	private Terrain type;
	
	/******************************************************** STRING ***************************************************/
	
	/**
	 * Return a String representation of the Tile.
	 * 
	 * @return	A String including the name of the Class and its horizontal and vertical position.
	 */
	@Override
	public String toString(){
		return "Tile x:"+getRoundedPositionX()+" y:"+getRoundedPositionY();
	}
	
}
