package entity;

import java.awt.event.KeyEvent;
import java.io.IOException;

import java.util.HashMap;

import assets.Asset;
import assets.AssetType;
import assets.Texture;
import main.Input;
import main.Renderer;
import math.Vector2;

public class Player implements Entity {

	public Scene HierarchyScene = null;
	
	// Movement
	Vector2 Position;
	int PlayerSpeed = 4;

	// Texture/Animations
	HashMap<String, Texture[]> PlayerAnimationTexture = new HashMap<>();
	String Direction = "down";
	int AnimationIndex = 0;
	double AccumulatedTimeStep = 0.0;

	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.Position = new Vector2(500, 500);
		this.HierarchyScene = hierarchyScene;
		
		LoadPlayerAssets();
	}
	
	public void LoadPlayerAssets() {
		try {
			
			this.PlayerAnimationTexture.put("up",         new Texture[2]);
			this.PlayerAnimationTexture.get("up")[0] =    Asset.Load("/player/boy_up_1.png").<Texture>As();
			this.PlayerAnimationTexture.get("up")[1] =    Asset.Load("/player/boy_up_2.png").<Texture>As();
			
			this.PlayerAnimationTexture.put("down",       new Texture[2]);
			this.PlayerAnimationTexture.get("down")[0] =  Asset.Load("/player/boy_down_1.png").<Texture>As();
			this.PlayerAnimationTexture.get("down")[1] =  Asset.Load("/player/boy_down_2.png").<Texture>As();
			
			this.PlayerAnimationTexture.put("left",       new Texture[2]);
			this.PlayerAnimationTexture.get("left")[0] =  Asset.Load("/player/boy_left_1.png").<Texture>As();
			this.PlayerAnimationTexture.get("left")[1] =  Asset.Load("/player/boy_left_2.png").<Texture>As();
	    	
			this.PlayerAnimationTexture.put("right",      new Texture[2]);
			this.PlayerAnimationTexture.get("right")[0] = Asset.Load("/player/boy_right_1.png").<Texture>As();
			this.PlayerAnimationTexture.get("right")[1] = Asset.Load("/player/boy_right_2.png").<Texture>As();
			
	    	
		} catch (IOException e) {	  
			e.printStackTrace();
			
		}
	}

	@Override
	public void OnUpdate(double timeStep) {
		
		AccumulatedTimeStep += timeStep;
		
		if(AccumulatedTimeStep >= 15.0) {
			AccumulatedTimeStep = 0.0;
			
			if(Input.IsKeyPressed(KeyEvent.VK_W) ||
			   Input.IsKeyPressed(KeyEvent.VK_S) ||
			   Input.IsKeyPressed(KeyEvent.VK_A) ||
			   Input.IsKeyPressed(KeyEvent.VK_D)
			   ) {
				this.AnimationIndex = this.AnimationIndex == 0 ? 1 : 0;
			}
		}
		
		// Walking in world space
		if (Input.IsKeyPressed(KeyEvent.VK_W)) {
			this.Direction = "up";
			Position.Y -= PlayerSpeed * 1;
	        
		} else if (Input.IsKeyPressed(KeyEvent.VK_S)) {
			this.Direction = "down";
	    	Position.Y += PlayerSpeed * 1;
	    }
	        
	    if (Input.IsKeyPressed(KeyEvent.VK_A)) {
	    	this.Direction = "left";
	        Position.X -= PlayerSpeed * 1;
	        
	        
	    } else if (Input.IsKeyPressed(KeyEvent.VK_D)) {
	    	this.Direction = "right";
	        Position.X += PlayerSpeed * 1;
	    }
	    
	}

	@Override
	public void OnDraw() {
		Texture image = this.PlayerAnimationTexture.get(this.Direction)[this.AnimationIndex];
	    
		this.HierarchyScene.SetPrimaryCameraPosition(this.Position);
	    Renderer.Submit(this.HierarchyScene.GetPrimaryCamera().CenterOfScreen, new Vector2(Renderer.TILE_SIZE), image, true);
	}
	
}


