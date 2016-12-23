package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class Block extends MovingEntity{

	boolean doUpdate;
	
	public Block(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
		
		relations[Tile.Water.index] = TileProperties.Destructable_Deadly;
		relations[Tile.Fire.index] = TileProperties.Passable;
		relations[Tile.Gravel.index] = TileProperties.Passable;
		replacementTile = Tile.Dirt;
		
		relations[Tile.Laser.index] = TileProperties.Passable;

	}

	@Override
	public boolean makeMoveDecision(Level level) {
		return true;
	}

	public int getImageIndex(Level level, Direction dir)
	{
		return 4;
	}
	
	@Override
	public void logicUpdate(Level level) {
		// TODO Auto-generated method stub
		if(doUpdate){
			super.logicUpdate(level);
		}else{
			doUpdate = true;
		}
	}
	
	public boolean push(Direction dir, Level level){
		if(level.getTile(position) == Tile.Metal_Wall){
			return false;
		}
		this.logicUpdate(level);
		//doUpdate = false;
		freezeUntilNextFrame = true;
		return this.move(dir, level);
		
		
	}
	@Override
	public void onStartFrame(Level level) {
		doUpdate = true;
		super.onStartFrame(level);
	}
	
}
