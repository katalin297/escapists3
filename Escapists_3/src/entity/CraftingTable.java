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

public class CraftingTable implements Entity {

	public Scene HierarchyScene = null;
	
	static boolean FirstInteraction = true;
	
	Vector2 Position = new Vector2();
	Player PlayerEntity = null;
	
	Texture CraftingTableTexture = null;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		
		this.Position = new Vector2(28 * Renderer.TILE_SIZE, 62 * Renderer.TILE_SIZE);
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
		try {
			this.CraftingTableTexture = Asset.Load("/tiles/craftingtable.png").<Texture>As();
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

			if(this.PlayerEntity.Inventory.HasItem("wood") && this.PlayerEntity.Inventory.HasItem("stone")) {

				centerPositionObject = new Vector2(centerPositionObject.X - 64, centerPositionObject.Y - 24);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "CRAFT SHOVEL");
				
				centerPositionObject = new Vector2(centerPositionObject.X + 48, centerPositionObject.Y + 20);
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS T)");
				
				
				if(Input.IsKeyPressed(KeyEvent.VK_T)) {
					
					if(!this.PressedOnce) {
						
						this.PlayerEntity.RemoveItem("wood");
						this.PlayerEntity.RemoveItem("stone");
						this.PlayerEntity.AddItem("shovel");
						
						DialogueSystem.DrawDialogue("Player", "Well done!!!");
						DialogueSystem.DrawDialogue("Player", "You are almost ready to escape.");
						DialogueSystem.DrawDialogue("Player", "You need to find a hole where you can escape.");
						DialogueSystem.DrawDialogue("Player", "Using the shovel, it will be easier for you.");
						DialogueSystem.DrawDialogue("Player", "Now go and explore the world to find the hole.");
						DialogueSystem.DrawDialogue("Player", "Be careful, the police is still all eyes on you!");
						
					}
					this.PressedOnce = true;
				}
				else {
					this.PressedOnce = false;
				}
				
			}
			else {
				centerPositionObject = new Vector2(centerPositionObject.X - 96, centerPositionObject.Y - 24);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "Not enough materials");				
			}
			

			if(FirstInteraction) {
				DialogueSystem.DrawDialogue("Player", "Hmm... you can craft a shovel with this.");
				DialogueSystem.DrawDialogue("Player", "But firstly, you need wood and stone.");
				DialogueSystem.DrawDialogue("Player", "Go and find those!");
				DialogueSystem.DrawDialogue("Player", "And come back and craft the final item.");
				FirstInteraction = false;
			}
		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		Renderer.Submit(this.Position, new Vector2(Renderer.TILE_SIZE), this.CraftingTableTexture);
	}

	@Override
	public String GetEntityName() {
		return "CraftingTable";
	}

}
