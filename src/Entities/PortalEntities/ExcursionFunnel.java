package Entities.PortalEntities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Recources.SpriteManager;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class ExcursionFunnel extends LinearEntity{


	int frame = 0;
	
	boolean isReversed=false;
	
	public ExcursionFunnel(Position pos, boolean reversed) {
		super(pos);
		isReversed = reversed;
	}

	@Override
	public int getTileSpriteIndex(Direction dir) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEmitterSpriteIndex(Direction dir) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tile getReplacementTile(Direction dir) {
		//dir.reverse();
		
		switch (isReversed? dir.reverse() : dir){
		case East:
			return Tile.ExcursionFunnel_Right;
		case North:
			return Tile.ExcursionFunnel_Up;
		case Null:
			return Tile.ExcursionFunnel_Right;
		case South:
			return Tile.ExcursionFunnel_Down;
		case West:
			return Tile.ExcursionFunnel_Left;
		default:
			return Tile.ExcursionFunnel_Right;
		
		}
	}
	
	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
//		// TODO Auto-generated method stub
//		super.render(g, offset, scale, level);
//		
//		
//		this.renderSprite(g, offset, scale, level, position.add(Position.direction(position.dir)), SpriteManager.getSprite(getEmitterSpriteIndex(position.dir)));
//		if(tiles.size() > 0){
//			this.renderSprite(g, offset, scale, level,tiles.get(tiles.size()-1).position, SpriteManager.getSprite(getEndSpriteIndex(tiles.get(tiles.size()-1).position.dir.reverse())));
//		}
//		
		int frameToDraw = frame;
		if(isReversed){
			frameToDraw = 16-frame;
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			Position pos = tiles.get(i).position;
			if(!tiles.get(i).tileOver.isLinearEntityTile()){
				if(tiles.get(i).tileOver != null)
					this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(tiles.get(i).tileOver.index));
			}
			if(i == 0 ){
			//	this.renderSprite(g, offset, scale, level, tiles.get(0).position, SpriteManager.getSprite(getEmitterSpriteIndex(tiles.get(0).position.dir)));
			}
			
			int imgIndex;
			
			if(isReversed){
				if(tiles.get(i).position.dir == Direction.North || tiles.get(i).position.dir == Direction.South){
					imgIndex =206;
				}else {
					imgIndex = 203;
				}
			}else{
				if(tiles.get(i).position.dir == Direction.North || tiles.get(i).position.dir == Direction.South){
					imgIndex =205;
				}else {
					imgIndex = 202;
				}
			}
			
			Image img = SpriteManager.getSprite(imgIndex);
			
			
			
			
			
			Vector2 drawLocation = offset.add(pos).scale(scale);
			
			if(tiles.get(i).position.dir == Direction.South || tiles.get(i).position.dir == Direction.East)
				g.drawImage(img, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frameToDraw*32,0,frameToDraw*32 + 32,32);
			else{
				g.drawImage(img, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frameToDraw*32 + 32,32,frameToDraw*32 ,0);
			}
			//this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(getTileSpriteIndex(pos.dir)));
			
			
			
		}
		
		Vector2 drawLocation = offset.add(position.add(Position.direction(position.dir))).scale(scale);
		
		int imgIndex;
		
		if(isReversed){
			if(position.dir == Direction.North || position.dir == Direction.South){
				imgIndex =209;
			}else {
				imgIndex = 208;
			}
		}else{
			if(position.dir == Direction.North || position.dir == Direction.South){
				imgIndex =207;
			}else {
				imgIndex = 204;
			}
		}
		
		Image img = SpriteManager.getSprite(imgIndex);
		
			
		if(position.dir == Direction.North || position.dir == Direction.East)
			g.drawImage(img, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frameToDraw*32,0,frameToDraw*32 + 32,32);
		else{
			g.drawImage(img, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, frameToDraw*32 + 32,32,frameToDraw*32 ,0);
		}
	}
	
	static int fps = 16;
	int counter;
	
	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
		// TODO Auto-generated method stub
		super.frameUpdate(deltaTimeMS, level);
		
		counter+=deltaTimeMS;
		if(counter  > 1000/fps){
			counter-=1000/fps;
			frame++;
			if(frame >=16){
				frame =0;
			}
		}
	}
	
	public void reverse(){
		isReversed = !isReversed;
	}public boolean isReversed(){
		return isReversed;
	}

}
