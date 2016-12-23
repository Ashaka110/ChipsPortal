package Entities.PortalEntities;

import org.newdawn.slick.Image;

import Entities.StandardEnemies.Block;
import Recources.Libraries.Angle;
import Recources.Libraries.Direction;
import Recources.SpriteManager;
import Structures.Level;
import Structures.Position;

public class Reflector extends Block{
	
	public Angle angle;
	Angle portalShadowAngle;
	
	public Reflector(Position pos, int rotation) {
		super(pos);
		angle = Angle.getAngle(rotation);
	}
	
	
	@Override
	public int getImageIndex(Level level, Direction dir) {
		return getImageIndex(angle);
	}
	public int getImageIndex(Angle a) {
		switch (a){
		case NE:
			return 169;
		case NW:
			return 168;
		case SE:
			return 166;
		case SW:
			return 167;
		default:
			return 169;
		
		}
	}
	
	
	@Override
	public Image getPortalShadowImage(Level level, Direction dir) {
		return SpriteManager.getSprite( getImageIndex(portalShadowAngle));
	}
	
	public Direction getOutputPosition(Direction dir){
		return angle.getOutputDirection(dir);
	}
	
	
	@Override
	public void OnEnterPortal(Direction enter, Direction exit) {
		int i = angle.index;
		portalShadowAngle = angle;
		
		if(enter == exit){
			
		}else if(exit == enter.clockwise()){
			i+=1;
		}else if(exit == enter.clockwise().clockwise()){
			i+=2;
		}else if(exit == enter.clockwise().clockwise().clockwise()){
			i+=3;
		}
		
		i%=4;
		
		angle = Angle.getAngle(i);
		
	}
}
