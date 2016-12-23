package Entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class SpawnerToggle extends Button{

	public SpawnerToggle(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}
	public SpawnerToggle(Position pos, Position trappos) {
		super(pos, trappos);
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
	
	public void onPress(Level level){
		if(targetPosition != null) {
			ArrayList<GameEntitiy> ents =  level.getGameEntitys(targetPosition);
			System.out.println(ents.size());
			for (int i = 0; i < ents.size(); i++) {
				if(ents.get(i) instanceof MovingEntity){
					MovingEntity a = ((MovingEntity)ents.get(i)).cloned();
					if(a.move(a.position.dir, level)){
						level.addEntity(a);
						a.freezeUntilNextFrame = true;
					}
					System.out.println("Spawned");
				}
			}
		}
	}

}
