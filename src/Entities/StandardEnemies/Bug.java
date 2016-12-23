package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Bug extends MovingEntity{

	public Bug(Position pos) {
		super(pos);
		relations[Tile.Fire.index] = TileProperties.Blocking;

		_ImageUpIndex = 105; 
		_ImageRightIndex = 106; 
		_ImageDownIndex = 107;
		_ImageLeftIndex = 108;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		Direction currentDirection = position.dir.counterClockwise();
		
		for (int i = 0; i < 4 && !this.move(currentDirection, level)  ; i++) {
			currentDirection = currentDirection.clockwise();
		}
		
		return false;
	}

}
