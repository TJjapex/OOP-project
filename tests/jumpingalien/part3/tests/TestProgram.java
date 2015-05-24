/**
 * Test file containing tests of general methods for programs
 */


package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.model.helper.Orientation;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.program.Program;
import jumpingalien.program.ProgramFactory;
import jumpingalien.program.expressions.BinaryOperator;
import jumpingalien.program.expressions.Constant;
import jumpingalien.program.expressions.Expression;
import jumpingalien.program.statements.Statement;
import jumpingalien.program.statements.Wait;
import jumpingalien.program.types.DoubleType;
import jumpingalien.program.types.Type;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestProgram {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/* Program setup */

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
	
	/* Well formed */
	
	@Test
	public void testWellFormed() {
		testProgram =
		"double a; double b;"
		+ "a := 5; "
		+ "while ( (a) < (5+ 3)) do "
		+ "foreach(mazub, x) where ( (getx x) > 30) do "
		+ " a:= (-1 + gethp x); "
		+ " b:= a + getx x;"
		+ " done "
		+ "stop_run right;"
		+ "done" ;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertTrue(program.isWellFormed());
	}
	
	@Test
	public void testWellFormed2() throws IOException {
		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parseResult = parser.parseFile("resources\\programs\\program_example_1.txt");
		if (parseResult.isPresent()){	
		} else {
			throw new IllegalStateException();
		}
		ParseOutcome<?> parseOutcome = ParseOutcome.success(parseResult.get());
		Program program = (Program) parseOutcome.getResult();
		assertTrue(program.isWellFormed());
	
	}
	
	
// Program_example_2.txt and buzam.txt throws errors, probably because it is created on a different OS, which uses another return character sequence (\r)
//
//	@Test
//	public void testWellFormed3() throws IOException {
//		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
//		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
//		Optional<Program> parseResult = parser.parseFile("resources\\programs\\program_example_2.txt");
//		if (parseResult.isPresent()){	
//		} else {
//			throw new IllegalStateException();
//		}
//		ParseOutcome<?> parseOutcome = ParseOutcome.success(parseResult.get());
//		Program program = (Program) parseOutcome.getResult();
//		assertTrue(program.isWellFormed());
//	}
	
//	@Test
//	public void testWellFormed4() throws IOException {
//		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
//		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
//		Optional<Program> parseResult = parser.parseFile("resources\\programs\\buzam.txt");
//		if (parseResult.isPresent()){	
//		} else {
//			throw new IllegalStateException();
//		}
//	}
//	
//	@Test
//	public void testWellFormed5() throws IOException {
//		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
//		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
//		Optional<Program> parseResult = parser.parseFile("resources\\programs\\shark.txt");
//		if (parseResult.isPresent()){	
//		} else {
//			throw new IllegalStateException();
//		}
//		ParseOutcome<?> parseOutcome = ParseOutcome.success(parseResult.get());
//		Program program = (Program) parseOutcome.getResult();
//		assertTrue(program.isWellFormed());
//	}
	

	
		@Test
	public void testWellFormedFalse_ActionInForeach() {
		testProgram =
		" double b;"
		+ "foreach(mazub, x) do "
		+ " start_run left;"
		+ "done" ;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertFalse(program.isWellFormed());
	}
	
	
	@Test
	public void testWellFormedTrue_BreakInsideLoop() {
		testProgram =
		"double a; double b;"
		+ "a := 5; "
		+ "while ( (a) < (5+ 3)) do "
		+ " start_run left;"
		+ " a := a + 2; "
		+ " break;"
		+ "done"
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertTrue(program.isWellFormed());
	}
	
	@Test
	public void testWellFormedFalse_BreakOutWhile() {
		testProgram =
		"double a; double b;"
		+ "a := 5; "
		+ "while ( (a) < (5+ 3)) do "
		+ " start_run left;"
		+ " a := a + 2; "
		+ "done"
		+ " break;"
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertFalse(program.isWellFormed());
	}
	
	@Test
	public void testWellFormedTrue_Foreach() {
		testProgram =
		"double x;"
		+ "foreach(plant, x) do "
		+ "print gethp x;"
		+ "break;"
		+ "done "
		
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertTrue(program.isWellFormed());
	}
	
	@Test
	public void testWellFormedFalse_BreakOutForeach() {
		testProgram =
		"double x;"
		+ "foreach(plant, x) do "
		+ "print gethp x;"
		+ "done "
		+ "break;"
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertFalse(program.isWellFormed());
	}
	
	@Test
	public void testWellFormedFalse_BreakInIf() {
		testProgram =
		"double a; a:=5;"
		+ "if (a == 5) then "
		+ "print a;"
		+ "fi "
		+ "break;"
		;

		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		assertFalse(program.isWellFormed());
	}
	
	
	
	/* Tests */	
	@Test 
	public void testTimeAdvancement(){
		String test = 
				" double a; a:= 0;"
				+ "while a < 3 do "
				+ " a := a + 1 ;"
				+ " done "
				+ " a := 85;";
		
		ParseOutcome<?> parseOutcome = facade.parse(test);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		facade.addPlant(world, plant2);

		world.advanceTime(0.0005); // Initial a assignment
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0013); // Evaluate while
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0009); // Assignment of a
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.001); // Evaluate while
		assertEquals(1, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0015); // Assignment of a
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0007); // Evaluate while
		assertEquals(2, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0011); // Assignment of a
		assertEquals(3, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0002); // Evaluate while (false now)
		assertEquals(3, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0006); // Assignment of a := 85
		assertEquals(85, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		world.advanceTime(0.0018); // Restart program
		assertEquals(0, ((DoubleType) program.getVariable("a")).getValue(), Util.DEFAULT_EPSILON);
		
	}	
	
	
	@Test
	public void testSequenceAndRun() {
		testProgram =
		""
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
	public void testIfAndRun() {
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
		facade.advanceTime(world, 0.0005); // Evaluate if  (time advancement smaller than 0.0001!)
		assertFalse(plant.isMoving());
		facade.advanceTime(world, 0.001); // Start_run right
		assertTrue(plant.isMoving());
		assertEquals(Orientation.RIGHT, plant.getOrientation());
		facade.advanceTime(world, 0.001); // Stop_run right
		assertFalse(plant.isMoving());
	}
	
	@Test
	public void testElseAndRun() {
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
	public void testWhileAndRun() {
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
