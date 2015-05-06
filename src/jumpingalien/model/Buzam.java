package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.util.Sprite;


public class Buzam extends Mazub{

	public Buzam(int pixelLeftX, int pixelBottomY, double velocityXInit,
			double velocityYInit, double velocityXMaxRunning,
			double accelerationXInit, Sprite[] sprites, int nbHitPoints,
			int maxNbHitPoints) throws IllegalPositionXException,
			IllegalPositionYException, IllegalWidthException,
			IllegalHeightException {
		super(pixelLeftX, pixelBottomY, velocityXInit, velocityYInit,
				velocityXMaxRunning, accelerationXInit, sprites, nbHitPoints,
				maxNbHitPoints);
		
	}
	
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
			throws IllegalPositionXException, IllegalPositionYException, IllegalWidthException, IllegalHeightException{
		this(pixelLeftX, pixelBottomY, 1.0, 8.0, 3.0, 0.9, sprites, 500, 500);
	}
	
	

}
