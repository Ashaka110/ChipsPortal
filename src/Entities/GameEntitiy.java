package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Entities.PortalEntities.LinearEntity;
import Recources.Libraries.TileProperties;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.SpriteManager;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public abstract class GameEntitiy {
	
	public String Name;
	
	public Boolean isAlive;
	
	public TileProperties[] relations;
	
	public Position position;
	
	public GameEntitiy(Position pos){
		position = pos;
		setUpBlockProperties();
		isAlive = true;
	}
	public abstract void halfUpdate(Level level);
	
	public abstract void logicUpdate(Level level);
	
	public abstract void frameUpdate(int deltaTimeMS, Level level);
	
	public abstract void render(Graphics g, Vector2 offset, float scale, Level level);
	
	
	protected void setUpBlockProperties(){
		relations = new TileProperties[SpriteManager.maxSprite];
		
		for(int i = 0; i< relations.length; i++){
			relations[i] = TileProperties.Blocking;
		}
		
		relations[Tile.Floor.index] = TileProperties.Passable;
	}
	
	public void renderSprite(Graphics g, Vector2 cameraOffset, float scale, Level level, Vector2 moveOffset, Position pos, Image sprite){
		Vector2 drawLocation = cameraOffset.add(pos).add(moveOffset).scale(scale);
		g.drawImage(sprite, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, 0,0,32,32);
		
	}
	public void renderSprite(Graphics g, Vector2 cameraOffset, float scale, Level level, Position pos, Image sprite){
		Vector2 drawLocation = cameraOffset.add(pos).scale(scale);
		if(sprite != null)
		g.drawImage(sprite, drawLocation.x, drawLocation.y, drawLocation.x + scale,drawLocation.y + scale, 0,0,32,32);
		
	}
	
	public void renderDebug(Graphics g, Vector2 offset, float scale, Level level) {
	}
	public void onStartFrame(Level level)
	{
		
	}
	
	public String getName(){
		return Name;
	}
	
	@Override
	public String toString() {
		return getName() + " " + position.x + " " + position.y + " " + position.dir.index + "\n"; 
	}
	
	public boolean isFloorEntity(){
		return this instanceof LinearEntity;
	}
}
