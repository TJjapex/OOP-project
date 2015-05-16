package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.util.Sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtTestProgramFactory {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	Facade facade;
	IProgramFactory<Expression<?>, Statement, Type, Program> factory;
	Sprite[] sprites;
	Sprite[] plantSprites;
	Mazub alien;
	World world;
	String testProgram;
	
	@Before
	public void setUp() throws Exception {
		factory = new ProgramFactory<>();
		
		facade = new Facade();
		
		sprites = spriteArrayForSize(3, 3);
		plantSprites = spriteArrayForSize(3, 3, 2);
		
		alien = facade.createMazub(100, 50, sprites);
		world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, 0); // create solid terrain
		}
		
		facade.setMazub(world, alien);
		
		testProgram = "";
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSequenceRun() {
		testProgram =
		"double a; "
		+ "start_run right;"
		+ "wait 0.002;"
		+ "stop_run right;";

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001);
		assertTrue(plant.isMoving());
		assertEquals(Orientation.RIGHT, plant.getOrientation());
		facade.advanceTime(world, 0.001);
		assertTrue(plant.isMoving());
		facade.advanceTime(world, 0.001);
		assertTrue(plant.isMoving());
		facade.advanceTime(world, 0.001);
		assertFalse(plant.isMoving());
	}
	
	@Test
	public void testIf() {
		testProgram =
		"double a; "
		+ "a := 5; "
		+ "if a==5 then "
		+ "start_run right;"
		+" else"
		+" start_run left;"
		+" fi "
		+" stop_run right;";

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Assign var
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Evaluate if
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Start_run right
		assertTrue(plant.isMoving());
		assertEquals(Orientation.RIGHT, plant.getOrientation());
		facade.advanceTime(world, 0.001); // Stop_run right
		assertFalse(plant.isMoving());
	}
	
	@Test
	public void testElse() {
		testProgram =
		"double a; "
		+ "a := 5; "
		+ "if a==6 then "
		+ "start_run right;"
		+" else "
		+" start_run left;"
		+" fi "
		+" stop_run right;";

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Assign var
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Evaluate if
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Start_run left
		assertTrue(plant.isMoving());
		assertEquals(Orientation.LEFT, plant.getOrientation());
		facade.advanceTime(world, 0.001); // Stop_run left
		assertFalse(plant.isMoving());		
	}

	@Test
	public void testWhile() {
		testProgram =
		" double a; "
		+ "a := 0; "
		+ "while a < 4 do "
		+ "a := a + 1 ;"
		+ "done "
		+ "if a == 5 then "
		+ "start_run right; "
		+ "fi "
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Assign var
		assertFalse(plant.isMoving());
		
		for(int i = 0; i < 4; i++){
			facade.advanceTime(world, 0.001); // Evaluate while
			assertFalse(plant.isMoving());
			facade.advanceTime(world, 0.001); // Assign var
			assertFalse(plant.isMoving());
		}
		
		facade.advanceTime(world, 0.001); // Test if
		assertFalse(plant.isMoving());
		
		facade.advanceTime(world, 0.001); // Start_run right
		assertFalse(plant.isMoving());
		assertEquals(Orientation.RIGHT, plant.getOrientation());	
	}

}
