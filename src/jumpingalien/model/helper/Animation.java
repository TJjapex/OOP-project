package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Mazub.
 * 
 * @note	For an Animation instance, the sprites can only be set once in the constructor. 
 * 			If new sprites are required, the instance should be destroyed and a new instance should be created.
 *
 * @invar	The sprites array of this animation has a length greater than or equal to 10 and is the length an even number
 * 			|	this.getNbSprites() >= 10 && (this.getNbSprites() % 2) == 0;
 * @invar	The number of frames for the walking animation should be greater than or equal to 1.
 * 			| 	this.getNbFrames() > 0
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Animation {
	
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
		assert sprites.length >= 10 && sprites.length % 2 == 0;
		
		this.sprites = sprites;
		this.setAnimationIndex(0);
	}
	
	/**
	 *  Return the correct sprite of the Mazub instance, depending on his current status.
	 * 
	 * @param 	alien
	 * 				A valid instance of the class Mazub.
	 * @return	A sprite located at index in sprites that fits the current status of the given Mazub instance.
	 * 			The index represents a certain status of Mazub:
	 * 			index = 0: 	Mazub is not moving horizontally, has not moved horizontally within the last 
	 * 						second of in-game-time and is not ducking.
	 * 					1:	Mazub is not moving horizontally, has not moved horizontally within the last second
	 * 						of in-game-time and is ducking.
	 * 					2:	Mazub is not moving horizontally but its last horizontal movement was to the right
	 * 						(within 1s), and the character is not ducking.
	 * 					3:	Mazub is not moving horizontally but its last horizontal movement was to the left
	 * 						(within 1s), and the character is not ducking.
	 * 					4:	Mazub is moving to the right and jumping and not ducking.
	 * 					5:	Mazub is moving to the left and jumping and not ducking.
	 * 					6:	Mazub is ducking and moving to the right or was moving to the right (within 1s).
	 * 					7:	Mazub is ducking and moving to the left or was moving to the left (within 1s).
	 * 					8 .. this.getNbFrames():	
	 * 						Mazub is neither ducking nor jumping and moving to the right.
	 * 					9 + this.getNbFrames() .. 9 + 2*this.getNbFrames():	
	 * 						Mazub is neither ducking nor jumping and moving to the left.
	 * @note	No formal documentation was required for this method.
	 */
	public Sprite getCurrentSprite(Mazub alien){
		int index = 0;	// Default
		
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
					index = 8 + this.getNbFrames() + this.getAnimationIndex();
				}
			}
		}

		return this.getSpriteAt(index);
	}	
	
	/**
	 * Return the sprites of this animation class.
	 * 
	 * @return	The sprites of this animation class.
	 */
	@Basic @Immutable
	public Sprite[] getSprites(){
		return this.sprites;
	}
	
	/**
	 * Returns the sprite of this animation class, with the given index.
	 * 
	 * @param 	index
	 * 				The index of the sprite.
	 * @return	The sprite of this animation class, with the given index.
	 */
	@Immutable
	public Sprite getSpriteAt(int index){
		return this.getSprites()[index];
	}
	
	/**
	 * Returns the number of elements in the sprites array of the object.
	 * 
	 * @return	The number of elements in the sprites array of the object.		
	 */
	@Immutable
	public int getNbSprites(){
		return this.getSprites().length;
	}
	
	private final Sprite[] sprites;
	
	/**
	 * Return the number of frames for an animation.
	 * 
	 * @return	The number of frames for one kind of animation. (e.g. walking in a given direction)
	 */
	@Basic @Immutable
	public int getNbFrames(){
		return ( this.getNbSprites() - 8) / 2;
	}	
	
	
	/**
	 * Return the current iteration number of the animation.
	 * 
	 * @return	The current iteration number of the animation.
	 */
	@Basic
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
		assert (animationIndex >= 0) && (animationIndex <= this.getNbFrames());
		
		this.animationIndex = animationIndex;
	}
	
	/**
	 * Increments the frame index, or sets it to 0 if the current frame is the last one.
	 * 
	 * @post	If the animation index was smaller than the number of frames of the animation minus one,
	 * 			the animation index is now increased with one. If the animation index was equal to
	 * 			the animation index plus one, the animation index is now reset to 0.
	 * 			| if (this.getAnimationIndex() + 1 < this.nbFrames)
	 * 			|	then new.getAnimationIndex() == this.getAnimationIndex() + 1 
	 * 			| else if (this.getAnimationIndex() + 1  == this.nbFrames)
	 * 			|	then new.getAnimationIndex() == 0		
	 */
	private void incrementAnimationIndex(){
		this.setAnimationIndex( (this.getAnimationIndex() + 1) % this.getNbFrames() );
	}
	
	/**
	 * Updates the current animation frame, based on the time since the last frame.
	 * 
	 * @pre The given timer instance is not null.
	 * 		| timer != null
	 * @param timer
	 * 			A valid timer instance.
	 * 
	 */
	public void updateAnimationIndex(Timer timer){
		assert timer != null;
		
		while(Util.fuzzyGreaterThanOrEqualTo(timer.getSinceLastSprite(), 0.075)){
			this.incrementAnimationIndex();
			timer.increaseSinceLastSprite(-0.075);
		}
	}
	
	private int animationIndex;
	
}
