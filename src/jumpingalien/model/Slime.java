package jumpingalien.model;

/**
 * A class of Slimes, enemy characters in the game world of Mazub.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class Slime {

	// * possess 100 hit-points
	// * move randomly to the left or right
	// * horizontal acceleration is equal to 0.7 [m/s^2] 
	// * maximal horizontal velocity is equal to 2.5 [m/s]
	// * movement periods have a duration of 2s to 6s
	// * lose 50 hit-points when making contact with Mazub or Shark
	// * do not attack each other but block each others' movement
	// * Plants do not block Slimes
	// * Slimes lose hit-points upon touching water/magma (same as Mazub)
	// * Slimes are organised in groups, called schools: - each Slime belongs to exactly one school
	// 													 - Slimes may switch from one school to another
	//													 - when a Slime loses hit-points, all other Slimes of that
	//													   school lose 1 hit-point
	//													 - upon switching from school a Slime hands over 1 hit-point
	//													   to every Slime of the old school and every Slime of the 
	//													   new school hands over 1 hit-point to the joining Slime
	//													 - Slimes switch from school when they collide with a 
	//													   Slime of a larger school
	//													 - no more than 10 schools in a game world
		
}
