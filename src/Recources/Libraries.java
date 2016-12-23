package Recources;

import java.util.Arrays;

public class Libraries {
	public enum Tile{
		NULL_TILE(-1), Wall(1), Floor(0), Water(3), Block(4), Dirt(5), Fire(7), Chip(8), 
		Chip_Socket(9), Portal(10), Popup_Wall(11), 
		Trap_Closed(201), Trap_Open(12), Teleport(13), Gravel(14),
		Equipment_Fireboots(24), Equipment_Flippers(21),  Equipment_IceScates(22), Equipment_ForceBoots(23),
		Button_Red(15), Button_Blue(16),Button_Green(17), Button_Trap(18), Button_Yellow(220), Button_FunnelReverse(221),
		Key_Red(26), Key_Blue(27), Key_Yellow(28), Key_Green(29),
		ToggleWall_Open(25), ToggleWall_Closed(30),
		Hint(86), Checkpoint(87),
		BlueWall_Fake(19), BlueWall_Real(6), Metal_Wall(20), Light_Bridge(142), Laser(149),
		Lock_Red(31), Lock_Blue(32), Lock_Yellow(33), Lock_Green(34), Block_Fire(38), Bomb(39),
		ForceFloor_Random(40), ForceFloor_Up(43), ForceFloor_Right(44), ForceFloor_Down(41),
		ForceFloor_Left(42),
		ExcursionFunnel_Random(181), ExcursionFunnel_Up(181), ExcursionFunnel_Right(181), ExcursionFunnel_Down(181),
		ExcursionFunnel_Left(181), 
		ThinWall_N(211), ThinWall_E(212), ThinWall_S(213), ThinWall_W(214), ThinWall_SE(210),
		Ice(45), Ice_NW(48), Ice_NE(49), Ice_SE(46), Ice_SW(47), Theif(71), Portal_Gun(128);
		
		public int index;
		
		
		
		private Tile(int index){
			this.index=index;
		}
		
		public Boolean isIce(){
			if( Arrays.asList(Ice, Ice_NE, Ice_NW, Ice_SE, Ice_SW).contains(this)){
				return true;
			}
			return false;
		}
		public Boolean isForceFloor(){
			if( Arrays.asList(ForceFloor_Random, ForceFloor_Up, ForceFloor_Right, ForceFloor_Down,
					ForceFloor_Left).contains(this)){
				return true;
			}
			return false;
		}
		
		public static Direction getForceFloorDirection(Tile tile){
			if(tile == ForceFloor_Random || tile == ExcursionFunnel_Random){
				return Direction.random();
			}if(tile == ForceFloor_Up || tile == ExcursionFunnel_Up){
				return Direction.North;
			}if(tile == ForceFloor_Right || tile == ExcursionFunnel_Right){
				return Direction.East;
			}if(tile == ForceFloor_Down || tile == ExcursionFunnel_Down){
				return Direction.South;
			}if(tile ==	ForceFloor_Left || tile == ExcursionFunnel_Left){
				return Direction.West;
			}
			return Direction.Null;
		}
		
		public static Direction getIceDirection(Tile tile, Direction dir){
			if(tile == Ice){
				return dir;
			}if(tile == Ice_NE){
				if(dir == Direction.South){
					return Direction.East;
				}if(dir == Direction.West){
					return Direction.North;
				}
			}if(tile == Ice_SE){
				if(dir == Direction.North){
					return Direction.East;
				}if(dir == Direction.West){
					return Direction.South;
				}
			}if(tile == Ice_NW){
				if(dir == Direction.South){
					return Direction.West;
				}if(dir == Direction.East){
					return Direction.North;
				}
			}if(tile == Ice_SW){
				if(dir == Direction.North){
					return Direction.West;
				}if(dir == Direction.East){
					return Direction.South;
				}
			}
			return Direction.Null;
		}
		public boolean canEnterBlock(Direction dir){
			return canEnterBlock(this, dir);
		}
		
		public static boolean canEnterBlock(Tile tile, Direction dir){
			if(tile == Ice_SE){
				return dir == Direction.North || dir == Direction.West;
			}if(tile == Ice_NE){
				return dir == Direction.West || dir == Direction.South;
			}if(tile == Ice_SW){
				return dir == Direction.North || dir == Direction.East;
			}if(tile == Ice_NW){
				return dir == Direction.East || dir == Direction.South;
			}
			if(tile == ThinWall_N){
				return dir != Direction.South;
			} if(tile == ThinWall_E){
				return dir != Direction.West;
			} if(tile ==  ThinWall_S){
				return dir != Direction.North;
			} if(tile ==  ThinWall_W){
				return dir != Direction.East;
			} if(tile ==  ThinWall_SE){
				return dir != Direction.North && dir != Direction.West;
			}
			return true;
		}
		public boolean canLeaveBlock(Direction dir){
			return canLeaveBlock(this, dir);
		}
		public static boolean canLeaveBlock(Tile tile, Direction dir){
			if(tile == Ice_NW){
				return dir == Direction.North || dir == Direction.West;
			}if(tile == Ice_SW){
				return dir == Direction.West || dir == Direction.South;
			}if(tile == Ice_NE){
				return dir == Direction.North || dir == Direction.East;
			}if(tile == Ice_SE){
				return dir == Direction.East || dir == Direction.South;
			}
			
			if(tile == ThinWall_N){
				return dir != Direction.North;
			} if(tile == ThinWall_E){
				return dir != Direction.East;
			} if(tile ==  ThinWall_S){
				return dir != Direction.South;
			} if(tile ==  ThinWall_W){
				return dir != Direction.West;
			} if(tile ==  ThinWall_SE){
				return dir != Direction.South && dir != Direction.East;
			}
			
			return true;
		}
		public boolean isLinearEntityTile(){
			return this == Tile.Light_Bridge || this == Tile.Laser || this.isFunnel();
		}
		
		public boolean isFunnel(){
			return this == ExcursionFunnel_Random || this ==  ExcursionFunnel_Up || this ==  ExcursionFunnel_Right  || this == ExcursionFunnel_Down 
					|| this == ExcursionFunnel_Left;
		}
		
	}
	
	public enum Angle{
		NE(0), SE(1), SW(2), NW(3);
		
		public int index;
		private Angle(int i){
			this.index = i;
		}
		public static Angle getAngle(int index){
			switch (index){
			case 0:
				return NE;
			case 1:
				return SE;
			case 2:
				return SW;
			case 3:
				return NW;
			}
			return NE;
		}
		
		public Direction getOutputDirection(Direction dir){
			if(this == NE){
				if(dir == Direction.South){
					return Direction.East;
				}if(dir == Direction.West){
					return Direction.North;
				}
			}if(this == SE){
				if(dir == Direction.North){
					return Direction.East;
				}if(dir == Direction.West){
					return Direction.South;
				}
			}if(this == NW){
				if(dir == Direction.South){
					return Direction.West;
				}if(dir == Direction.East){
					return Direction.North;
				}
			}if(this == SW){
				if(dir == Direction.North){
					return Direction.West;
				}if(dir == Direction.East){
					return Direction.South;
				}
			}
			return Direction.Null;
		}
	}
	
	
	public enum TileProperties{
		Blocking, //Walls, Socket
		Destructable, //Keys, Chips, Dirt, FakeWalls
		Destructable_Deadly, //Bombs
		Deadly, //Fire, Water,  
		Passable; //Floor, Gravel, Ice, ForceFloors, Buttons, Portal
	}
	
	public enum Entity{
		Ball(), Blob, Block, Bug, Fireball, Glider, 
		Paramicium, Tank, Walker, Lightbridge, Laser, 
	}
	
	public enum Direction{
		North(0), East(1), South(2), West(3), Null(-1);
		public int index;
		
		private Direction(int index){
			this.index=index;
		}
		
		public Direction reverse(){
			switch(this){
			case East:
				return West;
			case North:
				return South;
			case Null:
				return Null;
			case South:
				return North;
			case West:
				return East;
			default:
				return Null;
			
			}
		}
		public Direction clockwise(){
			switch(this){
			case East:
				return South;
			case North:
				return East;
			case Null:
				return Null;
			case South:
				return West;
			case West:
				return North;
			default:
				return Null;
			
			}
		}public Direction counterClockwise(){
			switch(this){
			case East:
				return North;
			case North:
				return West;
			case Null:
				return Null;
			case South:
				return East;
			case West:
				return South;
			default:
				return Null;
			
			}
		}
		public static Direction random(){
			double rand = Math.random();
			if(rand < .25){
				return East;
			}if(rand < .5){
				return South;
			}if(rand < .75){
				return North;
			}
			return West;
		}
	}
	
	
	
	public static Direction indexToDirection(int index){

		switch (index){
		case 0:
			return Direction.North;
		case 1:
			return Direction.East;
		case 2:
			return Direction.South;
		case 3:
			return Direction.West;
		default:
			return Direction.Null;
		}
	}
	
	public static int directionToIndex(Direction direction){
		return direction.index;
	}
	
	public static Tile indexToTile(int index){
		for(Tile tile : Tile.values()){
			if(tile.index == index){
				return tile;
			}
		}
		return Tile.NULL_TILE;
	}
	
	public static int tileToIndex(Tile tile){
		return tile.index;
	}
	
	public static Tile[][] intArrayToTileArray(int[][] array){
		Tile[][] tileArray = new Tile[array.length][array[0].length];
		
		for (int x =0; x< array.length; x++ ){
			for(int y = 0; y < array[x].length; y++ ){
				tileArray[x][y] = indexToTile(array[x][y]);
			}
		}
		
		return tileArray;
		
	}
	
	public enum deathStatus{
		drowned("Chip can't swim without Flippers!"), 
		burned("You can't walk in fire without Fireboots!"),
		monster("Look out for monsters!"),
		block("Look out for moving blocks!"),
		time("Out of time!"),
		lazer("Look out for Lazers!"),
		paradox("You created a Paradox");
		
		private deathStatus(String message){
			deathMessage = message;
		}
		private String deathMessage;
		
		public String getDeathMessage(){
			return deathMessage;
		}
		public String getTopText(){
			return "Bummer";
		}
	}
}
