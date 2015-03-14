package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
public enum Orientation {

	// Implementation as seen in the course:

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
