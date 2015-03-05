package jumpingalien.model;

import jumpingalien.util.Sprite;

public class Animation {
	
	public Animation(Sprite[] sprites){
		this.sprites = sprites;
		this.setAnimationIndex(0);
		this.nbFrames = ( this.sprites.length - 8) / 2; // Eventueel check of preconditie (even)
	}
	
	/**
	 *  Return the correct sprite of the Mazub instance, depending on his current status.
	 * 
	 * @param alien
	 * 				a valid instance of the Mazub class
	 * @return
	 * 				A sprite that fits the current status of the given Mazub instance.
	 */
	public Sprite getCurrentSprite(Mazub alien){
		int index = 0;	
		
		if(!alien.isMoving()){
			
			if(!alien.hasMovedInLastSecond()){
				if(!alien.isDucking()){
					index = 0;
				}else{
					index = 1;
				}
			}else{
				if(!alien.isDucking()){
					if(alien.getOrientation() == Orientation.RIGHT){
						index = 2;
					}else{ // LEFT
						index = 3;
					}
				}else{
					if(alien.getOrientation() == Orientation.RIGHT){
						index = 6;
					}else{ // LEFT
						index = 7;
					}
				}
			}
			
		}else{ // MOVING
			if(alien.isJumping()){
				if(!alien.isDucking()){
					if(alien.getOrientation() == Orientation.RIGHT){
						index = 4;
					}else{ // LEFT
						index = 5;
					}
				}
			}
			
			if(alien.isDucking()){
				if(alien.getOrientation() == Orientation.RIGHT){
					index = 6;
				}else{ // LEFT
					index = 7;
				}
			}
			if(!alien.isDucking() && !alien.isJumping()){
				if(alien.getOrientation() == Orientation.RIGHT){
					index = 8 + this.getAnimationIndex();
				}else{ // LEFT
					index = 8 + this.nbFrames + this.getAnimationIndex();
				}
			}
		}

		return this.sprites[index];
	}	
	
	/**
	 * Return the current iteration number of the animation.
	 * 
	 * @return
	 * 			the current iteration number of the animation
	 */
	public int getAnimationIndex(){
		return this.animationIndex;
	}
	
	/**
	 * Sets the animation index, which is the number of the sprite in an animated sequence.
	 * 
	 * @param animationIndex
	 * 
	 */
	private void setAnimationIndex(int animationIndex){
		// Checken of animationIndex < nbFrames en > 0, nominaal, totaal of defensief?
		this.animationIndex = animationIndex;
	}
	
	/**
	 * Updates the animation index for the next sprite. BESCHRIJVING MOET UITGEBREID WORDEn
	 * 
	 * @post	...
	 * 			| if this.animationIndex + 1 < this.nbFrames
	 * 			|	then new.AnmationIndex = this.animationIndex + 1 
	 * @post	...
	 * 			| if this.animationIndex + 1  == this.nbFrames
	 * 			|	then new.animationIndex = 0
	 * 			
	 */
	void updateAnimationIndex(){
		this.setAnimationIndex( (this.getAnimationIndex() + 1) % nbFrames );
	}
	

	
	
	
	private Sprite[] sprites;
	private final int nbFrames;
	private int animationIndex;
	
}
