package jumpingalien.model;

import jumpingalien.util.Sprite;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Animation {
	
	private Sprite[] sprites;
	private final int nbFrames;
	
	/**
	 * Constructor for the class Animation.
	 * 
	 * @param 	sprites
	 * 				The array of sprite images for the animation.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @post	The initial sprites of the animation are equal to the given array sprites.
	 * 			| this.sprites == sprites
	 * @post	The initial animation index of the animation is equal to 0.
	 * 			| new.getAnimationIndex() == 0
	 * @post	The initial number of frames of the animation is equal to the length of the sprites array
	 * 			decremented with 8 and afterwards divided by 2.
	 * 			| new.nbFrames == (this.sprites.length - 8) / 2
	 */
	public Animation(Sprite[] sprites){
		this.sprites = sprites;
		this.setAnimationIndex(0);
		this.nbFrames = ( this.sprites.length - 8) / 2;
	}
	
	/**
	 *  Return the correct sprite of the Mazub instance, depending on his current status.
	 * 
	 * @param 	alien
	 * 				A valid instance of the class Mazub.
	 * @return	A sprite that fits the current status of the given Mazub instance.
	 * @note	No formal documentation was required for this method.
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
	 * @return	The current iteration number of the animation.
	 */
	public int getAnimationIndex(){
		return this.animationIndex;
	}
	
	/**
	 * Set the animation index, which is the number of the sprite in an animated sequence.
	 * 
	 * @param 	animationIndex
	 * 				The number of the sprite in an animated sequence.
	 * @pre		The given animation index should be greater or equal to 0 and smaller or equal 
	 * 			to the number of frames of the animation.
	 * 			| (animationIndex >= 0) && (animationIndex <= this.nbFrames)
	 * @post	The animation index is equal to the given animationIndex.
	 * 			| new.getAnimationIndex() == animationIndex
	 */
	private void setAnimationIndex(int animationIndex){
		this.animationIndex = animationIndex;
	}
	
	/**
	 * Updates the animation index for the next sprite of the animation.
	 * 
	 * @post	If the animation index was smaller than the number of frames of the animation minus one,
	 * 			the animation index is now increased with one. If the animation index was equal to
	 * 			the animation index plus one, the animation index is now reset to 0.
	 * 			| if (this.getAnimationIndex() + 1 < this.nbFrames)
	 * 			|	then new.getAnimationIndex() == this.getAnimationIndex() + 1 
	 * 			| else if (this.getAnimationIndex() + 1  == this.nbFrames)
	 * 			|	then new.getAnimationIndex() == 0		
	 */
	void updateAnimationIndex(){
		this.setAnimationIndex( (this.getAnimationIndex() + 1) % nbFrames );
	}
	
	private int animationIndex;
	
}
