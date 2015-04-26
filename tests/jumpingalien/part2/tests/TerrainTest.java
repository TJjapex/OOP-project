package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.model.terrain.TerrainProperties;
import jumpingalien.util.Util;

import org.junit.Test;


public class TerrainTest {
	
	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;
	
	@Test
	public void terrainEnum_idToTerrain(){
		assertEquals(Terrain.AIR, Terrain.idToType(FEATURE_AIR));
		assertEquals(Terrain.SOLID, Terrain.idToType(FEATURE_SOLID));
		assertEquals(Terrain.WATER, Terrain.idToType(FEATURE_WATER));
		assertEquals(Terrain.MAGMA, Terrain.idToType(FEATURE_MAGMA));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void terrainEnum_idToTerrain_invalidId() {
		Terrain.idToType(4);
	}	
	
	@Test
	public void terrainProperties_valid(){
		TerrainProperties terrainProperties = new TerrainProperties(true, 40, 0.3, false);
		
		assertEquals(terrainProperties.isPassable(), true);
		assertEquals(terrainProperties.getDamage(), 40);
		assertEquals(terrainProperties.getDamageTime(), 0.3, Util.DEFAULT_EPSILON);
		assertEquals(terrainProperties.isInstantDamage(), false);
	}
}
