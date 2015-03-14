package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumration of directions. The direction can be left or right.
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
		 * @return	Negative direction
		 * 			| -1
		 */
		public int getSign(){
			return -1;
		}
	},
	RIGHT{
		/**
		 * Return the sign of the positive orientation.
		 * 
		 * @return	Positive direction
		 * 			| 1
		 */
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
