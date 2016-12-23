package Structures;

public class Camera {
	
	float scale;

	
	public float viewWidth = 9;
	public Vector2 screenSizePixels = new Vector2(576, 576);
	
	public Vector2 offset;
	
	public Camera(float sca){
		scale = sca;
	}
	
}
