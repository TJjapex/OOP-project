package jumpingalien.model;

public class Time {
	
	public Time(){
		this.setSinceLastMove(0);
		this.setSinceLastSprite(0);
	}
	
	// Last move
	
	public double getSinceLastMove() {
		return this.sinceLastMove;
	}

	public void setSinceLastMove(double sinceLastMove) {
		this.sinceLastMove = sinceLastMove;
	}
	
	public void sinceLastMoveIncrease(double dt){
		setSinceLastMove( getSinceLastMove() + dt);
	}
	
	private double sinceLastMove;
	
	// Last sprite
	
	public double getSinceLastSprite() {
		return sinceLastSprite;
	}
	
	public void setSinceLastSprite(double sinceLastSprite) {
		this.sinceLastSprite = sinceLastSprite;
	}
	
	public void setSinceLastSpriteIncrease(double dt){
		setSinceLastSprite(getSinceLastSprite() + dt);
	}

	private double sinceLastSprite;
		
}
