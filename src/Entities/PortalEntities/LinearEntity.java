package Entities.PortalEntities;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.newdawn.slick.Graphics;

import Entities.GameEntitiy;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.SpriteManager;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public abstract class LinearEntity extends GameEntitiy{

	Tile[] blockingTiles = {Tile.Wall, Tile.BlueWall_Fake, Tile.BlueWall_Real, Tile.Lock_Blue, Tile.Lock_Green, Tile.Lock_Red, Tile.Lock_Yellow, Tile.Metal_Wall, Tile.Chip_Socket, Tile.ToggleWall_Closed};
	
	//Tile replacementTile;
	
	ArrayList<LinearEntitySegment> tiles;
	
	public LinearEntity(Position pos) {
		super(pos);
		tiles = new ArrayList<LinearEntitySegment>();
		//replacementTile = Tile.Floor;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onStartFrame(Level level) {
		degenerate(level);
		super.onStartFrame(level);
	}

	public void degenerate(Level level){
		for (int i = tiles.size() -1; i >= 0 ; i--) {
			if(!tiles.get(i).tileOver.isLinearEntityTile())
				level.setTile(tiles.get(i).position, tiles.get(i).tileOver);
			tiles.remove(i);
		}
	}
	
	public void generate(Level level){
		
		degenerate(level);
		
		createTile(level, position, position.dir);
	}
	
	public void createTile(Level level, Position start, Direction dir){
		Position newPos = getNextPosition(level, start, dir);
		Tile t = level.getTile(newPos);
		
		if(!isBlocked(level, newPos)){//t != Tile.Wall){
			tiles.add(new LinearEntitySegment(newPos, t));
			level.setTile(newPos, getReplacementTile(newPos.dir));
			createTile(level, newPos, newPos.dir);
		}
	}
	
	public Position getNextPosition(Level level, Position start, Direction dir){
		return level.getPostitionInDiretion(start, dir);
	}
	
	@Override
	public void halfUpdate(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logicUpdate(Level level) {
		generate(level);
	}

	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		
		for (int i = 0; i < tiles.size(); i++) {
			Position pos = tiles.get(i).position;
			if(!tiles.get(i).tileOver.isLinearEntityTile()){
				if(tiles.get(i).tileOver != null){
				  this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(tiles.get(i).tileOver.index));
				}
			}
			if(i == 0 ){
				this.renderSprite(g, offset, scale, level, tiles.get(0).position, SpriteManager.getSprite(getEmitterSpriteIndex(tiles.get(0).position.dir)));
			}
			
			this.renderSprite(g, offset, scale, level, pos, SpriteManager.getSprite(getTileSpriteIndex(pos.dir)));
			
		}
		
	}
	
	public boolean isBlocked(Level level, Position pos){
		Tile t = level.getTile(pos);
		for (int i = 0; i < blockingTiles.length; i++) {
			if(t == blockingTiles[i]){
				return true;
			}
		}
		if (t == Tile.Wall || t == Tile.ToggleWall_Closed || t == Tile.Metal_Wall){
			return true;
		}
		
		
		
		return false;
	}
	
	public void onGreenButtonPress(){
		for (int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).tileOver == Tile.ToggleWall_Open){
				tiles.get(i).tileOver = Tile.ToggleWall_Closed;
			}else if(tiles.get(i).tileOver == Tile.ToggleWall_Closed){
				tiles.get(i).tileOver = Tile.ToggleWall_Open;
			}
		}
	}
	
	public int getChipCount(){
		int count =0;
		for (int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).tileOver == Tile.Chip){
				count ++;
			}
		}
		return count;
	}
	
	public abstract int getTileSpriteIndex(Direction dir);
	public abstract int getEmitterSpriteIndex(Direction dir);
	
	public abstract Tile getReplacementTile(Direction dir);
	
}
