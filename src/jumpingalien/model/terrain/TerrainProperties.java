package jumpingalien.model.terrain;


import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Terrain properties for the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 1.0
 */
public class TerrainProperties {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class TerrainProperties.
	 * 
	 * @pre		| damageTime > 0 || (damageTime == 0 && damage == 0)
	 * @param 	passable
	 * 				A boolean representing whether or not a terrain is passable.
	 * @param 	damage
	 * 				An integer representing the damage a Game object should take when it's on this terrain.
	 * @param 	damageTime
	 * 				A double representing the time interval between different cycles of taking damage on this terrain.
	 * @param 	instantDamage
	 * 				A boolean representing if a Game object takes instant damage on this terrain or only after
	 * 			 	the damage time.
	 * @post	| new.isPassable == passable
	 * @post	| new.getDamage() == damage
	 * @post	| new.damageTime() == damageTime
	 * @post	| new.IsInstantDamage == instantDamage
	 */
	public TerrainProperties(boolean passable, int damage, double damageTime, boolean instantDamage) {
		assert damageTime > 0 || (damageTime == 0 && damage == 0);
		
		this.passable = passable;
		this.damage = damage;
		this.damageTime = damageTime;
		this.instantDamage = instantDamage;
	}
	
	/****************************************************** PROPERTIES *************************************************/
		
	/**
	 * Check whether or not this terrain is passable.
	 * 
	 * @return	| result = ( this.passable )
	 */
	@Basic @Immutable
	public boolean isPassable(){
		return this.passable;
	}
	
	/**
	 * Variable registering whether or not this terrain is passable.
	 */
	private final boolean passable;
	
	/**
	 * Return the amount of damage a Game object should take when it's on this terrain.
	 * 
	 * @return	An integer representing the amount of damage a Game object should take when it's on this terrain.
	 */
	@Basic @Immutable
	public int getDamage(){
		return this.damage;
	}
	
	/**
	 * Variable registering the amount of damage a Game object should take when it's on this terrain.
	 */
	private final int damage;
	
	/**
	 * Return the time interval between different cycles of taking damage on this terrain.
	 * 
	 * @return	A double representing the time interval between different cycles of taking damage on this terrain.
	 */
	@Basic @Immutable
	public double getDamageTime(){
		return this.damageTime;
	}	
	
	/**
	 * Variable registering the the time interval between different cycles of taking damage on this terrain.
	 */
	private final double damageTime;
	
	/**
	 * Return if a Game object takes instant damage on this terrain or only after the damage time.
	 * 
	 * @return	A boolean representing if a Game object takes instant damage on this terrain or only after
	 * 			the damage time.
	 */
	@Basic @Immutable
	public boolean isInstantDamage(){
		return this.instantDamage;
	}	
	
	/**
	 * Variable registering if a Game object takes instant damage on this terrain or only after the damage time.
	 */
	private final boolean instantDamage;
	
}
