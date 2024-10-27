package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;
import renderer.DialogueSystem;
import renderer.Renderer;
import renderer.UserInterface;
import renderer.UserInterfaceOpening;

public class Hole implements Entity {

	public Scene HierarchyScene = null;
	
	Vector2 Position = new Vector2();
	Player PlayerEntity = null;
	
	Texture HoleTexture = null;
	
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		
		this.Position = new Vector2(83 * Renderer.TILE_SIZE, 83 * Renderer.TILE_SIZE);
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
		try {
			this.HoleTexture = Asset.Load("/tiles/hole.png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean PressedOnce = true;

	@Override
	public void OnUpdate(double timeStep) {
		Vector2 centerPositionObject = new Vector2(this.Position.X, this.Position.Y);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionObject, playerWorldPos);
		
		
		if(this.PlayerEntity.Inventory.HasItem("shovel")) {

			if(distance < (double)Renderer.TILE_SIZE * 1.5) {
				centerPositionObject = new Vector2(centerPositionObject.X - 26, centerPositionObject.Y - 16);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "ESCAPE");
				
				centerPositionObject = new Vector2(centerPositionObject.X + 10, centerPositionObject.Y + 20);
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS F)");
				
				if(Input.IsKeyPressed(KeyEvent.VK_F)) {
					
					if(!this.PressedOnce) {
						
//						DialogueSystem.DrawDialogue("Player:", "We have escaped!");
//						DialogueSystem.DrawDialogue("Player:", "We won");
						UserInterfaceOpening.SetGameStateAsFinished();
					}
					this.PressedOnce = true;
				}
				else {
					this.PressedOnce = false;
				}
				
			}
		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		if(this.PlayerEntity.Inventory.HasItem("shovel"))  {
			Renderer.Submit(this.Position, new Vector2(Renderer.TILE_SIZE), this.HoleTexture);
		}
	}

	@Override
	public String GetEntityName() {
		return "Stone";
	}

}
