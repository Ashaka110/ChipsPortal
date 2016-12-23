package Entities.PortalEntities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Entities.MovingEntity;
import Entities.Player;
import Recources.Renderer;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Turret extends MovingEntity{
	
	Player target;
	
	Vector2 emmiterOffset = new Vector2(0,0);
	
	public Turret(Position pos) {
		super(pos);
		_ImageUpIndex = 110; 
		_ImageRightIndex = 110; 
		_ImageDownIndex = 110;
		_ImageLeftIndex = 110;
	}
	
	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		super.render(g, offset, scale, level);
		if(target != null){
			Vector2 start = Renderer.getScreenPosition(emmiterOffset.add(position).add(movementOffset), offset, scale);
			Vector2 end = Renderer.getScreenPosition(target.movementOffset.add(target.position), offset, scale);
		
			g.setColor(Color.red);
			g.drawLine(start.x, start.y, end.x, end.y);
		}
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		for(int i =0; i < level.Players.size(); i++){
			if(rayTrace(level, position, level.Players.get(i).position)){
				target =(Player) level.Players.get(i);
			}
		}
		
		return false;
	}
	
	public boolean rayTrace(Level level, Position start, Position traget){
		return true;
	}

}
