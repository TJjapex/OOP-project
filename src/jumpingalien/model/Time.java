package jumpingalien.model;


// Wordt nog niet gebruikt in Mazub
public class Time {
	public Time(){
		
	}
	
	// Last move
	public double getLastMove() {
		return lastMove;
	}

	public void setLastMove(double lastMove) {
		this.lastMove = lastMove;
	}
	
	public void lastMoveIncrease(double dt){
		setLastMove( getLastMove() + dt);
	}
	
	// Last sprite
	public double getLastSprite() {
		return lastSprite;
	}
	
	public void setLastSprite(double lastSprite) {
		this.lastSprite = lastSprite;
	}
	
	public void lastSpriteIncrease(double dt){
		setLastSprite(getLastSprite() + dt);
	}

	private double lastSprite = 0;
	private double lastMove = 0;
	
}
