package Entities.PortalEntities;

import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.Position;

public class LightBarrier extends LightBridge{

	
	
	public LightBarrier(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Tile getReplacementTile(Direction dir) {
		// TODO Auto-generated method stub
		return Tile.Light_Bridge;
	}
	
	@Override
	public int getTileSpriteIndex(Direction dir) {
		switch(dir){
		case East:
			return 232;
		case North:
			return 231;
		case Null:
			return 143;
		case South:
			return 233;
		case West:
			return 234;
		default:
			return 143;
			
		}
		
	}

}
