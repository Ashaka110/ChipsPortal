package Structures;

import Recources.Libraries;
import Recources.Libraries.Direction;

public class Position {
	public int x, y;
	public Direction dir;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
		this.dir = Direction.Null;
	}
	
	public Position(int x, int y, int d){
		this.x = x;
		this.y = y;
		this.dir = Libraries.indexToDirection(d);
	}
	
	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	
	public Position add (Position b){
		return new Position(b.x + x, b.y + y);
	}
	
	public boolean equals(Position b){
		return b.x == x && b.y == y;
	}
	
	public static Position direction(Direction direction){
		Position relativePosition;
		
		switch (direction){
		case North:
			relativePosition = new Position(0,-1);
			break;
		case East:
			relativePosition = new Position(1,0);
			break;
		case South:
			relativePosition = new Position(0, 1);
			break;	
		case West:
			relativePosition = new Position(-1, 0);
			break;
		default:
			relativePosition = new Position(0, 0);
			break;
		}
		return relativePosition;
		
	}
}
