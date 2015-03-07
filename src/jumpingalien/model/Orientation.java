package jumpingalien.model;

public enum Orientation {
	LEFT {
		public int getSign(){
			return -1;
		}
	},
	RIGHT{
		public int getSign(){
			return 1;
		}
	};
	
	public abstract int getSign();
}
