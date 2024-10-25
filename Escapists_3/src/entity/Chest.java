package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;
import renderer.Renderer;
import renderer.UserInterface;

public class Chest implements Entity {

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
		Vector2 centerPositionObject = new Vector2(this.Position.X + Renderer.TILE_SIZE / 2, this.Position.Y + Renderer.TILE_SIZE / 2);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionObject, this.PlayerEntity.GetWorldPosition());
		
		if(distance < 12.0) {


			centerPositionObject = new Vector2(centerPositionObject.X - 108, centerPositionObject.Y - 60);
			playerWorldPos = this.PlayerEntity.Position;
			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "Get pickaxe");

			centerPositionObject = new Vector2(centerPositionObject.X + 56, centerPositionObject.Y + 24);
			playerWorldPos = this.PlayerEntity.Position;
			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS F)");
			
			if(Input.IsKeyPressed(KeyEvent.VK_F)) {
				
				if(!this.PressedOnce) {
					this.PlayerEntity.AddItem("pickaxe");
					
				}
				this.PressedOnce = true;
			}
			else {
				this.PressedOnce = false;
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
		return "CraftingTable";
	}

}
