package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.program.Program;
import jumpingalien.program.ProgramFactory;
import jumpingalien.program.expressions.Constant;
import jumpingalien.program.expressions.Expression;
import jumpingalien.program.expressions.SearchObject;
import jumpingalien.program.statements.Statement;
import jumpingalien.program.statements.Wait;
import jumpingalien.program.types.DirectionType;
import jumpingalien.program.types.DoubleType;
import jumpingalien.program.types.ObjectType;
import jumpingalien.program.types.Type;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSearchObject {
	
	/* Setup */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	ProgramFactory<Expression<Type>, Statement, Type, Program> programFactory;
	Program program;
	Facade facade;
	Sprite[] sprites;
	Sprite[] plantSprites;
	Mazub alien;
	World world;
	String test;
	
	@Before
	public void setUp() throws Exception {
		programFactory = new ProgramFactory<>();
		facade = new Facade();

		sprites = spriteArrayForSize(5, 4);
		plantSprites = spriteArrayForSize(3, 2, 2);
		
		alien = facade.createMazub(250, 50, sprites);
		world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		facade.setMazub(world, alien);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, Terrain.SOLID.getId()); // create solid terrain
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	/* Tests */
	
	@Test
	public void testGetDistance(){
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		Plant plant = facade.createPlantWithProgram(80, 50, plantSprites, program);
		facade.addPlant(world, plant);
		
		Constant<DirectionType> direction = new Constant<>(new DirectionType(Direction.RIGHT), null);
		SearchObject expr = new SearchObject(direction, null);
		
		// Expected: mazub is at 250, plant at 80 so 250 - 80 = 170
		assertEquals(170, expr.getDistance(alien, program));
	}
	
	@Test
	public void testInRightDirection(){
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		Plant plant = facade.createPlantWithProgram(80, 50, plantSprites, program);
		facade.addPlant(world, plant);
		
		Constant<DirectionType> direction = new Constant<>(new DirectionType(Direction.RIGHT), null);
		SearchObject expr = new SearchObject(direction, null);
	
		assertTrue(expr.objectInRightDirection(alien, program));
	}
	
	@Test
	public void testInRightDirectionFalse(){
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		Plant plant = facade.createPlantWithProgram(300, 50, plantSprites, program);
		facade.addPlant(world, plant);
		
		Constant<DirectionType> direction = new Constant<>(new DirectionType(Direction.RIGHT), null);
		SearchObject expr = new SearchObject(direction, null);
	
		assertFalse(expr.objectInRightDirection(alien, program));
	}
	
	@Test
	public void testInLineOfSight(){
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		Plant plant = facade.createPlantWithProgram(80, 50, plantSprites, program);
		facade.addPlant(world, plant);
		
		Constant<DirectionType> direction = new Constant<>(new DirectionType(Direction.RIGHT), null);
		SearchObject expr = new SearchObject(direction, null);
	
		assertTrue(expr.objectInRightDirection(alien, program));
	}
	
	@Test
	public void testInLineOfSightFalse(){
		program = programFactory.createProgram(new Wait(new Constant<DoubleType>(new DoubleType(1), null), null), new HashMap<String, Type>());
		Plant plant = facade.createPlantWithProgram(80, 150, plantSprites, program);
		facade.addPlant(world, plant);
		
		Constant<DirectionType> direction = new Constant<>(new DirectionType(Direction.RIGHT), null);
		SearchObject expr = new SearchObject(direction, null);

		assertFalse(expr.objectInLineOfSight(alien, program));
	}
	
	/* Searchobject result tests */
	
	@Test 
	public void testSearchObjRight(){
		String test = "object x;"
				+"x := searchobj right;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		world.advanceTime(0.001);
		assertEquals(alien, ((ObjectType) program.getVariable("x")).getValue());
	}
	
	@Test 
	public void testSearchObjRightClosest(){
		String test = "object x;"
				+"x := searchobj right;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);
		
		world.advanceTime(0.001);
		assertEquals(plant2, ((ObjectType) program.getVariable("x")).getValue());
	}
	
	@Test 
	public void testSearchObjAlienOutOfSight(){
		String test = "object x;"
				+"x := searchobj right;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 180, plantSprites);
		facade.addPlant(world, plant2);
		
		world.advanceTime(0.001);
		assertEquals(alien, ((ObjectType) program.getVariable("x")).getValue());
	}
	
}
