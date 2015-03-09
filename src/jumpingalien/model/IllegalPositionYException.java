package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

public class IllegalPositionYException extends RuntimeException {


	public IllegalPositionYException(double positionY) {
		this.positionY = positionY;
	}

	@Basic @Immutable
	public double getPositionY() {
		return this.positionY;
	}

	private final double positionY;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3910437603953458609L;

}