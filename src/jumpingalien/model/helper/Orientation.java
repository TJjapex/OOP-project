package jumpingalien.model.helper;
import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration of directions. The direction can be left or right.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
@Value
public enum Orientation {

	// Implementation as seen in the course:

	LEFT {
		/**
		 * Return the sign of the left orientation.
		 * 
		 * @return	Negative X direction
		 * 			| -1
		 */
		public int getSign(){
			return -1;
		}
	},
	RIGHT{
		/**
		 * Return the sign of the right orientation.
		 * 
		 * @return	Positive X direction
		 * 			| 1
		 */
		public int getSign(){
			return 1;
		}
	},
	TOP{
		/**
		 * Return the sign of the up orientation.
		 * 
		 * @return	Positive Y direction
		 * 			| 1
		 */
		public int getSign(){
			return 1;
		}
	},
	BOTTOM{
		/**
		 * Return the sign of the down orientation.
		 * 
		 * @return	Negative Y direction
		 * 			| -1
		 */
		public int getSign(){
			return -1;
		}
	},
	ALL{
		public int getSign(){
			return 1;
		}
	};
	
	/**
	 * Return the sign of the orientation, left is negative and right is positive.
	 * 
	 * @return
	 * 		The sign of the orientation.
	 */
	public abstract int getSign();
	
	
	// Another possible implementation:

	//	LEFT(-1), RIGHT(1);
	//	private int sign;
	//
	//	private Orientation(int sign) {
	//		this.sign = sign;
	//	}
	//	
	//	public int getSign(){
	//		return this.sign;
	//	}
	//	
}
