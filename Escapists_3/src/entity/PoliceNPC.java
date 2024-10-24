package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;
import renderer.Renderer;

class PoliceMovementData {
	int TargetPosition;
	String Axis;
	
	PoliceMovementData(String axis, int targetPosition) {
		this.TargetPosition = targetPosition;
		this.Axis = axis;
	}
}

public class PoliceNPC implements Entity {
	
	public Scene HierarchyScene = null;
	
	// Movement
	Vector2 Position;
	int PoliceSpeed = 3;
	
	// Texture/Animations
	HashMap<String, Texture[]> PoliceAnimationTexture = new HashMap<>();
	String Direction = "down";
	int AnimationIndex = 0;
	double AccumulatedTimeStep = 0.0;
	
	// Pre-defined movement for the police
	ArrayList<PoliceMovementData> PoliceMovementMap = new ArrayList<PoliceMovementData>();
	int MovementPoliceIndex = 0;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.Position = new Vector2(25 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2, 64 * Renderer.TILE_SIZE);
		this.HierarchyScene = hierarchyScene;
		
		LoadPlayerAssets();
		
		PoliceMovementMap.add(new PoliceMovementData("Y", 74 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2));
		PoliceMovementMap.add(new PoliceMovementData("X", 78 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2));
		PoliceMovementMap.add(new PoliceMovementData("X", 46 * Renderer.TILE_SIZE));
		PoliceMovementMap.add(new PoliceMovementData("Y", 60 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2));
		PoliceMovementMap.add(new PoliceMovementData("X", 58 * Renderer.TILE_SIZE));
		
		PoliceMovementMap.add(new PoliceMovementData("X", 46 * Renderer.TILE_SIZE));
		PoliceMovementMap.add(new PoliceMovementData("Y", 74 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2));
		PoliceMovementMap.add(new PoliceMovementData("X", 25 * Renderer.TILE_SIZE - Renderer.TILE_SIZE / 2));
		PoliceMovementMap.add(new PoliceMovementData("Y", 64 * Renderer.TILE_SIZE));
		//PoliceMovementMap.add(new PoliceMovementData("Y", 64 * Renderer.TILE_SIZE));
		
	}
	
	public void LoadPlayerAssets() {
		try {
			
			this.PoliceAnimationTexture.put("up",         new Texture[2]);
			this.PoliceAnimationTexture.get("up")[0] =    Asset.Load("/police/police_up_1.png").<Texture>As();
			this.PoliceAnimationTexture.get("up")[1] =    Asset.Load("/police/police_up_2.png").<Texture>As();
			
			this.PoliceAnimationTexture.put("down",       new Texture[2]);
			this.PoliceAnimationTexture.get("down")[0] =  Asset.Load("/police/police_down_1.png").<Texture>As();
			this.PoliceAnimationTexture.get("down")[1] =  Asset.Load("/police/police_down_2.png").<Texture>As();
			
			this.PoliceAnimationTexture.put("left",       new Texture[2]);
			this.PoliceAnimationTexture.get("left")[0] =  Asset.Load("/police/police_left_1.png").<Texture>As();
			this.PoliceAnimationTexture.get("left")[1] =  Asset.Load("/police/police_left_2.png").<Texture>As();
	    	
			this.PoliceAnimationTexture.put("right",      new Texture[2]);
			this.PoliceAnimationTexture.get("right")[0] = Asset.Load("/police/police_right_1.png").<Texture>As();
			this.PoliceAnimationTexture.get("right")[1] = Asset.Load("/police/police_right_2.png").<Texture>As();
			
			this.PoliceAnimationTexture.put("idle",       new Texture[2]);
			this.PoliceAnimationTexture.get("idle")[0] =  this.PoliceAnimationTexture.get("down")[0];
			this.PoliceAnimationTexture.get("idle")[1] =  this.PoliceAnimationTexture.get("down")[0];
			
	    	
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
		
		if(this.MovementPoliceIndex == this.PoliceMovementMap.size()) {
			this.MovementPoliceIndex = 0;
		}
		
		boolean hasCompletedPath = false;
		
		PoliceMovementData movementData = this.PoliceMovementMap.get(MovementPoliceIndex);
		
		if(movementData.Axis == "X") {
			hasCompletedPath = GoOnXAxis(timeStep, movementData.TargetPosition);
		} else if(movementData.Axis == "Y") {
			hasCompletedPath = GoOnYAxis(timeStep, movementData.TargetPosition);
		}
		
		if(hasCompletedPath) {
			this.MovementPoliceIndex++;
		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}
	
	public boolean GoOnXAxis(double timeStep, int positionX) {
		
		if(positionX == this.Position.X) {
			this.Direction = "idle";
			return true;
		}
		
		double sign = (positionX - this.Position.X);
		this.Direction = sign > 0.0 ? "right" : "left"; 
		
		if(this.Direction == "right") {
			this.Position.X += PoliceSpeed * 1;
			
			if(this.Position.X > positionX) {
				this.Position.X = positionX;
			}
			
		} else if(this.Direction == "left") {
			this.Position.X -= PoliceSpeed * 1;
			
			if(this.Position.X < positionX) {
				this.Position.X = positionX;
			}
		}
		
		return false;
	}
	
	public boolean GoOnYAxis(double timeStep, int positionY) {
		
		if(positionY == this.Position.Y) {
			this.Direction = "idle";
			return true;
		}
		
		double sign = (positionY - this.Position.Y);
		this.Direction = sign > 0.0 ? "down" : "up"; 
		
		if(this.Direction == "down") {
			this.Position.Y += PoliceSpeed * 1;
			
			if(this.Position.Y > positionY) {
				this.Position.Y = positionY;
			}
			
		} else if(this.Direction == "up") {
			this.Position.Y -= PoliceSpeed * 1;
			
			if(this.Position.Y < positionY) {
				this.Position.Y = positionY;
			}
		}
		
		return false;
	}
	
	public void UpdateAnimation(double timeStep) {
		AccumulatedTimeStep += timeStep;
		
		if(AccumulatedTimeStep >= 15.0) {
			AccumulatedTimeStep = 0.0;
			
			this.AnimationIndex = this.AnimationIndex == 0 ? 1 : 0;
		}
	}

	@Override
	public void OnDraw() {
		Texture image = this.PoliceAnimationTexture.get(this.Direction)[this.AnimationIndex];
	    
		Renderer.Submit(this.Position, new Vector2(Renderer.TILE_SIZE), image);
	}
	
}
