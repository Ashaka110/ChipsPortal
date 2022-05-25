package Entities.PortalEntities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Recources.SpriteManager;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class LightBridge extends LinearEntity{

	public LightBridge(Position pos) {
		super(pos);
	}
	
	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		// TODO Auto-generated method stub
		super.render(g, offset, scale, level);
		
		
		this.renderSprite(g, offset, scale, level, position.add(Position.direction(position.dir)), SpriteManager.getSprite(getEmitterSpriteIndex(position.dir)));
		if(tiles.size() > 0){
			this.renderSprite(g, offset, scale, level,tiles.get(tiles.size()-1).position, SpriteManager.getSprite(getEndSpriteIndex(tiles.get(tiles.size()-1).position.dir.reverse())));
		}
//		
//		frame++;
//		if(frame >=16){
//			frame =0;
//		}
//		
//		for (int i = 0; i < tiles.size(); i++) {
//			Position pos = tiles.get(i).position;
//			if(tiles.get(i).tileOver != getReplacementTile()){
//				if(tiles.get(i).tileOver != null)
//				this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(tiles.get(i).tileOver.index));
//			}
//			if(i == 0 ){
//				this.renderSprite(g, offset, scale, level, tiles.get(0).position, SpriteManager.getSprite(getEmitterSpriteIndex(tiles.get(0).position.dir)));
//			}
//			
//			
//			
//			Image img = SpriteManager.getSprite(202);
//			
//			Vector2 drawLocation = offset.add(pos).scale(scale);
//			
//			g.drawImage(img, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frame*32,0,frame*32 + 32,32);
//			
//			//this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(getTileSpriteIndex(pos.dir)));
//			
//			
//			
//		}
//		
//		Vector2 drawLocation = offset.add(position.add(Position.direction(position.dir))).scale(scale);
//		
//		Image emmiter =  SpriteManager.getSprite(204);
//		
//		g.drawImage(emmiter, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frame*32,0,frame*32 + 32,32);
//		
	}

	@Override
	public void logicUpdate(Level level) {
	
		super.logicUpdate(level);
	}
	
	@Override
	public Tile getReplacementTile(Direction dir) {
		// TODO Auto-generated method stub
		return Tile.Light_Bridge;
	}
	
//	@Override
//	public int getTileSpriteIndex(Direction dir) {
//		if(dir == Direction.North  || dir == Direction.South){
//			return 143;
//		}else{
//			return 142;
//		}
//		
//	}
	public int getTileSpriteIndex(Direction dir) {
		switch(dir){
		case East:
			return 142;//231;
		case North:
			return 143;//234;
		case Null:
			return 143;//143;
		case South:
			return 143;//232;
		case West:
			return 142;//233;
		default:
			return 143;
			
		}
		
	}

	@Override
	public int getEmitterSpriteIndex(Direction dir) {
		if(dir == Direction.North ){
			return 156;
		}else if (dir == Direction.East){
			return 157;
		}else if (dir == Direction.South){
			return 158;
		}else {
			return 159;
		}
	}
	public int getEndSpriteIndex(Direction dir) {
		if(dir == Direction.North ){
			return 145;
		}else if (dir == Direction.East){
			return 146;
		}else if (dir == Direction.South){
			return 147;
		}else {
			return 144;
		}
	}

	
	
}
