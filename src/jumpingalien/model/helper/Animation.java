package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.exceptions.IllegalHeightException;

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
	 * @throws 	IllegalWidthException
	 * 				The width of at least one sprite in the given array sprites is not a valid width.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				The height of at least one sprite in the given array sprites is not a valid height.
	 * 				| for some sprite in sprites:
	 * 				|	! isValidHeight(sprite.getHeight())
	 */
	public Animation(GameObject gameObject, Sprite[] sprites) throws IllegalWidthException, IllegalHeightException{
		assert sprites.length >= 0;
		
		for (int i = 0; i < sprites.length; i++){
			if( !Mazub.isValidWidth(sprites[i].getWidth()))
				throw new IllegalWidthException(sprites[i].getWidth());
			if ( !Mazub.isValidHeight(sprites[i].getHeight()))
				throw new IllegalHeightException(sprites[i].getHeight());
		}
		
		this.gameObject = gameObject;
		this.sprites = sprites;
		this.setSpriteIndex(0);
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	final GameObject gameObject;

	
	
	public Sprite getCurrentSprite(){
		return this.getSpriteAt(this.getSpriteIndex());
	}	
	
	public void updateSpriteIndex(){
		int currentIndex = this.getSpriteIndex();
		
		if(getGameObject().getOrientation() == Orientation.RIGHT){
			this.setSpriteIndex(1);
		}else{
			this.setSpriteIndex(0);
		}	
		
		if(getGameObject().doesCollide()){
			this.setSpriteIndex(currentIndex); // undo changes
		}
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
	
	/**
	 * Variable registering the sprites of this Animation.
	 */
	private final Sprite[] sprites;	
	
	
	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}
	
	private int spriteIndex;
	
	
	
	
	
}
