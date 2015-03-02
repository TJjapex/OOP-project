package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

public class IllegalPxException extends RuntimeException {

	
	public IllegalPxException(double px) {
		this.px = px;
	}

	@Basic @Immutable
	public double getPx() {
		return this.px;
	}

	private final double px;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1859511137434604743L;

}