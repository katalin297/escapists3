package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import java.util.HashMap;

import assets.Asset;
import assets.AssetType;
import assets.Texture;
import main.Application;
import main.Input;
import math.BoundingBox;
import math.Vector2;
import physics.PhysicsSystem;
import renderer.DialogueSystem;
import renderer.Renderer;

public class Player implements Entity {

	public Scene HierarchyScene = null;
	
	// Movement
	Vector2 Position;
	int PlayerSpeed = 5;

	// Texture/Animations
	HashMap<String, Texture[]> PlayerAnimationTexture = new HashMap<>();
	String Direction = "down";
	int AnimationIndex = 0;
	double AccumulatedTimeStep = 0.0;

	PlayerInventory Inventory;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.Position = new Vector2(56 * Renderer.TILE_SIZE, 72 * Renderer.TILE_SIZE);
		this.HierarchyScene = hierarchyScene;
		this.Inventory = new PlayerInventory();
		
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
			
			this.PlayerAnimationTexture.put("idle",       new Texture[2]);
			this.PlayerAnimationTexture.get("idle")[0] =  this.PlayerAnimationTexture.get("down")[0];
			this.PlayerAnimationTexture.get("idle")[1] =  this.PlayerAnimationTexture.get("down")[0];
			
	    	
		} catch (IOException e) {	  
			e.printStackTrace();
			
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void OnUpdate(double timeStep) {
	
		UpdateAnimation(timeStep);
		
		Vector2 previousPosition = new Vector2(this.Position.X, this.Position.Y);
		boolean pressedAnyButton = false;
		
		
		// Walking in world space
		if (Input.IsKeyPressed(KeyEvent.VK_W)) {
			this.Direction = "up";
			Position.Y -= PlayerSpeed * 1;
			pressedAnyButton = true;
	        
		} else if (Input.IsKeyPressed(KeyEvent.VK_S)) {
			this.Direction = "down";
	    	Position.Y += PlayerSpeed * 1;
	    	pressedAnyButton = true;
	    }
		
		BoundingBox finalBoundingBox = new BoundingBox(new Vector2(this.Position.X + 8, this.Position.Y + 8), new Vector2(Renderer.TILE_SIZE - 16));
	    if(PhysicsSystem.IsInCollision(finalBoundingBox) ) {
	    	this.Position.Y = previousPosition.Y;
	    }
	    
	    
	        
	    if (Input.IsKeyPressed(KeyEvent.VK_A)) {
	    	this.Direction = "left";
	        Position.X -= PlayerSpeed * 1;
	        pressedAnyButton = true;
	        
	        
	    } else if (Input.IsKeyPressed(KeyEvent.VK_D)) {
	    	this.Direction = "right";
	        Position.X += PlayerSpeed * 1;
	        pressedAnyButton = true;
	    }
	    
	    
	    finalBoundingBox = new BoundingBox(new Vector2(this.Position.X + 8, this.Position.Y + 8), new Vector2(Renderer.TILE_SIZE - 16));
	    if(PhysicsSystem.IsInCollision(finalBoundingBox) ) {
	    	this.Position.X = previousPosition.X;
	    	//System.out.println("Aaa");
	    }
	    
	    if(!pressedAnyButton) {
	    	this.Direction = "idle";
	    	
	    }
	    
	    

	    
	}
	
	public void UpdateAnimation(double timeStep) {
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
	}
	
	@Override
	public void OnPhysicsUpdate(double timeStep) {
		return;
	}

	@Override
	public void OnDraw() {
		Texture image = this.PlayerAnimationTexture.get(this.Direction)[this.AnimationIndex];
	    
		this.HierarchyScene.SetPrimaryCameraPosition(this.Position);
	    Renderer.Submit(this.HierarchyScene.GetPrimaryCamera().CenterOfScreen, new Vector2(Renderer.TILE_SIZE), image, true);
	    
	    this.Inventory.OnDraw();
	}
	
	public Vector2 GetWorldPosition() { 
		return new Vector2(
				this.Position.X + (Application.SCREEN_WIDTH / 2)  - (Renderer.TILE_SIZE / 2),
				this.Position.Y + (Application.SCREEN_HEIGHT / 2) - (Renderer.TILE_SIZE / 2)
			);
	}
	
	@Override
	public String GetEntityName() {
		return "Player";
	}
	
	public void AddItem(String itemName) {
		this.Inventory.AddItemName(itemName);
	}
	
	public void RemoveItem(String itemName) {
		this.Inventory.RemoveItemName(itemName);
	}
	
}


