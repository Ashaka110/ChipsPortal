package Recources;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import Recources.Libraries.Direction;
import Structures.Vector2;

public class InputManager {
	
	public static Input input;
	
	static Direction lastDiretionPressed;
	
	public static Boolean isLeftButtonPressed(){
		return input.isKeyDown(Input.KEY_A);
	}
	public static Boolean isRightButtonPressed(){
		return input.isKeyDown(Input.KEY_D);
	}
	public static Boolean isDownButtonPressed(){
		return input.isKeyDown(Input.KEY_S);
	}
	public static Boolean isUpButtonPressed(){
		return input.isKeyDown(Input.KEY_W);
	}
	
	public static Direction getMoveAxis(){
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
