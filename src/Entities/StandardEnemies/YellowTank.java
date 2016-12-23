package Entities.StandardEnemies;

import Entities.MovingEntity;
import Recources.Libraries.Direction;
import Structures.Level;
import Structures.Position;

public class YellowTank extends MovingEntity{

	public YellowTank(Position pos) {
		super(pos);
		_ImageUpIndex = 216; 
		_ImageRightIndex = 217; 
		_ImageDownIndex = 218;
		_ImageLeftIndex = 219;
	}

	@Override
	public boolean makeMoveDecision(Level level) {
		//this.move(position.dir, level);
		return true;
	}

	public void toggleMove(Direction dir, Level level){
		if(!freezeUntilNextFrame){
			freezeUntilNextFrame = true;
			this.move(dir, level);
		}
	}
	
	@Override
	public Boolean pushYeilds(Position target, Level level) {
		Block b = level.getBlock(target);
		if(b != null){
			if(!b.push(target.dir, level)){//!level.pushBlock(target)){
				return false;
			}
		}
		return super.pushYeilds(target, level);
	}
}
