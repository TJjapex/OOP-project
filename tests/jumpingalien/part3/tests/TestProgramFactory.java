package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.Optional;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.World;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.*;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

public class TestProgramFactory {

	@Test
	public void testAddition() {
		
		String testSequence = 
		"double a; "
		+ "a := 5;"
		+ "print a;"
		+ "a := 6; "
		+ "print a;";

		String testIf = 
		"double a; "
		+ "if (5 == 5) then "
		+ "a := (5);"
		+ "print a;"
		+ "a := 6; "
		+ "break;"
		+ "print a;"
		+ "fi "
		+ "if (6 == 5) then "
		+ "a := 7;"
		+ "print 7;"
		+ "fi ";
		
		String testWhile = 
		"double a; "
		+ "while true do "
		+ "a := 5;"
		+ "print a;"
		+ "break;"
		+ "a := 6; "
		+ "print a;"
		+ "done ";
		
		String testWhileExt = 
		  "double a; "
		+ "double b; "
		+ "while true do "
		+ "if (5 == 5) then "
		+ "a := 5;"
		+ "a := 6; "
		+ "while (a != 8) do "
		+ "b := 4;"
		+ "break;"
		+ "a := a + 1; "
		+ "done "
		+ "print b;"
		+ "fi "
		+ "done ";
		
		String testWait =
		"double a; "
		+ "a := 5;"
		+" wait 0.003;"
		+ "print a;"
		+ "a := 6;"
		+ "skip;"
		+ "a := 7;";
		
		String testForEach = 
		"double x; "
		+ "double a; "
		+ " a := 4; "
		+ "foreach(plant, x) do "
		+ " print a; "
		+ " print a; "
		+ " print gethp x; "
		+ " done "
		+ "a := 5;";
			
		String testForEachWhere = 
		"double x; "
		+ "double a; "
		+ " a := 4; "
		+ "foreach(plant, x) "
		+ "where (gety x == 180)"
		+ " do "
		+ " print a; "
		+ " print a; "
		+ " print gethp x; "
		+ " done "
		+ "a := 5;";
		
		String testForEachSort = 
				"double x; "
				+ "double a; "
				+ " a := 4; "
				+ "foreach(plant, x) "
				+ "sort getx x descending"
				+ " do "
				+ " print getx x; "
				+ " print a; "
				+ " print gethp x; "
				+ " done "
				+ "a := 5;";
		
		String testSearchObject = 
				"double x; "
				+ "x := searchobj right;"
				+" print getx x;";
		
		// Selecteer programma
		String testProgram = testForEachWhere;
		
		//IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
		
		Facade facade = new Facade();

		facade = new Facade();
		Sprite[] sprites = spriteArrayForSize(3, 3);
		Sprite[] plantSprites = spriteArrayForSize(3, 3, 2);
		
		Mazub alien = facade.createMazub(250, 50, sprites);
		World world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, Terrain.SOLID.getId()); // create solid terrain
		}
		
		facade.setMazub(world, alien);
		
		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(180, 50, plantSprites, program);
		System.out.println(plant + " " + plant.getPositionX());
		facade.addPlant(world, plant);
		Plant plant2 = facade.createPlant(200, 50, plantSprites);
		System.out.println(plant2 + " " + plant2.getPositionX());
		facade.addPlant(world, plant2);
		
		for (int i=0; i<7; i++){
			facade.advanceTime(world, 0.2);
		}
	}
}
