package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
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

public class PlantTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;

	private Sprite[] sprites;
	private Plant plant;
	private Mazub alien;
	private World world;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sprites = spriteArrayForSize(54, 27, 2);
		plant = new Plant(160, 180, sprites);
		world = new World(50, 20, 15, 400, 400, 4, 1);
		
		Sprite[] alienSprites = spriteArrayForSize(20, 20);
		alien = new Mazub(100,100, alienSprites); // At least one alien in world
		alien.setWorldTo(world);
		plant.setWorldTo(world);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	/**
	 * Check if the Plant is correctly initiated.
	 */
	@Test
	public void testConstructor(){
		assertEquals(160, plant.getRoundedPositionX());
		assertEquals(180, plant.getRoundedPositionY());
		assertEquals(sprites[0], plant.getCurrentSprite());
	}
	
	/******************************************************* MOVEMENT **************************************************/
	
	/**
	 * Check if the Plant moves alternating to the left and to the right.
	 */
	@Test
	public void testAlternateMovement(){
		world.start();
		assertTrue(world.hasStarted());
		
		world.advanceTime(0.2);
		
		assertEquals(Orientation.LEFT, plant.getOrientation());
		assertTrue(plant.isMoving());
		for (int i=0; i<2; i += 1){
			world.advanceTime(0.2);
		}
		// After 0.5s, the Plant should move to the other direction. ( Check after 0.6s )
		assertEquals(Orientation.RIGHT, plant.getOrientation());
		assertTrue(plant.isMoving());
	}
	
	/****************************************************** COLLISION **************************************************/
	
	/**
	 * Check if the Plant changes direction upon horizontal collision.
	 */
	@Test
	public void testChangeDirectionUponHorizontalCollision(){
		// Set a solid tile to the left of the plant
		world.setGeologicalFeature(2, 4, Terrain.SOLID);
		world.start();
		assertTrue(world.hasStarted());

		// Plant starts moving to the left
		world.advanceTime(0.2);
		// Plant collides with the solid tile and changes direction
		world.advanceTime(0.2);
		assertEquals(Orientation.RIGHT, plant.getOrientation());
	}
	
	/******************************************************* OVERLAP **************************************************/
	
	/**
	 * Check if the Plant gets killed when a Mazub overlaps with it.
	 */
	@Test
	public void testKilledUponMazubOverlap(){
		World world2 = new World(50, 20, 15, 400, 400, 4, 1);
		Mazub alien2 = new Mazub(170, 210, spriteArrayForSize(66, 92, 10));
		Plant plant2 = new Plant(160, 180, sprites);

		alien2.setWorldTo(world2);
		plant2.setWorldTo(world2);
		world2.start();
		assertTrue(world2.hasStarted());
		
		world2.advanceTime(0.2);
		
		// Mazub falls on top of the plant and overlaps with the top row pixels of the plant
		assertEquals(plant2.getRoundedPositionY()+plant2.getHeight()-1, alien2.getRoundedPositionY());
		assertTrue(plant2.isKilled());
	}
	
}
