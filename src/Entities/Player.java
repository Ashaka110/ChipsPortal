package Entities;

import org.newdawn.slick.Graphics;

import Entities.PortalEntities.PortalManager;
import Entities.StandardEnemies.Block;
import Recources.InputManager;
import Recources.LevelLoader;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Libraries.TileProperties;
import Recources.Renderer;
import Structures.Inventory;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Player extends MovingEntity{

	Inventory inventory;
	
	PortalManager portalManager;
	
	
	public Player(Position pos) {
		super(pos);
		_ImageUpIndex = 102; 
		_ImageRightIndex = 103; 
		_ImageDownIndex = 101;
		_ImageLeftIndex = 104; 
		inventory = new Inventory();
		portalManager = new PortalManager();
		
		relations[Tile.Gravel.index] = TileProperties.Passable;
		relations[Tile.Dirt.index] = TileProperties.Destructable;
		relations[Tile.BlueWall_Fake.index] = TileProperties.Destructable;
		relations[Tile.Popup_Wall.index] = TileProperties.Passable;
		relations[Tile.Portal.index] = TileProperties.Passable;
		
		relations[Tile.Theif.index] = TileProperties.Passable;

		
	}
	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
		// TODO Auto-generated method stub
		super.frameUpdate(deltaTimeMS, level);
		if(inventory.hasPortalGun){
			if(InputManager.isLeftMouseButtonPressed()){
				shootPortal(level, true);
			}
			if(InputManager.isRightMouseButtonPressed()){
				shootPortal(level, false);
			}
		}
	}
	
	@Override
	public boolean makeMoveDecision(Level level) {
		
		if(level.getBlock(position) != null){
			isAlive = false;
			return true;
		}
		
		if(this == level.activePlayer){
			Direction dir = InputManager.getLastPressedMove();
			
			if(dir != Direction.Null && dir != null){
				
				Tile t = level.getTile(position);
				if(t.isFunnel()){
					if (dir == Tile.getForceFloorDirection(t) || dir == Tile.getForceFloorDirection(t).reverse()){
						position.dir = dir;
						return false;
					}
				}
				
				position.dir = dir;
				this.move(dir, level);
	
				
				return true;
			}
		}
		
		return false;
	}
	
	public void shootPortal(Level level, boolean isBlue){
		Position pos = new Position(0,0).add(position);
		Direction d = Renderer.getPlayerScreenPosition(level).getRelativeDirection(InputManager.getMousePostion());
		pos.dir = d;
	
		portalManager.Fire(pos, level, isBlue);
	}
	
	public Boolean pushYeilds(Position target, Level level)
	{
		super.pushYeilds(target, level);
		Tile t = level.getTile(target);
		
		Tile on = level.getTile(position);
		if(on == Tile.Trap_Closed){
			return false;
		}
		
		if(!t.canEnterBlock(target.dir)){
			return false;
		}		
		if(!on.canLeaveBlock(target.dir)){
			return false;
		}
		
		
		
		Block b = level.getBlock(target);
		if(b != null){
			if(!b.push(target.dir, level)){//!level.pushBlock(target)){
				return false;
			}
		}
		
		if(inventory.attemptCollect(t)){
			level.setTile(target, Tile.Floor);
		}
		else if(t == Tile.Chip_Socket && level.chipCount() == 0){
			level.setTile(target, Tile.Floor);
		}
		
		if(t == Tile.BlueWall_Real){
			level.setTile(target, Tile.Wall);
		}
		if(t == Tile.Theif){
			inventory.onStepThief();
		}
		
		return true;
	}
	
	@Override
	public void onCommitMove(Position target, Level level) {
		if(level.getTile(target) == Tile.Checkpoint){
			level.Players.remove(this);
			level.Players.add(this);
			
			LevelLoader.saveCheckPoint(level, "CheckPoint.txt");
			level.CheckpointEnabled = true;
		}
		super.onCommitMove(target, level);
	}
	
	@Override
	public void onMove(Position target, Level level) {
		super.onMove(target, level);
		
		if(level.isTileMonsterOcupied(target)){
			isAlive = false;
		}
		
		if(level.getTile(position) == Tile.Popup_Wall){
			level.setTile(position, Tile.Wall);
		}
		
	}
	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		// TODO Auto-generated method stub
		super.render(g, offset, scale, level);
	}
	
	
	public int getImageIndex(Level level, Direction dir)
	{
		Tile tileOn = level.getTile(position);
		
		
		if(tileOn == Tile.Water){
			if(!this.hasFlippers()  && this.movementOffset.equals(new Vector2())){
				return 99;
			}
			switch (dir){
			case North:
				return 178;
			case East:
				return 179;
			case South:
				return 176;
			case West:
				return 177;
			default:
				return 176;
			}
		}
		return super.getImageIndex(level, dir);
	}
	
	public boolean hasWon(Level level){
		Tile t = level.getTile(position);
		if(t == Tile.Portal){
			return true;
		}
		return false;
	}
	
	public boolean hasForceFloorBoots(){
		return inventory.hasForceBoots;
	}public boolean hasIceSkates(){
		return inventory.hasIceSkates;
	}public boolean hasFlippers(){
		return inventory.hasFlippers;
	}public boolean hasFireBoots(){
		return inventory.hasFireBoots;
	}public boolean canMoveOnForceFloors(){
		return true;
	}
	public Inventory getInventory()
	{	
		return inventory;
	}
	public PortalManager getPortalManager()
	{	
		return portalManager;
	}
}
