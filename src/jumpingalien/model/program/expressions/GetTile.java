package jumpingalien.model.program.expressions;

import jumpingalien.model.Tile;
import jumpingalien.model.World;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.types.ObjectType;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.types.*;

public class GetTile extends Expression<ObjectType> {

	public GetTile(SourceLocation sourceLocation, Expression<?> x, Expression<?> y) {
		super(sourceLocation);
		this.x = x;
		this.y = y;
	}

	private Expression<?> x;
	private Expression<?> y;
	
	// TODO is echt nog heel lelijk maar wou het gewoon ff werkend krijgen
	@Override
	public ObjectType execute(Program program) {
		World world = program.getGameObject().getWorld();
		int intX = (int) ((DoubleType) x.execute(program)).getValue();
		int intY = (int) ((DoubleType) y.execute(program)).getValue();
		return new ObjectType( new Tile(world.getTileLength(), world.getTileX(intX)*world.getTileLength(), world.getTileY(intY)*world.getTileLength(), world.getGeologicalFeature(world.getTileX(intX)*world.getTileLength(), world.getTileY(intY)*world.getTileLength())));
	}
	
}
