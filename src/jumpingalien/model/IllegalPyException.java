package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

public class IllegalPyException extends RuntimeException {


	public IllegalPyException(double py) {
		this.py = py;
	}

	@Basic @Immutable
	public double getPy() {
		return this.py;
	}

	private final double py;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3910437603953458609L;

}