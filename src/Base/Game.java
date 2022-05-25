package Base;

import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import Recources.FontManager;
import Recources.InputManager;
import Recources.SpriteManager;
import Structures.Level;


public class Game extends StateBasedGame{

	public static final int menu = 0;
	public static final int player = 1;
	public static final int editor = 2;
	public static final int tester = 3;
	
	public static final int DISPLAY_WIDTH = 872;
	public static final int DISPLAY_HEIGHT = 576;
	public static final int PLAY_SIZE = 576;
	
	
	public Game(String name) {
		
		super(name);
		

		
		this.addState(new Menu(menu));
		this.addState(new LevelPlayer(player));
		LevelTester t = new LevelTester(tester);
		this.addState(t);
		this.addState(new LevelEditor(editor, t));
		
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {

		SpriteManager.loadSprites();
		InputManager.gameContainer = gc;
		InputManager.input = gc.getInput();
		FontManager.initFonts();
		
		
		
		this.getState(menu).init(gc, this);
		this.getState(player).init(gc, this);
		this.getState(editor).init(gc, this);

		this.enterState(menu);
	}
	
	public  void loadLevel(Level level){
		enterState(player);
		
		((LevelPlayer) getState(player)).loadLevel(level);
	}

}
