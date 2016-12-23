package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Blob extends MovingEntity{

	public Blob(Position pos) {
		super(pos);
		//relations[Tile.Fire.index] = TileProperties.Blocking;

		_ImageUpIndex = 123; 
		_ImageRightIndex = 123; 
		_ImageDownIndex = 123;
		_ImageLeftIndex = 123;
	}
	
	boolean isOddMove;
	
	@Override
	public boolean makeMoveDecision(Level level) {
		if(isOddMove)
		{
			isOddMove = false;
			move(Direction.random(), level);
		}else{
			isOddMove = true;
		}
		return false;
	}

}
