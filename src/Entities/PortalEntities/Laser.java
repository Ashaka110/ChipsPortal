package Entities.PortalEntities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import Entities.GameEntitiy;
import Recources.SpriteManager;
import Recources.Libraries.Angle;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Laser extends LinearEntity{

	ArrayList<corner> corners;
	
	public Laser(Position pos) {
		super(pos);
		corners = new ArrayList<corner>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void degenerate(Level level) {
		corners.clear();
		super.degenerate(level);
	}

	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		//render emitter
		this.renderSprite(g, offset, scale, level, position.add(Position.direction(position.dir)), SpriteManager.getSprite(getEmitterSpriteIndex(position.dir)));
		
		super.render(g, offset, scale, level);
		for(int i =0; i < corners.size(); i++){
			Position p = corners.get(i).pos;
			Angle a = corners.get(i).angle;
			this.renderSprite(g, offset, scale, level, p, SpriteManager.getSprite(corners.get(i).getImageIndex(a)));
		}
	}
	
	@Override
	public int getTileSpriteIndex(Direction dir) {
		if(dir == Direction.North  || dir == Direction.South){
			return 148;
		}else{
			return 149;
		}
	}
	
	@Override
	public boolean isBlocked(Level level, Position pos) {
		if(level.getBlock(pos) != null){
			return true;
		}
		
		return super.isBlocked(level, pos);
	}

	@Override
	public Position getNextPosition(Level level, Position start, Direction dir) {
		Position next = super.getNextPosition(level, start, dir);
		
		ArrayList<GameEntitiy> ents = level.getGameEntitys(next);
		for(int i = 0; i < ents.size(); i++ ){
			if(ents.get(i) instanceof Reflector){
				Direction result = ((Reflector) ents.get(i)).getOutputPosition(dir);
				if(result != Direction.Null){
					//Position p = level.getPostitionInDiretion(next, result);
					corners.add(new corner(((Reflector) ents.get(i)).angle, next));
					return getNextPosition(level, next, result);
				}
			}
		}
		
		
		return next;
	}
	
	@Override
	public Tile getReplacementTile(Direction dir) {
		// TODO Auto-generated method stub
		return Tile.Laser;
	}
	@Override
	public int getEmitterSpriteIndex(Direction dir) {
		if(dir == Direction.North ){
			return 161;
		}else if (dir == Direction.East){
			return 162;
		}else if (dir == Direction.South){
			return 163;
		}else {
			return 164;
		}
	}
	
	
	private class corner{
		Position pos;
		Angle angle;
		
		public corner(Angle ang, Position p){
			angle = ang;
			pos = p;
		}
		
		int getImageIndex(Angle ang){
			switch (ang)
			{
			case NE:
				return 174;
			case NW:
				return 173;
			case SE:
				return 171;
			case SW:
				return 172;
			default:
				return 171;
			}
		}
	}
}
