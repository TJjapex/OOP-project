package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.exceptions.IllegalHeightException;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 * 
 * @note	For an Animation instance, the sprites can only be set once in the constructor. 
 * 			If new sprites are required, the instance should be destroyed and a new instance should be created.
 *
 * @invar	The sprites array of this animation has a length greater than or equal to 10 and is the length an even number.
 * 			|	this.getNbSprites() >= 10 && (this.getNbSprites() % 2) == 0;
 * @invar	The number of frames for the walking animation should be greater than 0.
 * 			| 	this.getNbFrames() > 0
 */
public class MazubAnimation extends Animation {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class MazubAnimation.
	 * 
	 * @param 	sprites
	 * 				The array of sprite images for the Animation.
	 * @pre		The length of the given array sprites should be greater or equal to 10 and an even number.
	 * 			| (Array.getLength(sprites) >= 10) && (Array.getLength(sprites) % 2 == 0)
	 * @effect	The MazubAnimation is initiated with the constructor of his superclass Animation.
	 * 			| super(mazub, sprites)
	 * @effect	Set the initial animation index to 0.
	 * 			| setAnimationIndex(0)
	 * @throws 	IllegalWidthException
	 * 				The width of at least one sprite in the given array sprites is not a valid width.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				The height of at least one sprite in the given array sprites is not a valid height.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public MazubAnimation(Mazub mazub, Sprite[] sprites) throws IllegalWidthException, IllegalHeightException{
		super(mazub, sprites);
		assert sprites.length >= 10 && sprites.length % 2 == 0;
		
		this.setAnimationIndex(0);
	}
	
	/****************************************************** ANIMATION **************************************************/
	
	/**
	 *  Update the sprite index of the Mazub instance, depending on his current status.
	 * 
	 * @return	The sprite index in sprites for the current status of the given Mazub instance.
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
	@Override
	public void updateSpriteIndex(){
		int currentIndex = this.getSpriteIndex();
		Mazub mazub = (Mazub) this.getGameObject();
		
		int index = 0;
		if(!mazub.isMoving()){
			if(!mazub.hasMovedInLastSecond()){
				if(!mazub.isDucking()){
					index = 0;
				}else{
					index = 1;
				}
			}else{
				if(!mazub.isDucking()){
					if(mazub.getOrientation() == Orientation.RIGHT){
						index = 2;
					}else{ // LEFT
						index = 3;
					}
				}else{
					if(mazub.getOrientation() == Orientation.RIGHT){
						index = 6;
					}else{ // LEFT
						index = 7;
					}
				}
			}
			
		}else{ // MOVING
			if(!mazub.isOnGround()){
				if(!mazub.isDucking()){
					if(mazub.getOrientation() == Orientation.RIGHT){
						index = 4;
					}else{ // LEFT
						index = 5;
					}
				}
			}
			
			if(mazub.isDucking()){
				if(mazub.getOrientation() == Orientation.RIGHT){
					index = 6;
				}else{ // LEFT
					index = 7;
				}
			}
			if(!mazub.isDucking() && mazub.isOnGround()){
				if(mazub.getOrientation() == Orientation.RIGHT){
					index = 8 + this.getAnimationIndex();
				}else{ // LEFT
					index = 8 + this.getNbFrames() + this.getAnimationIndex();
				}
			}
		}
		
		this.setSpriteIndex(index);
		
		if(mazub.doesCollide()){
			this.setSpriteIndex(currentIndex); // undo changes
		}
	}
	
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
	 * @effect	Set the animation index to the remainder of the current animation index plus 1 divided by the current
	 * 			number of frames.
	 * 			| setAnimationIndex( (this.getAnimationIndex() + 1) % this.getNbFrames() )	
	 */
	private void incrementAnimationIndex(){
		this.setAnimationIndex( (this.getAnimationIndex() + 1) % this.getNbFrames() );
	}
	
	/**
	 * Update the current animation frame, based on the time since the last frame.
	 * 
	 * @param 	timer
	 * 				A valid timer instance.
	 * @pre 	The given timer instance is not null.
	 * 			| timer != null
	 */
	public void updateAnimationIndex(Timer timer){
		assert timer != null;
		
		while(Util.fuzzyGreaterThanOrEqualTo(timer.getSinceLastSprite(), 0.075)){
			this.incrementAnimationIndex();
			timer.increaseSinceLastSprite(-0.075);
		}
	}

	/**
	 * Variable registering the current index of the Animation.
	 */
	private int animationIndex;
	
}
