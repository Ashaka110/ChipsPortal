package Recources;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import Recources.Libraries.Direction;
import Structures.Vector2;

public class InputManager {
	
	public static Input input;
	public static GameContainer gameContainer;
	
	static Direction lastDiretionPressed;
	
	static boolean leftpause;
	static boolean rightpause;
	static boolean uppause;
	static boolean downpause;
	
	
	public static void ClearInput() {
		System.out.println("Clearing Input");

		uppause = true;
		downpause = true;
		rightpause = true;
		leftpause = true;
	}
	
	public static Boolean isLeftButtonPressed(){
		if(leftpause) {			
			if(input.isKeyPressed(Input.KEY_A)) {
				leftpause = false;
				return true;
			}else return false;
		}else
		return input.isKeyDown(Input.KEY_A);
	}
	public static Boolean isRightButtonPressed(){
		if(rightpause) {
			if(input.isKeyPressed(Input.KEY_D)) {
				rightpause = false;
				return true;
			}else return false;
		}else
		return input.isKeyDown(Input.KEY_D);
	}
	public static Boolean isDownButtonPressed(){
		if(downpause) {
			if(input.isKeyPressed(Input.KEY_S)) {
				downpause = false;
				return true;
			}else return false;
		}else
		return input.isKeyDown(Input.KEY_S);
	}
	public static Boolean isUpButtonPressed(){
		if(uppause) {
			if(input.isKeyPressed(Input.KEY_W)) {
				uppause = false;
				return true;
			}else return false;
		}else
		return input.isKeyDown(Input.KEY_W);
	}
	
	public static Direction getMoveAxis(){
		
		//ClearInput();
		//if(input.isKeyPressed(Input.KEY_A)) 
		{
			//System.out.println("Why");
		}
		if(isLeftButtonPressed()){
			return Direction.West;
		}else if(isRightButtonPressed()){
			return Direction.East;
		}else if(isDownButtonPressed()){
			return Direction.South;
		}else if(isUpButtonPressed()){
			return Direction.North;
		}else{
			return Direction.Null;
		}
	}
	
	static boolean leftMouseDownPreviousFrame;
	static boolean rightMouseDownPreviousFrame;
	
	static boolean leftMouseUp;
	static boolean rightMouseUp;
	
	static Direction previousFrameDirection;
	static Direction lastPressedDirection;
	static Direction directionPressedLastFrame;
	
	public static void frameUpdate(){
		
		leftMouseUp = leftMouseDownPreviousFrame &&  !isLeftMouseButtonDown();
		rightMouseUp  = rightMouseDownPreviousFrame &&  !isRightMouseButtonDown();
	
		leftMouseDownPreviousFrame = isLeftMouseButtonDown();
		rightMouseDownPreviousFrame = isRightMouseButtonDown();
	
		Direction d = getMoveAxis();
		
		if(previousFrameDirection == Direction.Null){
			if(d != Direction.Null){
				directionPressedLastFrame = d;
			}
		}
		
		
		
		
	//	if(d != null)
			lastDiretionPressed = d; 
		
		
		
			previousFrameDirection = d;
	}
	
	public static void logicUpdate(){
		//lastDiretionPressed = null;
	}
	
	public static Direction getLastPressedMove(){
		Direction d = lastDiretionPressed;
		lastDiretionPressed = null;
		if(directionPressedLastFrame != null){
			d = directionPressedLastFrame;
			directionPressedLastFrame = null;
		}
		return d;
	}
	
	public static boolean isResetButtonPressed(){
		return input.isKeyDown(Input.KEY_R);
	}
	
	public static boolean isLeftMouseButtonPressed(){
		return input.isMousePressed(0);
	}
	public static boolean isRightMouseButtonPressed(){
		return input.isMousePressed(1);//isMouseButtonDown(1);
	}
	public static boolean isLeftMouseButtonDown(){
		return input.isMouseButtonDown(0);
	}
	public static boolean isRightMouseButtonDown(){
		return input.isMouseButtonDown(1);//isMouseButtonDown(1);
	}
	public static boolean isLeftMouseButtonUp(){
		return leftMouseUp;
	}
	public static boolean isRightMouseButtonUp(){
		return rightMouseUp;//isMouseButtonDown(1);
	}
	public static boolean isCyclePlayerButtonPressed(){
		return input.isKeyDown(Input.KEY_C);
	}
	
	public static Vector2 getMousePostion(){
		return new Vector2(input.getMouseX(), input.getMouseY());
	}
	
	public static boolean isZoomButtonPressed(){
		
		return input.isMouseButtonDown(2);
	}
	
	public static boolean isPanButtonPressed(){
		
		return input.isMousePressed(2);
	}
	public static boolean isPanButtonDown(){
		
		return input.isMouseButtonDown(2);
	}
	public static float getScroll(){
		
		return Mouse.getDWheel();
	}
	public static void reset(){
		
	}
	
	
}
