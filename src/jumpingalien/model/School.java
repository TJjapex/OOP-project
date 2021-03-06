package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Schools, groups of Slimes in the game world of Mazub.
 * 
 * @author 	Thomas Verelst, Hans Cauwenbergh
 * @note	See the class Mazub for further information about our project.
 * @version 2.0
 * 
 * @invar	| hasProperSlimes()
 */
public class School {
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Constructor for the class School.
	 * 
	 */
	public School(){
		
	}
	
	/******************************************************** SLIME ****************************************************/
	
	/**
	 * Checks whether or not a School can have slime as its member.
	 * 
	 * @param 	slime
	 * 				The Slime to check.
	 * @return	| result == ( ( slime != null) && ( ! this.hasAsSlime(slime) ) && ( ! this.isTerminated() ) )
	 */
	public boolean canHaveAsSlime(Slime slime){
		return slime != null  && !this.hasAsSlime(slime) && !this.isTerminated();
	}
	
	/**
	 * Checks whether or not a School has proper Slimes as its members.
	 * 
	 * @return	| result == ( ! ( for some slime in this.getAllSlimes()
	 * 								slime.getSchool != this				) )
	 */
	@Raw
	public boolean hasProperSlimes(){
		for(Slime slime: this.getAllSlimes()){
			if(slime.getSchool() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether or not the School has slime as its member.
	 * 
	 * @param 	slime
	 * 				The Slime to check.
	 * @return	| result == ( this.getAllSlimes().contains(slime) )
	 */
	@Raw
	public boolean hasAsSlime(Slime slime){
		return this.getAllSlimes().contains(slime);
	}
	
	/**
	 * Add a Slime to a School.
	 * 
	 * @param 	slime
	 * 				The Slime to add.
	 * @pre		| canHaveAsSlime(slime)
	 * @pre		| slime.getSchool() == this
	 * @post	| new.hasAsSlime == true
	 */
	@Basic @Raw
	void addAsSlime(Slime slime){
		assert canHaveAsSlime(slime);
		assert slime.getSchool() == this;

		slimes.add(slime);
	}
	
	/**
	 * Remove a Slime from a School.
	 * 
	 * @param 	slime
	 * 				The Slime to remove.
	 * @pre		| (slime != null) && (! slime.hasSchool())	
	 * @pre		| hasAsSlime(slime)
	 * @post	| new.hasAsSlime == false
	 */
	@Basic @Raw
	void removeAsSlime(Slime slime){
		assert slime != null && !slime.hasSchool();
		assert this.hasAsSlime(slime);
		
		slimes.remove(slime);
	}
	
	/**
	 * Return all Slimes that are member of this School.
	 * 
	 * @return	A Hashset of all Slimes that are member of this School.
	 */
	@Basic
	public Set<Slime> getAllSlimes(){
		HashSet<Slime> slimesClone =  new HashSet<Slime>(this.slimes);
		return slimesClone;
	}
	
	/**
	 * Return the number of Slimes that are member of this School.
	 * 
	 * @return	An integer representing the number of Slimes that are member of this School.
	 */
	public int getNbSlimes(){
		return this.getAllSlimes().size();
	}
	
	/**
	 * Variable registering the slimes that are member of this School.
	 */
	public Set<Slime> slimes = new HashSet<Slime>();
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Terminate a School.
	 * 
	 * @post	| new.isTerminated == true
	 * @throws	IllegalStateException
	 * 				| getNbSlimes() > 0
	 */
	void terminate() throws IllegalStateException{
		if(this.getNbSlimes() > 0)
			throw new IllegalStateException("School still has slimes!");
		
		this.terminated = true;
	}
	
	/**
	 * Check if a School is terminated.
	 * 
	 * @return	| result == ( this.terminated )
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * Variable registering the terminated status of a School.
	 */
	private boolean terminated = false;
	
}
