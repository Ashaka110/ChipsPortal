package Structures;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.Line;

import Entities.Button;
import Entities.GameEntitiy;
import Entities.Player;
import Entities.SpawnerToggle;
import Entities.PortalEntities.ExcursionFunnel;
import Entities.PortalEntities.LinearEntity;
import Entities.PortalEntities.Portal;
import Entities.PortalEntities.PortalFissler;
import Entities.StandardEnemies.Block;
import Entities.StandardEnemies.Tank;
import Entities.StandardEnemies.YellowTank;
import Recources.InputManager;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;

public class Level {
	
	public String levelName;
	public int maxTime;

	public Tile[][] board;
	
	
	public boolean CheckpointEnabled;
	
	public ArrayList<GameEntitiy> floorEntities;
	public ArrayList<GameEntitiy> portalFisslers;
	public ArrayList<GameEntitiy> entities;
	public ArrayList<GameEntitiy> Players;
	public ArrayList<GameEntitiy> Portals;
	public ArrayList<GameEntitiy> Traps;
	
	public Player activePlayer;
	
	public Level(){
		Portals= new ArrayList<GameEntitiy>();
		floorEntities= new ArrayList<GameEntitiy>();
		portalFisslers= new ArrayList<GameEntitiy>();
		entities= new ArrayList<GameEntitiy>();
		Traps= new ArrayList<GameEntitiy>();

		Players= new ArrayList<GameEntitiy>();
	}
	
	public Tile getTile (Position p){
		if(p.x == board[0].length + 500 || p.x == -500 || p.y == -500 || p.y == board.length + 500){
			return Tile.Wall;
		}if(p.x < 0 || p.x >= board[0].length || p.y < 0 || p.y >= board.length){
			return Tile.NULL_TILE;
		}
		return board[p.y][p.x];
	}
	
	public Position getPostitionInDiretion(Position startPosition,Direction direction){
		
		Position relativePosition = Position.direction(direction);
		
		Position result = startPosition.add(relativePosition);
		
		result.dir = direction;
		
		
		Portal p = getPortal(result);
		
		if(p != null){
			if(p.attemptEnter(result.dir)){
				Position pos = p.getExitPosition();
				
				//System.out.println(pos.x + " " + pos.y + " " + pos.dir.name() + " " + getTile(pos));			
				
				return pos;
				
			}
		}
		
		return result;
		
	}
	
	public Position getNextReverseReadTile(Position p, Tile t){
		Position result = new Position(p.x, p.y);
		
		//System.out.println("Searching same row");
		
		for(int x = p.x-1; x >=0; x--){
			if(board[p.y][x] == t){
				return new Position(x, p.y);
			}
		}
		//System.out.println("Searching ...");
		
		for(int y = p.y-1; y >= 0; y --){
			for(int x = board[y].length-1; x >= 0; x--){
				if(board[y][x] == t){
					return new Position(x, y);
				}
			}
		}
		//System.out.println("Searching from beginning");
		
		for(int y = board.length-1; y >= p.y; y --){
			for(int x = board[0].length-1; x >= 0; x--){
				if(board[y][x] == t){

					return new Position(x, y);
				}
			}
		}

		//System.out.println("Teleport not found");
		return result;
	}
	
	public boolean directionLeadsToPortal(Position startPosition,Direction direction){
		Position relativePosition = Position.direction(direction);
		
		Position result = startPosition.add(relativePosition);
		
		result.dir = direction;
		
		Portal p = getPortal(result);
		
		if(p != null){
			if(p.attemptEnter(result.dir)){
				return true;
			}
		}
		return false;
	}
	
	public Portal getPortal(Position pos){
		for(int i =0; i<Portals.size(); i++){
			if(Portals.get(i).position.equals(pos) && pos.dir == Portals.get(i).position.dir){
				return (Portal) Portals.get(i);
			}
		}
		return null;
	}
	
	public void toggleGreen(){
		for(int x = 0; x < board.length; x++ ){
			for(int y =0; y < board[x].length; y++){
				if(board[x][y] == Tile.ToggleWall_Closed){
					board[x][y] = Tile.ToggleWall_Open;
				}else if(board[x][y] == Tile.ToggleWall_Open){
					board[x][y] = Tile.ToggleWall_Closed;
				}
			}
		}
		
		for (int i = 0; i < floorEntities.size(); i++) {
			if(floorEntities.get(i) instanceof LinearEntity){
				((LinearEntity) floorEntities.get(i)).onGreenButtonPress();
				((LinearEntity) floorEntities.get(i)).generate(this);
			}
		}
	}
	public void toggleBlue(){
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i) instanceof Tank){
				((Tank)entities.get(i)).reverse();
			}
		}
	}
	public void toggleYellow(Direction dir){
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i) instanceof YellowTank){
				((YellowTank)entities.get(i)).toggleMove(dir, this);
			}
		}
	}
	public void toggleRed(Position p){
		ArrayList<GameEntitiy> ents = getGameEntitys(Traps, p);
		System.out.println("Found : " + ents.size() + " Spawners");
		for(int i =0; i<ents.size(); i++){
			if(ents.get(i) instanceof SpawnerToggle){
				
				((SpawnerToggle)ents.get(i)).onPress(this);
			}
		}
	}
	
	public void setTile(Position pos, Tile tileToPlace){
		if(pos.y < board.length && pos.x < board[0].length && pos.x >=0 && pos.y >=0)
			board[pos.y][pos.x] = tileToPlace;
	}
	public ArrayList<GameEntitiy> getAllGameEntitys (){
		ArrayList<GameEntitiy> ents = new ArrayList<GameEntitiy>();
		
		ents.addAll( entities);
		ents.addAll( Players);
		ents.addAll( floorEntities);
		ents.addAll( Traps);
		
		return ents;
	}
	public ArrayList<GameEntitiy> getGameEntitys (Position pos){
		ArrayList<GameEntitiy> ents = new ArrayList<GameEntitiy>();
		
		ents.addAll( getGameEntitys(entities, pos));
		ents.addAll( getGameEntitys(Players, pos));
		ents.addAll( getGameEntitys(floorEntities, pos));
		ents.addAll( getGameEntitys(Traps, pos));
		
		return ents;
	}
	public ArrayList<GameEntitiy> getGameEntitys(ArrayList<GameEntitiy> list, Position pos){
		ArrayList<GameEntitiy> ents = new ArrayList<GameEntitiy>();
		
		for(int i =0; i<list.size(); i++){
			if(list.get(i).position.equals(pos)){
				ents.add(list.get(i));
			}
		}
		return ents;
	}
	
	public boolean hasEntAt(Position pos, Class<?> c){
		ArrayList<GameEntitiy> ents = getGameEntitys(pos);
		for(int i =0; i < ents.size(); i++){
			if(ents.get(i).getClass().equals(c)){
				return true;
			}
		}
		return false;
	}
	
	public GameEntitiy getEntity(Position pos){
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i).position.equals(pos) ){
				return entities.get(i);
			}
		}
		return null;
	}
	
	public boolean isTileMonsterOcupied(Position pos){
		GameEntitiy entity = getEntity(pos);
		
		if(entity != null){
			if(!(entity instanceof Block) && !(entity instanceof Player) && !(entity instanceof PortalFissler)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isTileBlocked(Position pos){
		GameEntitiy entity = getEntity(pos);
		
		if(entity != null){
			if(!(entity instanceof Player)){
				if(entity.isAlive){
					return true;
				}
			}
		}
		return false;
	}

	public Block getBlock(Position pos){
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i) instanceof Block){
				if(entities.get(i).position.equals(pos)){
					return (Block) entities.get(i);
				}
			}
		}
		return null;
	}
	
	public boolean pushBlock(Position pos){
		return false;
	}
	
	public Player findPlayer(){
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i) instanceof Player){
				return (Player) entities.get(i);
			}
		}
		return null;
	}
	
	public boolean isPlayerAlive(){		
		for(int i = 0; i<Players.size(); i++){
			if(!Players.get(i).isAlive){
				return false;
			}
			
		}
		return true;//activePlayer.isAlive;
	}
	public void frameUpdate(int delta){
		for(int i = 0; i<floorEntities.size(); i++){
			floorEntities.get(i).frameUpdate(delta, this);
		}
		for(int i = 0; i<Players.size(); i++){	
			Players.get(i).frameUpdate(delta, this);
		}
		
		for(int i = 0; i<entities.size(); i++){
			entities.get(i).frameUpdate(delta, this);
		}
		for(int i = 0; i<portalFisslers.size(); i++){
			portalFisslers.get(i).logicUpdate(this);
			if(!portalFisslers.get(i).isAlive){
				portalFisslers.remove(i);
			}
		}
	}
	
	public void halfUpdate(){
		
		for(int i = 0; i<floorEntities.size(); i++){
			floorEntities.get(i).logicUpdate(this);
		}
		for(int i = 0; i<Players.size(); i++){
			
			Players.get(i).halfUpdate(this);
		}
		for(int i = 0; i<entities.size(); i++){
			entities.get(i).halfUpdate(this);
		}
	    InputManager.logicUpdate();
	}
	
	public void Update(){
		resetTraps();
		ArrayList<GameEntitiy> allEnts = getAllGameEntitys();
		for (int i = 0; i < allEnts.size(); i++) {
			allEnts.get(i).onStartFrame(this);
			
		}
		
		
		for(int i = 0; i<Traps.size(); i++){
			Traps.get(i).logicUpdate(this);
		}
		
		for(int i = 0; i<floorEntities.size(); i++){
			floorEntities.get(i).logicUpdate(this);
		}
		
		for(int i = 0; i<Players.size(); i++){
			if(((Player)Players.get(i)).hasWon(this)){
				if(Players.get(i) == activePlayer){
					this.cyclePlayer();
				}
				Players.remove(i);
				i--;
			}if(i > -1)
			Players.get(i).logicUpdate(this);
		}
	//	activePlayer.logicUpdate(this);
		
		
		for(int i = 0; i<entities.size(); i++){
			entities.get(i).logicUpdate(this);
			if(!entities.get(i).isAlive){
				entities.remove(i);
				i--;
			}
		}
		
	}
	
	public void resetTraps(){
		for(int x = 0; x < board.length; x++ ){
			for(int y =0; y < board[x].length; y++){
				if(board[x][y] == Tile.Trap_Open){
					board[x][y] = Tile.Trap_Closed;
				}
			}
		}
	}
	
	
	public Vector2 getCameraPlayerOffset(float viewWidth){
		if(activePlayer != null){
			Vector2 playerpos = activePlayer.movementOffset.add(activePlayer.position);
			
			Vector2 camPos = new Vector2();
			
			if(board[0].length < viewWidth +1){
				camPos.x = 0;
			}else if(playerpos.x +.5f< viewWidth/2){
				camPos.x = 0;
				//camPos.x = playerpos.x - viewWidth/2;
			}else if(playerpos.x +.5f > board[0].length - viewWidth/2){
				camPos.x = board[0].length - viewWidth ;
			}else{
				camPos.x = playerpos.x - viewWidth/2 + .5f;
			}
			
			
			if(board.length < viewWidth +1){
				camPos.y = 0;
			}else if(playerpos.y +.5f < viewWidth/2){
				camPos.y = 0;
			}else if(playerpos.y +.5f > board.length - viewWidth/2){
				camPos.y = board.length - viewWidth ;
			}else{
				camPos.y = playerpos.y - viewWidth/2 + .5f;
			}
			
			return camPos;
			
		}
		
		for(int i =0; i<entities.size(); i++){
			if(entities.get(i) instanceof Player){
				
				
			//	return ((Player)entities.get(i)).movementOffset.add(entities.get(i).position);
			}
		}
		return new Vector2();
	}
	
	public void addEntity(GameEntitiy entity){
		if(entity instanceof Button){
			Traps.add(entity);
		}else
		if(entity.isFloorEntity()){
			floorEntities.add(entity);
		}else
		if(entity instanceof Player && getTile(entity.position) != Tile.Metal_Wall){
			Players.add(entity);
			activePlayer = (Player) entity;
		}else
		entities.add(entity);
	}
	
	public ArrayList<GameEntitiy> getPlayers(){
		return Players;
	}
	public ArrayList<GameEntitiy> getFloorEntities(){
		return Players;
	}
	
	public void addPortal(GameEntitiy entity){
		if(!Portals.contains(entity))
			Portals.add(entity);
	}
	
	public void cyclePlayer(){
		int currentPlayerIndex = Players.indexOf(activePlayer);
		
		currentPlayerIndex ++;
		if(currentPlayerIndex == Players.size()){
			currentPlayerIndex = 0;
		}
		activePlayer = (Player) Players.get(currentPlayerIndex);
	}

	public void addFissler(PortalFissler portalFissler) {
		portalFisslers.add(portalFissler);
	}
	public void addPlayer(GameEntitiy p) {
		Players.add(p);
	}
	
	public int getPlayerCount(){
		return Players.size();
	}
	
	public void removeEntity(ArrayList<GameEntitiy> ents, Position pos){
		for(int i =0; i<ents.size(); i++){
			if(ents.get(i).position.equals(pos) ){
				ents.remove(i);
				i--;
			}
		}
	}
	public void removeFloorEntity(Position pos, Direction dir){
		for(int i =0; i<floorEntities.size(); i++){
			if(floorEntities.get(i).position.equals(pos) && floorEntities.get(i).position.dir == dir){
				floorEntities.remove(i);
				i--;
			}
		}
	}
	public void removeEntity(Position pos){
		removeEntity(entities, pos);
		removeEntity(Players, pos);
		removeEntity(floorEntities, pos);
		removeEntity(Traps, pos);
	}
	
	public GameEntitiy getNearestPlayer(Position pos){
		if(Players.size() <= 0){
			return null;
		}
		GameEntitiy nearestP = Players.get(0);
		
		for (int i = 0; i < Players.size(); i++) {
			
			
			
		}
		return nearestP;
		
	}
	
	public int chipCount(){
		int count = 0;
		for(int x = 0; x < board.length; x++ ){
			for(int y =0; y < board[x].length; y++){
				if(board[x][y] == Tile.Chip){
					count++;
				}
			}
		}
		for(int i =0; i<floorEntities.size(); i++){
			count += ((LinearEntity) floorEntities.get(i)).getChipCount();
		}
		return count;
	}
	
	public boolean hasWon(){
		for(int i =0; i<Players.size(); i++){
			if(! ((Player)Players.get(i)).hasWon(this)){
				return false;
			}
		}
		return true;
	}
	
	
	
	public void expandLevel ( int dx, int dy){
		
		Tile[][] newb =new Tile[board.length +Math.abs(dy)][board[0].length + Math.abs(dx)]; 
		
		for (int cx=0; cx<newb[0].length; cx++){
			for (int cy=0; cy<newb.length; cy++){
				newb[cy][cx]=Tile.Wall;
			}
		}
		int x , y ;
		if(dx>0){
			x=0;
		}else{
			x=Math.abs(dx);
		}
		if(dy>0){
			y=0;
		}else{
			y=Math.abs(dy);
		}
	
		ArrayList<GameEntitiy> ents = getAllGameEntitys();
		
		for(int c = 0; c < ents.size(); c++){
			ents.get(c).position.x +=x;
			ents.get(c).position.y +=y;
		}
		
		for (int cx=0; cx<newb[0].length; cx++){
			for (int cy=0; cy<newb.length; cy++){
				try{
					newb[cy+y][cx+x]=board[cy][cx];
				}catch(Exception e){
					System.out.println("nope "+ cx + " " + y);
				}
			}
		}
		board = newb;
		
	}
	public void contractLevel ( int dx, int dy){
		
		Tile[][] newb =new Tile[board.length -Math.abs(dy)][board[0].length - Math.abs(dx)]; 
		
		for (int cx=0; cx<newb[0].length; cx++){
			for (int cy=0; cy<newb.length; cy++){
				newb[cy][cx]=Tile.Wall;
			}
		}
		int x , y ;
		if(dx>0){
			x=0;
		}else{
			x= -Math.abs(dx);
		}
		if(dy>0){
			y=0;
		}else{
			y= -Math.abs(dy);
		}
	
		for (int cx=0; cx<newb[0].length; cx++){
			for (int cy=0; cy<newb.length; cy++){
				try{
					newb[cy+y][cx+x]=board[cy][cx];
				}catch(Exception e){
					System.out.println("nope "+ cx + " " + y);
				}
			}
		}
		
		
		board = newb;
		
		ArrayList<GameEntitiy> ents = getAllGameEntitys();
		
		for(int c = 0; c < ents.size(); c++){
			ents.get(c).position.x +=x;
			ents.get(c).position.y +=y;
			
			if(isOffBoard( ents.get(c).position)){
				removeEntity(ents.get(c).position);
			}
		}
		
	}
	
	public void reverseFunnels(){
		for(int i =0; i<floorEntities.size(); i++){
			if(floorEntities.get(i) instanceof ExcursionFunnel){
				((ExcursionFunnel) floorEntities.get(i)).reverse();
			}
		}
	}
	
	public boolean isOffBoard(Position p){
		return p.x < 0 || p.y < 0 || p.y > board.length -1 || p.x > board[0].length -1;
	}
	
	
}
