package jumpingalien.model;

public enum Orientation {
	LEFT {
		public int getDirection(){
			return -1;
		}
	},
	RIGHT{
		public int getDirection(){
			return 1;
		}
	},
	NONE {
		public int getDirection(){
			return 0;
		}
	};
	
	public abstract int getDirection(); // Slechte naam
}
