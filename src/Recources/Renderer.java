package Recources;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Base.Game;
import Entities.Button;
import Entities.GameEntitiy;
import Entities.TrapToggle;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.Inventory;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Renderer {
	public static final int TILE_SOURCE_WIDTH = 32;
	public static float viewWidth = 9;
	//public static float screenSize = 576;
	
	public static int chipStatYOffset = 383;
	public static int timeStatYOffset = 203;
	public static int levelStatYOffset = 79;
	
	public static int tileSize = 32;
	
	public static void setScale(int tileWidth)
	{
		viewWidth = Game.PLAY_SIZE/tileWidth; 
		
	}	
	public static void renderLevel(Graphics g, Level level, Vector2 offset, int tileDrawWidth){
		float tileWidth = tileDrawWidth;
		
		Tile[][] tiles = level.board;
		ArrayList<GameEntitiy> floorEntities = level.floorEntities;
		ArrayList<GameEntitiy> portalFisslers = level.portalFisslers;
		ArrayList<GameEntitiy> entities = level.entities;
		ArrayList<GameEntitiy> players = level.Players;
		ArrayList<GameEntitiy> portals = level.Portals;
		
		renderTiles(g, tiles, offset, tileWidth);
		renderEntities(g, floorEntities, offset, tileWidth, level);
		renderEntities(g, portalFisslers, offset, tileWidth, level);
		renderEntities(g, players, offset, tileWidth, level);
		renderEntities(g, entities, offset, tileWidth, level);
		renderEntities(g, portals, offset, tileWidth, level);
		//render portals
		
		
	}
	
	public static void renderFloorEntities(){
		
	}
	
	public static void renderTiles(Graphics g, Tile[][] tiles, Vector2 offset, float tileWidth){
		for(int x = 0; x < tiles.length; x++ ){
			 for(int y = 0; y < tiles[x].length; y++ ){
				 if(y + offset.x > -2 && x + offset.y > -2 && y + offset.x < viewWidth +2 && x + offset.y < viewWidth +2)
					 renderTile(g, tiles[x][y], new Position(y,x), offset, tileWidth);
			 }	 
		 }
	}
	
	public static void renderTile(Graphics g, Tile tile, Position position, Vector2 offset, float tileWidth){
		Vector2 drawPosition = offset.add(position).scale(tileWidth);
		
		renderTile(g, tile, drawPosition);
	}
	public static void renderTile(Graphics g, Tile tile, Vector2 drawPosition){	
		if(tile == null){
			return;
		}
		Image image = SpriteManager.getSprite(tile.index);
		
		try
		{
			renderSprite(g, image, drawPosition,(int)( Game.PLAY_SIZE/viewWidth));
		}
		catch(Exception e){
			
			if(tile == Tile.NULL_TILE)
			{
				System.out.println("Null Tile");// At: "  + position.x + ", " + position.y);
			}
			else
			{
				System.out.println("Image Not Found: Index: " + tile.index);// + " At: "  + position.x + ", " + position.y);
			}
		}
	}
	
	
	
	public static void renderEntities(Graphics g, ArrayList<GameEntitiy> entities , Vector2 offset, float scale, Level level){
		for(int i = 0; i < entities.size(); i++ ){
			 entities.get(i).render(g, offset, scale, level);
		}
	}
	
	
	public static Vector2 getScreenOffset(Level level)
	{
		return level.getCameraPlayerOffset(viewWidth).scale(-1);
	}
	
	
	public static Vector2 getPlayerScreenPosition(Level level){	
		Vector2 screenOffset = getScreenOffset(level);//.add(new Vector2(8,8));
		Vector2 playerSpritePos = screenOffset.add(level.activePlayer.position);
		return playerSpritePos.scale(Game.PLAY_SIZE/viewWidth);//.add(new Vector2(.5f, .5f))
	}
	
	public static void renderSprite(Graphics g, Vector2 cameraOffset,int gridSize, int imgSize, Level level, Position pos, Image sprite){
		Vector2 drawLocation = cameraOffset.add(pos).scale(gridSize);
		renderSprite(g, sprite, drawLocation, imgSize);
	}
	
	public static void renderSprite(Graphics g, Vector2 cameraOffset, int imgSize, Level level, Position pos, Image sprite){
		Vector2 drawLocation = cameraOffset.add(pos).scale(imgSize);
		renderSprite(g, sprite, drawLocation, imgSize);
	}
	
	public static void renderSprite(Graphics g, Image sprite, Vector2 drawLocation, int imgSize){
		renderImage(g, sprite, drawLocation, imgSize, imgSize, sprite.getWidth(), sprite.getHeight());
	}
	
	
	public static void renderImage(Graphics g, Image sprite, Vector2 drawLocation){
		if(drawLocation != null)
			renderImage(g, sprite, drawLocation, sprite.getWidth(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
	}
	
	public static void renderImage(Graphics g, Image sprite, Vector2 drawLocation, int drawWidth, int drawHeight, int sorceWidth, int sourceHeight){
		g.drawImage(sprite, drawLocation.x, drawLocation.y, drawLocation.x + drawWidth,drawLocation.y + drawHeight, 0,0, sorceWidth , sourceHeight);
	}
	
	public static void renderSidePannel(Graphics g, int level, int time, int chipcount, Inventory inventory){
		renderImage(g,SpriteManager.getSprite(129), new Vector2( 576, 0 ));
		
		NumberRenderer.drawstat(g, level, levelStatYOffset);
		NumberRenderer.drawstat(g, time, timeStatYOffset);
		NumberRenderer.drawstat(g, chipcount, chipStatYOffset);
	
		renderInventory(g, 576 +20, chipStatYOffset + 60, 64, inventory);
	}
	
	public static void renderInventory(Graphics g, int xOffset, int yOffset, float scale, Inventory inventory){
		Vector2 o = new Vector2(xOffset, yOffset);
		renderSprite(g, SpriteManager.getSprite(inventory.blueKeys>0? Tile.Key_Blue.index:Tile.Floor.index), o, 64);
		renderSprite(g, SpriteManager.getSprite(inventory.greenKeys>0? Tile.Key_Green.index:Tile.Floor.index), o.add(new Vector2(64, 0)), 64);
		renderSprite(g, SpriteManager.getSprite(inventory.redKeys>0? Tile.Key_Red.index:Tile.Floor.index), o.add(new Vector2(128, 0)), 64);
		renderSprite(g, SpriteManager.getSprite(inventory.yellowKeys>0? Tile.Key_Yellow.index:Tile.Floor.index), o.add(new Vector2(192, 0)), 64);
		
		renderSprite(g,SpriteManager.getSprite( inventory.hasFlippers? Tile.Equipment_Flippers.index:Tile.Floor.index), o.add(new Vector2(0, 64)), 64);
		renderSprite(g,SpriteManager.getSprite( inventory.hasFireBoots? Tile.Equipment_Fireboots.index:Tile.Floor.index), o.add(new Vector2(64, 64)), 64);
		renderSprite(g,SpriteManager.getSprite( inventory.hasForceBoots? Tile.Equipment_ForceBoots.index:Tile.Floor.index), o.add(new Vector2(128, 64)), 64);
		renderSprite(g,SpriteManager.getSprite( inventory.hasIceSkates? Tile.Equipment_IceScates.index:Tile.Floor.index), o.add(new Vector2(192, 64)), 64);

	}
	
	public static void renderDebug(Graphics g, Level level, Vector2 offset){
		
		for (int i = 0; i < level.entities.size(); i++) {
			renderSprite(g, SpriteManager.getSprite(getDebugDirectionIndex(level.entities.get(i).position.dir)), offset.add(level.entities.get(i).position).scale(TILE_SOURCE_WIDTH), TILE_SOURCE_WIDTH);
		}
		
		for (int i = 0; i < level.Traps.size(); i++) {
			//renderSprite(g, SpriteManager.getSprite(getDebugDirectionIndex(level.entities.get(i).position.dir)), offset.add(level.entities.get(i).position).scale(TILE_SOURCE_WIDTH), TILE_SOURCE_WIDTH);
			((Button)level.Traps.get(i)).renderDebug(g, offset, TILE_SOURCE_WIDTH, level);
		}
		
		Tile[][] tiles = level.board;
		
		for(int x = 0; x < tiles.length; x++ ){
			 for(int y = 0; y < tiles[x].length; y++ ){
				if(tiles[x][y] == Tile.BlueWall_Fake)// if(y + offset.x > -2 && x + offset.y > -2 && y + offset.x < viewWidth +2 && x + offset.y < viewWidth +2)
					 renderSprite(g, offset, TILE_SOURCE_WIDTH, level, new Position(y,x), SpriteManager.getSprite(150));
			 }	 
		 }
		
	}
	
	public static Vector2 getScreenPosition(Vector2 location, Vector2 offset, float scale){
		return location.add(offset).scale(scale);
	}
	
	public static int getDebugDirectionIndex(Direction dir){
		switch (dir){
		case East:
			return 152;
		case North:
			return 151;
		case Null:
			return 151;
		case South:
			return 153;
		case West:
			return 154;
		default:
			return 151;
		
		}
	}
	
}
