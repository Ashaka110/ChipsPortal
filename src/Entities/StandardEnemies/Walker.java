package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Walker extends MovingEntity{

	public Walker(Position pos) {
		super(pos);
		//relations[Tile.Fire.index] = TileProperties.Blocking;

		_ImageUpIndex = 74	; 
		_ImageRightIndex = 73; 
		_ImageDownIndex = 74;
		_ImageLeftIndex = 73;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		Direction currentDirection = position.dir;//.counterClockwise();
		
		if(!move(currentDirection, level)){
			move(Direction.random(), level);
		}
		
		return false;
	}

}
