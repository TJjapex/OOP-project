package jumpingalien.model;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Worlds for Mazubs to move around in. 
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class World {

	/************************************************** GENERAL ***********************************************/
	
	// 	World segmentation:
	
	// 	* bottom-left pixel (0,0) to top-right pixel (X-1,Y-1)
	// 	* each pixel has a side length of 1 [cm] = 0.01 [m]
	// 	* pixels are grouped in TILES of size: length [pixel] x length [pixel]
	// 	  such that: (X % length == 0) and (Y % length == 0)
	// 	* bottom-left tile (0,0) to top-right tile (xTmax,yTmax)
	// 	  such that: (xTmax * length == X) and (yTmax * length == Y)
	
	// 	World contains:	
	
	//	Geological features	:	- passable terrain (air=default, water, magma)
	//							- impassable terrain (solid ground)
	//
	//							* position is determined by means of the position of a tile (xT,yT)
	
	//	Game objects		:	- player character (Mazub)
	//							- enemy characters
	//							- collectable items
	//
	//							* typically rectangular, occupy multiple pixels
	//							* position is determined by means of the position of the bottom-left pixel (x,y)
	//							* affects pixels, not always whole tiles
	//							* may occupy any pixel of passable terrain
	//							* may only occupy the top-most row of pixels of solid terrain tiles
	//							* numeric aspects of game world and positions shall be worked out using integers
	//							* may not pass through other game objects
	//							* size: 		Xg [pixel] x Yg [pixel]
	//							* left side: 	(x			, y+1 .. y+Yg-2)
	//							* right side: 	(x+Xg-1		, y+1 .. y+Yg-2)
	//							* bottom side:	(x .. x+Xg-1, y)
	//							* top side:		(x .. x+Xg-1, y+Yg-1)
	
	//	Upon world creation:
	
	//	* at least one player character (Mazub)
	//	* no more than 100 other game objects
	//	* world may at all times contain no other objects than these
	//	* initial positions of objects are passed at time of their creation
	
	//	World behaviour:
	
	//	* objects are removed if their position leaves the boundaries of the game world
	//	* death objects will: - not move
	//						  - be removed from the world with a delay of 0.6s
	//						  - passively interact with other objects
	//	* game terminates when Mazub is removed from the world or reaches a target tile
	//	* 
	
	// 	World methods:
	
	//	* inspect game world's dimensions (X,Y)
	//	* inspect length of world's tiles
	//  * determine whose bottom-left pixel is positioned on a given position (constant time! -> Map(key,value) )
	//  * advanceTime to iteratively invoke advanceTime of all game objects in the world, starting with Mazub
	//  * inspect position (bottom-left corner) of the display window
	//	* inspect height and width of the display window
}
