package Base;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Entities.*;
import Entities.PortalEntities.*;
import Entities.StandardEnemies.*;
import Recources.GuiManager;
import Recources.InputManager;
import Recources.LevelLoader;
import Recources.Libraries;
import Recources.Renderer;
import Recources.SpriteManager;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Structures.GUIButton;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public class LevelEditor extends BasicGameState{
	int _state;
	
	int[] toolbar = {2,	 0,   1,   8,   9,   10,
				 	 5,  4,   11,  3,   14,  7, 
				 	19,  6,   12,  18,  16,  13, 
				 	15,  17,  21,  22,  23,  24, 
				 	20,  25,  26,  27,  28,  29,
				 	184, 30,  31,  32,  33,  34, 
				 	185, 40,  41,  42,  43,  44,
				 	210,  45,  46,  47,  48,  49, 
				 	212,  51,  56,  61,  66,  67,  
				 	213,  68,  70,  75,  71,  39,  
				 	 73,   128, 182, 183, 142, 215,  
				 	149, 87,  166, 167, 168, 169,
				 	218,  220, 221,   -1,  -1,  -1, 
				 	155, 193, 194, 195, 198, 197
				 	
				 	};
	Vector2 toolbarPosition = new Vector2(586 + 32+16, 16);
	int toolbarTileWidth = 32;
	int toolbarRowLength =6;
	
	int leftBrush, rightBrush;
	Vector2 rBrushCurserPosition;// = new Vector2(576, 0);
	Vector2 lBrushCurserPosition;// = new Vector2(576, 0);
	
	Level openLevel;
	LevelTester levelTester;
	static final int SAVE_BUTTON_INDEX = 193;
	static final int PLAY_BUTTON_INDEX = 194;
	static final int NEW_BUTTON_INDEX = 195;
	static final int MENU_BUTTON_INDEX = 197;
	
	Vector2 offset= new Vector2(0,0);
	int tileDrawSize = Renderer.TILE_SOURCE_WIDTH;
	Vector2 panClickReference, panOffsetReference;
	
	Vector2 curserPosition = new Vector2(576, 0);
	
	GUIButton[] expansionButtons;
	
	
	static final String ERROR_MESSAGE_NO_PLAYER = "You must have at least one player to test"; 
	
	public LevelEditor(int state, LevelTester tester){
		this._state = state;
		levelTester = tester;
		
		expansionButtons = new GUIButton[8];
		for (int i = 0; i < expansionButtons.length; i++) {
			expansionButtons[i] = new GUIButton(0,0,32,32);

		}
		
		
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		LevelLoader.LevelSet = "1";
		openLevel = LevelLoader.loadLevel("Temp.txt");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//level view
		Renderer.setScale(tileDrawSize);
		Renderer.renderLevel(g, openLevel, offset,(int) tileDrawSize);
		Renderer.renderDebug(g, openLevel, offset);
		
		//expansion buttons
		for (int i = 0; i < expansionButtons.length; i++) {
			Renderer.renderSprite(g, expansionButtons[i].Position, 1, 32, openLevel, new Position(0, 0), SpriteManager.getSprite(getExpansionButtonImmageIndex(i)));
		}
		
		
		if(curserPosition != null)
			Renderer.renderImage(g, SpriteManager.getSprite(150), curserPosition);
		
		
		//Toolbar
		Renderer.renderImage(g, SpriteManager.getSprite(190), new Vector2(576 , 0));
		
		for (int i = 0; i < toolbar.length; i++) {
			Vector2 drawPosition = new Vector2(i%toolbarRowLength, i/toolbarRowLength).scale(toolbarTileWidth).add(toolbarPosition);
			
			if(toolbar[i] != -1) 
				Renderer.renderImage(g, SpriteManager.getSprite(toolbar[i]), drawPosition);
			
		}
		
		Renderer.renderImage(g, SpriteManager.getSprite(191), lBrushCurserPosition);// new Vector2(576 + 4*32, 544));
		Renderer.renderImage(g, SpriteManager.getSprite(192), rBrushCurserPosition);//new Vector2(576 +4*32+ 64, 544));
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Display.sync(60);
		InputManager.frameUpdate();
		if(InputManager.isPanButtonPressed()){
			panClickReference = InputManager.getMousePostion();
			panOffsetReference = offset;
		}
		if(InputManager.isPanButtonDown()){
			offset = panOffsetReference.add(InputManager.getMousePostion().add(panClickReference.scale(-1)).scale(1.0f/32.0f) );
		}
		
		Direction movedir = InputManager.getLastPressedMove();
		
		offset = offset.add( Vector2.direction(movedir).scale(-.5f));
		
		updateExpansionButtons();
		
		if (isMouseOverToolbar(toolbarPosition)){
			Position toolbarPos =getToolbarPositionUnderMouse(toolbarTileWidth);
			
			//curserPosition = new Vector2().add(toolbarPos).scale(toolbarTileWidth).add(toolbarPosition); 
			
			if(InputManager.isLeftMouseButtonPressed()){
				lBrushCurserPosition =  new Vector2().add(toolbarPos).scale(toolbarTileWidth).add(toolbarPosition); 
				clickToolbar(true, sbg);
			}
			if(InputManager.isRightMouseButtonPressed()){
				rBrushCurserPosition =  new Vector2().add(toolbarPos).scale(toolbarTileWidth).add(toolbarPosition); 
				clickToolbar(false, sbg);
			}
			
		}else{
			Position gamePosition = getGamePositionUnderMouse(tileDrawSize);
			
			curserPosition = new Vector2().add( gamePosition).add(offset).scale(tileDrawSize); 
			
			for (int i = 0; i < expansionButtons.length; i++) {
				if(expansionButtons[i].isOver(InputManager.getMousePostion())){
					curserPosition = expansionButtons[i].Position;
					if(InputManager.isLeftMouseButtonUp()){
						onExpansionButtonClick(i);
					}
					return;
				}
			}
			
			
			if(InputManager.isLeftMouseButtonDown() && !openLevel.isOffBoard(gamePosition)){
				useBush(leftBrush, gamePosition);
			}
			if(InputManager.isRightMouseButtonDown() && !openLevel.isOffBoard(gamePosition)){
				useBush(rightBrush, gamePosition);
			}
		}
		
		
	}

	public void clickToolbar(boolean left, StateBasedGame sbg){
		Position toolbarPos = getToolbarPositionUnderMouse(toolbarTileWidth);
		
		
		int brushSelected = toolbar[toolbarPos.x + toolbarRowLength * toolbarPos.y]; 
		
		if(brushSelected == 194){
			playTest(sbg);
		}else if(brushSelected == 195){
			openLevel = createDefaultLevel(32, 32);
		}else if(brushSelected == 197){
			sbg.enterState(Game.menu);
		}else if(brushSelected == 193){
			String a = JOptionPane.showInputDialog("Save as Level #: ");
			if(a != null){
				int level = Integer.parseInt(a);
				LevelLoader.saveLevel(openLevel, "1", level);
			}
		}else if(brushSelected == 198){
			String a = JOptionPane.showInputDialog("Open Level #: ");
			if(a != null){
				int level = Integer.parseInt(a);
				Level lev = LevelLoader.loadLevel("1", level);
				if(lev!=null)
					openLevel = lev;
				else
					JOptionPane.showMessageDialog(null, "Level does not yet exist.", "Bummer", JOptionPane.PLAIN_MESSAGE);
				
			}//GuiManager.createGUI();
		}else if(left)
			leftBrush = brushSelected;
		else{
			rightBrush = brushSelected;
		}
		
	}
	public void playTest(StateBasedGame sbg){
		if(openLevel.Players.isEmpty()){
			JOptionPane.showMessageDialog(null, ERROR_MESSAGE_NO_PLAYER, "Bummer", JOptionPane.PLAIN_MESSAGE);
			
		}else{
			LevelLoader.saveLevel(openLevel, "Temp.txt");
		  levelTester.loadLevel(LevelLoader.loadLevel("Temp.txt"));
		  sbg.enterState(Game.tester);
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return _state;
	}
	
	public Position getGamePositionUnderMouse(float scale){
		Vector2 mousePos = InputManager.getMousePostion();
		
		Vector2 tilepos = mousePos.add(offset.scale(-scale));
		return new Position((int)(tilepos.x/scale), (int)(tilepos.y/scale) );
	}
	
	public Position getToolbarPositionUnderMouse(float scale){
		Vector2 mousePos = InputManager.getMousePostion();
		Vector2 toolbarPos = mousePos.add(toolbarPosition.scale(-1));
		
		return new Position((int)(toolbarPos.x/scale), (int)(toolbarPos.y/scale) );
	}
	
	
	public boolean isMouseOverToolbar(Vector2 toolbarPos){
		Vector2 mousePos = InputManager.getMousePostion();
		return mousePos.x > toolbarPos.x;
	}
	
	boolean isPlacingTrap;
	boolean isPlacingSpawner;
	
	public void useBush(int brush, Position position){
		if(isPlacingTrap && (InputManager.isLeftMouseButtonPressed() || InputManager.isRightMouseButtonPressed())) {
			isPlacingTrap = false;
			TrapToggle t =(TrapToggle) openLevel.Traps.get(openLevel.Traps.size() -1);
			t.setTarget(position);
			openLevel.setTile(position, Tile.Trap_Closed);
			return;
		}if(isPlacingSpawner && (InputManager.isLeftMouseButtonPressed() || InputManager.isRightMouseButtonPressed())) {
			isPlacingSpawner = false;
			SpawnerToggle t =(SpawnerToggle) openLevel.Traps.get(openLevel.Traps.size() -1);
			t.setTarget(position);
			openLevel.setTile(position, Tile.Metal_Wall);
			return;
		}
		
		GameEntitiy ent = getEntity(brush, position);
		
		
		
		
		if(ent != null){
			ent.position.dir = InputManager.getMousePostion().getRelativeDirection(offset.add(position).scale(tileDrawSize).add(new Vector2(tileDrawSize/2,tileDrawSize/2))).reverse();
				
			if (ent instanceof LinearEntity){
				openLevel.removeFloorEntity(position, position.dir);
				openLevel.floorEntities.add(ent);
				
			}else if(ent instanceof Player){
				openLevel.removeEntity(position);
				openLevel.addPlayer(ent);
			}else if(ent instanceof TrapToggle){
				if(  (InputManager.isLeftMouseButtonPressed() || InputManager.isRightMouseButtonPressed())){
					isPlacingTrap = true;
					openLevel.Traps.add(ent);
					openLevel.setTile(position, Tile.Button_Trap);
				}
			}else if(ent instanceof SpawnerToggle){
				if(  (InputManager.isLeftMouseButtonPressed() || InputManager.isRightMouseButtonPressed())){
					isPlacingSpawner = true;
					openLevel.Traps.add(ent);
					openLevel.setTile(position, Tile.Button_Red);
				}
			}else{
				openLevel.removeEntity(openLevel.entities, position);
				openLevel.addEntity(ent);
			}
		}else if(brush == 155){
			openLevel.removeEntity(position);
		}else if(brush != -1){
			openLevel.setTile(position, Libraries.indexToTile(brush));
		}
	}
	
	public int getToolbarValue(){
		return 0;
	}
	
	
	
	public GameEntitiy getEntity(int index, Position position){
		switch(index){
		case 2:
			return new Player(position);
		case 4:
			return new Block(position);
		case 70:
			return new Ball(position);
		case 67:
			return new Blob(position);
		case 51:
			return new Bug(position);
		case 66:
			return new Fireball(position);
		case 56:
			return new Glider(position);
		case 68:
			return new Paramicium(position);
		case 75:
			return new Tank(position);
		case 61:
			return new Teeth(position);
		case 73:
			return new Walker(position);
		case 149:
			return new Laser(position);
		case 142:
			return new LightBridge(position);
		case 170:
			return new Turret(position);
		case 185:
			return new TrapToggle(position);
		case 184:
			return new SpawnerToggle(position);
		case 182:
			return new ExcursionFunnel(position, false);
		case 183:
			return new ExcursionFunnel(position, true);
		case 166:
			return new Reflector(position, 1);
		case 167:
			return new Reflector(position, 2);
		case 168:
			return new Reflector(position, 3);
		case 169:
			return new Reflector(position, 0);
		case 215:
			return new IceBlock(position);
		case 218:
			return new YellowTank(position);
		
		}
		
	
		return null;
	}
	
	
	public void onSaveLevel(int level){
		
	}
	
	public Level createDefaultLevel(int sizex, int sizey){
		Level l = new Level();
		
		Tile[][] tiles = new Tile[sizex][sizey];
		for(int x =0; x< sizex; x++){
			for(int y =0; y< sizey; y++){
				tiles[x][y] = Tile.Wall;
			}
		}
		
		l.board = tiles;
		return l;
		
		
	}
	private void onExpansionButtonClick(int i){
		switch (i){
		case 0:
			openLevel.expandLevel(-1, 0);
			break;
		case 1:
			openLevel.expandLevel(1, 0);
			break;
		case 2:
			openLevel.expandLevel(0, -1);
			break;
		case 3:
			openLevel.expandLevel(0, 1);
			break;
		case 4:
			openLevel.contractLevel(-1, 0);
			break;
		case 5:
			openLevel.contractLevel(1, 0);
			break;
		case 6:
			openLevel.contractLevel(0, -1);
			break;
		case 7:
			openLevel.contractLevel(0, 1);
			break;
		}
	}
		
	private int getExpansionButtonImmageIndex(int i){
		switch (i){
		case 0:
			return 189;
		case 1:
			return 187;
		case 2:
			return 186;
		case 3:
			return 188;
		case 4:
			return 187;
		case 5:
			return 189;
		case 6:
			return 188;
		case 7:
			return 186;
		}
		return 189;
	}
	
	private void updateExpansionButtons(){
		Position rightpos = new Position(openLevel.board[0].length +1, 0 );
		Position bottompos = new Position(0 , openLevel.board.length +1 );
		Position leftpos = new Position(-2, 0 );
		Position toppos = new Position(0 , -2 );
		
		Vector2 horisOffset = new Vector2(openLevel.board[0].length/2,0);//-offset.x + Game.PLAY_SIZE/tileDrawSize/2, 0);
		Vector2 vertOffset = new Vector2(0, openLevel.board.length/2);//-offset.y + Game.PLAY_SIZE/tileDrawSize/2);
		
		expansionButtons[0].Position = offset.add(vertOffset).add(0,.7f).add(leftpos).scale(tileDrawSize);
		expansionButtons[1].Position = offset.add(vertOffset).add(0,.7f).add(rightpos).scale(tileDrawSize);
		expansionButtons[2].Position = offset.add(horisOffset).add(.7f,0).add(toppos).scale(tileDrawSize);
		expansionButtons[3].Position = offset.add(horisOffset).add(.7f,0).add(bottompos).scale(tileDrawSize);
		
		expansionButtons[4].Position = offset.add(vertOffset).add(0,-.7f).add(leftpos).scale(tileDrawSize);
		expansionButtons[5].Position = offset.add(vertOffset).add(0,-.7f).add(rightpos).scale(tileDrawSize);
		expansionButtons[6].Position = offset.add(horisOffset).add(-.7f,0).add(toppos).scale(tileDrawSize);
		expansionButtons[7].Position = offset.add(horisOffset).add(-.7f,0).add(bottompos).scale(tileDrawSize);
		
		//expansionButtons[1].Position = offset.add(vertOffset).add(0,.7f);
		
		//Renderer.renderSprite(g, offset.add(vertOffset).add(0,.7f), 32, openLevel, rightpos, SpriteManager.getSprite(187));
//		Renderer.renderSprite(g, offset.add(horisOffset).add(.7f,0), 32, openLevel, toppos, SpriteManager.getSprite(186));
//		Renderer.renderSprite(g, offset.add(horisOffset).add(.7f,0), 32, openLevel, bottompos, SpriteManager.getSprite(188));
//		
//		Renderer.renderSprite(g, offset.add(vertOffset).add(0,-.7f), 32, openLevel, leftpos, SpriteManager.getSprite(187));
//		Renderer.renderSprite(g, offset.add(vertOffset).add(0,-.7f), 32, openLevel, rightpos, SpriteManager.getSprite(189));
//		Renderer.renderSprite(g, offset.add(horisOffset).add(-.7f,0), 32, openLevel, toppos, SpriteManager.getSprite(188));
//		Renderer.renderSprite(g, offset.add(horisOffset).add(-.7f,0), 32, openLevel, bottompos, SpriteManager.getSprite(186));
//		
		
		
	}
	
}
