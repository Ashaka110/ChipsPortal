package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Paramicium extends MovingEntity{

	public Paramicium(Position pos) {
		super(pos);
		//relations[Tile.Fire.index] = TileProperties.Blocking;

		_ImageUpIndex = 124; 
		_ImageRightIndex = 125; 
		_ImageDownIndex = 124;
		_ImageLeftIndex = 125;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		Direction currentDirection = position.dir.clockwise();
		
		for (int i = 0; i < 4 && !this.move(currentDirection, level)  ; i++) {
			currentDirection = currentDirection.counterClockwise();
		}
		
		return false;
	}

}
