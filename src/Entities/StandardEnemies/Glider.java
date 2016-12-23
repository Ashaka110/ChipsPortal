package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Glider extends MovingEntity{

	public Glider(Position pos) {
		super(pos);
		//relations[Tile.Fire.index] = TileProperties.Blocking;
		relations[Tile.Water.index] = TileProperties.Passable;

		_ImageUpIndex = 115; 
		_ImageRightIndex = 116; 
		_ImageDownIndex = 117;
		_ImageLeftIndex = 118;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		Direction currentDirection = position.dir;
		
		if(!move(currentDirection, level)){
			if(!move(currentDirection.counterClockwise(), level)){
				if(!move(currentDirection.clockwise(), level)){
					if(!move(currentDirection.reverse(), level)){
						
					}	
				}	
			}
		}
		
		
		return false;
	}
}
