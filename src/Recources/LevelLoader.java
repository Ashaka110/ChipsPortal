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
		
		return loadLevel("LevelSet" +  LevelSet + "/Level" + levelNumber +".txt");
	}
	
	public static Level loadLevel(String levelSet, int levelNumber){
		
		return loadLevel("LevelSet" +  levelSet + "/Level" + levelNumber +".txt");
	}
	
	
	private static Scanner openFile(String filename) {
		try{
			return new Scanner(new File(filename));
		}catch(Exception e){
			System.out.print("unable to locate file");
			return null;
		}
	}
	
	private static Level ReadLevelFromFile(Scanner scan){
		Level level = new Level();
		ArrayList<GameEntitiy> entities = new ArrayList<GameEntitiy>();  
		ArrayList<GameEntitiy> players = new ArrayList<GameEntitiy>();  

		level.Players = players;
		
		level.entities = entities;
		
		int width =scan.nextInt();
		int height =scan.nextInt();
		
		int[][] l=new int[width][height];
		
		for (int x = 0; x<l.length; x++){
			for(int y = 0; y < l[x].length; y++){
				int index = scan.nextInt();
				if(index == Tile.Block.index){
					entities.add(new Block(new Position(y, x, -1)));
					l[x][y]= Tile.Floor.index;
				}else if(index == Tile.Block_Fire.index){
					entities.add(new Block(new Position(y, x, -1)));
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
				level.addEntity(new Player(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				//level.activePlayer = (Player) players.get(players.size()-1);
				//p=new Player(scan.nextInt(), scan.nextInt(), scan.nextInt());
			}else if(n.equals("t")){
				level.maxTime = scan.nextInt();
				//timer=scan.nextInt();
			}else if(n.equals("bug")){
				entities.add(new Bug(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new bug(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("block")){
				entities.add(new Block(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				
			}else if(n.equals("iceblock")){
				entities.add(new IceBlock(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				
			}else if(n.equals("param")){
				entities.add(new Paramicium(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new paramicium(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("glider")){
				entities.add(new Glider(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new glider(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("ball")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				entities.add(new Ball(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new ball(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("blob")){
				entities.add(new Blob(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new blob(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}else if(n.equals("fireball")){
				entities.add(new Fireball(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new fireball(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("teeth")){
				entities.add(new Teeth	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("yellowtank")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				entities.add(new YellowTank(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new tank(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("tank")){
				int x = scan.nextInt();
				int y = scan.nextInt();
				int d = scan.nextInt();
				entities.add(new Tank(new Position(x,y,d)));
				scan.nextInt();
				//monster.add(new tank(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("walker")){
				entities.add(new Walker	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("reflector")){
				entities.add(new Reflector(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()) , scan.nextInt()));
				
				//monster.add(new Reflector(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt()));
			}else if(n.equals("lightbridge")){
				//bridge.add(new LightBridge(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.floorEntities.add(new LightBridge(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
			}else if(n.equals("excursionfunnel")){
				//bridge.add(new ExcursionFunnel(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.floorEntities.add(new ExcursionFunnel(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), scan.nextInt() == 0 ));
				
			}else if(n.equals("lazer")){
				//this.lazer.add(new LazerField(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), board, pl, monster));
				level.floorEntities.add(new Laser(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
			}else if(n.equals("trap")){
				level.Traps.add(new TrapToggle(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
			}else if(n.equals("spawner")){
				level.Traps.add(new SpawnerToggle(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt()), new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
			}else if(n.equals("turret")){
				entities.add(new Turret	(new Position(scan.nextInt(), scan.nextInt(), scan.nextInt())));
				scan.nextInt();
				//monster.add(new teeth(scan.nextInt(),scan.nextInt(),scan.nextInt(),scan.nextInt(), p));
			}else if(n.equals("hasp")){
				//p.portal=true;
			}else if(n.equals("inventory")){
				((Player)players.get(players.size()-1)).getInventory().setInventory(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
			}else if(n.equals("BluePortal")){
				Position p = new Position(scan.nextInt(), scan.nextInt(), scan.nextInt());
				((Player)players.get(players.size()-1)).getPortalManager().onOpenPortal(level, p, true);
			}else if(n.equals("OrangePortal")){
				Position p = new Position(scan.nextInt(), scan.nextInt(), scan.nextInt());
				((Player)players.get(players.size()-1)).getPortalManager().onOpenPortal(level, p, false);
			}
			
		}
		return level;
	}
	
	public static void saveLevel(Level level, String levelSet, int levelNumber)
	{
		saveLevel(level, "LevelSet" +  levelSet + "/Level" + levelNumber +".txt");
	}
	
	
	public static void saveLevel(Level level, String Filename){
		int chips=0;
		String b= "";
		
		for (int c=0; c< level.floorEntities.size(); c++){
			if(level.floorEntities.get(c) instanceof LightBridge){
				b=String.format("%s %s %s %s %s %s\n", b, "lightbridge", level.floorEntities.get(c).position.x, level.floorEntities.get(c).position.y, level.floorEntities.get(c).position.dir.index, 0 );
			}
		}
		for (int c=0; c< level.floorEntities.size(); c++){
			if(level.floorEntities.get(c) instanceof ExcursionFunnel){
				b=String.format("%s %s %s %s %s %s\n", b, "excursionfunnel", level.floorEntities.get(c).position.x, level.floorEntities.get(c).position.y, level.floorEntities.get(c).position.dir.index, ((ExcursionFunnel)level.floorEntities.get(c)).isReversed()?0:1);
			}
			//excursionfunnel
		}
		for (int c=0; c< level.floorEntities.size(); c++){
			if(level.floorEntities.get(c) instanceof Laser){
				b=String.format("%s %s %s %s %s %s\n", b, "lazer", level.floorEntities.get(c).position.x, level.floorEntities.get(c).position.y, level.floorEntities.get(c).position.dir.index, 0);
			}
		}
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
		for (int c=0; c<level.Players.size(); c++){
			Position p = level.Players.get(c).position;
			Player player =((Player)level.Players.get(c));
			Inventory i = player.getInventory();
			text.format("%s %s %s %s %s %s", "p ",p.x, p.y, p.dir.index, 0, "\n");
			text.format("%s %s %s", "inventory ", i.toString(), "\n");
			text.format("%s", player.getPortalManager().toString());
		}
		text.format("%s %s \n",	"t", level.maxTime);
		text.format("%s %s\n", "level", 1);
		for (int c=0; c<level.entities.size(); c++){
			Position p = level.entities.get(c).position;
			if (level.entities.get(c) instanceof Ball){
				text.format("%s %s %s %s %s \n", "ball" , p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Bug){
				text.format("%s %s %s %s %s\n", "bug" , p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Paramicium){
				text.format("%s %s %s %s %s\n", "param" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Fireball){
				text.format("%s %s %s %s %s\n", "fireball" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Glider){
				text.format("%s %s %s %s %s\n", "glider" , p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Blob){
				text.format("%s %s %s %s %s\n", "blob" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Teeth){
				text.format("%s %s %s %s %s\n", "teeth" , p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Tank){
				text.format("%s %s %s %s %s\n", "tank" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof YellowTank){
				text.format("%s %s %s %s %s\n", "yellowtank" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof IceBlock){
				text.format("%s %s %s %s %s\n", "iceblock" ,  p.x, p.y, p.dir.index, 0);
			}else if (level.entities.get(c) instanceof Reflector){
				text.format("%s %s %s %s %s\n", "reflector" ,  p.x, p.y, p.dir.index, ((Reflector) level.entities.get(c)).angle.index);
			}else if (level.entities.get(c) instanceof Block){
				text.format("%s %s %s %s %s\n", "block" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Walker){
					text.format("%s %s %s %s %s\n", "walker" ,  p.x, p.y, p.dir.index, 0);
			}if (level.entities.get(c) instanceof Turret){
				text.format("%s %s %s %s %s\n", "turret" ,  p.x, p.y, p.dir.index, 0);	
		//	}if (level.entities.get(c) instanceof){
		//		text.format("%s %s %s %s %s %s\n", "reflector" , monster.get(c).pos[0], monster.get(c).pos[1], monster.get(c).pos[2], monster.get(c).getid()-166, monster.get(c).on );
			}
		}
		for (int c=0; c<level.Traps.size(); c++){
			Position p = level.Traps.get(c).position;
			if (level.Traps.get(c) instanceof TrapToggle){
				Position t =((Button) level.Traps.get(c)).targetPosition;
				text.format("%s %s %s %s %s %s %s\n", "trap" ,  p.x, p.y, p.dir.index, t.x, t.y, t.dir.index);
			}if (level.Traps.get(c) instanceof SpawnerToggle){
				Position t =((Button) level.Traps.get(c)).targetPosition;
				text.format("%s %s %s %s %s %s %s\n", "spawner" ,  p.x, p.y, p.dir.index, t.x, t.y, t.dir.index);
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
		for(int i = 0; i<level.floorEntities.size(); i++){
			if(level.floorEntities.get(i) instanceof LinearEntity){
				((LinearEntity)level.floorEntities.get(i)).degenerate(level); 
			}
		}
		saveLevel(level, FileName);
		for(int i = 0; i<level.floorEntities.size(); i++){
			if(level.floorEntities.get(i) instanceof LinearEntity){
				((LinearEntity)level.floorEntities.get(i)).generate(level); 
			}
		}
	}
}