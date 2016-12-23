package Entities.PortalEntities;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Structures.Level;
import Structures.Position;

public class PortalFissler extends MovingEntity{

	boolean isBlue;
	PortalManager portalManager;
	
	public PortalFissler(Position pos, PortalManager manager, boolean isBlue) {
		super(pos);
		portalManager = manager;
		this.isBlue = isBlue;
		
		relations[Tile.Fire.index] = TileProperties.Passable;
		relations[Tile.Water.index] = TileProperties.Passable;

		relations[Tile.Laser.index] = TileProperties.Passable;

	}

	@Override
	public void logicUpdate(Level level) {
		makeMoveDecision(level);
	}
	
	@Override
	public boolean makeMoveDecision(Level level) {
		Direction currentDirection = position.dir;
		
		//move(currentDirection, level);
		//move(currentDirection, level);
		if(!move(currentDirection, level)){
			if(level.getTile(level.getPostitionInDiretion(position, currentDirection)) == Tile.Wall){
				Position ppos = position.add(Position.direction(position.dir));
				ppos.dir = position.dir;
				//Portal p = new Portal(ppos, isBlue, portalManager);
				
				portalManager.onOpenPortal(level, ppos, isBlue);
				
			}
			this.isAlive = false;
		}	
		
		
		return false;
	}
	@Override
	public boolean isOverClosedTrap(Level level) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean moveOnForceFloor(Level level) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean moveOnIce(Level level) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getImageIndex(Level level, Direction dir) {
		if(isBlue){
			return 130;
		}else{
			return 135;
		}
	}
	
	@Override
	public void onCommitMove(Position target, Level level) {
		// TODO Auto-generated method stub
		//super.onCommitMove(target, level);
	}
	
}
