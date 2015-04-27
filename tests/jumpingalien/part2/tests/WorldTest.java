package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {
		
	private Sprite[] sprites;
	private Mazub alien;
	private World world;
	private IFacadePart2 facade;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Initiliaze some often used variables. These will be reset each test.
		
		facade = new Facade();
		
		sprites = spriteArrayForSize(3, 3);
		
		alien = facade.createMazub(100, 50, sprites);
		world = facade.createWorld(50, 20, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 20; i++){
			facade.setGeologicalFeature(world, i, 0, FEATURE_SOLID);
		}
		
		facade.setMazub(world, alien);
	}


	@After
	public void tearDown() throws Exception {
	}
	
	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	/***************************************************** CONSTRUCTOR *************************************************/
	
	@Test
	public void testConstructor(){
		assertEquals(50, world.getTileLength());
		assertEquals(20, world.getNbTilesX());
		assertEquals(15, world.getNbTilesY());
		assertEquals(200, world.getDisplayWidth());
		assertEquals(150, world.getDisplayHeight());
		assertEquals(4, world.getTargetTileX());
		assertEquals(1, world.getTargetTileY());
		
		assertEquals(50, facade.getTileLength(world));
		assertArrayEquals(new int[] {1000, 750}, facade.getWorldSizeInPixels(world));
	}
	
	/******************************************************* GENERAL ***************************************************/
	
	@Test
	public void testWorldDimensions(){
		assertEquals(50, world.getTileLength());
		assertEquals(50*20, world.getWorldWidth());
		assertEquals(50*15, world.getWorldHeight());
	}
	
	/******************************************************** TILES ****************************************************/
	
	@Test
	public void positionToTile(){
		assertEquals(2, world.getTileX(100));
		assertEquals(1, world.getTileX(99));
		assertEquals(0, world.getTileX(0));
		assertEquals(2, world.getTileX(120));
		assertEquals(3, world.getTileX(160));
		
		assertEquals(2, world.getTileY(100));
		assertEquals(1, world.getTileY(99));
		assertEquals(0, world.getTileY(0));
		assertEquals(2, world.getTileY(120));
		assertEquals(3, world.getTileY(160));
	}
	
	@Test
	public void tileToPosition(){
		assertEquals(100, world.getPositionXOfTile(2));
		assertEquals(50, world.getPositionXOfTile(1));
		assertEquals(0, world.getPositionXOfTile(0));
		
		assertEquals(100, world.getPositionYOfTile(2));
		assertEquals(50, world.getPositionYOfTile(1));
		assertEquals(0, world.getPositionYOfTile(0));
		
		assertArrayEquals(new int[] {100, 50}, facade.getBottomLeftPixelOfTile(world, 2, 1));
		assertArrayEquals(new int[] {50, 150}, facade.getBottomLeftPixelOfTile(world, 1, 3));

	}
	
	// Predefined
	@Test
	public void testGetBottomLeftPixelOfRandomTile() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(5, 4, 3, 1, 1, 1, 1);
		assertArrayEquals(intArray(15, 10),
				facade.getBottomLeftPixelOfTile(world, 3, 2));
	}
	
	/*************************************************** TILES IN REGION ***********************************************/
	
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
	
	/************************************************** GEOLOGICAL FEATURE *********************************************/
	
	@Test
	public void testGeologicalFeature_notSet(){
		assertEquals(Terrain.AIR, world.getGeologicalFeature(world.getPositionXOfTile(17),
				  world.getPositionYOfTile(14)));
		assertEquals(FEATURE_AIR, facade.getGeologicalFeature(world, world.getPositionXOfTile(17),
				  world.getPositionYOfTile(14)));
	}
	
	@Test
	public void testGeologicalFeature(){
		world.setGeologicalFeature(1, 2, Terrain.SOLID);
		assertEquals(Terrain.SOLID, world.getGeologicalFeature(world.getPositionXOfTile(1),
								    world.getPositionYOfTile(2)));
		assertEquals(FEATURE_SOLID, facade.getGeologicalFeature(world, world.getPositionXOfTile(1),
				  world.getPositionYOfTile(2)));
	}
	
	@Test(expected=IllegalPositionXException.class)
	public void testGeologicalFeature_illegalPositionX(){
		world.setGeologicalFeature(-1, 2, Terrain.SOLID);
	}
	
	/******************************************************* VISIBLE WINDOW ******************************************/
	
	@Test
	public void visibleWindow_leftBottom(){
		assertArrayEquals(new int[]{0, 0, 200,150}, facade.getVisibleWindow(world));
	}
	
	@Test
	public void visibleWindow_middleBottom(){
		facade.startGame(world);
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		assertArrayEquals(new int[]{8, 0, 208, 150}, facade.getVisibleWindow(world));
	}
}
