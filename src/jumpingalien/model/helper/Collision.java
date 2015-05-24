package jumpingalien.model.helper;

public class Collision {
	public Collision(){
		
	}
	
	/**
	 * Check if one given region (region 1) overlaps with another given region (region 2), in the given direction.
	 * 
	 * @param 	x1
	 * 				The horizontal position of the left bottom corner of the first region.
	 * @param 	y1
	 * 				The vertical position of the left bottom corner of the first region.
	 * @param 	width1
	 * 				The width of the first region.
	 * @param 	height1
	 * 				The height of the first region.
	 * @param 	x2
	 * 				The horizontal position of the left bottom corner of the second region.
	 * @param 	y2
	 * 				The vertical position of the left bottom corner of the second region.
	 * @param 	width2
	 * 				The width of the second region.
	 * @param 	height2
	 * 				The height of the second region.
	 * @param 	orientation
	 * 				The orientation to check overlap in. 
	 * @return	In case orientation is Orientation.ALL, this method will check if at least one pixel of region 1 overlaps
	 *  			with one pixel of region 2.
	 * 	 		In case orientation is Orientation.BOTTOM, this method will check if the bottom perimeter overlaps with any
	 *  			pixel of region 2. 
	 * 			In case orientation is Orientation.TOP, this method will check if the top perimeter overlaps with any pixel
	 *  			of region 2.
	 * 			In case orientation is Orientation.RIGHT, this method will check if the right perimeter overlaps with any pixel
	 *  			of region 2.
	 * 			In case orientation is Orientation.LEFT, this method will check if the left perimeter overlaps with any pixel
	 *  			of region 2.
	 * @note	The perimeters in the assignment are defined as follows:
	 * 				bottom: x..x + Xg - 1, y
	 * 				top:	x..x + Xg - 1, y + Yg - 1 
	 * 				left:	x, y+1..y+Yg-2
	 * 				right:	x+Xg-1, y+1..y+Yg-2
	 * @throws 	IllegalArgumentException
	 * 				The given orientation is not implemented.
	 * 				| orientation != Orientation.RIGHT &&
	 * 				| orientation != Orientation.LEFT &&
	 * 				| orientation != Orientation.TOP &&
	 * 				| orientation != Orientation.BOTTOM &&
	 * 				| orientation != Orientation.ALL &&
	 */
	public static boolean doRegionsOverlap(int x1, int y1, int width1, int height1, int x2, int y2,
										   int width2, int height2, Orientation orientation) 
							throws IllegalArgumentException{
		
		switch (orientation) {
		
			case RIGHT: 
				return  x1 + width1 > x2 &&
						x1 + width1 <= x2 + width2 &&
						Collision.doPixelsOverlap(y1, height1, y2, height2);
			case LEFT:
				return  x1 >= x2 &&
						x1 < x2 + width2 &&
						Collision.doPixelsOverlap(y1, height1, y2, height2);
			case TOP:
				return 	y1 + height1 > y2 && 
						y1 + height1 <= y2 + height2 &&
						Collision.doPixelsOverlap(x1, width1, x2, width2);				
			case BOTTOM:
				return 	y1 >= y2 &&
						y1 < y2 + height2 &&
						Collision.doPixelsOverlap(x1, width1, x2, width2);
			case ALL:
				return ! ( // Dus geeft true als elke deelexpressie false geeft
						   (x1 + (width1 - 1) < x2) 
						|| (x2 + (width2 - 1) < x1)
						|| (y1 + (height1 - 1) < y2) // top
						|| (y2 + (height2 - 1) < y1) //bottom
					
				);
			default:
				throw new IllegalArgumentException("Given orientation not implemented!");
		}
	}
	
	/**
	 * Check whether two lines of pixels do overlap. The lines are defined by their most-left pixel (x) and their width. 
	 * This can also be used for vertical lines, since the condition is the same.
	 * 
	 * @param 	x1
	 * 				The first pixel of the first line.
	 * @param 	width1
	 * 				The width of the first line.
	 * @param 	x2
	 * 				The first pixel of the second line
	 * @param 	width2
	 * 				The width of the second line
	 * @return	True if and only if the given lines of pixels overlap, which means they have at least one overlapping pixel.
	 * 			| result == ( x1 < x2 + width2 && x1 + width1 > x2 )
	 */
	public static boolean doPixelsOverlap(int x1, int width1, int x2, int width2){
		return	x1 < x2 + width2 && x1 + width1 > x2;
	}
}
