package Entities.StandardEnemies;

import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class IceBlock extends Block{

	public IceBlock(Position pos) {
		super(pos);
		relations[Tile.Fire.index] = TileProperties.Destructable_Deadly;
		
	}
	
	boolean isAlreadyinPushSequence;
	
	@Override
	public Boolean move(Direction dir, Level level) {
		
		if(isAlreadyinPushSequence){
			return false;
		}
		isAlreadyinPushSequence = true;
		
		Position target = level.getPostitionInDiretion(position, dir);
		Block b = level.getBlock(target);
		
		if(b != null){
			if(b instanceof IceBlock){
				if(!b.push(target.dir, level)){
					return false;
				}
			}
		}

	//	isAlreadyinPushSequence = false;
		
		return super.move(dir, level);
	}
	
	public boolean push(Direction dir, Level level){
		if(level.getTile(position) == Tile.Metal_Wall){
			return false;
		}
		
		
		this.logicUpdate(level);
		if(this.move(dir, level)){
			doUpdate = false;
			return true;
		}
		return false;
	}
	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {

		isAlreadyinPushSequence = false;
		super.frameUpdate(deltaTimeMS, level);
	}
	@Override
	public void logicUpdate(Level level) {
		//isAlreadyinPushSequence = false;
		super.logicUpdate(level);
	}
	public int getImageIndex(Level level, Direction dir)
	{
		return 215;
	}
}
