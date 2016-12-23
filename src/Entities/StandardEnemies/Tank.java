package Entities.StandardEnemies;

import Entities.MovingEntity;
import Structures.Level;
import Structures.Position;

public class Tank extends MovingEntity{

	public Tank(Position pos) {
		super(pos);
		_ImageUpIndex = 78; 
		_ImageRightIndex = 79; 
		_ImageDownIndex = 76;
		_ImageLeftIndex = 77;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		this.move(position.dir, level);
		return true;
	}

	public void reverse(){
		position.dir = position.dir.reverse();
	}
}
