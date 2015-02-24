package jumpingalien.part1;

import jumpingalien.model.Facade;
import jumpingalien.part1.internal.JumpingAlienGUIPart1;
import jumpingalien.part1.internal.JumpingAlienGamePart1;
import jumpingalien.part1.internal.Part1Options;

public class JumpingAlienPart1 {

	public static void main(String[] args) {
		Part1Options options = Part1Options.parse(args);

		JumpingAlienGamePart1 game = new JumpingAlienGamePart1(options, new Facade());

		new JumpingAlienGUIPart1(game).start();
	}

}
