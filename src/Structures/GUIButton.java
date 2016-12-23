package Structures;

public class GUIButton {

	public Vector2 Position;
	Vector2 size;
	
	public GUIButton(int x, int y, int width, int height){
		Position = new Vector2(x,y);
		size = new Vector2(width, height);
	}
	
	public boolean isOver(Vector2 pos){
		return
			pos.x > Position.x &&
			pos.y > Position.y &&
			pos.x < Position.x +  size.x &&
			pos.y < Position.y +  size.y
			;
				
	}
	
	
	public void onMouseClick(boolean isdown){
		
	}
	
	public boolean isMouseUp(){
		return false;
	}
	
	
}
