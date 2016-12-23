package Base;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Recources.InputManager;
import Recources.Renderer;
import Recources.SpriteManager;
import Structures.GUIButton;
import Structures.Vector2;

public class LevelTester extends LevelPlayer {

	GUIButton backBotton = new GUIButton(800,390, Renderer.TILE_SOURCE_WIDTH,Renderer.TILE_SOURCE_WIDTH);
	int BackButtonSpriteIndex = 196;
	
	public LevelTester(int state) {
		super(state);	
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
		
		Renderer.renderImage(g, SpriteManager.getSprite(BackButtonSpriteIndex), backBotton.Position);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		super.update(gc, sbg, arg2);
		
		if(backBotton.isOver(InputManager.getMousePostion())){
			if(InputManager.isLeftMouseButtonDown()){
				sbg.enterState(Game.editor);
			}
		}
	}
	
	
	@Override
	public void resetLevel() {
		if(level.CheckpointEnabled){
			loadLevel("CheckPoint.txt");
			level.CheckpointEnabled= true;
		}else
			loadLevel("Temp.txt");
	}
	@Override
	public void onWin(StateBasedGame sbg) {
		JOptionPane.showMessageDialog(null, "you Win!", "woo", JOptionPane.PLAIN_MESSAGE);
		sbg.enterState(Game.editor);
	}
	
	public String getTitle(){
		if(level.CheckpointEnabled){
			return "Checkpoint";
		}
		
		return "Testing";
	}
	
}
