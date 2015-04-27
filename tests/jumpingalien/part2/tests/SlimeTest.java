package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SlimeTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	private Sprite[] sprites;
	private Slime slime;
	private Slime secondSlime;
	private Slime thirdSlime;
	private World world;
	private School school;
	private School secondSchool;
	private Mazub mazub;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		IFacadePart2 facade = new Facade();
		
		// The World needs at least one Mazub
		mazub = facade.createMazub(0, 110, spriteArrayForSize(66, 92, 10));
		sprites = spriteArrayForSize(50,28, 2);
		school = facade.createSchool();
		slime = facade.createSlime(60, 60, sprites, school);
		
		world = facade.createWorld(50, 20, 15, 400, 400, 4, 1);
		// Create an area to lock up the Slimes7
		facade.setGeologicalFeature(world, 0, 0, 1);
		facade.setGeologicalFeature(world, 0, 1, 1);
		facade.setGeologicalFeature(world, 1, 0, 1);
		facade.setGeologicalFeature(world, 2, 0, 1);
		facade.setGeologicalFeature(world, 3, 0, 1);
		facade.setGeologicalFeature(world, 4, 0, 1);
		facade.setGeologicalFeature(world, 5, 0, 1);
		facade.setGeologicalFeature(world, 6, 0, 1);
		facade.setGeologicalFeature(world, 6, 1, 1);
		facade.setMazub(world, mazub);	
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Check if the Slime is correctly initiated.
	 */
	@Test
	public void testConstructor(){
		IFacadePart2 facade = new Facade();
		facade.addSlime(world, slime);
		
		assertEquals(60, facade.getLocation(slime)[0]);
		assertEquals(60, facade.getLocation(slime)[1]);
		assertEquals(sprites[0], facade.getCurrentSprite(slime));
		assertEquals(school, facade.getSchool(slime));
	}
	
	/**
	 * Check if the number of Slimes in the World is right.
	 */
	@Test
	public void testNbSlimesInWorld(){
		IFacadePart2 facade = new Facade();
		secondSchool = facade.createSchool();
		secondSlime = facade.createSlime(120, 60, sprites, secondSchool);
		thirdSlime = facade.createSlime(180, 60, sprites, secondSchool);
		facade.addSlime(world, slime);
		facade.addSlime(world, secondSlime);
		facade.addSlime(world, thirdSlime);
		
		facade.startGame(world);;
		assertEquals(3, facade.getSlimes(world).size());
	}
	
	/******************************************************** SCHOOL ***************************************************/
	
	/**
	 * Check if a Slime correctly switches from School.
	 */
	@Test
	public void testSwitchingSchool(){
		IFacadePart2 facade = new Facade();
		secondSchool = facade.createSchool();
		secondSlime = facade.createSlime(120, 60, sprites, secondSchool);
		thirdSlime = facade.createSlime(180, 60, sprites, secondSchool);
		facade.addSlime(world, slime);
		facade.addSlime(world, secondSlime);
		facade.addSlime(world, thirdSlime);
		
		facade.startGame(world);
		
		// Iterate long enough to make sure the Slimes overlapped
		for (int i=0; i<30; i += 1){
			facade.advanceTime(world,0.2);
		}
		
		assertTrue(slime.hasProperSchool());
		assertEquals(secondSchool, facade.getSchool(slime));
		assertTrue(slime.getSchool().hasProperSlimes());
		assertEquals(100, slime.getNbHitPoints());
		assertEquals(99, secondSlime.getNbHitPoints());
		assertEquals(99, thirdSlime.getNbHitPoints());
	}
	
	/**
	 * Check that if a Slime member of a School takes damage, the other members do too.
	 */
	@Test
	public void testTakeDamageWithSchool(){
		IFacadePart2 facade = new Facade();
		secondSchool = facade.createSchool();
		secondSlime = facade.createSlime(120, 60, sprites, secondSchool);
		thirdSlime = facade.createSlime(180, 60, sprites, secondSchool);
		facade.addSlime(world, slime);
		facade.addSlime(world, secondSlime);
		facade.addSlime(world, thirdSlime);
		
		facade.startGame(world);
		
		// Iterate long enough to make sure the Slimes overlapped
		for (int i=0; i<30; i += 1){
			facade.advanceTime(world,0.2);
		}
		
		int expectedNbHitPointsFirstSlime = slime.getNbHitPoints();
		int expectedNbHitPointsSecondSlime = secondSlime.getNbHitPoints();
		int expectedNbHitPointsThirdSlime = thirdSlime.getNbHitPoints();
		
		facade.startMoveRight(mazub);
		for (int i=0; i<5; i += 1){
			facade.advanceTime(world,0.2);
		}
		
		if (slime.getNbHitPoints() <= expectedNbHitPointsFirstSlime - 50){ // first slime took damage from mazub 
			expectedNbHitPointsFirstSlime -= 50;
			expectedNbHitPointsSecondSlime -= 1;
			expectedNbHitPointsThirdSlime -= 1;
		}
		
		if (secondSlime.getNbHitPoints() <= expectedNbHitPointsSecondSlime - 50){ // second slime took damage from mazub 
			expectedNbHitPointsFirstSlime -= 1;
			expectedNbHitPointsSecondSlime -= 50;
			expectedNbHitPointsThirdSlime -= 1;
		}
		
		if (thirdSlime.getNbHitPoints() <= expectedNbHitPointsThirdSlime - 50){ // third slime took damage from mazub 
			expectedNbHitPointsFirstSlime -= 1;
			expectedNbHitPointsSecondSlime -= 1;
			expectedNbHitPointsThirdSlime -= 50;
		}
		
		assertEquals(expectedNbHitPointsFirstSlime, slime.getNbHitPoints());
		assertEquals(expectedNbHitPointsSecondSlime, secondSlime.getNbHitPoints());
		assertEquals(expectedNbHitPointsThirdSlime, thirdSlime.getNbHitPoints());
		
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Check that a slime can never move upwards.
	 */
	@Test
	public void testNoPositiveChangeInYPosition(){
		IFacadePart2 facade = new Facade();
		facade.addSlime(world, slime);
		double oldPositionYFirstSlime = slime.getPositionY();
		
		facade.startGame(world);
		
		// Check for each slime that no upwards movement has taken place
		for (int i=0; i<50; i += 1){
			facade.advanceTime(world,0.2);	
			assertTrue(oldPositionYFirstSlime >= slime.getPositionY());
			oldPositionYFirstSlime = slime.getPositionY();
		}
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Check if the Slime changes direction upon horizontal collision.
	 */
	@Test
	public void testChangeDirectionUponHorizontalCollision(){
		IFacadePart2 facade = new Facade();
		facade.addSlime(world, slime);
		facade.setGeologicalFeature(world, 3, 1, 1);

		facade.advanceTime(world, 0.01); // to start the random movement of the slime
		if (slime.getOrientation() == Orientation.RIGHT){
			facade.advanceTime(world,0.2);	
			facade.advanceTime(world,0.2);	
			assertEquals(Orientation.LEFT, slime.getOrientation());
		} else{
			facade.advanceTime(world,0.2);	
			facade.advanceTime(world,0.2);	
			assertEquals(Orientation.RIGHT, slime.getOrientation());
		}
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Check if the Slime takes damage when a Mazub overlaps with it and is immune for 0.6s after taking damage.
	 */
	@Test
	public void testDamageUponMazubOverlap(){
		IFacadePart2 facade = new Facade();
		slime = facade.createSlime(200, 60, sprites, school);
		facade.addSlime(world, slime);
		int initialNbHitPointsSlime = slime.getNbHitPoints();
		int expectedNbHitPointsSlime = slime.getNbHitPoints();
		int nonDamageIterations = 3;
		
		facade.startGame(world);
		
		facade.startMoveRight(mazub);
		
		// Check if slime took damage and if so, check that the previous damage the slime got was more than 0.6s ago.
		for (int i=0; i<20; i += 1){
			facade.advanceTime(world,0.2);
			assertTrue(slime.getNbHitPoints() == expectedNbHitPointsSlime ||
					   ( slime.getNbHitPoints() == expectedNbHitPointsSlime - 50 &&
						 nonDamageIterations >= 3 )	);
			if ( slime.getNbHitPoints() == expectedNbHitPointsSlime - 50 ){
				expectedNbHitPointsSlime -= 50;
				nonDamageIterations = 1;
			} else {
				nonDamageIterations += 1;
			}
		}
		
		// Make sure that slime took damage at all due to an overlap with mazub
		assertFalse(slime.getNbHitPoints() == initialNbHitPointsSlime);
		
	}
	
	/***************************************************** TERMINATION *************************************************/
	
	/**
	 * Check that the Slime gets terminated with a 0.6s delay after he's killed.
	 */
	@Test
	public void testTerminate(){
		IFacadePart2 facade = new Facade();
		Slime deadSlime = new Slime(60, 60, 1.0, 0.0, 2.5, 0.7, sprites, school, 0, 100);
		facade.addSlime(world, deadSlime);
		
		facade.startGame(world);
		
		assertTrue(deadSlime.isKilled());
		
		for (int i=0; i<3; i += 1){
			facade.advanceTime(world,0.2);
		}
		facade.advanceTime(world, 0.1);
		assertTrue(deadSlime.isTerminated());
		assertFalse(deadSlime.hasWorld());		
	}
	
}
