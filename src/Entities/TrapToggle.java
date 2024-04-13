package Entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Entities.PortalEntities.PortalFissler;
import Recources.Renderer;
import Recources.Libraries.Tile;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class TrapToggle extends Button{
	
	
	
	public TrapToggle(Position pos) {
		super(pos);
	}
	public TrapToggle(Position pos, Position trappos) {
		super(pos);
		setTarget(trappos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void halfUpdate(Level level) {
		
	}

	@Override
	public void logicUpdate(Level level) {
		if(targetPosition != null && level.getTile(position) == ButtonTile()){
			
			ArrayList<GameEntitiy> ents = level.getGameEntitys(position);
			for(int i =0; i < ents.size(); i++){
				if(ents.get(i) instanceof MovingEntity && ! (ents.get(i) instanceof PortalFissler))
				{
					setTrapOpen(level);
				}
			}
			//if(level.hasEntAt(position, Player.class)){
				
				//trap set to closed in level.reset traps called at beginning of update 
		//		level.setTile(targetPosition, Tile.Trap_Open);
			//}
		}
	}

	public Tile ButtonTile() {
		return Tile.Button_Trap;
	}
	public Tile DefaultTargetTile() {
		return Tile.Trap_Closed;
	}

	
	void setTrapOpen(Level level) {
		level.setTile(targetPosition, Tile.Trap_Open);
	}
	
	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
		
	}

	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		
	}
	
	
	
	
	
	
	

}
