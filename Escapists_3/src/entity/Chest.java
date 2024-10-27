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

public class Chest implements Entity {

	static boolean FirstInteraction = true;
	static boolean FirstGrabingItem = true;

	public Scene HierarchyScene = null;
	
	Vector2 Position = new Vector2();
	Player PlayerEntity = null;
	
	Texture ChestTexture = null;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		
		this.Position = new Vector2(21 * Renderer.TILE_SIZE, 62 * Renderer.TILE_SIZE);
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
		try {
			this.ChestTexture = Asset.Load("/tiles/chest.png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// Only used for registering only one key press (even if the user holds the key)
	boolean PressedOnce = false;
	
	@Override
	public void OnUpdate(double timeStep) {
		Vector2 centerPositionObject = new Vector2(this.Position.X, this.Position.Y);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionObject, playerWorldPos);
		
		if(distance < 96.0) {


			centerPositionObject = new Vector2(centerPositionObject.X - 76, centerPositionObject.Y - 42);
			playerWorldPos = this.PlayerEntity.Position;
			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "Get pickaxe");

			centerPositionObject = new Vector2(centerPositionObject.X + 48, centerPositionObject.Y + 24);
			playerWorldPos = this.PlayerEntity.Position;
			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS F)");
			
			if(Input.IsKeyPressed(KeyEvent.VK_F)) {
				
				if(!this.PressedOnce) {
					
					this.PlayerEntity.AddItem("pickaxe");
					
					if(FirstGrabingItem) {
						DialogueSystem.DrawDialogue("Player", "The pickaxe is a very useful tool!");
						DialogueSystem.DrawDialogue("Player", "But be careful, if the police sees you with it.");
						DialogueSystem.DrawDialogue("Player", "They will take all your items!");
						FirstGrabingItem = false;						
					}
					
					
				}
				this.PressedOnce = true;
			}
			else {
				this.PressedOnce = false;
			}
			
			if(FirstInteraction) {
				DialogueSystem.DrawDialogue("Player", "Good job!");
				DialogueSystem.DrawDialogue("Player", "You have found the police building!");
				DialogueSystem.DrawDialogue("Player", "Now go grab your pickaxe");
				DialogueSystem.DrawDialogue("Player", "and cut down some trees and stones");
				FirstInteraction = false;
			}
		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		Renderer.Submit(this.Position, new Vector2(Renderer.TILE_SIZE), this.ChestTexture);
	}

	@Override
	public String GetEntityName() {
		return "Chest";
	}

}
