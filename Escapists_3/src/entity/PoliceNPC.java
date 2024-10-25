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
import renderer.DialogueSystem;
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
	Player PlayerEntity = null;
	
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
		
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
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

	boolean HasPlayerBeenCaught = false;
	boolean PressedOnce = false;
	int NrPressedKey = 0;
	
	@Override
	public void OnUpdate(double timeStep) {
		
		
		
		if(HasPlayerBeenCaught) {
			
			if(Input.IsKeyPressed(KeyEvent.VK_ENTER)) {
				
				if(!this.PressedOnce) {
					NrPressedKey++;
				}
				this.PressedOnce = true;
			}
			else {
				this.PressedOnce = false;
			}
			
			
			if(NrPressedKey == 3) {
				NrPressedKey = 0;
				HasPlayerBeenCaught = false;
				this.PressedOnce = false;
			}
			
			
			this.Direction = "idle";
			return;
		}
		
		Vector2 centerPositionPolice = new Vector2(this.Position.X, this.Position.Y);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionPolice, playerWorldPos);
		
		if((distance < (Renderer.TILE_SIZE * 4)) && this.PlayerEntity.Inventory.HasAnyItems()) {
			HasPlayerBeenCaught = true;
			DialogueSystem.DrawDialogue("Police Officer", "What do we have here?");
			DialogueSystem.DrawDialogue("Police Officer", "Why do you have a pickaxe?");
			DialogueSystem.DrawDialogue("Police Officer", "This is mine now!!!");
			this.PlayerEntity.Inventory.Clear();
		}
		
		
		
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
	
	@Override
	public String GetEntityName() {
		return "PoliceNPC";
	}
	
}
