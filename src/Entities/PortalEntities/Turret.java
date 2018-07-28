package Entities.PortalEntities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Entities.GameEntitiy;
import Entities.MovingEntity;
import Entities.Player;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.Renderer;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Turret extends MovingEntity{
	
	Player target;
	
	Vector2 emmiterOffset = new Vector2(0,0);
	
	public Turret(Position pos) {
		super(pos);
		_ImageUpIndex = 226; 
		_ImageRightIndex = 227; 
		_ImageDownIndex = 226;
		_ImageLeftIndex = 229;
	}
	
	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		super.render(g, offset, scale, level);
		if(target != null ){
			if(isInRange(target.position) && rayTrace(level, position, target.position)){
				Vector2 start = Renderer.getScreenPosition(emmiterOffset.add(position).add(movementOffset).add(.5f,.4f), offset, scale);
				Vector2 end = Renderer.getScreenPosition(target.movementOffset.add(target.position).add(.5f,.5f), offset, scale);
		
				g.setColor(Color.red);
				g.drawLine(start.x, start.y, end.x, end.y);
			
				Position[] consideredPositions = getPositionsOnLine(position, target.position);
				
				for (int i = 0; i < consideredPositions.length; i++) {
					//if(consideredPositions[i] != null)
				//	Renderer.renderTile(g, Tile.Laser, consideredPositions[i], offset, scale);
					//Renderer.render
				//	consideredPositions[i];
				}
			}
		}
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		ArrayList<GameEntitiy> Players = level.getPlayers();
		//TODO
		
		for(int i =0; i < Players.size(); i++){
			if(rayTrace(level, position, Players.get(i).position)){
				target =(Player) Players.get(i);
			}
		}
		
		return false;
	}
	
	public boolean rayTrace(Level level, Position start, Position traget){
		//for (int i = 0 )
		Position[] positions = getPositionsOnLine(start, traget);
		for (int i = 0; i < positions.length; i++) {
			if(level.getTile(positions[i]) == Tile.Wall){
				return false;
			}
		}
		
		return true;
	}

	public boolean isInRange(Position p){
		return getDirectionToTarget(position, p) == position.dir;
	}
	
	
	Direction getDirectionToTarget(Position start, Position target){
		Position d = new Position(start.x-target.x, start.y-target.y);
		
		if(Math.abs(d.x) > Math.abs(d.y)){
			if(d.x > 0){
				return Direction.West;
			}else{
				return Direction.East;
			}
			
		}else{
			if(d.y > 0){
				return Direction.North;
			}else{
				return Direction.South;
			}
		}
	}
	
	
	Position[] getPositionsOnLine(Position start, Position end){
		int dx = start.x -end.x;
		int dy = start.y -end.y;
		int xdir = dx > 0 ? -1 : 1;
		int ydir = dy > 0 ? -1 : 1;

		
		ArrayList<Position> positions = new ArrayList<Position>();//[Math.abs(dx) + Math.abs(dy) -1];
		//int i = 0;
		int y = 0;
		int x = 0;
		int yi = 0;
		for (int xi = 0; xi <= Math.abs(dx); xi ++){

			positions.add(new Position(start.x + x, start.y + y));

			
			if(dy !=0)
			while(Math.abs((float)dx)/Math.abs((float)dy)*(float)(yi+.5f) < xi + .5f && yi < Math.abs(dy) ){
//				
//				
				if(positions.size() > 200){
					break;
				}
				yi++;
				y += ydir;
				positions.add(new Position(start.x + x, start.y + y));
				
			}
			if(positions.size() > 200){
				break;
			}
			
			x+=xdir;
		}
		
		//wwwwwwwwwpositions[0] = position;
		//positions[1] = position.add(Position.direction(Direction.South));
		
		
		
		return positions.toArray(new Position[0]);
	}

}
