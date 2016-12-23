package Entities.PortalEntities;

import org.newdawn.slick.Graphics;

import Entities.GameEntitiy;
import Recources.Libraries.Tile;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class LinearEntitySegment extends GameEntitiy{
	

	Tile tileOver;
	
	public LinearEntitySegment(Position pos, Tile over) {
		super(pos);
		tileOver = over;
	}

	@Override
	public void halfUpdate(Level level) {
	}

	@Override
	public void logicUpdate(Level level) {
	}

	@Override
	public void frameUpdate(int deltaTimeMS, Level level) {
	}

	@Override
	public void render(Graphics g, Vector2 offset, float scale, Level level) {
	}
	
	
	
}
