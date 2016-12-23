package Structures;

import Recources.Libraries.Direction;

public class Vector2 {
	public float x,y;
	
	public Vector2(){
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2 add (Vector2 b){
		return new Vector2(b.x + x, b.y + y);
	}
	public Vector2 add (float x, float y){
		return new Vector2(this.x + x, this.y + y);
	}
	
	public Vector2 add (Position b){
		return new Vector2(b.x + x, b.y + y);
	}
	
	public static Vector2 add(Vector2 a, Vector2 b){
		return new Vector2 (a.x + b.x, a.y + b.y);
	}
	
	public Vector2 scale(float scale){
		return new Vector2 (x*scale, y* scale);
	}
	
	public Vector2 normalize(){
		double length = length();
		
		return new Vector2((float)(x/length), (float)(y/length));
	}
	
	public float length(){
		return (float)Math.hypot(x, y);
	}
	
	public Direction getRelativeDirection(Vector2 b){
		if(b.x == x && b.y == y){
			return Direction.Null;
		}else if(x-b.x > y-b.y){
			if(x-b.x > -(y-b.y))
				return Direction.West;
			else 
				return Direction.South;
		}else if(x-b.x < y-b.y){
			if(x-b.x > -(y-b.y))
				return Direction.North;
			else 
				return Direction.East;
		}else if(x-b.x > y-b.y){
			return Direction.North;
		}
		return Direction.East;
		
	}
	
	public Vector2 moveTowardsZero(float speed, int deltaMS){
		Vector2 change = this.scale(-1).normalize().scale((float)deltaMS/(float)speed);
		
		if(this.length() > (float)deltaMS/(float)speed)
			return this.add(change);
		else{
			return new Vector2();
		}
	}
	
	public boolean equals(Vector2 obj) {
		// TODO Auto-generated method stub
		return x == obj.x && y == obj.y;
	}
	
	public static Vector2 direction(Direction d){
		switch(d){
		case East:
			return new Vector2(1,0);
		case North:
			return new Vector2(0,-1);
		case Null:
			return new Vector2(0,0);
		case South:
			return new Vector2(0,1);
		case West:
			return new Vector2(-1,0);
		default:
			return new Vector2(0,0);		
		}
	}
}
