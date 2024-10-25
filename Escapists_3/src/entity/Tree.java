package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;
import renderer.Renderer;
import renderer.UserInterface;

public class Tree implements Entity {

	public Scene HierarchyScene = null;
	
	Player PlayerEntity = null;
	Texture TreeTexture = null;
	Vector2 Position;
	int HealthPoint = 100;
	
	public Tree(Vector2 position) {
		this.Position = position;
	}
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
		Random random = new Random();
        int randomNumber = random.nextInt(3); // Generates a random number between 0 and 2
        
        try {
			this.TreeTexture = Asset.Load("/tiles/tree" + randomNumber + ".png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
        
	}

	// Only used for registering only one key press (even if the user holds the key)
	boolean PressedOnce = false;
	
	@Override
	public void OnUpdate(double timeStep) {
		
		if(this.HealthPoint <= 0) { return; }
		
		Vector2 centerPositionTree = new Vector2(this.Position.X + 240 / 2, this.Position.Y + 336);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionTree, this.PlayerEntity.GetWorldPosition());
		
		
		if(distance < 144.0) {
			
			if(this.PlayerEntity.Inventory.HasItem("pickaxe")) {
				centerPositionTree = new Vector2(centerPositionTree.X - 74, centerPositionTree.Y + 20);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionTree.minus(playerWorldPos), 32, "CUT TREE");
				
				centerPositionTree = new Vector2(centerPositionTree.X + 18, centerPositionTree.Y + 20);
				UserInterface.DrawText(centerPositionTree.minus(playerWorldPos), 24, "(PRESS T)");
				
				centerPositionTree = new Vector2(centerPositionTree.X - 36, centerPositionTree.Y - 340);
				UserInterface.DrawText(centerPositionTree.minus(playerWorldPos), 32, "HP: " + this.HealthPoint + "/100");
				
				if(Input.IsKeyPressed(KeyEvent.VK_T)) {
					
					if(!this.PressedOnce) {
						this.HealthPoint -= 10;
						
						if(this.HealthPoint == 0) {
							this.PlayerEntity.AddItem("wood");
						}
					}
					this.PressedOnce = true;
				}
				else {
					this.PressedOnce = false;
				}
				
			}
			else {
				centerPositionTree = new Vector2(centerPositionTree.X - 130, centerPositionTree.Y + 20);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionTree.minus(playerWorldPos), 24, "YOU CANT CUT THIS TREE");
				
				centerPositionTree = new Vector2(centerPositionTree.X + 40, centerPositionTree.Y - 340);
				UserInterface.DrawText(centerPositionTree.minus(playerWorldPos), 32, "HP: " + this.HealthPoint + "/100");
				
			}
			
			
			
			
			
			
			
		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		
		if(this.HealthPoint <= 0) { return; }
		
		Renderer.Submit(this.Position, new Vector2(240, 336), this.TreeTexture);
		
	}
	
	@Override
	public String GetEntityName() {
		return "Tree";
	}

}
