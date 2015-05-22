package jumpingalien.model.helper;
import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration of orientations.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 3.0
 */
@Value
public enum Orientation {

	LEFT {
		/**
		 * Return the sign of the left orientation.
		 * 
		 * @return	Negative X direction.
		 * 			| -1
		 */
		@Override @Basic @Immutable
		public int getSign(){
			return -1;
		}
	},
	RIGHT{
		/**
		 * Return the sign of the right orientation.
		 * 
		 * @return	Positive X direction.
		 * 			| 1
		 */
		@Override @Basic @Immutable
		public int getSign(){
			return 1;
		}
	},
	TOP{
		/**
		 * Return the sign of the up orientation.
		 * 
		 * @return	Positive Y direction.
		 * 			| 1
		 */
		@Override @Basic @Immutable
		public int getSign(){
			return 1;
		}
	},
	BOTTOM{
		/**
		 * Return the sign of the down orientation.
		 * 
		 * @return	Negative Y direction.
		 * 			| -1
		 */
		@Override @Basic @Immutable
		public int getSign(){
			return -1;
		}
	},
	ALL{
		/**
		 * Return the sign of all orientations.
		 * 
		 * @return	All directions.
		 * 			| 1
		 */
		@Override @Basic @Immutable
		public int getSign(){
			return 1;
		}
	};
	
	/**
	 * Return the sign of the orientation.
	 * 
	 * @return	The sign of the orientation.
	 */
	@Basic @Immutable
	public abstract int getSign();
	
}
