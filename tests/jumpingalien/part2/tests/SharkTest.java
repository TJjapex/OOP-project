package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Shark;
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

public class SharkTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	private Sprite[] sprites;
	private Shark shark;
	private World world;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sprites = spriteArrayForSize(3, 3);
		shark = new Shark(10, 12, sprites);
		world = new World(50, 20, 15, 200, 150, 4, 1);
		shark.setWorldTo(world);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/* Test constructor */
	
	@Test
	public void testConstructor(){
		assertEquals(10, shark.getRoundedPositionX());
		assertEquals(12, shark.getRoundedPositionY());
		assertEquals(sprites[0], shark.getCurrentSprite());
	}
	
	/* General */
	@Test
	public void testOutOfWater(){
		assertFalse(shark.isSubmergedIn(Terrain.WATER));
		assertTrue(shark.isSubmergedIn(Terrain.AIR));
	}
	
	
	/* Test death */
	
	
	/** Shark will fall until it is out of gameWorld, then it will be killed and after 0.6s it should be terminated */
	@Test
	public void deathOutOfGameWorld(){
		
		/* calculations -12 = -10/2 * t**2 -> t = 0.155 */
		shark.advanceTime(0.17);
		assertTrue(shark.getRoundedPositionY() == 0);
		assertTrue(shark.isKilled());
		assertFalse(shark.isTerminated());
		shark.advanceTime(0.2);
		shark.advanceTime(0.2);
		shark.advanceTime(0.2);
		assertTrue(shark.isTerminated());
	}
	
	/** Tests if terminating after 0.6s works */
	@Test
	public void testTerminate(){
		Shark deadShark = new Shark(12, 12, 0.0, 0.0, 4.0, 1.5, sprites, 0, 100);
		deadShark.setWorldTo(world);
		assertTrue(deadShark.isKilled());
		
		for (int i=0; i<3; i += 1){
			deadShark.advanceTime(0.2);
		}
		deadShark.advanceTime(0.1);
		assertTrue(deadShark.isTerminated());
		assertFalse(deadShark.hasWorld());		
	}
}
