package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import assets.Texture;
import math.Vector2;

class RenderData {
	public Vector2 Position = new Vector2();
	public Vector2 Scale = new Vector2();
	public Texture EntityTexture = null;
	public boolean IsScreenPosition = false;
	
	public RenderData(Vector2 position, Vector2 scale, Texture texture) {
		this.Position = position;
		this.Scale = scale;
		this.EntityTexture = texture;
	}
	
	public RenderData(Vector2 position, Vector2 scale, Texture texture, boolean isScreenPosition) {
		this.Position = position;
		this.Scale = scale;
		this.EntityTexture = texture;
		this.IsScreenPosition = isScreenPosition;
	}
}

public class Renderer {

	// GLOBAL SCREEN/WORLD SETTINGS
	public static final int TILE_SIZE = 16 * 3;
	public static final Vector2 MAX_WORLD_SIZE = new Vector2(100, 100);
	public static final int FRAMES_PER_SECOND = 60;
	
	// Render Queue
	public static ArrayList<RenderData> RenderQueue;
	
	
	public static void Initialize() {
		RenderQueue = new ArrayList<RenderData>();
	}
	
	public static void BeginScene() {
		RenderQueue.clear();
	}
	
	public static void Submit(Vector2 position, Vector2 scale, Texture texture) {
		RenderQueue.add(new RenderData(position, scale, texture));
	}
	
	public static void Submit(Vector2 position, Vector2 scale, Texture texture, boolean isScreenPosition) {
		RenderQueue.add(new RenderData(position, scale, texture, isScreenPosition));
	}
	
	public static void EndScene() {
		
	}
	
	public static void RenderFrame(Camera camera, Graphics graphicsAPI) {
		for(int i = 0; i < RenderQueue.size(); i++){
			RenderData currentData = RenderQueue.get(i);
			
			Vector2 screenPosition = camera.ComputeScreenPosition(currentData.Position);
			
			if(currentData.IsScreenPosition) {
				screenPosition = currentData.Position;
			}
			
			((Graphics2D)graphicsAPI).drawImage(
					currentData.EntityTexture.InternalImage,
					screenPosition.X, screenPosition.Y,
					currentData.Scale.X, currentData.Scale.Y,
					null
			);
		}
	}
	
	
}
