package jumpingalien.part2.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import jumpingalien.model.GameObject;
import jumpingalien.model.helper.Collision;
import jumpingalien.model.helper.Orientation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollisionTest {
	
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
	
	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	/***************************************************** OVERLAPPING *************************************************/
	
	/* public boolean GameObject.doPixelsOverlap(int x1, int width1, int x2, int width2) */
	
	 @Test
	 public void testDoPixelOverlap(){
		 assertTrue(Collision.doPixelsOverlap(20, 20, 25, 20));
		 assertTrue(Collision.doPixelsOverlap(20, 20, 15, 20));
		 assertTrue(Collision.doPixelsOverlap(20, 20, 25, 10));
		 assertTrue(Collision.doPixelsOverlap(20, 10, 15, 20));
	 }
	 
	 @Test
	 public void testDoPixelOverlap_edges(){
		 assertFalse(Collision.doPixelsOverlap(20, 20, 40, 20));
		 assertFalse(Collision.doPixelsOverlap(20, 20, 0, 20));
		 
		 assertTrue(Collision.doPixelsOverlap(20, 20, 39, 20));
		 assertTrue(Collision.doPixelsOverlap(20, 20, 1, 20));
		 assertTrue(Collision.doPixelsOverlap(20, 20, 20, 20));
	 }
	 
	 /*************************************************** REGION OVERLAP ************************************************/
	 
	 /* Orientation.ALL */
	 
	 @Test
	 public void testRegionOverlap(){
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.ALL));
		 
		 // region 2 inside region 1
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.ALL));
		 
		 // region 2 outside region 1
		 assertTrue(Collision.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.ALL));
	 }
	 
	 @Test
	 public void testRegionOverlap2(){
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 20, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 19, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 20, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 19, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 20, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 19, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 20, 20, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 19, 19, 20, 20, Orientation.ALL));
		 
		 // Same tests as above but reversed region 1 and 2
		 assertFalse(Collision.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(20, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(19, 0, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 
		 assertFalse(Collision.doRegionsOverlap(20, 20, 20, 20, 0, 0, 20, 20, Orientation.ALL));
		 assertTrue(Collision.doRegionsOverlap(19, 19, 20, 20, 0, 0, 20, 20, Orientation.ALL));
	 }
	 
	 /* Orientation.BOTTOM */
	 @Test
	 public void testRegionOverlap_bottom1(){
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.BOTTOM));
		 assertTrue(Collision.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.BOTTOM));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.BOTTOM));
		 
		 // region 2 inside region 1
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.BOTTOM));
		 
		 // region 2 outside region 1
		 assertTrue(Collision.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.BOTTOM));
	 }
	 
	 @Test
	 public void testRegionOverlap_bottom2(){
		 assertFalse(Collision.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
		 assertTrue(Collision.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.BOTTOM));
	 }
	
	 /* Orientation.RIGHT */
	 @Test
	 public void testRegionOverlap_right1(){
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.RIGHT));
		 assertFalse(Collision.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.RIGHT));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.RIGHT));
		 
		 
		 // region 2 inside region 1
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.RIGHT));
		 
		 // region 2 outside region 1
		 assertTrue(Collision.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.RIGHT));
	 }
	 
	 @Test
	 public void testRegionOverlap_right2(){
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 20, 0, 20, 20, Orientation.RIGHT));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 19, 0, 20, 20, Orientation.RIGHT));
	 }
		
	 /* Orientation.LEFT */
	 @Test
	 public void testRegionOverlap_left1(){
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.LEFT));
		 assertTrue(Collision.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.LEFT));
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.LEFT));
		 
		 
		 // region 2 inside region 1
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.LEFT));
		 
		 // region 2 outside region 1
		 assertTrue(Collision.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.LEFT));
	 }
	 
	 @Test
	 public void testRegionOverlap_left2(){
		 assertFalse(Collision.doRegionsOverlap(0, 20, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
		 assertTrue(Collision.doRegionsOverlap(0, 19, 20, 20, 0, 0, 20, 20, Orientation.LEFT));
	 }
		
	 /* Orientation.TOP */
	 @Test
	 public void testRegionOverlap_top1(){
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 0, 20, 20, Orientation.TOP));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 10, 20, 20, Orientation.TOP));
		 assertFalse(Collision.doRegionsOverlap(10, 10, 20, 20, 0, 0, 20, 20, Orientation.TOP));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 10, 20, 20, Orientation.TOP));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 10, 0, 20, 20, Orientation.TOP));
		 
		 
		 // region 2 inside region 1
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 5, 5, 10, 10, Orientation.TOP));
		 
		 // region 2 outside region 1
		 assertTrue(Collision.doRegionsOverlap(5, 5, 10, 10, 0, 0, 20, 20, Orientation.TOP));
	 }
	 
	 @Test
	 public void testRegionOverlap_top2(){
		 assertFalse(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 20, 20, 20, Orientation.TOP));
		 assertTrue(Collision.doRegionsOverlap(0, 0, 20, 20, 0, 19, 20, 20, Orientation.TOP));
	 }

}
