package jumpingalien.part3.facade;

import java.util.Collection;

import org.antlr.runtime.ANTLRStringStream;

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
import jumpingalien.model.helper.Orientation;
import jumpingalien.model.program.Expression;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.Statement;
import jumpingalien.model.program.Type;
import jumpingalien.model.terrain.Terrain;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;

public class Facade  extends jumpingalien.part2.facade.Facade implements IFacadePart3 {	
	
	@Override
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
		
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
		return this.createBuzam(pixelLeftX, pixelBottomY, sprites);
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		ProgramFactory factory = new ProgramFactory();
		ProgramParser<Expression, Statement, Type<?>, Program> programParser = new ProgramParser<Expression, Statement, Type<?>, Program>(factory);
		
		// TODO: ik zie niet in hoe er hier ParseOutcome kan uitkomen
		return programParser.parseString(text);
	}

	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
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