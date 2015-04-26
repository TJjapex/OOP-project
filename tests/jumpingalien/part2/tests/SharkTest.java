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
		assertEquals(shark.getRoundedPositionX(), 10);
		assertEquals(shark.getRoundedPositionY(), 12);
		assertEquals(shark.getCurrentSprite(), sprites[0]);
	}
	
	/* General */
	@Test
	public void testOutOfWater(){
		assertFalse(shark.isSubmergedIn(Terrain.WATER));
		assertTrue(shark.isSubmergedIn(Terrain.AIR));
	}
	
	
	/* Test death */
	
	@Test
	public void deathOutOfGameWorld(){
		
		shark.advanceTime(0.2);
		shark.advanceTime(0.2);
		shark.advanceTime(0.2);
		shark.advanceTime(0.2);
		System.out.println(shark.getRoundedPositionY());
	}
	
	
//	@Test
	
//	public void testTerminteWhenTerminatedWithinTheBounds(){
//		assertTrue(shark.isAlive());
//		shark.addHP(-100);
//		//Terminates when HP goes below 0 HP
//		assertTrue(shark.getHP() == 0);
//		assertTrue(shark.isDying());
//		for (int i=0; i<5; i += 1){
//			shark.advanceTime(0.199);
//		}
//		assertTrue(shark.isDead());
//		assertTrue(shark.getWorld() == null);		
//	}
}
