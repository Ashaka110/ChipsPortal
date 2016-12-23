package Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Recources.Renderer;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Button extends GameEntitiy{

	public Position targetPosition;
	
	
	public Button(Position pos) {
		super(pos);
	}
	public Button(Position pos, Position trappos) {
		super(pos);
		setTarget(trappos);
	}

	@Override
	public void halfUpdate(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logicUpdate(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
		// TODO Auto-generated method stub
		
	}
	
	public void setTarget(Position p){
		targetPosition = p;
	}

	
	public void renderDebug(Graphics g, Vector2 offset, float scale, Level level) {
		//super.render(g, offset, scale, level);
		if(targetPosition != null){
			Vector2 start = Renderer.getScreenPosition(new Vector2(.5f, .5f).add(position), offset, scale);
			Vector2 end = Renderer.getScreenPosition(new Vector2(.5f, .5f).add(targetPosition), offset, scale);
		
			g.setColor(Color.red);
			g.drawLine(start.x, start.y, end.x, end.y);
		}
	}
	
	
}
