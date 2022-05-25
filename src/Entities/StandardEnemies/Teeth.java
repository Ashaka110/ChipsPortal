package Entities.StandardEnemies;

import Entities.GameEntitiy;
import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Teeth extends MovingEntity {

	public Teeth(Position pos) {
		super(pos);
		//relations[Tile.Fire.index] = TileProperties.Blocking;

		_ImageUpIndex = 119; 
		_ImageRightIndex = 120; 
		_ImageDownIndex = 121;
		_ImageLeftIndex = 122;
	}
	
	boolean isOddMove;
	
	@Override
	public boolean makeMoveDecision(Level level) {
		if(isOddMove)
		{
			isOddMove = false;
			GameEntitiy player = level.getNearestPlayer(position);
			if(player != null)
				MoveTowards(player.position, level);
		}else{
			isOddMove = true;
		}
		return false;
	}
	
	public boolean MoveTowards(Position p, Level level){
		Position t = position;
		Position d = new Position(t.x-p.x, t.y-p.y);
		
		if(Math.abs(d.x) > Math.abs(d.y)){
			if(d.x > 0){
				if(!move(Direction.West, level)){
					if(d.y > 0){
						move(Direction.North, level);
					}else if(d.y != 0){
						move(Direction.South, level);
					}
				}
			}else{
				if(!move(Direction.East, level)){
					if(d.y > 0){
						move(Direction.North, level);
					}else if(d.y != 0){
						move(Direction.South, level);
					}
				}
			}
			
		}else{
			if(d.y > 0){
				if(!move(Direction.North, level)){
					if(d.x > 0){
						move(Direction.West, level);
					}else if(d.x != 0){
						move(Direction.East, level);
					}
				}
			}else{
				if(!move(Direction.South, level)){
					if(d.x > 0){
						move(Direction.West, level);
					}else if(d.x != 0){
						move(Direction.East, level);
					}
				}
			}
		}
		
		
		return false;
	}
}
