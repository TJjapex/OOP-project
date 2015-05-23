package jumpingalien.model.helper;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.GameObject;
import jumpingalien.util.Sprite;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.model.exceptions.IllegalHeightException;

/**
 * An Animation class, implemented with methods to serve as a helper class for the class Game object.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 3.0
 * 
 * @note	For an Animation instance, the sprites can only be set once in the constructor. 
 * 			If new sprites are required, the instance should be destroyed and a new instance should be created.
 *
 * @invar	The sprites array of this animation has a length greater than or equal to 0
 * 			|	this.getNbSprites() >= 0
 * @invar	Sprite index is always positive
 * 			| 	this.getSpriteIndex() >= 0
 */
public class Animation {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class Animation.
	 * 
	 * @param 	sprites
	 * 				The array of sprite images for the animation.
	 * @pre		The length of the given array sprites should not be null and should be greater or equal to 2.
	 * 			| sprites != null && (Array.getLength(sprites) >= 2)
	 * @post	The Game object related to the Animation is equal to gameObject.
	 * 			| new.getGameObject() == gameObject
	 * @post	The initial sprites of the animation are equal to the given array sprites.
	 * 			| new.sprites == sprites
	 * @effect	The initial sprite index is set to 0.
	 * 			| setSpriteIndex(0)
	 * @throws 	IllegalWidthException
	 * 				The width of at least one sprite in the given array sprites is not a valid width.
	 * 				| for some sprite in sprites:
	 * 				|	! GameObject.isValidWidth(sprite.getWidth())
	 * @throws	IllegalHeightException
	 * 				The height of at least one sprite in the given array sprites is not a valid height.
	 * 				| for some sprite in sprites:
	 * 				|	! GameObject.isValidHeight(sprite.getHeight())
	 */
	public Animation(GameObject gameObject, Sprite[] sprites) throws IllegalWidthException, IllegalHeightException{
		assert sprites != null && sprites.length >= 2;
		
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
	
	/***************************************************** GAME OBJECT *************************************************/
	
	/**
	 * Return the related Game object.
	 * 
	 * @return	The related Game object.
	 */
	@Basic @Immutable
	public GameObject getGameObject() {
		return gameObject;
	}
	
	/**
	 * Variable registering the related Game object.
	 */
	private final GameObject gameObject;

	/******************************************************* SPRITES ***************************************************/
	
	/**
	 * Return the current sprite of the Animation.
	 * 
	 * @return	The current sprite of the Animation.
	 * @note 	must be worked out nominally
	 */
	public Sprite getCurrentSprite(){
		return this.getSpriteAt(this.getSpriteIndex());
	}	
	
	/**
	 * Update the current sprite according to the state of the related Game object.
	 * 
	 * @effect	If the current Orientation of the related Game object is RIGHT, set the sprite
	 * 			index of this Animation to 1, else set it to 0.
	 * 			| if ( this.getGameObject().getOrientation() == Orientation.RIGHT )
	 * 			|	then this.setSpriteIndex(1)
	 * 			| else
	 * 			| 	setSpriteIndex(0)
	 * @effect	If the related Game object does collide after changing the sprite index, reset the 
	 * 			sprite index.
	 * 			| if ( this.getGameObject().doesCollide() )
	 * 			|	then new.setSpriteIndex( this.getSpriteIndex() )
	 */
	public void updateSpriteIndex(){
		int currentIndex = this.getSpriteIndex();
		
		if( this.getGameObject().getOrientation() == Orientation.RIGHT){
			this.setSpriteIndex(1);
		}else{
			this.setSpriteIndex(0);
		}	
		
		if( this.getGameObject().doesCollide()){ // if new sprite causes collision because of a greater width or height
			this.setSpriteIndex(currentIndex); // undo changes
		}
	}
	
	/**
	 * Return the sprites of this Animation.
	 * 
	 * @return	The sprites of this Animation.
	 */
	@Basic @Immutable
	public Sprite[] getSprites(){
		return this.sprites;
	}
	
	/**
	 * Return the sprite of this Animation, with the given index.
	 * 
	 * @param 	index
	 * 				The index of the sprite.
	 * @return	The sprite of this Animation, with the given index.
	 */
	@Immutable
	public Sprite getSpriteAt(int index){
		return this.getSprites()[index];
	}
	
	/**
	 * Return the number of elements in the sprites array of the related Game object.
	 * 
	 * @return	The number of elements in the sprites array of the related Game object.		
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
	 * Return the current sprite index for this Animation.
	 * 
	 * @return	The current sprite index for this Animation.
	 */
	@Basic @Raw
	public int getSpriteIndex() {
		return this.spriteIndex;
	}

	/**
	 * Set the current sprite index to the given one.
	 * 
	 * @param 	spriteIndex
	 * 				The given sprite index.
	 */
	@Basic @Raw
	protected void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}
	
	/**
	 * Variable registering the sprite index of this Animation.
	 */
	private int spriteIndex;	
	
}
