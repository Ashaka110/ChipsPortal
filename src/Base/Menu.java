package Base;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Recources.FontManager;
import Recources.InputManager;
import Recources.Renderer;
import Recources.SpriteManager;
import Structures.GUIButton;
import Structures.Vector2;

public class Menu extends BasicGameState{
	int state;
	
	GUIButton playButton = new GUIButton(200, 225, 64, 64);
	GUIButton editorButton = new GUIButton(Game.DISPLAY_WIDTH/2 - 32, 225, 64, 64);;
	GUIButton settingsButton = new GUIButton(Game.DISPLAY_WIDTH - 200 -64, 225, 64, 64);;
	
	
	public Menu(int state){
		this.state = state;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Renderer.renderImage(g, SpriteManager.getSprite(199), new Vector2());
		Renderer.renderImage(g, SpriteManager.getSprite(194), playButton.Position, 64,64,32,32);
		Renderer.renderImage(g, SpriteManager.getSprite(195), editorButton.Position, 64,64,32,32);
		Renderer.renderImage(g, SpriteManager.getSprite(196), settingsButton.Position, 64,64,32,32);
		
		FontManager.drawString(430, 160, "NOW WITH PORTALS!", Color.cyan);
		
		FontManager.drawString(250, 480, "Programmed by: Aaron Hietanen", Color.green);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Display.sync(60);
		InputManager.frameUpdate();
		
		if(playButton.isOver(InputManager.getMousePostion())){
			if(InputManager.isLeftMouseButtonUp()){
				sbg.enterState(Game.player);
			}
		}
		
		if(editorButton.isOver(InputManager.getMousePostion())){
			if(InputManager.isLeftMouseButtonUp()){
				sbg.enterState(Game.editor);
			}
		}
		
		if(settingsButton.isOver(InputManager.getMousePostion())){
			if(InputManager.isLeftMouseButtonUp()){
				gc.exit();
			}
		}
	}

	@Override
	public int getID() {
		return state;
	}
	
	

}
