package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

public class IllegalPositionXException extends RuntimeException {

	public IllegalPositionXException(double positionX) {
		this.positionX = positionX;
	}

	@Basic @Immutable
	public double getPositionX() {
		return this.positionX;
	}

	private final double positionX;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1859511137434604743L;

}