package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.GameObject;
import jumpingalien.util.Sprite;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.exceptions.IllegalHeightException;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Game object.
 * 
 * @note	For an Animation instance, the sprites can only be set once in the constructor. 
 * 			If new sprites are required, the instance should be destroyed and a new instance should be created.
 *
 * @invar	The sprites array of this animation has a length greater than or equal to 0
 * 			|	this.getNbSprites() >= 0
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
	 * 			| (Array.getLength(sprites) >= 0)
	 * @post	The initial sprites of the animation are equal to the given array sprites.
	 * 			| this.sprites == sprites
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
		assert sprites != null && sprites.length >= 0;
		
		for (int i = 0; i < sprites.length; i++){
			if( !GameObject.isValidWidth(sprites[i].getWidth()))
				throw new IllegalWidthException(sprites[i].getWidth());
			if ( !GameObject.isValidHeight(sprites[i].getHeight()))
				throw new IllegalHeightException(sprites[i].getHeight());
		}
		
		this.gameObject = gameObject;
		this.sprites = sprites;
		this.setSpriteIndex(0);
	}
	
	/**
	 * Returns the related game object
	 * 
	 * @return
	 * 		The related game object
	 */
	@Basic
	public GameObject getGameObject() {
		return gameObject;
	}
	
	private final GameObject gameObject;

	/**
	 * Returns the current sprite
	 * 
	 * @return
	 * 		The current sprite
	 */
	public Sprite getCurrentSprite(){
		return this.getSpriteAt(this.getSpriteIndex());
	}	
	
	/**
	 * Updates the current sprite according to the state of the related Game object
	 */
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
	
	/**
	 * Returns the current sprite index
	 * 
	 * @return
	 * 		The current sprite index
	 */
	@Basic @Raw
	public int getSpriteIndex() {
		return this.spriteIndex;
	}

	/**
	 * Sets the current sprite index to the given one
	 * 
	 * @param spriteIndex
	 * 			The given sprite index
	 */
	@Basic @Raw
	protected void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}
	
	private int spriteIndex;	
	
}
