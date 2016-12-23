package Entities.PortalEntities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Entities.GameEntitiy;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.SpriteManager;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class Portal extends GameEntitiy{

	PortalManager parent;
	Portal link;
	boolean isBlue;
	
	boolean isOpen;
	
	public Portal(Position pos, boolean isBlue, PortalManager parent) {
		super(pos);
		this.isBlue = isBlue;
		this.parent = parent;
		isOpen = false;
	}
	public void setLink(Portal p){
		link = p;
	}

	public void open(Position p){
		this.position = p;
		isOpen = true;
	}
	public void close(){
		isOpen = false;
	}
	
	public boolean attemptEnter(Direction dir){
		if(isOpen && link.isOpen){
			return dir == position.dir;
		}
		return false;
	}
	
	public Position getExitPosition(){
		Position exit = new Position(link.position.x, link.position.y);
		exit = exit.add(Position.direction(link.position.dir.reverse()));
		exit.dir = link.position.dir.reverse();
		return exit;
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
		if(isOpen){
			Vector2 drawLocation = offset.add(position).scale(scale);
			//g.drawImage(SpriteManager.getSprite(Tile.Wall.index), drawLocation.x, drawLocation.y);
			
			this.renderSprite(g, offset, scale, level, position, SpriteManager.getSprite(Tile.Wall.index));
			
			drawLocation = offset.add(position);
		
			drawLocation = drawLocation.add(Position.direction(position.dir.reverse()));
		
			drawLocation = drawLocation.scale(scale);
		
			//g.drawImage(getImage(level), drawLocation.x, drawLocation.y);
			this.renderSprite(g, offset, scale, level, position.add(Position.direction(position.dir.reverse())), getImage(level));
			
		}
	}
	public Image getImage(Level level){
		return SpriteManager.getSprite(getImageIndex(level));
	}
	
	
	public int getImageIndex(Level level)
	{
		if(position.dir == null){
			return 131;
		}
		if(isBlue){
			switch (position.dir){
				case North:
					return 131;
				case East:
					return 132;
				case South:
					return 133;
				case West:
					return 134;
				default:
					return 131;
			}
		}else{
			switch (position.dir){
			case North:
				return 136;
			case East:
				return 137;
			case South:
				return 138;
			case West:
				return 139;
			default:
				return 136;
		}
		}
	}
	
	@Override
	public String getName() {
		if(isBlue){
			return "BluePortal";
		}else{
			return "OrangePortal";
		}
	}
}
