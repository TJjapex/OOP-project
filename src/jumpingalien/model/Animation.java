package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.Sprite;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Animation {
	
	// Geen idee of dit wel beter is dan gewoon cijfertjes :p mss beter gewoon cijfertjes en in ne comment de betekenis want dees is ook echt nie handig
	private final int NOTMOVING_HASNOTMOVED_NOTDUCKING = 0;
	private final int NOTMOVING_HASNOTMOVED_DUCKING = 1;
	private final int NOTMOVING_HASMOVED_RIGHT_NOTDUCKING = 2;
	private final int NOTMOVING_HASMOVED_LEFT_NOTDUCKING = 3;
	private final int MOVING_RIGHT_JUMPING_NOTDUCKING = 4;
	private final int MOVING_LEFT_JUMPING_NOTDUCKING = 5;
	private final int DUCKING_MOVING_RIGHT_OR_HASMOVED = 6;
	private final int DUCKING_MOVING_LEFT_OR_HASMOVED = 7;
	private final int NOTDUCKING_NOTJUMPING_MOVING_RIGHT = 8;
	private final int DUCKING_NOTJUMPING_MOVING_LEFT = 9;
	
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
		//this.nbFrames = ( this.sprites.length - 8) / 2;
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
	 * 				The index of the sprite
	 * @return	The sprite of this animation class, with the given index.
	 */
	public Sprite getSpriteAt(int index){
		return this.getSprites()[index];
	}
	
	/**
	 * Returns the number of elements in the sprites array of the object.
	 * 
	 * @return	The number of elements in the sprites array of the object.		
	 */
	public int getNbSprites(){
		return this.getSprites().length;
	}
	
	private final Sprite[] sprites;
	
	/**
	 * Return the number of frames for an animation.
	 * 
	 * @return	The number of frames for one kind of animation. (e.g. walking in a given direction)
	 */
	@Basic // @Immutable
	public int getNbFrames(){
		return ( this.getNbSprites() - 8) / 2;
		//return this.nbFrames;
	}	
	
	//private final int nbFrames;	
	
	
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
	@Basic
	private void setAnimationIndex(int animationIndex){
		assert (animationIndex >= 0) && (animationIndex <= this.getNbFrames());
		
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
		this.setAnimationIndex( (this.getAnimationIndex() + 1) % this.getNbFrames() );
	}
	
	private int animationIndex;
	
}
