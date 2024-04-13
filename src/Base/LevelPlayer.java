package Base;


import javax.swing.JOptionPane;

//import org.lwjgl.util.Display;
//import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Entities.MovingEntity;
import Recources.FontManager;
import Recources.InputManager;
import Recources.LevelLoader;
import Recources.Libraries.Direction;
import Recources.NumberRenderer;
import Recources.Renderer;
import Recources.SpriteManager;
import Structures.GUIButton;
import Structures.Camera;
import Structures.Level;
import Structures.Vector2;

public class LevelPlayer extends BasicGameState{
	Level level;
	int _state;
	int levelNumber =1;
	boolean cycleButtonHeld;
	boolean isPlayStarted;
	
	Camera camera;
	
	GUIButton menuButton = new GUIButton(605,390, Renderer.TILE_SOURCE_WIDTH,Renderer.TILE_SOURCE_WIDTH);
	
	int tileDrawSize = Renderer.TILE_SOURCE_WIDTH * 2;
	
	public LevelPlayer(int state){
		this._state = state;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		LevelLoader.LevelSet = "2";
		level = LevelLoader.loadLevel(levelNumber);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Renderer.setScale(tileDrawSize);
		Renderer.renderLevel(g, level, Renderer.getScreenOffset(level), (int) tileDrawSize);
		//NumberRenderer.drawstat(g, clockTime, 200);
		Renderer.renderSidePannel(g, levelNumber, clockTime, level.chipCount(), level.activePlayer.getInventory());
		
		Renderer.renderImage(g, SpriteManager.getSprite(197), menuButton.Position);
		
		if(!isPlayStarted){
			renderLevelCard(g);
		}
		
		//Renderer.renderDebug(g, level, Renderer.getScreenOffset(level));
	}
	public String getTitle(){
		if(level.CheckpointEnabled){
			return "Checkpoint";
		}
		
		return "Level " + levelNumber;
	}
	
	
	public void renderLevelCard(Graphics g){
		Image img = SpriteManager.getSprite(200);
		float scaleFactor = 2;
		Vector2 imgSize = new Vector2(img.getWidth() *scaleFactor, img.getHeight() * scaleFactor);
		Vector2 imgPos = new Vector2(Game.PLAY_SIZE/2 - imgSize.x/2, 425);
		Renderer.renderImage(g, img, imgPos, (int)imgSize.x, (int)imgSize.y, img.getWidth(), img.getHeight());
		
		
		String levelna = getTitle();
		float fontSize = 40f;
		
		//FontManager.setFontSize(fontSize);
		int srtLength = FontManager.getStringLength(levelna);
		
		
		FontManager.drawString((int)(imgPos.x + imgSize.x/2  - srtLength/2), (int)imgPos.y + 20, levelna, Color.yellow);
	}

	int clockCounter = 0;
	int clockTime = 200;
	
	int moveCounter;
	boolean halfUpdate;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Display.sync(60);
		
		if(menuButton.isOver(InputManager.getMousePostion())){
			if(InputManager.isLeftMouseButtonPressed()){
				sbg.enterState(Game.menu);
			}
		}
		if(!isPlayStarted){
			if(InputManager.getMoveAxis() != Direction.Null){
				isPlayStarted = true;
			}
		}
		
		if(isPlayStarted){
			if(InputManager.isCyclePlayerButtonPressed()){
				if(!cycleButtonHeld){
					level.cyclePlayer();
					cycleButtonHeld = true;
				}
			}else{
				cycleButtonHeld = false;
			}
			
			clockCounter +=arg2;
			moveCounter+= arg2;
			
			while(clockCounter > 1000){
				clockCounter-=1000;
				clockTime--;
			}
			
			while(moveCounter > MovingEntity.moveSpeedMS/2){
				moveCounter-=MovingEntity.moveSpeedMS/2;
				
				
				if(halfUpdate){
					halfUpdate = false;
					level.halfUpdate();
				}else{
					if(level.hasWon()){
						onWin(sbg);
					}
					halfUpdate = true;
					level.Update();
				}
				if(!level.isPlayerAlive()){
					JOptionPane.showMessageDialog(null, "Oops!", "Bummer", JOptionPane.PLAIN_MESSAGE);
					InputManager.ClearInput();
					this.resetLevel();
					//Sys.alert("hi", "woo");
				}
			}
			
			level.frameUpdate(arg2);
			
			InputManager.frameUpdate();
			
			
			
			if(InputManager.isResetButtonPressed()){
				this.resetLevel();
			}
			
			
			if(InputManager.isZoomButtonPressed()){
				tileDrawSize = Renderer.TILE_SOURCE_WIDTH;
			} else{
				tileDrawSize = Renderer.TILE_SOURCE_WIDTH;
			}
			//if(InputManager.getScroll() != 0)
			//System.out.println(InputManager.getScroll());
		}		
		
		
	}

	private void advanceLevel() {
		levelNumber ++;
		
		loadLevel(levelNumber);
	}
	@Override
	public int getID() {
		return _state;
	}
	
	public void loadLevel(String file){
		level = LevelLoader.loadLevel(file);
		this.clockTime = level.maxTime;
		isPlayStarted = false;
	}
	public void loadLevel(int i){
		level = LevelLoader.loadLevel(i);
		this.clockTime = level.maxTime;
		isPlayStarted = false;
	}
	public void loadLevel(Level l){
		level = l;
		this.clockTime = level.maxTime;
		isPlayStarted = false;
	}
	
	public void resetLevel(){
		InputManager.ClearInput();
		if(level.CheckpointEnabled){
			loadLevel("CheckPoint.txt");
			level.CheckpointEnabled = true;
		}else{
			loadLevel(this.levelNumber);
			isPlayStarted = false;
		}
	}
	
	public void onWin(StateBasedGame sbg){
		JOptionPane.showMessageDialog(null, "you Win!", "Conratulations", JOptionPane.PLAIN_MESSAGE);
		InputManager.ClearInput();
		advanceLevel();
	}
}
