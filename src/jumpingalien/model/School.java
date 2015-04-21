package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

// All aspects shall ONLY be specified in a formal way.

/**
 * A class of Schools, groups of Slimes in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 * 
 * @invar
 * 			| hasProperSlimes()
 */

public class School {
	
	/************************************************** GENERAL ***********************************************/
	

	
	/************************************************ CONSTRUCTOR *********************************************/
	
	public School(){
		
	}
	
	/************************************************** RELATIONS **********************************************/
	
	public boolean canHaveAsSlime(Slime slime){
		return slime != null  && !this.hasAsSlime(slime) && !this.isTerminated();
	}
	
	public boolean hasProperSlimes(){
		for(Slime slime: this.slimes){
			if(slime.getSchool() != this)
				return false;
		}
		return true;
	}
	
	public boolean hasAsSlime(Slime slime){
		return this.getAllSlimes().contains(slime);
	}
	
	public void addAsSlime(Slime slime){
		assert canHaveAsSlime(slime);
		assert slime.getSchool() == this;

		slimes.add(slime);
	}
	
	public void removeAsSlime(Slime slime){
		assert slime != null && !slime.hasSchool();
		assert hasAsSlime(slime);
		
		slimes.remove(slime);
	}
	
	public Set<Slime> getAllSlimes(){
		HashSet<Slime> slimesClone =  new HashSet<Slime>(this.slimes);
		return slimesClone;
	}
	
	public int getNbSlimes(){
		return this.getAllSlimes().size();
	}
	
	public Set<Slime> slimes = new HashSet<Slime>();
	
	public void terminate() throws IllegalStateException{
		if(this.getNbSlimes() > 0)
			throw new IllegalStateException("School still has slimes!");
		this.terminated = true;
	}
	
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
	
	
}
