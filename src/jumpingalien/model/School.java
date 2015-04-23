package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

/**
 * A class of Schools, groups of Slimes in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
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
	 * 				The slime to check.
	 * @return	| result == ( ( slime != null) && ( ! this.hasAsSlime(slime) ) && ( ! this.isTerminated() ) )
	 */
	public boolean canHaveAsSlime(Slime slime){
		return slime != null  && !this.hasAsSlime(slime) && !this.isTerminated();
	}
	
	/**
	 * Checks whether or not a School has proper slimes as its members.
	 * 
	 * @return	| result == ( ! ( for some slime in this.getAllSlimes()
	 * 								slime.getSchool != this				) )
	 */
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
	 * 				The slime to check.
	 * @return	| result == ( this.getAllSlimes().contains(slime) )
	 */
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
	public void addAsSlime(Slime slime){
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
	public void removeAsSlime(Slime slime){
		assert slime != null && !slime.hasSchool();
		assert this.hasAsSlime(slime);
		
		slimes.remove(slime);
	}
	
	/**
	 * Return all Slimes that are member of this School.
	 * 
	 * @return	A Hashset of all slimes that are member of this School.
	 */
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
	public void terminate() throws IllegalStateException{
		if(this.getNbSlimes() > 0)
			throw new IllegalStateException("School still has slimes!");
		this.terminated = true;
	}
	
	/**
	 * Check if a School is terminated.
	 * 
	 * @return	| result == ( this.terminated )
	 */
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * Variable registering the terminated status of a School.
	 */
	private boolean terminated = false;
	
}
