package jumpingalien.model;

import jumpingalien.model.helper.Orientation;

public interface IMovable {
	
	public void startMove(Orientation orientation);
	
	public void endMove(Orientation orientation);
	
	public boolean isMoving();
	
	public boolean isMoving(Orientation orientations);
	
}
