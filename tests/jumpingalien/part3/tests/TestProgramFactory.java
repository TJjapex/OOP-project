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
		
		// TODO hier wat debug programmas bijhouden :)
		
		// Werkt, test if en meerdere statements in if, er mag maar één statement per executeNext gedaan worden -> ok nu
		String testIf = 
		"double a; "
		+ "if (5 == 5) then "
		+ "a := 5;"
		+ "print a;"
		+ "a := 6; "
		+ "print a;"
		+ "fi ";
		
		// Werkt nog niet correct -> conditie in aparte executeNext + blijft in ergens op een vreemde manier in z'n body hangen + conditie wordt iedere executeNext gecheckt (denk ik, nog niet goed bekeken)
		String testWhile = 
		"double a; "
		+ "while true do "
		+ "a := 5;"
		+ "print a;"
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
		+ "a := a + 1; "
		+ "done "
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
							
		// Selecteer programma
		String testProgram = testWait;
		
		
		
		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
		
		Facade facade = new Facade();

		facade = new Facade();
		Sprite[] sprites = spriteArrayForSize(3, 3);
		Sprite[] plantSprites = spriteArrayForSize(3, 3, 2);
		
		Mazub alien = facade.createMazub(100, 50, sprites);
		World world = facade.createWorld(50, 60, 15, 200, 150, 4, 1);
		
		for(int i = 0; i < 60; i++){
			facade.setGeologicalFeature(world, i, 0, 0); // create solid terrain
		}
		
		facade.setMazub(world, alien);
		
		ParseOutcome<?> parseOutcome = facade.parse(testProgram);
		
		if(!parseOutcome.isSuccess()){
			throw new IllegalArgumentException("Program parsing failed");
		}
		
		Program program = (Program) parseOutcome.getResult();
		Plant plant = facade.createPlantWithProgram(80, 80, plantSprites, program);
		facade.addPlant(world, plant);
		
		for (int i=0; i<3; i++){
			facade.advanceTime(world, 0.2);
		}
		
	}
}
