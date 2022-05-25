package Recources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import Entities.Button;
import Entities.GameEntitiy;
import Entities.Player;
import Entities.SpawnerToggle;
import Entities.TrapToggle;
import Entities.PortalEntities.ExcursionFunnel;
import Entities.PortalEntities.Laser;
import Entities.PortalEntities.LightBridge;
import Entities.PortalEntities.LinearEntity;
import Entities.PortalEntities.Reflector;
import Entities.PortalEntities.Turret;
import Entities.StandardEnemies.*;
import Recources.Libraries.Tile;
import Structures.Inventory;
import Structures.Level;
import Structures.Position;



public class LevelLoader {
	public static String LevelSet;
	
	
	public static Level loadLevel(String fileName){
		Scanner scan = openFile(fileName);
		
		if(scan == null){
			return null;
		}
		
		Level level = ReadLevelFromFile(scan);
		
		scan.close();
		
		return level;
	}
	
	public static Level loadLevel(int levelNumber){
		
		//return loadLevel("LevelSet" +  LevelSet + "/Level" + levelNumber +".txt");
		return loadLevel(FindLevel(LevelSet, levelNumber));
	}
	
	public static Level loadLevel(String levelSet, int levelNumber){
		
		//return loadLevel("LevelSet" +  levelSet + "/Level" + levelNumber +".txt");
		return loadLevel(FindLevel(levelSet, levelNumber));
	}
	
	static String FindLevel(String levelSet, int levelNumber) {
		File f = new File("LevelSet" +  levelSet);
		String[] pathnames = f.list();
		
		for(String path : pathnames) {
			String[] s = path.split("[-/.]");
			System.out.println(s[0]);
			System.out.println(s[1]);
			if(s[0].equals("Level" + levelNumber)) {
				return "LevelSet"+levelSet+"/"+path;
			}
		}
		return "";
	}
	
	private static Scanner openFile(String filename) {
		String[] s = filename.split("/");
		System.out.println(s);
		
		
		try{
			return new Scanner(new File(filename));
		}catch(Exception e){
			System.out.println("unable to locate file "+filename);
			return null;
		}
	}
	
	static Player lastCreatedPlayer;
	
	private static Level ReadLevelFromFile(Scanner scan){
		Level level = new Level();
		//ArrayList<GameEntitiy> entities = new ArrayList<GameEntitiy>();  
		//ArrayList<GameEntitiy> players = new ArrayList<GameEntitiy>();  

		//level.Players = players;
		
	//	level.entities = entities;
		
		int width =scan.nextInt();
		int height =scan.nextInt();
		
		int[][] l=new int[width][height];
		
		for (int x = 0; x<l.length; x++){
			for(int y = 0; y < l[x].length; y++){
				int index = scan.nextInt();
				if(index == Tile.Block.index){
					level.addEntity(new Block(new Position(y, x, -1)));
					l[x][y]= Tile.Floor.index;
				}else if(index == Tile.Block_Fire.index){
					level.addEntity(new Block(new Position(y, x, -1)));
					l[x][y]= Tile.Fire.index;
				}else{
					l[x][y]=index;
				}
			}
		}
		
		if(scan.next().matches("f")){
			System.out.println("good");
			
			level.board = Libraries.intArrayToTileArray(l);
		}
		while(scan.hasNext()){
			//Player pl = new Player(1, 1, 1, 1);
			String n = scan.next();
			if (n.equals("p")){
				lastCreatedPlayer = new Player(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()));
				level.addEntity(lastCreatedPlayer);
				//level.activePlayer = (Player) players.get(players.size()-1);
				//p=new Player(scan.nextInt(), scan.nextInt(), scan.nextInt());
			}else if(n.equals("t")){
				level.maxTime = scan.nextInt();
				//timer=scan.nextInt();
			}else if(n.equals("bug")){
				level.addEntity(new Bug(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new bug(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("block")){
				level.addEntity(new Block(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				
			}else if(n.equals("iceblock")){
				level.addEntity(new IceBlock(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				
			}else if(n.equals("param")){
				level.addEntity(new Paramicium(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new paramicium(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("glider")){
				level.addEntity(new Glider(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new glider(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("ball")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				level.addEntity(new Ball(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new ball(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("blob")){
				level.addEntity(new Blob(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new blob(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("fireball")){
				level.addEntity(new Fireball(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new fireball(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("teeth")){
				level.addEntity(new Teeth	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("yellowtank")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				level.addEntity(new YellowTank(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new tank(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("tank")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				level.addEntity(new Tank(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new tank(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("walker")){
				level.addEntity(new Walker	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("reflector")){
				level.addEntity(new Reflector(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()) , scan.nextInt()));
				
				//monster.add(new Reflector(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("lightbridge")){
				//bridge.add(new LightBridge(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.addEntity(new LightBridge(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
			}else if(n.equals("excursionfunnel")){
				//bridge.add(new ExcursionFunnel(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.addEntity(new ExcursionFunnel(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), scan.nextInt() == 0 ));
				
			}else if(n.equals("lazer")){
				//this.lazer.add(new LazerField(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.addEntity(new Laser(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
			}else if(n.equals("trap")){
				level.addEntity(new TrapToggle(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
			}else if(n.equals("spawner")){
				level.addEntity(new SpawnerToggle(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
			}else if(n.equals("turret")){
				level.addEntity(new Turret	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("hasp")){
				//p.portal=true;
			}else if(n.equals("inventory")){
				(lastCreatedPlayer).getInventory().setInventory(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
			}else if(n.equals("BluePortal")){
				Position p = new Position(scan.nextInt(), scan.nextInt(), scan.nextInt());
				(lastCreatedPlayer).getPortalManager().onOpenPortal(level, p, true);
			}else if(n.equals("OrangePortal")){
				Position p = new Position(scan.nextInt(), scan.nextInt(), scan.nextInt());
				(lastCreatedPlayer).getPortalManager().onOpenPortal(level, p, false);
			}
			
		}
		return level;
	}
	
	public static void saveLevel(Level level, String levelSet, int levelNumber)
	{
		saveLevel(level, "LevelSet" +  levelSet + "/Level" + levelNumber +".txt");
	}
	
	
	public static void saveLevel(Level level, String Filename){

		ArrayList<GameEntitiy> entities = level.getAllGameEntitys();
		
		int chips=0;
		
		Formatter text = null;
		try {
			text= new Formatter(Filename);
		} catch (FileNotFoundException e) {}
		
		text.format("%s %s\n", level.board.length, level.board[0].length);
		
		for(int x=0; x<level.board.length; x++){
			for(int y=0; y< level.board[x] .length; y++){
				text.format("%s%s", level.board[x][y].index, " ");
				if(level.board[x][y] == Tile.Chip){
					chips++;
				}
			}
			text.format("%s", "\n");
		}
		text.format("%s", "f\n");
		
		//ArrayList<GameEntitiy> Players = level.getPlayers();
		for (int c=0; c<entities.size(); c++){
			if ( entities.get(c) instanceof Player){
				Player player =((Player) entities.get(c));
				Position p = player.position;
				Inventory i = player.getInventory();
				text.format("%s %s %s %s %s %s", "p ",p.x, p.y, p.dir.index, 0, "\n");
				text.format("%s %s %s", "inventory ", i.toString(), "\n");
				text.format("%s", player.getPortalManager().toString());
			}
		}
		text.format("%s %s \n",	"t", level.maxTime);
		text.format("%s %s\n", "level", 1);
		
		for (int c=0; c< entities.size(); c++){
			Position p = entities.get(c).position;
			if ( entities.get(c) instanceof Ball){
				text.format("%s %s %s %s %s \n", "ball" , p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Bug){
				text.format("%s %s %s %s %s\n", "bug" , p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Paramicium){
				text.format("%s %s %s %s %s\n", "param" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Fireball){
				text.format("%s %s %s %s %s\n", "fireball" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Glider){
				text.format("%s %s %s %s %s\n", "glider" , p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Blob){
				text.format("%s %s %s %s %s\n", "blob" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Teeth){
				text.format("%s %s %s %s %s\n", "teeth" , p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Tank){
				text.format("%s %s %s %s %s\n", "tank" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof YellowTank){
				text.format("%s %s %s %s %s\n", "yellowtank" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof IceBlock){
				text.format("%s %s %s %s %s\n", "iceblock" ,  p.x, p.y, p.dir.index, 0);
			}else if (entities.get(c) instanceof Reflector){
				text.format("%s %s %s %s %s\n", "reflector" ,  p.x, p.y, p.dir.index, ((Reflector) entities.get(c)).angle.index);
			}else if (entities.get(c) instanceof Block){
				text.format("%s %s %s %s %s\n", "block" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Walker){
					text.format("%s %s %s %s %s\n", "walker" ,  p.x, p.y, p.dir.index, 0);
			}if (entities.get(c) instanceof Turret){
				text.format("%s %s %s %s %s\n", "turret" ,  p.x, p.y, p.dir.index, 0);	
		//	}if (level.entities.get(c) instanceof){
		//		text.format("%s %s %s %s %s %s\n", "reflector" , monster.get(c).pos[0], monster.get(c).pos[1], monster.get(c).pos[2], monster.get(c).getid()-166, monster.get(c).on );
			}
		}
		for (int c=0; c< entities.size(); c++){
			Position p = entities.get(c).position;
			if (entities.get(c) instanceof TrapToggle){
				Position t =((Button) entities.get(c)).targetPosition;
				text.format("%s %s %s %s %s %s %s\n", "trap" ,  p.x, p.y, p.dir.index, t.x, t.y, t.dir.index);
			}if (entities.get(c) instanceof SpawnerToggle){
				Position t =((Button) entities.get(c)).targetPosition;
				text.format("%s %s %s %s %s %s %s\n", "spawner" ,  p.x, p.y, p.dir.index, t.x, t.y, t.dir.index);
			}
		}
		
		String b= "";
		
		for (int c=0; c< entities.size(); c++){
			if(entities.get(c) instanceof LightBridge){
				b=String.format("%s %s %s %s %s %s\n", b, "lightbridge", entities.get(c).position.x, entities.get(c).position.y, entities.get(c).position.dir.index, 0 );
			}
		}
		for (int c=0; c< entities.size(); c++){
			if(entities.get(c) instanceof ExcursionFunnel){
				b=String.format("%s %s %s %s %s %s\n", b, "excursionfunnel", entities.get(c).position.x, entities.get(c).position.y, entities.get(c).position.dir.index, ((ExcursionFunnel)entities.get(c)).isReversed()?0:1);
			}
			//excursionfunnel
		}
		for (int c=0; c< entities.size(); c++){
			if(entities.get(c) instanceof Laser){
				b=String.format("%s %s %s %s %s %s\n", b, "lazer", entities.get(c).position.x, entities.get(c).position.y, entities.get(c).position.dir.index, 0);
			}
		}
		text.format("%s" , b);
		
		
		text.close();
//		if (lev != null){
//			monster.clear();
//			bridge.clear();
//			lazer.clear();
//			openFile();
//			try{
//				readFile();
//			}catch(Exception e){}
//			
//		}
	}
	
	
	public static void saveCheckPoint(Level level, String FileName){
		ArrayList<GameEntitiy> entities = level.getAllGameEntitys();
		for(int i = 0; i< entities.size(); i++){
			if( entities.get(i) instanceof LinearEntity){
				((LinearEntity) entities.get(i)).degenerate(level); 
			}
		}
		saveLevel(level, FileName);
		for(int i = 0; i<entities.size(); i++){
			if(entities.get(i) instanceof LinearEntity){
				((LinearEntity)entities.get(i)).generate(level); 
			}
		}
	}
}
