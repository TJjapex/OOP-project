package jumpingalien.part3.facade;

import java.util.Optional;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.exceptions.IllegalHeightException;
import jumpingalien.model.exceptions.IllegalPositionXException;
import jumpingalien.model.exceptions.IllegalPositionYException;
import jumpingalien.model.exceptions.IllegalWidthException;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.program.Program;
import jumpingalien.program.ProgramFactory;
import jumpingalien.program.expressions.Expression;
import jumpingalien.program.statements.*;
import jumpingalien.program.types.*;

public class Facade  extends jumpingalien.part2.facade.Facade implements IFacadePart3 {	
	
	@Override
	public void advanceTime(Mazub alien, double dt) {
		try{
			alien.getWorld().advanceTime(dt);	
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument exception: " + exc.getMessage());
		}
	}

	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{
			return new Buzam(pixelLeftX, pixelBottomY, sprites);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException( exc.getMessage() );
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		try{
			return new Buzam(pixelLeftX, pixelBottomY, sprites, program);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException( exc.getMessage() );
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		try{
			return new Plant(x, y, sprites, program);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}	
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		try{
			return new Shark(x, y, sprites, program);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		try{
			return new Slime(x,y,sprites,school, program);
		}catch( IllegalPositionXException | IllegalPositionYException exc){
			throw new ModelException("Invalid position given.");
		}catch( IllegalWidthException | IllegalHeightException exc){
			throw new ModelException("Invalid sprite size given.");
		}
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory<>();
		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parseResult = parser.parseString(text);
		if (parseResult.isPresent()){	
			return ParseOutcome.success(parseResult.get());
		} else {
			return ParseOutcome.failure(parser.getErrors());
		}
	}

	@Override
	public boolean isWellFormed(Program program) {
		return program.isWellFormed();
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		buzam.setWorldTo(world);
	}

	@Override
	public int[] getLocation(Buzam alien) {
		return new int[] {alien.getRoundedPositionX(), alien.getRoundedPositionY()};
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		return new double[] {alien.getVelocityX(), alien.getVelocityY()};	

	}

	@Override
	public double[] getAcceleration(Buzam alien) {
		return new double[] {alien.getAccelerationX(), alien.getAccelerationY()};
	}

	@Override
	public int[] getSize(Buzam alien) {
		return new int[] {alien.getWidth(), alien.getHeight()};	
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		return alien.getCurrentSprite();

	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		return alien.getNbHitPoints();
	}
	
}