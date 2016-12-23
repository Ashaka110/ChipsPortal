package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Fireball extends MovingEntity{

		public Fireball(Position pos) {
			super(pos);
			relations[Tile.Fire.index] = TileProperties.Passable;

			_ImageUpIndex = 109; 
			_ImageRightIndex = 109; 
			_ImageDownIndex = 109;
			_ImageLeftIndex = 109;
		}

		@Override
		public boolean makeMoveDecision(Level level) {
			Direction currentDirection = position.dir;
			
			if(!move(currentDirection, level)){
				if(!move(currentDirection.clockwise(), level)){
					if(!move(currentDirection.counterClockwise(), level)){
						if(!move(currentDirection.reverse(), level)){
							
						}	
					}	
				}
			}
			
			
			return false;
		}
}
