package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
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
	private Mazub dummyMazub;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// The World needs at least one Mazub
		dummyMazub = new Mazub(0, 110, spriteArrayForSize(66, 92, 10));
		sprites = spriteArrayForSize(50,28, 2);
		school = new School();
		secondSchool = new School();
		slime = new Slime(60, 60, sprites, school);
		secondSlime = new Slime(120, 60, sprites, secondSchool);
		thirdSlime = new Slime(180, 60, sprites, secondSchool);
		
		world = new World(50, 20, 15, 400, 400, 4, 1);
		// Create an area to lock up the Slimes
		world.setGeologicalFeature(0, 0, Terrain.SOLID);
		world.setGeologicalFeature(0, 1, Terrain.SOLID);
		world.setGeologicalFeature(1, 0, Terrain.SOLID);
		world.setGeologicalFeature(2, 0, Terrain.SOLID);
		world.setGeologicalFeature(3, 0, Terrain.SOLID);
		world.setGeologicalFeature(4, 0, Terrain.SOLID);
		world.setGeologicalFeature(5, 0, Terrain.SOLID);
		world.setGeologicalFeature(6, 0, Terrain.SOLID);
		world.setGeologicalFeature(6, 1, Terrain.SOLID);
		dummyMazub.setWorldTo(world);
		slime.setWorldTo(world);
		secondSlime.setWorldTo(world);
		thirdSlime.setWorldTo(world);
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
		assertEquals(60, slime.getRoundedPositionX());
		assertEquals(60, slime.getRoundedPositionY());
		assertEquals(sprites[0], slime.getCurrentSprite());
		assertEquals(school, slime.getSchool());
	}
	
	/******************************************************** SCHOOL ***************************************************/
	
	@Test
	public void testSwitchingSchool(){
		// Iterate long enough to make sure the Slimes overlapped
		for (int i=0; i<10; i += 1){
			world.advanceTime(0.2);
		}
		assertEquals(secondSchool, slime.getSchool());
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	
	
	/****************************************************** COLLISION **************************************************/
	
	
	
	/******************************************************* OVERLAP **************************************************/
	
	
	
	/***************************************************** TERMINATION *************************************************/
	
	
	
}
