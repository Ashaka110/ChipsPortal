package Entities;

import Structures.Level;
import Structures.Position;
import Recources.Libraries.Tile;

public class PurpleToggle extends TrapToggle {

	public PurpleToggle(Position pos) {
		super(pos);
	}


	
	public PurpleToggle(Position position, Position position2) {
		// TODO Auto-generated constructor stub
		super(position,position2);
	}



	@Override
	public Tile ButtonTile() {
		return Tile.Button_Purple;
	}

	@Override
	public Tile DefaultTargetTile() {
		return Tile.Purple_Wall_Closed;
	}

	@Override
	void setTrapOpen(Level level) {
		level.setTile(targetPosition, Tile.Purple_Wall_Open);
	}
}