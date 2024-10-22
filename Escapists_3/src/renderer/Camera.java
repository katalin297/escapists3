package renderer;

import main.Application;
import math.Vector2;

public class Camera {
	
	Vector2 CameraPosition;
	public Vector2 CenterOfScreen;
	
	public Camera() {
		Vector2 screenDimensions = new Vector2(Application.SCREEN_WIDTH, Application.SCREEN_HEIGHT);
		this.CenterOfScreen = (screenDimensions.div(2)).minus(new Vector2(Renderer.TILE_SIZE / 2));
		
		this.CameraPosition = new Vector2();
	}
	
	public void UpdateCameraPosition(Vector2 position) {
		this.CameraPosition = position;
	}
	
	public Vector2 ComputeScreenPosition(Vector2 worldPos) {
		return worldPos.minus(this.CameraPosition);
	}
}
