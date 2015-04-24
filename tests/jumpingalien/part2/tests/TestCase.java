package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.helper.Orientation;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test suite for the class Mazub and helper classes.
 * 
 * @author Thomas Verelst, Hans Cauwenbergh
 * @version 1.0
 */
public class TestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/*************************************************** GIVEN TESTS *******************************************************/
	
	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;

	@Test
	public void testGetBottomLeftPixelOfRandomTile() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(5, 4, 3, 1, 1, 1, 1);
		assertArrayEquals(intArray(15, 10),
				facade.getBottomLeftPixelOfTile(world, 3, 2));
	}

	@Test
	public void testZeroAccellerationOnGround() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);

		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}

	/********************************************* TILES ******************************************************/
	
	/* getTilePositionsIn(int pixelLeft, int pixelBottom, int pixelRight, int pixelTop)  */
	
	@Test
	public void testTilesInRegion() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 105, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}

	// right edge
	@Test
	public void testTilesInRegion_rightEdge() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 100, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	@Test
	public void testTilesInRegion_rightEdge2() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 80, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 0, 1 },
				{ 1, 1 }, { 0, 2 }, { 1, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	// top edge
	@Test
	public void testTilesInRegion_topEdge() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 105, 100);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	@Test
	public void testTilesInRegion_topEdge2() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 105, 99);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	// bottom edge
	@Test
	public void testTilesInRegion_bottomEdge() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 49, 105, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	@Test
	public void testTilesInRegion_bottomEdge2() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 50, 105, 105);
		int[][] expectedTiles = { { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	// left edge
	@Test
	public void testTilesInRegion_leftEdge() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 49, 20, 105, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	@Test
	public void testTilesInRegion_leftEdge2() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 50, 20, 105, 105);
		int[][] expectedTiles = { { 1, 0 }, { 2, 0 },
				{ 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}	
	
	
	/************************************************ OVERLAPPOING ***************************************/
	
	/* public boolean getXOverlap(int x1, int width1, int x2, int width2) */
	
	 @Test
	 public void testXoverlap(){
		 assertTrue(GameObject.getXOverlap(20, 20, 25, 20));
		 assertTrue(GameObject.getXOverlap(20, 20, 15, 20));
		 assertTrue(GameObject.getXOverlap(20, 20, 25, 10));
		 assertTrue(GameObject.getXOverlap(20, 10, 15, 20));
	 }
	 
	 @Test
	 public void testXoverlap_edges(){
		 assertFalse(GameObject.getXOverlap(20, 20, 40, 20));
		 assertFalse(GameObject.getXOverlap(20, 20, 0, 20));
		 
		 assertTrue(GameObject.getXOverlap(20, 20, 39, 20));
		 assertTrue(GameObject.getXOverlap(20, 20, 1, 20));
		 assertTrue(GameObject.getXOverlap(20, 20, 20, 20));
//		 assertTrue(GameObject.getXOverlap(20, 20, 25, 10));
//		 assertTrue(GameObject.getXOverlap(20, 10, 15, 20));
	 }
	 
	 
	 /************************************************ REGION OVERLAP *******************************************/
	 /* Orientation.ALL */
	 
	 @Test
	 public void testRegionOverlap(){
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.ALL));
		 
		 // region 2 inside region 1
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.ALL));
		 
		 // region 2 outside region 1
		 assertTrue(GameObject.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.ALL));
	 }
	 
	 @Test
	 public void testRegionOverlap2(){
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 20, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 19, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 20, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 19, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 20, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 19, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 20, 20, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 19, 19, 20, 20, Orientation.ALL));
		 
		 // Same tests as above but reversed region 1 and 2
		 assertFalse(GameObject.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(20, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(19, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(GameObject.doRegionsOverlap(20, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(GameObject.doRegionsOverlap(19, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
	 }
	 
	 /* Orientation.BOTTOM */
	 @Test
	 public void testRegionOverlap_bottom1(){
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.BOTTOM));
		 assertTrue(GameObject.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.BOTTOM));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.BOTTOM));
		 
		 // region 2 inside region 1
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.BOTTOM));
		 
		 // region 2 outside region 1
		 assertTrue(GameObject.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.BOTTOM));
	 }
	 
	 @Test
	 public void testRegionOverlap_bottom2(){
		 assertFalse(GameObject.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertTrue(GameObject.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
	 }
	
	 /* Orientation.RIGHT */
	 @Test
	 public void testRegionOverlap_right1(){
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.RIGHT));
		 assertFalse(GameObject.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.RIGHT));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.RIGHT));
		 
		 
		 // region 2 inside region 1
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.RIGHT));
		 
		 // region 2 outside region 1
		 assertTrue(GameObject.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.RIGHT));
	 }
	 
	 @Test
	 public void testRegionOverlap_right2(){
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 20, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 19, 0, 20, 20, Orientation.RIGHT));
	 }
	
	 /* Orientation.LEFT */
	 @Test
	 public void testRegionOverlap_left1(){
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.LEFT));
		 assertTrue(GameObject.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertTrue(GameObject.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.LEFT));
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.LEFT));
		 
		 
		 // region 2 inside region 1
		 assertFalse(GameObject.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.LEFT));
		 
		 // region 2 outside region 1
		 assertTrue(GameObject.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.LEFT));
	 }
	 
	 @Test
	 public void testRegionOverlap_left2(){
		 assertFalse(GameObject.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertTrue(GameObject.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
	 }
	
	
	 
	
	
	
	
	
	
	
	
	
	@Test
	public void startMoveRightCorrect() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		assertArrayEquals(intArray(10, 499), facade.getLocation(alien));
	}

	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(world, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testWalkAnimationLastFrame() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 1, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(3, 3, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 499, sprites);
		facade.setMazub(world, alien);

		facade.startMoveRight(alien);

		facade.advanceTime(world, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(world, 0.075);
		}

		assertEquals(sprites[8 + m], facade.getCurrentSprite(alien));
	}
	
}

