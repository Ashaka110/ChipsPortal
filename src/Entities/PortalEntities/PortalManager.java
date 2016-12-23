package Entities.PortalEntities;

import Structures.Level;
import Structures.Position;

public class PortalManager {

	Portal Blue, Orange;
	
	public PortalManager(){
		Blue = new Portal(new Position(0,0), true, this);
		Orange = new Portal(new Position(0,0), false, this);
		
		Blue.setLink(Orange);
		Orange.setLink(Blue);
		
		
	}
	
	public void Fire(Position pos, Level level, boolean isBlue){
		level.addFissler(new PortalFissler(pos, this, isBlue));
	}
	
	
	public void onOpenPortal(Level level, Position p, boolean isBlue){
		if(isBlue){
			Blue.open(p);
		}else{
			Orange.open(p);
		}
		
		level.addPortal(Blue);
		level.addPortal(Orange);
		
	}
	
	@Override
	public String toString() {
		String s = "";
		if(Blue.isOpen){
			s += Blue.toString();
		}
		if(Orange.isOpen){
			s += Orange.toString(); 
		}
		return s;
	}
	
	
}
