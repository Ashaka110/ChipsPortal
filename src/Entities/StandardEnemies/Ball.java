package Entities.StandardEnemies;

import Entities.MovingEntity;
import Structures.Level;
import Structures.Position;

public class Ball extends MovingEntity {

	public Ball(Position pos) {
		super(pos);
		_ImageUpIndex = 110; 
		_ImageRightIndex = 110; 
		_ImageDownIndex = 110;
		_ImageLeftIndex = 110;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		if(!this.move(position.dir, level)){

			//System.out.println(position.dir.reverse() + "WHAT");
			
			//position.dir = position.dir.reverse();
			move(position.dir.reverse(), level);
			//System.out.println(position.dir.reverse() + "WHAT2");
		}
		return true;
	}

}
